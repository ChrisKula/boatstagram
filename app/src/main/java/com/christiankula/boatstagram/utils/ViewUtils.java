package com.christiankula.boatstagram.utils;

import android.view.View;

public final class ViewUtils {

    private ViewUtils() {

    }

    /**
     * Starts a fade in animation on the given {@code view} with default animation duration (300ms)
     */
    public static void fadeIn(View view) {
        view.setAlpha(0);

        view.animate().alpha(1.0f).start();
    }

    /**
     * Starts a fade out animation on the given {@code view} with default animation duration (300ms)
     */
    public static void fadeOut(View view) {
        view.animate().alpha(0.0f).start();
    }
}
