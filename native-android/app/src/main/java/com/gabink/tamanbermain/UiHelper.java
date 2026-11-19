package com.gabink.tamanbermain;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.Drawable;
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

    /** Chunky toy-block drawable with 3D ink shadow lip at the bottom and subtle top highlight */
    public static Drawable toyBox3D(Context c, int bgColor, int radiusDp, int strokeDp, int lipDp) {
        int r = dp(c, radiusDp);
        int st = dp(c, strokeDp);
        int lip = dp(c, lipDp);

        GradientDrawable shadow = new GradientDrawable();
        shadow.setColor(0xFF2A2140);
        shadow.setCornerRadius(r);

        int topCol = blendWhite(bgColor, 0.30f);
        GradientDrawable face = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{topCol, bgColor});
        face.setCornerRadius(r);
        face.setStroke(st, 0xFF2A2140);

        LayerDrawable ld = new LayerDrawable(new Drawable[]{shadow, face});
        ld.setLayerInset(0, 0, lip, 0, 0);
        ld.setLayerInset(1, 0, 0, 0, lip);
        return ld;
    }

    public static int blendWhite(int color, float ratio) {
        int a = (color >> 24) & 0xFF;
        int r = (int) (((color >> 16) & 0xFF) * (1f - ratio) + 255 * ratio);
        int g = (int) (((color >> 8) & 0xFF) * (1f - ratio) + 255 * ratio);
        int b = (int) ((color & 0xFF) * (1f - ratio) + 255 * ratio);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static void bounce(View v) {
        v.animate().cancel();
        v.setScaleX(0.86f);
        v.setScaleY(0.86f);
        v.animate().scaleX(1f).scaleY(1f).setDuration(300)
            .setInterpolator(new OvershootInterpolator(2.2f)).start();
    }

    public static void shake(View v) {
        ObjectAnimator.ofFloat(v, "translationX", 0f, -18f, 18f, -12f, 12f, 0f)
            .setDuration(360).start();
    }
}
