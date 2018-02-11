package zenghao.com.study.picker.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import zenghao.com.study.R;
import zenghao.com.study.picker.listener.OnDismissListener;

/**
 * Created by Sai on 15/11/22.
 * 精仿iOSPickerViewController控件
 */
public class BasePickerView {
    private final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.BOTTOM
    );

    private Context context;

    protected ViewGroup contentContainer;

    private ViewGroup dialogView;//附加Dialog 的 根View

    protected int pickerview_timebtn_nor = 0xFF057dff;
    protected int pickerview_timebtn_pre = 0xFFc2daf5;
    protected int pickerview_bg_topbar = 0xFFf5f5f5;
    protected int pickerview_topbar_title = 0xFF000000;
    protected int bgColor_default = 0xFFFFFFFF;

    private OnDismissListener onDismissListener;
    private boolean dismissing;


    private boolean isShowing;
    private int gravity = Gravity.BOTTOM;


    private Dialog mDialog;
    private boolean cancelable;//是否能取消


    private boolean isAnim = true;

    public BasePickerView(Context context) {
        this.context = context;
    }

    protected void initViews(int backgroudId) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

            //如果是对话框模式
            dialogView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, null, false);
            //设置界面的背景为透明
            dialogView.setBackgroundColor(Color.TRANSPARENT);


            //这个是真正要加载时间选取器的父布局
            contentContainer = (ViewGroup) dialogView.findViewById(R.id.content_container);
            //设置对话框 左右间距屏幕30
            this.params.leftMargin = 30;
            this.params.rightMargin = 30;
            contentContainer.setLayoutParams(this.params);
            //创建对话框
            createDialog();
            //给背景设置点击事件,这样当点击内容以外的地方会关闭界面
            dialogView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

        setKeyBackCancelable(true);
    }

    protected void init() {

    }

    protected void initEvents() {
    }


    /**
     * @param isAnim 是否显示动画效果
     */
    public void show( boolean isAnim) {
        this.isAnim = isAnim;
        show();
    }

    /**
     * 添加View到根视图
     */
    public void show() {
        showDialog();
    }


    /**
     * 检测该View是不是已经添加到根视图
     *
     * @return 如果视图已经存在该View返回true
     */
    public boolean isShowing() {
        return false;

    }

    public void dismiss() {
        dismissDialog();

    }

    public BasePickerView setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    public void setKeyBackCancelable(boolean isCancelable) {

        ViewGroup group =dialogView;


        group.setFocusable(isCancelable);
        group.setFocusableInTouchMode(isCancelable);
        if (isCancelable) {
            group.setOnKeyListener(onKeyBackListener);
        } else {
            group.setOnKeyListener(null);
        }
    }

    private View.OnKeyListener onKeyBackListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == MotionEvent.ACTION_DOWN
                    && isShowing()) {
                dismiss();
                return true;
            }
            return false;
        }
    };

    protected BasePickerView setOutSideCancelable(boolean isCancelable) {

        /*if (rootView != null) {
            View view = rootView.findViewById(R.id.outmost_container);

            if (isCancelable) {
                view.setOnTouchListener(onCancelableTouchListener);
            } else {
                view.setOnTouchListener(null);
            }
        }*/

        return this;
    }

    /**
     * 设置对话框模式是否可以点击外部取消
     *
     * @param cancelable
     */
    public void setDialogOutSideCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        if (mDialog != null) {
            mDialog.setCancelable(cancelable);
        }
    }


    /**
     * Called when the user touch on black overlay in order to dismiss the dialog
     */
    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
            }
            return false;
        }
    };

    public View findViewById(int id) {
        return contentContainer.findViewById(id);
    }

    public void createDialog() {
        if (dialogView != null) {
            mDialog = new Dialog(context, R.style.custom_dialog2);
            mDialog.setCancelable(cancelable);//不能点外面取消,也不 能点back取消
            mDialog.setContentView(dialogView);

            mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mDialog.getWindow().setGravity(Gravity.BOTTOM);

            mDialog.getWindow().setWindowAnimations(R.style.pickerview_dialogAnim);
            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (onDismissListener != null) {
                        onDismissListener.onDismiss(BasePickerView.this);
                    }
                }
            });
        }

    }

    public void showDialog() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    public void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }


}
