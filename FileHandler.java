// file ghi nhận số điểm cao nhất mà người dùng đạt được và lưu vào file highscore.txt

package utils;

import java.io.*;

public class FileHandler {
    private static final String HIGH_SCORE_FILE = "highscore.txt";
	
// lưu điểm cao nhất vào file highscore.txt
    public static int readHighScore() {
        File file = new File(HIGH_SCORE_FILE);
        if (!file.exists()) return 0;
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line = in.readLine();
            if (line != null) return Integer.parseInt(line.trim());
        } catch (Exception e) {}
        return 0;
    }

// hàm đọc điểm cao nhất được lưu trong file highscore.txt
    public static void saveHighScore(int score) {
        if (score <= readHighScore()) return;
        try (PrintWriter out = new PrintWriter(new FileWriter(HIGH_SCORE_FILE))) {
            out.println(score);
        } catch (IOException e) {}
    }
}
