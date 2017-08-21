package knd.com.lib_core.animation;

import android.view.View;

/**
 * Created by itphe on 3/9/2017.
 */

public class SSpacedAnimation {
    public static boolean toggleX(View v, boolean left) {
        if (v.getTranslationX() >= 0) {
            v.setTag(v.getTranslationX());
            v.animate().translationX(left ? -(getSize(v)) : (getSize(v) * 2)).setDuration(300);
            return true;
        } else {
            v.animate().translationX((Float) v.getTag()).setDuration(300);
            return false;
        }
    }

    public static boolean toggleY(View v, boolean top) {
        if (v.getTranslationY() >= 0) {
            v.setTag(v.getTranslationY());
            v.animate().translationY(top ? -(getSize(v)) : (getSize(v) * 2)).setDuration(300);
            return true;
        } else {
            v.animate().translationY((Float) v.getTag()).setDuration(300);
            return false;
        }
    }

    private static int getSize(View v) {
        return v.getMeasuredWidth();
    }
}
