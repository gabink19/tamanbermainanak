package com.gabink.tamanbermain;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundManager {
    private static SoundManager instance;
    private final Context ctx;
    private final Prefs prefs;
    private final ExecutorService synthPool = Executors.newCachedThreadPool();
    private final Handler main = new Handler(Looper.getMainLooper());
    private MediaPlayer voicePlayer;
    private MediaPlayer realPlayer;
    private TextToSpeech tts;
    private boolean ttsReady = false;

    public static synchronized SoundManager get(Context c) {
        if (instance == null) instance = new SoundManager(c.getApplicationContext());
        return instance;
    }

    private SoundManager(Context c) {
        this.ctx = c;
        this.prefs = new Prefs(c);
        tts = new TextToSpeech(c, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int r = tts.setLanguage(new Locale("id", "ID"));
                ttsReady = (r != TextToSpeech.LANG_MISSING_DATA && r != TextToSpeech.LANG_NOT_SUPPORTED);
                tts.setSpeechRate(0.95f);
                tts.setPitch(1.1f);
            }
        });
    }

    public void playVoice(String key) {
        if (!prefs.getBool("voice", true) || key == null) return;
        stopVoice();
        String tempo = prefs.getStr("tempo", "75");
        String path = "suara-" + tempo + "/" + key + ".mp3";
        try {
            AssetFileDescriptor afd = ctx.getAssets().openFd(path);
            voicePlayer = new MediaPlayer();
            voicePlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            voicePlayer.setOnCompletionListener(mp -> { mp.release(); if (voicePlayer == mp) voicePlayer = null; });
            voicePlayer.prepare();
            voicePlayer.start();
            return;
        } catch (Exception ignored) {}

        String line = GameData.LINES.get(key);
        if (line != null && ttsReady) {
            tts.speak(line, TextToSpeech.QUEUE_FLUSH, null, key);
        }
    }

    public void playReal(String key) {
        if (!prefs.getBool("sfx", true) || key == null) return;
        try {
            if (realPlayer != null) {
                try { realPlayer.stop(); } catch (Exception ignored) {}
                realPlayer.release();
                realPlayer = null;
            }
            AssetFileDescriptor afd = ctx.getAssets().openFd("sfx-real/" + key + ".mp3");
            realPlayer = new MediaPlayer();
            realPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            realPlayer.setOnCompletionListener(mp -> { mp.release(); if (realPlayer == mp) realPlayer = null; });
            realPlayer.prepare();
            realPlayer.start();
        } catch (Exception ignored) {}
    }

    public void stopVoice() {
        if (voicePlayer != null) {
            try { voicePlayer.stop(); } catch (Exception ignored) {}
            voicePlayer.release();
            voicePlayer = null;
        }
        if (tts != null) tts.stop();
    }

    public void stopAll() {
        stopVoice();
        if (realPlayer != null) {
            try { realPlayer.stop(); } catch (Exception ignored) {}
            realPlayer.release();
            realPlayer = null;
        }
    }

    public void tone(double freqStart, double freqEnd, int durMs, double vol) {
        if (!prefs.getBool("sfx", true)) return;
        synthPool.execute(() -> {
            int sr = 22050;
            int samples = Math.max(1, (sr * durMs) / 1000);
            short[] buf = new short[samples];
            double phase = 0;
            for (int i = 0; i < samples; i++) {
                double p = (double) i / samples;
                double f = freqStart + (freqEnd - freqStart) * p;
                double env = Math.min(1.0, i / 120.0) * (1.0 - p);
                buf[i] = (short) (Math.sin(phase) * Short.MAX_VALUE * vol * env);
                phase += 2.0 * Math.PI * f / sr;
            }
            playPcm(buf, sr);
        });
    }

    public void noise(int durMs, double vol) {
        if (!prefs.getBool("sfx", true)) return;
        synthPool.execute(() -> {
            int sr = 22050;
            int samples = Math.max(1, (sr * durMs) / 1000);
            short[] buf = new short[samples];
            for (int i = 0; i < samples; i++) {
                double env = 1.0 - (double) i / samples;
                buf[i] = (short) ((Math.random() * 2 - 1) * Short.MAX_VALUE * vol * env);
            }
            playPcm(buf, sr);
        });
    }

    private void playPcm(short[] buf, int sr) {
        try {
            AudioTrack at = new AudioTrack.Builder()
                .setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build())
                .setAudioFormat(new AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(sr)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build())
                .setBufferSizeInBytes(buf.length * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build();
            at.write(buf, 0, buf.length);
            at.play();
            main.postDelayed(() -> {
                try { at.stop(); at.release(); } catch (Exception ignored) {}
            }, (buf.length * 1000L / sr) + 80);
        } catch (Exception ignored) {}
    }

    public void tap() { tone(880, 880, 75, 0.18); }
    public void pick() { tone(520, 900, 110, 0.22); }
    public void pop() { tone(500, 1200, 110, 0.25); }
    public void plop() { tone(300, 120, 160, 0.28); noise(50, 0.14); }
    public void boing() { tone(180, 560, 320, 0.28); }
    public void wrong() { tone(320, 200, 220, 0.22); }
    public void chomp() { for (int i = 0; i < 3; i++) main.postDelayed(() -> noise(70, 0.26), i * 140L); }
    public void drive() { tone(90, 240, 850, 0.14); }
    public void sparkle() {
        double[] notes = {880, 1046.5, 1318.5, 1568};
        for (int i = 0; i < notes.length; i++) {
            final double f = notes[i];
            main.postDelayed(() -> tone(f, f, 200, 0.18), i * 50L);
        }
    }
    public void good() { tone(660, 660, 120, 0.22); main.postDelayed(() -> tone(990, 990, 200, 0.22), 90); }
    public void star(int i) { double[] f = {784, 988, 1175}; tone(f[Math.min(i, 2)], f[Math.min(i, 2)], 240, 0.24); }
    public void note(double f) { tone(f, f, 450, 0.28); }
    public void whoosh() { tone(120, 520, 650, 0.22); noise(500, 0.18); }
    public void ding() {
        double[] notes = {523.25, 659.25, 783.99, 1046.5};
        for (int i = 0; i < notes.length; i++) {
            final double f = notes[i];
            main.postDelayed(() -> tone(f, f, 260, 0.22), i * 75L);
        }
    }
}
