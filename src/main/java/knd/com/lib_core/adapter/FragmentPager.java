package knd.com.lib_core.adapter;

import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import knd.com.lib_core.base.BaseFragment;

/**
 * Created by phearom on 7/14/16.
 */
public class FragmentPager extends IconFragmentStatePagerAdapter {
    private List<BaseFragment> items;

    public FragmentPager(FragmentManager fm) {
        super(fm);
        items = new ArrayList<>();
    }

    public List<BaseFragment> getItems() {
        return items;
    }

    @Override
    public int getIcon(int pos) {
        return items.get(pos).getIcon();
    }

    public void addItem(BaseFragment item) {
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public BaseFragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }
}
