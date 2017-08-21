package knd.com.lib_api.utils;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.afinos.api.transform.CropCircleTransformation;
import com.afinos.studyspaced.R;
import com.afinos.studyspaced.databinding.ToastUserBinding;
import com.afinos.studyspaced.model.realm.UserRealm;
import com.bumptech.glide.Glide;

/**
 * @Copy Right 2012-2016, Afinos, Inc., or its affiliates
 * @Author: Afinos Team
 * @Created by phearom on 9/16/16 5:29 PM
 **/
public class ToastUtils {
    public static void info(Context context, UserRealm user, String text) {
        final Toast toast = new Toast(context);
        int w = context.getResources().getDimensionPixelSize(R.dimen.dialog_width);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, w, 0);
        toast.setDuration(Toast.LENGTH_LONG);

        ToastUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.toast_user, null, false);

        binding.toastTitle.setText(user.getDisplayName());
        binding.toastMessage.setText(text);

        String urlImage = TextUtils.isEmpty(user.getAvatarUrl()) ? user.getSocialAvatarUrl() : user.getAvatarUrl();

        Drawable drawableAvatar = new BitmapDrawable(context.getResources(), AppUtils.init(context).generateAvatar(user.getInitials()));

        Glide.with(context).load(urlImage).placeholder(drawableAvatar).bitmapTransform(new CropCircleTransformation(context)).into(binding.toastIcon);
        toast.setView(binding.getRoot());
        toast.show();
    }
}
