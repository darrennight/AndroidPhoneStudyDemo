package zenghao.com.study.measureUI.sample;

import java.util.ArrayList;
import java.util.List;
import zenghao.com.study.measureUI.base.Element;
import zenghao.com.study.measureUI.base.IAttrs;
import zenghao.com.study.measureUI.base.Item;
import zenghao.com.study.measureUI.base.TextItem;

public class CustomAttribution implements IAttrs {

    @Override
    public List<Item> getAttrs(Element element) {
        List<Item> items = new ArrayList<>();
        if (element.getView() instanceof CustomView) {
            CustomView view = (CustomView) element.getView();
            items.add(new TextItem("More", view.getMoreAttribution()));
        }
        return items;
    }
}