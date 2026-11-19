package com.gabink.tamanbermain;

import android.app.AlertDialog;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements BaseGameView.Host {
    private SoundManager sfx;
    private Prefs prefs;
    private ParticleView particles;
    private FrameLayout stageContainer;
    private BaseGameView currentGame;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Random rnd = new Random();
    private boolean alive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sfx = SoundManager.get(this);
        prefs = new Prefs(this);

        String gid = getIntent().getStringExtra("game_id");
        GameData.GameDef def = null;
        for (GameData.GameDef g : GameData.GAMES) {
            if (g.id.equals(gid)) { def = g; break; }
        }
        if (def == null) { finish(); return; }

        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(def.dark ? 0xFF14123A : 0xFFC9F0E1);

        LinearLayout col = new LinearLayout(this);
        col.setOrientation(LinearLayout.VERTICAL);

        // Top bar
        LinearLayout top = new LinearLayout(this);
        top.setOrientation(LinearLayout.HORIZONTAL);
        top.setGravity(Gravity.CENTER_VERTICAL);
        int p = UiHelper.dp(this, 10);
        top.setPadding(p, p, p, UiHelper.dp(this, 6));

        HoldExitButton exitBtn = new HoldExitButton(this, this::finish);
        int eSz = UiHelper.dp(this, 52);
        top.addView(exitBtn, new LinearLayout.LayoutParams(eSz, eSz));

        LinearLayout titleCol = new LinearLayout(this);
        titleCol.setOrientation(LinearLayout.VERTICAL);
        titleCol.setPadding(UiHelper.dp(this, 12), 0, 0, 0);

        TextView title = new TextView(this);
        title.setText(def.name);
        title.setTextSize(19);
        title.setTextColor(def.dark ? 0xFFFFFFFF : 0xFF2A2140);
        title.setTypeface(null, Typeface.BOLD);
        titleCol.addView(title);

        TextView hint = new TextView(this);
        hint.setText("Tahan 🏠 1 detik untuk kembali");
        hint.setTextSize(12);
        hint.setTextColor(def.dark ? 0xCCFFFFFF : 0xCC2A2140);
        titleCol.addView(hint);

        top.addView(titleCol, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        Button replayBtn = new Button(this);
        replayBtn.setText("🔊");
        replayBtn.setTextSize(18);
        replayBtn.setBackground(UiHelper.toyBox(this, 0xFFFFFAF0, 99, 3));
        int rSz = UiHelper.dp(this, 48);
        replayBtn.setOnClickListener(v -> {
            UiHelper.bounce(replayBtn);
            if (currentGame != null) currentGame.onReplay();
        });
        top.addView(replayBtn, new LinearLayout.LayoutParams(rSz, rSz));

        col.addView(top);

        stageContainer = new FrameLayout(this);
        col.addView(stageContainer, new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f));

        root.addView(col);

        particles = new ParticleView(this);
        root.addView(particles, new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        setContentView(root);

        sfx.playVoice("g_" + def.id);
        currentGame = createGameView(def.id);
        stageContainer.addView(currentGame, new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        handler.postDelayed(() -> {
            if (alive && currentGame != null) currentGame.start();
        }, 700);
    }

    private BaseGameView createGameView(String id) {
        switch (id) {
            case "balon": return new GamesPack1.BalonGame(this, this);
            case "gelembung": return new GamesPack1.GelembungGame(this, this);
            case "tangkap": return new GamesPack1.TangkapGame(this, this);
            case "kembang": return new GamesPack1.KembangGame(this, this);
            case "piano": return new GamesPack1.PianoGame(this, this);
            case "musik": return new GamesPack1.MusikGame(this, this);
            case "cilukba": return new GamesPack1.CilukbaGame(this, this);
            case "telepon": return new GamesPack1.TeleponGame(this, this);
            case "sembunyi": return new GamesPack2.SembunyiGame(this, this);
            case "roket": return new GamesPack2.RoketGame(this, this);
            case "makan": return new GamesPack2.MakanGame(this, this);
            case "panen": return new GamesPack2.PanenGame(this, this);
            case "es": return new GamesPack2.EsKrimGame(this, this);
            case "pasangan": return new GamesPack2.PasanganGame(this, this);
            case "hitung": return new GamesPack2.HitungGame(this, this);
            default: return new GamesPack1.BalonGame(this, this);
        }
    }

    @Override
    public SoundManager sfx() { return sfx; }

    @Override
    public ParticleView particles() { return particles; }

    @Override
    public void celebrate(Runnable nextRound, String voiceKey) {
        if (!alive) return;
        particles.confetti();
        sfx.ding();
        sfx.playVoice(voiceKey != null ? voiceKey : "menang" + (1 + rnd.nextInt(3)));

        List<String> keys = new ArrayList<>(GameData.ITEMS.keySet());
        String stkKey = keys.get(rnd.nextInt(keys.size()));
        boolean isNew = prefs.awardSticker(stkKey);
        GameData.Item stk = GameData.ITEMS.get(stkKey);

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setGravity(Gravity.CENTER_HORIZONTAL);
        int p = UiHelper.dp(this, 24);
        card.setPadding(p, p, p, p);
        card.setBackground(UiHelper.toyBox(this, 0xFFFFFAF0, 30, 4));

        TextView cici = new TextView(this);
        cici.setText("🐥");
        cici.setTextSize(46);
        card.addView(cici);

        TextView stars = new TextView(this);
        stars.setText("⭐⭐⭐");
        stars.setTextSize(38);
        card.addView(stars);

        TextView msg = new TextView(this);
        String[] praises = {"Hebat!", "Pintar!", "Horee!", "Keren!", "Juara!"};
        msg.setText(praises[rnd.nextInt(praises.length)]);
        msg.setTextSize(24);
        msg.setTextColor(0xFF2A2140);
        msg.setTypeface(null, Typeface.BOLD);
        card.addView(msg);

        TextView stkView = new TextView(this);
        stkView.setText(stk.emoji + "  " + (isNew ? "Stiker baru!" : "Stiker!"));
        stkView.setTextSize(18);
        stkView.setTextColor(0xFF2A2140);
        stkView.setTypeface(null, Typeface.BOLD);
        stkView.setPadding(UiHelper.dp(this, 14), UiHelper.dp(this, 6), UiHelper.dp(this, 14), UiHelper.dp(this, 6));
        stkView.setBackground(UiHelper.toyBox(this, 0xFFFFD23F, 99, 3));
        LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        slp.topMargin = UiHelper.dp(this, 10);
        card.addView(stkView, slp);

        Button nextBtn = new Button(this);
        nextBtn.setText("▶ Main Lagi");
        nextBtn.setAllCaps(false);
        nextBtn.setTextSize(18);
        nextBtn.setTextColor(0xFF2A2140);
        nextBtn.setTypeface(null, Typeface.BOLD);
        nextBtn.setBackground(UiHelper.toyBox(this, 0xFF6CC56F, 99, 4));
        LinearLayout.LayoutParams nlp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nlp.topMargin = UiHelper.dp(this, 16);
        card.addView(nextBtn, nlp);

        AlertDialog dlg = new AlertDialog.Builder(this).setView(card).setCancelable(false).create();
        if (dlg.getWindow() != null) dlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final boolean[] dismissed = {false};
        Runnable closeDlg = () -> {
            if (dismissed[0] || !alive) return;
            dismissed[0] = true;
            dlg.dismiss();
            if (nextRound != null) nextRound.run();
        };
        nextBtn.setOnClickListener(v -> { sfx.tap(); closeDlg.run(); });
        dlg.show();
        handler.postDelayed(closeDlg, 5500);
    }

    @Override
    protected void onDestroy() {
        alive = false;
        handler.removeCallbacksAndMessages(null);
        if (currentGame != null) currentGame.dispose();
        sfx.stopAll();
        super.onDestroy();
    }

    private static class HoldExitButton extends FrameLayout {
        private float progress = 0f;
        private boolean holding = false;
        private final Paint ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final RectF rect = new RectF();
        private final Handler h = new Handler(Looper.getMainLooper());
        private long startMs = 0;

        HoldExitButton(GameActivity act, Runnable onExit) {
            super(act);
            setWillNotDraw(false);
            setBackground(UiHelper.toyBox(act, 0xFFFFFAF0, 99, 3));
            TextView tv = new TextView(act);
            tv.setText("🏠");
            tv.setTextSize(22);
            tv.setGravity(Gravity.CENTER);
            addView(tv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            ringPaint.setStyle(Paint.Style.STROKE);
            ringPaint.setStrokeWidth(UiHelper.dp(act, 4));
            ringPaint.setColor(0xFFFF5D8F);
            ringPaint.setStrokeCap(Paint.Cap.ROUND);

            Runnable tick = new Runnable() {
                @Override
                public void run() {
                    if (!holding) return;
                    progress = Math.min(1f, (System.currentTimeMillis() - startMs) / 1000f);
                    invalidate();
                    if (progress >= 1f) {
                        holding = false;
                        onExit.run();
                    } else {
                        h.postDelayed(this, 16);
                    }
                }
            };

            setOnTouchListener((v, ev) -> {
                switch (ev.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        holding = true;
                        startMs = System.currentTimeMillis();
                        h.post(tick);
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        holding = false;
                        progress = 0f;
                        invalidate();
                        h.removeCallbacks(tick);
                        return true;
                }
                return false;
            });
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (progress > 0f) {
                float pad = ringPaint.getStrokeWidth() / 2f;
                rect.set(pad, pad, getWidth() - pad, getHeight() - pad);
                canvas.drawArc(rect, -90f, 360f * progress, false, ringPaint);
            }
        }
    }
}
