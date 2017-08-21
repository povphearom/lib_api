package knd.com.lib_core.base;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import knd.com.lib_core.BR;


/**
 * Created by Dell on 1/26/2017.
 */

public abstract class VmBase<T> extends BaseObservable {
    private boolean checked;
    private String displayText;
    private T model;

    public VmBase() {
    }

    public VmBase(T model) {
        this.model = model;
    }

    public void setModel(T model) {
        this.model = model;
        notifyPropertyChanged(BR.model);
    }

    @Bindable
    public T getModel() {
        return model;
    }

    public VmBase(boolean checked) {
        this.checked = checked;
    }

    protected abstract String getUniqueId();

    protected String getKeyFilter() {
        return null;
    }

    protected int getKeyFilterByType() {
        return 0;
    }

    @Bindable
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        notifyPropertyChanged(BR.checked);
    }

    @Override
    public boolean equals(Object obj) {
        try {
            if (obj instanceof VmBase) {
                VmBase bvs = (VmBase) obj;
                return (bvs.getUniqueId().equals(getUniqueId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Bindable
    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
        notifyPropertyChanged(BR.displayText);
    }
}
