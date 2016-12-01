package zenghao.com.study.RecyleMutilType;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zenghao
 * @since 16/11/2 下午5:24
 */
public class MutilTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements TypePool,FlatTypeAdapter{

    protected  List<? extends Item> items = null;
    protected LayoutInflater inflater;
    protected TypePool delegate;


    public MutilTypeAdapter(@NonNull List<? extends Item> items) {
        this.delegate = new MultiTypePool();
        this.items = items;
    }


    public MutilTypeAdapter(@NonNull List<? extends Item> items, TypePool pool) {
        this.delegate = pool;
        this.items = items;
    }

    @NonNull
    @Override
    public Class onFlattenClass(@NonNull Item item) {
        return item.getClass();
    }

    @NonNull
    @Override
    public Item onFlattenItem(@NonNull Item item) {
        return item;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return getProviderByIndex(viewType).onCreateViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        Item item = items.get(position);
        getProviderByIndex(type).onBindViewHolder(holder, onFlattenItem(item));
    }

    @SuppressWarnings("unchecked") @Override
    public int getItemViewType(int position) {
        Item item = items.get(position);
        return indexOf(onFlattenClass(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void register(@NonNull Class<? extends Item> clazz, @NonNull ItemViewProvider provider) {
        delegate.register(clazz, provider);
    }

    @Override
    public int indexOf(@NonNull Class<? extends Item> clazz) throws ProviderNotFoundException{
        int index = delegate.indexOf(clazz);
        if (index >= 0) {
            return index;
        }
        throw new ProviderNotFoundException(clazz);
    }

    @NonNull
    @Override
    public ArrayList<Class<? extends Item>> getContents() {
        return delegate.getContents();
    }

    @NonNull
    @Override
    public ArrayList<ItemViewProvider> getProviders() {
        return delegate.getProviders();
    }

    @NonNull
    @Override
    public ItemViewProvider getProviderByIndex(int index) {
        return delegate.getProviderByIndex(index);
    }

    @NonNull
    @Override
    public <T extends ItemViewProvider> T getProviderByClass(@NonNull Class<? extends Item> clazz) {
        return delegate.getProviderByClass(clazz);
    }

    public void applyGlobalMultiTypePool() {
        for (int i = 0; i < GlobalMultiTypePool.getContents().size(); i++) {
            final Class<? extends Item> clazz = GlobalMultiTypePool.getContents().get(i);
            final ItemViewProvider provider = GlobalMultiTypePool.getProviders().get(i);
            if (!this.getContents().contains(clazz)) {
                this.register(clazz, provider);
            }
        }
    }


    public void registerAll(@NonNull final MultiTypePool pool) {
        for (int i = 0; i < pool.getContents().size(); i++) {
            delegate.register(pool.getContents().get(i), pool.getProviders().get(i));
        }
    }

}
