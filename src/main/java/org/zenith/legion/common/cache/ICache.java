package org.zenith.legion.common.cache;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ICache <K, V> {

    V get(K key);

    void set(K key, V value);

    default List<?> getList(String key){
        return null;
    }
}
