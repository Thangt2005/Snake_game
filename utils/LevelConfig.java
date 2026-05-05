package utils;

public class LevelConfig {
    public final int level;
    public final int requiredFood;
    public final int numObstacles;
    public final int delay;

    public LevelConfig(int level, int requiredFood, int numObstacles, int delay) {
        this.level = level;
        this.requiredFood = requiredFood;
        this.numObstacles = numObstacles;
        this.delay = delay;
    }
}
