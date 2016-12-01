package zenghao.com.study.RecyleMutilType;

public class ProviderNotFoundException extends RuntimeException {
 
    public ProviderNotFoundException(Class<? extends Item> clazz) {
        super("Do you have registered the provider for {className}.class in the adapter/pool?" 
            .replace("{className}", clazz.getSimpleName()));
    } 
} 