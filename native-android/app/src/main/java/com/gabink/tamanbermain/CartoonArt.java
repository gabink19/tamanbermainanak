package com.gabink.tamanbermain;

import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

/**
 * Hand-crafted cartoon vector illustrations drawn directly on Android Canvas.
 * Replaces system emojis with consistent, chunky-outlined, animated artwork.
 */
public class CartoonArt {
    private static final int INK = 0xFF2A2140;

    public static void draw(Canvas c, String key, float w, float h, float sec, boolean silhouette) {
        if (w <= 0 || h <= 0 || key == null) return;
        float s = Math.min(w, h) / 120f;
        c.save();
        c.translate((w - 120f * s) / 2f, (h - 120f * s) / 2f);
        c.scale(s, s);

        Paint f = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint st = new Paint(Paint.ANTI_ALIAS_FLAG);
        st.setStyle(Paint.Style.STROKE);
        st.setColor(INK);
        st.setStrokeWidth(4.2f);
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

        float bob = (float) Math.sin(sec * 3.2) * 3f;
        boolean blink = (sec % 4.0f) > 3.85f;

        switch (key) {
            case "kucing": drawCat(c, f, st, bob, blink, silhouette); break;
            case "anjing": drawDog(c, f, st, bob, blink, silhouette); break;
            case "sapi": drawCow(c, f, st, bob, blink, silhouette); break;
            case "kelinci": drawBunny(c, f, st, bob, blink, silhouette); break;
            case "katak": drawFrog(c, f, st, bob, blink, silhouette); break;
            case "singa": drawLion(c, f, st, bob, blink, silhouette); break;
            case "harimau": drawTiger(c, f, st, bob, blink, silhouette); break;
            case "gajah": drawElephant(c, f, st, bob, blink, silhouette); break;
            case "panda": drawPanda(c, f, st, bob, blink, silhouette); break;
            case "monyet": drawMonkey(c, f, st, bob, blink, silhouette); break;
            case "ayam": case "anakayam": drawChick(c, f, st, bob, blink, key.equals("ayam"), silhouette); break;
            case "bebek": drawDuck(c, f, st, bob, blink, silhouette); break;
            case "domba": case "kambing": drawSheep(c, f, st, bob, blink, silhouette); break;
            case "beruang": drawBear(c, f, st, bob, blink, silhouette); break;
            case "tikus": drawMouse(c, f, st, bob, blink, silhouette); break;
            case "ikan": case "ikanhias": case "hiu": case "buntal": drawFish(c, f, st, sec, key.equals("ikanhias") ? 0xFFFF8C1A : 0xFF4CC9F0, silhouette); break;
            case "gurita": drawOctopus(c, f, st, sec, silhouette); break;
            case "kepiting": drawCrab(c, f, st, sec, silhouette); break;
            case "kurakura": drawTurtle(c, f, st, bob, silhouette); break;
            case "lumbalumba": case "paus": drawWhale(c, f, st, bob, silhouette); break;
            case "apel": drawApple(c, f, st, bob); break;
            case "pisang": drawBanana(c, f, st, bob); break;
            case "jeruk": drawOrange(c, f, st, bob); break;
            case "semangka": drawWatermelon(c, f, st, bob); break;
            case "stroberi": drawStrawberry(c, f, st, bob); break;
            case "anggur": drawGrapes(c, f, st, bob); break;
            case "wortel": drawCarrot(c, f, st, bob); break;
            case "jagung": drawCorn(c, f, st, bob); break;
            case "tomat": drawTomato(c, f, st, bob); break;
            case "brokoli": drawBroccoli(c, f, st, bob); break;
            case "kentang": drawPotato(c, f, st, bob); break;
            case "mobil": drawCar(c, f, st, sec, 0xFFE63946, 0); break;
            case "mobilpolisi": drawCar(c, f, st, sec, 0xFF2B2D42, 1); break;
            case "ambulans": drawCar(c, f, st, sec, 0xFFFFFFFF, 2); break;
            case "pemadam": drawCar(c, f, st, sec, 0xFFE63946, 3); break;
            case "bus": drawBus(c, f, st, sec); break;
            case "kereta": drawTrain(c, f, st, sec); break;
            case "traktor": drawTractor(c, f, st, sec); break;
            case "truk": drawTruck(c, f, st, sec); break;
            case "pesawat": drawPlane(c, f, st, bob); break;
            case "helikopter": drawHeli(c, f, st, sec); break;
            case "roket": drawRocket(c, f, st, sec); break;
            case "kapal": case "perahu": case "speedboat": drawBoat(c, f, st, bob); break;
            case "tulang": drawBone(c, f, st, bob); break;
            case "keju": drawCheese(c, f, st, bob); break;
            case "madu": drawHoney(c, f, st, bob); break;
            case "susu": drawMilk(c, f, st, bob); break;
            case "bambu": drawBamboo(c, f, st, bob); break;
            case "rumput": drawGrass(c, f, st, bob); break;
            default: drawGenericBadge(c, f, st, bob, 0xFFFFD23F); break;
        }
        c.restore();
    }

    private static void drawEyes(Canvas c, Paint f, Paint st, float lx, float rx, float y, boolean blink, boolean sil) {
        if (sil) return;
        if (blink) {
            c.drawLine(lx - 5, y, lx + 5, y, st);
            c.drawLine(rx - 5, y, rx + 5, y, st);
        } else {
            f.setColor(INK);
            c.drawCircle(lx, y, 5.2f, f);
            c.drawCircle(rx, y, 5.2f, f);
            f.setColor(0xFFFFFFFF);
            c.drawCircle(lx - 1.5f, y - 1.8f, 1.9f, f);
            c.drawCircle(rx - 1.5f, y - 1.8f, 1.9f, f);
        }
    }

    private static void drawCat(Canvas c, Paint f, Paint st, float bob, boolean blink, boolean sil) {
        c.translate(0, bob);
        Path earL = new Path(); earL.moveTo(24, 52); earL.lineTo(22, 18); earL.lineTo(50, 34); earL.close();
        Path earR = new Path(); earR.moveTo(96, 52); earR.lineTo(98, 18); earR.lineTo(70, 34); earR.close();
        f.setColor(0xFFFF9F1C);
        c.drawPath(earL, f); c.drawPath(earL, st);
        c.drawPath(earR, f); c.drawPath(earR, st);
        c.drawOval(new RectF(20, 30, 100, 102), f); c.drawOval(new RectF(20, 30, 100, 102), st);
        if (!sil) {
            f.setColor(0xFFFFE5B4);
            c.drawOval(new RectF(38, 66, 82, 96), f);
            f.setColor(0xFFFF5D8F);
            Path nose = new Path(); nose.moveTo(55, 67); nose.lineTo(65, 67); nose.lineTo(60, 74); nose.close();
            c.drawPath(nose, f);
            c.drawLine(14, 65, 34, 68, st); c.drawLine(14, 75, 34, 74, st);
            c.drawLine(106, 65, 86, 68, st); c.drawLine(106, 75, 86, 74, st);
        }
        drawEyes(c, f, st, 44, 76, 58, blink, sil);
    }

    private static void drawDog(Canvas c, Paint f, Paint st, float bob, boolean blink, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFF9E6B43);
        c.drawRoundRect(new RectF(10, 38, 32, 84), 12, 12, f); c.drawRoundRect(new RectF(10, 38, 32, 84), 12, 12, st);
        c.drawRoundRect(new RectF(88, 38, 110, 84), 12, 12, f); c.drawRoundRect(new RectF(88, 38, 110, 84), 12, 12, st);
        f.setColor(0xFFD99B66);
        c.drawOval(new RectF(22, 26, 98, 102), f); c.drawOval(new RectF(22, 26, 98, 102), st);
        if (!sil) {
            f.setColor(0xFFFFF3E0);
            c.drawOval(new RectF(38, 62, 82, 96), f);
            f.setColor(INK);
            c.drawOval(new RectF(52, 65, 68, 76), f);
            f.setColor(0xFFFF5D8F);
            c.drawOval(new RectF(54, 80, 66, 92), f);
        }
        drawEyes(c, f, st, 45, 75, 53, blink, sil);
    }

    private static void drawCow(Canvas c, Paint f, Paint st, float bob, boolean blink, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFFFFD23F);
        c.drawOval(new RectF(26, 16, 40, 36), f); c.drawOval(new RectF(26, 16, 40, 36), st);
        c.drawOval(new RectF(80, 16, 94, 36), f); c.drawOval(new RectF(80, 16, 94, 36), st);
        f.setColor(0xFFFFFFFF);
        c.drawOval(new RectF(22, 26, 98, 100), f); c.drawOval(new RectF(22, 26, 98, 100), st);
        if (!sil) {
            f.setColor(INK);
            c.drawCircle(38, 42, 10, f);
            f.setColor(0xFFFFB3C6);
            c.drawOval(new RectF(32, 64, 88, 98), f); c.drawOval(new RectF(32, 64, 88, 98), st);
            f.setColor(INK);
            c.drawCircle(48, 78, 3.5f, f); c.drawCircle(72, 78, 3.5f, f);
        }
        drawEyes(c, f, st, 44, 76, 52, blink, sil);
    }

    private static void drawBunny(Canvas c, Paint f, Paint st, float bob, boolean blink, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFFFFFFFF);
        c.drawOval(new RectF(30, 8, 50, 56), f); c.drawOval(new RectF(30, 8, 50, 56), st);
        c.drawOval(new RectF(70, 8, 90, 56), f); c.drawOval(new RectF(70, 8, 90, 56), st);
        if (!sil) {
            f.setColor(0xFFFFB3C6);
            c.drawOval(new RectF(35, 16, 45, 48), f);
            c.drawOval(new RectF(75, 16, 85, 48), f);
        }
        f.setColor(0xFFFFFFFF);
        c.drawOval(new RectF(22, 38, 98, 106), f); c.drawOval(new RectF(22, 38, 98, 106), st);
        if (!sil) {
            f.setColor(0xFFFF5D8F);
            c.drawCircle(60, 74, 5, f);
        }
        drawEyes(c, f, st, 44, 76, 62, blink, sil);
    }

    private static void drawFrog(Canvas c, Paint f, Paint st, float bob, boolean blink, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFF5FBF5A);
        c.drawCircle(36, 36, 17, f); c.drawCircle(36, 36, 17, st);
        c.drawCircle(84, 36, 17, f); c.drawCircle(84, 36, 17, st);
        c.drawOval(new RectF(16, 36, 104, 100), f); c.drawOval(new RectF(16, 36, 104, 100), st);
        if (!sil) {
            f.setColor(0xFFFFFFFF);
            c.drawCircle(36, 36, 10, f); c.drawCircle(84, 36, 10, f);
            f.setColor(0xFFB5E48C);
            c.drawOval(new RectF(32, 66, 88, 94), f);
            c.drawArc(new RectF(36, 56, 84, 84), 15, 150, false, st);
        }
        drawEyes(c, f, st, 36, 84, 36, blink, sil);
    }

    private static void drawLion(Canvas c, Paint f, Paint st, float bob, boolean blink, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFFD96B27);
        for (int i = 0; i < 12; i++) {
            double a = i * Math.PI * 2 / 12;
            c.drawCircle((float) (60 + Math.cos(a) * 38), (float) (62 + Math.sin(a) * 38), 16, f);
        }
        c.drawCircle(60, 62, 42, st);
        f.setColor(0xFFFFD23F);
        c.drawCircle(60, 62, 32, f); c.drawCircle(60, 62, 32, st);
        if (!sil) {
            f.setColor(0xFFFFF3B0);
            c.drawOval(new RectF(42, 64, 78, 88), f);
            f.setColor(INK);
            c.drawCircle(60, 68, 5, f);
        }
        drawEyes(c, f, st, 48, 72, 56, blink, sil);
    }

    private static void drawTiger(Canvas c, Paint f, Paint st, float bob, boolean blink, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFFFF8C1A);
        c.drawCircle(28, 34, 13, f); c.drawCircle(28, 34, 13, st);
        c.drawCircle(92, 34, 13, f); c.drawCircle(92, 34, 13, st);
        c.drawOval(new RectF(20, 30, 100, 102), f); c.drawOval(new RectF(20, 30, 100, 102), st);
        if (!sil) {
            c.drawLine(52, 32, 60, 44, st); c.drawLine(68, 32, 60, 44, st);
            c.drawLine(24, 58, 36, 62, st); c.drawLine(96, 58, 84, 62, st);
            f.setColor(0xFFFFFFFF);
            c.drawOval(new RectF(40, 68, 80, 96), f);
            f.setColor(INK);
            c.drawCircle(60, 72, 5, f);
        }
        drawEyes(c, f, st, 45, 75, 58, blink, sil);
    }

    private static void drawElephant(Canvas c, Paint f, Paint st, float bob, boolean blink, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFF8ECAE6);
        c.drawOval(new RectF(8, 32, 40, 88), f); c.drawOval(new RectF(8, 32, 40, 88), st);
        c.drawOval(new RectF(80, 32, 112, 88), f); c.drawOval(new RectF(80, 32, 112, 88), st);
        c.drawOval(new RectF(26, 26, 94, 94), f); c.drawOval(new RectF(26, 26, 94, 94), st);
        f.setColor(0xFF70B5D6);
        c.drawRoundRect(new RectF(52, 62, 68, 108), 8, 8, f); c.drawRoundRect(new RectF(52, 62, 68, 108), 8, 8, st);
        drawEyes(c, f, st, 45, 75, 52, blink, sil);
    }

    private static void drawPanda(Canvas c, Paint f, Paint st, float bob, boolean blink, boolean sil) {
        c.translate(0, bob);
        f.setColor(INK);
        c.drawCircle(28, 32, 14, f); c.drawCircle(92, 32, 14, f);
        f.setColor(0xFFFFFFFF);
        c.drawOval(new RectF(20, 30, 100, 102), f); c.drawOval(new RectF(20, 30, 100, 102), st);
        if (!sil) {
            f.setColor(INK);
            c.drawOval(new RectF(32, 48, 52, 70), f);
            c.drawOval(new RectF(68, 48, 88, 70), f);
            f.setColor(0xFFFFFFFF);
            c.drawCircle(42, 57, 4, f); c.drawCircle(78, 57, 4, f);
            f.setColor(INK);
            c.drawCircle(60, 74, 5.5f, f);
        }
    }

    private static void drawMonkey(Canvas c, Paint f, Paint st, float bob, boolean blink, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFF8B5A2B);
        c.drawCircle(18, 60, 14, f); c.drawCircle(18, 60, 14, st);
        c.drawCircle(102, 60, 14, f); c.drawCircle(102, 60, 14, st);
        c.drawOval(new RectF(24, 28, 96, 98), f); c.drawOval(new RectF(24, 28, 96, 98), st);
        if (!sil) {
            f.setColor(0xFFFFE0B2);
            c.drawOval(new RectF(34, 46, 86, 94), f);
            c.drawArc(new RectF(44, 68, 76, 86), 15, 150, false, st);
        }
        drawEyes(c, f, st, 47, 73, 56, blink, sil);
    }

    private static void drawChick(Canvas c, Paint f, Paint st, float bob, boolean blink, boolean redComb, boolean sil) {
        c.translate(0, bob);
        f.setColor(redComb ? 0xFFE63946 : 0xFFFF9F1C);
        c.drawOval(new RectF(52, 10, 68, 30), f); c.drawOval(new RectF(52, 10, 68, 30), st);
        f.setColor(redComb ? 0xFFFFFFFF : 0xFFFFD23F);
        c.drawOval(new RectF(22, 26, 98, 104), f); c.drawOval(new RectF(22, 26, 98, 104), st);
        if (!sil) {
            f.setColor(0xFFFF9F1C);
            Path beak = new Path(); beak.moveTo(52, 64); beak.lineTo(68, 64); beak.lineTo(60, 75); beak.close();
            c.drawPath(beak, f); c.drawPath(beak, st);
        }
        drawEyes(c, f, st, 45, 75, 54, blink, sil);
    }

    private static void drawDuck(Canvas c, Paint f, Paint st, float bob, boolean blink, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFFFFD23F);
        c.drawOval(new RectF(24, 24, 96, 100), f); c.drawOval(new RectF(24, 24, 96, 100), st);
        if (!sil) {
            f.setColor(0xFFFF8C1A);
            c.drawOval(new RectF(42, 64, 78, 82), f); c.drawOval(new RectF(42, 64, 78, 82), st);
        }
        drawEyes(c, f, st, 45, 75, 52, blink, sil);
    }

    private static void drawSheep(Canvas c, Paint f, Paint st, float bob, boolean blink, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFFFFFFFF);
        for (int i = 0; i < 8; i++) {
            double a = i * Math.PI * 2 / 8;
            c.drawCircle((float) (60 + Math.cos(a) * 32), (float) (60 + Math.sin(a) * 32), 18, f);
        }
        c.drawCircle(60, 60, 44, st);
        f.setColor(0xFFFFE0B2);
        c.drawOval(new RectF(34, 42, 86, 94), f); c.drawOval(new RectF(34, 42, 86, 94), st);
        drawEyes(c, f, st, 48, 72, 60, blink, sil);
    }

    private static void drawBear(Canvas c, Paint f, Paint st, float bob, boolean blink, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFF8B5A2B);
        c.drawCircle(28, 32, 14, f); c.drawCircle(28, 32, 14, st);
        c.drawCircle(92, 32, 14, f); c.drawCircle(92, 32, 14, st);
        c.drawOval(new RectF(20, 30, 100, 102), f); c.drawOval(new RectF(20, 30, 100, 102), st);
        if (!sil) {
            f.setColor(0xFFD9A066);
            c.drawOval(new RectF(40, 64, 80, 94), f);
            f.setColor(INK);
            c.drawCircle(60, 70, 5, f);
        }
        drawEyes(c, f, st, 44, 76, 56, blink, sil);
    }

    private static void drawMouse(Canvas c, Paint f, Paint st, float bob, boolean blink, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFFB8C0CC);
        c.drawCircle(26, 36, 18, f); c.drawCircle(26, 36, 18, st);
        c.drawCircle(94, 36, 18, f); c.drawCircle(94, 36, 18, st);
        c.drawOval(new RectF(26, 36, 94, 102), f); c.drawOval(new RectF(26, 36, 94, 102), st);
        if (!sil) {
            f.setColor(0xFFFF8FAB);
            c.drawCircle(60, 80, 5, f);
        }
        drawEyes(c, f, st, 46, 74, 62, blink, sil);
    }

    private static void drawFish(Canvas c, Paint f, Paint st, float sec, int col, boolean sil) {
        c.translate(0, (float) Math.sin(sec * 3.5) * 4f);
        Path tail = new Path(); tail.moveTo(84, 60); tail.lineTo(110, 38); tail.lineTo(110, 82); tail.close();
        f.setColor(col);
        c.drawPath(tail, f); c.drawPath(tail, st);
        c.drawOval(new RectF(16, 32, 92, 88), f); c.drawOval(new RectF(16, 32, 92, 88), st);
        if (!sil) {
            f.setColor(0xFFFFFFFF);
            c.drawCircle(36, 54, 7, f);
            f.setColor(INK);
            c.drawCircle(34, 54, 3.5f, f);
        }
    }

    private static void drawOctopus(Canvas c, Paint f, Paint st, float sec, boolean sil) {
        c.translate(0, (float) Math.sin(sec * 3.2) * 4f);
        f.setColor(0xFFFF5D8F);
        for (int i = 0; i < 4; i++) {
            float tx = 28 + i * 21;
            c.drawRoundRect(new RectF(tx - 6, 70, tx + 6, 106), 6, 6, f);
            c.drawRoundRect(new RectF(tx - 6, 70, tx + 6, 106), 6, 6, st);
        }
        c.drawOval(new RectF(22, 18, 98, 84), f); c.drawOval(new RectF(22, 18, 98, 84), st);
        drawEyes(c, f, st, 46, 74, 52, false, sil);
    }

    private static void drawCrab(Canvas c, Paint f, Paint st, float sec, boolean sil) {
        c.translate((float) Math.sin(sec * 4f) * 4f, 0);
        f.setColor(0xFFE63946);
        c.drawCircle(24, 34, 14, f); c.drawCircle(24, 34, 14, st);
        c.drawCircle(96, 34, 14, f); c.drawCircle(96, 34, 14, st);
        c.drawOval(new RectF(20, 44, 100, 98), f); c.drawOval(new RectF(20, 44, 100, 98), st);
        drawEyes(c, f, st, 46, 74, 62, false, sil);
    }

    private static void drawTurtle(Canvas c, Paint f, Paint st, float bob, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFF9AD77A);
        c.drawCircle(96, 68, 14, f); c.drawCircle(96, 68, 14, st);
        f.setColor(0xFF3FB950);
        c.drawArc(new RectF(16, 32, 92, 108), 180, 180, true, f);
        c.drawArc(new RectF(16, 32, 92, 108), 180, 180, true, st);
    }

    private static void drawWhale(Canvas c, Paint f, Paint st, float bob, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFF4CC9F0);
        c.drawOval(new RectF(14, 38, 102, 96), f); c.drawOval(new RectF(14, 38, 102, 96), st);
        if (!sil) {
            c.drawLine(56, 38, 46, 20, st); c.drawLine(64, 38, 74, 20, st);
        }
        drawEyes(c, f, st, 36, 36, 64, false, sil);
    }

    private static void drawApple(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        c.drawLine(60, 32, 60, 16, st);
        f.setColor(0xFF3FB950);
        c.drawOval(new RectF(60, 14, 86, 30), f); c.drawOval(new RectF(60, 14, 86, 30), st);
        f.setColor(0xFFE63946);
        c.drawOval(new RectF(22, 30, 98, 104), f); c.drawOval(new RectF(22, 30, 98, 104), st);
        f.setColor(0x66FFFFFF);
        c.drawOval(new RectF(34, 42, 48, 64), f);
    }

    private static void drawBanana(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        Path p = new Path();
        p.moveTo(22, 86); p.quadTo(62, 102, 96, 30); p.quadTo(80, 22, 74, 36); p.quadTo(56, 72, 22, 86); p.close();
        f.setColor(0xFFFFD23F);
        c.drawPath(p, f); c.drawPath(p, st);
    }

    private static void drawOrange(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFF3FB950);
        c.drawOval(new RectF(58, 16, 84, 32), f); c.drawOval(new RectF(58, 16, 84, 32), st);
        f.setColor(0xFFFF8C1A);
        c.drawCircle(60, 66, 36, f); c.drawCircle(60, 66, 36, st);
        f.setColor(0x55FFFFFF);
        c.drawCircle(45, 50, 8, f);
    }

    private static void drawWatermelon(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFF3FB950);
        c.drawArc(new RectF(14, 24, 106, 116), 0, 180, true, f);
        c.drawArc(new RectF(14, 24, 106, 116), 0, 180, true, st);
        f.setColor(0xFFE63946);
        c.drawArc(new RectF(22, 32, 98, 108), 0, 180, true, f);
        f.setColor(INK);
        c.drawCircle(46, 76, 3, f); c.drawCircle(60, 82, 3, f); c.drawCircle(74, 76, 3, f);
    }

    private static void drawStrawberry(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        Path p = new Path(); p.moveTo(26, 40); p.lineTo(94, 40); p.quadTo(60, 112, 26, 40); p.close();
        f.setColor(0xFFE63946); c.drawPath(p, f); c.drawPath(p, st);
        f.setColor(0xFF3FB950); c.drawOval(new RectF(34, 24, 86, 44), f); c.drawOval(new RectF(34, 24, 86, 44), st);
        f.setColor(0xFFFFD23F);
        c.drawCircle(48, 56, 2.5f, f); c.drawCircle(72, 56, 2.5f, f); c.drawCircle(60, 72, 2.5f, f);
    }

    private static void drawGrapes(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFF8E44EC);
        float[][] pts = {{44, 44}, {60, 44}, {76, 44}, {52, 62}, {68, 62}, {60, 80}};
        for (float[] pt : pts) { c.drawCircle(pt[0], pt[1], 13, f); c.drawCircle(pt[0], pt[1], 13, st); }
    }

    private static void drawCarrot(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFF3FB950);
        c.drawOval(new RectF(46, 10, 62, 36), f); c.drawOval(new RectF(46, 10, 62, 36), st);
        c.drawOval(new RectF(58, 12, 74, 36), f); c.drawOval(new RectF(58, 12, 74, 36), st);
        Path p = new Path(); p.moveTo(38, 34); p.lineTo(82, 34); p.lineTo(60, 108); p.close();
        f.setColor(0xFFFF8C1A); c.drawPath(p, f); c.drawPath(p, st);
    }

    private static void drawCorn(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFFFFD23F);
        c.drawOval(new RectF(40, 16, 80, 96), f); c.drawOval(new RectF(40, 16, 80, 96), st);
        f.setColor(0xFF3FB950);
        c.drawOval(new RectF(26, 56, 56, 104), f); c.drawOval(new RectF(26, 56, 56, 104), st);
        c.drawOval(new RectF(64, 56, 94, 104), f); c.drawOval(new RectF(64, 56, 94, 104), st);
    }

    private static void drawTomato(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFFE63946);
        c.drawCircle(60, 64, 36, f); c.drawCircle(60, 64, 36, st);
        f.setColor(0xFF3FB950);
        c.drawOval(new RectF(44, 24, 76, 38), f); c.drawOval(new RectF(44, 24, 76, 38), st);
    }

    private static void drawBroccoli(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFF8FD76A);
        c.drawRoundRect(new RectF(48, 60, 72, 102), 8, 8, f); c.drawRoundRect(new RectF(48, 60, 72, 102), 8, 8, st);
        f.setColor(0xFF3FB950);
        c.drawCircle(42, 46, 20, f); c.drawCircle(42, 46, 20, st);
        c.drawCircle(78, 46, 20, f); c.drawCircle(78, 46, 20, st);
        c.drawCircle(60, 34, 22, f); c.drawCircle(60, 34, 22, st);
    }

    private static void drawPotato(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFFD39B5C);
        c.drawOval(new RectF(22, 34, 98, 92), f); c.drawOval(new RectF(22, 34, 98, 92), st);
    }

    private static void drawCar(Canvas c, Paint f, Paint st, float sec, int col, int mode) {
        c.translate(0, (float) Math.sin(sec * 8f) * 1.5f);
        if (mode == 1 || mode == 2 || mode == 3) {
            boolean flash = ((int) (sec * 4)) % 2 == 0;
            f.setColor(flash ? 0xFF2F80ED : 0xFFE63946);
            c.drawRoundRect(new RectF(50, 22, 70, 34), 4, 4, f);
            c.drawRoundRect(new RectF(50, 22, 70, 34), 4, 4, st);
        }
        f.setColor(col);
        c.drawRoundRect(new RectF(24, 34, 96, 68), 14, 14, f); c.drawRoundRect(new RectF(24, 34, 96, 68), 14, 14, st);
        c.drawRoundRect(new RectF(12, 54, 108, 86), 12, 12, f); c.drawRoundRect(new RectF(12, 54, 108, 86), 12, 12, st);
        f.setColor(0xFFBFE8FF);
        c.drawRoundRect(new RectF(32, 40, 88, 56), 6, 6, f);
        f.setColor(INK);
        c.drawCircle(34, 88, 12, f); c.drawCircle(86, 88, 12, f);
        f.setColor(0xFFFFFFFF);
        c.drawCircle(34, 88, 5, f); c.drawCircle(86, 88, 5, f);
    }

    private static void drawBus(Canvas c, Paint f, Paint st, float sec) {
        c.translate(0, (float) Math.sin(sec * 8f) * 1.5f);
        f.setColor(0xFFFFD23F);
        c.drawRoundRect(new RectF(12, 30, 108, 86), 14, 14, f); c.drawRoundRect(new RectF(12, 30, 108, 86), 14, 14, st);
        f.setColor(0xFFBFE8FF);
        for (int i = 0; i < 3; i++) c.drawRoundRect(new RectF(20 + i * 28, 38, 42 + i * 28, 58), 4, 4, f);
        f.setColor(INK);
        c.drawCircle(32, 88, 12, f); c.drawCircle(88, 88, 12, f);
    }

    private static void drawTrain(Canvas c, Paint f, Paint st, float sec) {
        c.translate(0, (float) Math.sin(sec * 8f) * 1.5f);
        f.setColor(0xFFE63946);
        c.drawRoundRect(new RectF(16, 46, 98, 84), 10, 10, f); c.drawRoundRect(new RectF(16, 46, 98, 84), 10, 10, st);
        f.setColor(0xFFFFD23F);
        c.drawRoundRect(new RectF(56, 26, 96, 52), 8, 8, f); c.drawRoundRect(new RectF(56, 26, 96, 52), 8, 8, st);
        f.setColor(INK);
        c.drawRoundRect(new RectF(24, 24, 38, 46), 4, 4, f);
        c.drawCircle(34, 88, 12, f); c.drawCircle(64, 88, 12, f); c.drawCircle(88, 88, 12, f);
    }

    private static void drawTractor(Canvas c, Paint f, Paint st, float sec) {
        c.translate(0, (float) Math.sin(sec * 8f) * 1.5f);
        f.setColor(0xFF3FB950);
        c.drawRoundRect(new RectF(16, 48, 86, 80), 10, 10, f); c.drawRoundRect(new RectF(16, 48, 86, 80), 10, 10, st);
        c.drawRoundRect(new RectF(52, 26, 88, 54), 8, 8, f); c.drawRoundRect(new RectF(52, 26, 88, 54), 8, 8, st);
        f.setColor(INK);
        c.drawCircle(30, 84, 10, f); c.drawCircle(76, 78, 18, f);
    }

    private static void drawTruck(Canvas c, Paint f, Paint st, float sec) {
        drawCar(c, f, st, sec, 0xFF4CC9F0, 0);
    }

    private static void drawPlane(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFF4CC9F0);
        Path wing = new Path(); wing.moveTo(48, 54); wing.lineTo(74, 20); wing.lineTo(86, 20); wing.lineTo(72, 54); wing.close();
        c.drawPath(wing, f); c.drawPath(wing, st);
        f.setColor(0xFFFFFFFF);
        c.drawOval(new RectF(16, 46, 104, 74), f); c.drawOval(new RectF(16, 46, 104, 74), st);
    }

    private static void drawHeli(Canvas c, Paint f, Paint st, float sec) {
        c.translate(0, (float) Math.sin(sec * 4f) * 3f);
        c.drawLine(20, 26, 100, 26, st); c.drawLine(60, 26, 60, 40, st);
        f.setColor(0xFFFF9F1C);
        c.drawOval(new RectF(26, 40, 94, 84), f); c.drawOval(new RectF(26, 40, 94, 84), st);
        f.setColor(0xFFBFE8FF);
        c.drawOval(new RectF(34, 46, 66, 72), f);
    }

    private static void drawRocket(Canvas c, Paint f, Paint st, float sec) {
        c.translate(0, (float) Math.sin(sec * 5f) * 2.5f);
        f.setColor(((int) (sec * 10) % 2 == 0) ? 0xFFFF9F1C : 0xFFFFD23F);
        Path fl = new Path(); fl.moveTo(46, 90); fl.lineTo(74, 90); fl.lineTo(60, 114); fl.close();
        c.drawPath(fl, f);
        f.setColor(0xFFFFFFFF);
        c.drawOval(new RectF(38, 16, 82, 94), f); c.drawOval(new RectF(38, 16, 82, 94), st);
        f.setColor(0xFFE63946);
        c.drawArc(new RectF(38, 16, 82, 56), 180, 180, true, f);
        f.setColor(0xFF4CC9F0);
        c.drawCircle(60, 56, 11, f); c.drawCircle(60, 56, 11, st);
    }

    private static void drawBoat(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFFFFFFFF);
        Path sail = new Path(); sail.moveTo(60, 16); sail.lineTo(92, 66); sail.lineTo(60, 66); sail.close();
        c.drawPath(sail, f); c.drawPath(sail, st);
        f.setColor(0xFFD96B27);
        Path hull = new Path(); hull.moveTo(16, 70); hull.lineTo(104, 70); hull.lineTo(88, 96); hull.lineTo(32, 96); hull.close();
        c.drawPath(hull, f); c.drawPath(hull, st);
    }

    private static void drawBone(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFFFFFAF0);
        c.drawCircle(24, 48, 12, f); c.drawCircle(24, 68, 12, f);
        c.drawCircle(96, 48, 12, f); c.drawCircle(96, 68, 12, f);
        c.drawRect(24, 48, 96, 68, f);
        c.drawRoundRect(new RectF(20, 44, 100, 72), 10, 10, st);
    }

    private static void drawCheese(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        Path p = new Path(); p.moveTo(20, 84); p.lineTo(100, 84); p.lineTo(86, 30); p.close();
        f.setColor(0xFFFFD23F); c.drawPath(p, f); c.drawPath(p, st);
        f.setColor(0xFFFF9F1C); c.drawCircle(58, 70, 6, f); c.drawCircle(78, 66, 5, f);
    }

    private static void drawHoney(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFFFFB703);
        c.drawOval(new RectF(26, 32, 94, 98), f); c.drawOval(new RectF(26, 32, 94, 98), st);
    }

    private static void drawMilk(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFFFFFFFF);
        c.drawRoundRect(new RectF(38, 36, 82, 100), 12, 12, f); c.drawRoundRect(new RectF(38, 36, 82, 100), 12, 12, st);
        f.setColor(0xFFFFD23F);
        c.drawOval(new RectF(50, 18, 70, 38), f); c.drawOval(new RectF(50, 18, 70, 38), st);
    }

    private static void drawBamboo(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFF3FB950);
        c.drawRoundRect(new RectF(48, 16, 72, 104), 8, 8, f); c.drawRoundRect(new RectF(48, 16, 72, 104), 8, 8, st);
    }

    private static void drawGrass(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFF6CC56F);
        Path p = new Path(); p.moveTo(24, 96); p.lineTo(40, 30); p.lineTo(54, 72); p.lineTo(64, 24); p.lineTo(78, 72); p.lineTo(94, 34); p.lineTo(96, 96); p.close();
        c.drawPath(p, f); c.drawPath(p, st);
    }

    private static void drawGenericBadge(Canvas c, Paint f, Paint st, float bob, int col) {
        c.translate(0, bob);
        f.setColor(col);
        c.drawCircle(60, 60, 38, f); c.drawCircle(60, 60, 38, st);
    }
}
