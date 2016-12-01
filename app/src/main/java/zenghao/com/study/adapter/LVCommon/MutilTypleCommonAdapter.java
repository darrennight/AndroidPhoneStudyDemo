package zenghao.com.study.adapter.LVCommon;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *多类型通用listView
 * 此方式的封装需要 不同类型使用不同的数据bean 并且所有的bean以父类 数据模型继承
 * @author zenghao
 * @since 16/11/24 下午6:49
 */
public class MutilTypleCommonAdapter extends ListCommonAdapter {

    private Map<String, IViewHandler> mHandlerMap;
    private List<String> mTypleId;
    private IClickCallback listener;

    public MutilTypleCommonAdapter(Context context, List list) {
        super(context, list);
        mHandlerMap = new HashMap<>();
        mTypleId = new ArrayList<>();
    }

    @Override
    public int getViewTypeCount() {
        return mHandlerMap.size();
    }

    @Override
    public int getItemViewType(int position) {

        IViewHandler handler =  getViewHandler(mDatas.get(position).getClass().getSimpleName());

        String key = handler.getItemViewType();
        if(!mTypleId.contains(key)){
            mTypleId.add(key);
        }
        int id =  mTypleId.indexOf(handler.getItemViewType());
        Log.e("======","idid"+id);
        return id;
    }

    /**
     * 注册type&添加viewHandler
     * 此分类方式 用于多个数据bean 继承父类
     * 多分类时尽量一个类型一个bean 面向对象多态
     * @param bean
     * @param iviewHandler
     */
    public void registTyple(Class bean,Class iviewHandler){

        String key = bean.getSimpleName();
        IViewHandler result = mHandlerMap.get(key);
        if (result == null) {
            try {
                result = (IViewHandler) iviewHandler.newInstance();
                mHandlerMap.put(key, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private IViewHandler getViewHandler(String viewHandlerClazz) {
        return mHandlerMap.get(viewHandlerClazz);
    }


    @Override
    protected void convert(ViewHolder holder, Object data, int position,ViewGroup parent) {
        IViewHandler handler =  getViewHandler(data.getClass().getSimpleName());
        handler.setOnClickListener(listener);
        handler.handle(parent,holder,data,position);
    }

    @Override
    protected int getItemLayoutId(int position) {
        IViewHandler handler =  getViewHandler(mDatas.get(position).getClass().getSimpleName());
        return handler.getItemViewLayoutId();
    }

    public void setOnClickListener(IClickCallback callback){
        this.listener = callback;
    }
}
