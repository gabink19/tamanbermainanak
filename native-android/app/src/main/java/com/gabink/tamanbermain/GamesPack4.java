package com.gabink.tamanbermain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class GamesPack4 {

    /* 25. Dokter Hewan */
    public static class DokterGame extends BaseGameView {
        private int step = 0;
        private String patient;

        public DokterGame(Context c, Host h) { super(c, h); setBackgroundColor(0xFFE6F7FF); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            step = 0;
            String[] pts = {"kucing", "anjing", "kelinci", "panda", "beruang", "monyet"};
            patient = pts[rnd.nextInt(pts.length)];
            GameData.Item it = GameData.ITEMS.get(patient);

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            SpriteView ani = new SpriteView(getContext(), patient);
            ani.setMood(CartoonArt.Mood.SICK);
            int aniSz = UiHelper.dp(getContext(), 170);
            col.addView(ani, new LinearLayout.LayoutParams(aniSz, aniSz));

            TextView status = new TextView(getContext());
            status.setText("Ayo obati " + it.name + "!");
            status.setTextSize(20);
            status.setTextColor(0xFF2A2140);
            status.setTypeface(null, Typeface.BOLD);
            col.addView(status);

            LinearLayout tools = new LinearLayout(getContext());
            tools.setOrientation(LinearLayout.HORIZONTAL);
            tools.setGravity(Gravity.CENTER);
            tools.setPadding(0, UiHelper.dp(getContext(), 28), 0, 0);

            String[][] dok = {{"termometer", "🌡️"}, {"stetoskop", "🩺"}, {"obat", "💊"}, {"plester", "🩹"}};
            List<Button> btns = new ArrayList<>();
            for (int i = 0; i < dok.length; i++) {
                final int idx = i;
                Button b = new Button(getContext());
                b.setText(dok[i][1]);
                b.setTextSize(32);
                b.setBackground(UiHelper.toyBox3D(getContext(), i == 0 ? 0xFFFFD23F : 0xFFFFFAF0, 20, 3, 4));
                int sz = UiHelper.dp(getContext(), 72);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sz, sz);
                lp.setMargins(UiHelper.dp(getContext(), 5), 0, UiHelper.dp(getContext(), 5), 0);
                b.setOnClickListener(v -> {
                    if (idx != step) { UiHelper.shake(b); host.sfx().wrong(); return; }
                    b.setAlpha(0.3f);
                    step++;
                    UiHelper.bounce(ani);
                    host.sfx().good();
                    host.sfx().playVoice("dok_" + dok[idx][0]);
                    burstView(ani, null, 18);
                    if (step < dok.length) {
                        btns.get(step).setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFD23F, 20, 3, 4));
                    } else {
                        ani.setMood(CartoonArt.Mood.HAPPY);
                        ani.react();
                        status.setText(it.name + " sudah sembuh! 💖");
                        host.sfx().playReal(patient);
                        handler.postDelayed(() -> host.celebrate(this::nextRound, "sembuh_" + patient), 1200);
                    }
                });
                btns.add(b);
                tools.addView(b, lp);
            }
            col.addView(tools);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("dokter_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice("dokter_intro"); }
    }

    /* 26. Dandani Teman */
    public static class DandanGame extends BaseGameView {
        private int count = 0;

        public DandanGame(Context c, Host h) { super(c, h); setBackgroundColor(0xFFFFE6F2); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            count = 0;
            String[] pts = {"kucing", "anjing", "kelinci", "panda", "beruang"};
            String k = pts[rnd.nextInt(pts.length)];
            GameData.Item it = GameData.ITEMS.get(k);

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            TextView hatTv = new TextView(getContext());
            hatTv.setText(""); hatTv.setTextSize(48); hatTv.setGravity(Gravity.CENTER);
            SpriteView aniTv = new SpriteView(getContext(), k);
            int aniSz = UiHelper.dp(getContext(), 155);
            TextView neckTv = new TextView(getContext());
            neckTv.setText(""); neckTv.setTextSize(42); neckTv.setGravity(Gravity.CENTER);
            col.addView(hatTv); col.addView(aniTv, new LinearLayout.LayoutParams(aniSz, aniSz)); col.addView(neckTv);

            LinearLayout opts = new LinearLayout(getContext());
            opts.setOrientation(LinearLayout.HORIZONTAL);
            opts.setGravity(Gravity.CENTER);
            opts.setPadding(0, UiHelper.dp(getContext(), 20), 0, 0);

            String[][] accs = {{"🎩", "topi", "hat"}, {"👑", "mahkota", "hat"}, {"🕶️", "kacamatahitam", "neck"}, {"🎀", "pita", "neck"}, {"🧣", "syal", "neck"}};
            for (String[] a : accs) {
                Button b = new Button(getContext());
                b.setText(a[0]); b.setTextSize(30);
                b.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFFAF0, 99, 3, 4));
                int sz = UiHelper.dp(getContext(), 60);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sz, sz);
                lp.setMargins(UiHelper.dp(getContext(), 4), 0, UiHelper.dp(getContext(), 4), 0);
                b.setOnClickListener(v -> {
                    if ("hat".equals(a[2])) hatTv.setText(a[0]);
                    else neckTv.setText(a[0]);
                    UiHelper.bounce(aniTv);
                    aniTv.setMood(CartoonArt.Mood.HAPPY);
                    aniTv.react();
                    host.sfx().pop();
                    host.sfx().playVoice("aks_" + a[1]);
                    burstView(aniTv, null, 18);
                    count++;
                    if (count >= 3) {
                        handler.postDelayed(() -> host.celebrate(this::nextRound, "dandan_done"), 900);
                    }
                });
                opts.addView(b, lp);
            }
            col.addView(opts);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("dandan_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice("dandan_intro"); }
    }

    /* 27. Mandikan Hewan */
    public static class MandiGame extends BaseGameView {
        private int rubs = 0;

        public MandiGame(Context c, Host h) { super(c, h); setBackgroundColor(0xFFBFE8FF); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            rubs = 0;
            String[] pts = {"kucing", "anjing", "gajah", "kuda", "sapi"};
            String k = pts[rnd.nextInt(pts.length)];
            GameData.Item it = GameData.ITEMS.get(k);

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            LinearLayout aniBox = new LinearLayout(getContext());
            aniBox.setOrientation(LinearLayout.HORIZONTAL);
            aniBox.setGravity(Gravity.CENTER);
            SpriteView ani = new SpriteView(getContext(), k);
            int aniSz = UiHelper.dp(getContext(), 155);
            aniBox.addView(ani, new LinearLayout.LayoutParams(aniSz, aniSz));
            TextView fx = new TextView(getContext());
            fx.setText(" 🟤🟤");
            fx.setTextSize(34);
            aniBox.addView(fx);
            col.addView(aniBox);

            TextView info = new TextView(getContext());
            info.setText("Ketuk spons 🧽 untuk menggosok! (0 / 5)");
            info.setTextSize(18);
            info.setTextColor(0xFF2A2140);
            info.setTypeface(null, Typeface.BOLD);
            info.setPadding(0, UiHelper.dp(getContext(), 12), 0, UiHelper.dp(getContext(), 20));
            col.addView(info);

            Button sponge = new Button(getContext());
            sponge.setText("🧽 Gosok!");
            sponge.setAllCaps(false);
            sponge.setTextSize(24);
            sponge.setTextColor(0xFF2A2140);
            sponge.setTypeface(null, Typeface.BOLD);
            sponge.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFD23F, 99, 4, 6));
            sponge.setPadding(UiHelper.dp(getContext(), 28), UiHelper.dp(getContext(), 12), UiHelper.dp(getContext(), 28), UiHelper.dp(getContext(), 12));
            sponge.setOnClickListener(v -> {
                if (rubs >= 5) return;
                rubs++;
                UiHelper.bounce(ani);
                ani.react();
                host.sfx().pop();
                burstView(ani, new int[]{0xFFFFFFFF, 0xFFBDF0FF, 0xFF4CC9F0}, 20);
                info.setText("Ketuk spons 🧽 untuk menggosok! (" + rubs + " / 5)");
                if (rubs == 2) fx.setText(" 🫧🫧");
                else if (rubs == 4) fx.setText(" ✨");
                else if (rubs >= 5) {
                    fx.setText(" ✨✨");
                    ani.setMood(CartoonArt.Mood.HAPPY);
                    host.sfx().playReal(k);
                    handler.postDelayed(() -> host.celebrate(this::nextRound, "mandi_done"), 800);
                }
            });
            col.addView(sponge);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("mandi_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice("mandi_intro"); }
    }

    /* 28. Sikat Gigi */
    public static class GigiGame extends BaseGameView {
        private int left = 4;

        public GigiGame(Context c, Host h) { super(c, h); setBackgroundColor(0xFFFFD1DC); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            left = 4;
            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            TextView tooth = new TextView(getContext());
            tooth.setText("🦷");
            tooth.setTextSize(108);
            tooth.setGravity(Gravity.CENTER);
            col.addView(tooth);

            TextView hint = new TextView(getContext());
            hint.setText("Ketuk semua kuman 🦠 untuk menyikatnya!");
            hint.setTextSize(17);
            hint.setTextColor(0xFF2A2140);
            hint.setTypeface(null, Typeface.BOLD);
            col.addView(hint);

            LinearLayout germs = new LinearLayout(getContext());
            germs.setOrientation(LinearLayout.HORIZONTAL);
            germs.setGravity(Gravity.CENTER);
            germs.setPadding(0, UiHelper.dp(getContext(), 22), 0, 0);

            for (int i = 0; i < 4; i++) {
                TextView g = new TextView(getContext());
                g.setText("🦠");
                g.setTextSize(48);
                g.setPadding(UiHelper.dp(getContext(), 8), UiHelper.dp(getContext(), 8), UiHelper.dp(getContext(), 8), UiHelper.dp(getContext(), 8));
                g.setOnClickListener(v -> {
                    if (g.getVisibility() != VISIBLE) return;
                    g.setVisibility(INVISIBLE);
                    host.sfx().pop();
                    burstView(g, new int[]{0xFFFFFFFF, 0xFF4CC9F0}, 18);
                    left--;
                    if (left == 0) {
                        tooth.setText("✨🦷✨");
                        UiHelper.bounce(tooth);
                        handler.postDelayed(() -> host.celebrate(this::nextRound, "gigi_done"), 700);
                    }
                });
                germs.addView(g);
            }
            col.addView(germs);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("gigi_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice("gigi_intro"); }
    }

    /* 29. Kue Ulang Tahun */
    public static class KueGame extends BaseGameView {
        private int candles = 0, blown = 0;
        private boolean blowPhase = false;

        public KueGame(Context c, Host h) { super(c, h); setBackgroundColor(0xFFFFF3C4); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            candles = 0; blown = 0; blowPhase = false;

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            LinearLayout candleRow = new LinearLayout(getContext());
            candleRow.setOrientation(LinearLayout.HORIZONTAL);
            candleRow.setGravity(Gravity.CENTER);
            TextView[] flames = new TextView[3];
            for (int i = 0; i < 3; i++) {
                final int idx = i;
                TextView fl = new TextView(getContext());
                fl.setText("🕯️");
                fl.setTextSize(54);
                fl.setAlpha(0.25f);
                fl.setPadding(UiHelper.dp(getContext(), 8), 0, UiHelper.dp(getContext(), 8), 0);
                fl.setOnClickListener(v -> {
                    if (!blowPhase && fl.getAlpha() < 0.5f) {
                        fl.setAlpha(1f);
                        fl.setText("🔥\n🕯️");
                        fl.setTextSize(34);
                        fl.setGravity(Gravity.CENTER);
                        candles++;
                        host.sfx().pop();
                        burstView(fl, null, 14);
                        if (candles >= 3) {
                            blowPhase = true;
                            host.sfx().playVoice("kue_tiup");
                        }
                    } else if (blowPhase && fl.getText().toString().contains("🔥")) {
                        fl.setText("💨\n🕯️");
                        blown++;
                        host.sfx().noise(180, 0.25);
                        if (blown >= 3) {
                            handler.postDelayed(() -> host.celebrate(this::nextRound, "kue_done"), 700);
                        }
                    }
                });
                flames[i] = fl;
                candleRow.addView(fl);
            }

            TextView cake = new TextView(getContext());
            cake.setText("🎂");
            cake.setTextSize(116);
            cake.setGravity(Gravity.CENTER);

            col.addView(candleRow);
            col.addView(cake);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("kue_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice(blowPhase ? "kue_tiup" : "kue_intro"); }
    }

    /* 30. Kereta Hewan */
    public static class KeretaGame extends BaseGameView {
        private int boarded = 0;

        public KeretaGame(Context c, Host h) { super(c, h); setBackgroundColor(0xFFCFEAFF); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            boarded = 0;
            List<String> pool = new ArrayList<>(GameData.VOCAL_ANIMALS);
            java.util.Collections.shuffle(pool);
            List<String> sel = pool.subList(0, 3);

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            TextView train = new TextView(getContext());
            train.setText("🚂 🚃 🚃 🚃");
            train.setTextSize(42);
            train.setGravity(Gravity.CENTER);
            train.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFD23F, 24, 3, 5));
            train.setPadding(UiHelper.dp(getContext(), 16), UiHelper.dp(getContext(), 14), UiHelper.dp(getContext(), 16), UiHelper.dp(getContext(), 14));
            col.addView(train);

            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER);
            row.setPadding(0, UiHelper.dp(getContext(), 32), 0, 0);

            StringBuilder riders = new StringBuilder("🚂");
            for (String ak : sel) {
                GameData.Item it = GameData.ITEMS.get(ak);
                FrameLayout tv = new FrameLayout(getContext());
                int pad = UiHelper.dp(getContext(), 10);
                tv.setPadding(pad, pad, pad, pad);
                tv.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFFFFF, 22, 3, 5));
                SpriteView sv = new SpriteView(getContext(), ak);
                tv.addView(sv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                int sz = UiHelper.dp(getContext(), 90);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sz, sz);
                lp.setMargins(UiHelper.dp(getContext(), 6), 0, UiHelper.dp(getContext(), 6), 0);
                tv.setOnClickListener(v -> {
                    if (tv.getVisibility() != VISIBLE) return;
                    tv.setVisibility(INVISIBLE);
                    boarded++;
                    riders.append(" ").append(it.emoji);
                    train.setText(riders.toString());
                    UiHelper.bounce(train);
                    host.sfx().playReal(ak);
                    host.sfx().playVoice("ada_" + ak);
                    burstView(train, null, 18);
                    if (boarded >= 3) {
                        handler.postDelayed(() -> {
                            host.sfx().playReal("kereta");
                            host.celebrate(this::nextRound, "kereta_done");
                        }, 1100);
                    }
                });
                row.addView(tv, lp);
            }
            col.addView(row);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("kereta_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice("kereta_intro"); }
    }

    /* 31. Mewarnai (Custom Canvas Coloring House) */
    public static class WarnaiGame extends BaseGameView {
        private int activeCol = 0xFFE63946;
        private final int[] partColors = {0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF};
        private int painted = 0;

        public WarnaiGame(Context c, Host h) { super(c, h); setBackgroundColor(0xFFFFFAF0); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            painted = 0;
            for (int i = 0; i < 4; i++) partColors[i] = 0xFFFFFFFF;

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            View canvasView = new View(getContext()) {
                final Paint fp = new Paint(Paint.ANTI_ALIAS_FLAG);
                final Paint sp = new Paint(Paint.ANTI_ALIAS_FLAG);
                final android.graphics.Path roof = new android.graphics.Path();
                { sp.setStyle(Paint.Style.STROKE); sp.setColor(0xFF2A2140); sp.setStrokeWidth(UiHelper.dp(getContext(), 4)); }
                @Override protected void onDraw(Canvas c) {
                    float w = getWidth(), h = getHeight(), cx = w / 2, cy = h / 2;
                    float bw = Math.min(w, h) * 0.56f;
                    // Roof (part 0)
                    roof.reset();
                    roof.moveTo(cx, cy - bw * 0.65f);
                    roof.lineTo(cx + bw * 0.6f, cy - bw * 0.1f);
                    roof.lineTo(cx - bw * 0.6f, cy - bw * 0.1f);
                    roof.close();
                    fp.setColor(partColors[0]); c.drawPath(roof, fp); c.drawPath(roof, sp);
                    // Wall (part 1)
                    RectF wall = new RectF(cx - bw * 0.45f, cy - bw * 0.1f, cx + bw * 0.45f, cy + bw * 0.55f);
                    fp.setColor(partColors[1]); c.drawRect(wall, fp); c.drawRect(wall, sp);
                    // Door (part 2)
                    RectF door = new RectF(cx - bw * 0.14f, cy + bw * 0.15f, cx + bw * 0.14f, cy + bw * 0.55f);
                    fp.setColor(partColors[2]); c.drawRect(door, fp); c.drawRect(door, sp);
                    // Sun (part 3)
                    fp.setColor(partColors[3]);
                    c.drawCircle(cx + bw * 0.52f, cy - bw * 0.52f, bw * 0.16f, fp);
                    c.drawCircle(cx + bw * 0.52f, cy - bw * 0.52f, bw * 0.16f, sp);
                }
                @Override public boolean onTouchEvent(MotionEvent ev) {
                    if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        float w = getWidth(), h = getHeight(), cx = w / 2, cy = h / 2, bw = Math.min(w, h) * 0.56f;
                        float x = ev.getX(), y = ev.getY();
                        int part = -1;
                        if (Math.hypot(x - (cx + bw * 0.52f), y - (cy - bw * 0.52f)) < bw * 0.22f) part = 3;
                        else if (y < cy - bw * 0.1f) part = 0;
                        else if (x > cx - bw * 0.14f && x < cx + bw * 0.14f && y > cy + bw * 0.15f) part = 2;
                        else part = 1;
                        if (partColors[part] == 0xFFFFFFFF) painted++;
                        partColors[part] = activeCol;
                        invalidate();
                        host.sfx().pop();
                        host.particles().burst(x, y, new int[]{activeCol, 0xFFFFFFFF}, 18);
                        if (painted >= 4) handler.postDelayed(() -> host.celebrate(WarnaiGame.this::nextRound, "warnai_done"), 800);
                        return true;
                    }
                    return super.onTouchEvent(ev);
                }
            };
            col.addView(canvasView, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f));

            LinearLayout pal = new LinearLayout(getContext());
            pal.setOrientation(LinearLayout.HORIZONTAL);
            pal.setGravity(Gravity.CENTER);
            pal.setPadding(0, UiHelper.dp(getContext(), 10), 0, UiHelper.dp(getContext(), 20));
            int[] cols = {0xFFE63946, 0xFFFF8C1A, 0xFFFFD23F, 0xFF3FB950, 0xFF2F80ED, 0xFF8E44EC};
            for (int cVal : cols) {
                Button cb = new Button(getContext());
                cb.setBackground(UiHelper.toyBox3D(getContext(), cVal, 99, 3, 4));
                int sz = UiHelper.dp(getContext(), 48);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sz, sz);
                lp.setMargins(UiHelper.dp(getContext(), 4), 0, UiHelper.dp(getContext(), 4), 0);
                cb.setOnClickListener(v -> { activeCol = cVal; UiHelper.bounce(cb); host.sfx().tap(); });
                pal.addView(cb, lp);
            }
            col.addView(pal);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("warnai_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice("warnai_intro"); }
    }

    /* 32. Memancing */
    public static class PancingGame extends BaseGameView {
        private int caught = 0;

        public PancingGame(Context c, Host h) { super(c, h); setBackgroundColor(0xFF4CC9F0); }
        @Override public void start() { nextRound(); }

        private void nextRound() {
            removeAllViews();
            caught = 0;
            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            TextView score = new TextView(getContext());
            score.setText("🎣 Ikan: 0 / 5");
            score.setTextSize(22);
            score.setTextColor(0xFF2A2140);
            score.setTypeface(null, Typeface.BOLD);
            score.setPadding(UiHelper.dp(getContext(), 18), UiHelper.dp(getContext(), 8), UiHelper.dp(getContext(), 18), UiHelper.dp(getContext(), 8));
            score.setBackground(UiHelper.toyBox(getContext(), 0xFFFFD23F, 99, 3));
            col.addView(score);

            GridLayout pond = new GridLayout(getContext());
            pond.setColumnCount(3);
            pond.setPadding(UiHelper.dp(getContext(), 16), UiHelper.dp(getContext(), 28), UiHelper.dp(getContext(), 16), 0);
            String[] fishEmojis = {"🐟", "🐠", "🐡", "🐬", "🐙", "🦀"};
            for (int i = 0; i < 6; i++) {
                TextView fv = new TextView(getContext());
                fv.setText(fishEmojis[i]);
                fv.setTextSize(54);
                fv.setGravity(Gravity.CENTER);
                fv.setPadding(UiHelper.dp(getContext(), 12), UiHelper.dp(getContext(), 16), UiHelper.dp(getContext(), 12), UiHelper.dp(getContext(), 16));
                fv.setOnClickListener(v -> {
                    if (fv.getVisibility() != VISIBLE || caught >= 5) return;
                    fv.setVisibility(INVISIBLE);
                    caught++;
                    score.setText("🎣 Ikan: " + caught + " / 5");
                    host.sfx().pop();
                    host.sfx().playVoice("num_" + caught);
                    burstView(fv, new int[]{0xFFFFFFFF, 0xFFBDF0FF, 0xFFFFD23F}, 20);
                    if (caught >= 5) handler.postDelayed(() -> host.celebrate(this::nextRound, "pancing_done"), 700);
                });
                pond.addView(fv);
            }
            col.addView(pond);
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("pancing_intro");
        }
        @Override public void onReplay() { host.sfx().playVoice("pancing_intro"); }
    }
}
