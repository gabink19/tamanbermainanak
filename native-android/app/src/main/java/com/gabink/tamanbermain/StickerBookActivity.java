package com.gabink.tamanbermain;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StickerBookActivity extends AppCompatActivity {
    private Prefs prefs;
    private SoundManager sfx;
    private ParticleView particles;
    private GridLayout grid;
    private String cat = "all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new Prefs(this);
        sfx = SoundManager.get(this);

        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(0xFFFFF3C4);

        LinearLayout col = new LinearLayout(this);
        col.setOrientation(LinearLayout.VERTICAL);
        int p = UiHelper.dp(this, 14);
        col.setPadding(p, p, p, p);

        // Top bar
        LinearLayout top = new LinearLayout(this);
        top.setOrientation(LinearLayout.HORIZONTAL);
        top.setGravity(Gravity.CENTER_VERTICAL);

        Button closeBtn = new Button(this);
        closeBtn.setText("✖");
        closeBtn.setTextSize(18);
        closeBtn.setTextColor(0xFF2A2140);
        closeBtn.setBackground(UiHelper.toyBox(this, 0xFFFFFAF0, 99, 3));
        int sz = UiHelper.dp(this, 48);
        closeBtn.setOnClickListener(v -> { sfx.tap(); finish(); });
        top.addView(closeBtn, new LinearLayout.LayoutParams(sz, sz));

        LinearLayout titleCol = new LinearLayout(this);
        titleCol.setOrientation(LinearLayout.VERTICAL);
        titleCol.setPadding(UiHelper.dp(this, 12), 0, 0, 0);

        TextView title = new TextView(this);
        title.setText("Buku Stiker");
        title.setTextSize(22);
        title.setTextColor(0xFF2A2140);
        title.setTypeface(null, Typeface.BOLD);
        titleCol.addView(title);

        Map<String, Integer> owned = prefs.getStickers();
        TextView count = new TextView(this);
        count.setText(owned.size() + " / " + GameData.ITEMS.size() + " stiker terkumpul");
        count.setTextSize(13);
        count.setTextColor(0xFF4A4063);
        titleCol.addView(count);

        ProgressBar prog = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        prog.setMax(GameData.ITEMS.size());
        prog.setProgress(owned.size());
        titleCol.addView(prog);

        top.addView(titleCol, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        col.addView(top);

        // Tabs
        HorizontalScrollView hsv = new HorizontalScrollView(this);
        hsv.setHorizontalScrollBarEnabled(false);
        LinearLayout tabs = new LinearLayout(this);
        tabs.setOrientation(LinearLayout.HORIZONTAL);
        tabs.setPadding(0, UiHelper.dp(this, 10), 0, UiHelper.dp(this, 10));
        String[][] cats = {{"all", "✨ Semua"}, {"animal", "🐾 Hewan"}, {"fruit", "🍎 Buah"}, {"veg", "🥕 Sayur"}, {"veh", "🚗 Kendaraan"}};
        List<Button> btns = new ArrayList<>();
        for (String[] c : cats) {
            Button b = new Button(this);
            b.setText(c[1]);
            b.setAllCaps(false);
            b.setTextSize(13);
            b.setTextColor(0xFF2A2140);
            b.setTypeface(null, Typeface.BOLD);
            b.setBackground(UiHelper.toyBox(this, c[0].equals(cat) ? 0xFFFFD23F : 0xFFFFFAF0, 99, 3));
            b.setPadding(UiHelper.dp(this, 12), UiHelper.dp(this, 4), UiHelper.dp(this, 12), UiHelper.dp(this, 4));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.rightMargin = UiHelper.dp(this, 8);
            b.setTag(c[0]);
            b.setOnClickListener(v -> {
                sfx.tap();
                cat = c[0];
                for (Button xb : btns) {
                    xb.setBackground(UiHelper.toyBox(this, cat.equals(xb.getTag()) ? 0xFFFFD23F : 0xFFFFFAF0, 99, 3));
                }
                renderStickers();
            });
            btns.add(b);
            tabs.addView(b, lp);
        }
        hsv.addView(tabs);
        col.addView(hsv);

        ScrollView scroll = new ScrollView(this);
        grid = new GridLayout(this);
        grid.setColumnCount(getResources().getConfiguration().screenWidthDp >= 600 ? 5 : 4);
        scroll.addView(grid);
        col.addView(scroll, new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f));

        root.addView(col);
        particles = new ParticleView(this);
        root.addView(particles, new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        setContentView(root);
        renderStickers();
    }

    private void renderStickers() {
        grid.removeAllViews();
        Map<String, Integer> owned = prefs.getStickers();
        for (GameData.Item it : GameData.ITEMS.values()) {
            if (!"all".equals(cat) && !it.cat.equals(cat)) continue;
            boolean have = owned.containsKey(it.key) && owned.get(it.key) > 0;
            TextView cell = new TextView(this);
            cell.setText(have ? it.emoji : "❔");
            cell.setTextSize(34);
            cell.setGravity(Gravity.CENTER);
            cell.setPadding(0, UiHelper.dp(this, 12), 0, UiHelper.dp(this, 12));
            cell.setBackground(UiHelper.toyBox(this, have ? 0xFFFFFFFF : 0x66FFFFFF, 99, have ? 3 : 1));
            cell.setAlpha(have ? 1f : 0.4f);
            cell.setOnClickListener(v -> {
                if (have) {
                    UiHelper.bounce(cell);
                    sfx.pop();
                    sfx.playReal(it.key);
                    sfx.playVoice(GameData.LINES.containsKey("ada_" + it.key) ? "ada_" + it.key : "n_" + it.key);
                    particles.burst(cell.getX() + cell.getWidth() / 2f, cell.getY() + cell.getHeight() / 2f, null, 14);
                } else {
                    sfx.wrong();
                }
            });
            GridLayout.LayoutParams glp = new GridLayout.LayoutParams();
            glp.width = 0;
            glp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            int m = UiHelper.dp(this, 5);
            glp.setMargins(m, m, m, m);
            grid.addView(cell, glp);
        }
    }

    @Override
    protected void onDestroy() {
        sfx.stopAll();
        super.onDestroy();
    }
}
