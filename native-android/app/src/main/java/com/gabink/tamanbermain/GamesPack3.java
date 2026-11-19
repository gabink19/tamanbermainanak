package com.gabink.tamanbermain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamesPack3 {

    /* 16. Siapa Naik Apa? (Profesi) */
    public static class ProfesiGame extends BaseGameView {
        private int pickedIdx = -1, left = 3;
        private final String[][] jobs = {
            {"dokter", "🧑‍⚕️", "Dokter", "ambulans", "🚑"},
            {"polisi", "👮", "Polisi", "mobilpolisi", "🚓"},
            {"damkar", "🧑‍🚒", "Pemadam", "pemadam", "🚒"},
            {"petani", "🧑‍🌾", "Petani", "traktor", "🚜"},
            {"pilot", "🧑‍✈️", "Pilot", "pesawat", "✈️"},
            {"astronot", "🧑‍🚀", "Astronot", "roket", "🚀"},
        };

        public ProfesiGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            pickedIdx = -1;
            List<Integer> idxs = new ArrayList<>();
            for (int i = 0; i < jobs.length; i++) idxs.add(i);
            Collections.shuffle(idxs);
            List<Integer> sel = idxs.subList(0, 3);
            left = 3;

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            LinearLayout topRow = new LinearLayout(getContext());
            topRow.setOrientation(LinearLayout.HORIZONTAL);
            topRow.setGravity(Gravity.CENTER);

            LinearLayout botRow = new LinearLayout(getContext());
            botRow.setOrientation(LinearLayout.HORIZONTAL);
            botRow.setGravity(Gravity.CENTER);
            botRow.setPadding(0, UiHelper.dp(getContext(), 32), 0, 0);

            List<TextView> pViews = new ArrayList<>();
            for (int i : sel) {
                TextView pv = new TextView(getContext());
                pv.setText(jobs[i][1] + "\n" + jobs[i][2]);
                pv.setTextSize(16);
                pv.setTextColor(0xFF2A2140);
                pv.setTypeface(null, Typeface.BOLD);
                pv.setGravity(Gravity.CENTER);
                pv.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFFAF0, 22, 3, 5));
                pv.setTag(i);
                int w = UiHelper.dp(getContext(), 104), h = UiHelper.dp(getContext(), 100);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, h);
                lp.setMargins(UiHelper.dp(getContext(), 6), 0, UiHelper.dp(getContext(), 6), 0);
                pv.setOnClickListener(v -> {
                    if (pv.getAlpha() < 0.5f) return;
                    pickedIdx = i;
                    for (TextView tv : pViews) {
                        if (tv.getAlpha() > 0.5f)
                            tv.setBackground(UiHelper.toyBox3D(getContext(), ((int) tv.getTag() == i) ? 0xFFFFD23F : 0xFFFFFAF0, 22, 3, 5));
                    }
                    UiHelper.bounce(pv);
                    host.sfx().tap();
                });
                pViews.add(pv);
                topRow.addView(pv, lp);
            }

            List<Integer> botSel = new ArrayList<>(sel);
            Collections.shuffle(botSel);
            for (int i : botSel) {
                FrameLayout cv = new FrameLayout(getContext());
                cv.setBackground(UiHelper.toyBox3D(getContext(), 0xFF4CC9F0, 22, 3, 5));
                int pad = UiHelper.dp(getContext(), 10);
                cv.setPadding(pad, pad, pad, pad);
                SpriteView sv = new SpriteView(getContext(), jobs[i][3]);
                cv.addView(sv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                int sz = UiHelper.dp(getContext(), 100);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sz, sz);
                lp.setMargins(UiHelper.dp(getContext(), 8), 0, UiHelper.dp(getContext(), 8), 0);
                cv.setOnClickListener(v -> {
                    if (cv.getAlpha() < 0.5f) return;
                    if (pickedIdx == -1) {
                        UiHelper.bounce(cv);
                        host.sfx().playReal(jobs[i][3]);
                        return;
                    }
                    if (pickedIdx == i) {
                        host.sfx().good();
                        host.sfx().playReal(jobs[i][3]);
                        burstView(cv, null, 24);
                        cv.setAlpha(0.3f);
                        for (TextView tv : pViews) if ((int) tv.getTag() == i) tv.setAlpha(0.3f);
                        pickedIdx = -1;
                        left--;
                        if (left == 0) handler.postDelayed(() -> host.celebrate(this::nextRound, null), 900);
                    } else {
                        UiHelper.shake(cv);
                        host.sfx().wrong();
                        host.sfx().playVoice("ups");
                    }
                });
                botRow.addView(cv, lp);
            }

            col.addView(topRow);
            col.addView(botRow);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("profesi_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice("profesi_intro"); }
    }

    /* 17. Masukkan ke Kotak (Sortir Buah vs Sayur) */
    public static class SortirGame extends BaseGameView {
        private int left = 0, picked = -1;

        public SortirGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            picked = -1;
            List<GameData.Item> items = new ArrayList<>();
            for (GameData.Item it : GameData.ITEMS.values()) {
                if ("fruit".equals(it.cat) || "veg".equals(it.cat)) items.add(it);
            }
            Collections.shuffle(items);
            List<GameData.Item> round = items.subList(0, 4);
            left = round.size();

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            LinearLayout tray = new LinearLayout(getContext());
            tray.setOrientation(LinearLayout.HORIZONTAL);
            tray.setGravity(Gravity.CENTER);
            List<FrameLayout> tvs = new ArrayList<>();
            for (int i = 0; i < round.size(); i++) {
                final int idx = i;
                GameData.Item it = round.get(i);
                FrameLayout tv = new FrameLayout(getContext());
                int pad = UiHelper.dp(getContext(), 8);
                tv.setPadding(pad, pad, pad, pad);
                tv.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFFFFF, 20, 3, 4));
                tv.addView(new SpriteView(getContext(), it.key), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                int sz = UiHelper.dp(getContext(), 76);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sz, sz);
                lp.setMargins(UiHelper.dp(getContext(), 5), 0, UiHelper.dp(getContext(), 5), 0);
                tv.setOnClickListener(v -> {
                    if (tv.getVisibility() != VISIBLE) return;
                    picked = idx;
                    for (int j = 0; j < tvs.size(); j++) {
                        tvs.get(j).setBackground(UiHelper.toyBox3D(getContext(), j == idx ? 0xFFFFD23F : 0xFFFFFFFF, 20, 3, 4));
                    }
                    UiHelper.bounce(tv);
                    host.sfx().playVoice("n_" + it.key);
                });
                tvs.add(tv);
                tray.addView(tv, lp);
            }

            LinearLayout boxes = new LinearLayout(getContext());
            boxes.setOrientation(LinearLayout.HORIZONTAL);
            boxes.setGravity(Gravity.CENTER);
            boxes.setPadding(0, UiHelper.dp(getContext(), 36), 0, 0);

            String[][] cats = {{"fruit", "🍎", "Buah", "0xFFFF9FBF"}, {"veg", "🥕", "Sayur", "0xFF9FDC84"}};
            int[] bCols = {0xFFFF9FBF, 0xFF9FDC84};
            for (int b = 0; b < 2; b++) {
                final String catKey = cats[b][0];
                final int bc = bCols[b];
                TextView box = new TextView(getContext());
                box.setText(cats[b][1] + "\n" + cats[b][2]);
                box.setTextSize(22);
                box.setTextColor(0xFF2A2140);
                box.setTypeface(null, Typeface.BOLD);
                box.setGravity(Gravity.CENTER);
                box.setBackground(UiHelper.toyBox3D(getContext(), bc, 24, 4, 6));
                int bw = UiHelper.dp(getContext(), 145), bh = UiHelper.dp(getContext(), 130);
                LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(bw, bh);
                blp.setMargins(UiHelper.dp(getContext(), 10), 0, UiHelper.dp(getContext(), 10), 0);
                box.setOnClickListener(v -> {
                    if (picked < 0) return;
                    GameData.Item it = round.get(picked);
                    if (it.cat.equals(catKey)) {
                        tvs.get(picked).setVisibility(INVISIBLE);
                        picked = -1;
                        host.sfx().good();
                        UiHelper.bounce(box);
                        burstView(box, null, 20);
                        left--;
                        if (left == 0) handler.postDelayed(() -> host.celebrate(this::nextRound, null), 700);
                    } else {
                        UiHelper.shake(box);
                        host.sfx().wrong();
                        host.sfx().playVoice("ups");
                    }
                });
                boxes.addView(box, blp);
            }

            col.addView(tray);
            col.addView(boxes);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("sortir_buahsayur");
        }
        @Override public void onReplay() { host.sfx().playVoice("sortir_buahsayur"); }
    }

    /* 18. Besar dan Kecil */
    public static class BesarKecilGame extends BaseGameView {
        private int picked = -1, left = 4;

        public BesarKecilGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            picked = -1; left = 4;
            String[] pool = {"apel", "gajah", "mobil", "semangka", "kucing", "beruang"};
            String e1 = pool[rnd.nextInt(pool.length)];
            String e2 = pool[(rnd.nextInt(pool.length - 1) + 1) % pool.length];
            String[][] items = {{e1, "besar"}, {e1, "kecil"}, {e2, "besar"}, {e2, "kecil"}};

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            LinearLayout tray = new LinearLayout(getContext());
            tray.setOrientation(LinearLayout.HORIZONTAL);
            tray.setGravity(Gravity.CENTER);
            List<FrameLayout> tvs = new ArrayList<>();
            for (int i = 0; i < items.length; i++) {
                final int idx = i;
                boolean big = "besar".equals(items[i][1]);
                FrameLayout tv = new FrameLayout(getContext());
                int pad = UiHelper.dp(getContext(), big ? 6 : 18);
                tv.setPadding(pad, pad, pad, pad);
                tv.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFFFFF, 20, 3, 4));
                tv.addView(new SpriteView(getContext(), items[i][0]), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                int sz = UiHelper.dp(getContext(), 76);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sz, sz);
                lp.setMargins(UiHelper.dp(getContext(), 5), 0, UiHelper.dp(getContext(), 5), 0);
                tv.setOnClickListener(v -> {
                    if (tv.getVisibility() != VISIBLE) return;
                    picked = idx;
                    for (int j = 0; j < tvs.size(); j++)
                        tvs.get(j).setBackground(UiHelper.toyBox3D(getContext(), j == idx ? 0xFFFFD23F : 0xFFFFFFFF, 20, 3, 4));
                    UiHelper.bounce(tv);
                    host.sfx().playVoice(items[idx][1]);
                });
                tvs.add(tv);
                tray.addView(tv, lp);
            }

            LinearLayout boxes = new LinearLayout(getContext());
            boxes.setOrientation(LinearLayout.HORIZONTAL);
            boxes.setGravity(Gravity.CENTER);
            boxes.setPadding(0, UiHelper.dp(getContext(), 34), 0, 0);
            String[][] bDefs = {{"besar", "Besar", "26"}, {"kecil", "Kecil", "16"}};
            int[] bCols = {0xFFFFD23F, 0xFF7CC7F0};
            for (int b = 0; b < 2; b++) {
                final String cat = bDefs[b][0];
                TextView box = new TextView(getContext());
                box.setText(bDefs[b][1]);
                box.setTextSize(Integer.parseInt(bDefs[b][2]));
                box.setTextColor(0xFF2A2140);
                box.setTypeface(null, Typeface.BOLD);
                box.setGravity(Gravity.CENTER);
                box.setBackground(UiHelper.toyBox3D(getContext(), bCols[b], 24, 4, 6));
                int bw = UiHelper.dp(getContext(), b == 0 ? 160 : 120), bh = UiHelper.dp(getContext(), b == 0 ? 130 : 100);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(bw, bh);
                lp.setMargins(UiHelper.dp(getContext(), 10), 0, UiHelper.dp(getContext(), 10), 0);
                box.setOnClickListener(v -> {
                    if (picked < 0) return;
                    if (items[picked][1].equals(cat)) {
                        tvs.get(picked).setVisibility(INVISIBLE);
                        picked = -1; left--;
                        host.sfx().good(); UiHelper.bounce(box); burstView(box, null, 18);
                        if (left == 0) handler.postDelayed(() -> host.celebrate(this::nextRound, null), 700);
                    } else {
                        UiHelper.shake(box); host.sfx().wrong(); host.sfx().playVoice("ups");
                    }
                });
                boxes.addView(box, lp);
            }
            col.addView(tray); col.addView(boxes);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("besarkecil_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice("besarkecil_intro"); }
    }

    /* 19. Rumah Hewan */
    public static class RumahGame extends BaseGameView {
        private int picked = -1, left = 3;

        public RumahGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            picked = -1; left = 3;
            String[][] pairs = {
                {"ikan", "laut"}, {"sapi", "ternak"}, {"singa", "hutan"},
                {"gurita", "laut"}, {"ayam", "ternak"}, {"gajah", "hutan"}
            };
            int off = rnd.nextBoolean() ? 0 : 3;
            String[][] round = {pairs[off], pairs[off + 1], pairs[off + 2]};

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            LinearLayout tray = new LinearLayout(getContext());
            tray.setOrientation(LinearLayout.HORIZONTAL);
            tray.setGravity(Gravity.CENTER);
            List<FrameLayout> tvs = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                final int idx = i;
                GameData.Item it = GameData.ITEMS.get(round[i][0]);
                FrameLayout tv = new FrameLayout(getContext());
                int pad = UiHelper.dp(getContext(), 8);
                tv.setPadding(pad, pad, pad, pad);
                tv.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFFFFF, 22, 3, 5));
                tv.addView(new SpriteView(getContext(), it.key), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                int sz = UiHelper.dp(getContext(), 88);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sz, sz);
                lp.setMargins(UiHelper.dp(getContext(), 8), 0, UiHelper.dp(getContext(), 8), 0);
                tv.setOnClickListener(v -> {
                    if (tv.getVisibility() != VISIBLE) return;
                    picked = idx;
                    for (int j = 0; j < tvs.size(); j++)
                        tvs.get(j).setBackground(UiHelper.toyBox3D(getContext(), j == idx ? 0xFFFFD23F : 0xFFFFFFFF, 22, 3, 5));
                    UiHelper.bounce(tv);
                    host.sfx().playReal(it.key);
                    host.sfx().playVoice("n_" + it.key);
                });
                tvs.add(tv);
                tray.addView(tv, lp);
            }

            LinearLayout boxes = new LinearLayout(getContext());
            boxes.setOrientation(LinearLayout.HORIZONTAL);
            boxes.setGravity(Gravity.CENTER);
            boxes.setPadding(0, UiHelper.dp(getContext(), 34), 0, 0);
            String[][] homes = {{"laut", "🌊\nLaut"}, {"ternak", "🏡\nTernak"}, {"hutan", "🌳\nHutan"}};
            int[] hCols = {0xFF7CC7F0, 0xFFC8E6A0, 0xFF5FBF5A};
            for (int b = 0; b < 3; b++) {
                final String hk = homes[b][0];
                TextView box = new TextView(getContext());
                box.setText(homes[b][1]);
                box.setTextSize(18);
                box.setTextColor(0xFF2A2140);
                box.setTypeface(null, Typeface.BOLD);
                box.setGravity(Gravity.CENTER);
                box.setBackground(UiHelper.toyBox3D(getContext(), hCols[b], 22, 3, 5));
                int bw = UiHelper.dp(getContext(), 102), bh = UiHelper.dp(getContext(), 110);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(bw, bh);
                lp.setMargins(UiHelper.dp(getContext(), 6), 0, UiHelper.dp(getContext(), 6), 0);
                box.setOnClickListener(v -> {
                    if (picked < 0) return;
                    if (round[picked][1].equals(hk)) {
                        tvs.get(picked).setVisibility(INVISIBLE);
                        picked = -1; left--;
                        host.sfx().good(); UiHelper.bounce(box); burstView(box, null, 20);
                        if (left == 0) handler.postDelayed(() -> host.celebrate(this::nextRound, null), 700);
                    } else {
                        UiHelper.shake(box); host.sfx().wrong(); host.sfx().playVoice("ups");
                    }
                });
                boxes.addView(box, lp);
            }
            col.addView(tray); col.addView(boxes);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("rumah_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice("rumah_intro"); }
    }

    /* 20. Cari Bayangan */
    public static class BayanganGame extends BaseGameView {
        private int picked = -1, left = 3;

        public BayanganGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            picked = -1; left = 3;
            List<String> keys = new ArrayList<>(GameData.VOCAL_ANIMALS);
            Collections.shuffle(keys);
            List<String> sel = keys.subList(0, 3);

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            LinearLayout shRow = new LinearLayout(getContext());
            shRow.setOrientation(LinearLayout.HORIZONTAL);
            shRow.setGravity(Gravity.CENTER);

            LinearLayout itRow = new LinearLayout(getContext());
            itRow.setOrientation(LinearLayout.HORIZONTAL);
            itRow.setGravity(Gravity.CENTER);
            itRow.setPadding(0, UiHelper.dp(getContext(), 34), 0, 0);

            List<FrameLayout> itemTvs = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                final int idx = i;
                GameData.Item it = GameData.ITEMS.get(sel.get(i));
                FrameLayout tv = new FrameLayout(getContext());
                int pad = UiHelper.dp(getContext(), 8);
                tv.setPadding(pad, pad, pad, pad);
                tv.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFFFFF, 22, 3, 5));
                tv.addView(new SpriteView(getContext(), it.key), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                int sz = UiHelper.dp(getContext(), 92);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sz, sz);
                lp.setMargins(UiHelper.dp(getContext(), 8), 0, UiHelper.dp(getContext(), 8), 0);
                tv.setOnClickListener(v -> {
                    if (tv.getVisibility() != VISIBLE) return;
                    picked = idx;
                    for (int j = 0; j < itemTvs.size(); j++)
                        itemTvs.get(j).setBackground(UiHelper.toyBox3D(getContext(), j == idx ? 0xFFFFD23F : 0xFFFFFFFF, 22, 3, 5));
                    UiHelper.bounce(tv);
                    host.sfx().playVoice("n_" + it.key);
                });
                itemTvs.add(tv);
                itRow.addView(tv, lp);
            }

            List<Integer> shOrder = new ArrayList<>(List.of(0, 1, 2));
            Collections.shuffle(shOrder);
            for (int i : shOrder) {
                GameData.Item it = GameData.ITEMS.get(sel.get(i));
                FrameLayout sv = new FrameLayout(getContext());
                int pad = UiHelper.dp(getContext(), 8);
                sv.setPadding(pad, pad, pad, pad);
                sv.setBackground(UiHelper.toyBox3D(getContext(), 0xFFD0C9C0, 22, 3, 5));
                SpriteView silSp = new SpriteView(getContext(), it.key);
                silSp.setSilhouette(true);
                sv.addView(silSp, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                int sz = UiHelper.dp(getContext(), 96);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sz, sz);
                lp.setMargins(UiHelper.dp(getContext(), 8), 0, UiHelper.dp(getContext(), 8), 0);
                sv.setOnClickListener(v -> {
                    if (picked < 0 || sv.getTag() != null) return;
                    if (picked == i) {
                        sv.setTag("ok");
                        silSp.setSilhouette(false);
                        sv.setBackground(UiHelper.toyBox3D(getContext(), 0xFF6CC56F, 22, 3, 5));
                        itemTvs.get(picked).setVisibility(INVISIBLE);
                        picked = -1; left--;
                        host.sfx().good(); host.sfx().playReal(it.key); burstView(sv, null, 20);
                        if (left == 0) handler.postDelayed(() -> host.celebrate(this::nextRound, null), 700);
                    } else {
                        UiHelper.shake(sv); host.sfx().wrong(); host.sfx().playVoice("ups");
                    }
                });
                shRow.addView(sv, lp);
            }

            col.addView(shRow); col.addView(itRow);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("bayangan_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice("bayangan_intro"); }
    }

    /* 21. Bentuk dan Lubang (Custom Canvas Vector Shapes) */
    public static class BentukGame extends BaseGameView {
        private int picked = -1, left = 3;
        private final String[] shapes = {"lingkaran", "segitiga", "persegi", "bintang", "hati"};
        private final int[] cols = {0xFFE63946, 0xFFFF8C1A, 0xFF4CC9F0, 0xFFFFD23F, 0xFFFF5D8F};

        public BentukGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            picked = -1; left = 3;
            List<Integer> idxs = new ArrayList<>(List.of(0, 1, 2, 3, 4));
            Collections.shuffle(idxs);
            List<Integer> sel = idxs.subList(0, 3);

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            LinearLayout holesRow = new LinearLayout(getContext());
            holesRow.setOrientation(LinearLayout.HORIZONTAL);
            holesRow.setGravity(Gravity.CENTER);

            LinearLayout piecesRow = new LinearLayout(getContext());
            piecesRow.setOrientation(LinearLayout.HORIZONTAL);
            piecesRow.setGravity(Gravity.CENTER);
            piecesRow.setPadding(0, UiHelper.dp(getContext(), 34), 0, 0);

            List<ShapeView> pViews = new ArrayList<>();
            for (int i : sel) {
                ShapeView sv = new ShapeView(getContext(), shapes[i], cols[i], false);
                sv.setTag(i);
                int sz = UiHelper.dp(getContext(), 92);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sz, sz);
                lp.setMargins(UiHelper.dp(getContext(), 8), 0, UiHelper.dp(getContext(), 8), 0);
                sv.setOnClickListener(v -> {
                    if (sv.getVisibility() != VISIBLE) return;
                    picked = i;
                    for (ShapeView x : pViews) x.setScaleX((int) x.getTag() == i ? 1.15f : 1f);
                    for (ShapeView x : pViews) x.setScaleY((int) x.getTag() == i ? 1.15f : 1f);
                    host.sfx().playVoice("s_" + shapes[i]);
                });
                pViews.add(sv);
                piecesRow.addView(sv, lp);
            }

            List<Integer> hOrder = new ArrayList<>(sel);
            Collections.shuffle(hOrder);
            for (int i : hOrder) {
                ShapeView hv = new ShapeView(getContext(), shapes[i], 0x552A2140, true);
                int sz = UiHelper.dp(getContext(), 96);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sz, sz);
                lp.setMargins(UiHelper.dp(getContext(), 8), 0, UiHelper.dp(getContext(), 8), 0);
                hv.setOnClickListener(v -> {
                    if (picked < 0 || hv.filled) return;
                    if (picked == i) {
                        hv.setFilled(cols[i]);
                        for (ShapeView x : pViews) if ((int) x.getTag() == i) x.setVisibility(INVISIBLE);
                        picked = -1; left--;
                        host.sfx().good(); UiHelper.bounce(hv); burstView(hv, null, 20);
                        if (left == 0) handler.postDelayed(() -> host.celebrate(this::nextRound, null), 700);
                    } else {
                        UiHelper.shake(hv); host.sfx().wrong(); host.sfx().playVoice("ups");
                    }
                });
                holesRow.addView(hv, lp);
            }
            col.addView(holesRow); col.addView(piecesRow);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("bentuk_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice("bentuk_intro"); }

        private static class ShapeView extends View {
            String shape; int color; boolean hole; boolean filled = false;
            Paint fp = new Paint(Paint.ANTI_ALIAS_FLAG), sp = new Paint(Paint.ANTI_ALIAS_FLAG);
            Path path = new Path();
            ShapeView(Context c, String s, int col, boolean hole) {
                super(c); this.shape = s; this.color = col; this.hole = hole;
                sp.setStyle(Paint.Style.STROKE); sp.setColor(0xFF2A2140); sp.setStrokeWidth(UiHelper.dp(c, 4));
                if (hole) sp.setPathEffect(new DashPathEffect(new float[]{14, 10}, 0));
            }
            void setFilled(int c) { filled = true; color = c; sp.setPathEffect(null); invalidate(); }
            @Override protected void onDraw(Canvas c) {
                float w = getWidth(), h = getHeight(), pad = w * 0.14f;
                fp.setColor(color);
                path.reset();
                if ("lingkaran".equals(shape)) {
                    c.drawCircle(w / 2, h / 2, w / 2 - pad, fp);
                    c.drawCircle(w / 2, h / 2, w / 2 - pad, sp);
                    return;
                } else if ("persegi".equals(shape)) {
                    RectF r = new RectF(pad, pad, w - pad, h - pad);
                    c.drawRoundRect(r, 14, 14, fp); c.drawRoundRect(r, 14, 14, sp);
                    return;
                } else if ("segitiga".equals(shape)) {
                    path.moveTo(w / 2, pad); path.lineTo(w - pad, h - pad); path.lineTo(pad, h - pad); path.close();
                } else if ("bintang".equals(shape)) {
                    for (int i = 0; i < 10; i++) {
                        double a = i * Math.PI / 5.0 - Math.PI / 2.0;
                        float r = (i % 2 == 0) ? (w / 2 - pad) : (w / 2 - pad) * 0.45f;
                        float px = (float) (w / 2 + Math.cos(a) * r), py = (float) (h / 2 + Math.sin(a) * r);
                        if (i == 0) path.moveTo(px, py); else path.lineTo(px, py);
                    }
                    path.close();
                } else {
                    path.moveTo(w / 2, h * 0.84f);
                    path.cubicTo(w * 0.08f, h * 0.56f, w * 0.12f, h * 0.18f, w / 2, h * 0.34f);
                    path.cubicTo(w * 0.88f, h * 0.18f, w * 0.92f, h * 0.56f, w / 2, h * 0.84f);
                }
                c.drawPath(path, fp); c.drawPath(path, sp);
            }
        }
    }

    /* 22. Susun Pelangi (Custom Canvas 6-Arc Rainbow) */
    public static class PelangiGame extends BaseGameView {
        private int filledCount = 0;
        private final String[] colNames = {"merah", "oranye", "kuning", "hijau", "biru", "ungu"};
        private final int[] colVals = {0xFFE63946, 0xFFFF8C1A, 0xFFFFD23F, 0xFF3FB950, 0xFF2F80ED, 0xFF8E44EC};
        private final double[] notes = {523.25, 587.33, 659.25, 698.46, 783.99, 880.0};

        public PelangiGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            filledCount = 0;
            boolean[] filled = new boolean[6];

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            View rainbowView = new View(getContext()) {
                final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
                final RectF rect = new RectF();
                @Override protected void onDraw(Canvas c) {
                    p.setStyle(Paint.Style.STROKE);
                    p.setStrokeCap(Paint.Cap.ROUND);
                    float w = getWidth(), h = getHeight();
                    float sw = Math.min(w, h * 1.6f) * 0.055f;
                    p.setStrokeWidth(sw);
                    for (int i = 0; i < 6; i++) {
                        float r = (Math.min(w * 0.45f, h * 0.85f)) - i * (sw * 1.15f);
                        rect.set(w / 2 - r, h * 0.92f - r, w / 2 + r, h * 0.92f + r);
                        if (filled[i]) {
                            p.setPathEffect(null);
                            p.setColor(colVals[i]);
                        } else {
                            p.setPathEffect(new DashPathEffect(new float[]{18, 14}, 0));
                            p.setColor(0xAAFFFFFF);
                        }
                        c.drawArc(rect, 180, 180, false, p);
                    }
                }
            };
            col.addView(rainbowView, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, 0, 1f));

            LinearLayout tray = new LinearLayout(getContext());
            tray.setOrientation(LinearLayout.HORIZONTAL);
            tray.setGravity(Gravity.CENTER);
            tray.setPadding(0, UiHelper.dp(getContext(), 12), 0, UiHelper.dp(getContext(), 22));

            List<Integer> order = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5));
            Collections.shuffle(order);
            for (int i : order) {
                Button b = new Button(getContext());
                b.setText("🌈");
                b.setTextSize(20);
                b.setBackground(UiHelper.toyBox3D(getContext(), colVals[i], 99, 3, 4));
                int sz = UiHelper.dp(getContext(), 52);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sz, sz);
                lp.setMargins(UiHelper.dp(getContext(), 4), 0, UiHelper.dp(getContext(), 4), 0);
                b.setOnClickListener(v -> {
                    if (filled[i]) return;
                    filled[i] = true;
                    b.setAlpha(0.25f);
                    rainbowView.invalidate();
                    host.sfx().note(notes[i]);
                    host.sfx().playVoice("c_" + colNames[i]);
                    burstView(rainbowView, new int[]{colVals[i], 0xFFFFFFFF}, 20);
                    filledCount++;
                    if (filledCount >= 6) {
                        handler.postDelayed(() -> host.celebrate(this::nextRound, "pelangi_done"), 700);
                    }
                });
                tray.addView(b, lp);
            }
            col.addView(tray);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("pelangi_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice("pelangi_intro"); }
    }

    /* 23. Puzzle 2x2 */
    public static class PuzzleGame extends BaseGameView {
        private int placed = 0;

        public PuzzleGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            placed = 0;
            String[] pics = {"singa", "gajah", "roket", "kereta", "stroberi", "kupukupu"};
            String pic = pics[rnd.nextInt(pics.length)];

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            TextView preview = new TextView(getContext());
            preview.setText("Susun Gambar! (0 / 4)");
            preview.setTextSize(22);
            preview.setTextColor(0xFF2A2140);
            preview.setTypeface(null, Typeface.BOLD);
            preview.setPadding(UiHelper.dp(getContext(), 16), UiHelper.dp(getContext(), 6), UiHelper.dp(getContext(), 16), UiHelper.dp(getContext(), 6));
            preview.setBackground(UiHelper.toyBox(getContext(), 0xFFFFD23F, 99, 3));
            col.addView(preview);

            GridLayout grid = new GridLayout(getContext());
            grid.setColumnCount(2);
            int sz = UiHelper.dp(getContext(), 110);
            LinearLayout.LayoutParams glpRoot = new LinearLayout.LayoutParams(sz * 2 + UiHelper.dp(getContext(), 16), sz * 2 + UiHelper.dp(getContext(), 16));
            glpRoot.topMargin = UiHelper.dp(getContext(), 20);
            FrameLayout[] slots = new FrameLayout[4];
            for (int i = 0; i < 4; i++) {
                FrameLayout s = new FrameLayout(getContext());
                int sPad = UiHelper.dp(getContext(), 10);
                s.setPadding(sPad, sPad, sPad, sPad);
                s.setBackground(UiHelper.toyBox(getContext(), 0x88FFFFFF, 18, 3));
                TextView num = new TextView(getContext());
                num.setText(String.valueOf(i + 1));
                num.setTextSize(28); num.setTextColor(0x662A2140); num.setGravity(Gravity.CENTER);
                s.addView(num, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                slots[i] = s;
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.width = sz; lp.height = sz;
                int m = UiHelper.dp(getContext(), 4);
                lp.setMargins(m, m, m, m);
                grid.addView(s, lp);
            }
            col.addView(grid, glpRoot);

            LinearLayout tray = new LinearLayout(getContext());
            tray.setOrientation(LinearLayout.HORIZONTAL);
            tray.setGravity(Gravity.CENTER);
            tray.setPadding(0, UiHelper.dp(getContext(), 22), 0, 0);
            List<Integer> order = new ArrayList<>(List.of(0, 1, 2, 3));
            Collections.shuffle(order);
            for (int i : order) {
                FrameLayout pb = new FrameLayout(getContext());
                int pPad = UiHelper.dp(getContext(), 8);
                pb.setPadding(pPad, pPad, pPad, pPad);
                pb.setBackground(UiHelper.toyBox3D(getContext(), 0xFF4CC9F0, 18, 3, 4));
                pb.addView(new SpriteView(getContext(), pic), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(UiHelper.dp(getContext(), 72), UiHelper.dp(getContext(), 72));
                lp.setMargins(UiHelper.dp(getContext(), 4), 0, UiHelper.dp(getContext(), 4), 0);
                pb.setOnClickListener(v -> {
                    pb.setVisibility(INVISIBLE);
                    slots[i].removeAllViews();
                    slots[i].addView(new SpriteView(getContext(), pic), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                    slots[i].setBackground(UiHelper.toyBox3D(getContext(), 0xFF6CC56F, 18, 3, 4));
                    host.sfx().good();
                    burstView(slots[i], null, 16);
                    placed++;
                    preview.setText("Susun Gambar! (" + placed + " / 4)");
                    if (placed >= 4) handler.postDelayed(() -> host.celebrate(this::nextRound, "puzzle_done"), 700);
                });
                tray.addView(pb, lp);
            }
            col.addView(tray);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("puzzle_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice("puzzle_intro"); }
    }

    /* 24. Antar Sampai Tujuan (Jalan) */
    public static class JalanGame extends BaseGameView {
        private int step = 0;

        public JalanGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            step = 0;
            String[][] trips = {{"🚗", "🏡", "mobil"}, {"🚂", "🚉", "kereta"}, {"🚢", "🏝️", "kapal"}};
            String[] tr = trips[rnd.nextInt(trips.length)];

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            TextView road = new TextView(getContext());
            road.setText(tr[0] + "   ·   ·   ·   " + tr[1]);
            road.setTextSize(34);
            road.setGravity(Gravity.CENTER);
            road.setPadding(UiHelper.dp(getContext(), 18), UiHelper.dp(getContext(), 22), UiHelper.dp(getContext(), 18), UiHelper.dp(getContext(), 22));
            road.setBackground(UiHelper.toyBox(getContext(), 0xFF6B6F80, 24, 4));
            road.setTextColor(0xFFFFFFFF);
            col.addView(road);

            Button goBtn = new Button(getContext());
            goBtn.setText("Maju! " + tr[0]);
            goBtn.setAllCaps(false);
            goBtn.setTextSize(22);
            goBtn.setTextColor(0xFF2A2140);
            goBtn.setTypeface(null, Typeface.BOLD);
            goBtn.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFD23F, 99, 4, 6));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.topMargin = UiHelper.dp(getContext(), 28);
            goBtn.setPadding(UiHelper.dp(getContext(), 28), UiHelper.dp(getContext(), 12), UiHelper.dp(getContext(), 28), UiHelper.dp(getContext(), 12));
            goBtn.setOnClickListener(v -> {
                step++;
                UiHelper.bounce(road);
                host.sfx().playReal(tr[2]);
                burstView(road, null, 14);
                if (step == 1) road.setText("·   " + tr[0] + "   ·   ·   " + tr[1]);
                else if (step == 2) road.setText("·   ·   " + tr[0] + "   ·   " + tr[1]);
                else if (step == 3) road.setText("·   ·   ·   " + tr[0] + "   " + tr[1]);
                else {
                    road.setText("🎉  " + tr[0] + "  " + tr[1] + "  🎉");
                    goBtn.setEnabled(false);
                    handler.postDelayed(() -> host.celebrate(this::nextRound, "jalan_done"), 700);
                }
            });
            col.addView(goBtn, lp);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("jalan_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice("jalan_intro"); }
    }
}
