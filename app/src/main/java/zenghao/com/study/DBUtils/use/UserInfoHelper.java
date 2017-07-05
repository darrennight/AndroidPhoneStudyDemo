package zenghao.com.study.DBUtils.use;
 
import android.database.sqlite.SQLiteDatabase;
import zenghao.com.study.DBUtils.frame.CustomTableHelper;

/** 
 *  
 * @author Jacks gong 
 * @data 2014-9-4 上午10:14:11 
 * @Describe Table Helper 
 */ 
public class UserInfoHelper extends CustomTableHelper {
 
	public final static String TABLE_NAME = "user_info";
 
	private final static class ClassHolder { 
		private final static UserInfoHelper INSTANCE = new UserInfoHelper();
	} 
 
	public static UserInfoHelper getImpl() { 
		return ClassHolder.INSTANCE;
	} 
 
	@Override 
	public String getTableName() {
		return TABLE_NAME;
	} 
 
	@Override 
	public void onDataBaseCreate(SQLiteDatabase db) {
		final String create = getCustomCreatePre() + UserInfoFields.NAME + " TEXT," + UserInfoFields.SEX + " TEXT," + UserInfoFields.AGE + " TEXT);";
		db.execSQL(create);
	}

	@Override
	public void onDatabaseUpgrade(int oldVersion, int newVersion, SQLiteDatabase db) {
		super.onDatabaseUpgrade(oldVersion, newVersion, db);
	}
}