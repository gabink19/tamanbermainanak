package com.gabink.tamanbermain;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.animation.OvershootInterpolator;

public class UiHelper {
    public static int dp(Context c, int dp) {
        return Math.round(dp * c.getResources().getDisplayMetrics().density);
    }

    public static GradientDrawable toyBox(Context c, int bgColor, int radiusDp, int strokeDp) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(bgColor);
        d.setCornerRadius(dp(c, radiusDp));
        d.setStroke(dp(c, strokeDp), 0xFF2A2140);
        return d;
    }

    public static void bounce(View v) {
        v.animate().cancel();
        v.setScaleX(0.86f);
        v.setScaleY(0.86f);
        v.animate().scaleX(1f).scaleY(1f).setDuration(300)
            .setInterpolator(new OvershootInterpolator(2.2f)).start();
    }
}
