package knd.com.lib_api.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Collection;

import knd.com.lib_api.binder.ItemBinder;
import knd.com.lib_api.listener.ClickHandler;

public class BindingVPagerAdapter<T> extends PagerAdapter implements View.OnClickListener {
    private static final int ITEM_MODEL = -124;
    private final ItemBinder<T> itemBinder;
    private ObservableList<T> items;
    private ClickHandler<T> clickHandler;
    private LayoutInflater inflater;
    private float mPageWidth = 1f;

    public BindingVPagerAdapter(ItemBinder<T> itemBinder, @Nullable Collection<T> items) {
        this.itemBinder = itemBinder;
        setItems(items);
    }

    public BindingVPagerAdapter(ItemBinder<T> itemBinder) {
        this.itemBinder = itemBinder;
        items = new ObservableArrayList<>();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        if (inflater == null) {
            inflater = LayoutInflater.from(collection.getContext());
        }

        final T item = items.get(position);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, itemBinder.getLayoutRes(item), null, false);
        binding.setVariable(itemBinder.getBindingVariable(item), item);
        binding.getRoot().setTag(ITEM_MODEL, item);
        binding.getRoot().setOnClickListener(this);
        binding.executePendingBindings();
        collection.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public float getPageWidth(int position) {
        return mPageWidth;
    }

    public void setPageWidth(float pageWidth) {
        this.mPageWidth = pageWidth;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public void onClick(View v) {
        if (clickHandler != null) {
            T item = (T) v.getTag(ITEM_MODEL);
            clickHandler.onClick(item, v);
        }
    }

    public void setClickHandler(ClickHandler<T> clickHandler) {
        this.clickHandler = clickHandler;
    }

    public ObservableList<T> getItems() {
        return items;
    }

    public void setItems(@Nullable Collection<T> items) {
        if (this.items == items) {
            return;
        }

        if (items instanceof ObservableList) {
            this.items = (ObservableList<T>) items;
        } else if (items != null) {
            this.items = new ObservableArrayList<>();
            this.items.addAll(items);
        } else {
            this.items = null;
        }
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return this.items.get(position);
    }

    public void addItem(T item) {
        this.items.add(item);
        notifyDataSetChanged();
    }
}