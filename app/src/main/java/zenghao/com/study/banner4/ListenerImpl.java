package zenghao.com.study.banner4;

import java.util.ArrayList;
import java.util.List;

public class ListenerImpl<ListenerType> implements IListener<ListenerType> {
 
  private int listenerKey = Integer.MAX_VALUE;
 
  private List<ListenerType> mListenerTypes;
 
  @Override public void addListener(ListenerType _listenerType) {
 
    if (null == mListenerTypes ) {
      mListenerTypes = new ArrayList<>();
    }
    if (!mListenerTypes.contains(_listenerType)) {
      mListenerTypes.add(_listenerType);
    }
  } 
 
  @Override public void removeListener(ListenerType _listenerType) {
    mListenerTypes.remove(_listenerType);
  } 
 
  public List<ListenerType> from() {
    return mListenerTypes;
  } 
 
  public boolean hasListener() { 
    return null != mListenerTypes && mListenerTypes.size() > 0;
  } 
} 