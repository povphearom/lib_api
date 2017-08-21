package knd.com.lib_core.listener;

import android.view.View;

public interface ClickHandler<T>
{
    void onClick(T viewModel, View v);
}