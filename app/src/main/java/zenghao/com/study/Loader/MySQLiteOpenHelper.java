package zenghao.com.study.Loader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
 
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
 
  public static final String db_name = "test.db3";
  public static final int version = 1;
 
  public MySQLiteOpenHelper(Context context) {
    super(context, db_name, null, version);
  }
 
  @Override
  public void onCreate(SQLiteDatabase db) {
 
    String create_sql = "create table tb_student(_id integer primary key autoincrement,name varchar(20),age integer)";
    db.execSQL(create_sql);
  }
 
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
  }
 
}