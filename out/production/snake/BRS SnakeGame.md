# 🎮 BUSINESS REQUIREMENT SPECIFICATION (BRS)
## 📌 Dự án: Game Rắn Săn Mồi (Snake Game)

---

## 1. 📖 Giới thiệu

### 1.1 Mục đích tài liệu
Tài liệu này mô tả chi tiết các yêu cầu nghiệp vụ của hệ thống **Snake Game**, làm cơ sở cho việc:
- Phân tích hệ thống
- Thiết kế phần mềm
- Lập trình và kiểm thử

---

### 1.2 Phạm vi hệ thống
Hệ thống là một trò chơi giải trí đơn giản chạy trên máy tính, cho phép người chơi điều khiển một con rắn để ăn mồi và đạt điểm cao.

---

### 1.3 Định nghĩa thuật ngữ

| Thuật ngữ | Mô tả |
|----------|------|
| Snake | Nhân vật chính |
| Food | Mồi |
| Score | Điểm |
| Collision | Va chạm |
| Game Loop | Vòng lặp xử lý game |

---

## 2. 🎯 Mục tiêu nghiệp vụ

- Cung cấp trò chơi giải trí đơn giản
- Tăng khả năng phản xạ của người chơi
- Đạt điểm số cao nhất có thể

---

## 3. 👥 Stakeholders

| Stakeholder | Vai trò |
|------------|--------|
| Người chơi | Sử dụng hệ thống |
| Developer | Xây dựng hệ thống |
| Giảng viên | Đánh giá sản phẩm |

---

## 4. 📋 Business Requirements

### BR-01: Bắt đầu trò chơi
- Người chơi có thể bắt đầu game từ màn hình chính

### BR-02: Điều khiển rắn
- Người chơi điều khiển rắn bằng bàn phím

### BR-03: Ăn mồi
- Khi rắn ăn mồi:
  - Tăng điểm
  - Tăng chiều dài

### BR-04: Va chạm
- Khi xảy ra va chạm:
  - Game kết thúc

### BR-05: Hiển thị thông tin
- Hệ thống hiển thị:
  - Điểm số
  - Trạng thái game

### BR-06: Chơi lại
- Người chơi có thể restart game

---

## 5. ⚙️ Business Rules

| Rule ID | Mô tả |
|--------|------|
| R1 | Rắn không được đi ngược chiều ngay lập tức |
| R2 | Mồi không spawn trên thân rắn |
| R3 | Tốc độ game tăng theo điểm |
| R4 | Game kết thúc khi va chạm |

---

## 6. 🧩 Use Case List

| ID | Use Case | Mô tả |
|----|----------|------|
| UC-01 | Start Game | Bắt đầu game |
| UC-02 | Control Snake | Điều khiển rắn |
| UC-03 | Eat Food | Ăn mồi |
| UC-04 | Game Over | Kết thúc |
| UC-05 | Restart | Chơi lại |

---

## 7. 🖥️ Mô tả Use Case chi tiết

### UC-03: Eat Food

- **Actor:** Người chơi  
- **Pre-condition:** Game đang chạy  
- **Post-condition:** Điểm tăng, rắn dài hơn  

#### Luồng chính:
1. Rắn di chuyển
2. Rắn chạm mồi
3. Hệ thống tăng điểm
4. Tạo mồi mới

#### Luồng thay thế:
- Không có

---

## 8. 🏗️ Yêu cầu hệ thống

### 8.1 Functional Requirements

| ID | Yêu cầu |
|----|--------|
| FR-01 | Di chuyển rắn |
| FR-02 | Sinh mồi |
| FR-03 | Tính điểm |
| FR-04 | Phát hiện va chạm |
| FR-05 | Restart game |

---

### 8.2 Non-Functional Requirements

| ID | Yêu cầu | Mô tả |
|----|--------|------|
| NFR-01 | Hiệu năng | Game chạy mượt |
| NFR-02 | Usability | Dễ chơi |
| NFR-03 | Stability | Không crash |
| NFR-04 | Maintainability | Dễ mở rộng |

---

## 9. 🔄 Luồng nghiệp vụ (Business Flow)

1. Người chơi mở game
2. Nhấn Start
3. Điều khiển rắn
4. Ăn mồi → tăng điểm
5. Va chạm → Game Over
6. Restart

---

## 10. ⚠️ Giả định và ràng buộc

### Giả định:
- Người chơi biết sử dụng bàn phím
- Máy tính hỗ trợ Java

### Ràng buộc:
- Phát triển bằng Java
- Không sử dụng engine game phức tạp

---

## 11. 📊 Tiêu chí thành công

- Game chạy ổn định
- Không lỗi logic
- Gameplay mượt
- Người chơi có thể hoàn thành vòng chơi

---

## 12. 🏁 Kết luận

Tài liệu BRS mô tả đầy đủ yêu cầu nghiệp vụ của hệ thống Snake Game, là nền tảng cho các bước phân tích, thiết kế và phát triển tiếp theo.

---