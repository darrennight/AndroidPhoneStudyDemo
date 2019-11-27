package zenghao.com.study.scaleview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by weixiancui on 2014/12/10.
 */
public abstract class ScaleArrayAdapter extends ExtArrayAdapter<Object>  {
	public ScaleArrayAdapter(Context context, int resource) {
		super(context, resource);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		boolean isScale = false;
		if (convertView == null) {
			isScale = true;
		}
		View view =getScaleView(position, convertView, parent);
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
