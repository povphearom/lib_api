package knd.com.lib_core.listener;


import java.util.List;

public abstract class OnChannelCallback<T> {
        public abstract void onSuccess(List<T> items);

        public void onError(Exception e) {
            e.printStackTrace();
        }
    }