package knd.com.lib_core.base;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.text.TextUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

/**
 * Created by Dell on 1/26/2017.
 */

public class VmBases<T extends VmBase> extends BaseObservable {
    public static int UNDEFINE = -1;
    public static int SINGLE_CHOICE = 0;
    public static int MULTI_CHOICE = 1;

    public enum Sort {
        ASC, DESC
    }

    @Bindable
    public ObservableList<T> items;

    public ObservableList<T> tempItems;

    private int mChoice;

    public int getChoice() {
        return mChoice;
    }

    public VmBases(int choice) {
        mChoice = choice;
        items = new ObservableArrayList<>();
        tempItems = new ObservableArrayList<>();
    }

    public void addItem(T item) {
        try {
            if (items.size() > 0) {
                int index = items.indexOf(item);
                if (index > -1)
                    this.items.set(index, item);
                else
                    this.items.add(item);
            } else
                items.add(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItem(int pos, T item) {
        try {
            if (items.size() > 0) {
                int index = items.indexOf(item);
                if (index > -1)
                    this.items.set(index, item);
                else
                    this.items.add(pos, item);
            } else
                items.add(pos, item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setItems(Collection<T> items) {
        for (T item : items) {
            addItem(item);
        }
    }

    public void remove(T item) {
        if (this.items.size() > 0)
            this.items.remove(item);
    }

    public void removeAll() {
        if (this.items.size() > 0)
            this.items.clear();
    }

    public void clearAllChecked() {
        for (T item : items) {
            item.setChecked(false);
        }
    }

    public void clearCheck(int index) {
        if (items.size() > 0)
            items.get(index).setChecked(false);
    }

    public void setCheck(int index) {
        if (items.size() > 0)
            items.get(index).setChecked(true);
    }

    public void doCheck(T itemSelected) {
        if (mChoice == SINGLE_CHOICE) {
            for (T item : items) {
                if (item.equals(itemSelected))
                    itemSelected.setChecked(!itemSelected.isChecked());
                else
                    item.setChecked(false);
            }
        } else if (mChoice == MULTI_CHOICE) {
            itemSelected.setChecked(!itemSelected.isChecked());
        } else {
            itemSelected.setChecked(false);
        }
    }

    public T getItemSelected() {
        if (mChoice == SINGLE_CHOICE)
            for (T item : items) {
                if (item.isChecked())
                    return item;
            }
        return null;
    }

    public List<T> getItems() {
        return items;
    }

    public List<T> getItemsSelected() {
        List<T> itemsSelected = new ArrayList<>();
        if (mChoice != MULTI_CHOICE) return itemsSelected;
        for (T item : items) {
            if (item.isChecked())
                itemsSelected.add(item);
        }
        return itemsSelected;
    }


    public void filter(final String s) {
        if (TextUtils.isEmpty(s)) {
            items.clear();
            items.addAll(tempItems);
            tempItems.clear();
            return;
        }
        if (tempItems.size() == 0) {
            tempItems.addAll(items);
        }
        Collection<T> filtered = Collections2.filter(tempItems, new Predicate<T>() {
            @Override
            public boolean apply(@Nullable T input) {
                if (TextUtils.isEmpty(input.getKeyFilter()))
                    return false;
                return input.getKeyFilter().toLowerCase(Locale.US).indexOf(s.toLowerCase(Locale.US)) > -1;
            }
        });
        items.clear();
        items.addAll(filtered);
    }

    public void filter(final int type) {
        if (type < 0) {
            items.clear();
            items.addAll(tempItems);
            tempItems.clear();
            return;
        }
        if (tempItems.size() == 0) {
            tempItems.addAll(items);
        }
        Collection<T> filtered = Collections2.filter(tempItems, new Predicate<T>() {
            @Override
            public boolean apply(@Nullable T input) {
                return input.getKeyFilterByType() == type;
            }
        });
        items.clear();
        items.addAll(filtered);
    }

    protected void filterBy(Predicate<T> predicate) {
        if (tempItems.size() == 0)
            tempItems.addAll(items);
        Collection<T> filtered = Collections2.filter(tempItems, predicate);

        items.clear();
        items.addAll(filtered);
    }

    protected void sortBy(Comparator<T> comparator) {
        Collections.sort(items, comparator);
    }

}
