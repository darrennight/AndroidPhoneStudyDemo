package zenghao.com.study.util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

/**
 * 
 * @ClassName: SimpleAsyncTask 
 * @Description:简单的单线程异步任务 
 * @author zenghao
 * @date 2015年5月8日 下午2:01:02 
 *
 */
public abstract class SimpleAsyncTask {
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			postExcute();
		}
		
	};
	/**
	 * 执行后台任务前操作
	 */
	public abstract void preExcute();
	/**
	 * 执行后台任务
	 */
	public abstract void doInBackground();
	/**
	 * 任务执行结束后操作
	 */
	public abstract void postExcute();
	public void excute(){
		preExcute();
		new Thread(){
			public void run() {
				doInBackground();
				mHandler.sendEmptyMessage(0);
			}
		}.start();
	}
	public void publishProgress(int id){
		onProgressUpdate(id);
	}
	protected void onProgressUpdate(int id){
		
	}
	
}
