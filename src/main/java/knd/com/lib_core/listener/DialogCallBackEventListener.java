package knd.com.lib_core.listener;

/**
 * Created by pisey on 17-Oct-16.
 */

public abstract class DialogCallBackEventListener {
    public void init(String zipCode, String countryCode, String countryName) {

    }
    public abstract void onItemClick(String zipCode, String countryCode, String countryName);
    public abstract void onCloseClick();

}
