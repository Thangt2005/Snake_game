package utils;

public enum Difficulty {
    EASY(150), MEDIUM(100), HARD(60);
    private final int delay;
    Difficulty(int delay) { this.delay = delay; }
    public int getDelay() { return delay; }
}
