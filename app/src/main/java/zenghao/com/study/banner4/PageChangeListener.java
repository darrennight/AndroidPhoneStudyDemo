package zenghao.com.study.banner4;

import android.support.v4.view.ViewPager;
import java.util.List;

public class PageChangeListener extends ListenerImpl<ViewPager.SimpleOnPageChangeListener> {
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    notifyOnPageScrolled(position, positionOffset, positionOffsetPixels);
  } 
 
  public void onPageSelected(int position) {
    notifyOnPageSelected(position);
  } 
 
  public void onPageScrollStateChanged(int state) {
    notifyOnPageScrollStateChanged(state);
  } 
 
  private void notifyOnPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    List<ViewPager.SimpleOnPageChangeListener> lFrom = from();
    if (null != lFrom && !lFrom.isEmpty()) {
      for (ViewPager.SimpleOnPageChangeListener lSimpleOnPageChangeListener : lFrom) {
        if (null != lSimpleOnPageChangeListener) {
          lSimpleOnPageChangeListener.onPageScrolled(position, positionOffset,
              positionOffsetPixels);
        } 
      } 
    } 
  } 
 
  private void notifyOnPageSelected(int position) {
    List<ViewPager.SimpleOnPageChangeListener> lFrom = from();
    if (null != lFrom && !lFrom.isEmpty()) {
      for (ViewPager.SimpleOnPageChangeListener lSimpleOnPageChangeListener : lFrom) {
        if (null != lSimpleOnPageChangeListener) {
          lSimpleOnPageChangeListener.onPageSelected(position);
        } 
      } 
    } 
  } 
 
  private void notifyOnPageScrollStateChanged(int state) {
    List<ViewPager.SimpleOnPageChangeListener> lFrom = from();
    if (null != lFrom && !lFrom.isEmpty()) {
      for (ViewPager.SimpleOnPageChangeListener lSimpleOnPageChangeListener : lFrom) {
        if (null != lSimpleOnPageChangeListener) {
          lSimpleOnPageChangeListener.onPageScrollStateChanged(state);
        } 
      } 
    } 
  } 
} 