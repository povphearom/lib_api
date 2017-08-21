package knd.com.lib_core.feature.model;

/**
 * Created by itphe on 8/21/2017.
 */

public interface Request<T, P> {
    T getParams(P p);
}
