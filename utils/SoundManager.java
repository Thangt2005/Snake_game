package utils;

import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {
    private static Clip backgroundClip;
    private static FloatControl backgroundVolumeControl;

    private static boolean isMusicEnabled = true;
    private static boolean isSfxEnabled = true;

    private static float musicVolume = 1.0f;

    // === GETTER PUBLIC (sửa lỗi compile) ===
    public static boolean isMusicEnabled() { return isMusicEnabled; }
    public static boolean isSfxEnabled() { return isSfxEnabled; }

    public static void playBackgroundMusic() {
        if (!isMusicEnabled) return;
        try {
            File file = new File("sounds/background.wav");
            if (!file.exists()) {
                System.err.println("Không tìm thấy file nhạc nền: sounds/background.wav");
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioStream);

            if (backgroundClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                backgroundVolumeControl = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);
                setMusicVolume(musicVolume);
            }
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundClip.start();
        } catch (Exception e) {
            System.err.println("❌ Lỗi phát nhạc nền: " + e.getMessage());
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.stop();
            backgroundClip.close();
            backgroundClip = null;
        }
    }

    public static void playEat()     { playSound("eat.wav"); }
    public static void playHit()     { playSound("hit.wav"); }
    public static void playClick()   { playSound("click.wav"); }
    public static void playLose()    { playSound("lose.wav"); }
    public static void playWin()     { playSound("win.wav"); }

    private static void playSound(String soundName) {
        if (!isSfxEnabled) return;
        try {
            File file = new File("sounds/" + soundName);
            if (!file.exists()) return;
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception ignored) {}
    }

    public static void setMusicVolume(float volume) {
        musicVolume = Math.max(0.0f, Math.min(1.0f, volume));
        if (backgroundVolumeControl != null) {
            float dB = (float) (20 * Math.log10(musicVolume == 0 ? 0.0001 : musicVolume));
            dB = Math.max(backgroundVolumeControl.getMinimum(), Math.min(backgroundVolumeControl.getMaximum(), dB));
            backgroundVolumeControl.setValue(dB);
        }
    }

    public static void setMusicEnabled(boolean enabled) {
        isMusicEnabled = enabled;
        if (enabled) playBackgroundMusic();
        else stopBackgroundMusic();
    }

    public static void setSfxEnabled(boolean enabled) {
        isSfxEnabled = enabled;
    }
}
