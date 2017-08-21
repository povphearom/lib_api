package knd.com.lib_core.listener;

import android.view.View;

public interface LongClickHandler<T>
{
    void onLongClick(T viewModel, View v);
}