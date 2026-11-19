package com.gabink.tamanbermain;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

/** Live animated cartoon sprite View backed by CartoonArt with Mood and tap reaction support. */
public class SpriteView extends View {
    private String key;
    private boolean silhouette = false;
    private CartoonArt.Mood mood = CartoonArt.Mood.NORMAL;
    private final long t0 = System.currentTimeMillis();
    private float reactUntil = 0f;

    public SpriteView(Context c, String key) {
        super(c);
        this.key = key;
    }

    public void setKey(String key) {
        this.key = key;
        invalidate();
    }

    public String getKey() {
        return key;
    }

    public void setSilhouette(boolean sil) {
        this.silhouette = sil;
        invalidate();
    }

    public void setMood(CartoonArt.Mood mood) {
        this.mood = mood != null ? mood : CartoonArt.Mood.NORMAL;
        invalidate();
    }

    public void react() {
        reactUntil = (System.currentTimeMillis() - t0) / 1000f + 0.65f;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float sec = (System.currentTimeMillis() - t0) / 1000f;
        int w = getWidth(), h = getHeight();
        canvas.save();
        if (sec < reactUntil && w > 0 && h > 0) {
            float p = 1f - (reactUntil - sec) / 0.65f;
            float jump = (float) Math.sin(p * Math.PI) * (h * 0.08f);
            float sx = 1f + (float) Math.sin(p * Math.PI * 2f) * 0.07f;
            float sy = 1f - (float) Math.sin(p * Math.PI * 2f) * 0.07f;
            canvas.translate(0, -jump);
            canvas.scale(sx, sy, w / 2f, h * 0.88f);
        }
        CartoonArt.draw(canvas, key, w, h, sec, silhouette, mood);
        canvas.restore();
        postInvalidateOnAnimation();
    }
}
