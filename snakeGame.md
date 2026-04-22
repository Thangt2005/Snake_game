# 🎮 Business Requirement Document (BRD)
## Dự án: Game Rắn Săn Mồi (Snake Game)

---

## 📌 1. Giới thiệu

### 1.1 Mục tiêu
Xây dựng một trò chơi **Rắn săn mồi (Snake Game)** đơn giản nhằm mục đích giải trí và rèn luyện phản xạ cho người chơi.

### 1.2 Phạm vi
- Ứng dụng chạy trên desktop (Java Swing / JavaFX)
- Gameplay cơ bản: điều khiển rắn, ăn mồi, tăng điểm, tránh va chạm

---

## 🎯 2. Stakeholders

| Vai trò | Mô tả |
|--------|------|
| Người chơi | Sử dụng game để giải trí |
| Developer | Phát triển và bảo trì game |
| Giảng viên | Đánh giá đồ án |

---

## 🎮 3. Tổng quan hệ thống

Game là một ứng dụng desktop với các đặc điểm:
- Giao diện 2D
- Điều khiển bằng bàn phím
- Cơ chế game loop liên tục

---

## 📋 4. Business Requirements

### 4.1 Gameplay cơ bản
- Người chơi điều khiển rắn theo 4 hướng:
  - Lên
  - Xuống
  - Trái
  - Phải
- Rắn ăn mồi → tăng chiều dài
- Điểm số tăng theo số mồi ăn được

### 4.2 Luật chơi
- Game kết thúc khi:
  - Rắn đâm vào tường
  - Rắn tự cắn vào thân
- Cho phép chơi lại sau khi thua

### 4.3 Giao diện
- Hiển thị:
  - Điểm số
  - Trạng thái Game Over
  - Nút Start / Restart

---

## ⚙️ 5. Functional Requirements

### 5.1 Điều khiển
- W / ↑ : Di chuyển lên  
- S / ↓ : Di chuyển xuống  
- A / ← : Di chuyển trái  
- D / → : Di chuyển phải  

### 5.2 Hệ thống rắn
- Rắn gồm nhiều đoạn (segments)
- Di chuyển theo đầu rắn
- Thân rắn đi theo quỹ đạo của đầu

### 5.3 Hệ thống mồi
- Mồi xuất hiện ngẫu nhiên trên bản đồ
- Không xuất hiện trên thân rắn

### 5.4 Tính điểm
- +1 điểm mỗi lần ăn mồi
- Có thể tăng độ khó theo điểm

### 5.5 Game Loop
- Game chạy theo chu kỳ (tick)
- Tốc độ tăng dần theo thời gian hoặc điểm

---

## 🚫 6. Non-Functional Requirements

| Yêu cầu | Mô tả |
|--------|------|
| Hiệu năng | Game chạy mượt, không lag |
| Dễ sử dụng | Điều khiển đơn giản |
| Tương thích | Chạy trên Windows |
| Độ ổn định | Không bị crash |

---

## 🧩 7. Use Cases

### Use Case 1: Bắt đầu game
- Người chơi nhấn Start
- Game bắt đầu

### Use Case 2: Ăn mồi
- Rắn chạm mồi
- Tăng điểm và chiều dài

### Use Case 3: Game Over
- Va chạm xảy ra
- Hiển thị điểm
- Cho phép Restart

---

## 🖥️ 8. Giao diện (UI Overview)

### Màn hình chính
- Nút Start

### Màn hình chơi
- Bản đồ
- Rắn
- Mồi
- Điểm số

### Màn hình Game Over
- Hiển thị điểm
- Nút Restart

---

## 🔄 9. Future Enhancements

- Thêm level
- Thêm chướng ngại vật
- Lưu điểm cao (High Score)
- Âm thanh và hiệu ứng
- Multiplayer

---

## ⚠️ 10. Risks

| Rủi ro | Giải pháp |
|-------|----------|
| Lag khi rắn dài | Tối ưu thuật toán |
| Lỗi logic va chạm | Kiểm thử kỹ |
| Giao diện đơn giản | Cải tiến UI sau |

---

## 📊 11. Success Criteria

- Game hoạt động ổn định
- Gameplay mượt mà
- Không lỗi logic
- Đáp ứng yêu cầu đồ án

---

## 🏁 12. Kết luận

Dự án **Snake Game** là một ứng dụng đơn giản nhưng có tính thực tiễn cao, phù hợp cho việc học lập trình hướng đối tượng và phát triển game 2D.

---