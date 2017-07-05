package zenghao.com.study.DBUtils.use;
 
import java.util.ArrayList;
import java.util.List;
 
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import zenghao.com.study.DBUtils.frame.BaseTableOperator;
import zenghao.com.study.MyApplication;

/** 
 *  
 * @author Jacks gong 
 * @data 2014-9-4 上午10:17:11 
 * @Describe 
 */ 
public class UserInfoOperator extends BaseTableOperator<UserInfoFields, UserInfoHelper> {
 
	private final static class ClassHolder { 
		private final static UserInfoOperator INSTANCE = new UserInfoOperator(MyApplication.getApplication(), UserInfoHelper.getImpl());
	} 
 
	public static UserInfoOperator getImpl() { 
		return ClassHolder.INSTANCE;
	} 
 
	public UserInfoOperator(Context context, UserInfoHelper helper) {
		super(context, helper);
	} 
 
	@Override 
	public Uri getUri() {
		return getTableHelper().getContentUri(SampleDB.AUTHORITY); 
	} 
 
	@Override 
	protected List<UserInfoFields> createColumns(Cursor c) {
		if (c == null || c.isClosed() || c.isAfterLast()) {
			return null; 
		} 
 
		List<UserInfoFields> list = new ArrayList<UserInfoFields>();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			list.add(new UserInfoFields(c));
		} 
 
		return list;
	} 
 
} 