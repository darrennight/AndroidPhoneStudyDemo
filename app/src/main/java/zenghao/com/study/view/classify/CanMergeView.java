package zenghao.com.study.view.classify;

import java.util.List;

public interface CanMergeView {
    /**
     * 进入merge状态
     */
    void onMergeStart();

    /**
     * 离开merge状态
     */
    void onMergeCancel();

    /**
     * 结束merge事件
     */
    void onMerged();

    /**
     * 开始merge动画
     * @param duration  动画持续时间
     */
    void startMergeAnimation(int duration);

    /**
     * 准备merge
     * @return 返回新添加的view 应该放置在布局中的位置坐标
     */
    ChangeInfo prepareMerge();
    /**
     * 设置适配器
     * @param simpleAdapter
     */
    void setAdapter(SimpleAdapter simpleAdapter);

    /**
     * 初始化 主层级
     * @param list
     */
    void initMain(int parentIndex, List list);

    /**
     * 初始化 次级层级
     * @param parentIndex
     * @param subIndex
     */
    void initSub(int parentIndex,int subIndex);


    int getOutlinePadding();

}