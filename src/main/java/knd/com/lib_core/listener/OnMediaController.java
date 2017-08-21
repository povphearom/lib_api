package knd.com.lib_core.listener;

public interface OnMediaController<T> {
    void loadVideo(T vmMediaObject);
    void setLoading(boolean loading);
}