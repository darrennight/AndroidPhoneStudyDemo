package zenghao.com.study.RecyleMutilType;

import android.support.annotation.NonNull;
import java.util.ArrayList;

public class GlobalMultiTypePool {
 
    private static MultiTypePool pool = new MultiTypePool();
 
 
    public static void register( 
        @NonNull Class<? extends Item> clazz, @NonNull ItemViewProvider provider) {
        pool.register(clazz, provider);
    } 
 
 
    public static int indexOf(@NonNull Class<? extends Item> clazz) {
        return pool.indexOf(clazz);
    } 
 
 
    @NonNull public static ArrayList<Class<? extends Item>> getContents() {
        return pool.getContents();
    } 
 
 
    @NonNull public static ArrayList<ItemViewProvider> getProviders() {
        return pool.getProviders();
    } 
 
 
    @NonNull public static ItemViewProvider getProviderByIndex(int index) {
        return pool.getProviderByIndex(index);
    } 
 
 
    @NonNull public static <T extends ItemViewProvider> T getProviderByClass( 
        @NonNull Class<? extends Item> clazz) {
        return pool.getProviderByClass(clazz);
    } 
 
 
    @NonNull public static MultiTypePool getPool() { 
        return pool;
    } 
} 