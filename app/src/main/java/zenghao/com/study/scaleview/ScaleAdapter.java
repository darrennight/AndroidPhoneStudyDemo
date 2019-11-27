package zenghao.com.study.scaleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by lizhennian on 2014/5/30.
 */
public abstract class ScaleAdapter extends BaseAdapter {
	public LayoutInflater mInflater;

	public ScaleAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		boolean isScale = false;
		if (convertView == null) {
			isScale = true;
		}
		View view = getScaleView(position, convertView, parent);
		if (isScale) {
			try {
				ScaleCalculator.getInstance().scaleView(view);
			} catch (Exception e) {
			}
		}
		return view;
	}

	/**
	 * Need to subclass overrides
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	public abstract View getScaleView(int position, View convertView,
			ViewGroup parent);
}
