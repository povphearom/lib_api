package knd.com.lib_api.utils;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;

/**
 * @Copy Right 2012-2016, Afinos, Inc., or its affiliates
 * @Author: Afinos Team
 * @Created by phearom on 10/26/16 2:59 PM
 **/
public class DrawableUtils {
    public static void setDrawableRadius(LayerDrawable drawable, int id, int radius) {
        GradientDrawable gradientDrawable = (GradientDrawable) drawable
                .findDrawableByLayerId(id);
        gradientDrawable.setCornerRadius(radius);
    }

    public static void setDrawableColor(LayerDrawable drawable, int id, int color) {
        GradientDrawable gradientDrawable = (GradientDrawable) drawable
                .findDrawableByLayerId(id);
        gradientDrawable.setColor(color);
    }
}
