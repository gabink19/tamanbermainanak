/* Isi permainan + naskah suara. Dipakai halaman (window.DATA / window.LINES)
   dan oleh tools/buat-suara.mjs untuk membuat file suara/<kunci>.mp3.
   Naskah sengaja ditulis sebagai kalimat utuh yang luwes: suara multibahasa
   mengenali bahasa Indonesia lebih baik dari kalimat daripada satu kata. */
(function (W) {
  const ANIMALS = {
    kucing:  { e: '🐱', n: 'Kucing',    s: 'Meong, meong!' },
    anjing:  { e: '🐶', n: 'Anjing',    s: 'Guk, guk, guk!' },
    sapi:    { e: '🐮', n: 'Sapi',      s: 'Mooo!' },
    ayam:    { e: '🐔', n: 'Ayam',      s: 'Kukuruyuuuk!' },
    bebek:   { e: '🦆', n: 'Bebek',     s: 'Kwek, kwek, kwek!' },
    kambing: { e: '🐐', n: 'Kambing',   s: 'Mbeeek!' },
    domba:   { e: '🐑', n: 'Domba',     s: 'Beee!' },
    kuda:    { e: '🐴', n: 'Kuda',      s: 'Hiiihiii!' },
    katak:   { e: '🐸', n: 'Katak',     s: 'Kwok, kwok!' },
    singa:   { e: '🦁', n: 'Singa',     s: 'Auuum!' },
    harimau: { e: '🐯', n: 'Harimau',   s: 'Grauum!' },
    gajah:   { e: '🐘', n: 'Gajah',     s: 'Prooot!' },
    monyet:  { e: '🐵', n: 'Monyet',    s: 'Uu, aa, aa!' },
    kelinci: { e: '🐰', n: 'Kelinci',   s: 'Hop, hop, hop!' },
    panda:   { e: '🐼', n: 'Panda',     s: 'Halo, halo!' },
    tikus:   { e: '🐭', n: 'Tikus',     s: 'Cit, cit, cit!' },
    beruang: { e: '🐻', n: 'Beruang',   s: 'Grrr!' },
    burung:  { e: '🐦', n: 'Burung',    s: 'Cuit, cuit!' },
    burunghantu: { e: '🦉', n: 'Burung hantu', s: 'Hu, hu, hu!' },
    lebah:   { e: '🐝', n: 'Lebah',     s: 'Nguuung!' },
    bayi:    { e: '👶', n: 'Adik bayi', s: 'Oek, oek!' },
    anakayam:{ e: '🐤', n: 'Anak ayam', s: 'Ciap, ciap!' },
    zebra:   { e: '🦓', n: 'Zebra' },
    jerapah: { e: '🦒', n: 'Jerapah' },
    kupukupu:{ e: '🦋', n: 'Kupu-kupu' },
    ikan:    { e: '🐟', n: 'Ikan' },
    ikanhias:{ e: '🐠', n: 'Ikan hias' },
    gurita:  { e: '🐙', n: 'Gurita' },
    kepiting:{ e: '🦀', n: 'Kepiting' },
    kurakura:{ e: '🐢', n: 'Kura-kura' },
    lumbalumba: { e: '🐬', n: 'Lumba-lumba' },
    paus:    { e: '🐳', n: 'Ikan paus' },
    hiu:     { e: '🦈', n: 'Ikan hiu' },
    buntal:  { e: '🐡', n: 'Ikan buntal' },
    cumi:    { e: '🦑', n: 'Cumi-cumi' },
    udang:   { e: '🦐', n: 'Udang' },
  };
  const SEA = ['ikan', 'ikanhias', 'gurita', 'kepiting', 'kurakura', 'lumbalumba', 'paus', 'hiu', 'buntal', 'cumi', 'udang'];
  const HABITAT = {
    laut: ['ikan', 'gurita', 'kepiting', 'lumbalumba', 'paus', 'kurakura'],
    ternak: ['sapi', 'ayam', 'kambing', 'kuda', 'domba', 'bebek'],
    hutan: ['singa', 'monyet', 'gajah', 'harimau', 'zebra', 'jerapah'],
  };
  const VEG = {
    wortel: { e: '🥕', n: 'Wortel' }, kentang: { e: '🥔', n: 'Kentang' }, jagung: { e: '🌽', n: 'Jagung' },
    terong: { e: '🍆', n: 'Terong' }, brokoli: { e: '🥦', n: 'Brokoli' }, sawi: { e: '🥬', n: 'Sawi' },
    bawang: { e: '🧅', n: 'Bawang' }, tomat: { e: '🍅', n: 'Tomat' }, timun: { e: '🥒', n: 'Timun' },
    cabai: { e: '🌶️', n: 'Cabai' }, paprika: { e: '🫑', n: 'Paprika' },
  };
  const FRUIT = {
    apel: { e: '🍎', n: 'Apel' }, pisang: { e: '🍌', n: 'Pisang' }, anggur: { e: '🍇', n: 'Anggur' },
    jeruk: { e: '🍊', n: 'Jeruk' }, semangka: { e: '🍉', n: 'Semangka' }, stroberi: { e: '🍓', n: 'Stroberi' },
    nanas: { e: '🍍', n: 'Nanas' }, mangga: { e: '🥭', n: 'Mangga' }, pir: { e: '🍐', n: 'Buah pir' },
    ceri: { e: '🍒', n: 'Ceri' }, kelapa: { e: '🥥', n: 'Kelapa' }, lemon: { e: '🍋', n: 'Lemon' },
  };
  const VEHICLES = {
    mobil: { e: '🚗', n: 'Mobil', z: 'darat' }, bus: { e: '🚌', n: 'Bus', z: 'darat' },
    kereta: { e: '🚂', n: 'Kereta api', z: 'darat' }, sepeda: { e: '🚲', n: 'Sepeda', z: 'darat' },
    motor: { e: '🏍️', n: 'Motor', z: 'darat' }, truk: { e: '🚚', n: 'Truk', z: 'darat' },
    kapal: { e: '🚢', n: 'Kapal laut', z: 'laut' }, perahu: { e: '⛵', n: 'Perahu layar', z: 'laut' },
    speedboat: { e: '🚤', n: 'Perahu motor', z: 'laut' },
    pesawat: { e: '✈️', n: 'Pesawat terbang', z: 'udara' }, helikopter: { e: '🚁', n: 'Helikopter', z: 'udara' },
    roket: { e: '🚀', n: 'Roket', z: 'udara' },
    ambulans: { e: '🚑', n: 'Mobil ambulans' }, mobilpolisi: { e: '🚓', n: 'Mobil polisi' },
    pemadam: { e: '🚒', n: 'Mobil pemadam kebakaran' }, traktor: { e: '🚜', n: 'Traktor' },
  };
  const FOODS = { wortel: '🥕', pisang: '🍌', ikan: '🐟', tulang: '🦴', rumput: '🌿', bambu: '🎋', keju: '🧀', madu: '🍯', jagung: '🌽', susu: '🍼' };
  const FOOD_NAMES = { wortel: 'wortel', pisang: 'pisang', ikan: 'ikan', tulang: 'tulang', rumput: 'rumput', bambu: 'bambu', keju: 'keju', madu: 'madu', jagung: 'jagung', susu: 'susu' };
  const FEED = [
    ['kelinci', 'wortel'], ['monyet', 'pisang'], ['kucing', 'ikan'], ['anjing', 'tulang'], ['sapi', 'rumput'],
    ['panda', 'bambu'], ['tikus', 'keju'], ['beruang', 'madu'], ['ayam', 'jagung'], ['bayi', 'susu'],
  ];
  const JOBS = [
    { k: 'dokter', e: '🧑‍⚕️', n: 'Dokter', v: 'ambulans', line: 'Bu dokter naik mobil ambulans. Nguing, nguing, nguing!' },
    { k: 'polisi', e: '👮', n: 'Polisi', v: 'mobilpolisi', line: 'Pak polisi naik mobil polisi. Nguing, nguing!' },
    { k: 'damkar', e: '🧑‍🚒', n: 'Pemadam kebakaran', v: 'pemadam', line: 'Petugas pemadam naik mobil pemadam kebakaran. Ayo, padamkan apinya!' },
    { k: 'petani', e: '🧑‍🌾', n: 'Petani', v: 'traktor', line: 'Pak tani naik traktor ke sawah. Dug, dug, dug!' },
    { k: 'pilot', e: '🧑‍✈️', n: 'Pilot', v: 'pesawat', line: 'Pilot menerbangkan pesawat tinggi sekali. Ngeeeng!' },
    { k: 'astronot', e: '🧑‍🚀', n: 'Astronot', v: 'roket', line: 'Astronot naik roket ke bulan. Wuuush!' },
    { k: 'tukang', e: '👷', n: 'Tukang bangunan', v: 'truk', line: 'Tukang bangunan naik truk. Brum, brum!' },
  ];
  const COLORS = {
    merah: { n: 'Merah', c: '#e63946' }, oranye: { n: 'Oranye', c: '#ff8c1a' }, kuning: { n: 'Kuning', c: '#ffd23f' },
    hijau: { n: 'Hijau', c: '#3fb950' }, biru: { n: 'Biru', c: '#2f80ed' }, ungu: { n: 'Ungu', c: '#8e44ec' },
    merahmuda: { n: 'Merah muda', c: '#ff7eb6' }, cokelat: { n: 'Cokelat', c: '#8b5a2b' },
    hitam: { n: 'Hitam', c: '#2a2140' }, putih: { n: 'Putih', c: '#ffffff' },
  };
  const COLOR_ITEMS = [
    ['apel', '🍎', 'merah'], ['stroberi', '🍓', 'merah'], ['ceri', '🍒', 'merah'], ['tomat', '🍅', 'merah'], ['pemadam', '🚒', 'merah'],
    ['pisang', '🍌', 'kuning'], ['jagung', '🌽', 'kuning'], ['lemon', '🍋', 'kuning'], ['anakayam', '🐤', 'kuning'], ['keju', '🧀', 'kuning'],
    ['brokoli', '🥦', 'hijau'], ['timun', '🥒', 'hijau'], ['katak', '🐸', 'hijau'], ['pir', '🍐', 'hijau'], ['sawi', '🥬', 'hijau'],
  ];
  const SHAPES = { lingkaran: 'Lingkaran', segitiga: 'Segitiga', persegi: 'Persegi', bintang: 'Bintang', hati: 'Hati' };
  const DOCTOR = [
    ['termometer', '🌡️', 'Pertama, kita cek suhunya dulu, ya. Hmm, agak panas.'],
    ['stetoskop', '🩺', 'Sekarang, kita dengarkan detak jantungnya. Dug, dug, dug.'],
    ['obat', '💊', 'Ini obatnya. Diminum, ya, supaya cepat sembuh.'],
    ['plester', '🩹', 'Terakhir, kita pasang plester di lukanya. Nah, sudah!'],
  ];
  const PATIENTS = ['kucing', 'anjing', 'kelinci', 'panda', 'beruang', 'monyet'];
  const ACCESSORIES = [
    { k: 'topi', e: '🎩', slot: 'kepala', line: 'Pakai topi. Wah, gagah sekali!' },
    { k: 'mahkota', e: '👑', slot: 'kepala', line: 'Pakai mahkota, seperti raja!' },
    { k: 'topibisbol', e: '🧢', slot: 'kepala', line: 'Pakai topi biru. Keren!' },
    { k: 'kacamatahitam', e: '🕶️', slot: 'mata', line: 'Pakai kacamata hitam. Gaya sekali!' },
    { k: 'kacamata', e: '👓', slot: 'mata', line: 'Pakai kacamata. Kelihatan pintar!' },
    { k: 'pita', e: '🎀', slot: 'leher', line: 'Pakai pita. Cantik sekali!' },
    { k: 'dasi', e: '👔', slot: 'leher', line: 'Pakai dasi. Rapi sekali!' },
    { k: 'syal', e: '🧣', slot: 'leher', line: 'Pakai syal, supaya hangat.' },
  ];
  const INSTRUMENTS = [
    ['drum', '🥁', 'Drum', 'Ini drum. Dum, dum, dum!'],
    ['gitar', '🎸', 'Gitar', 'Ini gitar. Jreng, jreng!'],
    ['terompet', '🎺', 'Terompet', 'Ini terompet. Tet, tereret!'],
    ['biola', '🎻', 'Biola', 'Ini biola. Suaranya merdu sekali.'],
    ['saksofon', '🎷', 'Saksofon', 'Ini saksofon. Dudu, duuu!'],
    ['lonceng', '🔔', 'Lonceng', 'Ini lonceng. Kling, kling, kling!'],
    ['gendang', '🪘', 'Gendang', 'Ini gendang. Tak, dung, tak, dung!'],
    ['tepuk', '👏', 'Tepuk tangan', 'Ayo, tepuk tangan! Prok, prok, prok!'],
  ];
  const GAMES = {
    profesi: 'Siapa Naik Apa?', panen: 'Panen Sayur', sortir: 'Masukkan ke Kotak', makan: 'Beri Makan Hewan',
    dokter: 'Dokter Hewan', dandan: 'Dandani Teman', kereta: 'Kereta Hewan', jalan: 'Antar Sampai Tujuan',
    kue: 'Kue Ulang Tahun', hitung: 'Hitung Buah', bentuk: 'Bentuk dan Lubang', besarkecil: 'Besar dan Kecil',
    rumah: 'Rumah Hewan', bayangan: 'Cari Bayangan', puzzle: 'Puzzle', mandi: 'Mandikan Hewan', gigi: 'Sikat Gigi',
    pancing: 'Memancing', pasangan: 'Kartu Kembar', warnai: 'Mewarnai', balon: 'Balon', gelembung: 'Gelembung Laut',
    kembang: 'Kembang Api', piano: 'Piano', musik: 'Alat Musik', cilukba: 'Cilukba', telepon: 'Telepon',
    tangkap: 'Tangkap Bintang', es: 'Buat Es Krim', sembunyi: 'Cari Hewan', pelangi: 'Susun Pelangi', roket: 'Luncurkan Roket',
  };
  const NUM = ['Nol', 'Satu', 'Dua', 'Tiga', 'Empat', 'Lima', 'Enam', 'Tujuh', 'Delapan', 'Sembilan', 'Sepuluh'];

  /* ---------- naskah suara (kunci -> kalimat) ---------- */
  const L = {};
  const low = s => s.toLowerCase();
  Object.assign(L, {
    yeay: 'Yeay, pintar!', hebat: 'Hebat sekali!', pintar: 'Wah, pintar sekali!', hore: 'Horee, betul!',
    keren: 'Keren banget!', mantap: 'Mantap sekali!', betul: 'Betul sekali!', bagus: 'Bagus! Tepat sekali!',
    ups: 'Ups, bukan di situ. Coba lagi, ya!', coba: 'Hmm, bukan yang itu. Coba yang lain, yuk!',
    menang1: 'Yeay, berhasil! Kamu hebat sekali!', menang2: 'Horee! Kamu pintar sekali!', menang3: 'Hebat! Ayo, kita main lagi!',
    halo: 'Halo! Ayo kita bermain bersama!', tes: 'Halo, adik! Aku teman bermainmu. Yuk, kita main bersama!',
  });
  for (const [k, n] of Object.entries(GAMES)) L['g_' + k] = `Yuk, main ${low(n).replace('?', '')}!`;
  L.g_profesi = 'Yuk, main siapa naik apa!';
  Object.assign(L, {
    profesi_intro: 'Siapa naik apa, ya? Ayo, bantu mereka naik kendaraannya!',
    panen_intro: 'Ayo panen sayur! Masukkan sayurnya ke dalam kotak, ya!',
    panen_done: 'Wah, panennya banyak sekali! Kamu hebat!',
    sortir_buahsayur: 'Buah masuk ke kotak buah. Sayur masuk ke kotak sayur, ya!',
    sortir_warna: 'Masukkan ke keranjang yang warnanya sama, ya!',
    sortir_dlu: 'Mana yang jalan di darat, berlayar di laut, dan terbang di udara?',
    box_buah: 'Ini kotak buah.', box_sayur: 'Ini kotak sayur.', box_darat: 'Ini darat.', box_laut: 'Ini laut.', box_udara: 'Ini udara.',
    box_ternak: 'Ini peternakan.', box_hutan: 'Ini hutan.', box_besar: 'Ini kotak besar.', box_kecil: 'Ini kotak kecil.',
    makan_no: 'Hmm, bukan itu. Coba yang lain, yuk!',
    bayangan_intro: 'Ayo, cari bayangannya! Seret gambarnya ke bayangan yang sama, ya.',
    puzzle_intro: 'Ayo, susun puzzlenya!', puzzle_done: 'Yeay! Gambarnya sudah jadi!',
    mandi_intro: 'Wah, kotor sekali! Ayo, gosok sampai bersih!', mandi_done: 'Wah, sudah bersih dan wangi!',
    pancing_intro: 'Ayo memancing! Ketuk ikannya, ya!', pancing_done: 'Wah, dapat lima ikan! Hebat!',
    pasangan_intro: 'Ayo, cari dua gambar yang sama!',
    warnai_intro: 'Pilih warnanya dulu, lalu ketuk gambarnya, ya!', warnai_done: 'Wah, gambarnya cantik sekali!',
    balon_intro: 'Ayo, pecahkan balonnya! Dor, dor!',
    gelembung_intro: 'Ayo, pecahkan gelembungnya! Ada hewan laut di dalamnya.',
    kembang_intro: 'Ketuk langitnya, ya! Duar, duar!',
    piano_intro: 'Ayo, main piano! Ketuk tutsnya, ya.',
    musik_intro: 'Ayo, main musik! Ketuk alat musiknya, ya.',
    cilukba: 'Ciluk... baa!', dimana: 'Di mana, ya? Ayo, buka selimutnya!',
    menelepon: 'Kring, kring! Siapa yang menelepon, ya?', dadah: 'Dadah! Sampai jumpa lagi!',
    dokter_intro: 'Kita jadi dokter hewan, yuk!',
    dandan_intro: 'Ayo, kita dandani! Seret topi ke kepala, kacamata ke mata, dan pita ke leher.',
    dandan_done: 'Wah, sekarang jadi keren sekali!',
    kereta_intro: 'Ayo, bantu hewan-hewan naik kereta api!',
    kereta_done: 'Semua sudah naik. Kereta berangkat! Tut, tut, tuuut!',
    jalan_intro: 'Ayo, antar sampai ke tujuan! Seret pelan-pelan, ikuti jalannya, ya.',
    jalan_done: 'Yeay, sudah sampai!',
    kue_intro: 'Hari ini ada yang ulang tahun! Ayo, pasang lilinnya di atas kue!',
    kue_tiup: 'Selamat ulang tahun! Ayo, tiup lilinnya! Ketuk apinya, ya.',
    kue_done: 'Horee! Selamat ulang tahun!',
    bentuk_intro: 'Ayo, masukkan bentuknya ke lubang yang pas!',
    besarkecil_intro: 'Yang besar masuk kotak besar. Yang kecil masuk kotak kecil, ya!',
    besar: 'Yang ini besar!', kecil: 'Yang ini kecil!',
    rumah_intro: 'Ayo, antar hewan ke rumahnya! Ada laut, peternakan, dan hutan.',
    gigi_intro: 'Ih, ada kuman di gigi! Ayo, sikat giginya sampai bersih!',
    gigi_done: 'Wah, giginya bersih dan berkilau! Senyum, dong!',
    hitung_ayo: 'Ayo, kita hitung bersama!',
    tangkap_intro: 'Ayo tangkap bintangnya! Ketuk bintang yang bersinar, ya!',
    tangkap_done: 'Wah, bintangnya banyak sekali! Cantik, ya!',
    es_intro: 'Yuk, buat es krim! Pilih tiga rasa es krim kesukaanmu!',
    es_topping: 'Sekarang tambahkan topping di atasnya, ya!',
    es_makan: 'Wah, es krimnya enak! Ketuk es krimnya untuk dimakan, nyam!',
    sembunyi_intro: 'Hewannya bersembunyi! Di mana, ya? Ketuk semaknya!',
    pelangi_intro: 'Ayo susun warna pelangi! Merah, oranye, kuning, hijau, biru, dan ungu!',
    pelangi_done: 'Wah, pelanginya indah sekali!',
    roket_intro: 'Ayo isi bahan bakar roket! Ketuk lima bintangnya, ya!',
    roket_siap: 'Roket sudah penuh! Ketuk tombol roket untuk meluncur!',
    roket_meluncur: 'Meluncur ke luar angkasa! Wuuush!',
    cici1: 'Cuit, cuit! Aku Cici! Ayo kita main bersama!',
    cici2: 'Kamu anak hebat! Yuk, pilih permainan kesukaanmu!',
    cici3: 'Cilukba! Banyak stiker lucu menunggumu!',
  });
  for (let i = 0; i <= 10; i++) L['num_' + i] = NUM[i] + '!';
  for (let i = 1; i <= 5; i++) {
    L['hitung_' + i] = `Masukkan ${low(NUM[i])} buah ke keranjang, ya!`;
    L['hitungok_' + i] = `Pas! Ada ${low(NUM[i])} buah di keranjang. Pintar!`;
  }
  for (const [k, c] of Object.entries(COLORS)) L['c_' + k] = `Ini warna ${low(c.n)}!`;
  for (const [k, n] of Object.entries(SHAPES)) L['s_' + k] = `Ini ${low(n)}!`;
  for (const [k, a] of Object.entries(ANIMALS)) {
    L['n_' + k] = `Ini ${low(a.n)}!`;
    if (a.s) {
      L['ada_' + k] = `Wah, ada ${low(a.n)}! ${a.s}`;
      L['halo_' + k] = `Halo, halo! Ini ${low(a.n)}. ${a.s} Apa kabar?`;
      L['cari_' + k] = `Di mana ${low(a.n)}, ya? ${a.s} Ayo, cari!`;
    }
  }
  for (const [k, v] of Object.entries(VEG)) L['n_' + k] = `Ini ${low(v.n)}!`;
  for (const [k, v] of Object.entries(FRUIT)) L['n_' + k] = `Ini ${low(v.n)}!`;
  for (const [k, v] of Object.entries(VEHICLES)) L['n_' + k] = `Ini ${low(v.n)}!`;
  Object.assign(L, { n_keju: 'Ini keju!', n_tulang: 'Ini tulang!', n_rumput: 'Ini rumput!', n_bambu: 'Ini bambu!', n_madu: 'Ini madu!', n_susu: 'Ini susu!' });
  for (const j of JOBS) { L['n_' + j.k] = `Ini ${low(j.n)}!`; L['job_' + j.k] = j.line; }
  for (const [a, f] of FEED) {
    const n = ANIMALS[a].n;
    L['lapar_' + a] = `Aduh, ${low(n)} lapar, nih. ${n} mau makan apa, ya?`;
    L['nyam_' + a] = `Nyam, nyam, nyam! ${n} suka sekali makan ${FOOD_NAMES[f]}. Terima kasih!`;
  }
  for (const [k, , line] of DOCTOR) L['dok_' + k] = line;
  for (const a of PATIENTS) {
    L['sakit_' + a] = `Aduh, ${low(ANIMALS[a].n)} sedang sakit. Ayo, kita obati!`;
    L['sembuh_' + a] = `Terima kasih, dokter! ${ANIMALS[a].n} sudah sehat lagi!`;
  }
  for (const a of ACCESSORIES) L['aks_' + a.k] = a.line;
  for (const [k, , , line] of INSTRUMENTS) L['alat_' + k] = line;

  W.DATA = { ANIMALS, SEA, HABITAT, VEG, FRUIT, VEHICLES, FOODS, FEED, JOBS, COLORS, COLOR_ITEMS, SHAPES, DOCTOR, PATIENTS, ACCESSORIES, INSTRUMENTS, GAMES, NUM };
  W.LINES = L;
})(typeof window !== 'undefined' ? window : globalThis);
