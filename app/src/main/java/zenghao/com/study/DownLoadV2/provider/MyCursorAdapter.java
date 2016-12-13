package zenghao.com.study.DownLoadV2.provider;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import zenghao.com.study.DownLoadV2.db.DownLoadDBHelpV2;
import zenghao.com.study.R;

/**
 * TODO
 *
 * @author zenghao
 * @since 16/12/11 下午6:18
 */
public class MyCursorAdapter extends CursorAdapter {

    public MyCursorAdapter(Context context, Cursor cursor) {
        super(context,cursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        View inflate = LayoutInflater.from(context).inflate(R.layout.item_down_task,parent,false);
        holder.item_tv_name = (TextView) inflate.findViewById(R.id.tv_task_name);
        holder.pb  = ((ProgressBar) inflate.findViewById(R.id.pb_task_pro));
        holder.pb.setMax(100);
        inflate.setTag(holder);
        return inflate;//返回的view传给bindView。
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        String name = cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_NAME));
        int phone = cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_PROGRESS));
        holder.item_tv_name.setText(name);
        holder.pb.setProgress(phone);
    }


    class ViewHolder {
        TextView item_tv_name;
        ProgressBar pb;
    }
}
