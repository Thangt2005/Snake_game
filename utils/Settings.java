package utils;

import java.awt.Color;
import java.io.*;

/**
 * Lưu trữ và đọc/ghi cài đặt màu sắc của game vào file settings.txt
 * Format file:
 *   snake=R,G,B
 *   food=R,G,B
 *   bg=R,G,B
 */
public class Settings {

    private static final String SETTINGS_FILE = "settings.txt";

    // Màu mặc định
    private Color snakeColor = Color.GREEN;
    private Color foodColor  = Color.RED;
    private Color bgColor    = Color.BLACK;

    // ── Getter / Setter ──────────────────────────────────────────────────────

    public Color getSnakeColor() { return snakeColor; }
    public Color getFoodColor()  { return foodColor;  }
    public Color getBgColor()    { return bgColor;    }

    public void setSnakeColor(Color c) { if (c != null) snakeColor = c; }
    public void setFoodColor (Color c) { if (c != null) foodColor  = c; }
    public void setBgColor   (Color c) { if (c != null) bgColor    = c; }

    // ── Lưu xuống file ───────────────────────────────────────────────────────

    public void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SETTINGS_FILE))) {
            pw.println("snake=" + colorToString(snakeColor));
            pw.println("food="  + colorToString(foodColor));
            pw.println("bg="    + colorToString(bgColor));
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi settings: " + e.getMessage());
        }
    }

    // ── Đọc từ file (trả về Settings với giá trị đã lưu hoặc mặc định) ──────

    public static Settings load() {
        Settings s = new Settings();
        File file = new File(SETTINGS_FILE);
        if (!file.exists()) return s;           // dùng giá trị mặc định

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length != 2) continue;
                Color color = stringToColor(parts[1].trim());
                if (color == null) continue;
                switch (parts[0].trim()) {
                    case "snake" -> s.snakeColor = color;
                    case "food"  -> s.foodColor  = color;
                    case "bg"    -> s.bgColor    = color;
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc settings: " + e.getMessage());
        }
        return s;
    }

    // ── Helper ───────────────────────────────────────────────────────────────

    private String colorToString(Color c) {
        return c.getRed() + "," + c.getGreen() + "," + c.getBlue();
    }

    private static Color stringToColor(String s) {
        try {
            String[] rgb = s.split(",");
            return new Color(Integer.parseInt(rgb[0].trim()),
                             Integer.parseInt(rgb[1].trim()),
                             Integer.parseInt(rgb[2].trim()));
        } catch (Exception e) {
            return null;
        }
    }
}
