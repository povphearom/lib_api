package knd.com.lib_core.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Dell on 1/17/2017.
 */

public class BindingSimpleBaseAdapter<T> extends BaseAdapter {
    private ObservableList<T> items;
    private int mBindVariable;
    @LayoutRes
    private int mLayoutRes;

    public BindingSimpleBaseAdapter(int bindVariable, @LayoutRes int layoutRes) {
        this.mBindVariable = bindVariable;
        this.mLayoutRes = layoutRes;
        items = new ObservableArrayList<>();
    }

    public void addItem(T item) {
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T item = getItem(position);
        ViewDataBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutRes, parent, false);
            convertView = binding.getRoot();
        } else {
            binding = DataBindingUtil.bind(convertView);
        }

        binding.setVariable(mBindVariable, item);
        binding.executePendingBindings();

        return convertView;
    }
}
