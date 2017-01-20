package zenghao.com.study.holdStyle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import zenghao.com.study.R;

/**
 *拆分之后的部分布局
 * 该布局的子控件再次初始化和设置内容
 * @author zenghao
 * @since 17/1/20 下午2:50
 */
public class RecommendHold extends BaseHolder {

    public RecommendHold(Context context){
        super(context);
    }


    @Override
    public View setContentView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.item_hold_recommend,null,false);
    }


}
