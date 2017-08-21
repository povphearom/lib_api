package knd.com.lib_core.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

/**
 * Created by phearom on 7/14/16.
 */
public abstract class BaseFragment extends Fragment implements FragmentManager.OnBackStackChangedListener {
    public FragmentManager getSFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    public abstract int getIcon();

    public abstract CharSequence getTitle();

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    private boolean notFirstLoad = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (notFirstLoad) {
                onSpecialStart();
            } else {
                onFirstStart();
                notFirstLoad = true;
            }
        } else {
            onSpecialStop();
        }
    }

    public void loadData() {
        Log.i("loadData", "worked");
    }

    public void onSpecialStop() {
        Log.i("onSpecialStop", "worked");
    }

    public void onSpecialStart() {
        Log.i("onSpecialStart", "worked");
    }

    public void onFirstStart() {
        Log.i("onFirstStart", "worked");
    }

    public boolean onBackPressed() {
        return true;
    }
}
