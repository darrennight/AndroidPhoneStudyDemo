package zenghao.com.study.adapter.LVCommon;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author zenghao
 * @since 16/11/24 下午7:11
 */
public class MultiTypeHelp {

    private static Map<String, IViewHandler> mHandlerMap = new HashMap<String, IViewHandler>();


    public static IViewHandler getViewHandler(String viewHandlerClazz) {
        IViewHandler result = mHandlerMap.get(viewHandlerClazz);

        if (result == null) {
            try {
                Class clazz = Class.forName(viewHandlerClazz);
                result = (IViewHandler) clazz.newInstance();
                mHandlerMap.put(viewHandlerClazz, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(result == null)
            throw new RuntimeException("IViewHandler Create failed:" + viewHandlerClazz);

        return result;
    }
}
