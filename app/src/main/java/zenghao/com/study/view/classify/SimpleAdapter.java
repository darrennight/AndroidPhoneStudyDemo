package zenghao.com.study.view.classify;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import zenghao.com.study.R;

/**
 * <p/>
 * Date: 16/6/7 11:55
 * Author: zhendong.wu@shoufuyou.com
 * <p/>
 */
public abstract class SimpleAdapter<T, VH extends SimpleAdapter.ViewHolder> implements BaseSimpleAdapter {
    protected List<List<T>> mData;
    private SimpleMainAdapter mSimpleMainAdapter;
    private SimpleSubAdapter mSimpleSubAdapter;

    public SimpleAdapter(List<List<T>> data) {
        mData = data;
        mSimpleMainAdapter = new SimpleMainAdapter(this, mData);
        mSimpleSubAdapter = new SimpleSubAdapter(this);
    }

    @Override
    public BaseMainAdapter getMainAdapter() {
        return mSimpleMainAdapter;
    }

    @Override
    public BaseSubAdapter getSubAdapter() {
        return mSimpleSubAdapter;
    }

    protected VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item, parent, false);
        return (VH) new ViewHolder(view);
    }

    protected void onBindMainViewHolder(VH holder, int position) {
    }

    protected void onBindSubViewHolder(VH holder, int mainPosition,int subPosition) {
    }


    public void notifyItemInsert(int position){
        mSimpleMainAdapter.notifyItemInserted(position);
    }

    public void notifyItemChanged(int position){
        mSimpleMainAdapter.notifyItemChanged(position);
    }

    public void notifyItemRangeChanged(int position,int count){
        mSimpleMainAdapter.notifyItemRangeChanged(position,count);
    }

    public void notifyItemRangeInsert(int position,int count){
        mSimpleMainAdapter.notifyItemRangeInserted(position,count);
    }


    public void notifyDataSetChanged(){
        mSimpleMainAdapter.notifyDataSetChanged();
    }
    /**
     * @param parentIndex
     * @param index       if -1  in main region
     */
    protected void onItemClick(View view, int parentIndex, int index) {
    }

    /**
     * 显示一个item的布局
     *
     * @return
     */
    public abstract View getView(ViewGroup parent, int mainPosition, int subPosition);

    class SimpleMainAdapter extends BaseMainAdapter<VH, SimpleSubAdapter> {
        private List<List<T>> mData;
        private SimpleAdapter<T, VH> mSimpleAdapter;

        public SimpleMainAdapter(SimpleAdapter<T, VH> simpleAdapter, List<List<T>> data) {
            mData = data;
            mSimpleAdapter = simpleAdapter;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            VH vh = mSimpleAdapter.onCreateViewHolder(parent, viewType);
            CanMergeView canMergeView = vh.getCanMergeView();
            if (canMergeView != null) {
                canMergeView.setAdapter(mSimpleAdapter);
            }
            return vh;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            CanMergeView canMergeView = holder.getCanMergeView();
            if (canMergeView != null) {
                canMergeView.initMain(position, mData.get(position));
            }
            mSimpleAdapter.onBindMainViewHolder(holder, position);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public boolean canDragOnLongPress(int position, View pressedView) {
            return true;
        }



        @Override
        public boolean onMergeStart(VH selectedViewHolder, VH targetViewHolder,
                int selectedPosition, int targetPosition) {
            CanMergeView canMergeView = targetViewHolder.getCanMergeView();
            if (canMergeView != null) {
                canMergeView.onMergeStart();
            }
            return true;
        }

        @Override
        public void onMergeCancel(VH selectedViewHolder, VH targetViewHolder,
                int selectedPosition, int targetPosition) {
            CanMergeView canMergeView = targetViewHolder.getCanMergeView();
            if (canMergeView != null) {
                canMergeView.onMergeCancel();
            }
        }

        @Override
        public void onMerged(VH selectedViewHolder, VH targetViewHolder,
                int selectedPosition, int targetPosition) {
            CanMergeView canMergeView = targetViewHolder.getCanMergeView();
            if (canMergeView != null) {
                canMergeView.onMerged();
            }
            mData.get(targetPosition).add(mData.get(selectedPosition).get(0));
            mData.remove(selectedPosition);
            notifyItemRemoved(selectedPosition);
            if(selectedPosition < targetPosition) {
                notifyItemChanged(targetPosition-1);
            }else {
                notifyItemChanged(targetPosition);
            }
        }

        @Override
        public ChangeInfo onPrePareMerge(VH selectedViewHolder, VH targetViewHolder, int selectedPosition, int targetPosition) {
            if(targetViewHolder == null || selectedViewHolder == null) return null;
            CanMergeView canMergeView = targetViewHolder.getCanMergeView();
            if (canMergeView != null) {
                ChangeInfo info = canMergeView.prepareMerge();
                info.paddingLeft = selectedViewHolder.getPaddingLeft();
                info.paddingRight = selectedViewHolder.getPaddingRight();
                info.paddingTop = selectedViewHolder.getPaddingTop();
                info.paddingBottom = selectedViewHolder.getPaddingBottom();
                info.outlinePadding = canMergeView.getOutlinePadding();
                return info;
            }
            return null;
        }

        @Override
        public void onStartMergeAnimation(VH selectedViewHolder, VH targetViewHolder, int selectedPosition, int targetPosition,int duration) {
            CanMergeView canMergeView = targetViewHolder.getCanMergeView();
            if (canMergeView != null) {
                canMergeView.startMergeAnimation(duration);
            }
        }


        @Override
        public boolean onMove(int selectedPosition, int targetPosition) {
            notifyItemMoved(selectedPosition, targetPosition);
            List<T> list = mData.remove(selectedPosition);
            mData.add(targetPosition, list);
            return true;
        }

        @Override
        public boolean canMergeItem(int selectedPosition, int targetPosition) {
            List<T> currentSelected = mData.get(selectedPosition);
            return currentSelected.size() < 2;
        }


        @Override
        public int onLeaveSubRegion(int selectedPosition, SubAdapterReference<SimpleSubAdapter> subAdapterReference) {
            SimpleSubAdapter simpleSubAdapter = subAdapterReference.getAdapter();
            T t = simpleSubAdapter.getData().remove(selectedPosition);
            List<T> list = new ArrayList<>();
            list.add(t);
            mData.add(list);
            int parentIndex = simpleSubAdapter.getParentIndex();
            if (parentIndex != -1) notifyItemChanged(parentIndex);
            return mData.size() - 1;
        }

        @Override
        public void onItemClick(int position, View pressedView) {
            mSimpleAdapter.onItemClick(pressedView, position, -1);
        }

        @Override
        public List<T> explodeItem(int position, View pressedView) {
            if (position < mData.size())
                return mData.get(position);
            return null;
        }
    }

    class SimpleSubAdapter extends BaseSubAdapter<VH> {
        private List<T> mData;
        private int parentIndex = -1;
        private SimpleAdapter<T, VH> mSimpleAdapter;

        public SimpleSubAdapter(SimpleAdapter<T, VH> simpleAdapter) {
            mSimpleAdapter = simpleAdapter;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            VH vh = mSimpleAdapter.onCreateViewHolder(parent, viewType);
            CanMergeView canMergeView = vh.getCanMergeView();
            if (canMergeView != null) {
                canMergeView.setAdapter(mSimpleAdapter);
            }
            return vh;
        }

        public int getParentIndex() {
            return parentIndex;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            CanMergeView canMergeView = holder.getCanMergeView();
            if (canMergeView != null) {
                canMergeView.initSub(parentIndex,position);
            }
            mSimpleAdapter.onBindSubViewHolder(holder,parentIndex,position);
        }

        @Override
        public int getItemCount() {
            if (mData == null) return 0;
            return mData.size();
        }

        @Override
        public void onItemClick(int position, View pressedView) {
            mSimpleAdapter.onItemClick(pressedView, parentIndex, position);
        }

        @Override
        public void initData(int parentIndex, List data) {
            mData = data;
            this.parentIndex = parentIndex;
            notifyDataSetChanged();
        }

        @Override
        public boolean onMove(int selectedPosition, int targetPosition) {
            notifyItemMoved(selectedPosition, targetPosition);
            T t = mData.remove(selectedPosition);
            mData.add(targetPosition, t);
            if(parentIndex != -1) {
                mSimpleMainAdapter.notifyItemChanged(parentIndex);
            }
            return true;
        }

        public List<T> getData() {
            return mData;
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected CanMergeView mCanMergeView;
        private int paddingLeft;
        private int paddingRight;
        private int paddingTop;
        private int paddingBottom;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView instanceof CanMergeView) {
                mCanMergeView = (CanMergeView) itemView;
            } else if (itemView instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) itemView;
                paddingLeft = group.getPaddingLeft();
                paddingRight = group.getPaddingRight();
                paddingTop = group.getPaddingTop();
                paddingBottom = group.getPaddingBottom();
                //只遍历一层 寻找第一个符合条件的view
                for (int i = 0; i < group.getChildCount(); i++) {
                    View child = group.getChildAt(i);
                    if (child instanceof CanMergeView) {
                        mCanMergeView = (CanMergeView) child;
                        break;
                    }
                }
            }
        }

        public CanMergeView getCanMergeView() {
            return mCanMergeView;
        }

        public int getPaddingLeft() {
            return paddingLeft;
        }

        public int getPaddingRight() {
            return paddingRight;
        }

        public int getPaddingTop() {
            return paddingTop;
        }

        public int getPaddingBottom() {
            return paddingBottom;
        }
    }
}