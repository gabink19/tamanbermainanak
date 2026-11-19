package com.gabink.tamanbermain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameData {
    public static class Item {
        public final String key, emoji, name, soundLine, cat;
        public Item(String key, String emoji, String name, String soundLine, String cat) {
            this.key = key; this.emoji = emoji; this.name = name; this.soundLine = soundLine; this.cat = cat;
        }
    }

    public static class GameDef {
        public final String id, icon, name, desc;
        public final int section; // 0: cocokkan, 1: rawat, 2: seru & musik
        public final int color;
        public final boolean dark;
        public GameDef(String id, String icon, String name, String desc, int section, int color, boolean dark) {
            this.id = id; this.icon = icon; this.name = name; this.desc = desc;
            this.section = section; this.color = color; this.dark = dark;
        }
    }

    public static final int[] TILE_COLORS = {
        0xFFFFD23F, 0xFFFF5D8F, 0xFF4CC9F0, 0xFF6CC56F, 0xFFFF9F1C, 0xFFA77FF2
    };

    public static final Map<String, Item> ITEMS = new LinkedHashMap<>();
    public static final List<String> VOCAL_ANIMALS = new ArrayList<>();
    public static final List<String> SEA_ANIMALS = List.of(
        "ikan", "ikanhias", "gurita", "kepiting", "kurakura", "lumbalumba", "paus", "hiu", "buntal", "cumi", "udang"
    );
    public static final String[][] FEED_PAIRS = {
        {"kelinci", "wortel"}, {"monyet", "pisang"}, {"kucing", "ikan"}, {"anjing", "tulang"},
        {"sapi", "rumput"}, {"panda", "bambu"}, {"tikus", "keju"}, {"beruang", "madu"},
        {"ayam", "jagung"}, {"bayi", "susu"}
    };
    public static final Map<String, String> FOODS = new LinkedHashMap<>();
    public static final Map<String, String> LINES = new LinkedHashMap<>();
    public static final List<GameDef> GAMES = new ArrayList<>();

    static {
        addItem("kucing", "🐱", "Kucing", "Meong, meong!", "animal");
        addItem("anjing", "🐶", "Anjing", "Guk, guk, guk!", "animal");
        addItem("sapi", "🐮", "Sapi", "Mooo!", "animal");
        addItem("ayam", "🐔", "Ayam", "Kukuruyuuuk!", "animal");
        addItem("bebek", "🦆", "Bebek", "Kwek, kwek, kwek!", "animal");
        addItem("kambing", "🐐", "Kambing", "Mbeeek!", "animal");
        addItem("domba", "🐑", "Domba", "Beee!", "animal");
        addItem("kuda", "🐴", "Kuda", "Hiiihiii!", "animal");
        addItem("katak", "🐸", "Katak", "Kwok, kwok!", "animal");
        addItem("singa", "🦁", "Singa", "Auuum!", "animal");
        addItem("harimau", "🐯", "Harimau", "Grauum!", "animal");
        addItem("gajah", "🐘", "Gajah", "Prooot!", "animal");
        addItem("monyet", "🐵", "Monyet", "Uu, aa, aa!", "animal");
        addItem("kelinci", "🐰", "Kelinci", "Hop, hop, hop!", "animal");
        addItem("panda", "🐼", "Panda", "Halo, halo!", "animal");
        addItem("tikus", "🐭", "Tikus", "Cit, cit, cit!", "animal");
        addItem("beruang", "🐻", "Beruang", "Grrr!", "animal");
        addItem("burung", "🐦", "Burung", "Cuit, cuit!", "animal");
        addItem("burunghantu", "🦉", "Burung hantu", "Hu, hu, hu!", "animal");
        addItem("lebah", "🐝", "Lebah", "Nguuung!", "animal");
        addItem("bayi", "👶", "Adik bayi", "Oek, oek!", "animal");
        addItem("anakayam", "🐤", "Anak ayam", "Ciap, ciap!", "animal");
        addItem("zebra", "🦓", "Zebra", null, "animal");
        addItem("jerapah", "🦒", "Jerapah", null, "animal");
        addItem("ikan", "🐟", "Ikan", null, "animal");
        addItem("ikanhias", "🐠", "Ikan hias", null, "animal");
        addItem("gurita", "🐙", "Gurita", null, "animal");
        addItem("kepiting", "🦀", "Kepiting", null, "animal");
        addItem("kurakura", "🐢", "Kura-kura", null, "animal");
        addItem("lumbalumba", "🐬", "Lumba-lumba", null, "animal");
        addItem("paus", "🐳", "Ikan paus", null, "animal");
        addItem("hiu", "🦈", "Ikan hiu", null, "animal");
        addItem("buntal", "🐡", "Ikan buntal", null, "animal");
        addItem("cumi", "🦑", "Cumi-cumi", null, "animal");
        addItem("udang", "🦐", "Udang", null, "animal");

        addItem("apel", "🍎", "Apel", null, "fruit");
        addItem("pisang", "🍌", "Pisang", null, "fruit");
        addItem("anggur", "🍇", "Anggur", null, "fruit");
        addItem("jeruk", "🍊", "Jeruk", null, "fruit");
        addItem("semangka", "🍉", "Semangka", null, "fruit");
        addItem("stroberi", "🍓", "Stroberi", null, "fruit");
        addItem("nanas", "🍍", "Nanas", null, "fruit");
        addItem("mangga", "🥭", "Mangga", null, "fruit");

        addItem("wortel", "🥕", "Wortel", null, "veg");
        addItem("jagung", "🌽", "Jagung", null, "veg");
        addItem("brokoli", "🥦", "Brokoli", null, "veg");
        addItem("tomat", "🍅", "Tomat", null, "veg");
        addItem("kentang", "🥔", "Kentang", null, "veg");

        addItem("mobil", "🚗", "Mobil", null, "veh");
        addItem("bus", "🚌", "Bus", null, "veh");
        addItem("kereta", "🚂", "Kereta api", null, "veh");
        addItem("sepeda", "🚲", "Sepeda", null, "veh");
        addItem("motor", "🏍️", "Motor", null, "veh");
        addItem("truk", "🚚", "Truk", null, "veh");
        addItem("kapal", "🚢", "Kapal laut", null, "veh");
        addItem("pesawat", "✈️", "Pesawat terbang", null, "veh");
        addItem("helikopter", "🚁", "Helikopter", null, "veh");
        addItem("roket", "🚀", "Roket", null, "veh");
        addItem("ambulans", "🚑", "Mobil ambulans", null, "veh");
        addItem("mobilpolisi", "🚓", "Mobil polisi", null, "veh");
        addItem("pemadam", "🚒", "Mobil pemadam kebakaran", null, "veh");
        addItem("traktor", "🚜", "Traktor", null, "veh");

        FOODS.put("wortel", "🥕"); FOODS.put("pisang", "🍌"); FOODS.put("ikan", "🐟");
        FOODS.put("tulang", "🦴"); FOODS.put("rumput", "🌿"); FOODS.put("bambu", "🎋");
        FOODS.put("keju", "🧀"); FOODS.put("madu", "🍯"); FOODS.put("jagung", "🌽");
        FOODS.put("susu", "🍼");

        LINES.put("halo", "Halo! Ayo kita bermain bersama!");
        LINES.put("tes", "Halo, adik! Aku teman bermainmu. Yuk, kita main bersama!");
        LINES.put("cici1", "Cuit, cuit! Aku Cici! Ayo kita main bersama!");
        LINES.put("cici2", "Kamu anak hebat! Yuk, pilih permainan kesukaanmu!");
        LINES.put("cici3", "Cilukba! Banyak stiker lucu menunggumu!");
        LINES.put("yeay", "Yeay, pintar!");
        LINES.put("hebat", "Hebat sekali!");
        LINES.put("hore", "Horee, betul!");
        LINES.put("menang1", "Yeay, berhasil! Kamu hebat sekali!");
        LINES.put("menang2", "Horee! Kamu pintar sekali!");
        LINES.put("menang3", "Hebat! Ayo, kita main lagi!");
        LINES.put("ups", "Ups, bukan di situ. Coba lagi, ya!");
        LINES.put("cilukba", "Ciluk... baa!");
        LINES.put("dimana", "Di mana, ya? Ayo, buka selimutnya!");
        LINES.put("menelepon", "Kring, kring! Siapa yang menelepon, ya?");
        LINES.put("dadah", "Dadah! Sampai jumpa lagi!");
        LINES.put("balon_intro", "Ayo, pecahkan balonnya! Dor, dor!");
        LINES.put("gelembung_intro", "Ayo, pecahkan gelembungnya! Ada hewan laut di dalamnya.");
        LINES.put("kembang_intro", "Ketuk langitnya, ya! Duar, duar!");
        LINES.put("piano_intro", "Ayo, main piano! Ketuk tutsnya, ya.");
        LINES.put("musik_intro", "Ayo, main musik! Ketuk alat musiknya, ya.");
        LINES.put("tangkap_intro", "Ayo tangkap bintangnya! Ketuk bintang yang bersinar, ya!");
        LINES.put("tangkap_done", "Wah, bintangnya banyak sekali! Cantik, ya!");
        LINES.put("sembunyi_intro", "Hewannya bersembunyi! Di mana, ya? Ketuk semaknya!");
        LINES.put("es_intro", "Yuk, buat es krim! Pilih tiga rasa es krim kesukaanmu!");
        LINES.put("es_topping", "Sekarang tambahkan topping di atasnya, ya!");
        LINES.put("es_makan", "Wah, es krimnya enak! Ketuk es krimnya untuk dimakan, nyam!");
        LINES.put("roket_intro", "Ayo isi bahan bakar roket! Ketuk lima bintangnya, ya!");
        LINES.put("roket_siap", "Roket sudah penuh! Ketuk tombol roket untuk meluncur!");
        LINES.put("roket_meluncur", "Meluncur ke luar angkasa! Wuuush!");
        LINES.put("panen_intro", "Ayo panen sayur! Ketuk sayurnya untuk masuk ke kotak!");
        LINES.put("panen_done", "Wah, panennya banyak sekali! Kamu hebat!");
        LINES.put("profesi_intro", "Siapa naik apa, ya? Ayo, bantu mereka naik kendaraannya!");
        LINES.put("sortir_buahsayur", "Buah masuk ke kotak buah. Sayur masuk ke kotak sayur, ya!");
        LINES.put("besarkecil_intro", "Yang besar masuk kotak besar. Yang kecil masuk kotak kecil, ya!");
        LINES.put("besar", "Yang ini besar!");
        LINES.put("kecil", "Yang ini kecil!");
        LINES.put("rumah_intro", "Ayo, antar hewan ke rumahnya! Ada laut, peternakan, dan hutan.");
        LINES.put("bayangan_intro", "Ayo, cari bayangannya! Cocokkan gambarnya, ya.");
        LINES.put("bentuk_intro", "Ayo, masukkan bentuknya ke lubang yang pas!");
        LINES.put("pelangi_intro", "Ayo susun warna pelangi! Merah, oranye, kuning, hijau, biru, dan ungu!");
        LINES.put("pelangi_done", "Wah, pelanginya indah sekali!");
        LINES.put("puzzle_intro", "Ayo, susun puzzlenya!");
        LINES.put("puzzle_done", "Yeay! Gambarnya sudah jadi!");
        LINES.put("jalan_intro", "Ayo, antar sampai ke tujuan!");
        LINES.put("jalan_done", "Yeay, sudah sampai!");
        LINES.put("dokter_intro", "Kita jadi dokter hewan, yuk!");
        LINES.put("dandan_intro", "Ayo, kita dandani teman kita!");
        LINES.put("dandan_done", "Wah, sekarang jadi keren sekali!");
        LINES.put("mandi_intro", "Wah, kotor sekali! Ayo, gosok sampai bersih!");
        LINES.put("mandi_done", "Wah, sudah bersih dan wangi!");
        LINES.put("gigi_intro", "Ih, ada kuman di gigi! Ayo, sikat giginya sampai bersih!");
        LINES.put("gigi_done", "Wah, giginya bersih dan berkilau! Senyum, dong!");
        LINES.put("kue_intro", "Hari ini ada yang ulang tahun! Ayo, pasang lilinnya di atas kue!");
        LINES.put("kue_tiup", "Selamat ulang tahun! Ayo, tiup lilinnya!");
        LINES.put("kue_done", "Horee! Selamat ulang tahun!");
        LINES.put("kereta_intro", "Ayo, bantu hewan-hewan naik kereta api!");
        LINES.put("kereta_done", "Semua sudah naik. Kereta berangkat! Tut, tut, tuuut!");
        LINES.put("warnai_intro", "Pilih warnanya dulu, lalu ketuk gambarnya, ya!");
        LINES.put("warnai_done", "Wah, gambarnya cantik sekali!");
        LINES.put("pancing_intro", "Ayo memancing! Ketuk ikannya, ya!");
        LINES.put("pancing_done", "Wah, dapat lima ikan! Hebat!");

        for (Item it : ITEMS.values()) {
            LINES.put("n_" + it.key, "Ini " + it.name.toLowerCase() + "!");
            if (it.soundLine != null) {
                LINES.put("ada_" + it.key, "Wah, ada " + it.name.toLowerCase() + "! " + it.soundLine);
                LINES.put("halo_" + it.key, "Halo, halo! Ini " + it.name.toLowerCase() + ". " + it.soundLine + " Apa kabar?");
                LINES.put("cari_" + it.key, "Di mana " + it.name.toLowerCase() + ", ya? Ayo, cari!");
            }
        }
        for (String[] p : FEED_PAIRS) {
            Item a = ITEMS.get(p[0]);
            if (a != null) {
                LINES.put("lapar_" + a.key, "Aduh, " + a.name.toLowerCase() + " lapar, nih. Mau makan apa, ya?");
                LINES.put("nyam_" + a.key, "Nyam, nyam! " + a.name + " suka sekali makan " + p[1] + "!");
            }
        }
        String[] nums = {"Nol", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh"};
        for (int i = 0; i <= 10; i++) LINES.put("num_" + i, nums[i] + "!");

        // Daftar game native Java
        addGame("balon", "🎈", "Balon", "Pecahkan balon warna-warni", 2, false);
        addGame("gelembung", "🐠", "Gelembung Laut", "Pecahkan gelembung laut", 2, false);
        addGame("tangkap", "⭐", "Tangkap Bintang", "Tangkap bintang di langit malam", 2, true);
        addGame("kembang", "🎆", "Kembang Api", "Ketuk langit malam", 2, true);
        addGame("piano", "🎹", "Piano", "Main musik dengan jari", 2, false);
        addGame("musik", "🥁", "Alat Musik", "Drum, gitar, terompet", 2, false);
        addGame("cilukba", "🙈", "Cilukba", "Siapa di balik selimut?", 2, false);
        addGame("telepon", "📞", "Telepon", "Halo, siapa ini?", 2, false);
        addGame("sembunyi", "🌿", "Cari Hewan", "Cari hewan di balik semak", 2, false);
        addGame("roket", "🚀", "Luncurkan Roket", "Isi bintang dan luncurkan roket", 2, true);
        addGame("makan", "🐰", "Beri Makan Hewan", "Hewan suka makan apa?", 0, false);
        addGame("panen", "🥕", "Panen Sayur", "Panen sayur ke dalam kotak", 0, false);
        addGame("es", "🍦", "Buat Es Krim", "Pilih rasa, topping, lalu makan", 1, false);
        addGame("pasangan", "🃏", "Kartu Kembar", "Cari dua gambar yang sama", 0, false);
        addGame("hitung", "🍎", "Hitung Buah", "Petik buah sesuai angka", 0, false);
        addGame("profesi", "🚒", "Siapa Naik Apa?", "Cocokkan profesi dan kendaraannya", 0, false);
        addGame("sortir", "📦", "Masukkan ke Kotak", "Pilah buah dan sayur", 0, false);
        addGame("besarkecil", "🧸", "Besar dan Kecil", "Pilah yang besar dan kecil", 0, false);
        addGame("rumah", "🏡", "Rumah Hewan", "Laut, peternakan, atau hutan?", 0, false);
        addGame("bayangan", "🦒", "Cari Bayangan", "Cocokkan dengan bayangannya", 0, false);
        addGame("bentuk", "🔷", "Bentuk dan Lubang", "Masukkan bentuk ke lubangnya", 0, false);
        addGame("pelangi", "🌈", "Susun Pelangi", "Susun warna pelangi yang indah", 0, false);
        addGame("puzzle", "🧩", "Puzzle", "Susun potongan gambar", 0, false);
        addGame("jalan", "🛣️", "Antar Sampai Tujuan", "Ikuti jalan sampai tujuan", 0, false);
        addGame("dokter", "🩺", "Dokter Hewan", "Obati hewan yang sakit", 1, false);
        addGame("dandan", "🎩", "Dandani Teman", "Pakaikan topi dan kacamata", 1, false);
        addGame("mandi", "🛁", "Mandikan Hewan", "Gosok lumpurnya sampai bersih", 1, false);
        addGame("gigi", "🦷", "Sikat Gigi", "Sikat kuman sampai bersih", 1, false);
        addGame("kue", "🎂", "Kue Ulang Tahun", "Pasang lilin, lalu tiup", 1, false);
        addGame("kereta", "🚂", "Kereta Hewan", "Naikkan hewan ke gerbong", 1, false);
        addGame("warnai", "🖍️", "Mewarnai", "Ketuk untuk mewarnai rumah", 1, false);
        addGame("pancing", "🎣", "Memancing", "Tangkap lima ikan", 2, false);

        for (GameDef g : GAMES) {
            LINES.put("g_" + g.id, "Yuk, main " + g.name.toLowerCase() + "!");
        }
    }

    private static void addItem(String key, String emoji, String name, String soundLine, String cat) {
        ITEMS.put(key, new Item(key, emoji, name, soundLine, cat));
        if (soundLine != null) VOCAL_ANIMALS.add(key);
    }

    private static void addGame(String id, String icon, String name, String desc, int section, boolean dark) {
        int col = TILE_COLORS[GAMES.size() % TILE_COLORS.length];
        GAMES.add(new GameDef(id, icon, name, desc, section, col, dark));
    }
}
