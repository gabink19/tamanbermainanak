package com.gabink.tamanbermain;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

/** Live animated cartoon sprite View backed by CartoonArt. */
public class SpriteView extends View {
    private String key;
    private boolean silhouette = false;
    private final long t0 = System.currentTimeMillis();

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

    @Override
    protected void onDraw(Canvas canvas) {
        float sec = (System.currentTimeMillis() - t0) / 1000f;
        CartoonArt.draw(canvas, key, getWidth(), getHeight(), sec, silhouette);
        postInvalidateOnAnimation();
    }
}
