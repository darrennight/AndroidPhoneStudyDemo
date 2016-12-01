package zenghao.com.study.RecyleMutilType;

import android.support.annotation.NonNull;
import java.util.ArrayList;

public interface TypePool {

    void register(@NonNull Class<? extends Item> clazz, @NonNull ItemViewProvider provider);

    /**
     * For getting index of the item class.
     * If the subclass is already registered, the registered mapping is used.
     * If the subclass is not registered, then look for the parent class is
     * registered, if the parent class is registered,
     * the subclass is regarded as the parent class.
     *
     * @param clazz the item class.
     * @return the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     */
    int indexOf(@NonNull Class<? extends Item> clazz);

    @NonNull
    ArrayList<Class<? extends Item>> getContents();

    @NonNull
    ArrayList<ItemViewProvider> getProviders();

    @NonNull
    ItemViewProvider getProviderByIndex(int index);

    @NonNull
    <T extends ItemViewProvider> T getProviderByClass(@NonNull Class<? extends Item> clazz);
} 
