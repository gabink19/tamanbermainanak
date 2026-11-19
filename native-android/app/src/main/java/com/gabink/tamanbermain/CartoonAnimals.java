package com.gabink.tamanbermain;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * 35 authentic storybook-style animal characters for Taman Bermain Si Kecil.
 * Every animal has full-body proportions, characteristic details, breathing/wagging/swimming
 * animations, and supports CartoonArt.Mood expressions.
 */
public class CartoonAnimals {
    private static final int INK = CartoonArt.INK;

    public static boolean draw(
        Canvas c, Paint f, Paint st, String key,
        float sec, float bob, boolean blink, boolean sil, CartoonArt.Mood mood
    ) {
        switch (key) {
            case "kucing": drawKucing(c, f, st, sec, blink, sil, mood); return true;
            case "anjing": drawAnjing(c, f, st, sec, blink, sil, mood); return true;
            case "sapi": drawSapi(c, f, st, sec, blink, sil, mood); return true;
            case "ayam": drawAyam(c, f, st, sec, blink, sil, mood, true); return true;
            case "anakayam": drawAyam(c, f, st, sec, blink, sil, mood, false); return true;
            case "bebek": drawBebek(c, f, st, sec, blink, sil, mood); return true;
            case "kambing": drawKambing(c, f, st, sec, blink, sil, mood); return true;
            case "domba": drawDomba(c, f, st, sec, blink, sil, mood); return true;
            case "kuda": drawKuda(c, f, st, sec, blink, sil, mood); return true;
            case "katak": drawKatak(c, f, st, sec, blink, sil, mood); return true;
            case "singa": drawSinga(c, f, st, sec, blink, sil, mood); return true;
            case "harimau": drawHarimau(c, f, st, sec, blink, sil, mood); return true;
            case "gajah": drawGajah(c, f, st, sec, blink, sil, mood); return true;
            case "monyet": drawMonyet(c, f, st, sec, blink, sil, mood); return true;
            case "kelinci": drawKelinci(c, f, st, sec, blink, sil, mood); return true;
            case "panda": drawPanda(c, f, st, sec, blink, sil, mood); return true;
            case "tikus": drawTikus(c, f, st, sec, blink, sil, mood); return true;
            case "beruang": drawBeruang(c, f, st, sec, blink, sil, mood); return true;
            case "burung": drawBurung(c, f, st, sec, blink, sil, mood); return true;
            case "burunghantu": drawBurungHantu(c, f, st, sec, blink, sil, mood); return true;
            case "lebah": drawLebah(c, f, st, sec, blink, sil, mood); return true;
            case "bayi": drawBayi(c, f, st, sec, blink, sil, mood); return true;
            case "zebra": drawZebra(c, f, st, sec, blink, sil, mood); return true;
            case "jerapah": drawJerapah(c, f, st, sec, blink, sil, mood); return true;
            case "ikan": drawIkan(c, f, st, sec, blink, sil, mood, false); return true;
            case "ikanhias": drawIkan(c, f, st, sec, blink, sil, mood, true); return true;
            case "gurita": drawGurita(c, f, st, sec, blink, sil, mood); return true;
            case "kepiting": drawKepiting(c, f, st, sec, blink, sil, mood); return true;
            case "kurakura": drawKuraKura(c, f, st, sec, blink, sil, mood); return true;
            case "lumbalumba": drawLumbaLumba(c, f, st, sec, blink, sil, mood); return true;
            case "paus": drawPaus(c, f, st, sec, blink, sil, mood); return true;
            case "hiu": drawHiu(c, f, st, sec, blink, sil, mood); return true;
            case "buntal": drawBuntal(c, f, st, sec, blink, sil, mood); return true;
            case "cumi": drawCumi(c, f, st, sec, blink, sil, mood); return true;
            case "udang": drawUdang(c, f, st, sec, blink, sil, mood); return true;
            default: return false;
        }
    }

    private static void drawBody(Canvas c, Paint f, Paint st, int bodyCol, int bellyCol) {
        f.setColor(bodyCol);
        c.drawOval(new RectF(32, 62, 88, 106), f);
        c.drawOval(new RectF(32, 62, 88, 106), st);
        if (bellyCol != 0) {
            f.setColor(bellyCol);
            c.drawOval(new RectF(44, 72, 76, 101), f);
        }
    }

    private static void drawPaws(Canvas c, Paint f, Paint st, int col) {
        f.setColor(col);
        c.drawOval(new RectF(36, 94, 52, 108), f); c.drawOval(new RectF(36, 94, 52, 108), st);
        c.drawOval(new RectF(68, 94, 84, 108), f); c.drawOval(new RectF(68, 94, 84, 108), st);
    }

    private static void drawRoundEars(Canvas c, Paint f, Paint st, float lx, float rx, float y, float r, int outerCol, int innerCol, boolean sil) {
        f.setColor(outerCol);
        c.drawCircle(lx, y, r, f); c.drawCircle(lx, y, r, st);
        c.drawCircle(rx, y, r, f); c.drawCircle(rx, y, r, st);
        if (!sil && innerCol != 0) {
            f.setColor(innerCol);
            c.drawCircle(lx, y, r * 0.55f, f);
            c.drawCircle(rx, y, r * 0.55f, f);
        }
    }

    private static void drawKucing(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 30, 6);
        CartoonArt.applyBreath(c, sec, 3.2f, 0.03f);
        // Wagging curly tail
        float wag = (float) Math.sin(sec * 4.5f) * 6f;
        Path tail = new Path();
        tail.moveTo(84, 88);
        tail.quadTo(110, 82 + wag, 106, 58 + wag);
        float oldW = st.getStrokeWidth();
        st.setStrokeWidth(9f); st.setColor(INK); c.drawPath(tail, st);
        st.setStrokeWidth(5f); st.setColor(0xFFFF9F43); c.drawPath(tail, st);
        st.setColor(INK); st.setStrokeWidth(oldW);

        drawBody(c, f, st, 0xFFFFB142, sil ? 0 : 0xFFFFF3D6);
        drawPaws(c, f, st, 0xFFFFB142);

        // Pointy ears
        Path earL = new Path(); earL.moveTo(26, 52); earL.lineTo(22, 20); earL.lineTo(48, 34); earL.close();
        Path earR = new Path(); earR.moveTo(94, 52); earR.lineTo(98, 20); earR.lineTo(72, 34); earR.close();
        f.setColor(0xFFFFB142);
        c.drawPath(earL, f); c.drawPath(earL, st);
        c.drawPath(earR, f); c.drawPath(earR, st);
        if (!sil) {
            f.setColor(0xFFFF8FB1);
            Path inL = new Path(); inL.moveTo(29, 45); inL.lineTo(27, 28); inL.lineTo(42, 36); inL.close();
            Path inR = new Path(); inR.moveTo(91, 45); inR.lineTo(93, 28); inR.lineTo(78, 36); inR.close();
            c.drawPath(inL, f); c.drawPath(inR, f);
        }

        // Head
        f.setColor(0xFFFFB142);
        c.drawOval(new RectF(24, 26, 96, 84), f);
        c.drawOval(new RectF(24, 26, 96, 84), st);

        if (!sil) {
            // Forehead tabby stripes
            st.setColor(0xFFE67E22); st.setStrokeWidth(3.2f);
            c.drawLine(60, 28, 60, 37, st);
            c.drawLine(52, 30, 54, 37, st);
            c.drawLine(68, 30, 66, 37, st);
            // Whiskers
            st.setColor(INK); st.setStrokeWidth(2.4f);
            c.drawLine(15, 56, 30, 58, st); c.drawLine(14, 64, 30, 63, st);
            c.drawLine(105, 56, 90, 58, st); c.drawLine(106, 64, 90, 63, st);
            st.setStrokeWidth(oldW);
        }
        CartoonArt.drawFace(c, f, st, 60, 54, 13, 4.8f, 67, mood, blink, sil, CartoonArt.NOSE_TRI, true, true);
    }

    private static void drawAnjing(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 30, 6);
        CartoonArt.applyBreath(c, sec, 3.6f, 0.03f);
        float wag = (float) Math.sin(sec * 9f) * 8f;
        Path tail = new Path(); tail.moveTo(84, 86); tail.quadTo(106, 76 + wag, 104, 62 + wag);
        float oldW = st.getStrokeWidth();
        st.setStrokeWidth(9f); c.drawPath(tail, st);
        st.setStrokeWidth(5f); st.setColor(0xFFD48C46); c.drawPath(tail, st);
        st.setColor(INK); st.setStrokeWidth(oldW);

        drawBody(c, f, st, 0xFFE6A15C, sil ? 0 : 0xFFFFF1D6);
        if (!sil) {
            // Red collar & gold bell
            f.setColor(0xFFE63946);
            c.drawRoundRect(new RectF(42, 66, 78, 74), 4, 4, f);
            c.drawRoundRect(new RectF(42, 66, 78, 74), 4, 4, st);
            f.setColor(0xFFFFD23F);
            c.drawCircle(60, 75, 4.5f, f); c.drawCircle(60, 75, 4.5f, st);
        }
        drawPaws(c, f, st, 0xFFE6A15C);

        // Floppy ears
        f.setColor(0xFFA86434);
        c.drawRoundRect(new RectF(13, 38, 32, 76), 12, 12, f); c.drawRoundRect(new RectF(13, 38, 32, 76), 12, 12, st);
        c.drawRoundRect(new RectF(88, 38, 107, 76), 12, 12, f); c.drawRoundRect(new RectF(88, 38, 107, 76), 12, 12, st);

        // Head
        f.setColor(0xFFE6A15C);
        c.drawOval(new RectF(24, 24, 96, 82), f);
        c.drawOval(new RectF(24, 24, 96, 82), st);
        if (!sil) {
            f.setColor(0x66A86434);
            c.drawCircle(45, 50, 11, f);
            f.setColor(0xFFFFF1D6);
            c.drawOval(new RectF(43, 56, 77, 77), f);
        }
        CartoonArt.drawFace(c, f, st, 60, 50, 13, 4.8f, 65, mood, blink, sil, CartoonArt.NOSE_DARK, false, true);
    }

    private static void drawSapi(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 32, 6);
        CartoonArt.applyBreath(c, sec, 2.8f, 0.025f);
        drawBody(c, f, st, 0xFFFFFFFF, 0);
        if (!sil) {
            f.setColor(INK);
            c.drawCircle(46, 82, 8, f);
            c.drawCircle(72, 92, 6, f);
        }
        drawPaws(c, f, st, INK);

        // Ears & Horns
        f.setColor(0xFFFFFFFF);
        c.drawOval(new RectF(14, 34, 34, 48), f); c.drawOval(new RectF(14, 34, 34, 48), st);
        c.drawOval(new RectF(86, 34, 106, 48), f); c.drawOval(new RectF(86, 34, 106, 48), st);
        f.setColor(0xFFFFD23F);
        c.drawOval(new RectF(32, 14, 44, 32), f); c.drawOval(new RectF(32, 14, 44, 32), st);
        c.drawOval(new RectF(76, 14, 88, 32), f); c.drawOval(new RectF(76, 14, 88, 32), st);

        // Head
        f.setColor(0xFFFFFFFF);
        c.drawOval(new RectF(24, 24, 96, 82), f); c.drawOval(new RectF(24, 24, 96, 82), st);
        if (!sil) {
            f.setColor(INK);
            c.drawCircle(40, 38, 9, f);
            f.setColor(0xFFFFB3C6);
            c.drawOval(new RectF(34, 56, 86, 82), f); c.drawOval(new RectF(34, 56, 86, 82), st);
            f.setColor(INK);
            c.drawCircle(50, 66, 2.8f, f); c.drawCircle(70, 66, 2.8f, f);
        }
        CartoonArt.drawFace(c, f, st, 60, 47, 13, 4.6f, 70, mood, blink, sil, CartoonArt.NOSE_NONE, false, false);
    }

    private static void drawAyam(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood, boolean adult) {
        CartoonArt.drawShadow(c, f, 60, 108, 28, 6);
        CartoonArt.applyBreath(c, sec, 3.6f, 0.03f);
        float flap = (float) Math.sin(sec * 5f) * 10f;
        int bodyCol = adult ? 0xFFFFFFFF : 0xFFFFD23F;
        int wingCol = adult ? 0xFFF2ECE1 : 0xFFFFB703;
        int combCol = adult ? 0xFFE63946 : 0xFFFF9F1C;

        // Feet
        f.setColor(0xFFFF9F1C);
        c.drawRoundRect(new RectF(42, 96, 52, 108), 4, 4, f); c.drawRoundRect(new RectF(42, 96, 52, 108), 4, 4, st);
        c.drawRoundRect(new RectF(68, 96, 78, 108), 4, 4, f); c.drawRoundRect(new RectF(68, 96, 78, 108), 4, 4, st);

        // Crest
        f.setColor(combCol);
        c.drawCircle(52, 20, 7, f); c.drawCircle(52, 20, 7, st);
        c.drawCircle(60, 16, 8, f); c.drawCircle(60, 16, 8, st);
        c.drawCircle(68, 20, 7, f); c.drawCircle(68, 20, 7, st);

        // Wings
        c.save(); c.rotate(-flap, 30, 70);
        f.setColor(wingCol);
        c.drawOval(new RectF(14, 58, 34, 86), f); c.drawOval(new RectF(14, 58, 34, 86), st);
        c.restore();
        c.save(); c.rotate(flap, 90, 70);
        c.drawOval(new RectF(86, 58, 106, 86), f); c.drawOval(new RectF(86, 58, 106, 86), st);
        c.restore();

        // Egg-shaped chubby body+head
        f.setColor(bodyCol);
        c.drawOval(new RectF(24, 24, 96, 100), f); c.drawOval(new RectF(24, 24, 96, 100), st);
        if (!sil) {
            f.setColor(adult ? 0xFFFFF8E8 : 0xFFFFF3B0);
            c.drawOval(new RectF(38, 62, 82, 94), f);
        }
        CartoonArt.drawFace(c, f, st, 60, 50, 14, 4.6f, 66, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
        if (!sil) {
            f.setColor(0xFFFF9F1C);
            Path beak = new Path(); beak.moveTo(52, 57); beak.lineTo(68, 57); beak.lineTo(60, 67); beak.close();
            c.drawPath(beak, f); c.drawPath(beak, st);
        }
    }

    private static void drawBebek(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 28, 6);
        CartoonArt.applyBreath(c, sec, 3.4f, 0.03f);
        f.setColor(0xFFFF8C1A);
        c.drawOval(new RectF(38, 96, 54, 108), f); c.drawOval(new RectF(38, 96, 54, 108), st);
        c.drawOval(new RectF(66, 96, 82, 108), f); c.drawOval(new RectF(66, 96, 82, 108), st);

        drawBody(c, f, st, 0xFFFFD23F, sil ? 0 : 0xFFFFF3B0);
        // Wings
        f.setColor(0xFFFFB703);
        c.drawOval(new RectF(22, 66, 38, 92), f); c.drawOval(new RectF(22, 66, 38, 92), st);
        c.drawOval(new RectF(82, 66, 98, 92), f); c.drawOval(new RectF(82, 66, 98, 92), st);

        // Head
        f.setColor(0xFFFFD23F);
        c.drawOval(new RectF(28, 22, 92, 78), f); c.drawOval(new RectF(28, 22, 92, 78), st);
        CartoonArt.drawFace(c, f, st, 60, 46, 13, 4.5f, 64, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
        if (!sil) {
            f.setColor(0xFFFF8C1A);
            c.drawOval(new RectF(46, 53, 74, 65), f); c.drawOval(new RectF(46, 53, 74, 65), st);
        }
    }

    private static void drawKambing(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 30, 6);
        CartoonArt.applyBreath(c, sec, 3.0f, 0.025f);
        drawBody(c, f, st, 0xFFF5EFE6, sil ? 0 : 0xFFFFFFFF);
        drawPaws(c, f, st, 0xFF7D6B5D);

        // Curved horns
        f.setColor(0xFFD4A373);
        c.drawOval(new RectF(32, 10, 44, 34), f); c.drawOval(new RectF(32, 10, 44, 34), st);
        c.drawOval(new RectF(76, 10, 88, 34), f); c.drawOval(new RectF(76, 10, 88, 34), st);
        // Ears
        f.setColor(0xFFE8DFD1);
        c.drawOval(new RectF(14, 38, 34, 52), f); c.drawOval(new RectF(14, 38, 34, 52), st);
        c.drawOval(new RectF(86, 38, 106, 52), f); c.drawOval(new RectF(86, 38, 106, 52), st);

        // Head + little goatee
        f.setColor(0xFFF5EFE6);
        c.drawOval(new RectF(26, 26, 94, 84), f); c.drawOval(new RectF(26, 26, 94, 84), st);
        if (!sil) {
            Path beard = new Path(); beard.moveTo(54, 82); beard.lineTo(66, 82); beard.lineTo(60, 94); beard.close();
            f.setColor(0xFFD4A373); c.drawPath(beard, f); c.drawPath(beard, st);
        }
        CartoonArt.drawFace(c, f, st, 60, 50, 12, 4.5f, 66, mood, blink, sil, CartoonArt.NOSE_PINK, false, true);
    }

    private static void drawDomba(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 32, 6);
        CartoonArt.applyBreath(c, sec, 2.8f, 0.03f);
        drawPaws(c, f, st, 0xFF4A4063);
        // Fluffy wool cloud body
        f.setColor(0xFFFFFFFF);
        for (int i = 0; i < 8; i++) {
            double a = i * Math.PI * 2 / 8;
            float cx = (float) (60 + Math.cos(a) * 24);
            float cy = (float) (68 + Math.sin(a) * 22);
            c.drawCircle(cx, cy, 18, f);
            c.drawCircle(cx, cy, 18, st);
        }
        c.drawCircle(60, 68, 28, f);

        // Ears
        f.setColor(0xFF4A4063);
        c.drawOval(new RectF(16, 42, 36, 56), f); c.drawOval(new RectF(16, 42, 36, 56), st);
        c.drawOval(new RectF(84, 42, 104, 56), f); c.drawOval(new RectF(84, 42, 104, 56), st);

        // Head
        f.setColor(0xFFFFE5CC);
        c.drawOval(new RectF(32, 34, 88, 86), f); c.drawOval(new RectF(32, 34, 88, 86), st);
        // Fluffy wool tuft on top
        f.setColor(0xFFFFFFFF);
        c.drawCircle(50, 32, 10, f); c.drawCircle(50, 32, 10, st);
        c.drawCircle(62, 29, 11, f); c.drawCircle(62, 29, 11, st);
        c.drawCircle(72, 33, 9, f); c.drawCircle(72, 33, 9, st);
        CartoonArt.drawFace(c, f, st, 60, 56, 11, 4.3f, 70, mood, blink, sil, CartoonArt.NOSE_PINK, false, true);
    }

    private static void drawKuda(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 30, 6);
        CartoonArt.applyBreath(c, sec, 3.2f, 0.03f);
        drawBody(c, f, st, 0xFFC67D46, sil ? 0 : 0xFFE8B48A);
        drawPaws(c, f, st, 0xFF5C3A21);

        // Mane
        f.setColor(0xFF5C3A21);
        c.drawCircle(46, 22, 9, f); c.drawCircle(46, 22, 9, st);
        c.drawCircle(58, 18, 10, f); c.drawCircle(58, 18, 10, st);
        c.drawCircle(70, 22, 9, f); c.drawCircle(70, 22, 9, st);

        // Pointy horse ears
        Path earL = new Path(); earL.moveTo(30, 38); earL.lineTo(28, 16); earL.lineTo(44, 28); earL.close();
        Path earR = new Path(); earR.moveTo(90, 38); earR.lineTo(92, 16); earR.lineTo(76, 28); earR.close();
        f.setColor(0xFFC67D46);
        c.drawPath(earL, f); c.drawPath(earL, st);
        c.drawPath(earR, f); c.drawPath(earR, st);

        // Head
        c.drawOval(new RectF(26, 24, 94, 84), f); c.drawOval(new RectF(26, 24, 94, 84), st);
        if (!sil) {
            f.setColor(0xFFFFF1E0);
            c.drawOval(new RectF(38, 54, 82, 82), f);
            c.drawRoundRect(new RectF(56, 28, 64, 52), 4, 4, f);
            f.setColor(0xFF5C3A21);
            c.drawCircle(50, 64, 3, f); c.drawCircle(70, 64, 3, f);
        }
        CartoonArt.drawFace(c, f, st, 60, 48, 13, 4.5f, 68, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawKatak(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 32, 6);
        CartoonArt.applyBreath(c, sec, 3.8f, 0.04f);
        f.setColor(0xFF5FBF5A);
        c.drawOval(new RectF(26, 90, 50, 106), f); c.drawOval(new RectF(26, 90, 50, 106), st);
        c.drawOval(new RectF(70, 90, 94, 106), f); c.drawOval(new RectF(70, 90, 94, 106), st);

        drawBody(c, f, st, 0xFF5FBF5A, sil ? 0 : 0xFFC7F0A5);
        // Eye bumps on top
        f.setColor(0xFF5FBF5A);
        c.drawCircle(38, 34, 16, f); c.drawCircle(38, 34, 16, st);
        c.drawCircle(82, 34, 16, f); c.drawCircle(82, 34, 16, st);
        c.drawOval(new RectF(20, 34, 100, 82), f); c.drawOval(new RectF(20, 34, 100, 82), st);
        if (!sil) {
            f.setColor(0xFFFFFFFF);
            c.drawCircle(38, 34, 10, f); c.drawCircle(82, 34, 10, f);
        }
        CartoonArt.drawFace(c, f, st, 60, 34, 22, 5.0f, 60, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawSinga(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 30, 6);
        CartoonArt.applyBreath(c, sec, 3.0f, 0.03f);
        drawBody(c, f, st, 0xFFFFD23F, sil ? 0 : 0xFFFFF3B0);
        drawPaws(c, f, st, 0xFFFFD23F);

        // Fluffy scalloped mane
        f.setColor(0xFFD96B27);
        for (int i = 0; i < 12; i++) {
            double a = i * Math.PI * 2 / 12;
            float mx = (float) (60 + Math.cos(a) * 32);
            float my = (float) (48 + Math.sin(a) * 30);
            c.drawCircle(mx, my, 12, f);
            c.drawCircle(mx, my, 12, st);
        }
        c.drawCircle(60, 48, 32, f);

        // Face circle
        f.setColor(0xFFFFD23F);
        c.drawCircle(60, 48, 26, f); c.drawCircle(60, 48, 26, st);
        if (!sil) {
            f.setColor(0xFFFFF3B0);
            c.drawOval(new RectF(44, 50, 76, 70), f);
        }
        CartoonArt.drawFace(c, f, st, 60, 44, 11, 4.3f, 59, mood, blink, sil, CartoonArt.NOSE_TRI, true, true);
    }

    private static void drawHarimau(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 30, 6);
        CartoonArt.applyBreath(c, sec, 3.2f, 0.03f);
        drawBody(c, f, st, 0xFFFF8C1A, sil ? 0 : 0xFFFFFFFF);
        drawPaws(c, f, st, 0xFFFF8C1A);
        drawRoundEars(c, f, st, 28, 92, 28, 11, 0xFFFF8C1A, 0xFFFFE0B2, sil);

        f.setColor(0xFFFF8C1A);
        c.drawOval(new RectF(22, 24, 98, 82), f); c.drawOval(new RectF(22, 24, 98, 82), st);
        if (!sil) {
            f.setColor(0xFFFFFFFF);
            c.drawOval(new RectF(40, 52, 80, 78), f);
            float oldW = st.getStrokeWidth(); st.setStrokeWidth(3.4f);
            c.drawLine(60, 26, 60, 38, st);
            c.drawLine(48, 28, 53, 38, st);
            c.drawLine(72, 28, 67, 38, st);
            c.drawLine(25, 52, 36, 55, st); c.drawLine(24, 60, 35, 60, st);
            c.drawLine(95, 52, 84, 55, st); c.drawLine(96, 60, 85, 60, st);
            st.setStrokeWidth(oldW);
        }
        CartoonArt.drawFace(c, f, st, 60, 49, 13, 4.6f, 64, mood, blink, sil, CartoonArt.NOSE_TRI, true, true);
    }

    private static void drawGajah(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 34, 6);
        CartoonArt.applyBreath(c, sec, 2.5f, 0.025f);
        drawBody(c, f, st, 0xFF8ECAE6, sil ? 0 : 0xFFBEE3F4);
        drawPaws(c, f, st, 0xFF70B5D6);

        // Big floppy ears
        f.setColor(0xFF8ECAE6);
        c.drawOval(new RectF(6, 26, 38, 76), f); c.drawOval(new RectF(6, 26, 38, 76), st);
        c.drawOval(new RectF(82, 26, 114, 76), f); c.drawOval(new RectF(82, 26, 114, 76), st);
        if (!sil) {
            f.setColor(0xFFFFB3C6);
            c.drawOval(new RectF(13, 35, 32, 68), f);
            c.drawOval(new RectF(88, 35, 107, 68), f);
        }

        // Head
        f.setColor(0xFF8ECAE6);
        c.drawOval(new RectF(26, 20, 94, 78), f); c.drawOval(new RectF(26, 20, 94, 78), st);
        CartoonArt.drawFace(c, f, st, 60, 44, 14, 4.6f, 58, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);

        // Curved trunk
        Path trunk = new Path();
        trunk.moveTo(54, 52);
        trunk.quadTo(52, 82, 68, 82);
        trunk.quadTo(74, 82, 72, 74);
        float oldW = st.getStrokeWidth();
        st.setStrokeWidth(14f); st.setColor(INK); c.drawPath(trunk, st);
        st.setStrokeWidth(8f); st.setColor(0xFF70B5D6); c.drawPath(trunk, st);
        st.setColor(INK); st.setStrokeWidth(oldW);
    }

    private static void drawMonyet(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 30, 6);
        CartoonArt.applyBreath(c, sec, 3.6f, 0.03f);
        float wag = (float) Math.sin(sec * 4f) * 6f;
        Path tail = new Path(); tail.moveTo(84, 88); tail.quadTo(112, 80 + wag, 104, 56 + wag);
        float oldW = st.getStrokeWidth();
        st.setStrokeWidth(8f); c.drawPath(tail, st);
        st.setStrokeWidth(4.5f); st.setColor(0xFF8B5A2B); c.drawPath(tail, st);
        st.setColor(INK); st.setStrokeWidth(oldW);

        drawBody(c, f, st, 0xFF8B5A2B, sil ? 0 : 0xFFFFE0B2);
        drawPaws(c, f, st, 0xFFFFE0B2);
        drawRoundEars(c, f, st, 20, 100, 50, 12, 0xFF8B5A2B, 0xFFFFE0B2, sil);

        f.setColor(0xFF8B5A2B);
        c.drawOval(new RectF(24, 24, 96, 80), f); c.drawOval(new RectF(24, 24, 96, 80), st);
        if (!sil) {
            f.setColor(0xFFFFE0B2);
            c.drawCircle(47, 46, 13, f); c.drawCircle(73, 46, 13, f);
            c.drawOval(new RectF(36, 52, 84, 78), f);
        }
        CartoonArt.drawFace(c, f, st, 60, 48, 13, 4.5f, 65, mood, blink, sil, CartoonArt.NOSE_DARK, false, true);
    }

    private static void drawKelinci(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 28, 6);
        CartoonArt.applyBreath(c, sec, 3.8f, 0.035f);
        drawBody(c, f, st, 0xFFFFFFFF, sil ? 0 : 0xFFFFE5F0);
        drawPaws(c, f, st, 0xFFFFFFFF);

        // Tall bunny ears
        f.setColor(0xFFFFFFFF);
        c.drawOval(new RectF(34, 6, 52, 46), f); c.drawOval(new RectF(34, 6, 52, 46), st);
        c.drawOval(new RectF(68, 6, 86, 46), f); c.drawOval(new RectF(68, 6, 86, 46), st);
        if (!sil) {
            f.setColor(0xFFFFB3C6);
            c.drawOval(new RectF(38, 12, 48, 40), f);
            c.drawOval(new RectF(72, 12, 82, 40), f);
        }
        f.setColor(0xFFFFFFFF);
        c.drawOval(new RectF(26, 32, 94, 86), f); c.drawOval(new RectF(26, 32, 94, 86), st);
        CartoonArt.drawFace(c, f, st, 60, 56, 13, 4.6f, 70, mood, blink, sil, CartoonArt.NOSE_PINK, true, true);
    }

    private static void drawPanda(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 32, 6);
        CartoonArt.applyBreath(c, sec, 2.9f, 0.03f);
        drawBody(c, f, st, 0xFFFFFFFF, 0);
        // Dark shoulder band
        f.setColor(INK);
        c.drawOval(new RectF(34, 64, 86, 80), f);
        drawPaws(c, f, st, INK);
        drawRoundEars(c, f, st, 28, 92, 28, 12, INK, 0, sil);

        f.setColor(0xFFFFFFFF);
        c.drawOval(new RectF(22, 26, 98, 82), f); c.drawOval(new RectF(22, 26, 98, 82), st);
        if (!sil) {
            f.setColor(INK);
            c.drawOval(new RectF(34, 42, 52, 62), f);
            c.drawOval(new RectF(68, 42, 86, 62), f);
            // White sclera inside dark patches so eyes pop
            f.setColor(0xFFFFFFFF);
            c.drawCircle(44, 51, 6, f);
            c.drawCircle(76, 51, 6, f);
        }
        CartoonArt.drawFace(c, f, st, 60, 51, 16, 3.8f, 67, mood, blink, sil, CartoonArt.NOSE_DARK, true, true);
    }

    private static void drawTikus(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 28, 6);
        CartoonArt.applyBreath(c, sec, 4.0f, 0.03f);
        float wag = (float) Math.sin(sec * 5f) * 5f;
        Path tail = new Path(); tail.moveTo(82, 92); tail.quadTo(110, 94 + wag, 106, 72 + wag);
        float oldW = st.getStrokeWidth();
        st.setStrokeWidth(4.5f); st.setColor(0xFFFF8FAB); c.drawPath(tail, st);
        st.setColor(INK); st.setStrokeWidth(oldW);

        drawBody(c, f, st, 0xFFC5CDD8, sil ? 0 : 0xFFF1F5F9);
        drawPaws(c, f, st, 0xFFFFB3C6);
        drawRoundEars(c, f, st, 26, 94, 32, 16, 0xFFC5CDD8, 0xFFFFB3C6, sil);

        f.setColor(0xFFC5CDD8);
        c.drawOval(new RectF(26, 32, 94, 84), f); c.drawOval(new RectF(26, 32, 94, 84), st);
        if (!sil) {
            st.setStrokeWidth(2.2f);
            c.drawLine(16, 58, 32, 60, st); c.drawLine(16, 65, 32, 64, st);
            c.drawLine(104, 58, 88, 60, st); c.drawLine(104, 65, 88, 64, st);
            st.setStrokeWidth(oldW);
        }
        CartoonArt.drawFace(c, f, st, 60, 54, 12, 4.4f, 68, mood, blink, sil, CartoonArt.NOSE_PINK, false, true);
    }

    private static void drawBeruang(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 32, 6);
        CartoonArt.applyBreath(c, sec, 2.8f, 0.03f);
        drawBody(c, f, st, 0xFF9E6B43, sil ? 0 : 0xFFE6BE9A);
        drawPaws(c, f, st, 0xFF7D502E);
        drawRoundEars(c, f, st, 28, 92, 28, 12, 0xFF9E6B43, 0xFFE6BE9A, sil);

        f.setColor(0xFF9E6B43);
        c.drawOval(new RectF(22, 24, 98, 82), f); c.drawOval(new RectF(22, 24, 98, 82), st);
        if (!sil) {
            f.setColor(0xFFE6BE9A);
            c.drawOval(new RectF(42, 52, 78, 76), f);
        }
        CartoonArt.drawFace(c, f, st, 60, 49, 14, 4.6f, 65, mood, blink, sil, CartoonArt.NOSE_DARK, false, true);
    }

    private static void drawBurung(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 24, 5);
        c.translate(0, (float) Math.sin(sec * 4.5f) * 3.5f);
        float flap = (float) Math.sin(sec * 8f) * 14f;

        f.setColor(0xFFFF9F1C);
        c.drawLine(48, 94, 48, 104, st); c.drawLine(72, 94, 72, 104, st);
        // Crest
        f.setColor(0xFF4CC9F0);
        c.drawOval(new RectF(54, 12, 66, 28), f); c.drawOval(new RectF(54, 12, 66, 28), st);

        // Wings
        c.save(); c.rotate(-flap, 28, 64);
        f.setColor(0xFF2FA8CC);
        c.drawOval(new RectF(10, 52, 34, 78), f); c.drawOval(new RectF(10, 52, 34, 78), st);
        c.restore();
        c.save(); c.rotate(flap, 92, 64);
        c.drawOval(new RectF(86, 52, 110, 78), f); c.drawOval(new RectF(86, 52, 110, 78), st);
        c.restore();

        f.setColor(0xFF4CC9F0);
        c.drawOval(new RectF(24, 24, 96, 96), f); c.drawOval(new RectF(24, 24, 96, 96), st);
        if (!sil) {
            f.setColor(0xFFE6F7FF);
            c.drawOval(new RectF(38, 60, 82, 90), f);
        }
        CartoonArt.drawFace(c, f, st, 60, 48, 13, 4.5f, 64, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
        if (!sil) {
            f.setColor(0xFFFF9F1C);
            Path beak = new Path(); beak.moveTo(53, 54); beak.lineTo(67, 54); beak.lineTo(60, 64); beak.close();
            c.drawPath(beak, f); c.drawPath(beak, st);
        }
    }

    private static void drawBurungHantu(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 28, 6);
        CartoonArt.applyBreath(c, sec, 2.8f, 0.025f);
        // Ear tufts
        Path tL = new Path(); tL.moveTo(28, 36); tL.lineTo(24, 16); tL.lineTo(44, 28); tL.close();
        Path tR = new Path(); tR.moveTo(92, 36); tR.lineTo(96, 16); tR.lineTo(76, 28); tR.close();
        f.setColor(0xFF8B5E83);
        c.drawPath(tL, f); c.drawPath(tL, st);
        c.drawPath(tR, f); c.drawPath(tR, st);

        f.setColor(0xFF8B5E83);
        c.drawOval(new RectF(22, 22, 98, 100), f); c.drawOval(new RectF(22, 22, 98, 100), st);
        if (!sil) {
            f.setColor(0xFFF3E8EE);
            c.drawOval(new RectF(34, 62, 86, 94), f);
            // Big owl eye discs
            f.setColor(0xFFFFFFFF);
            c.drawCircle(44, 48, 13, f); c.drawCircle(44, 48, 13, st);
            c.drawCircle(76, 48, 13, f); c.drawCircle(76, 48, 13, st);
        }
        CartoonArt.drawFace(c, f, st, 60, 48, 16, 4.8f, 66, mood, blink, sil, CartoonArt.NOSE_NONE, false, false);
        if (!sil) {
            f.setColor(0xFFFFB703);
            Path beak = new Path(); beak.moveTo(55, 53); beak.lineTo(65, 53); beak.lineTo(60, 62); beak.close();
            c.drawPath(beak, f); c.drawPath(beak, st);
        }
    }

    private static void drawLebah(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 24, 5);
        c.translate(0, (float) Math.sin(sec * 7f) * 4f);
        float flap = (float) Math.sin(sec * 24f) * 12f;

        // Translucent wings
        c.save(); c.rotate(-flap, 40, 46);
        f.setColor(0xCCE6F7FF);
        c.drawOval(new RectF(16, 24, 48, 54), f); c.drawOval(new RectF(16, 24, 48, 54), st);
        c.restore();
        c.save(); c.rotate(flap, 80, 46);
        c.drawOval(new RectF(72, 24, 104, 54), f); c.drawOval(new RectF(72, 24, 104, 54), st);
        c.restore();

        // Antennae
        c.drawLine(50, 32, 42, 16, st); c.drawLine(70, 32, 78, 16, st);
        f.setColor(INK);
        c.drawCircle(42, 16, 4, f); c.drawCircle(78, 16, 4, f);

        // Striped chubby bee body
        f.setColor(0xFFFFD23F);
        c.drawOval(new RectF(26, 30, 94, 96), f); c.drawOval(new RectF(26, 30, 94, 96), st);
        if (!sil) {
            f.setColor(INK);
            c.drawRoundRect(new RectF(32, 74, 88, 82), 4, 4, f);
            c.drawRoundRect(new RectF(36, 86, 84, 92), 4, 4, f);
        }
        CartoonArt.drawFace(c, f, st, 60, 52, 13, 4.6f, 64, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawBayi(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 28, 6);
        CartoonArt.applyBreath(c, sec, 3.2f, 0.03f);
        // Cute pastel onesie body
        drawBody(c, f, st, 0xFF7FD0FF, sil ? 0 : 0xFFFFFFFF);
        drawPaws(c, f, st, 0xFFFFD5B8);

        // Ears
        f.setColor(0xFFFFD5B8);
        c.drawCircle(22, 54, 8, f); c.drawCircle(22, 54, 8, st);
        c.drawCircle(98, 54, 8, f); c.drawCircle(98, 54, 8, st);

        // Chubby baby head
        c.drawOval(new RectF(24, 22, 96, 82), f); c.drawOval(new RectF(24, 22, 96, 82), st);
        // Curly hair swirl on top
        if (!sil) {
            Path curl = new Path();
            curl.moveTo(58, 22);
            curl.quadTo(54, 10, 66, 12);
            curl.quadTo(72, 16, 62, 22);
            c.drawPath(curl, st);
        }
        CartoonArt.drawFace(c, f, st, 60, 50, 13, 4.8f, 65, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawZebra(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 30, 6);
        CartoonArt.applyBreath(c, sec, 3.2f, 0.03f);
        drawBody(c, f, st, 0xFFFFFFFF, 0);
        drawPaws(c, f, st, INK);

        // Upright striped mane
        f.setColor(INK);
        c.drawRoundRect(new RectF(52, 12, 68, 28), 6, 6, f);
        Path earL = new Path(); earL.moveTo(30, 38); earL.lineTo(28, 16); earL.lineTo(44, 28); earL.close();
        Path earR = new Path(); earR.moveTo(90, 38); earR.lineTo(92, 16); earR.lineTo(76, 28); earR.close();
        f.setColor(0xFFFFFFFF);
        c.drawPath(earL, f); c.drawPath(earL, st);
        c.drawPath(earR, f); c.drawPath(earR, st);

        c.drawOval(new RectF(26, 24, 94, 84), f); c.drawOval(new RectF(26, 24, 94, 84), st);
        if (!sil) {
            float oldW = st.getStrokeWidth(); st.setStrokeWidth(3.6f);
            c.drawLine(34, 34, 46, 38, st); c.drawLine(86, 34, 74, 38, st);
            c.drawLine(28, 48, 38, 50, st); c.drawLine(92, 48, 82, 50, st);
            st.setStrokeWidth(oldW);
            f.setColor(0xFFE8E8F0);
            c.drawOval(new RectF(40, 56, 80, 82), f);
        }
        CartoonArt.drawFace(c, f, st, 60, 48, 13, 4.5f, 68, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawJerapah(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 28, 6);
        CartoonArt.applyBreath(c, sec, 2.8f, 0.025f);
        // Long neck + body
        f.setColor(0xFFFFD23F);
        c.drawOval(new RectF(34, 68, 86, 106), f); c.drawOval(new RectF(34, 68, 86, 106), st);
        c.drawRoundRect(new RectF(48, 46, 72, 82), 8, 8, f); c.drawRoundRect(new RectF(48, 46, 72, 82), 8, 8, st);
        drawPaws(c, f, st, 0xFFD96B27);

        // Ossicones (giraffe horns)
        c.drawLine(46, 24, 42, 12, st); c.drawLine(74, 24, 78, 12, st);
        f.setColor(0xFFD96B27);
        c.drawCircle(42, 12, 4.5f, f); c.drawCircle(42, 12, 4.5f, st);
        c.drawCircle(78, 12, 4.5f, f); c.drawCircle(78, 12, 4.5f, st);

        // Ears
        f.setColor(0xFFFFD23F);
        c.drawOval(new RectF(20, 26, 36, 38), f); c.drawOval(new RectF(20, 26, 36, 38), st);
        c.drawOval(new RectF(84, 26, 100, 38), f); c.drawOval(new RectF(84, 26, 100, 38), st);

        // Head
        c.drawOval(new RectF(30, 20, 90, 64), f); c.drawOval(new RectF(30, 20, 90, 64), st);
        if (!sil) {
            f.setColor(0xFFD96B27);
            c.drawCircle(42, 86, 6, f);
            c.drawCircle(74, 90, 5, f);
            c.drawCircle(58, 70, 5, f);
            f.setColor(0xFFFFF3B0);
            c.drawOval(new RectF(42, 42, 78, 62), f);
        }
        CartoonArt.drawFace(c, f, st, 60, 36, 12, 4.2f, 52, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawIkan(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood, boolean clown) {
        c.translate(0, (float) Math.sin(sec * 3.5f) * 4f);
        float swish = (float) Math.sin(sec * 7f) * 5f;
        int col = clown ? 0xFFFF8C1A : 0xFF4CC9F0;

        Path tail = new Path();
        tail.moveTo(82, 60); tail.lineTo(110, 38 + swish); tail.lineTo(110, 82 + swish); tail.close();
        f.setColor(col);
        c.drawPath(tail, f); c.drawPath(tail, st);

        // Top fin
        Path fin = new Path(); fin.moveTo(46, 32); fin.quadTo(60, 14, 74, 32); fin.close();
        c.drawPath(fin, f); c.drawPath(fin, st);

        // Body
        c.drawOval(new RectF(16, 30, 92, 90), f); c.drawOval(new RectF(16, 30, 92, 90), st);
        if (!sil && clown) {
            f.setColor(0xFFFFFFFF);
            c.drawRoundRect(new RectF(50, 32, 62, 88), 4, 4, f);
            c.drawRoundRect(new RectF(72, 38, 82, 82), 4, 4, f);
        }
        if (!sil) {
            f.setColor(0xAAFFFFFF);
            c.drawCircle(12, 34, 4, f);
            c.drawCircle(8, 22, 2.5f, f);
        }
        CartoonArt.drawFace(c, f, st, 38, 54, 10, 4.2f, 68, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawGurita(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        c.translate(0, (float) Math.sin(sec * 3.0f) * 3.5f);
        f.setColor(0xFFFF5D8F);
        for (int i = 0; i < 4; i++) {
            float tx = 28 + i * 21;
            float wave = (float) Math.sin(sec * 5f + i) * 3f;
            c.drawRoundRect(new RectF(tx - 6, 68, tx + 6, 106 + wave), 6, 6, f);
            c.drawRoundRect(new RectF(tx - 6, 68, tx + 6, 106 + wave), 6, 6, st);
        }
        c.drawOval(new RectF(22, 18, 98, 84), f); c.drawOval(new RectF(22, 18, 98, 84), st);
        if (!sil) {
            f.setColor(0x55FFFFFF);
            c.drawCircle(38, 32, 6, f);
        }
        CartoonArt.drawFace(c, f, st, 60, 50, 14, 4.8f, 64, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawKepiting(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        c.translate((float) Math.sin(sec * 4f) * 4f, 0);
        f.setColor(0xFFE63946);
        // Little legs
        for (int i = 0; i < 2; i++) {
            float y = 78 + i * 10;
            c.drawLine(24, y, 10, y + 6, st);
            c.drawLine(96, y, 110, y + 6, st);
        }
        // Snapping claws
        c.drawCircle(24, 34, 13, f); c.drawCircle(24, 34, 13, st);
        c.drawCircle(96, 34, 13, f); c.drawCircle(96, 34, 13, st);
        // Body
        c.drawOval(new RectF(20, 44, 100, 98), f); c.drawOval(new RectF(20, 44, 100, 98), st);
        CartoonArt.drawFace(c, f, st, 60, 64, 14, 4.6f, 78, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawKuraKura(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.drawShadow(c, f, 60, 108, 34, 6);
        CartoonArt.applyBreath(c, sec, 2.4f, 0.02f);
        // Legs
        f.setColor(0xFF9AD77A);
        c.drawRoundRect(new RectF(24, 82, 40, 98), 6, 6, f); c.drawRoundRect(new RectF(24, 82, 40, 98), 6, 6, st);
        c.drawRoundRect(new RectF(66, 82, 82, 98), 6, 6, f); c.drawRoundRect(new RectF(66, 82, 82, 98), 6, 6, st);
        // Head
        c.drawCircle(92, 64, 16, f); c.drawCircle(92, 64, 16, st);
        // Shell
        f.setColor(0xFF3FB950);
        c.drawArc(new RectF(14, 28, 86, 100), 180, 180, true, f);
        c.drawArc(new RectF(14, 28, 86, 100), 180, 180, true, st);
        if (!sil) {
            f.setColor(0xFF2D8A3E);
            c.drawCircle(42, 48, 7, f); c.drawCircle(58, 44, 7, f); c.drawCircle(50, 58, 6, f);
        }
        CartoonArt.drawFace(c, f, st, 92, 60, 6, 3.2f, 68, mood, blink, sil, CartoonArt.NOSE_NONE, false, false);
    }

    private static void drawLumbaLumba(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        c.translate(0, (float) Math.sin(sec * 3.5f) * 4f);
        f.setColor(0xFF4CC9F0);
        Path fin = new Path(); fin.moveTo(52, 34); fin.lineTo(66, 14); fin.lineTo(74, 36); fin.close();
        c.drawPath(fin, f); c.drawPath(fin, st);
        Path tail = new Path(); tail.moveTo(92, 66); tail.lineTo(112, 48); tail.lineTo(108, 76); tail.close();
        c.drawPath(tail, f); c.drawPath(tail, st);
        c.drawOval(new RectF(16, 34, 100, 88), f); c.drawOval(new RectF(16, 34, 100, 88), st);
        if (!sil) {
            f.setColor(0xFFE6F7FF);
            c.drawOval(new RectF(26, 64, 86, 84), f);
        }
        CartoonArt.drawFace(c, f, st, 38, 52, 9, 4.0f, 66, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawPaus(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        c.translate(0, (float) Math.sin(sec * 2.8f) * 3.5f);
        if (!sil) {
            float oldW = st.getStrokeWidth(); st.setStrokeWidth(3.5f); st.setColor(0xFF7FD0FF);
            c.drawLine(58, 36, 48, 16, st); c.drawLine(62, 36, 72, 16, st);
            st.setColor(INK); st.setStrokeWidth(oldW);
        }
        f.setColor(0xFF2F80ED);
        Path tail = new Path(); tail.moveTo(94, 68); tail.lineTo(114, 46); tail.lineTo(112, 82); tail.close();
        c.drawPath(tail, f); c.drawPath(tail, st);
        c.drawOval(new RectF(12, 36, 100, 96), f); c.drawOval(new RectF(12, 36, 100, 96), st);
        if (!sil) {
            f.setColor(0xFFBFE8FF);
            c.drawOval(new RectF(22, 72, 84, 92), f);
        }
        CartoonArt.drawFace(c, f, st, 38, 58, 10, 4.2f, 70, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawHiu(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        c.translate(0, (float) Math.sin(sec * 3.6f) * 3.5f);
        f.setColor(0xFF7B9EB8);
        Path fin = new Path(); fin.moveTo(50, 34); fin.lineTo(66, 12); fin.lineTo(74, 34); fin.close();
        c.drawPath(fin, f); c.drawPath(fin, st);
        Path tail = new Path(); tail.moveTo(92, 62); tail.lineTo(114, 38); tail.lineTo(110, 84); tail.close();
        c.drawPath(tail, f); c.drawPath(tail, st);
        c.drawOval(new RectF(14, 32, 100, 88), f); c.drawOval(new RectF(14, 32, 100, 88), st);
        if (!sil) {
            f.setColor(0xFFFFFFFF);
            c.drawOval(new RectF(22, 62, 88, 84), f);
        }
        CartoonArt.drawFace(c, f, st, 38, 52, 10, 4.2f, 66, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawBuntal(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        CartoonArt.applyBreath(c, sec, 4.0f, 0.05f);
        f.setColor(0xFFFFB703);
        for (int i = 0; i < 10; i++) {
            double a = i * Math.PI * 2 / 10;
            float sx = (float) (60 + Math.cos(a) * 39);
            float sy = (float) (60 + Math.sin(a) * 39);
            c.drawCircle(sx, sy, 6, f); c.drawCircle(sx, sy, 6, st);
        }
        f.setColor(0xFFFFD23F);
        c.drawCircle(60, 60, 35, f); c.drawCircle(60, 60, 35, st);
        if (!sil) {
            f.setColor(0xFFFFF3B0);
            c.drawOval(new RectF(36, 62, 84, 90), f);
        }
        CartoonArt.drawFace(c, f, st, 60, 52, 13, 4.6f, 68, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawCumi(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        c.translate(0, (float) Math.sin(sec * 3.4f) * 4f);
        f.setColor(0xFFA77FF2);
        for (int i = 0; i < 4; i++) {
            float tx = 36 + i * 16;
            c.drawRoundRect(new RectF(tx - 5, 72, tx + 5, 106), 5, 5, f);
            c.drawRoundRect(new RectF(tx - 5, 72, tx + 5, 106), 5, 5, st);
        }
        Path head = new Path();
        head.moveTo(60, 14);
        head.lineTo(90, 50);
        head.lineTo(84, 78);
        head.lineTo(36, 78);
        head.lineTo(30, 50);
        head.close();
        c.drawPath(head, f); c.drawPath(head, st);
        CartoonArt.drawFace(c, f, st, 60, 56, 12, 4.2f, 68, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }

    private static void drawUdang(Canvas c, Paint f, Paint st, float sec, boolean blink, boolean sil, CartoonArt.Mood mood) {
        c.translate(0, (float) Math.sin(sec * 3.8f) * 3.5f);
        if (!sil) {
            c.drawLine(32, 46, 14, 30, st);
            c.drawLine(32, 52, 12, 44, st);
        }
        f.setColor(0xFFFF7A59);
        c.drawOval(new RectF(26, 36, 86, 82), f); c.drawOval(new RectF(26, 36, 86, 82), st);
        c.drawOval(new RectF(66, 64, 96, 94), f); c.drawOval(new RectF(66, 64, 96, 94), st);
        Path tail = new Path(); tail.moveTo(86, 86); tail.lineTo(106, 96); tail.lineTo(94, 106); tail.close();
        c.drawPath(tail, f); c.drawPath(tail, st);
        CartoonArt.drawFace(c, f, st, 46, 52, 9, 3.8f, 64, mood, blink, sil, CartoonArt.NOSE_NONE, false, true);
    }
}
