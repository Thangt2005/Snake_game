package View;

import Controller.GameLogic;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * THÀNH PHẦN VIEW: Tiếp nhận tương tác phần cứng từ người chơi.
 * Ánh xạ trực tiếp tới UC-02: Điều khiển hướng di chuyển.
 */
public class InputListener extends KeyAdapter {
    private final GameLogic controller;

    public InputListener(GameLogic controller) {
        this.controller = controller;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        /* * =================================────────────────===================
         * [BƯỚC 2 TRONG USE CASE / SEQUENCE DIAGRAM]: 
         * Thành phần InputListener (View) nhận diện sự kiện phần cứng, thu giữ 
         * mã phím (keyCode) và chuyển tiếp thông tin sang cho GameLogic (Controller) 
         * thông qua hàm handleInput(keyCode).
         * =================================────────────────===================
         */
        controller.handleInput(keyCode);
    }
}