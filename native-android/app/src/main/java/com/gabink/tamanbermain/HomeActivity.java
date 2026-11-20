package com.gabink.tamanbermain;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    private Prefs prefs;
    private SoundManager sfx;
    private ParticleView particles;
    private GridLayout grid;
    private TextView stickerBadge, sayBubble;
    private String activeCat = "all";
    private int ciciIdx = 0;
    private final Random rnd = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new Prefs(this);
        sfx = SoundManager.get(this);

        FrameLayout root = new FrameLayout(this);
        particles = new ParticleView(this);
        PlaygroundViews.PlaygroundBgView bg = new PlaygroundViews.PlaygroundBgView(this, particles, sfx);
        root.addView(bg, new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        ScrollView scroll = new ScrollView(this);
        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        int p = UiHelper.dp(this, 14);
        content.setPadding(p, p, p, UiHelper.dp(this, 36));
        scroll.addView(content);
        root.addView(scroll);

        root.addView(particles, new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        // Hero: Custom-drawn animated Cici + Title
        LinearLayout hero = new LinearLayout(this);
        hero.setOrientation(LinearLayout.HORIZONTAL);
        hero.setGravity(Gravity.CENTER_VERTICAL);
        PlaygroundViews.CiciView cici = new PlaygroundViews.CiciView(this);
        int ciciSz = UiHelper.dp(this, 96);
        hero.addView(cici, new LinearLayout.LayoutParams(ciciSz, ciciSz));

        LinearLayout heroText = new LinearLayout(this);
        heroText.setOrientation(LinearLayout.VERTICAL);
        sayBubble = new TextView(this);
        sayBubble.setText("Halo! Aku Cici. Mau main apa hari ini?");
        sayBubble.setTextSize(14);
        sayBubble.setTextColor(0xFF2A2140);
        sayBubble.setTypeface(null, Typeface.BOLD);
        sayBubble.setPadding(UiHelper.dp(this, 12), UiHelper.dp(this, 6), UiHelper.dp(this, 12), UiHelper.dp(this, 6));
        sayBubble.setBackground(UiHelper.toyBox(this, Color.WHITE, 16, 3));
        heroText.addView(sayBubble);

        TextView title = new TextView(this);
        title.setText("Taman Bermain Si Kecil");
        title.setTextSize(26);
        title.setTextColor(0xFF2A2140);
        title.setTypeface(null, Typeface.BOLD);
        title.setPadding(0, UiHelper.dp(this, 6), 0, 0);
        heroText.addView(title);
        hero.addView(heroText, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        content.addView(hero);

        final String[][] ciciLines = {
            {"halo", "Halo! Aku Cici. Mau main apa hari ini?"},
            {"cici1", "Cuit, cuit! Aku Cici! Ayo kita main bersama!"},
            {"cici2", "Kamu anak hebat! Yuk, pilih permainan kesukaanmu!"},
            {"cici3", "Cilukba! Banyak stiker lucu menunggumu!"},
        };
        cici.setOnClickListener(v -> {
            UiHelper.bounce(cici);
            cici.wave();
            sfx.boing();
            String[] pair = ciciLines[ciciIdx++ % ciciLines.length];
            sayBubble.setText(pair[1]);
            sfx.playVoice(pair[0]);
            particles.burst(cici.getX() + cici.getWidth() / 2f, cici.getY() + cici.getHeight() / 2f, null, 20);
        });

        // Top action row
        HorizontalScrollView topScroll = new HorizontalScrollView(this);
        topScroll.setHorizontalScrollBarEnabled(false);
        LinearLayout topRow = new LinearLayout(this);
        topRow.setOrientation(LinearLayout.HORIZONTAL);
        topRow.setPadding(0, UiHelper.dp(this, 10), 0, UiHelper.dp(this, 10));

        Button bookBtn = makeChip("📒 Buku Stiker (0)", 0xFFFFD23F);
        stickerBadge = bookBtn;
        bookBtn.setOnClickListener(v -> {
            sfx.tap();
            startActivity(new Intent(this, StickerBookActivity.class));
        });
        topRow.addView(bookBtn);

        Button randBtn = makeChip("🎲 Main Acak!", 0xFFFF9F1C);
        randBtn.setOnClickListener(v -> {
            sfx.ding();
            GameData.GameDef g = GameData.GAMES.get(rnd.nextInt(GameData.GAMES.size()));
            openGame(g);
        });
        topRow.addView(randBtn);

        Button parentBtn = makeChip("⚙️ Orang Tua", 0xFFFFFAF0);
        parentBtn.setOnClickListener(v -> { sfx.tap(); showParentSettings(); });
        topRow.addView(parentBtn);

        topScroll.addView(topRow);
        content.addView(topScroll);

        // Category filter pills
        HorizontalScrollView catScroll = new HorizontalScrollView(this);
        catScroll.setHorizontalScrollBarEnabled(false);
        LinearLayout catBar = new LinearLayout(this);
        catBar.setOrientation(LinearLayout.HORIZONTAL);
        catBar.setPadding(0, 0, 0, UiHelper.dp(this, 12));
        String[][] cats = {
            {"all", "✨ Semua"},
            {"0", "🧩 Cocokkan"},
            {"1", "💖 Rawat"},
            {"2", "🎵 Seru & Musik"},
            {"fav", "❤️ Favorit"}
        };
        List<Button> catBtns = new ArrayList<>();
        for (String[] c : cats) {
            Button b = makeChip(c[1], c[0].equals(activeCat) ? 0xFFFFD23F : 0xFFFFFAF0);
            b.setTag(c[0]);
            b.setOnClickListener(v -> {
                sfx.tap();
                activeCat = c[0];
                for (Button cb : catBtns) {
                    boolean on = activeCat.equals(cb.getTag());
                    cb.setBackground(UiHelper.toyBox(this, on ? 0xFFFFD23F : 0xFFFFFAF0, 99, 3));
                }
                renderGrid();
            });
            catBtns.add(b);
            catBar.addView(b);
        }
        catScroll.addView(catBar);
        content.addView(catScroll);

        grid = new GridLayout(this);
        int cols = getResources().getConfiguration().screenWidthDp >= 600 ? 3 : 2;
        grid.setColumnCount(cols);
        content.addView(grid);

        setContentView(root);
        renderGrid();
        Updater.check(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int count = prefs.getStickers().size();
        stickerBadge.setText("📒 Buku Stiker (" + count + ")");
    }

    private Button makeChip(String text, int color) {
        Button b = new Button(this);
        b.setText(text);
        b.setAllCaps(false);
        b.setTextSize(14);
        b.setTextColor(0xFF2A2140);
        b.setTypeface(null, Typeface.BOLD);
        b.setBackground(UiHelper.toyBox(this, color, 99, 3));
        b.setPadding(UiHelper.dp(this, 14), UiHelper.dp(this, 6), UiHelper.dp(this, 14), UiHelper.dp(this, 6));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.rightMargin = UiHelper.dp(this, 8);
        b.setLayoutParams(lp);
        return b;
    }

    private void renderGrid() {
        grid.removeAllViews();
        List<String> favs = prefs.getFavs();
        List<GameData.GameDef> list = new ArrayList<>();
        for (GameData.GameDef g : GameData.GAMES) {
            if ("all".equals(activeCat)
                || ("fav".equals(activeCat) && favs.contains(g.id))
                || String.valueOf(g.section).equals(activeCat)) {
                list.add(g);
            }
        }
        if (list.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("Belum ada permainan favorit.\nKetuk ikon 🤍 di pojok kartu untuk menambahkan!");
            empty.setTextSize(15);
            empty.setTextColor(0xFF2A2140);
            empty.setTypeface(null, Typeface.BOLD);
            empty.setGravity(Gravity.CENTER);
            empty.setPadding(UiHelper.dp(this, 16), UiHelper.dp(this, 24), UiHelper.dp(this, 16), UiHelper.dp(this, 24));
            empty.setBackground(UiHelper.toyBox(this, 0xFFFFFAF0, 22, 3));
            GridLayout.LayoutParams glp = new GridLayout.LayoutParams();
            glp.columnSpec = GridLayout.spec(0, grid.getColumnCount(), 1f);
            glp.width = 0;
            grid.addView(empty, glp);
            return;
        }
        int tileIdx = 0;
        for (GameData.GameDef g : list) {
            FrameLayout card = new FrameLayout(this);
            card.setBackground(UiHelper.toyBox3D(this, g.color, 26, 3, 6));
            int cp = UiHelper.dp(this, 10);
            card.setPadding(cp, UiHelper.dp(this, 10), cp, UiHelper.dp(this, 14));

            LinearLayout inner = new LinearLayout(this);
            inner.setOrientation(LinearLayout.VERTICAL);
            inner.setGravity(Gravity.CENTER_HORIZONTAL);

            FrameLayout badge = new FrameLayout(this);
            badge.setBackground(UiHelper.toyBox(this, 0xFFFFF8E8, 99, 3));
            SpriteView sprite = new SpriteView(this, spriteForGame(g.id));
            if ("bayangan".equals(g.id)) sprite.setSilhouette(true);
            int spPad = UiHelper.dp(this, 10);
            badge.setPadding(spPad, spPad, spPad, spPad);
            badge.addView(sprite, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            int badgeSz = UiHelper.dp(this, 82);
            LinearLayout.LayoutParams ilp = new LinearLayout.LayoutParams(badgeSz, badgeSz);
            ilp.topMargin = UiHelper.dp(this, 4);
            ilp.bottomMargin = UiHelper.dp(this, 8);
            inner.addView(badge, ilp);

            TextView name = new TextView(this);
            name.setText(g.name);
            name.setTextSize(15);
            name.setTextColor(0xFF2A2140);
            name.setTypeface(null, Typeface.BOLD);
            name.setGravity(Gravity.CENTER);
            name.setMaxLines(2);
            inner.addView(name);

            TextView desc = new TextView(this);
            desc.setText(g.desc);
            desc.setTextSize(11.5f);
            desc.setTextColor(0xCC2A2140);
            desc.setGravity(Gravity.CENTER);
            desc.setMaxLines(2);
            desc.setPadding(UiHelper.dp(this, 4), UiHelper.dp(this, 2), UiHelper.dp(this, 4), 0);
            inner.addView(desc);

            card.addView(inner);

            TextView favBtn = new TextView(this);
            boolean isFav = favs.contains(g.id);
            favBtn.setText(isFav ? "❤️" : "🤍");
            favBtn.setTextSize(16);
            favBtn.setPadding(UiHelper.dp(this, 4), UiHelper.dp(this, 2), UiHelper.dp(this, 4), UiHelper.dp(this, 2));
            FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP | Gravity.END);
            favBtn.setOnClickListener(v -> {
                sfx.pop();
                boolean on = prefs.toggleFav(g.id);
                favBtn.setText(on ? "❤️" : "🤍");
                UiHelper.bounce(favBtn);
                if ("fav".equals(activeCat)) renderGrid();
            });
            card.addView(favBtn, flp);

            card.setOnClickListener(v -> {
                UiHelper.bounce(card);
                sfx.boing();
                openGame(g);
            });

            GridLayout.LayoutParams glp = new GridLayout.LayoutParams();
            glp.width = 0;
            glp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            int m = UiHelper.dp(this, 6);
            glp.setMargins(m, m, m, m);
            grid.addView(card, glp);
        }
    }

    private String spriteForGame(String id) {
        switch (id) {
            case "profesi": return "pemadam";
            case "panen": return "wortel";
            case "sortir": return "keranjang";
            case "makan": return "kelinci";
            case "dokter": return "stetoskop";
            case "dandan": return "mahkota";
            case "kereta": return "kereta";
            case "jalan": return "mobil";
            case "kue": return "kue";
            case "hitung": return "apel";
            case "bentuk": return "bintang";
            case "besarkecil": return "beruang";
            case "rumah": return "sapi";
            case "bayangan": return "gajah";
            case "puzzle": return "puzzle";
            case "mandi": return "spons";
            case "gigi": return "gigi";
            case "pancing": return "ikan";
            case "pasangan": return "tanda_tanya";
            case "warnai": return "kuas";
            case "balon": return "balon";
            case "gelembung": return "ikanhias";
            case "kembang": return "kembang";
            case "piano": return "piano";
            case "musik": return "drum";
            case "cilukba": return "selimut";
            case "telepon": return "telepon";
            case "tangkap": return "bintang";
            case "es": return "eskrim";
            case "sembunyi": return "semak";
            case "pelangi": return "pelangi";
            case "roket": return "roket";
            default: return "kucing";
        }
    }

    private void openGame(GameData.GameDef g) {
        Intent it = new Intent(this, GameActivity.class);
        it.putExtra("game_id", g.id);
        startActivity(it);
    }

    private void showParentSettings() {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        int p = UiHelper.dp(this, 20);
        box.setPadding(p, p, p, p);
        box.setBackground(UiHelper.toyBox(this, 0xFFFFFAF0, 26, 4));

        TextView h = new TextView(this);
        h.setText("⚙️ Pengaturan Orang Tua");
        h.setTextSize(18);
        h.setTextColor(0xFF2A2140);
        h.setTypeface(null, Typeface.BOLD);
        box.addView(h);

        Button vBtn = makeChip("🗣️ Suara pembicara: " + (prefs.getBool("voice", true) ? "Nyala" : "Mati"),
            prefs.getBool("voice", true) ? 0xFF6CC56F : 0xFFE9E4DC);
        vBtn.setOnClickListener(v -> {
            boolean nv = !prefs.getBool("voice", true);
            prefs.setBool("voice", nv);
            vBtn.setText("🗣️ Suara pembicara: " + (nv ? "Nyala" : "Mati"));
            vBtn.setBackground(UiHelper.toyBox(this, nv ? 0xFF6CC56F : 0xFFE9E4DC, 99, 3));
            if (!nv) sfx.stopVoice(); else sfx.playVoice("halo");
        });
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp1.topMargin = UiHelper.dp(this, 12);
        box.addView(vBtn, lp1);

        Button sBtn = makeChip("🔔 Efek suara: " + (prefs.getBool("sfx", true) ? "Nyala" : "Mati"),
            prefs.getBool("sfx", true) ? 0xFF6CC56F : 0xFFE9E4DC);
        sBtn.setOnClickListener(v -> {
            boolean ns = !prefs.getBool("sfx", true);
            prefs.setBool("sfx", ns);
            sBtn.setText("🔔 Efek suara: " + (ns ? "Nyala" : "Mati"));
            sBtn.setBackground(UiHelper.toyBox(this, ns ? 0xFF6CC56F : 0xFFE9E4DC, 99, 3));
            if (ns) sfx.tap();
        });
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp2.topMargin = UiHelper.dp(this, 8);
        box.addView(sBtn, lp2);

        TextView info = new TextView(this);
        info.setText("Versi Native Java: v" + BuildConfig.APP_VERSION + "\nDi dalam permainan, tahan tombol 🏠 selama 1 detik untuk kembali.");
        info.setTextSize(13);
        info.setTextColor(0xFF4A4063);
        info.setPadding(0, UiHelper.dp(this, 12), 0, 0);
        box.addView(info);

        AlertDialog dlg = new AlertDialog.Builder(this).setView(box).create();
        if (dlg.getWindow() != null) dlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dlg.show();
    }
}
