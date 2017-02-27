package zenghao.com.study.viewpager.NewBaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import zenghao.com.study.R;

public class SimpleAdapter extends RecyclingPagerAdapter {
  private static final String[] DATA = "The quick brown fox jumps over the lazy dog".split(" ");
 
  private final LayoutInflater inflater;
 
  public SimpleAdapter(Context context) {
    inflater = LayoutInflater.from(context);
  } 
 
  @Override public View getView(int position, View view, ViewGroup container) {
    String recycled = "No";
    ViewHolder holder;
    if (view != null) {
      holder = (ViewHolder) view.getTag();
      recycled = "Yes";
    } else { 
      view = inflater.inflate(R.layout.page, container, false);
      holder = new ViewHolder(view);
      view.setTag(holder);
    } 
 
    holder.word.setText(DATA[position]);
    holder.position.setText(String.valueOf(position));
    holder.recycled.setText(recycled);
 
    return view;
  } 
 
  @Override
  public int getCount() {
    return DATA.length;
  }

  /*@Override
  public int getCount() {
    return DATA.length <=1 ? DATA.length : DATA.length +2;
  }*/

  /**
   * 根据外层position的获取内层的position
   *
   * @param position 外层ViewPager的position
   * @return 外层viewPager当前数据位置对应的内层viewPager对应的位置。
   */
  public int getInnerAdapterPosition(int position) {
    //viewPager真正的可用的个数
    int realCount = getInnerCount();
    //内层没有可用的Item则换回为零
    if (realCount == 0)
      return 0;
    int realPosition = (position - 1) % realCount;
    if (realPosition < 0)
      realPosition += realCount;
    return realPosition;
  }

  /**
   * @return 内层ViewPager中可用的item个数
   */
  public int getInnerCount() {
    return getRealCount();
  }

  public int getRealCount(){
    return DATA.length;
  }

  /**
   * 根据内层postion的位置，返回映射后外层position的位置
   *
   * @param position 内层position的位置
   * @return 无限轮播ViewPager的切换位置
   */
  public int toLooperPosition(int position) {
    if (getInnerCount() > 1) {
      return position + 1;
    } else return position;
  }

  private static class ViewHolder {
    final TextView word;
    final TextView position;
    final TextView recycled;
 
    public ViewHolder(View view) {
      word = (TextView) view.findViewById(R.id.word);
      position = (TextView) view.findViewById(R.id.position);
      recycled = (TextView) view.findViewById(R.id.recycled);
    } 
  } 
} 