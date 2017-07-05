package zenghao.com.study.DBUtils.use;

import android.util.Log;
import java.util.HashMap;
import zenghao.com.study.DBUtils.frame.BaseContentProvider;
import zenghao.com.study.DBUtils.frame.BaseTableHelper;

/**
 *  
 * @author Jacks gong 
 * @data 2014-9-4 上午10:02:42 
 * @Describe Database 
 */ 
public class SampleDB extends BaseContentProvider {
 
	public final static String DATABASE_NAME = "sample.db";
 
	public final static String AUTHORITY = "sampledb";
 
	public final static int DATABASE_VERSION = 1;

	//此类在清单文件注册后启动程序自动调用构造器
	public SampleDB() {
        super(AUTHORITY);
		Log.e("====","SampleDBSampleDB");
	} 
 
	@Override 
	protected HashMap<String, BaseTableHelper> createAllTableHelper() {
		final HashMap<String, BaseTableHelper> hashMap = new HashMap<>();

		//不同的表创建不同的tableHelp然后在此put

		final UserInfoHelper userInfoHelper = UserInfoHelper.getImpl();
		hashMap.put(userInfoHelper.getTableName(), userInfoHelper);
 
		return hashMap;
	} 
 
	@Override 
	protected String getDatabaseName() {
		return DATABASE_NAME;
	} 
 
	@Override 
	protected int getDatabaseVersion() { 
		return DATABASE_VERSION;
	} 
 
} 