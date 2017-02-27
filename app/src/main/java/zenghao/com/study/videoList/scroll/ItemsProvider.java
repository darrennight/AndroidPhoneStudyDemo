package zenghao.com.study.videoList.scroll;

import zenghao.com.study.videoList.items.ListItem;

public interface ItemsProvider {

    ListItem getListItem(int position);

    int listItemSize();

}