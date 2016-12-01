package zenghao.com.study.RecyleMutilType;

import android.support.annotation.NonNull;

public interface FlatTypeAdapter {
 
    @NonNull Class onFlattenClass(@NonNull Item item);
 
    @NonNull Item onFlattenItem(@NonNull final Item item);
} 