package knd.com.lib_core.feature.model;

/**
 * Created by itphe on 8/21/2017.
 */

public interface Response<T, R> {
    R getResult(T t);
}
