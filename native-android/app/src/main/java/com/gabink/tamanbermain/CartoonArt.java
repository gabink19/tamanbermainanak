package com.gabink.tamanbermain;

import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * Authentic storybook-style cartoon character & item renderer drawn directly on Android Canvas.
 * All artwork uses a 120x120 virtual coordinate space with bold rounded outlines (#2A2140),
 * soft drop shadows, double eye sparkles, rosy cheeks, and dynamic mood expressions.
 */
public class CartoonArt {
    public static final int INK = 0xFF2A2140;

    public enum Mood {
        NORMAL, HAPPY, SICK, HUNGRY
    }

    public static final int NOSE_NONE = 0;
    public static final int NOSE_PINK = 1;
    public static final int NOSE_DARK = 2;
    public static final int NOSE_TRI = 3;

    public static void draw(Canvas c, String key, float w, float h, float sec, boolean silhouette) {
        draw(c, key, w, h, sec, silhouette, Mood.NORMAL);
    }

    public static void draw(Canvas c, String key, float w, float h, float sec, boolean silhouette, Mood mood) {
        if (w <= 0 || h <= 0 || key == null) return;
        float s = Math.min(w, h) / 120f;
        c.save();
        c.translate((w - 120f * s) / 2f, (h - 120f * s) / 2f);
        c.scale(s, s);

        Paint f = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint st = new Paint(Paint.ANTI_ALIAS_FLAG);
        st.setStyle(Paint.Style.STROKE);
        st.setColor(INK);
        st.setStrokeWidth(4.0f);
        st.setStrokeCap(Paint.Cap.ROUND);
        st.setStrokeJoin(Paint.Join.ROUND);

        if (silhouette) {
            ColorMatrix cm = new ColorMatrix(new float[]{
                0, 0, 0, 0, 42,
                0, 0, 0, 0, 33,
                0, 0, 0, 0, 64,
                0, 0, 0, 0.85f, 0
            });
            f.setColorFilter(new ColorMatrixColorFilter(cm));
        }

        float bob = (float) Math.sin(sec * 3.0) * 2.5f;
        boolean blink = !silhouette && mood == Mood.NORMAL && ((sec % 4.0f) > 3.85f);

        if (CartoonAnimals.draw(c, f, st, key, sec, bob, blink, silhouette, mood)) {
            c.restore();
            return;
        }
        if (CartoonExtras.draw(c, f, st, key, sec, bob, blink, silhouette, mood)) {
            c.restore();
            return;
        }

        // Fallback cute star badge
        c.translate(0, bob);
        drawShadow(c, f, 60, 108, 28, 6);
        f.setColor(0xFFFFD23F);
        c.drawCircle(60, 60, 36, f);
        c.drawCircle(60, 60, 36, st);
        drawFace(c, f, st, 60, 56, 12, 4.5f, 68, mood, blink, silhouette, NOSE_NONE, false, true);
        c.restore();
    }

    /** Subtle squash-and-stretch breathing anchored at the character's feet. */
    public static void applyBreath(Canvas c, float sec, float speed, float amp) {
        float wave = (float) Math.sin(sec * speed);
        float sy = 1f + wave * amp;
        float sx = 1f - wave * (amp * 0.65f);
        c.scale(sx, sy, 60f, 108f);
    }

    public static void drawShadow(Canvas c, Paint f, float cx, float cy, float rx, float ry) {
        f.setColor(0x242A2140);
        c.drawOval(new RectF(cx - rx, cy - ry, cx + rx, cy + ry), f);
    }

    /**
     * Expressive storybook face with rosy cheeks, double eye sparkles, blinking,
     * and Mood support (NORMAL, HAPPY, SICK, HUNGRY).
     */
    public static void drawFace(
        Canvas c, Paint f, Paint st,
        float cx, float ey, float dx, float er, float my,
        Mood mood, boolean blink, boolean sil,
        int noseType, boolean catMouth, boolean cheeks
    ) {
        if (sil) return;

        // Rosy cheeks
        if (cheeks) {
            f.setColor(0xB8FF7EB6);
            c.drawCircle(cx - dx - 8f, ey + 9f, 5.2f, f);
            c.drawCircle(cx + dx + 8f, ey + 9f, 5.2f, f);
        }

        // Eyes
        float oldW = st.getStrokeWidth();
        if (mood == Mood.HAPPY) {
            st.setStrokeWidth(3.4f);
            c.drawArc(new RectF(cx - dx - 5.5f, ey - 4f, cx - dx + 5.5f, ey + 5f), 195, 150, false, st);
            c.drawArc(new RectF(cx + dx - 5.5f, ey - 4f, cx + dx + 5.5f, ey + 5f), 195, 150, false, st);
            st.setStrokeWidth(oldW);
        } else if (mood == Mood.SICK) {
            st.setStrokeWidth(3.2f);
            c.drawLine(cx - dx - 4, ey - 4, cx - dx + 4, ey + 4, st);
            c.drawLine(cx - dx + 4, ey - 4, cx - dx - 4, ey + 4, st);
            c.drawLine(cx + dx - 4, ey - 4, cx + dx + 4, ey + 4, st);
            c.drawLine(cx + dx + 4, ey - 4, cx + dx - 4, ey + 4, st);
            st.setStrokeWidth(oldW);
        } else if (blink) {
            st.setStrokeWidth(3.2f);
            c.drawLine(cx - dx - 5, ey, cx - dx + 5, ey, st);
            c.drawLine(cx + dx - 5, ey, cx + dx + 5, ey, st);
            st.setStrokeWidth(oldW);
        } else {
            float ry = er * 1.15f;
            f.setColor(INK);
            c.drawOval(new RectF(cx - dx - er, ey - ry, cx - dx + er, ey + ry), f);
            c.drawOval(new RectF(cx + dx - er, ey - ry, cx + dx + er, ey + ry), f);
            // Double eye sparkles
            f.setColor(0xFFFFFFFF);
            c.drawCircle(cx - dx - er * 0.28f, ey - ry * 0.30f, er * 0.38f, f);
            c.drawCircle(cx + dx - er * 0.28f, ey - ry * 0.30f, er * 0.38f, f);
            c.drawCircle(cx - dx + er * 0.32f, ey + ry * 0.26f, er * 0.18f, f);
            c.drawCircle(cx + dx + er * 0.32f, ey + ry * 0.26f, er * 0.18f, f);
        }

        // Nose
        if (noseType == NOSE_PINK) {
            f.setColor(0xFFFF7EB6);
            c.drawOval(new RectF(cx - 4.5f, my - 9f, cx + 4.5f, my - 2.5f), f);
            st.setStrokeWidth(2.2f);
            c.drawOval(new RectF(cx - 4.5f, my - 9f, cx + 4.5f, my - 2.5f), st);
            st.setStrokeWidth(oldW);
        } else if (noseType == NOSE_DARK) {
            f.setColor(INK);
            c.drawOval(new RectF(cx - 5f, my - 9.5f, cx + 5f, my - 2.5f), f);
        } else if (noseType == NOSE_TRI) {
            Path np = new Path();
            np.moveTo(cx - 4.5f, my - 8f);
            np.lineTo(cx + 4.5f, my - 8f);
            np.lineTo(cx, my - 2.5f);
            np.close();
            f.setColor(0xFFFF7EB6);
            c.drawPath(np, f);
            st.setStrokeWidth(2.0f);
            c.drawPath(np, st);
            st.setStrokeWidth(oldW);
        }

        // Mouth
        st.setStrokeWidth(2.8f);
        if (mood == Mood.SICK) {
            Path mp = new Path();
            mp.moveTo(cx - 8, my + 4);
            mp.quadTo(cx - 4, my, cx, my + 4);
            mp.quadTo(cx + 4, my + 8, cx + 8, my + 4);
            c.drawPath(mp, st);
        } else if (mood == Mood.HUNGRY) {
            f.setColor(0xFFFF5D8F);
            c.drawOval(new RectF(cx - 6, my - 2, cx + 6, my + 10), f);
            c.drawOval(new RectF(cx - 6, my - 2, cx + 6, my + 10), st);
            f.setColor(0xFFFF9FBF);
            c.drawOval(new RectF(cx - 3.5f, my + 4, cx + 3.5f, my + 8.5f), f);
        } else if (mood == Mood.HAPPY) {
            Path mp = new Path();
            mp.moveTo(cx - 9, my - 1);
            mp.quadTo(cx, my + 12, cx + 9, my - 1);
            mp.close();
            f.setColor(0xFFFF5D8F);
            c.drawPath(mp, f);
            c.drawPath(mp, st);
        } else if (catMouth) {
            c.drawLine(cx, my - 2.5f, cx, my + 1f, st);
            c.drawArc(new RectF(cx - 7, my - 2, cx, my + 4.5f), 0, 180, false, st);
            c.drawArc(new RectF(cx, my - 2, cx + 7, my + 4.5f), 0, 180, false, st);
        } else {
            c.drawArc(new RectF(cx - 7, my - 4, cx + 7, my + 5), 15, 150, false, st);
        }
        st.setStrokeWidth(oldW);
    }
}
