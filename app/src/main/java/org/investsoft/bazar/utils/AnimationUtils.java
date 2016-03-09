package org.investsoft.bazar.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import org.investsoft.bazar.utils.AndroidUtils;

/**
 * Created by Despairs on 06.03.16.
 */
public class AnimationUtils {

    private static final ArgbEvaluator argbEvaluator;

    static {
        argbEvaluator = new ArgbEvaluator();
    }

    public static void shakeView(final View view, final float x, final int num) {
        if (num == 6) {
            view.setTranslationX(0);
            view.clearAnimation();
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, "translationX", AndroidUtils.dp(x)));
        animatorSet.setDuration(50);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                shakeView(view, num == 5 ? 0 : -x, num + 1);
            }
        });
        animatorSet.start();
    }

    public static void changeTextColor(TextView view, int fromColor, int toColor) {
        ObjectAnimator animator = ObjectAnimator.ofObject(view, "textColor", argbEvaluator, fromColor, toColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            animator.setAutoCancel(true);
        }
        animator.start();
    }
}
