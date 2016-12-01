package zenghao.com.study.RecyleMutilType;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * itemView
 * 不同item布局提供
 */
public abstract class ItemViewProvider<C extends Item, V extends RecyclerView.ViewHolder> {
 
    /* @formatter:off */ 
 
    @NonNull
    protected abstract V onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull
            ViewGroup parent);
 
    protected abstract void onBindViewHolder(@NonNull V holder, @NonNull C c);
} 