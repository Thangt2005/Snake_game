package utils;

public enum GameMode {
    CLASSIC("Cổ điển"),
    NO_WALLS("Xuyên tường"),
    SPEED_RUSH("Tăng tốc");     // ← Chế độ mới

    private final String displayName;

    GameMode(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
