package controller;

import javax.swing.Timer;

public class GameLoop {
    private final Timer timer;
    private final GameController gameController;   // ← Field này giờ ĐÃ ĐƯỢC SỬ DỤNG

    public GameLoop(GameController gameController, int delay) {
        this.gameController = gameController;      // gán field
        // SỬA Ở ĐÂY: dùng this.gameController thay vì parameter trực tiếp
        timer = new Timer(delay, e -> this.gameController.update());
    }

    public void start() {
        if (!timer.isRunning()) timer.start();
    }

    public void stop() {
        if (timer.isRunning()) timer.stop();
    }

    public void increaseSpeed() {
        int newDelay = Math.max(40, timer.getDelay() - 5);
        timer.setDelay(newDelay);
    }
}
