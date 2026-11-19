package com.gabink.tamanbermain;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GamesPack1 {

    /* 1. Balon */
    public static class BalonGame extends BaseGameView {
        private int popped = 0, spawned = 0;
        private TextView counter;
        private final String[] colNames = {"merah", "oranye", "kuning", "hijau", "biru", "ungu", "merahmuda"};
        private final int[] colVals = {0xFFE63946, 0xFFFF8C1A, 0xFFFFD23F, 0xFF3FB950, 0xFF2F80ED, 0xFF8E44EC, 0xFFFF7EB6};

        public BalonGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }

        @Override public void start() {
            counter = new TextView(getContext());
            counter.setText("🎈 0");
            counter.setTextSize(20);
            counter.setTextColor(0xFF2A2140);
            counter.setTypeface(null, Typeface.BOLD);
            counter.setPadding(UiHelper.dp(getContext(), 14), UiHelper.dp(getContext(), 6), UiHelper.dp(getContext(), 14), UiHelper.dp(getContext(), 6));
            counter.setBackground(UiHelper.toyBox(getContext(), 0xFFFFFAF0, 99, 3));
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.TOP | Gravity.END);
            lp.setMargins(0, UiHelper.dp(getContext(), 10), UiHelper.dp(getContext(), 14), 0);
            addView(counter, lp);
            host.sfx().playVoice("balon_intro");
            scheduleSpawn();
        }

        private void scheduleSpawn() {
            if (!alive) return;
            spawnBalloon();
            handler.postDelayed(this::scheduleSpawn, 950);
        }

        private void spawnBalloon() {
            if (getWidth() <= 0 || getHeight() <= 0 || getChildCount() > 10) return;
            spawned++;
            boolean rainbow = (spawned % 5 == 0);
            int idx = rnd.nextInt(colNames.length);
            int w = UiHelper.dp(getContext(), 78 + rnd.nextInt(24));
            int h = Math.round(w * 1.35f);

            PlaygroundViews.BalloonView b = new PlaygroundViews.BalloonView(getContext(), colVals[idx], rainbow);
            LayoutParams lp = new LayoutParams(w, h);
            b.setX(rnd.nextInt(Math.max(1, getWidth() - w)));
            b.setY(getHeight() + h);
            addView(b, lp);

            ObjectAnimator anim = ObjectAnimator.ofFloat(b, "y", getHeight() + h, -h * 1.2f);
            anim.setDuration(4800 + rnd.nextInt(2200));
            anim.setInterpolator(new LinearInterpolator());
            anim.addListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(Animator animation) { removeView(b); }
            });
            anim.start();

            b.setOnTouchListener((v, ev) -> {
                if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    anim.cancel();
                    removeView(b);
                    host.sfx().pop();
                    burstView(b, new int[]{colVals[idx], 0xFFFFFFFF}, rainbow ? 34 : 20);
                    popped += rainbow ? 2 : 1;
                    counter.setText("🎈 " + popped);
                    if (popped % 10 == 0) host.celebrate(() -> {}, null);
                    else host.sfx().playVoice("c_" + colNames[idx]);
                    return true;
                }
                return false;
            });
        }

        @Override public void onReplay() { host.sfx().playVoice("balon_intro"); }
    }

    /* 2. Gelembung Laut */
    public static class GelembungGame extends BaseGameView {
        private int popped = 0;
        private TextView counter;

        public GelembungGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }

        @Override public void start() {
            counter = new TextView(getContext());
            counter.setText("🫧 0");
            counter.setTextSize(20);
            counter.setTextColor(0xFF2A2140);
            counter.setTypeface(null, Typeface.BOLD);
            counter.setPadding(UiHelper.dp(getContext(), 14), UiHelper.dp(getContext(), 6), UiHelper.dp(getContext(), 14), UiHelper.dp(getContext(), 6));
            counter.setBackground(UiHelper.toyBox(getContext(), 0xFFFFFAF0, 99, 3));
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.TOP | Gravity.END);
            lp.setMargins(0, UiHelper.dp(getContext(), 10), UiHelper.dp(getContext(), 14), 0);
            addView(counter, lp);
            host.sfx().playVoice("gelembung_intro");
            scheduleSpawn();
        }

        private void scheduleSpawn() {
            if (!alive) return;
            spawnBubble();
            handler.postDelayed(this::scheduleSpawn, 1050);
        }

        private void spawnBubble() {
            if (getWidth() <= 0 || getHeight() <= 0 || getChildCount() > 9) return;
            String key = GameData.SEA_ANIMALS.get(rnd.nextInt(GameData.SEA_ANIMALS.size()));
            int sz = UiHelper.dp(getContext(), 90 + rnd.nextInt(26));

            PlaygroundViews.BubbleView b = new PlaygroundViews.BubbleView(getContext(), key);
            b.setX(rnd.nextInt(Math.max(1, getWidth() - sz)));
            b.setY(getHeight() + sz);
            addView(b, new LayoutParams(sz, sz));

            ObjectAnimator anim = ObjectAnimator.ofFloat(b, "y", getHeight() + sz, -sz * 1.5f);
            anim.setDuration(5500 + rnd.nextInt(2400));
            anim.setInterpolator(new LinearInterpolator());
            anim.addListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(Animator animation) { removeView(b); }
            });
            anim.start();

            b.setOnTouchListener((v, ev) -> {
                if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    anim.cancel();
                    removeView(b);
                    host.sfx().pop();
                    burstView(b, new int[]{0xFFFFFFFF, 0xFFBDF0FF, 0xFF7FD3E8}, 22);
                    popped++;
                    counter.setText("🫧 " + popped);
                    if (popped % 10 == 0) host.celebrate(() -> {}, null);
                    else host.sfx().playVoice("n_" + key);
                    return true;
                }
                return false;
            });
        }

        @Override public void onReplay() { host.sfx().playVoice("gelembung_intro"); }
    }

    /* 3. Tangkap Bintang */
    public static class TangkapGame extends BaseGameView {
        private int count = 0;
        private TextView jar;
        private final double[] notes = {523.25, 587.33, 659.25, 698.46, 783.99, 880.0, 987.77, 1046.5};

        public TangkapGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }

        @Override public void start() { resetRound(); host.sfx().playVoice("tangkap_intro"); }

        private void resetRound() {
            removeAllViews();
            count = 0;
            jar = new TextView(getContext());
            jar.setText("🫙  0 / 8");
            jar.setTextSize(20);
            jar.setTextColor(0xFF2A2140);
            jar.setTypeface(null, Typeface.BOLD);
            jar.setPadding(UiHelper.dp(getContext(), 20), UiHelper.dp(getContext(), 8), UiHelper.dp(getContext(), 20), UiHelper.dp(getContext(), 8));
            jar.setBackground(UiHelper.toyBox(getContext(), 0xFFFFD23F, 99, 3));
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            lp.bottomMargin = UiHelper.dp(getContext(), 18);
            addView(jar, lp);
            scheduleStar();
        }

        private void scheduleStar() {
            if (!alive || count >= 8) return;
            spawnStar();
            handler.postDelayed(this::scheduleStar, 850);
        }

        private void spawnStar() {
            if (getWidth() <= 0 || getHeight() <= 0 || getChildCount() > 8) return;
            boolean comet = rnd.nextFloat() < 0.22f;
            int sz = UiHelper.dp(getContext(), 76);
            SpriteView st = new SpriteView(getContext(), comet ? "komet" : "bintang");
            st.setX(rnd.nextInt(Math.max(1, getWidth() - sz)));
            st.setY(-sz);
            addView(st, new LayoutParams(sz, sz));

            ObjectAnimator anim = ObjectAnimator.ofFloat(st, "y", -sz, getHeight() + sz);
            anim.setDuration(4200 + rnd.nextInt(1800));
            anim.setInterpolator(new LinearInterpolator());
            anim.addListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(Animator animation) { removeView(st); }
            });
            anim.start();

            st.setOnTouchListener((v, ev) -> {
                if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    anim.cancel();
                    removeView(st);
                    host.sfx().note(notes[count % notes.length]);
                    burstView(st, null, comet ? 28 : 16);
                    count = Math.min(8, count + (comet ? 2 : 1));
                    jar.setText("🫙  " + count + " / 8");
                    UiHelper.bounce(jar);
                    if (count >= 8) {
                        handler.postDelayed(() -> host.celebrate(this::resetRound, "tangkap_done"), 500);
                    }
                    return true;
                }
                return false;
            });
        }

        @Override public void onReplay() { host.sfx().playVoice("tangkap_intro"); }
    }

    /* 4. Kembang Api */
    public static class KembangGame extends BaseGameView {
        private TextView hint;
        private final String[] colNames = {"merah", "oranye", "kuning", "hijau", "biru", "ungu", "merahmuda"};
        private final int[] colVals = {0xFFE63946, 0xFFFF8C1A, 0xFFFFD23F, 0xFF3FB950, 0xFF2F80ED, 0xFF8E44EC, 0xFFFF7EB6};

        public KembangGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }

        @Override public void start() {
            hint = new TextView(getContext());
            hint.setText("Ketuk langitnya! 🎆");
            hint.setTextSize(22);
            hint.setTextColor(0xFFFFFFFF);
            hint.setTypeface(null, Typeface.BOLD);
            addView(hint, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER));
            host.sfx().playVoice("kembang_intro");
        }

        @Override public boolean onTouchEvent(MotionEvent ev) {
            if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
                if (hint != null) hint.setVisibility(GONE);
                int idx = rnd.nextInt(colNames.length);
                host.sfx().noise(420, 0.3);
                host.sfx().tone(140, 50, 320, 0.28);
                host.particles().burst(ev.getX(), ev.getY(), new int[]{colVals[idx], 0xFFFFF7C2, 0xFFFFFFFF}, 45);
                host.sfx().playVoice("c_" + colNames[idx]);
                return true;
            }
            return super.onTouchEvent(ev);
        }

        @Override public void onReplay() { host.sfx().playVoice("kembang_intro"); }
    }

    /* 5. Piano */
    public static class PianoGame extends BaseGameView {
        private final double[] freqs = {261.63, 293.66, 329.63, 349.23, 392.00, 440.00, 493.88, 523.25};
        private final String[] labels = {"Do", "Re", "Mi", "Fa", "Sol", "La", "Si", "Do!"};
        private final int[] colors = {0xFFE63946, 0xFFFF8C1A, 0xFFFFD23F, 0xFF3FB950, 0xFF2F80ED, 0xFF8E44EC, 0xFFFF7EB6, 0xFFE63946};

        public PianoGame(Context c, Host h) { super(c, h); setBackgroundColor(0xFF2A2140); }

        @Override public void start() {
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            int p = UiHelper.dp(getContext(), 10);
            row.setPadding(p, p, p, p);
            for (int i = 0; i < freqs.length; i++) {
                final int idx = i;
                Button key = new Button(getContext());
                key.setText(labels[i]);
                key.setTextSize(18);
                key.setTextColor(0xFF2A2140);
                key.setTypeface(null, Typeface.BOLD);
                key.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                key.setPadding(0, 0, 0, UiHelper.dp(getContext(), 20));
                key.setBackground(UiHelper.toyBox(getContext(), colors[i], 18, 3));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
                lp.setMargins(UiHelper.dp(getContext(), 3), 0, UiHelper.dp(getContext(), 3), 0);
                key.setOnTouchListener((v, ev) -> {
                    if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        UiHelper.bounce(key);
                        host.sfx().note(freqs[idx]);
                        burstView(key, new int[]{colors[idx], 0xFFFFFFFF}, 14);
                        return true;
                    }
                    return false;
                });
                row.addView(key, lp);
            }
            addView(row, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("piano_intro");
        }

        @Override public void onReplay() { host.sfx().playVoice("piano_intro"); }
    }

    /* 6. Alat Musik */
    public static class MusikGame extends BaseGameView {
        public MusikGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }

        @Override public void start() {
            GridLayout grid = new GridLayout(getContext());
            grid.setColumnCount(2);
            int p = UiHelper.dp(getContext(), 14);
            grid.setPadding(p, p, p, p);
            String[][] inst = {
                {"drum", "Drum", "110", "55"},
                {"gitar", "Gitar", "330", "330"},
                {"terompet", "Terompet", "523", "587"},
                {"lonceng", "Lonceng", "1200", "1200"},
                {"gendang", "Gendang", "160", "100"},
                {"tepuk", "Tepuk", "800", "400"}
            };
            int[] cols = {0xFFFFD23F, 0xFFFF5D8F, 0xFF4CC9F0, 0xFF6CC56F, 0xFFFF9F1C, 0xFFA77FF2};
            for (int i = 0; i < inst.length; i++) {
                final int idx = i;
                LinearLayout b = new LinearLayout(getContext());
                b.setOrientation(LinearLayout.VERTICAL);
                b.setGravity(Gravity.CENTER);
                b.setBackground(UiHelper.toyBox3D(getContext(), cols[i], 24, 3, 5));
                SpriteView sv = new SpriteView(getContext(), inst[i][0]);
                b.addView(sv, new LinearLayout.LayoutParams(UiHelper.dp(getContext(), 84), UiHelper.dp(getContext(), 84)));
                TextView lbl = new TextView(getContext());
                lbl.setText(inst[i][1]);
                lbl.setTextSize(18);
                lbl.setTextColor(0xFF2A2140);
                lbl.setTypeface(null, Typeface.BOLD);
                b.addView(lbl);
                b.setOnClickListener(v -> {
                    UiHelper.bounce(b);
                    host.sfx().tone(Double.parseDouble(inst[idx][2]), Double.parseDouble(inst[idx][3]), 280, 0.28);
                    burstView(b, new int[]{cols[idx], 0xFFFFFFFF}, 16);
                });
                GridLayout.LayoutParams glp = new GridLayout.LayoutParams();
                glp.width = 0;
                glp.height = 0;
                glp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                glp.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                int m = UiHelper.dp(getContext(), 6);
                glp.setMargins(m, m, m, m);
                grid.addView(b, glp);
            }
            addView(grid, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("musik_intro");
        }

        @Override public void onReplay() { host.sfx().playVoice("musik_intro"); }
    }

    /* 7. Cilukba */
    public static class CilukbaGame extends BaseGameView {
        private boolean open = false;
        private String curKey;

        public CilukbaGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }

        @Override public void start() {
            curKey = GameData.VOCAL_ANIMALS.get(rnd.nextInt(GameData.VOCAL_ANIMALS.size()));
            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER);

            FrameLayout card = new FrameLayout(getContext());
            int sz = UiHelper.dp(getContext(), 220);
            int pad = UiHelper.dp(getContext(), 22);
            card.setPadding(pad, pad, pad, pad);
            card.setBackground(UiHelper.toyBox3D(getContext(), 0xFFFFFFFF, 36, 4, 6));
            SpriteView sp = new SpriteView(getContext(), "selimut");
            card.addView(sp, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            col.addView(card, new LinearLayout.LayoutParams(sz, sz));

            TextView label = new TextView(getContext());
            label.setText("Di mana, ya?");
            label.setTextSize(24);
            label.setTextColor(0xFF2A2140);
            label.setTypeface(null, Typeface.BOLD);
            label.setPadding(0, UiHelper.dp(getContext(), 18), 0, 0);
            col.addView(label);

            card.setOnClickListener(v -> {
                UiHelper.bounce(card);
                if (!open) {
                    open = true;
                    GameData.Item it = GameData.ITEMS.get(curKey);
                    sp.setKey(curKey);
                    label.setText("Cilukba! " + it.name + "!");
                    host.sfx().boing();
                    host.sfx().playReal(curKey);
                    host.sfx().playVoice("ada_" + curKey);
                    burstView(card, null, 28);
                } else {
                    open = false;
                    curKey = GameData.VOCAL_ANIMALS.get(rnd.nextInt(GameData.VOCAL_ANIMALS.size()));
                    sp.setKey("selimut");
                    label.setText("Di mana, ya?");
                    host.sfx().playVoice("dimana");
                }
            });
            addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            host.sfx().playVoice("dimana");
        }

        @Override public void onReplay() { host.sfx().playVoice("dimana"); }
    }

    /* 8. Telepon */
    public static class TeleponGame extends BaseGameView {
        private TextView screen;
        private String digits = "";

        public TeleponGame(Context c, Host h) { super(c, h); setBackgroundColor(0x00000000); }

        @Override public void start() {
            LinearLayout phone = new LinearLayout(getContext());
            phone.setOrientation(LinearLayout.VERTICAL);
            phone.setGravity(Gravity.CENTER_HORIZONTAL);
            int p = UiHelper.dp(getContext(), 18);
            phone.setPadding(p, p, p, p);
            phone.setBackground(UiHelper.toyBox(getContext(), 0xFFFFFAF0, 32, 4));

            screen = new TextView(getContext());
            screen.setText("📞");
            screen.setTextSize(36);
            screen.setTextColor(0xFF2A2140);
            screen.setTypeface(null, Typeface.BOLD);
            screen.setGravity(Gravity.CENTER);
            screen.setBackground(UiHelper.toyBox(getContext(), 0xFFE6F7FF, 20, 3));
            phone.addView(screen, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, UiHelper.dp(getContext(), 110)));

            GridLayout pad = new GridLayout(getContext());
            pad.setColumnCount(3);
            LinearLayout.LayoutParams plp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, 0, 1f);
            plp.topMargin = UiHelper.dp(getContext(), 14);
            String[] keys = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "📞", "0", "✖"};
            for (String k : keys) {
                Button b = new Button(getContext());
                b.setText(k);
                b.setTextSize(24);
                b.setTextColor(0xFF2A2140);
                b.setTypeface(null, Typeface.BOLD);
                int bg = "📞".equals(k) ? 0xFF6CC56F : "✖".equals(k) ? 0xFFFF5D8F : 0xFFFFD23F;
                b.setBackground(UiHelper.toyBox(getContext(), bg, 20, 3));
                b.setOnClickListener(v -> {
                    UiHelper.bounce(b);
                    if ("📞".equals(k)) {
                        String ak = GameData.VOCAL_ANIMALS.get(rnd.nextInt(GameData.VOCAL_ANIMALS.size()));
                        GameData.Item it = GameData.ITEMS.get(ak);
                        screen.setText(it.emoji + "  " + it.name);
                        host.sfx().playReal(ak);
                        host.sfx().playVoice("halo_" + ak);
                        burstView(screen, null, 20);
                    } else if ("✖".equals(k)) {
                        digits = "";
                        screen.setText("📞");
                        host.sfx().playVoice("dadah");
                    } else {
                        digits = (digits + k).length() > 8 ? (digits + k).substring(1) : (digits + k);
                        screen.setText(digits);
                        host.sfx().tap();
                        host.sfx().playVoice("num_" + k);
                    }
                });
                GridLayout.LayoutParams glp = new GridLayout.LayoutParams();
                glp.width = 0; glp.height = 0;
                glp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                glp.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                int m = UiHelper.dp(getContext(), 4);
                glp.setMargins(m, m, m, m);
                pad.addView(b, glp);
            }
            phone.addView(pad, plp);

            LayoutParams rootLp = new LayoutParams(UiHelper.dp(getContext(), 320), LayoutParams.MATCH_PARENT, Gravity.CENTER);
            int rm = UiHelper.dp(getContext(), 16);
            rootLp.setMargins(rm, rm, rm, rm);
            addView(phone, rootLp);
            host.sfx().playVoice("menelepon");
        }

        @Override public void onReplay() { host.sfx().playVoice("menelepon"); }
    }
}
