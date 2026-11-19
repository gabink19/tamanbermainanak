package com.gabink.tamanbermain;

import android.animation.ObjectAnimator;
import android.content.Context;
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

public class GamesPack2 {

    /* 9. Cari Hewan */
    public static class SembunyiGame extends BaseGameView {
        private String targetKey;
        private boolean found;

        public SembunyiGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }

        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            found = false;
            List<String> pool = new ArrayList<>();
            for (String k : GameData.VOCAL_ANIMALS) if (!"bayi".equals(k)) pool.add(k);
            targetKey = pool.get(rnd.nextInt(pool.size()));

            GridLayout grid = new GridLayout(getContext());
            grid.setColumnCount(2);
            int p = UiHelper.dp(getContext(), 20);
            grid.setPadding(p, p, p, p);

            String[] hiders = {"semak", "batu", "bunga", "kayu"};
            String[] decors = {"kupukupu", "kumbang", "bunga", "bintang"};
            int winIdx = rnd.nextInt(4);

            for (int i = 0; i < 4; i++) {
                final int idx = i;
                FrameLayout bush = new FrameLayout(getContext());
                int pad = UiHelper.dp(getContext(), 16);
                bush.setPadding(pad, pad, pad, pad);
                bush.setBackground(UiHelper.toyBox3D(getContext(), 0xAAFFFFFF, 28, 3, 5));
                SpriteView sp = new SpriteView(getContext(), hiders[i]);
                bush.addView(sp, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                bush.setOnClickListener(v -> {
                    if (found || bush.getTag() != null) return;
                    bush.setTag("open");
                    UiHelper.bounce(bush);
                    if (idx == winIdx) {
                        found = true;
                        sp.setKey(targetKey);
                        bush.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFFFFF, 28, 4, 6));
                        host.sfx().ding();
                        host.sfx().playReal(targetKey);
                        host.sfx().playVoice("ada_" + targetKey);
                        burstView(bush, null, 30);
                        handler.postDelayed(() -> host.celebrate(this::nextRound, "yeay"), 1400);
                    } else {
                        sp.setKey(decors[rnd.nextInt(decors.length)]);
                        host.sfx().wrong();
                    }
                });
                GridLayout.LayoutParams glp = new GridLayout.LayoutParams();
                glp.width = 0; glp.height = 0;
                glp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                glp.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                int m = UiHelper.dp(getContext(), 8);
                glp.setMargins(m, m, m, m);
                grid.addView(bush, glp);
            }
            addView(grid, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("cari_" + targetKey);
        }

        @Override public void onReplay() { host.sfx().playVoice("cari_" + targetKey); }
    }

    /* 10. Luncurkan Roket */
    public static class RoketGame extends BaseGameView {
        private int fuel = 0;

        public RoketGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }

        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            fuel = 0;
            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            SpriteView rocket = new SpriteView(getContext(), "roket");
            int rSz = UiHelper.dp(getContext(), 140);
            col.addView(rocket, new LinearLayout.LayoutParams(rSz, rSz));

            TextView bar = new TextView(getContext());
            bar.setText("0 / 5");
            bar.setTextSize(20);
            bar.setTextColor(0xFF2A2140);
            bar.setTypeface(null, Typeface.BOLD);
            bar.setPadding(UiHelper.dp(getContext(), 20), UiHelper.dp(getContext(), 6), UiHelper.dp(getContext(), 20), UiHelper.dp(getContext(), 6));
            bar.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFD23F, 99, 3, 4));
            col.addView(bar);

            LinearLayout starsRow = new LinearLayout(getContext());
            starsRow.setOrientation(LinearLayout.HORIZONTAL);
            starsRow.setGravity(Gravity.CENTER);
            starsRow.setPadding(0, UiHelper.dp(getContext(), 20), 0, 0);

            Button launchBtn = new Button(getContext());
            launchBtn.setText("LUNCUR!");
            launchBtn.setTextSize(20);
            launchBtn.setTextColor(0xFFFFFFFF);
            launchBtn.setTypeface(null, Typeface.BOLD);
            launchBtn.setBackground(UiHelper.toyBox3D(getContext(), 0xFFE63946, 99, 4, 6));
            launchBtn.setPadding(UiHelper.dp(getContext(), 28), UiHelper.dp(getContext(), 10), UiHelper.dp(getContext(), 28), UiHelper.dp(getContext(), 12));
            launchBtn.setVisibility(GONE);
            LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            blp.topMargin = UiHelper.dp(getContext(), 18);

            for (int i = 0; i < 5; i++) {
                SpriteView st = new SpriteView(getContext(), "bintang");
                int sSz = UiHelper.dp(getContext(), 56);
                LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(sSz, sSz);
                slp.setMargins(UiHelper.dp(getContext(), 4), 0, UiHelper.dp(getContext(), 4), 0);
                st.setOnClickListener(v -> {
                    if (st.getVisibility() != VISIBLE) return;
                    st.setVisibility(INVISIBLE);
                    fuel++;
                    bar.setText(fuel + " / 5");
                    UiHelper.bounce(rocket);
                    host.sfx().star(Math.min(2, fuel - 1));
                    host.sfx().playVoice("num_" + fuel);
                    burstView(rocket, null, 14);
                    if (fuel >= 5) {
                        launchBtn.setVisibility(VISIBLE);
                        UiHelper.bounce(launchBtn);
                        host.sfx().playVoice("roket_siap");
                    }
                });
                starsRow.addView(st, slp);
            }

            launchBtn.setOnClickListener(v -> {
                launchBtn.setEnabled(false);
                host.sfx().playReal("roket");
                host.sfx().whoosh();
                host.sfx().playVoice("roket_meluncur");
                burstView(rocket, new int[]{0xFFFF9F1C, 0xFFFFD23F, 0xFFE63946, 0xFFFFFFFF}, 45);
                ObjectAnimator anim = ObjectAnimator.ofFloat(rocket, "translationY", 0f, -getHeight() - 300f);
                anim.setDuration(1200);
                anim.start();
                handler.postDelayed(() -> host.celebrate(this::nextRound, null), 1300);
            });

            col.addView(starsRow);
            col.addView(launchBtn, blp);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("roket_intro");
        }

        @Override public void onReplay() { host.sfx().playVoice(fuel >= 5 ? "roket_siap" : "roket_intro"); }
    }

    /* 11. Beri Makan Hewan */
    public static class MakanGame extends BaseGameView {
        private String curAnimal;

        public MakanGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }

        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            String[] pair = GameData.FEED_PAIRS[rnd.nextInt(GameData.FEED_PAIRS.length)];
            curAnimal = pair[0];
            String goodFood = pair[1];
            GameData.Item ani = GameData.ITEMS.get(curAnimal);

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            SpriteView aniView = new SpriteView(getContext(), curAnimal);
            int aniSz = UiHelper.dp(getContext(), 170);
            aniView.setOnClickListener(v -> {
                UiHelper.bounce(aniView);
                host.sfx().playReal(curAnimal);
                host.sfx().playVoice("ada_" + curAnimal);
                burstView(aniView, null, 14);
            });
            col.addView(aniView, new LinearLayout.LayoutParams(aniSz, aniSz));

            List<String> allFoods = new ArrayList<>(GameData.FOODS.keySet());
            allFoods.remove(goodFood);
            Collections.shuffle(allFoods);
            List<String> opts = new ArrayList<>();
            opts.add(goodFood);
            opts.add(allFoods.get(0));
            opts.add(allFoods.get(1));
            Collections.shuffle(opts);

            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER);
            row.setPadding(0, UiHelper.dp(getContext(), 28), 0, 0);

            for (String fk : opts) {
                FrameLayout fb = new FrameLayout(getContext());
                fb.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFFAF0, 24, 3, 5));
                int pad = UiHelper.dp(getContext(), 10);
                fb.setPadding(pad, pad, pad, pad);
                SpriteView fsv = new SpriteView(getContext(), fk);
                fb.addView(fsv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                int sz = UiHelper.dp(getContext(), 92);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sz, sz);
                lp.setMargins(UiHelper.dp(getContext(), 8), 0, UiHelper.dp(getContext(), 8), 0);
                fb.setOnClickListener(v -> {
                    if (fk.equals(goodFood)) {
                        UiHelper.bounce(aniView);
                        host.sfx().good();
                        host.sfx().playReal(curAnimal);
                        host.sfx().playVoice("nyam_" + curAnimal);
                        burstView(aniView, null, 28);
                        handler.postDelayed(() -> host.celebrate(this::nextRound, null), 1200);
                    } else {
                        UiHelper.shake(fb);
                        host.sfx().wrong();
                        host.sfx().playVoice("ups");
                    }
                });
                row.addView(fb, lp);
            }
            col.addView(row);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("lapar_" + curAnimal);
        }

        @Override public void onReplay() { host.sfx().playVoice("lapar_" + curAnimal); }
    }

    /* 12. Panen Sayur */
    public static class PanenGame extends BaseGameView {
        private int left = 0;

        public PanenGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }

        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            List<GameData.Item> vegs = new ArrayList<>();
            for (GameData.Item it : GameData.ITEMS.values()) if ("veg".equals(it.cat)) vegs.add(it);
            Collections.shuffle(vegs);
            int n = Math.min(4, vegs.size());
            left = n;

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            FrameLayout crate = new FrameLayout(getContext());
            crate.setBackground(UiHelper.toyBox3D(getContext(), 0xFFD39B5C, 24, 4, 6));
            int cPad = UiHelper.dp(getContext(), 12);
            crate.setPadding(cPad, cPad, cPad, cPad);
            crate.addView(new SpriteView(getContext(), "keranjang"), new LayoutParams(UiHelper.dp(getContext(), 120), UiHelper.dp(getContext(), 90)));

            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER);
            row.setPadding(0, 0, 0, UiHelper.dp(getContext(), 32));

            for (int i = 0; i < n; i++) {
                GameData.Item it = vegs.get(i);
                FrameLayout fv = new FrameLayout(getContext());
                fv.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFFAF0, 20, 3, 4));
                int pad = UiHelper.dp(getContext(), 8);
                fv.setPadding(pad, pad, pad, pad);
                SpriteView sv = new SpriteView(getContext(), it.key);
                fv.addView(sv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                int vsz = UiHelper.dp(getContext(), 74);
                LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(vsz, vsz);
                vlp.setMargins(UiHelper.dp(getContext(), 5), 0, UiHelper.dp(getContext(), 5), 0);
                fv.setOnClickListener(v -> {
                    if (fv.getVisibility() != VISIBLE) return;
                    fv.setVisibility(INVISIBLE);
                    host.sfx().pop();
                    host.sfx().playVoice("n_" + it.key);
                    UiHelper.bounce(crate);
                    burstView(crate, null, 18);
                    left--;
                    if (left == 0) {
                        handler.postDelayed(() -> host.celebrate(this::nextRound, "panen_done"), 700);
                    }
                });
                row.addView(fv, vlp);
            }

            col.addView(row);
            col.addView(crate);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("panen_intro");
        }

        @Override public void onReplay() { host.sfx().playVoice("panen_intro"); }
    }

    /* 13. Buat Es Krim */
    public static class EsKrimGame extends BaseGameView {
        private int scoops = 0, bites = 0;
        private boolean readyToEat = false;

        public EsKrimGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }

        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            scoops = 0; bites = 0; readyToEat = false;

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            SpriteView coneView = new SpriteView(getContext(), "eskrim");
            int cSz = UiHelper.dp(getContext(), 150);
            col.addView(coneView, new LinearLayout.LayoutParams(cSz, cSz));

            TextView stackTv = new TextView(getContext());
            stackTv.setText("Pilih 3 rasa! (0 / 3)");
            stackTv.setTextSize(20);
            stackTv.setTextColor(0xFF2A2140);
            stackTv.setTypeface(null, Typeface.BOLD);
            col.addView(stackTv);

            LinearLayout opts = new LinearLayout(getContext());
            opts.setOrientation(LinearLayout.HORIZONTAL);
            opts.setGravity(Gravity.CENTER);
            opts.setPadding(0, UiHelper.dp(getContext(), 22), 0, 0);

            String[] flavors = {"stroberi", "jeruk", "apel", "anggur", "pisang"};
            for (String fl : flavors) {
                FrameLayout b = new FrameLayout(getContext());
                b.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFFAF0, 99, 3, 4));
                int pad = UiHelper.dp(getContext(), 8);
                b.setPadding(pad, pad, pad, pad);
                b.addView(new SpriteView(getContext(), fl), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                int sz = UiHelper.dp(getContext(), 64);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sz, sz);
                lp.setMargins(UiHelper.dp(getContext(), 4), 0, UiHelper.dp(getContext(), 4), 0);
                b.setOnClickListener(v -> {
                    if (readyToEat) return;
                    scoops++;
                    stackTv.setText("Pilih 3 rasa! (" + scoops + " / 3)");
                    UiHelper.bounce(coneView);
                    host.sfx().pop();
                    burstView(coneView, null, 14);
                    if (scoops >= 3) {
                        readyToEat = true;
                        opts.setVisibility(INVISIBLE);
                        stackTv.setText("Ketuk es krimnya untuk dimakan!");
                        host.sfx().playVoice("es_makan");
                    }
                });
                opts.addView(b, lp);
            }

            coneView.setOnClickListener(v -> {
                if (!readyToEat) return;
                bites++;
                UiHelper.bounce(coneView);
                host.sfx().noise(120, 0.25);
                burstView(coneView, null, 18);
                if (bites == 1) coneView.setScaleX(0.85f);
                else if (bites == 2) coneView.setScaleY(0.75f);
                else if (bites >= 3) {
                    coneView.setVisibility(INVISIBLE);
                    handler.postDelayed(() -> host.celebrate(this::nextRound, null), 600);
                }
            });

            col.addView(opts);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("es_intro");
        }

        @Override public void onReplay() { host.sfx().playVoice(readyToEat ? "es_makan" : "es_intro"); }
    }

    /* 14. Kartu Kembar */
    public static class PasanganGame extends BaseGameView {
        private FrameLayout firstCard = null;
        private int pairsLeft = 3;

        public PasanganGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }

        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            firstCard = null;
            pairsLeft = 3;
            List<GameData.Item> pool = new ArrayList<>();
            for (GameData.Item it : GameData.ITEMS.values()) if ("animal".equals(it.cat)) pool.add(it);
            Collections.shuffle(pool);
            List<GameData.Item> cards = new ArrayList<>();
            for (int i = 0; i < 3; i++) { cards.add(pool.get(i)); cards.add(pool.get(i)); }
            Collections.shuffle(cards);

            GridLayout grid = new GridLayout(getContext());
            grid.setColumnCount(2);
            int p = UiHelper.dp(getContext(), 20);
            grid.setPadding(p, p, p, p);

            for (GameData.Item it : cards) {
                FrameLayout c = new FrameLayout(getContext());
                int cPad = UiHelper.dp(getContext(), 14);
                c.setPadding(cPad, cPad, cPad, cPad);
                c.setBackground(UiHelper.toyBox3D(getContext(), 0xFF4CC9F0, 24, 3, 5));
                SpriteView sp = new SpriteView(getContext(), "tanda_tanya");
                c.addView(sp, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                c.setTag(it);
                c.setOnClickListener(v -> {
                    if ("tanda_tanya".equals(sp.getKey())) {
                        sp.setKey(it.key);
                        c.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFFFFF, 24, 3, 5));
                        UiHelper.bounce(c);
                        host.sfx().tap();
                        if (firstCard == null) {
                            firstCard = c;
                        } else {
                            FrameLayout prev = firstCard;
                            SpriteView prevSp = (SpriteView) prev.getChildAt(0);
                            firstCard = null;
                            GameData.Item pIt = (GameData.Item) prev.getTag();
                            if (pIt.key.equals(it.key)) {
                                host.sfx().good();
                                host.sfx().playVoice("n_" + it.key);
                                burstView(c, null, 20);
                                pairsLeft--;
                                if (pairsLeft == 0) {
                                    handler.postDelayed(() -> host.celebrate(this::nextRound, null), 700);
                                }
                            } else {
                                host.sfx().wrong();
                                handler.postDelayed(() -> {
                                    prevSp.setKey("tanda_tanya");
                                    prev.setBackground(UiHelper.toyBox3D(getContext(), 0xFF4CC9F0, 24, 3, 5));
                                    sp.setKey("tanda_tanya");
                                    c.setBackground(UiHelper.toyBox3D(getContext(), 0xFF4CC9F0, 24, 3, 5));
                                }, 700);
                            }
                        }
                    }
                });
                GridLayout.LayoutParams glp = new GridLayout.LayoutParams();
                glp.width = 0; glp.height = 0;
                glp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                glp.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                int m = UiHelper.dp(getContext(), 6);
                glp.setMargins(m, m, m, m);
                grid.addView(c, glp);
            }
            addView(grid, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("pasangan_intro");
        }

        @Override public void onReplay() { host.sfx().playVoice("pasangan_intro"); }
    }

    /* 15. Hitung Buah */
    public static class HitungGame extends BaseGameView {
        private int targetN = 3, got = 0;

        public HitungGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }

        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            targetN = 1 + rnd.nextInt(5);
            got = 0;
            List<GameData.Item> fruits = new ArrayList<>();
            for (GameData.Item it : GameData.ITEMS.values()) if ("fruit".equals(it.cat)) fruits.add(it);
            GameData.Item f = fruits.get(rnd.nextInt(fruits.size()));

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            TextView prompt = new TextView(getContext());
            prompt.setText("Petik " + targetN + " " + f.name + "  (0 / " + targetN + ")");
            prompt.setTextSize(22);
            prompt.setTextColor(0xFF2A2140);
            prompt.setTypeface(null, Typeface.BOLD);
            prompt.setPadding(UiHelper.dp(getContext(), 18), UiHelper.dp(getContext(), 8), UiHelper.dp(getContext(), 18), UiHelper.dp(getContext(), 8));
            prompt.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFD23F, 99, 3, 4));
            col.addView(prompt);

            GridLayout row = new GridLayout(getContext());
            row.setColumnCount(3);
            row.setPadding(0, UiHelper.dp(getContext(), 24), 0, 0);

            for (int i = 0; i < 6; i++) {
                FrameLayout fv = new FrameLayout(getContext());
                int pad = UiHelper.dp(getContext(), 8);
                fv.setPadding(pad, pad, pad, pad);
                fv.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFFAF0, 20, 3, 4));
                fv.addView(new SpriteView(getContext(), f.key), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                int sz = UiHelper.dp(getContext(), 84);
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.width = sz; lp.height = sz;
                int m = UiHelper.dp(getContext(), 6);
                lp.setMargins(m, m, m, m);
                fv.setOnClickListener(v -> {
                    if (fv.getVisibility() != VISIBLE || got >= targetN) return;
                    fv.setVisibility(INVISIBLE);
                    got++;
                    prompt.setText("Petik " + targetN + " " + f.name + "  (" + got + " / " + targetN + ")");
                    host.sfx().pop();
                    host.sfx().playVoice("num_" + got);
                    burstView(prompt, null, 14);
                    if (got >= targetN) {
                        handler.postDelayed(() -> host.celebrate(this::nextRound, null), 700);
                    }
                });
                row.addView(fv, lp);
            }
            col.addView(row);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("hitung_ayo");
        }

        @Override public void onReplay() { host.sfx().playVoice("hitung_ayo"); }
    }
}
