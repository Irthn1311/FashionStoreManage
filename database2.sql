-- Tạo bảng NhanVien
CREATE TABLE NhanVien (
    MaNhanVien VARCHAR(100) PRIMARY KEY,
    HoTen NVARCHAR(100),
    Email VARCHAR(100),
    SoDienThoai VARCHAR(15),
    DiaChi NVARCHAR(255),
    NgaySinh DATE,
    GioiTinh NVARCHAR(10),
    NgayVaoLam DATE,
    MucLuong DECIMAL(15, 2),
    ChucVu NVARCHAR(50),
    TrangThai NVARCHAR(50),
    MaQuanLy VARCHAR(100),
    FOREIGN KEY (MaQuanLy) REFERENCES NhanVien(MaNhanVien)
);

INSERT INTO NhanVien (MaNhanVien, HoTen, Email, SoDienThoai, DiaChi, NgaySinh, GioiTinh, NgayVaoLam, MucLuong, ChucVu, TrangThai, MaQuanLy)
VALUES
('NV001', N'Nguyễn Hữu Tri', 'nhuutri1311@gmail.com', '0582837353', N'123 Nguyễn Du, Q1, TP.HCM', '2005-01-12', N'Nam', '2020-01-10', 12000000.00, N'Quản lý', N'Đang làm', NULL),
('NV002', N'Nguyễn Đình Sơn', 'son.nguyen@shop.com', '0912345678', N'45 Lê Lợi, Q1, TP.HCM', '2005-07-20', N'Nam', '2020-03-15', 10000000.00, N'Nhân viên bán hàng', N'Đang làm', 'NV001'),
('NV003', N'Lê Minh Tâm', 'tam.le@shop.com', '0923456789', N'67 Hai Bà Trưng, Q3, TP.HCM', '1995-03-10', N'Nam', '2021-01-05', 9500000.00, N'Nhân viên kho', N'Đang làm', 'NV001'),
('NV004', N'Phạm Thị Mai', 'mai.pham@shop.com', '0934567890', N'89 Nguyễn Trãi, Q5, TP.HCM', '1991-11-25', N'Nữ', '2021-05-20', 9800000.00, N'Nhân viên bán hàng', N'Đang làm', 'NV001'),
('NV005', N'Võ Thành Tài', 'tai.vo@shop.com', '0945678901', N'12 Điện Biên Phủ, Bình Thạnh, TP.HCM', '1988-09-05', N'Nam', '2021-10-15', 11000000.00, N'Quản lý kho', N'Đang làm', 'NV001'),
('NV006', N'Đặng Thị Lan', 'lan.dang@shop.com', '0956789012', N'34 Lý Thường Kiệt, Q10, TP.HCM', '1993-04-18', N'Nữ', '2022-02-10', 9300000.00, N'Nhân viên bán hàng', N'Đang làm', 'NV001'),
('NV007', N'Ngô Văn Bình', 'binh.ngo@shop.com', '0967890123', N'56 Cách Mạng Tháng 8, Q3, TP.HCM', '1994-12-30', N'Nam', '2022-06-15', 9500000.00, N'Nhân viên kho', N'Đang làm', 'NV005'),
('NV008', N'Bùi Thị Hương', 'huong.bui@shop.com', '0978901234', N'78 Lê Văn Sỹ, Q3, TP.HCM', '1990-02-15', N'Nữ', '2022-09-01', 9700000.00, N'Nhân viên bán hàng', N'Đang làm', 'NV001'),
('NV009', N'Trịnh Văn Khải', 'khai.trinh@shop.com', '0989012345', N'90 Nguyễn Đình Chiểu, Q3, TP.HCM', '1992-08-10', N'Nam', '2023-01-10', 9400000.00, N'Nhân viên kho', N'Đang làm', 'NV005'),
('NV010', N'Nguyễn Vĩnh Khang', 'khang.nguyen@shop.com', '0990123456', N'23 Trần Quang Khải, Q1, TP.HCM', '2005-06-22', N'Nam', '2023-04-01', 10500000.00, N'Quản lý nhân viên', N'Đang làm', 'NV001');


CREATE TABLE TaiKhoan (
    MaTaiKhoan VARCHAR(100) PRIMARY KEY,
    TenDangNhap VARCHAR(50) UNIQUE,
    MatKhau VARCHAR(100),
    VaiTro NVARCHAR(50) CHECK (VaiTro IN (N'Quản trị', N'Quản lý kho', N'Quản lý nhân viên', N'Nhân viên')),
    TrangThai INT DEFAULT 1,
    NgayTao DATETIME DEFAULT GETDATE(),
    MaNhanVien VARCHAR(100) UNIQUE,
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien)
);

INSERT INTO TaiKhoan (MaTaiKhoan, TenDangNhap, MatKhau, VaiTro, TrangThai, NgayTao, MaNhanVien)
VALUES
('TK001', 'admin', 'admin123', N'Quản trị', 1, '2020-01-15', 'NV001'),
('TK002', 'dinhson', 'son123', N'Nhân viên', 1, '2020-03-20', 'NV002'),
('TK003', 'tamle', 'tam123', N'Nhân viên', 1, '2021-01-10', 'NV003'),
('TK004', 'maipham', 'mai123', N'Nhân viên', 1, '2021-05-25', 'NV004'),
('TK005', 'thanhtai', 'tai123', N'Quản lý kho', 1, '2021-10-20', 'NV005'),
('TK006', 'landang', 'lan123', N'Nhân viên', 1, '2022-02-15', 'NV006'),
('TK007', 'binhngo', 'binh123', N'Nhân viên', 1, '2022-06-20', 'NV007'),
('TK008', 'huongbui', 'huong123', N'Nhân viên', 1, '2022-09-05', 'NV008'),
('TK009', 'khaitrinh', 'khai123', N'Nhân viên', 1, '2023-01-15', 'NV009'),
('TK010', 'vinhkhang', 'khang123', N'Quản lý nhân viên', 1, '2023-04-05', 'NV010');


-- Tạo bảng KhachHang
CREATE TABLE KhachHang (
    MaKhachHang VARCHAR(100) PRIMARY KEY,
    HoTen NVARCHAR(100),
    GioiTinh NVARCHAR(10),
    SoDienThoai VARCHAR(20),
    Email VARCHAR(100),
    DiaChi NVARCHAR(200),
    NgaySinh DATE
);

INSERT INTO KhachHang (MaKhachHang, HoTen, GioiTinh, SoDienThoai, Email, DiaChi, NgaySinh)
VALUES
('KH001', N'Nguyễn Thị Ngọc', N'Nữ', '0901122334', 'ngoc.nguyen@email.com', N'123 Nguyễn Huệ, Q1, TP.HCM', '1995-06-15'),
('KH002', N'Trần Văn Hùng', N'Nam', '0912233445', 'hung.tran@email.com', N'456 Lê Lai, Q1, TP.HCM', '1990-08-20'),
('KH003', N'Lê Thị Hồng', N'Nữ', '0923344556', 'hong.le@email.com', N'789 Cách Mạng Tháng 8, Q3, TP.HCM', '1992-03-10'),
('KH004', N'Phạm Văn Đức', N'Nam', '0934455667', 'duc.pham@email.com', N'101 Lý Thường Kiệt, Q10, TP.HCM', '1988-11-25'),
('KH005', N'Hoàng Thị Lan', N'Nữ', '0945566778', 'lan.hoang@email.com', N'234 Nguyễn Trãi, Q5, TP.HCM', '1993-04-30'),
('KH006', N'Vũ Văn Minh', N'Nam', '0956677889', 'minh.vu@email.com', N'567 Trần Hưng Đạo, Q1, TP.HCM', '1991-12-05'),
('KH007', N'Đặng Thị Thu', N'Nữ', '0967788990', 'thu.dang@email.com', N'890 Lê Văn Sỹ, Q3, TP.HCM', '1994-09-15'),
('KH008', N'Ngô Văn Hải', N'Nam', '0978899001', 'hai.ngo@email.com', N'321 Lý Chính Thắng, Q3, TP.HCM', '1987-07-20'),
('KH009', N'Bùi Thị Thảo', N'Nữ', '0989900112', 'thao.bui@email.com', N'654 Điện Biên Phủ, Bình Thạnh, TP.HCM', '1996-02-10'),
('KH010', N'Trịnh Văn Long', N'Nam', '0990011223', 'long.trinh@email.com', N'987 Nguyễn Đình Chiểu, Q3, TP.HCM', '1989-10-15');



-- Tạo bảng KhoHang
CREATE TABLE KhoHang (
    MaKhoHang VARCHAR(100) PRIMARY KEY,
    TenKho NVARCHAR(200),
    DiaChi NVARCHAR(200),
    SoLuong INT,
    MaQuanLy BIGINT,
);
INSERT INTO KhoHang (MaKhoHang, TenKho, DiaChi, SoLuong, MaQuanLy)
VALUES
('KH001', N'Kho Quận 1', N'123 Nguyễn Huệ, Q1, TP.HCM', 500, 1001),
('KH002', N'Kho Quận 3', N'456 Lý Chính Thắng, Q3, TP.HCM', 450, 1002),
('KH003', N'Kho Quận 5', N'789 Trần Hưng Đạo, Q5, TP.HCM', 400, 1003),
('KH004', N'Kho Quận 7', N'101 Nguyễn Thị Thập, Q7, TP.HCM', 350, 1004),
('KH005', N'Kho Bình Thạnh', N'234 Điện Biên Phủ, Bình Thạnh, TP.HCM', 300, 1005),
('KH006', N'Kho Tân Bình', N'567 Cộng Hòa, Tân Bình, TP.HCM', 250, 1006),
('KH007', N'Kho Gò Vấp', N'890 Quang Trung, Gò Vấp, TP.HCM', 200, 1007),
('KH008', N'Kho Thủ Đức', N'321 Võ Văn Ngân, Thủ Đức, TP.HCM', 150, 1008),
('KH009', N'Kho Hóc Môn', N'654 Nguyễn Ảnh Thủ, Hóc Môn, TP.HCM', 100, 1009),
('KH010', N'Kho Bình Tân', N'987 Kinh Dương Vương, Bình Tân, TP.HCM', 50, 1010);


-- Tạo bảng NhaCungCap
CREATE TABLE NhaCungCap (
    MaNhaCungCap VARCHAR(100) PRIMARY KEY,
    TenNhaCungCap NVARCHAR(200),
    LoaiSP NVARCHAR(100),
    NamHopTac INT,
    DiaChi NVARCHAR(200),
    Email VARCHAR(100),
    SoDienThoai VARCHAR(20),
    TrangThai NVARCHAR(50)
);
INSERT INTO NhaCungCap (MaNhaCungCap, TenNhaCungCap, LoaiSP, NamHopTac, DiaChi, Email, SoDienThoai, TrangThai)
VALUES
('NCC001', N'Công ty Vải Việt', N'Áo thun', 2020, N'123 Lê Lợi, Q1, TP.HCM', 'contact@vaiviet.vn', '0901123456', N'Đang hợp tác'),
('NCC002', N'Công ty JeanPro', N'Quần jeans', 2018, N'45 Nguyễn Trãi, Q5, TP.HCM', 'info@jeanpro.vn', '0902233445', N'Đang hợp tác'),
('NCC003', N'Sneaker World', N'Giày thể thao', 2021, N'88 Hai Bà Trưng, Q1, TP.HCM', 'sneaker@sworld.com', '0911223344', N'Đang hợp tác'),
('NCC004', N'Hoodie House', N'Áo khoác', 2019, N'56 Trường Chinh, Q12, TP.HCM', 'support@hoodiehouse.vn', '0933445566', N'Đang hợp tác'),
('NCC005', N'Phụ Kiện Đỏ', N'Mũ thời trang', 2017, N'77 Lý Thường Kiệt, Q10, TP.HCM', 'pkd@phukiendo.com', '0909988776', N'Đang hợp tác'),
('NCC006', N'Sport Bag Co.', N'Balo', 2022, N'12 Hoàng Hoa Thám, Q.Tân Bình', 'sales@sportbag.com', '0905443322', N'Đang hợp tác'),
('NCC007', N'Sơ Mi Plus', N'Áo sơ mi', 2020, N'38 Đinh Tiên Hoàng, Q.Bình Thạnh', 'somiplus@gmail.com', '0907668899', N'Đang hợp tác'),
('NCC008', N'Vớ Việt', N'Vớ thời trang', 2016, N'95 Tô Hiến Thành, Q10', 'contact@voviet.vn', '0911227788', N'Đang hợp tác'),
('NCC009', N'Sunglasses Hub', N'Kính mát', 2021, N'10 Nguyễn Văn Cừ, Q5', 'support@sghub.vn', '0906778899', N'Đang hợp tác'),
('NCC010', N'Túi Xách Trẻ', N'Túi đeo chéo', 2018, N'66 Cách Mạng Tháng Tám, Q3', 'info@tuixachtre.vn', '0909988112', N'Đang hợp tác');


-- Tạo bảng SanPham
CREATE TABLE SanPham (
    MaSanPham VARCHAR(100) PRIMARY KEY,
    TenSanPham NVARCHAR(200),
	MaNhaCungCap VARCHAR(100),
    MaDanhMuc VARCHAR(100),
	MauSac NVARCHAR(100),
	Size NVARCHAR(50),
    SoLuongTonKho INT,
	GiaBan DECIMAL(15,2),
	ImgURL VARCHAR(500),
    TrangThai NVARCHAR(50),
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap)
);

INSERT INTO SanPham (MaSanPham, TenSanPham, MaNhaCungCap, MaDanhMuc, MauSac, Size, SoLuongTonKho, GiaBan, ImgURL, TrangThai)
VALUES
('SP0001', N'Áo sơ mi nam trắng', 'NCC007', N'Áo', N'Trắng', 'L', 120, 299000, 'img_product\aobongro.jpg', N'Còn hàng'),
('SP0002', N'Áo thun thế chữ', 'NCC001', N'Áo', N'Xanh', 'M', 80, 459000, 'img_product\anthuntheuchu.webp', N'Còn hàng'),
('SP0003', N'Áo form rộng', 'NCC001', N'Áo', N'Đen', 'S', 60, 499000, 'img_product\aoformrong.jpg', N'Còn hàng'),
('SP0004', N'Áo khoác gió', 'NCC004', N'Áo', N'Hồng', 'M', 75, 399000, 'img_product\aokhoacgio.jpg', N'Còn hàng'),
('SP0005', N'Áo khoác nam nữ', 'NCC004', N'Áo', N'Trắng', '42', 2, 750000, 'img_product\aokhoacnamnu.jpg', N'Còn hàng'),
('SP0006', N'Áo nữ tay dài', 'NCC001', N'Áo', N'Nâu', 'Free size', 40, 550000, 'img_product\aonutaydai.jpg', N'Còn hàng'),
('SP0007', N'Áo thun big size', 'NCC001', N'Áo', N'Đen', 'Free size', 200, 150000, 'img_product\aothunbigsize.jpg', N'Còn hàng'),
('SP0008', N'Áo thun cổ trắng', 'NCC001', N'Áo', N'Bạc', 'Free size', 10, 1200000, 'img_product\aothuncotrang.jpg', N'Còn hàng'),
('SP0009', N'Áo thun cotton', 'NCC001', N'Áo', N'Nâu', 'Free size', 100, 299000, 'img_product\aothuncotton.jpg', N'Còn hàng'),
('SP0010', N'Áo thun hình mèo', 'NCC001', N'Áo', N'Đen', 'S', 5, 199000, 'img_product\aothunhinhmeo.jpg', N'Còn hàng'),
('SP0011', N'Áo thun Mikey', 'NCC001', N'Áo', N'Đen', 'L', 50, 199009, 'img_product\aothunmikey.jpg', N'Còn hàng'),
('SP0012', N'Áo thun thể thao', 'NCC001', N'Áo', N'Xanh', 'M', 30, 399000, 'img_product\aothunthethao.jpg', N'Còn hàng'),
('SP0013', N'Áo thun phi hành gia', 'NCC001', N'Áo', N'Đỏ', 'XL', 20, 499000, 'img_product\aothunphihanhgia.jpg', N'Hết hàng'),
('SP0014', N'Giày Air', 'NCC003', N'Giày', N'Hồng', 'S', 40, 299000, 'img_product\giayair.jpg', N'Còn hàng'),
('SP0015', N'Giày Lucky', 'NCC003', N'Giày', N'Trắng', 'M', 60, 259000, 'img_product\giaylucky.jpg', N'Còn hàng');







CREATE TABLE NhaCungCap_SanPham (
    MaNhaCungCap VARCHAR(100),
    MaSanPham VARCHAR(100),
    PRIMARY KEY (MaNhaCungCap, MaSanPham),
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham)
);
INSERT INTO NhaCungCap_SanPham (MaNhaCungCap, MaSanPham)
VALUES
('NCC007', 'SP0001'),
('NCC001', 'SP0002'),
('NCC001', 'SP0003'),
('NCC004', 'SP0004'),
('NCC004', 'SP0005'),
('NCC001', 'SP0006'),
('NCC001', 'SP0007'),
('NCC001', 'SP0008'),
('NCC001', 'SP0009'),
('NCC001', 'SP0010');


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
INSERT INTO HoaDon (MaHoaDon, MaSanPham, TenSanPham, KichCo, MauSac, SoLuong, MaKhachHang, ThanhTien, DonGia, HinhThucThanhToan, ThoiGian, TrangThai, TenKhachHang)
VALUES
('HD001', 'SP0001', N'Áo sơ mi nam trắng', 'L', N'Trắng', 2, 'KH001', 598000.00, 299000.00, N'Tiền mặt', '2024-08-15 10:30:00', N'Hoàn thành', N'Nguyễn Thị Ngọc'),
('HD002', 'SP0002', N'Áo thun thế chữ', 'M', N'Xanh', 1, 'KH002', 459000.00, 459000.00, N'Chuyển khoản', '2024-08-16 14:45:00', N'Hoàn thành', N'Trần Văn Hùng'),
('HD003', 'SP0003', N'Áo form rộng', 'S', N'Đen', 3, 'KH003', 1497000.00, 499000.00, N'Tiền mặt', '2024-08-17 09:20:00', N'Hoàn thành', N'Lê Thị Hồng'),
('HD004', 'SP0004', N'Áo khoác gió', 'M', N'Hồng', 1, 'KH004', 399000.00, 399000.00, N'Chuyển khoản', '2024-08-18 16:10:00', N'Hoàn thành', N'Phạm Văn Đức'),
('HD005', 'SP0005', N'Áo khoác nam nữ', '42', N'Trắng', 2, 'KH005', 1500000.00, 750000.00, N'Tiền mặt', '2024-08-19 11:30:00', N'Hoàn thành', N'Hoàng Thị Lan'),
('HD006', 'SP0006', N'Áo nữ tay dài', 'Free size', N'Nâu', 1, 'KH006', 550000.00, 550000.00, N'Chuyển khoản', '2024-08-20 13:45:00', N'Đang xử lý', N'Vũ Văn Minh'),
('HD007', 'SP0007', N'Áo thun big size', 'Free size', N'Đen', 2, 'KH007', 300000.00, 150000.00, N'Tiền mặt', '2024-08-21 15:20:00', N'Đang xử lý', N'Đặng Thị Thu'),
('HD008', 'SP0008', N'Áo thun cổ trắng', 'Free size', N'Bạc', 1, 'KH008', 1200000.00, 1200000.00, N'Chuyển khoản', '2024-08-22 10:10:00', N'Chờ giao', N'Ngô Văn Hải'),
('HD009', 'SP0009', N'Áo thun cotton', 'Free size', N'Nâu', 3, 'KH009', 897000.00, 299000.00, N'Tiền mặt', '2024-08-23 14:30:00', N'Chờ giao', N'Bùi Thị Thảo'),
('HD010', 'SP0010', N'Áo thun hình mèo', 'S', N'Đen', 2, 'KH010', 398000.00, 199000.00, N'Chuyển khoản', '2024-08-24 16:45:00', N'Hoàn thành', N'Trịnh Văn Long');


-- Tạo bảng NhapHang
CREATE TABLE NhapHang (
    MaPN VARCHAR(100) PRIMARY KEY,
    MaNhaCungCap VARCHAR(100),
    LoaiSP NVARCHAR(100),
    MaSanPham VARCHAR(100),
    TenSanPham NVARCHAR(200),
    MauSac NVARCHAR(50),
    KichThuoc NVARCHAR(50),
    SoLuong INT,
    DonGia DECIMAL(15,2),
    ThanhTien DECIMAL(15,2),
    ThoiGian TIMESTAMP,
	HinhThucThanhToan NVARCHAR(100),
    TrangThai NVARCHAR(50),
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham)
);


-- Tạo bảng XuatHang
CREATE TABLE XuatHang (
    MaPX VARCHAR(100) PRIMARY KEY,
    MaKhachHang VARCHAR(100),
    HoTen NVARCHAR(100),
    MaSanPham VARCHAR(100),
    TenSanPham NVARCHAR(200),
    KichThuoc NVARCHAR(50),
    MauSac NVARCHAR(50),
    SoLuong INT,
    DonGia DECIMAL(15,2),
    ThanhTien DECIMAL(15,2),
	HinhThucThanhToan NVARCHAR(100),
    TrangThai NVARCHAR(50),
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKhachHang),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham)
);



CREATE TABLE KhuyenMai (
    MaKhuyenMai VARCHAR(50) PRIMARY KEY,
    MaSanPham VARCHAR(50) NOT NULL,
    TenSanPham NVARCHAR(100) NOT NULL,
    TenChuongTrinh NVARCHAR(100) NOT NULL,
    GiamGia DECIMAL(5, 2) NOT NULL,
    NgayBatDau DATE NOT NULL,
    NgayKetThuc DATE NOT NULL,
	GiaCu DECIMAL(10, 2) NOT NULL,
	GiaMoi DECIMAL(10, 2) NOT NULL,
    TrangThai VARCHAR(50) NOT NULL
);


INSERT INTO KhuyenMai (MaKhuyenMai, MaSanPham, TenSanPham, TenChuongTrinh, GiamGia, NgayBatDau, NgayKetThuc, GiaCu, GiaMoi, TrangThai)
VALUES
('KM001', 'SP0001', N'Áo sơ mi nam trắng', N'Khuyến mãi đầu tháng 5', 20.0, '2025-05-01', '2025-05-10', 299000.0, 239200.0, N'Hết hạn'),
('KM002', 'SP0002', N'Áo thun thế chữ', N'Khuyến mãi giữa tháng', 15.0, '2025-05-10', '2025-05-20', 459000.0, 390150.0, N'Hoạt động'),
('KM003', 'SP0003', N'Áo form rộng', N'Mừng ngày 14/5', 25.0, '2025-05-14', '2025-05-16', 499000.0, 374250.0, N'Chưa bắt đầu'),
('KM004', 'SP0004', N'Áo khoác gió', N'Sale cuối tuần', 30.0, '2025-05-12', '2025-05-15', 399000.0, 279300.0, N'Hoạt động'),
('KM005', 'SP0005', N'Áo khoác nam nữ', N'Flash Sale', 10.0, '2025-05-14', '2025-05-14', 750000.0, 675000.0, N'Chưa bắt đầu'),
('KM006', 'SP0006', N'Áo nữ tay dài', N'Khuyến mãi 24h', 40.0, '2025-05-13', '2025-05-13', 550000.0, 330000.0, N'Hoạt động'),
('KM007', 'SP0007', N'Áo thun big size', N'Sale cuối tháng', 50.0, '2025-05-25', '2025-05-31', 150000.0, 75000.0, N'Chưa bắt đầu'),
('KM008', 'SP0008', N'Áo thun cổ trắng', N'Khuyến mãi nửa đầu tháng 5', 5.0, '2025-05-01', '2025-05-15', 1200000.0, 1140000.0, N'Hoạt động'),
('KM009', 'SP0009', N'Áo thun cotton', N'Sale tháng 4', 20.0, '2025-04-15', '2025-04-30', 299000.0, 239200.0, N'Hết hạn'),
('KM010', 'SP0010', N'Áo thun hình mèo', N'Khuyến mãi nửa cuối tháng 5', 25.0, '2025-05-15', '2025-05-31', 199000.0, 149250.0, N'Chưa bắt đầu');

-- Tạo bảng ThongKe
CREATE TABLE ThongKe (
    MaSanPham VARCHAR(100),
    TenSanPham NVARCHAR(200),
    SoSPBanRa INT,
    DoanhThu DECIMAL(15,2)
);

INSERT INTO ThongKe (MaSanPham, TenSanPham, SoSPBanRa, DoanhThu)
VALUES
('SP0001', N'Áo sơ mi nam trắng', 85, 25415000),
('SP0002', N'Áo thun thế chữ', 63, 28917000),
('SP0003', N'Áo form rộng', 92, 45908000),
('SP0004', N'Áo khoác gió', 47, 18753000),
('SP0005', N'Áo khoác nam nữ', 31, 23250000),
('SP0006', N'Áo nữ tay dài', 54, 29700000),
('SP0007', N'Áo thun big size', 78, 11700000),
('SP0008', N'Áo thun cổ trắng', 25, 30000000),
('SP0009', N'Áo thun cotton', 45, 13455000),
('SP0010', N'Áo thun hình mèo', 67, 13333000);




CREATE TABLE PhieuNhap (
    MaPhieuNhap VARCHAR(100) PRIMARY KEY,
    MaNhaCungCap VARCHAR(100),
    MaSanPham VARCHAR(100),
    TenSanPham NVARCHAR(200),
    SoLuong INT,
    ThoiGian DATETIME DEFAULT GETDATE(),
    DonGia DECIMAL(15,2),
    TrangThai NVARCHAR(50),
    HinhThucThanhToan NVARCHAR(50),
    ThanhTien DECIMAL(15,2),
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham)
);

INSERT INTO PhieuNhap (MaPhieuNhap, MaNhaCungCap, MaSanPham, TenSanPham, SoLuong, ThoiGian, DonGia, TrangThai, HinhThucThanhToan, ThanhTien)
VALUES
('PN001', 'NCC007', 'SP0001', N'Áo sơ mi nam trắng', 50, '2023-05-10 09:30:00', 250000, N'Hoàn thành', N'Chuyển khoản', 12500000),
('PN002', 'NCC001', 'SP0002', N'Áo thun thế chữ', 30, '2023-05-12 10:45:00', 350000, N'Hoàn thành', N'Tiền mặt', 10500000),
('PN003', 'NCC001', 'SP0003', N'Áo form rộng', 40, '2023-05-15 14:20:00', 400000, N'Hoàn thành', N'Chuyển khoản', 16000000),
('PN004', 'NCC004', 'SP0004', N'Áo khoác gió', 35, '2023-05-18 11:30:00', 300000, N'Hoàn thành', N'Tiền mặt', 10500000),
('PN005', 'NCC004', 'SP0005', N'Áo khoác nam nữ', 25, '2023-05-20 15:45:00', 600000, N'Hoàn thành', N'Chuyển khoản', 15000000),
('PN006', 'NCC001', 'SP0006', N'Áo nữ tay dài', 20, '2023-06-01 09:15:00', 450000, N'Đang xử lý', N'Tiền mặt', 9000000),
('PN007', 'NCC001', 'SP0007', N'Áo thun big size', 40, '2023-06-05 10:30:00', 120000, N'Đang xử lý', N'Chuyển khoản', 4800000),
('PN008', 'NCC001', 'SP0008', N'Áo thun cổ trắng', 15, '2023-06-10 14:45:00', 950000, N'Đang xử lý', N'Tiền mặt', 14250000),
('PN009', 'NCC001', 'SP0009', N'Áo thun cotton', 30, '2023-06-15 16:20:00', 250000, N'Đang xử lý', N'Chuyển khoản', 7500000),
('PN010', 'NCC001', 'SP0010', N'Áo thun hình mèo', 25, '2023-06-20 11:10:00', 150000, N'Đang xử lý', N'Tiền mặt', 3750000);

