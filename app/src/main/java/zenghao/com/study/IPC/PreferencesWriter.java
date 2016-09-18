package zenghao.com.study.IPC;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import zenghao.com.study.MyApplication;

/**
 * SharePreference封装类
 * 
 * @Title: PreferencesWriter.java
 * @Description:
 * @author jiwei@breadtrip.com
 * @date 2015年3月17日 下午6:11:20
 * @version V1.0
 */
public abstract class PreferencesWriter {
	private String mName;
	private SharedPreferences mPreference;

	public static final String KEY_PREFERENCES_VERSION = "preferences_version";

	public static final int DEFAULT_PREFERENCES_VERSION = 0;

	protected PreferencesWriter() {
		super();
		this.mName = initFileName();
		this.mPreference = MyApplication.getApplication().getSharedPreferences(mName, Context.MODE_PRIVATE);
		initPreferenceChanges(getVersion());
	}

	/**
	 * 初始化文件名
	 * 
	 * @return
	 */
	protected abstract String initFileName();

	/**
	 * 初始化，通过判断当前版本型号，做出相应修改
	 */
	protected abstract void initPreferenceChanges(int preferVersion);


	/**
	 * 获取当前设置的版本型号
	 * 
	 * @return
	 */
	protected int getVersion() {
		return mPreference.getInt(KEY_PREFERENCES_VERSION, DEFAULT_PREFERENCES_VERSION);
	}

	/**
	 * 更新当前设置的版本型号
	 * 
	 * @param version
	 *            必须大于0
	 * @return
	 */
	protected boolean updateVersion(int version) {
		if (version > 0) {
			return updateValue(KEY_PREFERENCES_VERSION, version);
		}
		return false;
	}

	public boolean clear() {
		Editor editor = mPreference.edit();
		editor.clear();
		return editor.commit();
	}

	protected boolean removeKey(String key) {
		Editor editor = mPreference.edit();
		editor.remove(key);
		return editor.commit();
	}

	protected boolean updateValue(String key, String value) {
		Editor editor = mPreference.edit();
		editor.putString(key, value);
		return editor.commit();
	}

	protected boolean updateValue(String key, boolean value) {
		Editor editor = mPreference.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	protected boolean updateValue(String key, int value) {
		Editor editor = mPreference.edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	protected boolean updateValue(String key, float value) {
		Editor editor = mPreference.edit();
		editor.putFloat(key, value);
		return editor.commit();
	}

	protected boolean updateValue(String key, long value) {
		Editor editor = mPreference.edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	protected boolean updateValue(String key, Serializable serializableObject) {
		try {
			String seriString = getSeriString(serializableObject);
			Editor editor = mPreference.edit();
			editor.putString(key, seriString);
			return editor.commit();
		} catch (Exception e) {
			Log.e("PreferencesWriter", "保存序列化对象失败，key=" + key);
			return false;
		}
	}

	protected long getLong(String key, long defaultValue) {
		return mPreference.getLong(key, defaultValue);
	}

	protected String getString(String key, String defaultValue) {
		return mPreference.getString(key, defaultValue);
	}

	protected int getInt(String key, int defaultValue) {
		return mPreference.getInt(key, defaultValue);
	}

	protected float getFloat(String key, float defaultValue) {
		return mPreference.getFloat(key, defaultValue);
	}

	protected boolean getBoolean(String key, boolean defaultValue) {
		return mPreference.getBoolean(key, defaultValue);
	}

	protected Object getObject(String key, Object defaultValue) {
		try {
			String localSeriString = mPreference.getString(key, null);
			if (localSeriString != null) {
				return getObjectFromSerString(localSeriString);
			}
		} catch (Exception e) {
			Log.e("PreferencesWriter", "读取序列化对象失败，key=" + key);
			return defaultValue;
		}
		return defaultValue;
	}

	/**
	 * 序列化一个对象
	 * 
	 * @param object
	 * @return
	 */
	protected static String getSeriString(Object object) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		String productBase64 = "";
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			productBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} catch (OutOfMemoryError e) {
			throw new RuntimeException("OutOfMemoryError occurred. ", e);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
		return productBase64;
	}

	/**
	 * 反序列化一个对象
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private static Object getObjectFromSerString(String data) throws Exception {
		byte[] objBytes = Base64.decode(data.getBytes(), Base64.DEFAULT);
		if (objBytes == null || objBytes.length == 0) {
			return null;
		}
		ByteArrayInputStream bi = null;
		ObjectInputStream oi = null;
		Object object = null;
		try {
			bi = new ByteArrayInputStream(objBytes);
			oi = new ObjectInputStream(bi);
			object = oi.readObject();
		} finally {
			if (oi != null) {
				oi.close();
			}
			if (bi != null) {
				bi.close();
			}
		}
		return object;
	}

	protected boolean contains(String key){
		return mPreference.contains(key);
	}
}
