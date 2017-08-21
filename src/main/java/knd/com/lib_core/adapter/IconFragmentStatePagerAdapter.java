package knd.com.lib_core.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by itphe on 1/5/2017.
 */

public abstract class IconFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    public IconFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public abstract int getIcon(int pos);
}
