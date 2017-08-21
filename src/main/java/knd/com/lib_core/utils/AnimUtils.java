package knd.com.lib_core.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import com.afinos.api.animation.SimpleAnimationListener;

/**
 * Created by Dell on 11/23/2016.
 */

public class AnimUtils {
    public static void enterCenterReveal(View view) {
        int cx = view.getMeasuredWidth() / 2;
        int cy = view.getMeasuredHeight() / 2;
        int finalRadius = Math.max(view.getWidth(), view.getHeight()) / 2;
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        }
        view.setVisibility(View.VISIBLE);
        anim.start();
    }

    public static void enterBottomReveal(View view) {
        int cx = view.getMeasuredWidth() / 2;
        int cy = view.getMeasuredHeight();
        int finalRadius = Math.max(view.getWidth(), view.getHeight()) / 2;
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        }
        view.setVisibility(View.VISIBLE);
        anim.start();
    }

    public static void exitCenterReveal(final View view) {
        int cx = view.getMeasuredWidth() / 2;
        int cy = view.getMeasuredHeight() / 2;
        int initialRadius = view.getWidth() / 2;
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);
        } else {

        }
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });
        anim.start();
    }

    public static void exitBottomReveal(final View view) {
        int cx = view.getMeasuredWidth() / 2;
        int cy = view.getMeasuredHeight();
        int initialRadius = view.getWidth() / 2;
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);
        }
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });
        anim.start();
    }

    public static void fadeIn(final View view, float tranY, int duration) {
        TranslateAnimation tran = new TranslateAnimation(view.getTranslationX(), view.getTranslationX(), view.getTranslationY() - tranY, view.getTranslationY());
        tran.setDuration(duration);

        Animation fade = new AlphaAnimation(0, 1);
        fade.setDuration(duration);
        view.setVisibility(View.VISIBLE);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(fade);
        if (tranY > 0)
            animationSet.addAnimation(tran);
        view.setAnimation(animationSet);
    }

    public static void fadeOut(final View view, float tranY, int duration) {
        TranslateAnimation tran = new TranslateAnimation(view.getTranslationX(), view.getTranslationX(), view.getTranslationY(), view.getTranslationY() - tranY);
        tran.setDuration(duration);

        Animation fade = new AlphaAnimation(1, 0);
        fade.setDuration(duration);

        AnimationSet animationSet = new AnimationSet(true);
        if (tranY > 0)
            animationSet.addAnimation(tran);
        animationSet.addAnimation(fade);
        animationSet.setAnimationListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }
        });
        view.setAnimation(animationSet);
    }
}
