package knd.com.lib_api.listener;


/**
 * @Copy Right 2012-2016, Afinos, Inc., or its affiliates
 * @Author: Afinos Team
 **/
public abstract class OnViewModelPopupMenuCallback<T>  {
    private T item;

    public void setItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public abstract void showPopup();
}
