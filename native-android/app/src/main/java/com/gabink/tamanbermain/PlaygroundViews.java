package com.gabink.tamanbermain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;

public class PlaygroundViews {

    /** Animated sunny playground background with tappable sun & drifting clouds */
    public static class PlaygroundBgView extends View {
        private final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Path hillPath = new Path();
        private long t0 = System.currentTimeMillis();
        private float sunSpin = 0f;
        private float sunX, sunY, sunR;
        private final float[] cloudX = {0.15f, 0.55f, 0.85f};
        private final float[] cloudY = {0.09f, 0.19f, 0.28f};
        private final float[] cloudScale = {1.0f, 0.72f, 0.58f};
        private ParticleView particles;
        private SoundManager sfx;

        public PlaygroundBgView(Context c, ParticleView particles, SoundManager sfx) {
            super(c);
            this.particles = particles;
            this.sfx = sfx;
        }

        @Override
        protected void onDraw(Canvas c) {
            int w = getWidth(), h = getHeight();
            if (w == 0 || h == 0) return;
            float sec = (System.currentTimeMillis() - t0) / 1000f;

            // Sky gradient
            p.setShader(new LinearGradient(0, 0, 0, h * 0.75f,
                new int[]{0xFF7FD0FF, 0xFFBFE8FF, 0xFFE6F7FF}, null, Shader.TileMode.CLAMP));
            c.drawRect(0, 0, w, h, p);
            p.setShader(null);

            // Pulsing Sun
            sunR = Math.min(w, h) * 0.11f;
            sunX = w * 0.84f;
            sunY = h * 0.11f;
            float pulse = (float) Math.sin(sec * 2.0) * sunR * 0.12f;
            p.setColor(0x33FFD23F);
            c.drawCircle(sunX, sunY, sunR * 1.45f + pulse, p);
            p.setColor(0x55FFD23F);
            c.drawCircle(sunX, sunY, sunR * 1.22f + pulse * 0.5f, p);
            p.setColor(0xFFFFD23F);
            c.drawCircle(sunX, sunY, sunR, p);

            if (sunSpin > 0f) {
                sunSpin = Math.max(0f, sunSpin - 0.04f);
            }

            // Drifting Clouds
            for (int i = 0; i < 3; i++) {
                float cx = ((cloudX[i] + sec * (0.012f + i * 0.004f)) % 1.3f - 0.15f) * w;
                float cy = cloudY[i] * h;
                float sc = cloudScale[i] * Math.min(w, h) * 0.0012f;
                drawCloud(c, cx, cy, sc);
            }

            // Layered green hills
            p.setColor(0xFF9AD77A);
            hillPath.reset();
            hillPath.moveTo(-w * 0.2f, h);
            hillPath.quadTo(w * 0.25f, h * 0.62f, w * 0.85f, h);
            hillPath.close();
            c.drawPath(hillPath, p);

            p.setColor(0xFF7CC95E);
            hillPath.reset();
            hillPath.moveTo(w * 0.2f, h);
            hillPath.quadTo(w * 0.72f, h * 0.64f, w * 1.2f, h);
            hillPath.close();
            c.drawPath(hillPath, p);

            postInvalidateOnAnimation();
        }

        private void drawCloud(Canvas c, float x, float y, float s) {
            p.setColor(0xF2FFFFFF);
            float bw = 110 * s, bh = 38 * s;
            c.drawRoundRect(new RectF(x - bw / 2, y - bh / 2, x + bw / 2, y + bh / 2), bh, bh, p);
            c.drawCircle(x - 18 * s, y - 16 * s, 26 * s, p);
            c.drawCircle(x + 18 * s, y - 10 * s, 20 * s, p);
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
                float dx = ev.getX() - sunX, dy = ev.getY() - sunY;
                if (Math.hypot(dx, dy) <= sunR * 1.5f) {
                    sunSpin = 1f;
                    if (sfx != null) sfx.ding();
                    if (particles != null) particles.burst(sunX, sunY, new int[]{0xFFFFD23F, 0xFFFFF6B0, 0xFFFF9F1C}, 22);
                    return true;
                }
            }
            return super.onTouchEvent(ev);
        }
    }

    /** Custom-drawn Cici the Chick mascot matching the original SVG art */
    public static class CiciView extends View {
        private final Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Path beak = new Path();
        private final long t0 = System.currentTimeMillis();
        private float waveUntil = 0f;

        public CiciView(Context c) {
            super(c);
            stroke.setStyle(Paint.Style.STROKE);
            stroke.setColor(0xFF2A2140);
            stroke.setStrokeJoin(Paint.Join.ROUND);
            stroke.setStrokeCap(Paint.Cap.ROUND);
        }

        public void wave() {
            waveUntil = (System.currentTimeMillis() - t0) / 1000f + 1.2f;
        }

        @Override
        protected void onDraw(Canvas c) {
            int w = getWidth(), h = getHeight();
            if (w == 0 || h == 0) return;
            float s = Math.min(w, h) / 120f;
            float sec = (System.currentTimeMillis() - t0) / 1000f;
            float bounceY = (float) Math.abs(Math.sin(sec * 3.5)) * -6f;
            boolean blinking = (sec % 4f) > 3.85f;
            float wingAng = (sec < waveUntil) ? (float) Math.sin(sec * 24f) * 32f : (float) Math.sin(sec * 3.5) * 6f;

            c.save();
            c.scale(s, s);
            c.translate(0, bounceY);
            stroke.setStrokeWidth(4.5f);

            // Shadow
            fill.setColor(0x2E2A2140);
            c.drawOval(new RectF(30, 112, 90, 121), fill);

            // Crest
            fill.setColor(0xFFFF9F1C);
            c.drawOval(new RectF(54, 8, 66, 28), fill);
            c.drawOval(new RectF(54, 8, 66, 28), stroke);

            // Left & Right Wings
            c.save();
            c.rotate(-wingAng, 28, 72);
            fill.setColor(0xFFFFB703);
            c.drawOval(new RectF(10, 62, 34, 92), fill);
            c.drawOval(new RectF(10, 62, 34, 92), stroke);
            c.restore();

            c.save();
            c.rotate(wingAng, 92, 72);
            fill.setColor(0xFFFFB703);
            c.drawOval(new RectF(86, 62, 110, 92), fill);
            c.drawOval(new RectF(86, 62, 110, 92), stroke);
            c.restore();

            // Body
            fill.setColor(0xFFFFD23F);
            c.drawOval(new RectF(22, 24, 98, 108), fill);
            c.drawOval(new RectF(22, 24, 98, 108), stroke);

            // Belly highlight
            fill.setColor(0xFFFFF3B0);
            c.drawOval(new RectF(38, 66, 82, 100), fill);

            // Cheeks
            fill.setColor(0xAAFF5D8F);
            c.drawCircle(36, 64, 6.5f, fill);
            c.drawCircle(84, 64, 6.5f, fill);

            // Eyes
            fill.setColor(0xFF2A2140);
            if (blinking) {
                c.drawLine(40, 54, 50, 54, stroke);
                c.drawLine(70, 54, 80, 54, stroke);
            } else {
                c.drawCircle(45, 53, 5.5f, fill);
                c.drawCircle(75, 53, 5.5f, fill);
                fill.setColor(0xFFFFFFFF);
                c.drawCircle(43.5f, 51.2f, 2f, fill);
                c.drawCircle(73.5f, 51.2f, 2f, fill);
            }

            // Beak
            beak.reset();
            beak.moveTo(52, 62);
            beak.lineTo(68, 62);
            beak.lineTo(60, 73);
            beak.close();
            fill.setColor(0xFFFF9F1C);
            c.drawPath(beak, fill);
            c.drawPath(beak, stroke);

            // Feet
            fill.setColor(0xFFFF9F1C);
            c.drawRoundRect(new RectF(40, 105, 52, 114), 4, 4, fill);
            c.drawRoundRect(new RectF(40, 105, 52, 114), 4, 4, stroke);
            c.drawRoundRect(new RectF(68, 105, 80, 114), 4, 4, fill);
            c.drawRoundRect(new RectF(68, 105, 80, 114), 4, 4, stroke);

            c.restore();
            postInvalidateOnAnimation();
        }
    }

    /** Glossy custom-drawn Balloon for BalonGame */
    public static class BalloonView extends View {
        private final int color;
        private final boolean rainbow;
        private final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint st = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Path str = new Path();

        public BalloonView(Context c, int color, boolean rainbow) {
            super(c);
            this.color = color;
            this.rainbow = rainbow;
            st.setStyle(Paint.Style.STROKE);
            st.setColor(0xFF2A2140);
            st.setStrokeWidth(UiHelper.dp(c, 3));
        }

        @Override
        protected void onDraw(Canvas c) {
            float w = getWidth(), h = getHeight();
            float cx = w / 2f, cy = h * 0.42f, rx = w * 0.38f, ry = h * 0.36f;

            // String
            str.reset();
            str.moveTo(cx, cy + ry);
            str.cubicTo(cx - rx * 0.25f, cy + ry + h * 0.08f, cx + rx * 0.25f, cy + ry + h * 0.14f, cx, h * 0.96f);
            c.drawPath(str, st);

            // Body
            if (rainbow) {
                p.setShader(new LinearGradient(cx - rx, cy - ry, cx + rx, cy + ry,
                    new int[]{0xFFE63946, 0xFFFFD23F, 0xFF3FB950, 0xFF2F80ED, 0xFF8E44EC}, null, Shader.TileMode.CLAMP));
            } else {
                p.setColor(color);
            }
            c.drawOval(new RectF(cx - rx, cy - ry, cx + rx, cy + ry), p);
            p.setShader(null);
            c.drawOval(new RectF(cx - rx, cy - ry, cx + rx, cy + ry), st);

            // Glossy highlight
            p.setColor(0x77FFFFFF);
            c.drawOval(new RectF(cx - rx * 0.55f, cy - ry * 0.65f, cx - rx * 0.15f, cy - ry * 0.15f), p);
        }
    }
}
