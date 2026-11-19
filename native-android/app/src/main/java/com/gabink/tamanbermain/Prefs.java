package com.gabink.tamanbermain;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Prefs {
    private static final String NAME = "tbsk_prefs";
    private final SharedPreferences sp;

    public Prefs(Context ctx) {
        sp = ctx.getApplicationContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public boolean getBool(String k, boolean def) { return sp.getBoolean(k, def); }
    public void setBool(String k, boolean v) { sp.edit().putBoolean(k, v).apply(); }
    public String getStr(String k, String def) { return sp.getString(k, def); }
    public void setStr(String k, String v) { sp.edit().putString(k, v).apply(); }

    public Map<String, Integer> getStickers() {
        Map<String, Integer> m = new HashMap<>();
        try {
            JSONObject o = new JSONObject(sp.getString("stickers", "{}"));
            Iterator<String> it = o.keys();
            while (it.hasNext()) {
                String k = it.next();
                m.put(k, o.optInt(k, 0));
            }
        } catch (Exception ignored) {}
        return m;
    }

    public boolean awardSticker(String key) {
        Map<String, Integer> m = getStickers();
        boolean isNew = !m.containsKey(key) || m.get(key) == 0;
        m.put(key, (m.containsKey(key) ? m.get(key) : 0) + 1);
        JSONObject o = new JSONObject(m);
        sp.edit().putString("stickers", o.toString()).apply();
        return isNew;
    }

    public List<String> getFavs() {
        List<String> list = new ArrayList<>();
        try {
            JSONArray a = new JSONArray(sp.getString("favs", "[]"));
            for (int i = 0; i < a.length(); i++) list.add(a.getString(i));
        } catch (Exception ignored) {}
        return list;
    }

    public boolean toggleFav(String id) {
        List<String> list = getFavs();
        boolean on;
        if (list.contains(id)) {
            list.remove(id);
            on = false;
        } else {
            list.add(id);
            on = true;
        }
        sp.edit().putString("favs", new JSONArray(list).toString()).apply();
        return on;
    }
}
