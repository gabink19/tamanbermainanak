package com.gabink.tamanbermain;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.FrameLayout;
import java.util.Random;

public abstract class BaseGameView extends FrameLayout {
    public interface Host {
        SoundManager sfx();
        ParticleView particles();
        void celebrate(Runnable nextRound, String voiceKey);
    }

    protected final Host host;
    protected final Handler handler = new Handler(Looper.getMainLooper());
    protected final Random rnd = new Random();
    protected boolean alive = true;

    public BaseGameView(Context c, Host host) {
        super(c);
        this.host = host;
    }

    public abstract void start();
    public abstract void onReplay();

    public void dispose() {
        alive = false;
        handler.removeCallbacksAndMessages(null);
    }

    protected void burstView(android.view.View v, int[] colors, int n) {
        host.particles().burst(v.getX() + v.getWidth() / 2f, v.getY() + v.getHeight() / 2f, colors, n);
    }
}
