-- Tạo bảng NhanVien
CREATE TABLE NhanVien (
    MaNhanVien NVARCHAR(100) PRIMARY KEY,
    MaNhanVien NVARCHAR(100) PRIMARY KEY,
    HoTen NVARCHAR(100),
    Email NVARCHAR(100),
    SoDienThoai NVARCHAR(15),
    Email NVARCHAR(100),
    SoDienThoai NVARCHAR(15),
    DiaChi NVARCHAR(255),
    NgaySinh DATE,
    GioiTinh NVARCHAR(10),
    NgayVaoLam DATE,
    MucLuong DECIMAL(15, 2),
    ChucVu NVARCHAR(50),
    TrangThai NVARCHAR(50),
    MaQuanLy NVARCHAR(100),
    MaQuanLy NVARCHAR(100),
    FOREIGN KEY (MaQuanLy) REFERENCES NhanVien(MaNhanVien)
);

INSERT INTO NhanVien (MaNhanVien, HoTen, Email, SoDienThoai, DiaChi, NgaySinh, GioiTinh, NgayVaoLam, MucLuong, ChucVu, TrangThai, MaQuanLy)
VALUES
('NV001', N'Nguyen Thi Lan', 'lan.nguyen@abc.com', '0912345678', N'123 Mai Chí Thọ, TP.HCM', '1990-05-12', N'Nữ', '2020-01-15', 10000000.00, N'Quản lý', N'Đang làm', NULL),
('NV002', N'Tran Minh Tu', 'tu.tran@abc.com', '0912345679', N'456 Le Lai, TP.HCM', '1985-03-22', N'Nam', '2019-08-01', 12000000.00, N'Nhân viên bán hàng', N'Đang làm', 'NV001'),
('NV003', N'Pham Thi Mai', 'mai.pham@abc.com', '0912345680', N'789 Quang Trung, TP.HCM', '1992-11-08', N'Nữ', '2021-05-25', 9500000.00, N'Nhân viên kho', N'Đang làm', 'NV001'),
('NV004', N'Le Hoang Nam', 'nam.le@abc.com', '0912345681', N'321 Nguyen Du, TP.HCM', '1988-06-14', N'Nam', '2022-02-20', 11000000.00, N'Nhân viên bán hàng', N'Đang làm', 'NV002'),
('NV005', N'Hoang Minh Tam', 'tam.hoang@abc.com', '0912345682', N'654 Tran Hung Dao, TP.HCM', '1995-07-30', N'Nam', '2023-03-18', 13000000.00, N'Nhân viên kho', N'Đang làm', 'NV003'),
('NV006', N'Nguyen Thi Bich', 'bich.nguyen@abc.com', '0912345683', N'987 Ham Nghi, TP.HCM', '1989-09-05', N'Nữ', '2021-06-10', 10500000.00, N'Quản lý kho', N'Đang làm', 'NV002'),
('NV007', N'Phan Minh Khoa', 'khoa.phan@abc.com', '0912345684', N'654 Le Van Sy, TP.HCM', '1993-12-25', N'Nam', '2020-09-10', ceding11500000.00, N'Nhân viên bán hàng', N'Đang làm', 'NV001'),
('NV008', N'Le Thi Bao', 'bao.le@abc.com', '0912345685', N'123 Cong Hoa, TP.HCM', '1991-02-17', N'Nữ', '2019-11-25', 9800000.00, N'Nhân viên kho', N'Đang làm', 'NV003'),
('NV009', N'Vu Thi Lan', 'lan.vu@abc.com', '0912345686', N'246 Trieu Quang Phuc, TP.HCM', '1990-04-11', N'Nữ', '2022-07-05', 10000000.00, N'Nhân viên bán hàng', N'Đang làm', 'NV004'),
('NV010', N'Doan Minh Tuan', 'tuan.doan@abc.com', '0912345687', N'258 Hai Ba Trung, TP.HCM', '1994-01-19', N'Nam', '2020-04-18', 10800000.00, N'Quản lý kho', N'Đang làm', 'NV001');

-- Tạo bảng TaiKhoan
-- Tạo bảng TaiKhoan
CREATE TABLE TaiKhoan (
    MaTaiKhoan NVARCHAR(100) PRIMARY KEY,
    TenDangNhap NVARCHAR(50) UNIQUE,
    MatKhau NVARCHAR(100),
    MaTaiKhoan NVARCHAR(100) PRIMARY KEY,
    TenDangNhap NVARCHAR(50) UNIQUE,
    MatKhau NVARCHAR(100),
    VaiTro NVARCHAR(50) CHECK (VaiTro IN (N'Quản trị', N'Quản lý kho', N'Quản lý nhân viên', N'Nhân viên')),
    TrangThai INT DEFAULT 1,
    NgayTao DATETIME DEFAULT GETDATE(),
    MaNhanVien NVARCHAR(100) UNIQUE,
    MaNhanVien NVARCHAR(100) UNIQUE,
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien)
);

INSERT INTO TaiKhoan VALUES
('TK001', 'admin', 'admin123', N'Quản trị', 1, '2025-01-01', 'NV001'),
('TK002', 'nhanvien1', 'nv123', N'Nhân viên', 1, '2025-01-01', 'NV002'),
('TK003', 'nv_c', 'pass123', N'Nhân viên', 0, '2025-01-01', 'NV003'),
('TK004', 'user_d', '123456', N'Nhân viên', 1, '2025-01-01', 'NV004'),
('TK005', 'nv_e', 'matkhau', N'Nhân viên', 1, '2025-01-01', 'NV005'),
('TK006', 'nguyenf', '12345', N'Quản lý kho', 1, '2025-01-01', 'NV006'),
('TK007', 'vog', 'abc123', N'Nhân viên', 0, '2025-01-01', 'NV007'),
('TK008', 'lyh', 'xyz789', N'Nhân viên', 1, '2025-01-01', 'NV008'),
('TK009', 'phani', '000111', N'Nhân viên', 1, '2025-01-01', 'NV009'),
('TK010', 'buij', '987654', N'Quản lý nhân viên', 1, '2025-01-01', 'NV010');

-- Tạo bảng KhachHang
CREATE TABLE KhachHang (
    MaKhachHang NVARCHAR(100) PRIMARY KEY,
    MaKhachHang NVARCHAR(100) PRIMARY KEY,
    HoTen NVARCHAR(100),
    GioiTinh NVARCHAR(10),
    SoDienThoai NVARCHAR(20),
    Email NVARCHAR(100),
    SoDienThoai NVARCHAR(20),
    Email NVARCHAR(100),
    DiaChi NVARCHAR(200),
    NgaySinh DATE
);

INSERT INTO KhachHang (MaKhachHang, HoTen, GioiTinh, SoDienThoai, Email, DiaChi, NgaySinh) VALUES
('KH001', N'Lê Thị Lan', N'Nữ', '0938123456', 'lan.le@example.com', N'Hà Nội', '1992-04-10'),
('KH002', N'Nguyễn Văn Mạnh', N'Nam', '0938234567', 'manh.nguyen@example.com', N'Hồ Chí Minh', '1988-09-15'),
('KH003', N'Trần Thị Huyền', N'Nữ', '0938345678', 'huyen.tran@example.com', N'Bình Dương', '1995-12-01'),
('KH004', N'Phạm Văn An', N'Nam', '0938456789', 'an.pham@example.com', N'Hà Nam', '1990-06-20'),
('KH005', N'Đỗ Thị Thảo', N'Nữ', '0938567890', 'thao.do@example.com', N'Ninh Thuận', '1993-03-25'),
('KH006', N'Ngô Văn Khải', N'Nam', '0938678901', 'khai.ngo@example.com', N'Long An', '1989-11-30'),
('KH007', N'Bùi Thị Hạnh', N'Nữ', '0938789012', 'hanh.bui@example.com', N'Cần Thơ', '1996-01-18'),
('KH008', N'Lý Văn Minh', N'Nam', '0938890123', 'minh.ly@example.com', N'Huế', '1991-07-09'),
('KH009', N'Tạ Thị Kim', N'Nữ', '0938901234', 'kim.ta@example.com', N'Quảng Trị', '1994-10-05'),
('KH010', N'Phan Văn Long', N'Nam', '0938012345', 'long.phan@example.com', N'Bắc Ninh', '1990-08-22');

-- Tạo bảng KhoHang
CREATE TABLE KhoHang (
    MaKhoHang NVARCHAR(100) PRIMARY KEY,
    MaKhoHang NVARCHAR(100) PRIMARY KEY,
    TenKho NVARCHAR(200),
    DiaChi NVARCHAR(200),
    SoLuong INT,
    MaQuanLy BIGINT
    MaQuanLy BIGINT
);

INSERT INTO KhoHang (MaKhoHang, TenKho, DiaChi, SoLuong, MaQuanLy) VALUES
('K001', N'Kho Miền Bắc', N'Hà Nội', 500, 1001),
('K002', N'Kho Miền Nam', N'TP Hồ Chí Minh', 300, 1002),
('K003', N'Kho Miền Trung', N'Đà Nẵng', 250, 1003),
('K004', N'Kho Đông Bắc', N'Hải Phòng', 200, 1004),
('K005', N'Kho Tây Nam Bộ', N'Cần Thơ', 220, 1005),
('K006', N'Kho Đông Nam Bộ', N'Bình Dương', 280, 1006),
('K007', N'Kho Bắc Bộ', N'Quảng Ninh', 150, 1007),
('K008', N'Kho Đông Nam', N'Vũng Tàu', 180, 1008),
('K009', N'Kho Miền Đông', N'Đồng Nai', 210, 1009),
('K010', N'Kho Nam Trung Bộ', N'Nha Trang', 190, 1010);

-- Tạo bảng NhaCungCap
CREATE TABLE NhaCungCap (
    MaNhaCungCap NVARCHAR(100) PRIMARY KEY,
    MaNhaCungCap NVARCHAR(100) PRIMARY KEY,
    TenNhaCungCap NVARCHAR(200),
    LoaiSP NVARCHAR(100),
    NamHopTac INT,
    DiaChi NVARCHAR(200),
    Email NVARCHAR(100),
    SoDienThoai NVARCHAR(20),
    Email NVARCHAR(100),
    SoDienThoai NVARCHAR(20),
    TrangThai NVARCHAR(50)
);

INSERT INTO NhaCungCap (MaNhaCungCap, TenNhaCungCap, LoaiSP, NamHopTac, DiaChi, Email, SoDienThoai, TrangThai) VALUES
('NCC001', N'Công ty Vải Việt', N'Áo thun', 2020, N'123 Lê Lợi, Q1, TP.HCM', 'contact@vaiviet.vn', '0901123456', N'Đang hợp tác'),
('NCC002', N'Công ty JeanPro', N'Quần jeans', 2018, N'45 Nguyễn Trãi, Q5, TP.HCM', 'info@jeanpro.vn', '0902233445', N'Đang hợp tác'),
('NCC003', N'Sneaker World', N'Giày thể thao', 2021, N'88 Hai Bà Trưng, Q1, TP.HCM', 'sneaker@sworld.com', '0911223344', N'Đang hợp tác'),
('NCC004', N'Hoodie House', N'Áo khoác', 2019, N'56 Trường Chinh, Q12, TP.HCM', 'support@hoodiehouse.vn', '0933445566', N'Ngưng hợp tác'),
('NCC005', N'Phụ Kiện Đỏ', N'Mũ thời trang', 2017, N'77 Lý Thường Kiệt, Q10, TP.HCM', 'pkd@phukiendo.com', '0909988776', N'Đang hợp tác'),
('NCC006', N'Sport Bag Co.', N'Balo', 2022, N'12 Hoàng Hoa Thám, Q.Tân Bình', 'sales@sportbag.com', '0905443322', N'Đang hợp tác'),
('NCC007', N'Sơ Mi Plus', N'Áo sơ mi', 2020, N'38 Đinh Tiên Hoàng, Q.Bình Thạnh', 'somiplus@gmail.com', '0907668899', N'Đang hợp tác'),
('NCC008', N'Vớ Việt', N'Vớ thời trang', 2016, N'95 Tô Hiến Thành, Q10', 'contact@voviet.vn', '0911227788', N'Đang hợp tác'),
('NCC009', N'Sunglasses Hub', N'Kính mát', 2021, N'10 Nguyễn Văn Cừ, Q5', 'support@sghub.vn', '0906778899', N'Đang hợp tác'),
('NCC010', N'Túi Xách Trẻ', N'Túi đeo chéo', 2018, N'66 Cách Mạng Tháng Tám, Q3', 'info@tuixachtre.vn', '0909988112', N'Ngưng hợp tác');

-- Tạo bảng SanPham
CREATE TABLE SanPham (
    MaSanPham NVARCHAR(100) PRIMARY KEY,
    MaSanPham NVARCHAR(100) PRIMARY KEY,
    TenSanPham NVARCHAR(200),
    MaNhaCungCap NVARCHAR(100),
    MaDanhMuc NVARCHAR(100),
    MauSac NVARCHAR(100),
    Size NVARCHAR(50),
    MaNhaCungCap NVARCHAR(100),
    MaDanhMuc NVARCHAR(100),
    MauSac NVARCHAR(100),
    Size NVARCHAR(50),
    SoLuongTonKho INT,
    GiaBan DECIMAL(15,2),
    ImgURL NVARCHAR(500),
    GiaBan DECIMAL(15,2),
    ImgURL NVARCHAR(500),
    TrangThai NVARCHAR(50),
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap)
);

INSERT INTO SanPham (MaSanPham, TenSanPham, MaNhaCungCap, MaDanhMuc, MauSac, Size, SoLuongTonKho, GiaBan, ImgURL, TrangThai)
VALUES
('SP001', N'Áo sơ mi nam trắng', 'NCC001', N'Áo', N'Trắng', 'L', 120, 299000, 'img_product\aobongro.jpg', N'Còn hàng'),
('SP002', N'Quần Sort Nam', 'NCC002', N'Quần', N'Xanh', 'M', 80, 459000, 'img_product\quanshortnam.jpg', N'Còn hàng'),
('SP003', N'Áo form rộng', 'NCC003', N'Áo', N'Đen', 'S', 60, 499000, 'img_product\aoformrong.jpg', N'Còn hàng'),
('SP004', N'Áo khoác gió', 'NCC004', N'Áo', N'Hồng', 'M', 75, 399000, 'img_product\aokhoacgio.jpg', N'Còn hàng'),
('SP005', N'Áo khoác nam nữ', 'NCC005', N'Áo', N'Trắng', '42', 50, 750000, 'img_product\aokhoacnamnu.jpg', N'Còn hàng'),
('SP006', N'Áo nữ tay dài', 'NCC006', N'Áo', N'Nâu', 'Free size', 40, 550000, 'img_product\aonutaydai.jpg', N'Còn hàng'),
('SP007', N'Áo thun big size', 'NCC007', N'Áo', N'Đen', 'Free size', 200, 150000, 'img_product\aothunbigsize.jpg', N'Còn hàng'),
('SP008', N'Áo thun cổ trắng', 'NCC008', N'Áo', N'Bạc', 'Free size', 30, 1200000, 'img_product\aothuncotrang.jpg', N'Còn hàng'),
('SP009', N'Áo thun cotton', 'NCC009', N'Áo', N'Nâu', 'Free size', 100, 299000, 'img_product\aothuncotton.jpg', N'Còn hàng'),
('SP010', N'Áo thun hình mèo', 'NCC010', N'Áo', N'Đen', 'S', 90, 199000, 'img_product\aothunhinhmeo.jpg', N'Còn hàng'),
('SP011', N'Áo thun Mikey', 'NCC001', N'Áo', N'Đen', 'L', 50, 199009, 'img_product\aothunmikey.jpg', N'Còn hàng'),
('SP012', N'Áo thun thể thao', 'NCC002', N'Áo', N'Xanh', 'M', 30, 399000, 'img_product\aothunthethao.jpg', N'Còn hàng'),
('SP013', N'Áo thun phi hành gia', 'NCC003', N'Áo', N'Đỏ', 'XL', 20, 499000, 'img_product\aothunphihanhgia.jpg', N'Hết hàng'),
('SP014', N'Giày Air', 'NCC004', N'Giày', N'Hồng', 'S', 40, 299000, 'img_product\giayair.jpg', N'Còn hàng'),
('SP015', N'Giày Lucky', 'NCC001', N'Giày', N'Trắng', 'M', 60, 259000, 'img_product\giaylucky.jpg', N'Còn hàng');
('SP001', N'Áo sơ mi nam trắng', 'NCC001', N'Áo', N'Trắng', 'L', 120, 299000, 'img_product\aobongro.jpg', N'Còn hàng'),
('SP002', N'Áo thun thế chữ', 'NCC002', N'Áo', N'Xanh', 'M', 80, 459000, 'img_product\anthuntheuchu.webp', N'Còn hàng'),
('SP003', N'Áo form rộng', 'NCC003', N'Áo', N'Đen', 'S', 60, 499000, 'img_product\aoformrong.jpg', N'Còn hàng'),
('SP004', N'Áo khoác gió', 'NCC004', N'Áo', N'Hồng', 'M', 75, 399000, 'img_product\aokhoacgio.jpg', N'Còn hàng'),
('SP005', N'Áo khoác nam nữ', 'NCC005', N'Áo', N'Trắng', '42', 50, 750000, 'img_product\aokhoacnamnu.jpg', N'Còn hàng'),
('SP006', N'Áo nữ tay dài', 'NCC006', N'Áo', N'Nâu', 'Free size', 40, 550000, 'img_product\aonutaydai.jpg', N'Còn hàng'),
('SP007', N'Áo thun big size', 'NCC007', N'Áo', N'Đen', 'Free size', 200, 150000, 'img_product\aothunbigsize.jpg', N'Còn hàng'),
('SP008', N'Áo thun cổ trắng', 'NCC008', N'Áo', N'Bạc', 'Free size', 30, 1200000, 'img_product\aothuncotrang.jpg', N'Còn hàng'),
('SP009', N'Áo thun cotton', 'NCC009', N'Áo', N'Nâu', 'Free size', 100, 299000, 'img_product\aothuncotton.jpg', N'Còn hàng'),
('SP010', N'Áo thun hình mèo', 'NCC010', N'Áo', N'Đen', 'S', 90, 199000, 'img_product\aothunhinhmeo.jpg', N'Còn hàng'),
('SP011', N'Áo thun Mikey', 'NCC001', N'Áo', N'Đen', 'L', 50, 199009, 'img_product\aothunmikey.jpg', N'Còn hàng'),
('SP012', N'Áo thun thể thao', 'NCC002', N'Áo', N'Xanh', 'M', 30, 399000, 'img_product\aothunthethao.jpg', N'Còn hàng'),
('SP013', N'Áo thun phi hành gia', 'NCC003', N'Áo', N'Đỏ', 'XL', 20, 499000, 'img_product\aothunphihanhgia.jpg', N'Hết hàng'),
('SP014', N'Giày Air', 'NCC004', N'Giày', N'Hồng', 'S', 40, 299000, 'img_product\giayair.jpg', N'Còn hàng'),
('SP015', N'Giày Lucky', 'NCC001', N'Giày', N'Trắng', 'M', 60, 259000, 'img_product\giaylucky.jpg', N'Còn hàng');

-- Tạo bảng NhaCungCap_SanPham
-- Tạo bảng NhaCungCap_SanPham
CREATE TABLE NhaCungCap_SanPham (
    MaNhaCungCap NVARCHAR(100),
    MaSanPham NVARCHAR(100),
    MaNhaCungCap NVARCHAR(100),
    MaSanPham NVARCHAR(100),
    PRIMARY KEY (MaNhaCungCap, MaSanPham),
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham)
);


INSERT INTO NhaCungCap_SanPham (MaNhaCungCap, MaSanPham) VALUES
('NCC001', 'SP001'),
('NCC002', 'SP002'),
('NCC003', 'SP005'),
('NCC004', 'SP003'),
('NCC005', 'SP007'),
('NCC006', 'SP006'),
('NCC007', 'SP001'),
('NCC008', 'SP008'),
('NCC009', 'SP009'),
('NCC010', 'SP010');
('NCC001', 'SP001'),
('NCC002', 'SP002'),
('NCC003', 'SP005'),
('NCC004', 'SP003'),
('NCC005', 'SP007'),
('NCC006', 'SP006'),
('NCC007', 'SP001'),
('NCC008', 'SP008'),
('NCC009', 'SP009'),
('NCC010', 'SP010');

-- Tạo bảng HoaDon (đã chỉnh sửa theo yêu cầu)
CREATE TABLE HoaDon (
    MaHoaDon VARCHAR(100) PRIMARY KEY,
    MaSanPham VARCHAR(100),
    TenSanPham NVARCHAR(200),
    KichCo NVARCHAR(50),
    MauSac NVARCHAR(50),
    SoLuong INT,
    MaKhachHang VARCHAR(100),
    ThanhTien DECIMAL(15,2),
    DonGia DECIMAL(15,2),
    HinhThucThanhToan NVARCHAR(100),
    ThoiGian DATETIME DEFAULT GETDATE(),
    TrangThai NVARCHAR(50),
    TenKhachHang NVARCHAR(200),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham),
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKhachHang)
);



-- Thêm dữ liệu mẫu cho bảng HoaDon 
INSERT INTO HoaDon (
    MaHoaDon, MaSanPham, TenSanPham, KichCo, MauSac, SoLuong, MaKhachHang,
    ThanhTien, DonGia, HinhThucThanhToan, ThoiGian, TrangThai, TenKhachHang
) VALUES
('HD001', 'SP001', N'Áo Thun Nam', N'L', N'Đen', 2, 'KH001', 500000.00, 250000.00, N'Tiền mặt', '04/01/2025 10:30:00', N'Hoàn thành', N'Lê Thị Lan'),
('HD002', 'SP002', N'Quần Jeans', N'M', N'Xanh', 1, 'KH002', 400000.00, 400000.00, N'Chuyển khoản', '04/02/2025 14:15:00', N'Chờ giao', N'Nguyễn Văn Mạnh'),
('HD003', 'SP003', N'Giày Sneaker', N'42', N'Trắng', 1, 'KH003', 1200000.00, 1200000.00, N'Tiền mặt', '04/03/2025 09:45:00', N'Đang xử lý', N'Trần Thị Huyền'),
('HD004', 'SP004', N'Áo Khoác Hoodie', N'XL', N'Xám', 1, 'KH001', 650000.00, 650000.00, N'Chuyển khoản', '04/04/2025 16:20:00', N'Hoàn thành', N'Lê Thị Lan'),
('HD005', 'SP005', N'Mũ Lưỡi Trai', N'Free Size', N'Đỏ', 3, 'KH004', 450000.00, 150000.00, N'Tiền mặt', '04/05/2025 11:10:00', N'Đã hủy', N'Phạm Văn An'),
('HD006', 'SP006', N'Balo Thể Thao', N'M', N'Đen', 1, 'KH005', 700000.00, 700000.00, N'Chuyển khoản', '04/06/2025 13:50:00', N'Hoàn thành', N'Đỗ Thị Thảo'),
('HD007', 'SP007', N'Áo Sơ Mi Nam', N'L', N'Trắng', 2, 'KH002', 600000.00, 300000.00, N'Tiền mặt', '04/07/2025 15:30:00', N'Chờ giao', N'Nguyễn Văn Mạnh'),
('HD008', 'SP008', N'Vớ Nam', N'Free Size', N'Xám', 5, 'KH006', 250000.00, 50000.00, N'Chuyển khoản', '04/08/2025 08:40:00', N'Hoàn thành', N'Ngô Văn Khải'),
('HD009', 'SP009', N'Kính Mát', N'M', N'Đen', 1, 'KH007', 950000.00, 950000.00, N'Tiền mặt', '04/09/2025 17:25:00', N'Đang xử lý', N'Bùi Thị Hạnh'),
('HD010', 'SP010', N'Túi Đeo Chéo', N'S', N'Nâu', 1, 'KH008', 850000.00, 850000.00, N'Chuyển khoản', '04/10/2025 12:00:00', N'Hoàn thành', N'Lý Văn Minh');
-- Tạo bảng NhapHang
CREATE TABLE NhapHang (
    MaPN NVARCHAR(100) PRIMARY KEY,
    MaNhaCungCap NVARCHAR(100),
    MaSanPham NVARCHAR(100),
    MaPN NVARCHAR(100) PRIMARY KEY,
    MaNhaCungCap NVARCHAR(100),
    MaSanPham NVARCHAR(100),
    TenSanPham NVARCHAR(200),
    MauSac NVARCHAR(50),
    KichThuoc NVARCHAR(50),
    SoLuong INT,
    DonGia DECIMAL(15,2),
    ThanhTien DECIMAL(15,2),
    HinhThucThanhToan NVARCHAR(100),
    HinhThucThanhToan NVARCHAR(100),
    ThoiGian DATETIME DEFAULT GETDATE(),
    TrangThai NVARCHAR(50),
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham)
);

-- Thêm dữ liệu mẫu cho bảng NhapHang
INSERT INTO NhapHang (MaPN, MaNhaCungCap, MaSanPham, TenSanPham, MauSac, KichThuoc, SoLuong, DonGia, ThanhTien, HinhThucThanhToan, TrangThai)
VALUES
('PN001', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Trắng', N'L', 50, 250000, 12500000, N'Chuyển khoản', N'Đã nhập'),
('PN002', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Trắng', N'M', 30, 250000, 7500000, N'Tiền mặt', N'Đã nhập'),
('PN003', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Trắng', N'S', 40, 250000, 10000000, N'Chuyển khoản', N'Đã nhập'),
('PN004', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Trắng', N'XL', 25, 250000, 6250000, N'Tiền mặt', N'Chưa nhập'),
('PN005', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Trắng', N'XXL', 15, 250000, 3750000, N'Chuyển khoản', N'Chưa nhập'),
('PN006', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Xanh nhạt', N'L', 35, 260000, 9100000, N'Tiền mặt', N'Đã nhập'),
('PN007', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Xanh nhạt', N'M', 45, 260000, 11700000, N'Chuyển khoản', N'Đã nhập'),
('PN008', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Xanh nhạt', N'S', 20, 260000, 5200000, N'Tiền mặt', N'Chưa nhập'),
('PN009', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Xanh nhạt', N'XL', 10, 260000, 2600000, N'Chuyển khoản', N'Chưa nhập'),
('PN010', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Xanh nhạt', N'XXL', 5, 260000, 1300000, N'Tiền mặt', N'Đã nhập');

-- Tස

-- Thêm dữ liệu mẫu cho bảng NhapHang
INSERT INTO NhapHang (MaPN, MaNhaCungCap, MaSanPham, TenSanPham, MauSac, KichThuoc, SoLuong, DonGia, ThanhTien, HinhThucThanhToan, TrangThai)
VALUES
('PN001', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Trắng', N'L', 50, 250000, 12500000, N'Chuyển khoản', N'Đã nhập'),
('PN002', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Trắng', N'M', 30, 250000, 7500000, N'Tiền mặt', N'Đã nhập'),
('PN003', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Trắng', N'S', 40, 250000, 10000000, N'Chuyển khoản', N'Đã nhập'),
('PN004', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Trắng', N'XL', 25, 250000, 6250000, N'Tiền mặt', N'Chưa nhập'),
('PN005', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Trắng', N'XXL', 15, 250000, 3750000, N'Chuyển khoản', N'Chưa nhập'),
('PN006', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Xanh nhạt', N'L', 35, 260000, 9100000, N'Tiền mặt', N'Đã nhập'),
('PN007', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Xanh nhạt', N'M', 45, 260000, 11700000, N'Chuyển khoản', N'Đã nhập'),
('PN008', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Xanh nhạt', N'S', 20, 260000, 5200000, N'Tiền mặt', N'Chưa nhập'),
('PN009', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Xanh nhạt', N'XL', 10, 260000, 2600000, N'Chuyển khoản', N'Chưa nhập'),
('PN010', 'NCC001', 'SP001', N'Áo sơ mi nam trắng', N'Xanh nhạt', N'XXL', 5, 260000, 1300000, N'Tiền mặt', N'Đã nhập');

-- Tස

-- Tạo bảng XuatHang
CREATE TABLE XuatHang (
    MaPX NVARCHAR(100) PRIMARY KEY,
    MaKhachHang NVARCHAR(100),
    MaPX NVARCHAR(100) PRIMARY KEY,
    MaKhachHang NVARCHAR(100),
    HoTen NVARCHAR(100),
    MaSanPham NVARCHAR(100),
    MaSanPham NVARCHAR(100),
    TenSanPham NVARCHAR(200),
    KichThuoc NVARCHAR(50),
    MauSac NVARCHAR(50),
    SoLuong INT,
    DonGia DECIMAL(15,2),
    ThanhTien DECIMAL(15,2),
    HinhThucThanhToan NVARCHAR(100),
    ThoiGian DATETIME DEFAULT GETDATE(),
    HinhThucThanhToan NVARCHAR(100),
    ThoiGian DATETIME DEFAULT GETDATE(),
    TrangThai NVARCHAR(50),
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKhachHang),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham)
);

-- Thêm dữ liệu mẫu cho bảng XuatHang
INSERT INTO XuatHang (
    MaPX, MaKhachHang, HoTen, MaSanPham, TenSanPham, KichThuoc, MauSac,
    SoLuong, DonGia, ThanhTien, HinhThucThanhToan, TrangThai
) VALUES
('PX001', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'M', N'Trắng', 2, 300000, 600000, N'Chuyển khoản', N'Hoàn thành'),
('PX002', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'L', N'Trắng', 1, 300000, 300000, N'Tiền mặt', N'Đã xuất'),
('PX003', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'S', N'Trắng', 3, 300000, 900000, N'Chuyển khoản', N'Đang xử lý'),
('PX004', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'XL', N'Trắng', 2, 300000, 600000, N'Tiền mặt', N'Chờ xác nhận'),
('PX005', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'M', N'Trắng', 4, 300000, 1200000, N'Chuyển khoản', N'Đang giao'),
('PX006', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'L', N'Trắng', 1, 300000, 300000, N'Tiền mặt', N'Hoàn thành'),
('PX007', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'L', N'Trắng', 2, 300000, 600000, N'Chuyển khoản', N'Hoàn thành'),
('PX008', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'M', N'Trắng', 1, 300000, 300000, N'Tiền mặt', N'Đã xuất'),
('PX009', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'L', N'Trắng', 1, 300000, 300000, N'Chuyển khoản', N'Đang xử lý'),
('PX010', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'M', N'Trắng', 2, 300000, 600000, N'Tiền mặt', N'Đã giao');

-- Tạo bảng KhuyenMai
-- Thêm dữ liệu mẫu cho bảng XuatHang
INSERT INTO XuatHang (
    MaPX, MaKhachHang, HoTen, MaSanPham, TenSanPham, KichThuoc, MauSac,
    SoLuong, DonGia, ThanhTien, HinhThucThanhToan, TrangThai
) VALUES
('PX001', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'M', N'Trắng', 2, 300000, 600000, N'Chuyển khoản', N'Hoàn thành'),
('PX002', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'L', N'Trắng', 1, 300000, 300000, N'Tiền mặt', N'Đã xuất'),
('PX003', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'S', N'Trắng', 3, 300000, 900000, N'Chuyển khoản', N'Đang xử lý'),
('PX004', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'XL', N'Trắng', 2, 300000, 600000, N'Tiền mặt', N'Chờ xác nhận'),
('PX005', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'M', N'Trắng', 4, 300000, 1200000, N'Chuyển khoản', N'Đang giao'),
('PX006', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'L', N'Trắng', 1, 300000, 300000, N'Tiền mặt', N'Hoàn thành'),
('PX007', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'L', N'Trắng', 2, 300000, 600000, N'Chuyển khoản', N'Hoàn thành'),
('PX008', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'M', N'Trắng', 1, 300000, 300000, N'Tiền mặt', N'Đã xuất'),
('PX009', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'L', N'Trắng', 1, 300000, 300000, N'Chuyển khoản', N'Đang xử lý'),
('PX010', 'KH001', N'Nguyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'M', N'Trắng', 2, 300000, 600000, N'Tiền mặt', N'Đã giao');

-- Tạo bảng KhuyenMai
CREATE TABLE KhuyenMai (
    MaKhuyenMai NVARCHAR(50) PRIMARY KEY,
    MaSanPham NVARCHAR(50) NOT NULL,
    TenSanPham NVARCHAR(100) NOT NULL,
    TenChuongTrinh NVARCHAR(100) NOT NULL,
    MaKhuyenMai NVARCHAR(50) PRIMARY KEY,
    MaSanPham NVARCHAR(50) NOT NULL,
    TenSanPham NVARCHAR(100) NOT NULL,
    TenChuongTrinh NVARCHAR(100) NOT NULL,
    GiamGia DECIMAL(5, 2) NOT NULL,
    NgayBatDau DATE NOT NULL,
    NgayKetThuc DATE NOT NULL,
    GiaCu DECIMAL(10, 2) NOT NULL,
    GiaMoi DECIMAL(10, 2) NOT NULL,
    TrangThai NVARCHAR(50) NOT NULL
    GiaCu DECIMAL(10, 2) NOT NULL,
    GiaMoi DECIMAL(10, 2) NOT NULL,
    TrangThai NVARCHAR(50) NOT NULL
);

INSERT INTO KhuyenMai (MaKhuyenMai, MaSanPham, TenSanPham, TenChuongTrinh, GiamGia, NgayBatDau, NgayKetThuc, GiaCu, GiaMoi, TrangThai)
VALUES
('KM001', 'SP011', N'Áo thun nam', N'Quý 1 năm nay', 30.0, '2025-01-01', '2025-03-31', 150000.0, 105000.0, N'Hoạt động'),
('KM002', 'SP012', N'Quần jeans nữ', N'Khuyến mãi đầu năm', 20.0, '2025-01-15', '2025-02-15', 300000.0, 240000.0, N'Hết hạn'),
('KM003', 'SP013', N'Giày thể thao', N'Mùa lễ hội', 25.0, '2025-04-01', '2025-04-30', 500000.0, 375000.0, N'Chưa bắt đầu'),
('KM004', 'SP014', N'Balo du lịch', N'Khuyến mãi hè', 15.0, '2025-05-01', '2025-06-30', 400000.0, 340000.0, N'Chưa bắt đầu'),
('KM005', 'SP015', N'Mũ lưỡi trai', N'Khuyến mãi nhỏ', 10.0, '2025-02-01', '2025-02-28', 100000.0, 90000.0, N'Hết hạn');
('KM001', 'SP011', N'Áo thun nam', N'Quý 1 năm nay', 30.0, '2025-01-01', '2025-03-31', 150000.0, 105000.0, N'Hoạt động'),
('KM002', 'SP012', N'Quần jeans nữ', N'Khuyến mãi đầu năm', 20.0, '2025-01-15', '2025-02-15', 300000.0, 240000.0, N'Hết hạn'),
('KM003', 'SP013', N'Giày thể thao', N'Mùa lễ hội', 25.0, '2025-04-01', '2025-04-30', 500000.0, 375000.0, N'Chưa bắt đầu'),
('KM004', 'SP014', N'Balo du lịch', N'Khuyến mãi hè', 15.0, '2025-05-01', '2025-06-30', 400000.0, 340000.0, N'Chưa bắt đầu'),
('KM005', 'SP015', N'Mũ lưỡi trai', N'Khuyến mãi nhỏ', 10.0, '2025-02-01', '2025-02-28', 100000.0, 90000.0, N'Hết hạn');

-- Tạo bảng ThongKe
CREATE TABLE ThongKe (
    MaSanPham NVARCHAR(100),
    MaSanPham NVARCHAR(100),
    TenSanPham NVARCHAR(200),
    SoSPBanRa INT,
    DoanhThu DECIMAL(15,2)
);

INSERT INTO ThongKe (MaSanPham, TenSanPham, SoSPBanRa, DoanhThu)
VALUES
('SP001', N'Áo sơ mi nam trắng', 120, 30000000),
('SP001', N'Áo sơ mi nam trắng', 85, 21250000),
('SP001', N'Áo sơ mi nam trắng', 60, 15000000),
('SP001', N'Áo sơ mi nam trắng', 40, 10000000),
('SP001', N'Áo sơ mi nam trắng', 30, 7500000);

-- Tạo bảng PhieuNhap
-- Tạo bảng PhieuNhap
CREATE TABLE PhieuNhap (
    MaPhieuNhap INT PRIMARY KEY IDENTITY(1,1),
    NgayNhap DATETIME DEFAULT GETDATE(),
    MaSanPham NVARCHAR(100),
    MaSanPham NVARCHAR(100),
    SoLuongNhap INT,
    MaNhaCungCap NVARCHAR(100),
    MaNhanVien NVARCHAR(100),
    MaNhaCungCap NVARCHAR(100),
    MaNhanVien NVARCHAR(100),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham),
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap),
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien)
);

ALTER TABLE NhapHang DROP COLUMN ThoiGian;
ALTER TABLE NhapHang ADD ThoiGian DATETIME DEFAULT GETDATE();

ALTER TABLE XuatHang DROP COLUMN ThoiGian;
ALTER TABLE XuatHang ADD ThoiGian DATETIME DEFAULT GETDATE();
