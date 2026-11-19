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

    /** Storybook-style Cici the Chick mascot with expressive eyes, triple crest, and wing wave */
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
            waveUntil = (System.currentTimeMillis() - t0) / 1000f + 1.3f;
        }

        @Override
        protected void onDraw(Canvas c) {
            int w = getWidth(), h = getHeight();
            if (w == 0 || h == 0) return;
            float s = Math.min(w, h) / 120f;
            float sec = (System.currentTimeMillis() - t0) / 1000f;
            boolean waving = sec < waveUntil;
            float bounceY = waving
                ? (float) Math.abs(Math.sin(sec * 10.0)) * -8f
                : (float) Math.abs(Math.sin(sec * 3.5)) * -4.5f;
            boolean blinking = !waving && (sec % 4f) > 3.85f;
            float wingAng = waving ? (float) Math.sin(sec * 24f) * 34f : (float) Math.sin(sec * 3.5) * 7f;

            c.save();
            c.scale(s, s);
            stroke.setStrokeWidth(4.2f);

            // Soft ground shadow
            fill.setColor(0x282A2140);
            c.drawOval(new RectF(32, 111, 88, 119), fill);

            c.translate(0, bounceY);
            CartoonArt.applyBreath(c, sec, 3.5f, 0.028f);

            // Triple feathered crest
            fill.setColor(0xFFFF9F1C);
            c.drawOval(new RectF(46, 12, 56, 28), fill); c.drawOval(new RectF(46, 12, 56, 28), stroke);
            c.drawOval(new RectF(54, 7, 66, 26), fill); c.drawOval(new RectF(54, 7, 66, 26), stroke);
            c.drawOval(new RectF(64, 12, 74, 28), fill); c.drawOval(new RectF(64, 12, 74, 28), stroke);

            // Left & Right Wings
            c.save();
            c.rotate(-wingAng, 28, 72);
            fill.setColor(0xFFFFB703);
            c.drawOval(new RectF(10, 60, 34, 90), fill);
            c.drawOval(new RectF(10, 60, 34, 90), stroke);
            c.restore();

            c.save();
            c.rotate(wingAng, 92, 72);
            fill.setColor(0xFFFFB703);
            c.drawOval(new RectF(86, 60, 110, 90), fill);
            c.drawOval(new RectF(86, 60, 110, 90), stroke);
            c.restore();

            // Feet
            fill.setColor(0xFFFF9F1C);
            c.drawRoundRect(new RectF(40, 102, 52, 113), 5, 5, fill);
            c.drawRoundRect(new RectF(40, 102, 52, 113), 5, 5, stroke);
            c.drawRoundRect(new RectF(68, 102, 80, 113), 5, 5, fill);
            c.drawRoundRect(new RectF(68, 102, 80, 113), 5, 5, stroke);

            // Body
            fill.setColor(0xFFFFD23F);
            c.drawOval(new RectF(22, 24, 98, 106), fill);
            c.drawOval(new RectF(22, 24, 98, 106), stroke);

            // Belly highlight
            fill.setColor(0xFFFFF3B0);
            c.drawOval(new RectF(37, 64, 83, 98), fill);

            // Rosy cheeks
            fill.setColor(0xB8FF7EB6);
            c.drawCircle(35, 62, 6.2f, fill);
            c.drawCircle(85, 62, 6.2f, fill);

            // Eyes (happy crescent when waving, double-sparkle otherwise)
            if (waving) {
                stroke.setStrokeWidth(3.6f);
                c.drawArc(new RectF(39, 46, 51, 57), 195, 150, false, stroke);
                c.drawArc(new RectF(69, 46, 81, 57), 195, 150, false, stroke);
                stroke.setStrokeWidth(4.2f);
            } else if (blinking) {
                c.drawLine(40, 52, 50, 52, stroke);
                c.drawLine(70, 52, 80, 52, stroke);
            } else {
                fill.setColor(0xFF2A2140);
                c.drawOval(new RectF(39.5f, 46.5f, 50.5f, 59.5f), fill);
                c.drawOval(new RectF(69.5f, 46.5f, 80.5f, 59.5f), fill);
                fill.setColor(0xFFFFFFFF);
                c.drawCircle(43.2f, 50.2f, 2.2f, fill);
                c.drawCircle(73.2f, 50.2f, 2.2f, fill);
                c.drawCircle(46.8f, 55.2f, 1.1f, fill);
                c.drawCircle(76.8f, 55.2f, 1.1f, fill);
            }

            // Beak
            beak.reset();
            beak.moveTo(52, 60);
            beak.lineTo(68, 60);
            beak.lineTo(60, waving ? 73 : 70);
            beak.close();
            fill.setColor(0xFFFF9F1C);
            c.drawPath(beak, fill);
            c.drawPath(beak, stroke);

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

    /** Glossy translucent soap bubble for GelembungGame */
    public static class BubbleView extends android.widget.FrameLayout {
        private final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint st = new Paint(Paint.ANTI_ALIAS_FLAG);

        public BubbleView(Context c, String animalKey) {
            super(c);
            setWillNotDraw(false);
            st.setStyle(Paint.Style.STROKE);
            st.setColor(0xEEFFFFFF);
            st.setStrokeWidth(UiHelper.dp(c, 3));
            int pad = UiHelper.dp(c, 14);
            setPadding(pad, pad, pad, pad);
            addView(new SpriteView(c, animalKey), new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        @Override
        protected void onDraw(Canvas c) {
            float w = getWidth(), h = getHeight(), r = Math.min(w, h) / 2f - UiHelper.dp(getContext(), 3);
            p.setColor(0x44BDF0FF);
            c.drawCircle(w / 2f, h / 2f, r, p);
            c.drawCircle(w / 2f, h / 2f, r, st);
            p.setColor(0xAAFFFFFF);
            c.drawCircle(w * 0.34f, h * 0.30f, r * 0.18f, p);
            super.onDraw(c);
        }
    }

    /** Rich animated themed backgrounds for all 32 games */
    public static class SceneBgView extends View {
        private final String theme; // "sky", "garden", "sea", "night", "room"
        private final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Path path = new Path();
        private final long t0 = System.currentTimeMillis();

        public SceneBgView(Context c, String theme) {
            super(c);
            this.theme = theme;
        }

        @Override
        protected void onDraw(Canvas c) {
            int w = getWidth(), h = getHeight();
            if (w == 0 || h == 0) return;
            float sec = (System.currentTimeMillis() - t0) / 1000f;

            if ("night".equals(theme)) {
                p.setShader(new LinearGradient(0, 0, 0, h,
                    new int[]{0xFF141036, 0xFF282066, 0xFF3E318C}, null, Shader.TileMode.CLAMP));
                c.drawRect(0, 0, w, h, p);
                p.setShader(null);
                // Twinkling stars
                for (int i = 0; i < 24; i++) {
                    float sx = ((i * 137) % 97 + 1.5f) * w / 100f;
                    float sy = ((i * 73) % 78 + 3f) * h / 100f;
                    float al = 0.35f + 0.65f * (float) Math.abs(Math.sin(sec * 2.2f + i));
                    p.setColor(((int) (al * 255) << 24) | 0x00FFF6B0);
                    c.drawCircle(sx, sy, 3.5f + (i % 3) * 1.5f, p);
                }
                // Crescent moon
                p.setColor(0xFFFFF3B0);
                c.drawCircle(w * 0.82f, h * 0.14f, Math.min(w, h) * 0.08f, p);
                p.setColor(0xFF1C174A);
                c.drawCircle(w * 0.85f, h * 0.12f, Math.min(w, h) * 0.07f, p);
            } else if ("sea".equals(theme)) {
                p.setShader(new LinearGradient(0, 0, 0, h,
                    new int[]{0xFF7FDDF6, 0xFF3FA0D8, 0xFF2374AB}, null, Shader.TileMode.CLAMP));
                c.drawRect(0, 0, w, h, p);
                p.setShader(null);
                // Rising ambient bubbles
                p.setColor(0x44FFFFFF);
                for (int i = 0; i < 10; i++) {
                    float bx = ((i * 97) % 90 + 5f) * w / 100f;
                    float by = h - (((sec * 35f + i * 90f) % (h + 60f)) - 30f);
                    c.drawCircle(bx, by, 8f + (i % 4) * 4f, p);
                }
                // Sandy seabed
                p.setColor(0xFFF3D79B);
                path.reset();
                path.moveTo(0, h * 0.88f);
                path.quadTo(w * 0.5f, h * 0.82f, w, h * 0.88f);
                path.lineTo(w, h);
                path.lineTo(0, h);
                path.close();
                c.drawPath(path, p);
            } else if ("room".equals(theme)) {
                p.setShader(new LinearGradient(0, 0, 0, h * 0.62f,
                    new int[]{0xFFFFF3D9, 0xFFFFE5B8}, null, Shader.TileMode.CLAMP));
                c.drawRect(0, 0, w, h * 0.62f, p);
                p.setShader(null);
                p.setColor(0xFFE2A96B);
                c.drawRect(0, h * 0.62f, w, h, p);
                p.setColor(0x222A2140);
                for (int i = 1; i < 6; i++) {
                    float lx = w * i / 6f;
                    c.drawRect(lx, h * 0.62f, lx + 4f, h, p);
                }
            } else if ("garden".equals(theme)) {
                p.setShader(new LinearGradient(0, 0, 0, h * 0.48f,
                    new int[]{0xFF8BE0FF, 0xFFD9F5FF}, null, Shader.TileMode.CLAMP));
                c.drawRect(0, 0, w, h * 0.48f, p);
                p.setShader(null);
                p.setColor(0xFFFFD23F);
                c.drawCircle(w * 0.86f, h * 0.11f, Math.min(w, h) * 0.08f, p);
                p.setColor(0xFF9AD77A);
                c.drawRect(0, h * 0.42f, w, h, p);
                p.setColor(0xFF82CA62);
                path.reset();
                path.moveTo(0, h * 0.45f);
                path.quadTo(w * 0.5f, h * 0.36f, w, h * 0.45f);
                path.lineTo(w, h);
                path.lineTo(0, h);
                path.close();
                c.drawPath(path, p);
            } else { // "sky"
                p.setShader(new LinearGradient(0, 0, 0, h,
                    new int[]{0xFF7FD0FF, 0xFFBFE8FF, 0xFFEAF8FF}, null, Shader.TileMode.CLAMP));
                c.drawRect(0, 0, w, h, p);
                p.setShader(null);
                p.setColor(0xF0FFFFFF);
                for (int i = 0; i < 3; i++) {
                    float cx = (((0.15f + i * 0.35f) + sec * (0.015f + i * 0.004f)) % 1.3f - 0.15f) * w;
                    float cy = (0.10f + i * 0.11f) * h;
                    float r = Math.min(w, h) * 0.06f;
                    c.drawRoundRect(new RectF(cx - r * 1.6f, cy - r * 0.5f, cx + r * 1.6f, cy + r * 0.6f), r, r, p);
                    c.drawCircle(cx - r * 0.4f, cy - r * 0.4f, r * 0.7f, p);
                    c.drawCircle(cx + r * 0.5f, cy - r * 0.25f, r * 0.55f, p);
                }
            }
            postInvalidateOnAnimation();
        }
    }
}
