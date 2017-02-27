package zenghao.com.study.banner4;

import android.content.Context;
import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class BannerConfig {
 
  public BannerConfig(Context context) {
    mScroller = new FixedSpeedScroller(context);
  } 
 
  private static final int DEFAULT_INTERVAL = 1500;
  /** 
   * auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL} 
   */ 
  private long interval = DEFAULT_INTERVAL;
 
  /** 
   * set auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL} 
   * 
   * @param interval the interval to set 
   * @return this 
   */ 
  public BannerConfig interval(int interval) {
    this.interval = interval;
    return this;
  } 
 
  @IntDef({ LEFT, RIGHT }) @Retention(RetentionPolicy.SOURCE) public @interface Direction {
  } 
 
  public static final int LEFT = 0;
  public static final int RIGHT = 1;
  /** 
   * auto scroll direction, default is {@link #RIGHT} 
   */ 
  private int direction = RIGHT;
 
  /** 
   * set auto scroll direction 
   * 
   * @param direction {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT} 
   * @return this 
   */ 
  public BannerConfig direction(@Direction int direction) {
    this.direction = direction;
    return this;
  } 
 
  /** 
   * toggle the auto scroll direction 
   * 
   * @return this 
   */ 
  public BannerConfig toggleDirection() { 
    if (direction == LEFT) {
      direction(RIGHT);
    } else { 
      direction(LEFT);
    } 
    return this;
  } 
 
  /** 
   * whether stop auto scroll when touching, default is true 
   */ 
  private boolean stopScrollWhenTouch = true;
 
  /** 
   * whether stop auto scroll when touching, default is true 
   * 
   * @param stopScrollWhenTouch whether stop or not 
   * @return this 
   */ 
  public BannerConfig stopScrollWhenTouch(boolean stopScrollWhenTouch) {
    this.stopScrollWhenTouch = stopScrollWhenTouch;
    return this;
  } 
 
  /** 
   * scroll factor for auto scroll animation, default is 1.0 
   */ 
  private float autoScrollFactor = 1.0f;
 
  /** 
   * set the factor by which the duration of sliding animation will change while auto scrolling 
   */ 
  public BannerConfig autoScrollFactor(float autoScrollFactor) {
    this.autoScrollFactor = autoScrollFactor;
    return this;
  } 
 
  /** 
   * scroll factor for swipe scroll animation, default is 1.0 
   **/ 
  private float swipeScrollFactor = 1.0f;
 
  /** 
   * set the factor by which the duration of sliding animation will change while swiping 
   */ 
  public BannerConfig swipeScrollFactor(float swipeScrollFactor) {
    this.swipeScrollFactor = swipeScrollFactor;
    return this;
  } 
 
  /** 
   * fixed the sliding speed of viewpager 
   */ 
  private FixedSpeedScroller mScroller;
 
  public BannerConfig scroller(FixedSpeedScroller pScroller) {
    this.mScroller = pScroller;
    return this;
  } 
 
  public static BannerConfig sConfig(Context pContext) {
    return new BannerConfig(pContext);
  } 
 
  public float getAutoScrollFactor() { 
    return autoScrollFactor;
  } 
 
  public int getDirection() { 
    return direction;
  } 
 
  public long getInterval() { 
    return interval;
  } 
 
  public FixedSpeedScroller getScroller() { 
    return mScroller;
  } 
 
  public float getSwipeScrollFactor() { 
    return swipeScrollFactor;
  } 
 
  public boolean isStopScrollWhenTouch() { 
    return stopScrollWhenTouch;
  } 
} 