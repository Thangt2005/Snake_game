package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<int[]> body;
    private String direction;
    private boolean isAlive;
    private Color color = new Color(0, 100, 255); // mặc định xanh dương

    public Snake(int startX, int startY) {
        body = new ArrayList<>();
        body.add(new int[]{startX, startY});
        direction = "RIGHT";
        isAlive = true;
    }

    public Color getColor() { return color; }
    public void setColor(Color c) { if (c != null) color = c; }

    public List<int[]> getBody() { return body; }
    public String getDirection() { return direction; }
    public boolean isAlive() { return isAlive; }
    public void setAlive(boolean alive) { isAlive = alive; }

    public void setDirection(String newDir) {
        if ((newDir.equals("UP") && direction.equals("DOWN")) ||
            (newDir.equals("DOWN") && direction.equals("UP")) ||
            (newDir.equals("LEFT") && direction.equals("RIGHT")) ||
            (newDir.equals("RIGHT") && direction.equals("LEFT"))) return;
        this.direction = newDir;
    }

    public void move() {
        int[] head = body.get(0);
        int[] newHead = new int[]{head[0], head[1]};
        switch (direction) {
            case "UP" -> newHead[1]--;
            case "DOWN" -> newHead[1]++;
            case "LEFT" -> newHead[0]--;
            case "RIGHT" -> newHead[0]++;
        }
        body.add(0, newHead);
        body.remove(body.size() - 1);
    }

    public void grow() {
        int[] tail = body.get(body.size() - 1);
        body.add(new int[]{tail[0], tail[1]});
    }
}
