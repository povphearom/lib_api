package knd.com.lib_core.feature;

import knd.com.lib_core.callback.FeatureCallback;

/**
 * Created by itphe on 8/21/2017.
 */

public interface Feature<T> {
    void execute(FeatureCallback<T> featureCallback);
}
