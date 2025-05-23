# Fashion Store Manage
## Thành viên
1. Nguyễn Hữu Tri
2. Nguyễn Đình Sơn
3. Võ Thành Tài
4. Nguyễn Vĩnh Khang

## 📌 Giới thiệu

Fashion Store Manage là một hệ thống quản lý cửa hàng thời trang, giúp quản lý sản phẩm, kho hàng, đơn hàng và khách hàng một cách hiệu quả. Đây là một ứng dụng desktop được phát triển bằng Java Swing.

## 🚀 Tính năng chính

- Quản lý sản phẩm (quần, áo, giày, phụ kiện,...)
- Quản lý kho hàng
- Quản lý đơn hàng
- Quản lý mã khuyến mãi
- Quản lý nhân viên
- Quản lý khách hàng
- Quản lý nhà cung cấp
- Thống kê, báo cáo doanh thu

## 🛠️ Công nghệ sử dụng

- **Ngôn ngữ:** Java
- **Giao diện người dùng (UI):** Java Swing
- **Database:** SQL Server
- **IDE:** Apache NetBeans, Visual Studio Code
- **Công cụ Build:** Apache Ant (thông qua NetBeans)

## 📂 Cấu trúc thư mục (Sơ lược)

```
FashionStoreManage/
│-- src/
│   │-- BUS/         # Business Logic Layer
│   │-- components/  # UI Components (các thành phần giao diện tái sử dụng)
│   │-- DAO/         # Data Access Objects (truy cập dữ liệu)
│   │-- DTO/         # Data Transfer Objects (đối tượng truyền dữ liệu)
│   │-- DTB/         # Database connection/setup (quản lý kết nối cơ sở dữ liệu)
│   │-- icon_img/    # Icons and images for UI
│   │-- img_product/ # Product images
│   │-- main/        # Chứa lớp Main (điểm khởi chạy ứng dụng)
│   │-- model/       # Data models (các đối tượng dữ liệu chính)
│   │-- screens/     # Application screens/views (các màn hình chính của ứng dụng)
│   │-- utils/       # Utility classes (các lớp tiện ích)
│-- nbproject/     # NetBeans project files
│-- README.md
# (Các file cấu hình hoặc thư mục khác có thể có)
```

## 🔧 Cài đặt và chạy dự án

1.  **Clone repo:**
    ```sh
    git clone https://github.com/Irthn1311/FashionStoreManage.git
    ```
2.  **Mở dự án bằng Apache NetBeans IDE:**
    *   Mở NetBeans.
    *   Chọn `File > Open Project...`.
    *   Duyệt đến thư mục `FashionStoreManage` đã clone và chọn nó.
3.  **Cấu hình database:**
    *   Đảm bảo bạn đã cài đặt SQL Server.
    *   Cấu hình chuỗi kết nối và thông tin đăng nhập cho SQL Server trong mã nguồn (thường là trong các lớp DAO hoặc một file cấu hình riêng nếu có, ví dụ trong `src/DTB/`). Bạn cần xem qua mã nguồn để tìm vị trí chính xác.
    *   Tạo cơ sở dữ liệu và các bảng cần thiết. (Nếu có file script `.sql`, hãy chạy nó).
4.  **Chạy ứng dụng:**
    *   Trong NetBeans, nhấp chuột phải vào dự án `FashionStoreManage` trong cửa sổ "Projects".
    *   Chọn `Run` (hoặc nhấn phím F6). Thao tác này sẽ biên dịch và chạy lớp `main.Main`.

---

🚀 *Được phát triển bởi nhóm Irthn1311!*

