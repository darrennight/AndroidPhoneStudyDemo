package zenghao.com.study.banner4;

public interface IListener<ListenerType> {

  void addListener(ListenerType _listenerType);

  void removeListener(ListenerType _listenerType);
}