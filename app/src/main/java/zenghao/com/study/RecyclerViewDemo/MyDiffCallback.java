package zenghao.com.study.RecyclerViewDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by zenghao on 16/8/30.
 */
public class MyDiffCallback extends DiffUtil.Callback {


    private List<Student> oldList;
    private List<Student> newList;

    public MyDiffCallback(List<Student> oldList, List<Student> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }
    /**
     * 旧的数据源的大小
     */
    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    /**
     * 新的数据源的大小
     */
    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    /**
     * 该方法用于判断两个 Object 是否是相同的 Item，比如有唯一标识的时候应该比较唯一标识是否相等
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getNum() == newList.get(newItemPosition).getNum();
    }


    /**
     * 当 areItemsTheSame 返回 true 时调用该方法，返回显示的 Item 的内容是否一致
     *
     * 规则可以重写student的equals方法　
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return newList.get(newItemPosition).equals(oldList.get(oldItemPosition));
    }


    /**
     * 除了最后一个getChangePayload()方法，其他都很好理解。最后一个方法的调用情况是：areItemsTheSame()返回true而areContentsTheSame()返回false，
     * 也就是说两个对象代表的数据是一条，但是内容更新了。在getChangePayload()方法中，你要给出具体的变化。这里我使用的Bundle，
     * 具体使用什么方式来表示数据的更新并不重要，重要的是在这个方法中你把更新情况存入一个对象后，在后面还能从同一个对象中把更新的情况取出来。
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {

        Bundle diffBundle = new Bundle();
        Student oldObj = oldList.get(oldItemPosition);
        Student newObj = newList.get(newItemPosition);

        //返回bundle bundle里面存放变化的数据
        return diffBundle;
    }


    /*
        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            Item oldItem = oldList.get(oldItemPosition);
            Item newItem = newList.get(newItemPosition);
            Bundle diffBundle = new Bundle();
            if (!newItem.title.equals(oldItem.title)) {
                diffBundle.putString(KEY_TITLE, newItem.title);
            }
            if (!newItem.content.equals(oldItem.content)) {
                diffBundle.putString(KEY_CONTENT, newItem.content);
            }
            if (!newItem.footer.equals(oldItem.footer)) {
                diffBundle.putString(KEY_FOOTER, newItem.footer);
            }
            if (diffBundle.size() == 0)
                return null;
            return diffBundle;
        }*/
}
