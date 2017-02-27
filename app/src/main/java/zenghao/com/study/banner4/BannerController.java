/* 
 * Copyright (C) 2017.  BoBoMEe(wbwjx115@gmail.com) 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0 
 * 
 *  Unless required by applicable law or agreed to in writing, software 
 *  distributed under the License is distributed on an "AS IS" BASIS, 
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *  See the License for the specific language governing permissions and 
 *  limitations under the License. 
 */

package zenghao.com.study.banner4;
 
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
 
/** 
 * Created on 2017/1/17.上午11:04. 
 * 
 * @author bobomee. 
 */ 
 
public class BannerController { 
 
  /** 
   * the message.what for scroll 
   */ 
  private static final int SCROLL_WHAT = 0;
  private BannerConfig confing;
  private ViewPager mViewPager;
  private Handler mHandler;
  private boolean isAutoScroll = false;
  private boolean isStopByTouch = false;
  private int mTotalCount = 0;
  private PageChangeListener mPageChangeListener;
 
  public BannerController(Context pContext) {
    this.confing = BannerConfig.sConfig(pContext);
    mHandler = new MyHandler(Looper.myLooper(), this);
  } 
 
  public BannerController(BannerConfig pBannerConfig) {
    this.confing = pBannerConfig;
    mHandler = new MyHandler(Looper.myLooper(), this);
  } 
 
  public BannerController viewPager(ViewPager pViewPager) {
    this.mViewPager = pViewPager;
    setViewPagerScroller(); 
    return this;
  } 
 
  private void addSimpleOnPageChangeListener( 
      ViewPager.SimpleOnPageChangeListener pSimpleOnPageChangeListener) {
    if (null == mPageChangeListener) {
      mPageChangeListener = new PageChangeListener();
    } 
    mPageChangeListener.addListener(pSimpleOnPageChangeListener);
  } 
 
  public BannerController pageChangeListener( 
      ViewPager.SimpleOnPageChangeListener pOnPageChangeListener) {
    addSimpleOnPageChangeListener(pOnPageChangeListener);
    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override 
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
      } 
 
      @Override public void onPageSelected(int position) {
        mPageChangeListener.onPageSelected(position);
      } 
 
      @Override public void onPageScrollStateChanged(int state) {
        mPageChangeListener.onPageScrollStateChanged(state);
      } 
    }); 
    return this;
  } 
 
  /** 
   * set ViewPager scroller to change animation duration when sliding 
   */ 
  private void setViewPagerScroller() { 
    try { 
      Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
      scrollerField.setAccessible(true);
 
      FixedSpeedScroller lScroller = confing.getScroller();
      setAuto(); 
 
      scrollerField.set(mViewPager, lScroller);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  } 
 
  /** 
   * change the speed when swipe 
   */ 
  public void setSwipe() { 
    FixedSpeedScroller lFixedSpeedScroller = confing.getScroller();
    lFixedSpeedScroller.setScrollDurationFactor(confing.getSwipeScrollFactor());
  } 
 
  /** 
   * change the speed when auto scroll 
   */ 
  public void setAuto() { 
    FixedSpeedScroller lFixedSpeedScroller = confing.getScroller();
    lFixedSpeedScroller.setScrollDurationFactor(confing.getAutoScrollFactor());
  } 
 
  /** 
   * start auto scroll, first scroll delay time is {@link BannerConfig#getInterval()} 
   */ 
  public void startAutoScroll() { 
    isAutoScroll = true;
    sendScrollMessage(confing.getInterval());
  } 
 
  /** 
   * start auto scroll 
   * 
   * @param delayTimeInMills first scroll delay time 
   */ 
  public void startAutoScroll(long delayTimeInMills) {
    isAutoScroll = true;
    sendScrollMessage(delayTimeInMills);
  } 
 
  /** 
   * stop auto scroll 
   */ 
  private void stopAutoScroll() { 
    isAutoScroll = false;
    mHandler.removeMessages(SCROLL_WHAT);
  } 
 
  private void sendScrollMessage(long delayTimeInMills) {
    /** remove messages before, keeps one message is running at most **/ 
    mHandler.removeMessages(SCROLL_WHAT);
    mHandler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
  } 
 
  /** 
   * scroll only once 
   */ 
  private void scrollOnce() { 
    PagerAdapter adapter = mViewPager.getAdapter();
    if (adapter == null || (mTotalCount = adapter.getCount()) <= 1) return;
    int currentItem = mViewPager.getCurrentItem();
    int nextItem = (confing.getDirection() == BannerConfig.LEFT) ? --currentItem : ++currentItem;
    mViewPager.setCurrentItem(nextItem, true);
    sendScrollMessage(confing.getInterval());
  } 
 
  private static class MyHandler extends Handler {
 
    private final WeakReference<BannerController> mBannerScrollWeakReference;
 
    public MyHandler(Looper pLooper, BannerController pBannerScroll) {
      super(pLooper);
      this.mBannerScrollWeakReference = new WeakReference<BannerController>(pBannerScroll);
    } 
 
    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);
 
      switch (msg.what) {
        case SCROLL_WHAT:
          BannerController lBannerScroll = this.mBannerScrollWeakReference.get();
          if (lBannerScroll != null && lBannerScroll.isAutoScroll) {
            lBannerScroll.scrollOnce();
          } 
          break; 
        default: 
          break; 
      } 
    } 
  } 
 
  /** 
   * <ul> 
   * if stopScrollWhenTouch is true 
   * <li>if event is down, stop auto scroll.</li> 
   * <li>if event is up, start auto scroll again.</li> 
   * </ul> 
   */ 
  public void dispatchTouchEvent(MotionEvent ev) {
 
    PagerAdapter adapter = mViewPager.getAdapter();
    if (null != adapter && adapter.getCount() > 0) {
      int action = MotionEventCompat.getActionMasked(ev);
 
      if (action == MotionEvent.ACTION_DOWN) {
        if (isAutoScroll && confing.isStopScrollWhenTouch()) {
          isStopByTouch = true;
          setSwipe(); 
          stopAutoScroll(); 
        } 
      } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
        if (isStopByTouch && confing.isStopScrollWhenTouch()) {
          setAuto(); 
          startAutoScroll(); 
        } 
      } 
    } 
  } 
 
  public void onDetachedFromWindow() { 
    stopAutoScroll(); 
  } 
 
  public BannerConfig getConfing() { 
    return confing;
  } 
 
  public boolean isLast() { 
    if (mTotalCount == 0) return true;
    int lCurrentItem = mViewPager.getCurrentItem();
    return lCurrentItem == mTotalCount - 1;
  } 
 
  public boolean isFirst() { 
    if (mTotalCount == 0) return true;
    int lCurrentItem = mViewPager.getCurrentItem();
    return lCurrentItem == 0;
  } 
} 