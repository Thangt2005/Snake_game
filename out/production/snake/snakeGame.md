# 🎮 BUSINESS REQUIREMENT DOCUMENT (BRD)
## 📌 Dự án: Game Rắn Săn Mồi (Snake Game)

---

## 1. 📖 Giới thiệu

### 1.1 Mục tiêu
Xây dựng trò chơi **Rắn săn mồi (Snake Game)** nhằm:
- Giải trí cho người chơi
- Rèn luyện phản xạ và tư duy
- Áp dụng kiến thức lập trình hướng đối tượng và phát triển game 2D

---

### 1.2 Phạm vi dự án
- Nền tảng: Desktop
- Công nghệ: Java (Swing / JavaFX)
- Đối tượng người dùng: Sinh viên, người chơi phổ thông
- Chế độ chơi: Single Player

---

### 1.3 Định nghĩa thuật ngữ

| Thuật ngữ | Ý nghĩa |
|----------|--------|
| Snake | Nhân vật chính (con rắn) |
| Food | Mồi để rắn ăn |
| Score | Điểm số |
| Game Loop | Vòng lặp game |
| Collision | Va chạm |

---

## 2. 🎯 Stakeholders

| Vai trò | Mô tả |
|--------|------|
| Người chơi | Trải nghiệm game |
| Developer | Phát triển hệ thống |
| Giảng viên | Đánh giá dự án |

---

## 3. 🎮 Tổng quan hệ thống

Game là ứng dụng 2D gồm:
- Giao diện đồ họa
- Điều khiển bằng bàn phím
- Cơ chế cập nhật liên tục (Game Loop)

---

## 4. 📋 Business Requirements

### 4.1 Gameplay
- Người chơi điều khiển rắn di chuyển trên bản đồ
- Rắn ăn mồi để:
  - Tăng chiều dài
  - Tăng điểm
- Tốc độ game tăng dần theo điểm

---

### 4.2 Luật chơi
- Game kết thúc khi:
  - Rắn va vào tường
  - Rắn va vào chính thân mình
- Có thể chơi lại sau khi thua

---

### 4.3 Giao diện người dùng
- Hiển thị:
  - Điểm số (Score)
  - Trạng thái game (Running / Game Over)
  - Nút Start / Restart

---

## 5. ⚙️ Functional Requirements

### 5.1 Điều khiển
- Người chơi sử dụng bàn phím:
  - ↑ / W: Di chuyển lên
  - ↓ / S: Di chuyển xuống
  - ← / A: Di chuyển trái
  - → / D: Di chuyển phải

---

### 5.2 Hệ thống rắn
- Rắn gồm nhiều segment (đoạn)
- Di chuyển theo đầu rắn
- Thân rắn đi theo quỹ đạo của đầu

---

### 5.3 Hệ thống mồi
- Mồi xuất hiện ngẫu nhiên trên bản đồ
- Không xuất hiện trùng với thân rắn
- Sau khi ăn → sinh mồi mới

---

### 5.4 Tính điểm
- +1 điểm mỗi lần ăn mồi
- Điểm hiển thị real-time

---

### 5.5 Game Loop
- Game cập nhật theo chu kỳ thời gian (tick)
- Xử lý:
  - Di chuyển
  - Va chạm
  - Sinh mồi

---

### 5.6 Va chạm (Collision Detection)
- Va chạm với tường → Game Over
- Va chạm với thân → Game Over
- Va chạm với mồi → tăng điểm

---

### 5.7 Restart Game
- Khi Game Over:
  - Hiển thị thông báo
  - Cho phép Restart

---

## 6. 🚫 Non-Functional Requirements

| Yêu cầu | Mô tả |
|--------|------|
| Hiệu năng | Game chạy mượt, không lag |
| Độ phản hồi | Phản hồi điều khiển nhanh |
| Tính ổn định | Không bị crash |
| Dễ sử dụng | Giao diện đơn giản |
| Khả năng mở rộng | Dễ thêm tính năng mới |

---

## 7. 🧩 Use Cases

### UC-01: Start Game
- Người chơi nhấn Start
- Game bắt đầu

---

### UC-02: Điều khiển rắn
- Người chơi nhấn phím
- Rắn đổi hướng di chuyển

---

### UC-03: Ăn mồi
- Rắn chạm mồi
- Tăng điểm + tăng chiều dài

---

### UC-04: Game Over
- Xảy ra va chạm
- Hiển thị điểm
- Cho phép Restart

---

## 8. 🏗️ Kiến trúc hệ thống (Overview)

### Các thành phần chính:
- Game Panel (hiển thị)
- Player (Snake)
- Food (Mồi)
- Game Engine (Logic)
- Collision Checker

---

## 9. 🖥️ Thiết kế giao diện (UI)

### Màn hình chính
- Nút Start

### Màn hình chơi
- Bản đồ
- Rắn
- Mồi
- Score

### Màn hình Game Over
- Thông báo thua
- Nút Restart

---

## 10. 🧪 Kiểm thử (Testing)

| Test Case | Mô tả |
|----------|------|
| Di chuyển | Rắn di chuyển đúng hướng |
| Ăn mồi | Tăng điểm |
| Va chạm | Game Over |
| Restart | Chơi lại bình thường |

---

## 11. ⚠️ Rủi ro

| Rủi ro | Giải pháp |
|-------|----------|
| Lag khi rắn dài | Tối ưu xử lý |
| Lỗi va chạm | Test kỹ |
| UI đơn giản | Nâng cấp sau |

---

## 12. 🔄 Future Enhancements

- Thêm level
- Thêm chướng ngại vật
- Lưu High Score
- Âm thanh
- Multiplayer

---

## 13. 📊 Success Criteria

- Game chạy ổn định
- Không lỗi logic
- Gameplay mượt
- Đáp ứng yêu cầu đồ án

---

## 14. 🏁 Kết luận

Dự án Snake Game là một ứng dụng đơn giản nhưng có tính thực tiễn cao, phù hợp để học lập trình và phát triển game 2D.

---
