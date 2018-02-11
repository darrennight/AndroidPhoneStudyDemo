package zenghao.com.study.view.classify;
 
import android.support.v7.widget.RecyclerView; 
 
/** 
 * User:  Anarchy 
 * Email:  rsshinide38@163.com 
 * CreateTime: 六月/09/2016  15:54. 
 * Description: 
 * 用于告知如何将当前拖拽的view移动和缩放到目标位置 
 */
public class ChangeInfo {
    public int left;
    public int top;
    public int itemWidth;
    public int itemHeight;
    public int paddingLeft;
    public int paddingTop;
    public int paddingBottom;
    public int paddingRight;
    public int outlinePadding;
    @Override
    public String toString() {
        return "ChangeInfo{" +
                "left=" + left +
                ", top=" + top +
                ", itemWidth=" + itemWidth +
                ", itemHeight=" + itemHeight +
                '}';
    }
}