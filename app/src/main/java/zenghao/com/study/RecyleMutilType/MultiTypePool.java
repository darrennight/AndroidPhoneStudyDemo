package zenghao.com.study.RecyleMutilType;

import android.support.annotation.NonNull;
import java.util.ArrayList;

public final class MultiTypePool implements TypePool {
 
    private final String TAG = MultiTypePool.class.getSimpleName();
    private ArrayList<Class<? extends Item>> contents;
    private ArrayList<ItemViewProvider> providers;
 
 
    public MultiTypePool() { 
        this.contents = new ArrayList<>();
        this.providers = new ArrayList<>();
    } 
 
 
    public void register(@NonNull Class<? extends Item> clazz,@NonNull ItemViewProvider provider) {
        if (!contents.contains(clazz)) {
            contents.add(clazz);
            providers.add(provider);
        } else { 
            int index = contents.indexOf(clazz);
            providers.set(index, provider);

        } 
    } 
 
 
    @Override public int indexOf(@NonNull final Class<? extends Item> clazz) {
        int index = contents.indexOf(clazz);
        if (index >= 0) {
            return index;
        } 
        for (int i = 0; i < contents.size(); i++) {
            if (contents.get(i).isAssignableFrom(clazz)) {
                return i;
            } 
        } 
        return index;
    } 
 
 
    @NonNull @Override public ArrayList<Class<? extends Item>> getContents() {
        return contents;
    } 
 
 
    @NonNull @Override public ArrayList<ItemViewProvider> getProviders() {
        return providers;
    } 
 
 
    @NonNull @Override public ItemViewProvider getProviderByIndex(int index) {
        return providers.get(index);
    } 
 
 
    @SuppressWarnings("unchecked") @NonNull @Override 
    public <T extends ItemViewProvider> T getProviderByClass( 
        @NonNull final Class<? extends Item> clazz) {
        return (T) getProviderByIndex(indexOf(clazz));
    } 
} 