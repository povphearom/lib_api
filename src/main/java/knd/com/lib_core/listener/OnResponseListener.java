package knd.com.lib_core.listener;

/**
 * Created by phearom on 3/16/16.
 */
public interface OnResponseListener<T,D> {
    void onRecordSuccess(T object, D duration);
}
