package knd.com.lib_api.listener;

import android.view.View;

public abstract class ClickHandlerListener<T>
{
    public void onClick(T viewModel, View v) {}

    public void onClickReject(T viewModel, View v){}
}