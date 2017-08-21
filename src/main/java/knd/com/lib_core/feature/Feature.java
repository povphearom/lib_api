package knd.com.lib_core.feature;

import knd.com.lib_core.feature.model.Request;

/**
 * Created by itphe on 8/21/2017.
 */

public interface Feature<T extends Request> {
    void execute(T t);
}
