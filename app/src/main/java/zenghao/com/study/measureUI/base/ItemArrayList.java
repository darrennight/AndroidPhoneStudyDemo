package zenghao.com.study.measureUI.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class ItemArrayList<T extends Item> extends ArrayList<T> {

    @Override
    public boolean add(T t) {
        if (!t.isValid()) {
            return false;
        }
        return super.add(t);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Iterator<T> iterator = (Iterator<T>) c.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (!t.isValid()) {
                iterator.remove();
            }
        }
        return super.addAll(c);
    }
}