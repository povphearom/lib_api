package knd.com.lib_api.listener;

public interface OnMediaController<T> {
    void loadVideo(T vmMediaObject);
    void setLoading(boolean loading);
}