package com.gabink.tamanbermain;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import java.util.List;

/**
 * Smooth native touch drag-and-drop helper with tap fallback and generous hit zones.
 */
public class DragHelper {
    public interface Listener {
        boolean accept(int targetIdx);
        void onDrop(int targetIdx);
        void onTap();
        void onFail();
    }

    public static void attach(View item, List<? extends View> targets, SoundManager sfx, Listener listener) {
        final float slop = UiHelper.dp(item.getContext(), 10);
        final int pad = UiHelper.dp(item.getContext(), 28);
        final float[] start = new float[2];
        final boolean[] moved = {false};

        item.setOnTouchListener((v, ev) -> {
            if (v.getVisibility() != View.VISIBLE || Boolean.TRUE.equals(v.getTag())) return false;
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    start[0] = ev.getRawX();
                    start[1] = ev.getRawY();
                    moved[0] = false;
                    v.bringToFront();
                    v.animate().scaleX(1.16f).scaleY(1.16f).setDuration(100).start();
                    if (sfx != null) sfx.pick();
                    return true;

                case MotionEvent.ACTION_MOVE:
                    float dx = ev.getRawX() - start[0];
                    float dy = ev.getRawY() - start[1];
                    if (Math.hypot(dx, dy) > slop) moved[0] = true;
                    v.setTranslationX(dx);
                    v.setTranslationY(dy);
                    int hit = findTarget(ev.getRawX(), ev.getRawY(), targets, pad);
                    for (int i = 0; i < targets.size(); i++) {
                        targets.get(i).setScaleX(i == hit ? 1.07f : 1f);
                        targets.get(i).setScaleY(i == hit ? 1.07f : 1f);
                    }
                    return true;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    for (View t : targets) {
                        t.setScaleX(1f);
                        t.setScaleY(1f);
                    }
                    v.animate().scaleX(1f).scaleY(1f).setDuration(140).start();
                    if (!moved[0] || ev.getActionMasked() == MotionEvent.ACTION_CANCEL) {
                        v.animate().translationX(0f).translationY(0f).setDuration(220).start();
                        if (ev.getActionMasked() == MotionEvent.ACTION_UP) listener.onTap();
                        return true;
                    }
                    int dropIdx = findTarget(ev.getRawX(), ev.getRawY(), targets, pad);
                    if (dropIdx >= 0 && listener.accept(dropIdx)) {
                        v.setTag(Boolean.TRUE);
                        v.setTranslationX(0f);
                        v.setTranslationY(0f);
                        listener.onDrop(dropIdx);
                    } else {
                        v.animate().translationX(0f).translationY(0f).setDuration(260).start();
                        UiHelper.shake(v);
                        if (dropIdx >= 0) listener.onFail();
                    }
                    return true;
            }
            return false;
        });
    }

    private static int findTarget(float rx, float ry, List<? extends View> targets, int pad) {
        Rect r = new Rect();
        int[] loc = new int[2];
        for (int i = 0; i < targets.size(); i++) {
            View t = targets.get(i);
            if (t.getVisibility() != View.VISIBLE) continue;
            t.getLocationOnScreen(loc);
            r.set(loc[0] - pad, loc[1] - pad, loc[0] + t.getWidth() + pad, loc[1] + t.getHeight() + pad);
            if (r.contains((int) rx, (int) ry)) return i;
        }
        return -1;
    }
}
