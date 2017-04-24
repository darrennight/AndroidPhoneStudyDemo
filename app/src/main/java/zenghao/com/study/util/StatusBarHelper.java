package zenghao.com.study.util;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;

@RequiresApi(16) // For client
@TargetApi(16) // For lint
public class StatusBarHelper {
	/**
	 * Passing this value to {@link DecorView#setSystemUiVisibility(int)} will show the status bar. 
	 */ 
	private static final int SHOW_STATUS_BAR_FLAG = 0;
 
	/** 
	 * Hides the status bar of the supplied Window. 
	 * 
	 * @param window 
	 * 		the Window containing the status bar to hide 
	 */ 
	@RequiresApi(16) // For client 
	@TargetApi(16) // For lint 
	public static void hideStatusBar(final Window window) {
		if (window == null) {
			throw new IllegalArgumentException("window cannot be null");
		} 
 
		final View decorView = window.getDecorView();
		decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
	} 
 
	/** 
	 * Shows the status bar of the supplied Window. 
	 * 
	 * @param window 
	 * 		the Window containing the status bar to hide, not null 
	 */ 
	@RequiresApi(16) // For client 
	@TargetApi(16) // For lint 
	public static void showStatusBar(final Window window) {
		if (window == null) {
			throw new IllegalArgumentException("window cannot be null");
		} 
 
		window.getDecorView().setSystemUiVisibility(SHOW_STATUS_BAR_FLAG);
	} 
} 