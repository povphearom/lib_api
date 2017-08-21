package knd.com.lib_api.listener;

import android.view.View;

public interface ClickHandlerAction<T>
{
    void onClick(T viewModel, View v, int i);
}