package com.gabink.tamanbermain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ParticleView extends View {
    public static final int[] BRIGHT = {
        0xFFE63946, 0xFFFF8C1A, 0xFFFFD23F, 0xFF3FB950, 0xFF2F80ED, 0xFF8E44EC, 0xFFFF7EB6
    };

    private static class Particle {
        float x, y, vx, vy, r, rot, vrot, g;
        int color, life, maxLife;
        boolean star;
    }

    private final List<Particle> list = new ArrayList<>();
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path starPath = new Path();
    private final Random rnd = new Random();

    public ParticleView(Context c) { super(c); }
    public ParticleView(Context c, AttributeSet a) { super(c, a); }

    public void burst(float x, float y, int[] colors, int n) {
        if (colors == null) colors = BRIGHT;
        for (int i = 0; i < n; i++) {
            Particle p = new Particle();
            double ang = rnd.nextDouble() * Math.PI * 2;
            float spd = 4f + rnd.nextFloat() * 14f;
            p.x = x; p.y = y;
            p.vx = (float) Math.cos(ang) * spd;
            p.vy = (float) Math.sin(ang) * spd - 4f;
            p.g = 0.35f;
            p.r = 8f + rnd.nextFloat() * 10f;
            p.rot = rnd.nextFloat() * 360f;
            p.vrot = (rnd.nextFloat() - 0.5f) * 14f;
            p.color = colors[rnd.nextInt(colors.length)];
            p.maxLife = 36 + rnd.nextInt(24);
            p.life = 0;
            p.star = rnd.nextBoolean();
            list.add(p);
        }
        invalidate();
    }

    public void confetti() {
        burst(getWidth() * 0.3f, getHeight() * 0.35f, BRIGHT, 45);
        burst(getWidth() * 0.7f, getHeight() * 0.35f, BRIGHT, 45);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (list.isEmpty()) return;
        Iterator<Particle> it = list.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.x += p.vx;
            p.y += p.vy;
            p.vy += p.g;
            p.vx *= 0.985f;
            p.rot += p.vrot;
            p.life++;
            if (p.life >= p.maxLife) {
                it.remove();
                continue;
            }
            int alpha = (int) (255 * (1f - (float) p.life / p.maxLife));
            paint.setColor((p.color & 0x00FFFFFF) | (alpha << 24));
            canvas.save();
            canvas.translate(p.x, p.y);
            canvas.rotate(p.rot);
            if (p.star) {
                drawStar(canvas, p.r);
            } else {
                canvas.drawRect(-p.r, -p.r * 0.55f, p.r, p.r * 0.55f, paint);
            }
            canvas.restore();
        }
        if (!list.isEmpty()) postInvalidateOnAnimation();
    }

    private void drawStar(Canvas c, float r) {
        starPath.reset();
        for (int i = 0; i < 10; i++) {
            double a = i * Math.PI / 5.0 - Math.PI / 2.0;
            float rad = (i % 2 == 0) ? r : r * 0.45f;
            float px = (float) (Math.cos(a) * rad);
            float py = (float) (Math.sin(a) * rad);
            if (i == 0) starPath.moveTo(px, py);
            else starPath.lineTo(px, py);
        }
        starPath.close();
        c.drawPath(starPath, paint);
    }
}
