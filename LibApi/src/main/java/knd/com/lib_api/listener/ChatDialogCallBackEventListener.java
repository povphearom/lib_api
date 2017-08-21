package knd.com.lib_api.listener;

import java.util.HashMap;

/**
 * Created by pisey on 17-Oct-16.
 */

public abstract class ChatDialogCallBackEventListener {
    public abstract void onItemClick(double lat, double lon);

    public abstract void onItemSelected(HashMap<String, Object> values);

}
