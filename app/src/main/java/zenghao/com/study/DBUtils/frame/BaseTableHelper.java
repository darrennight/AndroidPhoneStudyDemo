package zenghao.com.study.DBUtils.frame;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
 
/** 
 *  
 * @author Jacks gong 
 * @data 2014-7-20 下午3:56:47 
 * @Describe 数据表规范
 * 规范(表名、主键、默认查询结果排序、表创建、表升级)
 */ 
public abstract class BaseTableHelper { 
 
	public final static String CREATE_TABLE_SQL_PRE = "CREATE TABLE IF NOT EXISTS ";
 
	public abstract String getPrimaryKey();
 
	public abstract String getTableName();
 
	public abstract String getDefaultSortOrder();
 
	public Uri getContentUri(final String authority) {
        return Uri.parse(BaseContentProvider.getContentAuthoritySlash(authority) + getTableName());
	}

	/**
	 * 如果操作集合，则必须以vnd.android.cursor.dir开头
	 * 1.vnd.android.cursor.dir代表返回结果为多列数据
	 * @param authority
	 * @return
	 */
	public String getContentType(final String authority) {
		return "vnd.android.cursor.dir/" + authority + "." + getTableName();
	}

	/**
	 * 如果操作非集合，则必须以vnd.android.cursor.item开头
	 * 2.vnd.android.cursor.item 代表返回结果为单列数据
	 * @param authority
	 * @return
	 */
	public String getEntryContentType(final String authority) {
		return "vnd.android.cursor.item/" + authority + "." + getTableName();
	} 
 
	public abstract void onDataBaseCreate(final SQLiteDatabase db);
 
	public abstract void onDatabaseUpgrade(final int oldVersion, final int newVersion, SQLiteDatabase db);
 
	protected String getCustomCreatePre() {
		return CREATE_TABLE_SQL_PRE + getTableName() + " (" + getPrimaryKey() + " INTEGER PRIMARY KEY AUTOINCREMENT," + BaseTableFields.CREATE_AT + " DATETIME DEFAULT(DATETIME('now', 'localtime')),";
	} 
	 
	 
 
} 