package com.gabink.tamanbermain;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * Storybook-style professions, fruits, vegetables, foods, and vehicles for Taman Bermain Si Kecil.
 */
public class CartoonExtras {
    private static final int INK = CartoonArt.INK;

    public static boolean draw(
        Canvas c, Paint f, Paint st, String key,
        float sec, float bob, boolean blink, boolean sil, CartoonArt.Mood mood
    ) {
        switch (key) {
            // Professions
            case "dokter": case "polisi": case "damkar": case "petani": case "pilot": case "astronot":
                drawProfesi(c, f, st, key, sec, blink, sil, mood); return true;
            // Fruits
            case "apel": drawApel(c, f, st, bob, sil, mood, blink); return true;
            case "pisang": drawPisang(c, f, st, bob, sil, mood, blink); return true;
            case "jeruk": drawJeruk(c, f, st, bob, sil, mood, blink); return true;
            case "semangka": drawSemangka(c, f, st, bob, sil, mood, blink); return true;
            case "stroberi": drawStroberi(c, f, st, bob, sil, mood, blink); return true;
            case "anggur": drawAnggur(c, f, st, bob, sil, mood, blink); return true;
            case "nanas": drawNanas(c, f, st, bob, sil, mood, blink); return true;
            case "mangga": drawMangga(c, f, st, bob, sil, mood, blink); return true;
            // Veggies
            case "wortel": drawWortel(c, f, st, bob, sil, mood, blink); return true;
            case "jagung": drawJagung(c, f, st, bob, sil, mood, blink); return true;
            case "tomat": drawTomat(c, f, st, bob, sil, mood, blink); return true;
            case "brokoli": drawBrokoli(c, f, st, bob, sil, mood, blink); return true;
            case "kentang": drawKentang(c, f, st, bob, sil, mood, blink); return true;
            // Vehicles
            case "mobil": drawCar(c, f, st, sec, 0xFFE63946, 0, sil); return true;
            case "mobilpolisi": drawCar(c, f, st, sec, 0xFF2B2D42, 1, sil); return true;
            case "ambulans": drawCar(c, f, st, sec, 0xFFFFFFFF, 2, sil); return true;
            case "pemadam": drawCar(c, f, st, sec, 0xFFE63946, 3, sil); return true;
            case "bus": drawBus(c, f, st, sec, sil); return true;
            case "kereta": drawTrain(c, f, st, sec, sil); return true;
            case "traktor": drawTractor(c, f, st, sec, sil); return true;
            case "truk": drawTruck(c, f, st, sec, sil); return true;
            case "pesawat": drawPlane(c, f, st, bob, sil); return true;
            case "helikopter": drawHeli(c, f, st, sec, sil); return true;
            case "roket": drawRocket(c, f, st, sec, sil); return true;
            case "kapal": case "perahu": case "speedboat": drawBoat(c, f, st, bob, sil); return true;
            case "sepeda": drawBike(c, f, st, sec, sil); return true;
            case "motor": drawMoto(c, f, st, sec, sil); return true;
            // Foods
            case "tulang": drawBone(c, f, st, bob); return true;
            case "keju": drawCheese(c, f, st, bob, sil); return true;
            case "madu": drawHoney(c, f, st, bob, sil); return true;
            case "susu": drawMilk(c, f, st, bob, sil); return true;
            case "bambu": drawBamboo(c, f, st, bob, sil); return true;
            case "rumput": drawGrass(c, f, st, bob); return true;
            default: return false;
        }
    }

    private static void drawProfesi(
        Canvas c, Paint f, Paint st, String key,
        float sec, boolean blink, boolean sil, CartoonArt.Mood mood
    ) {
        CartoonArt.drawShadow(c, f, 60, 108, 28, 6);
        CartoonArt.applyBreath(c, sec, 3.2f, 0.025f);

        int suitCol = 0xFFFFFFFF;
        int hatCol = 0xFF4CC9F0;
        switch (key) {
            case "dokter": suitCol = 0xFFFFFFFF; hatCol = 0xFF4CC9F0; break;
            case "polisi": suitCol = 0xFF2F5D8C; hatCol = 0xFF1D3557; break;
            case "damkar": suitCol = 0xFFE63946; hatCol = 0xFFFFB703; break;
            case "petani": suitCol = 0xFF4CC9F0; hatCol = 0xFFFFD23F; break;
            case "pilot": suitCol = 0xFF2B2D42; hatCol = 0xFF1D3557; break;
            case "astronot": suitCol = 0xFFF1F5F9; hatCol = 0xFFFFFFFF; break;
        }

        // Outfit body
        f.setColor(suitCol);
        c.drawRoundRect(new RectF(32, 68, 88, 106), 16, 16, f);
        c.drawRoundRect(new RectF(32, 68, 88, 106), 16, 16, st);

        if (!sil) {
            if ("dokter".equals(key)) {
                st.setColor(0xFF4CC9F0);
                c.drawArc(new RectF(44, 68, 76, 90), 0, 180, false, st);
                st.setColor(INK);
            } else if ("damkar".equals(key)) {
                f.setColor(0xFFFFD23F);
                c.drawRect(34, 84, 86, 92, f);
            } else if ("petani".equals(key)) {
                f.setColor(0xFF6CC56F);
                c.drawRoundRect(new RectF(40, 74, 80, 106), 8, 8, f);
            } else if ("polisi".equals(key) || "pilot".equals(key)) {
                f.setColor(0xFFFFD23F);
                c.drawCircle(72, 82, 5, f);
            } else if ("astronot".equals(key)) {
                f.setColor(0xFF4CC9F0);
                c.drawRoundRect(new RectF(46, 78, 74, 94), 6, 6, f);
            }
        }

        // Head
        f.setColor(0xFFFFD5B8);
        c.drawCircle(60, 48, 25, f);
        c.drawCircle(60, 48, 25, st);

        // Astronaut helmet bubble
        if ("astronot".equals(key)) {
            f.setColor(0x337FD0FF);
            c.drawCircle(60, 48, 32, f);
            c.drawCircle(60, 48, 32, st);
        } else if ("petani".equals(key)) {
            // Straw hat
            f.setColor(hatCol);
            c.drawOval(new RectF(20, 22, 100, 36), f); c.drawOval(new RectF(20, 22, 100, 36), st);
            c.drawArc(new RectF(36, 8, 84, 34), 180, 180, true, f); c.drawArc(new RectF(36, 8, 84, 34), 180, 180, true, st);
        } else if ("dokter".equals(key)) {
            // Head mirror band
            f.setColor(0xFFE63946);
            c.drawRoundRect(new RectF(36, 26, 84, 32), 3, 3, f);
            f.setColor(0xFFD8E2EC);
            c.drawCircle(60, 27, 8, f); c.drawCircle(60, 27, 8, st);
        } else {
            // Cap / Helmet
            f.setColor(hatCol);
            c.drawArc(new RectF(32, 14, 88, 44), 180, 180, true, f);
            c.drawArc(new RectF(32, 14, 88, 44), 180, 180, true, st);
            c.drawRoundRect(new RectF(28, 26, 92, 34), 4, 4, f);
            c.drawRoundRect(new RectF(28, 26, 92, 34), 4, 4, st);
            if (!sil) {
                f.setColor(0xFFFFD23F);
                c.drawCircle(60, 22, 4.5f, f);
            }
        }
        CartoonArt.drawFace(c, f, st, 60, 48, 10, 3.8f, 60, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawApel(Canvas c, Paint f, Paint st, float bob, boolean sil, CartoonArt.Mood mood, boolean blink) {
        c.translate(0, bob);
        c.drawLine(60, 32, 60, 16, st);
        f.setColor(0xFF3FB950);
        c.drawOval(new RectF(60, 14, 86, 30), f); c.drawOval(new RectF(60, 14, 86, 30), st);
        f.setColor(0xFFE63946);
        c.drawOval(new RectF(22, 30, 98, 104), f); c.drawOval(new RectF(22, 30, 98, 104), st);
        if (!sil) {
            f.setColor(0x55FFFFFF);
            c.drawOval(new RectF(32, 40, 44, 58), f);
        }
        CartoonArt.drawFace(c, f, st, 60, 62, 13, 4.2f, 75, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawPisang(Canvas c, Paint f, Paint st, float bob, boolean sil, CartoonArt.Mood mood, boolean blink) {
        c.translate(0, bob);
        Path p = new Path();
        p.moveTo(22, 84); p.quadTo(62, 106, 96, 28); p.quadTo(80, 18, 72, 34); p.quadTo(52, 72, 22, 84); p.close();
        f.setColor(0xFFFFD23F);
        c.drawPath(p, f); c.drawPath(p, st);
        CartoonArt.drawFace(c, f, st, 64, 62, 9, 3.4f, 72, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawJeruk(Canvas c, Paint f, Paint st, float bob, boolean sil, CartoonArt.Mood mood, boolean blink) {
        c.translate(0, bob);
        f.setColor(0xFF3FB950);
        c.drawOval(new RectF(58, 16, 84, 32), f); c.drawOval(new RectF(58, 16, 84, 32), st);
        f.setColor(0xFFFF8C1A);
        c.drawCircle(60, 66, 36, f); c.drawCircle(60, 66, 36, st);
        CartoonArt.drawFace(c, f, st, 60, 62, 13, 4.2f, 75, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawSemangka(Canvas c, Paint f, Paint st, float bob, boolean sil, CartoonArt.Mood mood, boolean blink) {
        c.translate(0, bob);
        f.setColor(0xFF3FB950);
        c.drawArc(new RectF(14, 24, 106, 116), 0, 180, true, f);
        c.drawArc(new RectF(14, 24, 106, 116), 0, 180, true, st);
        if (!sil) {
            f.setColor(0xFFE63946);
            c.drawArc(new RectF(22, 32, 98, 108), 0, 180, true, f);
        }
        CartoonArt.drawFace(c, f, st, 60, 74, 14, 3.8f, 84, mood, blink, sil, CartoonArt.NOSE_NONE, false, false);
    }

    private static void drawStroberi(Canvas c, Paint f, Paint st, float bob, boolean sil, CartoonArt.Mood mood, boolean blink) {
        c.translate(0, bob);
        Path p = new Path(); p.moveTo(26, 40); p.lineTo(94, 40); p.quadTo(60, 114, 26, 40); p.close();
        f.setColor(0xFFE63946); c.drawPath(p, f); c.drawPath(p, st);
        f.setColor(0xFF3FB950); c.drawOval(new RectF(34, 24, 86, 44), f); c.drawOval(new RectF(34, 24, 86, 44), st);
        CartoonArt.drawFace(c, f, st, 60, 58, 11, 3.8f, 69, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawAnggur(Canvas c, Paint f, Paint st, float bob, boolean sil, CartoonArt.Mood mood, boolean blink) {
        c.translate(0, bob);
        f.setColor(0xFF3FB950);
        c.drawOval(new RectF(50, 14, 74, 30), f); c.drawOval(new RectF(50, 14, 74, 30), st);
        f.setColor(0xFF8E44EC);
        float[][] pts = {{44, 44}, {60, 44}, {76, 44}, {52, 62}, {68, 62}, {60, 80}};
        for (float[] pt : pts) { c.drawCircle(pt[0], pt[1], 13, f); c.drawCircle(pt[0], pt[1], 13, st); }
        CartoonArt.drawFace(c, f, st, 60, 58, 12, 3.6f, 68, mood, blink, sil, CartoonArt.NOSE_NONE, false, false);
    }

    private static void drawNanas(Canvas c, Paint f, Paint st, float bob, boolean sil, CartoonArt.Mood mood, boolean blink) {
        c.translate(0, bob);
        f.setColor(0xFF3FB950);
        Path crown = new Path();
        crown.moveTo(44, 36); crown.lineTo(40, 14); crown.lineTo(54, 28);
        crown.lineTo(60, 10); crown.lineTo(66, 28); crown.lineTo(80, 14); crown.lineTo(76, 36); crown.close();
        c.drawPath(crown, f); c.drawPath(crown, st);
        f.setColor(0xFFFFB703);
        c.drawOval(new RectF(28, 34, 92, 104), f); c.drawOval(new RectF(28, 34, 92, 104), st);
        CartoonArt.drawFace(c, f, st, 60, 62, 12, 4.0f, 74, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawMangga(Canvas c, Paint f, Paint st, float bob, boolean sil, CartoonArt.Mood mood, boolean blink) {
        c.translate(0, bob);
        f.setColor(0xFF3FB950);
        c.drawOval(new RectF(56, 16, 82, 30), f); c.drawOval(new RectF(56, 16, 82, 30), st);
        f.setColor(0xFFFFB703);
        c.drawOval(new RectF(26, 28, 94, 102), f); c.drawOval(new RectF(26, 28, 94, 102), st);
        CartoonArt.drawFace(c, f, st, 60, 60, 12, 4.0f, 72, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawWortel(Canvas c, Paint f, Paint st, float bob, boolean sil, CartoonArt.Mood mood, boolean blink) {
        c.translate(0, bob);
        f.setColor(0xFF3FB950);
        c.drawOval(new RectF(46, 10, 62, 36), f); c.drawOval(new RectF(46, 10, 62, 36), st);
        c.drawOval(new RectF(58, 12, 74, 36), f); c.drawOval(new RectF(58, 12, 74, 36), st);
        Path p = new Path(); p.moveTo(36, 34); p.lineTo(84, 34); p.lineTo(60, 108); p.close();
        f.setColor(0xFFFF8C1A); c.drawPath(p, f); c.drawPath(p, st);
        CartoonArt.drawFace(c, f, st, 60, 52, 9, 3.6f, 63, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawJagung(Canvas c, Paint f, Paint st, float bob, boolean sil, CartoonArt.Mood mood, boolean blink) {
        c.translate(0, bob);
        f.setColor(0xFFFFD23F);
        c.drawOval(new RectF(38, 16, 82, 96), f); c.drawOval(new RectF(38, 16, 82, 96), st);
        f.setColor(0xFF3FB950);
        c.drawOval(new RectF(26, 56, 56, 104), f); c.drawOval(new RectF(26, 56, 56, 104), st);
        c.drawOval(new RectF(64, 56, 94, 104), f); c.drawOval(new RectF(64, 56, 94, 104), st);
        CartoonArt.drawFace(c, f, st, 60, 46, 9, 3.6f, 57, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawTomat(Canvas c, Paint f, Paint st, float bob, boolean sil, CartoonArt.Mood mood, boolean blink) {
        c.translate(0, bob);
        f.setColor(0xFFE63946);
        c.drawCircle(60, 64, 36, f); c.drawCircle(60, 64, 36, st);
        f.setColor(0xFF3FB950);
        c.drawOval(new RectF(44, 24, 76, 38), f); c.drawOval(new RectF(44, 24, 76, 38), st);
        CartoonArt.drawFace(c, f, st, 60, 62, 12, 4.0f, 74, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawBrokoli(Canvas c, Paint f, Paint st, float bob, boolean sil, CartoonArt.Mood mood, boolean blink) {
        c.translate(0, bob);
        f.setColor(0xFF8FD76A);
        c.drawRoundRect(new RectF(48, 60, 72, 102), 8, 8, f); c.drawRoundRect(new RectF(48, 60, 72, 102), 8, 8, st);
        f.setColor(0xFF3FB950);
        c.drawCircle(40, 46, 20, f); c.drawCircle(40, 46, 20, st);
        c.drawCircle(80, 46, 20, f); c.drawCircle(80, 46, 20, st);
        c.drawCircle(60, 34, 22, f); c.drawCircle(60, 34, 22, st);
        CartoonArt.drawFace(c, f, st, 60, 44, 11, 3.8f, 55, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawKentang(Canvas c, Paint f, Paint st, float bob, boolean sil, CartoonArt.Mood mood, boolean blink) {
        c.translate(0, bob);
        f.setColor(0xFFD39B5C);
        c.drawOval(new RectF(22, 34, 98, 92), f); c.drawOval(new RectF(22, 34, 98, 92), st);
        CartoonArt.drawFace(c, f, st, 60, 58, 12, 4.0f, 70, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawCar(Canvas c, Paint f, Paint st, float sec, int col, int mode, boolean sil) {
        c.translate(0, (float) Math.sin(sec * 8f) * 1.5f);
        if (mode >= 1) {
            boolean flash = ((int) (sec * 4)) % 2 == 0;
            f.setColor(flash ? 0xFF2F80ED : 0xFFE63946);
            c.drawRoundRect(new RectF(50, 22, 70, 34), 4, 4, f);
            c.drawRoundRect(new RectF(50, 22, 70, 34), 4, 4, st);
        }
        f.setColor(col);
        c.drawRoundRect(new RectF(24, 34, 96, 68), 14, 14, f); c.drawRoundRect(new RectF(24, 34, 96, 68), 14, 14, st);
        c.drawRoundRect(new RectF(12, 54, 108, 86), 12, 12, f); c.drawRoundRect(new RectF(12, 54, 108, 86), 12, 12, st);
        if (!sil) {
            f.setColor(0xFFBFE8FF);
            c.drawRoundRect(new RectF(32, 40, 88, 56), 6, 6, f);
            if (mode == 2) {
                f.setColor(0xFFE63946);
                c.drawRect(56, 62, 64, 78, f);
                c.drawRect(52, 66, 68, 74, f);
            }
        }
        f.setColor(INK);
        c.drawCircle(34, 88, 12, f); c.drawCircle(86, 88, 12, f);
        if (!sil) {
            f.setColor(0xFFFFFFFF);
            c.drawCircle(34, 88, 5, f); c.drawCircle(86, 88, 5, f);
        }
    }

    private static void drawBus(Canvas c, Paint f, Paint st, float sec, boolean sil) {
        c.translate(0, (float) Math.sin(sec * 8f) * 1.5f);
        f.setColor(0xFFFFD23F);
        c.drawRoundRect(new RectF(12, 30, 108, 86), 14, 14, f); c.drawRoundRect(new RectF(12, 30, 108, 86), 14, 14, st);
        if (!sil) {
            f.setColor(0xFFBFE8FF);
            for (int i = 0; i < 3; i++) c.drawRoundRect(new RectF(20 + i * 28, 38, 42 + i * 28, 58), 4, 4, f);
        }
        f.setColor(INK);
        c.drawCircle(32, 88, 12, f); c.drawCircle(88, 88, 12, f);
    }

    private static void drawTrain(Canvas c, Paint f, Paint st, float sec, boolean sil) {
        c.translate(0, (float) Math.sin(sec * 8f) * 1.5f);
        f.setColor(0xFFE63946);
        c.drawRoundRect(new RectF(16, 46, 98, 84), 10, 10, f); c.drawRoundRect(new RectF(16, 46, 98, 84), 10, 10, st);
        f.setColor(0xFFFFD23F);
        c.drawRoundRect(new RectF(56, 26, 96, 52), 8, 8, f); c.drawRoundRect(new RectF(56, 26, 96, 52), 8, 8, st);
        f.setColor(INK);
        c.drawRoundRect(new RectF(24, 24, 38, 46), 4, 4, f);
        c.drawCircle(34, 88, 12, f); c.drawCircle(64, 88, 12, f); c.drawCircle(88, 88, 12, f);
    }

    private static void drawTractor(Canvas c, Paint f, Paint st, float sec, boolean sil) {
        c.translate(0, (float) Math.sin(sec * 8f) * 1.5f);
        f.setColor(0xFF3FB950);
        c.drawRoundRect(new RectF(16, 48, 86, 80), 10, 10, f); c.drawRoundRect(new RectF(16, 48, 86, 80), 10, 10, st);
        c.drawRoundRect(new RectF(52, 26, 88, 54), 8, 8, f); c.drawRoundRect(new RectF(52, 26, 88, 54), 8, 8, st);
        f.setColor(INK);
        c.drawCircle(30, 84, 10, f); c.drawCircle(76, 78, 18, f);
    }

    private static void drawTruck(Canvas c, Paint f, Paint st, float sec, boolean sil) {
        drawCar(c, f, st, sec, 0xFF4CC9F0, 0, sil);
    }

    private static void drawPlane(Canvas c, Paint f, Paint st, float bob, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFF4CC9F0);
        Path wing = new Path(); wing.moveTo(48, 54); wing.lineTo(74, 20); wing.lineTo(86, 20); wing.lineTo(72, 54); wing.close();
        c.drawPath(wing, f); c.drawPath(wing, st);
        f.setColor(0xFFFFFFFF);
        c.drawOval(new RectF(16, 46, 104, 74), f); c.drawOval(new RectF(16, 46, 104, 74), st);
    }

    private static void drawHeli(Canvas c, Paint f, Paint st, float sec, boolean sil) {
        c.translate(0, (float) Math.sin(sec * 4f) * 3f);
        c.drawLine(20, 26, 100, 26, st); c.drawLine(60, 26, 60, 40, st);
        f.setColor(0xFFFF9F1C);
        c.drawOval(new RectF(26, 40, 94, 84), f); c.drawOval(new RectF(26, 40, 94, 84), st);
        if (!sil) {
            f.setColor(0xFFBFE8FF);
            c.drawOval(new RectF(34, 46, 66, 72), f);
        }
    }

    private static void drawRocket(Canvas c, Paint f, Paint st, float sec, boolean sil) {
        c.translate(0, (float) Math.sin(sec * 5f) * 2.5f);
        if (!sil) {
            f.setColor(((int) (sec * 10) % 2 == 0) ? 0xFFFF9F1C : 0xFFFFD23F);
            Path fl = new Path(); fl.moveTo(46, 90); fl.lineTo(74, 90); fl.lineTo(60, 114); fl.close();
            c.drawPath(fl, f);
        }
        f.setColor(0xFFFFFFFF);
        c.drawOval(new RectF(38, 16, 82, 94), f); c.drawOval(new RectF(38, 16, 82, 94), st);
        f.setColor(0xFFE63946);
        c.drawArc(new RectF(38, 16, 82, 56), 180, 180, true, f);
        f.setColor(0xFF4CC9F0);
        c.drawCircle(60, 56, 11, f); c.drawCircle(60, 56, 11, st);
    }

    private static void drawBoat(Canvas c, Paint f, Paint st, float bob, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFFFFFFFF);
        Path sail = new Path(); sail.moveTo(60, 16); sail.lineTo(92, 66); sail.lineTo(60, 66); sail.close();
        c.drawPath(sail, f); c.drawPath(sail, st);
        f.setColor(0xFFD96B27);
        Path hull = new Path(); hull.moveTo(16, 70); hull.lineTo(104, 70); hull.lineTo(88, 96); hull.lineTo(32, 96); hull.close();
        c.drawPath(hull, f); c.drawPath(hull, st);
    }

    private static void drawBike(Canvas c, Paint f, Paint st, float sec, boolean sil) {
        c.translate(0, (float) Math.sin(sec * 6f) * 1.5f);
        st.setColor(INK);
        c.drawCircle(30, 82, 16, st); c.drawCircle(90, 82, 16, st);
        st.setColor(0xFFE63946);
        c.drawLine(30, 82, 54, 52, st); c.drawLine(54, 52, 82, 52, st);
        c.drawLine(82, 52, 90, 82, st); c.drawLine(54, 52, 62, 82, st);
        c.drawLine(30, 82, 62, 82, st);
        st.setColor(INK);
    }

    private static void drawMoto(Canvas c, Paint f, Paint st, float sec, boolean sil) {
        c.translate(0, (float) Math.sin(sec * 8f) * 1.5f);
        f.setColor(INK);
        c.drawCircle(28, 84, 15, f); c.drawCircle(92, 84, 15, f);
        f.setColor(0xFFFF5D8F);
        c.drawRoundRect(new RectF(28, 52, 88, 76), 14, 14, f);
        c.drawRoundRect(new RectF(28, 52, 88, 76), 14, 14, st);
    }

    private static void drawBone(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFFFFFAF0);
        c.drawCircle(24, 48, 12, f); c.drawCircle(24, 68, 12, f);
        c.drawCircle(96, 48, 12, f); c.drawCircle(96, 68, 12, f);
        c.drawRect(24, 48, 96, 68, f);
        c.drawRoundRect(new RectF(20, 44, 100, 72), 10, 10, st);
    }

    private static void drawCheese(Canvas c, Paint f, Paint st, float bob, boolean sil) {
        c.translate(0, bob);
        Path p = new Path(); p.moveTo(20, 84); p.lineTo(100, 84); p.lineTo(86, 30); p.close();
        f.setColor(0xFFFFD23F); c.drawPath(p, f); c.drawPath(p, st);
        if (!sil) {
            f.setColor(0xFFFF9F1C); c.drawCircle(58, 70, 6, f); c.drawCircle(78, 66, 5, f);
        }
    }

    private static void drawHoney(Canvas c, Paint f, Paint st, float bob, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFFFFB703);
        c.drawOval(new RectF(26, 32, 94, 98), f); c.drawOval(new RectF(26, 32, 94, 98), st);
        if (!sil) {
            f.setColor(0xFFFFD23F);
            c.drawRoundRect(new RectF(34, 24, 86, 38), 6, 6, f);
            c.drawRoundRect(new RectF(34, 24, 86, 38), 6, 6, st);
        }
    }

    private static void drawMilk(Canvas c, Paint f, Paint st, float bob, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFFFFFFFF);
        c.drawRoundRect(new RectF(38, 36, 82, 100), 12, 12, f); c.drawRoundRect(new RectF(38, 36, 82, 100), 12, 12, st);
        f.setColor(0xFFFFD23F);
        c.drawOval(new RectF(50, 18, 70, 38), f); c.drawOval(new RectF(50, 18, 70, 38), st);
    }

    private static void drawBamboo(Canvas c, Paint f, Paint st, float bob, boolean sil) {
        c.translate(0, bob);
        f.setColor(0xFF3FB950);
        c.drawRoundRect(new RectF(48, 16, 72, 104), 8, 8, f); c.drawRoundRect(new RectF(48, 16, 72, 104), 8, 8, st);
        if (!sil) {
            c.drawLine(48, 44, 72, 44, st);
            c.drawLine(48, 74, 72, 74, st);
        }
    }

    private static void drawGrass(Canvas c, Paint f, Paint st, float bob) {
        c.translate(0, bob);
        f.setColor(0xFF6CC56F);
        Path p = new Path(); p.moveTo(24, 96); p.lineTo(40, 30); p.lineTo(54, 72); p.lineTo(64, 24); p.lineTo(78, 72); p.lineTo(94, 34); p.lineTo(96, 96); p.close();
        c.drawPath(p, f); c.drawPath(p, st);
    }
}
