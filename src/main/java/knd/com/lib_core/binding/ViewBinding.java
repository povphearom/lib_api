package knd.com.lib_core.binding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.widget.NumberPicker;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pisey on 14-Jun-16.
 */
public class ViewBinding {
    @BindingAdapter("bindRes")
    public static void setImage(AppCompatImageView imageView, int icon) {
        imageView.setImageResource(icon);
    }

    @BindingAdapter("bindBitmap")
    public static void setImage(AppCompatImageView imageView, Bitmap icon) {
        imageView.setImageBitmap(icon);
    }

    @BindingAdapter("bindImage")
    public static void setImage(AppCompatImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).thumbnail(0.4f).into(imageView);
    }

    @BindingAdapter("bindGif")
    public static void setSticker(AppCompatImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).dontAnimate().skipMemoryCache(true).thumbnail(0.3f).into(imageView);
    }

    @BindingAdapter(value = {"bindImage", "bindDefault"}, requireAll = true)
    public static void setImage(AppCompatImageView imageView, String url, @DrawableRes int resId) {
        if (TextUtils.isEmpty(url))
            Glide.with(imageView.getContext()).load(resId).into(imageView);
        else
            Glide.with(imageView.getContext()).load(url).placeholder(resId).error(resId).into(imageView);
    }

    @BindingAdapter(value = {"bindImage", "bindDefault"}, requireAll = true)
    public static void setImage(AppCompatImageView imageView, Uri uri, @DrawableRes int resId) {
        if (uri == null)
            Glide.with(imageView.getContext()).load(resId).into(imageView);
        else
            Glide.with(imageView.getContext()).load(uri).placeholder(resId).error(resId).into(imageView);
    }

    @BindingAdapter(value = {"bindImage", "bindDefault"}, requireAll = true)
    public static void setImage(AppCompatImageView imageView, String url, Drawable resId) {
        if (TextUtils.isEmpty(url))
            imageView.setImageDrawable(resId);
        else
            Glide.with(imageView.getContext()).load(url).placeholder(resId).error(resId).into(imageView);
    }

    @BindingAdapter(value = {"bindMin", "bindMax", "bindStep"}, requireAll = false)
    public static void setMinMaxValue(NumberPicker numberPicker, int min, int max, int step) {
        List<String> strings = new ArrayList<>();
        if (step == 0)
            step = 1;
        for (int i = min; i <= max; i++) {
            if (i % step == 0)
                strings.add("" + i);
        }

        numberPicker.setMaxValue(strings.size() - 1);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setDisplayedValues(strings.toArray(new String[]{}));
    }

    @BindingAdapter(value = {"bindNMin", "bindNMax", "bindNStep"}, requireAll = false)
    public static void setNumberMinMaxValue(NumberPicker numberPicker, int min, int max, int step) {
        List<String> strings = new ArrayList<>();
        if (step == 0)
            step = 1;
        for (int i = min; i <= max; i++) {
            if (i % step == 0)
                strings.add("" + i);
        }

        numberPicker.setMaxValue(strings.size() - 1);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setDisplayedValues(strings.toArray(new String[]{}));
    }
}
