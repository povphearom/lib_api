package knd.com.lib_core.binder;

public interface ItemBinder<T> {
    int getLayoutRes(T model);

    int getBindingVariable(T model);
}