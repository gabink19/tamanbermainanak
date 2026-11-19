package com.gabink.tamanbermain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

public class Updater {
    private static final String REPO = "gabink19/tamanbermainanak";

    public static void check(Activity act) {
        Prefs prefs = new Prefs(act);
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                URL url = new URL("https://api.github.com/repos/" + REPO + "/releases/latest");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Accept", "application/vnd.github+json");
                conn.setConnectTimeout(6000);
                conn.setReadTimeout(6000);
                if (conn.getResponseCode() != 200) return;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line);
                br.close();

                JSONObject rel = new JSONObject(sb.toString());
                String remoteVer = rel.optString("tag_name", "").replaceFirst("^[vV]", "").trim();
                if (remoteVer.isEmpty() || compareVer(remoteVer, BuildConfig.APP_VERSION) <= 0) return;
                if (remoteVer.equals(prefs.getStr("skippedVersion", ""))) return;

                String dlUrl = rel.optString("html_url", "");
                JSONArray assets = rel.optJSONArray("assets");
                if (assets != null) {
                    for (int i = 0; i < assets.length(); i++) {
                        JSONObject a = assets.getJSONObject(i);
                        if (a.optString("name", "").toLowerCase().endsWith(".apk")) {
                            dlUrl = a.optString("browser_download_url", dlUrl);
                            break;
                        }
                    }
                }
                if (dlUrl.isEmpty()) return;
                final String finalUrl = dlUrl;
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (!act.isFinishing() && !act.isDestroyed()) {
                        showDialog(act, prefs, remoteVer, finalUrl);
                    }
                });
            } catch (Exception ignored) {}
        });
    }

    private static int compareVer(String a, String b) {
        String[] pa = a.replaceFirst("^[vV]", "").split("\\.");
        String[] pb = b.replaceFirst("^[vV]", "").split("\\.");
        int len = Math.max(pa.length, pb.length);
        for (int i = 0; i < len; i++) {
            int va = i < pa.length ? parseIntSafe(pa[i]) : 0;
            int vb = i < pb.length ? parseIntSafe(pb[i]) : 0;
            if (va != vb) return va - vb;
        }
        return 0;
    }

    private static int parseIntSafe(String s) {
        try { return Integer.parseInt(s.replaceAll("[^0-9]", "")); } catch (Exception e) { return 0; }
    }

    private static void showDialog(Activity act, Prefs prefs, String remoteVer, String dlUrl) {
        int pad = UiHelper.dp(act, 22);
        LinearLayout box = new LinearLayout(act);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setGravity(Gravity.CENTER_HORIZONTAL);
        box.setPadding(pad, pad, pad, pad);
        box.setBackground(UiHelper.toyBox(act, 0xFFFFFAF0, 28, 4));

        TextView ico = new TextView(act);
        ico.setText("🚀");
        ico.setTextSize(42);
        box.addView(ico);

        TextView title = new TextView(act);
        title.setText("Ada Update Baru!");
        title.setTextSize(22);
        title.setTextColor(0xFF2A2140);
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        box.addView(title);

        TextView ver = new TextView(act);
        ver.setText("v" + remoteVer);
        ver.setTextSize(14);
        ver.setTextColor(0xFF2A2140);
        ver.setTypeface(null, android.graphics.Typeface.BOLD);
        ver.setPadding(UiHelper.dp(act, 14), UiHelper.dp(act, 4), UiHelper.dp(act, 14), UiHelper.dp(act, 4));
        ver.setBackground(UiHelper.toyBox(act, 0xFFFFD23F, 99, 3));
        LinearLayout.LayoutParams verLp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        verLp.topMargin = UiHelper.dp(act, 8);
        box.addView(ver, verLp);

        TextView msg = new TextView(act);
        msg.setText("Versi terbaru Taman Bermain Si Kecil sudah tersedia dengan perbaikan dan permainan baru.");
        msg.setTextSize(14);
        msg.setTextColor(0xFF4A4063);
        msg.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams msgLp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        msgLp.topMargin = UiHelper.dp(act, 10);
        box.addView(msg, msgLp);

        AlertDialog dlg = new AlertDialog.Builder(act)
            .setView(box)
            .setCancelable(false)
            .create();
        if (dlg.getWindow() != null) {
            dlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        Button btnNow = new Button(act);
        btnNow.setText("⬇️ Update Sekarang");
        btnNow.setAllCaps(false);
        btnNow.setTextSize(16);
        btnNow.setTextColor(0xFF2A2140);
        btnNow.setTypeface(null, android.graphics.Typeface.BOLD);
        btnNow.setBackground(UiHelper.toyBox(act, 0xFF6CC56F, 99, 3));
        LinearLayout.LayoutParams nowLp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nowLp.topMargin = UiHelper.dp(act, 16);
        btnNow.setOnClickListener(v -> {
            dlg.dismiss();
            act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(dlUrl)));
        });
        box.addView(btnNow, nowLp);

        Button btnSkip = new Button(act);
        btnSkip.setText("Lewati Versi Ini");
        btnSkip.setAllCaps(false);
        btnSkip.setTextSize(14);
        btnSkip.setTextColor(0xFF2A2140);
        btnSkip.setTypeface(null, android.graphics.Typeface.BOLD);
        btnSkip.setBackground(UiHelper.toyBox(act, 0xFFE9E4DC, 99, 3));
        LinearLayout.LayoutParams skipLp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        skipLp.topMargin = UiHelper.dp(act, 10);
        btnSkip.setOnClickListener(v -> {
            prefs.setStr("skippedVersion", remoteVer);
            SoundManager.get(act).tap();
            dlg.dismiss();
        });
        box.addView(btnSkip, skipLp);

        dlg.show();
    }
}
