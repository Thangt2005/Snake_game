package utils;

// Tiêu chí: Tự định nghĩa ít nhất 1 Custom Exception (image_6.png)
public class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}
