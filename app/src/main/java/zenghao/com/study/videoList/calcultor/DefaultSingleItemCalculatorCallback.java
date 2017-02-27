package zenghao.com.study.videoList.calcultor;

import android.view.View;
import zenghao.com.study.videoList.items.ListItem;

public class DefaultSingleItemCalculatorCallback implements SingleListViewItemActiveCalculator.Callback<ListItem>{
 
    @Override 
    public void activateNewCurrentItem(ListItem newListItem, View newView, int newViewPosition) {
        if (newListItem != null) {
            newListItem.setActive(newView, newViewPosition);
        } 
    } 
 
    @Override 
    public void deactivateCurrentItem(ListItem listItemToDeactivate, View view, int position) {
        if (listItemToDeactivate != null) {
            listItemToDeactivate.deactivate(view, position);
        } 
    } 
} 