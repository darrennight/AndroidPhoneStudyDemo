package zenghao.com.study.DaemonService;

import android.support.annotation.Nullable;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import zenghao.com.study.MyApplication;

/**
 * 
 * 描述：消息子进程，禁止向里面加代码
 * 
 * @author lvwenlong@cyou-inc.com
 */
public class CoreService extends Service {

	protected static final String TAG = "CoreService";
	private static String currentUdid = "0";

	@Override
	public void onCreate() {
		super.onCreate();
		sCore = this;
		// 所有实时聊天的消息处理,剩下的代码都是为了保护此service不被系统回收///////////////////代码区域

		// /////////////////////////////////////////////////////////////////////////

		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		try {
			mStartForeground = getClass().getMethod("startForeground", mStartForegroundSignature);
			mStopForeground = getClass().getMethod("stopForeground", mStopForegroundSignature);
			return;
		} catch (NoSuchMethodException e) {
			mStartForeground = mStopForeground = null;
		}
		try {
			mSetForeground = getClass().getMethod("setForeground", mSetForegroundSignature);
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException("OS doesn't have Service.startForeground OR Service.setForeground!");
		}

		startForegroundService();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startForegroundService();
		return START_STICKY;
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}







	@Override
	public void onDestroy() {
		super.onDestroy();
		sCore = null;
		stopForeground(true);
		Intent mIntent = new Intent();
		mIntent.setClass(this, CoreService.class);
		mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startService(mIntent);

		// // Make sure our notification is gone.
		stopForegroundService();

	}

	// /////////////////以下代码是保持service不回收/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void startForegroundService() {
		if (this.isStartKernelService) {
			return;
		}
		if (Build.VERSION.SDK_INT < 18) {
			startForegroundCompat(19890319, new Notification());
			startForeground(19890319, new Notification());
			this.isStartKernelService = true;
			return;
		}
		Intent localIntent = new Intent(MyApplication.getApplication(), KernelService.class);
		localIntent.putExtra("NotificationID", 19890319);
		MyApplication.getApplication().startService(localIntent);
		return;
	}

	private void stopForegroundService() {

		if (!this.isStartKernelService) {
			return;
		}
		while (true) {
			try {
				if (Build.VERSION.SDK_INT >= 18) {
					continue;
				}
				this.isStartKernelService = false;
				stopForeground(true);
				stopForegroundCompat(19890319);
				return;
			} catch (Exception e) {
			}
		}
	}

	private class KernelService extends Service {

		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}

		@Override
		public void onCreate() {
			Log.v("newmessage789", "KernelService onCreate...onCreate");
			try {
				stopForeground(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.onCreate();
		}

		@Override
		public int onStartCommand(Intent paramIntent, int flags, int startId) {
			Log.v("newmessage789", "KernelService onStartCommand...onStartCommand");
			try {
				if ((paramIntent != null) && (paramIntent.getIntExtra("NotificationID", 0) > 0) && (CoreService.sCore != null)) {
					CoreService.sCore.startForeground(19890319, new Notification());
					// CoreService.sCore.a = true;
					startForeground(19890319, new Notification());
					CoreService.sCore.stopForeground(true);
					// startForegroundCompat(19890319, new Notification());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return START_STICKY;
		}

		@Override
		public void onDestroy() {
			Log.v("newmessage789", "KernelService onDestroy...onDestroy");
			try {
				stopForeground(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.onDestroy();
		}

	}

	public static String getCurrentUdid() {
		return currentUdid;
	}

	// private NetworkStateReceiver networkStateReceiver;

	// 以下保持Service不挂的参数/////////////////////////////////////////////////////
	static CoreService sCore = null;
	private boolean isStartKernelService = false;

	private static final Class<?>[] mSetForegroundSignature = new Class[] { boolean.class };
	private static final Class<?>[] mStartForegroundSignature = new Class[] { int.class, Notification.class };
	private static final Class<?>[] mStopForegroundSignature = new Class[] { boolean.class };

	private NotificationManager mNM;
	private Method mSetForeground;
	private Method mStartForeground;
	private Method mStopForeground;
	private Object[] mSetForegroundArgs = new Object[1];
	private Object[] mStartForegroundArgs = new Object[2];
	private Object[] mStopForegroundArgs = new Object[1];

	// 以上保持Service不挂的参数/////////////////////////////////////////////////////

	private void invokeMethod(Method method, Object[] args) {
		try {
			method.invoke(this, args);
		} catch (InvocationTargetException e) {
			// Should not happen.
			Log.v("ApiDemos CoreService", "Unable to invoke method");
		} catch (IllegalAccessException e) {
			// Should not happen.
			Log.v("ApiDemos CoreService", "Unable to invoke method");
		}
	}

	/**
	 * This is a wrapper around the new startForeground method, using the older
	 * APIs if it is not available.
	 */
	private void startForegroundCompat(int id, Notification notification) {
		// If we have the new startForeground API, then use it.
		if (mStartForeground != null) {
			mStartForegroundArgs[0] = Integer.valueOf(id);
			mStartForegroundArgs[1] = notification;
			invokeMethod(mStartForeground, mStartForegroundArgs);
			return;
		}

		// Fall back on the old API.
		mSetForegroundArgs[0] = Boolean.TRUE;
		invokeMethod(mSetForeground, mSetForegroundArgs);
		mNM.notify(id, notification);
	}

	/**
	 * This is a wrapper around the new stopForeground method, using the older
	 * APIs if it is not available.
	 */
	private void stopForegroundCompat(int id) {
		// If we have the new stopForeground API, then use it.
		if (mStopForeground != null) {
			mStopForegroundArgs[0] = Boolean.TRUE;
			invokeMethod(mStopForeground, mStopForegroundArgs);
			return;
		}

		// Fall back on the old API. Note to cancel BEFORE changing the
		// foreground state, since we could be killed at that point.
		mNM.cancel(id);
		mSetForegroundArgs[0] = Boolean.FALSE;
		invokeMethod(mSetForeground, mSetForegroundArgs);
	}

	static void startCoreService() {
		Intent localIntent = new Intent(MyApplication.getApplication(), CoreService.class);
		MyApplication.getApplication().startService(localIntent);
	}

	static void stopCoreService() {
		Intent localIntent1 = new Intent(MyApplication.getApplication(), CoreService.class);
		MyApplication.getApplication().stopService(localIntent1);
		if (Build.VERSION.SDK_INT >= 18) {
			Intent localIntent2 = new Intent(MyApplication.getApplication(), KernelService.class);
			MyApplication.getApplication().stopService(localIntent2);
		}
	}
}
