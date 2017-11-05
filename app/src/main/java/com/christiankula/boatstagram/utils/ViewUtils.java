package com.christiankula.boatstagram.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public final class ViewUtils {
    public static long ALPHA_ANIMATION_DURATION_MS = 500;

    private ViewUtils() {

    }

    /**
     * Starts a fade in animation on the given {@code view} for {@link #ALPHA_ANIMATION_DURATION_MS} ms
     */
    public static void fadeIn(View view) {
        Animation alphaAnimation = new AlphaAnimation(0.0f, 1f);
        alphaAnimation.setDuration(ALPHA_ANIMATION_DURATION_MS);

        view.startAnimation(alphaAnimation);
    }
}
