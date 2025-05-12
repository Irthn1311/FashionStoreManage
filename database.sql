
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
('NV001', 'Nguyen Thi Lan', 'lan.nguyen@abc.com', '0912345678', '123 Mai Chí Thọ, TP.HCM', '1990-05-12', 'Nữ', '2020-01-15', 10000000.00, 'Quản lý', 'Đang làm', NULL),
('NV002', 'Tran Minh Tu', 'tu.tran@abc.com', '0912345679', '456 Le Lai, TP.HCM', '1985-03-22', 'Nam', '2019-08-01', 12000000.00, 'Nhân viên bán hàng', 'Đang làm', 'NV001'),
('NV003', 'Pham Thi Mai', 'mai.pham@abc.com', '0912345680', '789 Quang Trung, TP.HCM', '1992-11-08', 'Nữ', '2021-05-25', 9500000.00, 'Nhân viên kho', 'Đang làm', 'NV001'),
('NV004', 'Le Hoang Nam', 'nam.le@abc.com', '0912345681', '321 Nguyen Du, TP.HCM', '1988-06-14', 'Nam', '2022-02-20', 11000000.00, 'Nhân viên bán hàng', 'Đang làm', 'NV002'),
('NV005', 'Hoang Minh Tam', 'tam.hoang@abc.com', '0912345682', '654 Tran Hung Dao, TP.HCM', '1995-07-30', 'Nam', '2023-03-18', 13000000.00, 'Nhân viên kho', 'Đang làm', 'NV003'),
('NV006', 'Nguyen Thi Bich', 'bich.nguyen@abc.com', '0912345683', '987 Ham Nghi, TP.HCM', '1989-09-05', 'Nữ', '2021-06-10', 10500000.00, 'Quản lý kho', 'Đang làm', 'NV002'),
('NV007', 'Phan Minh Khoa', 'khoa.phan@abc.com', '0912345684', '654 Le Van Sy, TP.HCM', '1993-12-25', 'Nam', '2020-09-10', 11500000.00, 'Nhân viên bán hàng', 'Đang làm', 'NV001'),
('NV008', 'Le Thi Bao', 'bao.le@abc.com', '0912345685', '123 Cong Hoa, TP.HCM', '1991-02-17', 'Nữ', '2019-11-25', 9800000.00, 'Nhân viên kho', 'Đang làm', 'NV003'),
('NV009', 'Vu Thi Lan', 'lan.vu@abc.com', '0912345686', '246 Trieu Quang Phuc, TP.HCM', '1990-04-11', 'Nữ', '2022-07-05', 10000000.00, 'Nhân viên bán hàng', 'Đang làm', 'NV004'),
('NV010', 'Doan Minh Tuan', 'tuan.doan@abc.com', '0912345687', '258 Hai Ba Trung, TP.HCM', '1994-01-19', 'Nam', '2020-04-18', 10800000.00, 'Quản lý kho', 'Đang làm', 'NV001');


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

INSERT INTO TaiKhoan VALUES
('TK001', 'admin', 'admin123', N'Quản trị', 1, '2020-01-15', 'NV001'),
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
    MaKhachHang VARCHAR(100) PRIMARY KEY,
    HoTen NVARCHAR(100),
    GioiTinh NVARCHAR(10),
    SoDienThoai VARCHAR(20),
    Email VARCHAR(100),
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
    MaKhoHang VARCHAR(100) PRIMARY KEY,
    TenKho NVARCHAR(200),
    DiaChi NVARCHAR(200),
    SoLuong INT,
    MaQuanLy BIGINT,
);

-- Thêm dữ liệu mẫu cho bảng KhoHang
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
    MaNhaCungCap VARCHAR(100) PRIMARY KEY,
    TenNhaCungCap NVARCHAR(200),
    LoaiSP NVARCHAR(100),
    NamHopTac INT,
    DiaChi NVARCHAR(200),
    Email VARCHAR(100),
    SoDienThoai VARCHAR(20),
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
('SP0005', N'Áo khoác nam nữ', 'NCC004', N'Áo', N'Trắng', '42', 50, 750000, 'img_product\aokhoacnamnu.jpg', N'Còn hàng'),
('SP0006', N'Áo nữ tay dài', 'NCC001', N'Áo', N'Nâu', 'Free size', 40, 550000, 'img_product\aonutaydai.jpg', N'Còn hàng'),
('SP0007', N'Áo thun big size', 'NCC001', N'Áo', N'Đen', 'Free size', 200, 150000, 'img_product\aothunbigsize.jpg', N'Còn hàng'),
('SP0008', N'Áo thun cổ trắng', 'NCC001', N'Áo', N'Bạc', 'Free size', 30, 1200000, 'img_product\aothuncotrang.jpg', N'Còn hàng'),
('SP0009', N'Áo thun cotton', 'NCC001', N'Áo', N'Nâu', 'Free size', 100, 299000, 'img_product\aothuncotton.jpg', N'Còn hàng'),
('SP0010', N'Áo thun hình mèo', 'NCC001', N'Áo', N'Đen', 'S', 90, 199000, 'img_product\aothunhinhmeo.jpg', N'Còn hàng'),
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
INSERT INTO NhaCungCap_SanPham (MaNhaCungCap, MaSanPham) VALUES
('NCC007', 'SP0001'), -- Áo sơ mi nam trắng
('NCC001', 'SP0002'), -- Áo thun thế chữ
('NCC001', 'SP0003'), -- Áo form rộng
('NCC004', 'SP0004'), -- Áo khoác gió
('NCC004', 'SP0005'), -- Áo khoác nam nữ
('NCC001', 'SP0006'), -- Áo nữ tay dài
('NCC001', 'SP0007'), -- Áo thun big size
('NCC001', 'SP0008'), -- Áo thun cổ trắng
('NCC001', 'SP0009'), -- Áo thun cotton
('NCC001', 'SP0010'), -- Áo thun hình mèo
('NCC001', 'SP0011'), -- Áo thun Mikey
('NCC001', 'SP0012'), -- Áo thun thể thao
('NCC001', 'SP0013'), -- Áo thun phi hành gia
('NCC003', 'SP0014'), -- Giày Air
('NCC003', 'SP0015'); -- Giày Lucky


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
INSERT INTO HoaDon (
    MaHoaDon, MaSanPham, TenSanPham, KichCo, MauSac, SoLuong, MaKhachHang,
    ThanhTien, DonGia, HinhThucThanhToan, ThoiGian, TrangThai, TenKhachHang
) VALUES
('HD001', 'SP0011', N'Áo thun Mikey', N'L', N'Đen', 2, 'KH001', 398018.00, 199009.00, N'Tiền mặt', '2025-04-01 10:30:00', N'Hoàn thành', N'Lê Thị Lan'),
('HD002', 'SP0015', N'Giày Lucky', N'M', N'Trắng', 1, 'KH003', 259000.00, 259000.00, N'Tiền mặt', '2025-04-03 09:45:00', N'Đang xử lý', N'Trần Thị Huyền'),
('HD003', 'SP0005', N'Áo khoác nam nữ', N'42', N'Trắng', 1, 'KH001', 750000.00, 750000.00, N'Chuyển khoản', '2025-04-04 16:20:00', N'Hoàn thành', N'Lê Thị Lan'),
('HD004', 'SP0001', N'Áo sơ mi nam trắng', N'L', N'Trắng', 2, 'KH002', 598000.00, 299000.00, N'Tiền mặt', '2025-04-07 15:30:00', N'Chờ giao', N'Nguyễn Văn Mạnh'),
('HD005', 'SP0002', N'Áo thun thế chữ', N'M', N'Xanh', 2, 'KH009', 918000.00, 459000.00, N'Chuyển khoản', '2025-04-11 09:00:00', N'Hoàn thành', N'Tạ Thị Kim'),
('HD006', 'SP0006', N'Áo nữ tay dài', N'Free size', N'Nâu', 1, 'KH003', 550000.00, 550000.00, N'Tiền mặt', '2025-04-12 14:30:00', N'Chờ giao', N'Trần Thị Huyền'),
('HD007', 'SP0009', N'Áo thun cotton', N'Free size', N'Nâu', 3, 'KH010', 897000.00, 299000.00, N'Chuyển khoản', '2025-04-13 11:15:00', N'Đã hủy', N'Phan Văn Long'),
('HD008', 'SP0014', N'Giày Air', N'S', N'Hồng', 1, 'KH001', 299000.00, 299000.00, N'Tiền mặt', '2025-04-14 16:45:00', N'Đang xử lý', N'Lê Thị Lan'),
('HD009', 'SP0012', N'Áo thun thể thao', N'M', N'Xanh', 2, 'KH004', 798000.00, 399000.00, N'Chuyển khoản', '2025-04-15 10:20:00', N'Hoàn thành', N'Phạm Văn An');


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
INSERT INTO NhapHang (MaPN, MaNhaCungCap, MaSanPham, TenSanPham, MauSac, KichThuoc, SoLuong, DonGia, ThanhTien, HinhThucThanhToan, TrangThai)
VALUES
('PN001', 'NCC007', 'SP0001', N'Áo sơ mi nam trắng', N'Trắng', N'L', 50, 250000, 12500000, N'Chuyển khoản', N'Đang xử lý'),
('PN002', 'NCC007', 'SP0001', N'Áo sơ mi nam trắng', N'Trắng', N'M', 30, 250000, 7500000, N'Tiền mặt', N'Đang xử lý'),
('PN003', 'NCC007', 'SP0001', N'Áo sơ mi nam trắng', N'Trắng', N'S', 40, 250000, 10000000, N'Chuyển khoản', N'Đang xử lý'),
('PN004', 'NCC007', 'SP0001', N'Áo sơ mi nam trắng', N'Trắng', N'XL', 25, 250000, 6250000, N'Tiền mặt', N'Đang xử lý'),
('PN005', 'NCC007', 'SP0001', N'Áo sơ mi nam trắng', N'Trắng', N'XXL', 15, 250000, 3750000, N'Chuyển khoản', N'Đang xử lý'),
('PN006', 'NCC001', 'SP0002', N'Áo thun thế chữ', N'Xanh', N'L', 35, 400000, 14000000, N'Tiền mặt', N'Đang xử lý'),
('PN007', 'NCC001', 'SP0003', N'Áo form rộng', N'Đen', N'M', 45, 450000, 20250000, N'Chuyển khoản', N'Đang xử lý'),
('PN008', 'NCC004', 'SP0004', N'Áo khoác gió', N'Hồng', N'S', 20, 350000, 7000000, N'Tiền mặt', N'Đang xử lý'),
('PN009', 'NCC004', 'SP0005', N'Áo khoác nam nữ', N'Trắng', N'42', 10, 650000, 6500000, N'Chuyển khoản', N'Đang xử lý'),
('PN010', 'NCC001', 'SP0009', N'Áo thun cotton', N'Nâu', N'Free size', 25, 250000, 6250000, N'Tiền mặt', N'Đang xử lý');

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
INSERT INTO XuatHang (
    MaPX, MaKhachHang, HoTen, MaSanPham, TenSanPham, KichThuoc, MauSac,
    SoLuong, DonGia, ThanhTien, HinhThucThanhToan, TrangThai
) VALUES
('PX001', 'KH001', N'Lê Thị Lan', 'SP0001', N'Áo sơ mi nam trắng', N'M', N'Trắng', 2, 299000, 598000, N'Chuyển khoản', N'Hoàn thành'),
('PX002', 'KH002', N'Nguyễn Văn Mạnh', 'SP0001', N'Áo sơ mi nam trắng', N'L', N'Trắng', 1, 299000, 299000, N'Tiền mặt', N'Đã xuất'),
('PX003', 'KH003', N'Trần Thị Huyền', 'SP0002', N'Áo thun thế chữ', N'S', N'Xanh', 3, 459000, 1377000, N'Chuyển khoản', N'Đang xử lý'),
('PX004', 'KH004', N'Phạm Văn An', 'SP0003', N'Áo form rộng', N'XL', N'Đen', 2, 499000, 998000, N'Tiền mặt', N'Chờ xác nhận'),
('PX005', 'KH005', N'Đỗ Thị Thảo', 'SP0004', N'Áo khoác gió', N'M', N'Hồng', 4, 399000, 1596000, N'Chuyển khoản', N'Đang giao'),
('PX006', 'KH006', N'Ngô Văn Khải', 'SP0005', N'Áo khoác nam nữ', N'L', N'Trắng', 1, 750000, 750000, N'Tiền mặt', N'Hoàn thành'),
('PX007', 'KH007', N'Bùi Thị Hạnh', 'SP0006', N'Áo nữ tay dài', N'Free size', N'Nâu', 2, 550000, 1100000, N'Chuyển khoản', N'Hoàn thành'),
('PX008', 'KH008', N'Lý Văn Minh', 'SP0007', N'Áo thun big size', N'Free size', N'Đen', 1, 150000, 150000, N'Tiền mặt', N'Đã xuất'),
('PX009', 'KH009', N'Tạ Thị Kim', 'SP0011', N'Áo thun Mikey', N'L', N'Đen', 1, 199009, 199009, N'Chuyển khoản', N'Đang xử lý'),
('PX010', 'KH010', N'Phan Văn Long', 'SP0015', N'Giày Lucky', N'M', N'Trắng', 2, 259000, 518000, N'Tiền mặt', N'Đã giao');


CREATE TABLE KhuyenMai (
    MaKhuyenMai VARCHAR(50) PRIMARY KEY,
    MaSanPham VARCHAR(50) NOT NULL,
    TenSanPham VARCHAR(100) NOT NULL,
    TenChuongTrinh VARCHAR(100) NOT NULL,
    GiamGia DECIMAL(5, 2) NOT NULL,
    NgayBatDau DATE NOT NULL,
    NgayKetThuc DATE NOT NULL,
	GiaCu DECIMAL(10, 2) NOT NULL,
	GiaMoi DECIMAL(10, 2) NOT NULL,
    TrangThai VARCHAR(50) NOT NULL
);


INSERT INTO KhuyenMai (MaKhuyenMai, MaSanPham, TenSanPham, TenChuongTrinh, GiamGia, NgayBatDau, NgayKetThuc, GiaCu, GiaMoi, TrangThai)
VALUES
    ('KM001', 'SP0011', N'Áo thun Mikey', N'Quý 1 năm nay', 30.0, '2025-01-01', '2025-03-31', 199009.0, 139306.3, N'Hoạt động'),
    ('KM002', 'SP0003', N'Áo form rộng', N'Khuyến mãi đầu năm', 20.0, '2025-01-15', '2025-02-15', 499000.0, 399200.0, N'Hết hạn'),
    ('KM003', 'SP0014', N'Giày Air', N'Mùa lễ hội', 25.0, '2025-04-01', '2025-04-30', 299000.0, 224250.0, N'Chưa bắt đầu'),
    ('KM004', 'SP0007', N'Áo thun big size', N'Khuyến mãi hè', 15.0, '2025-05-01', '2025-06-30', 150000.0, 127500.0, N'Chưa bắt đầu'),
    ('KM005', 'SP0015', N'Giày Lucky', N'Khuyến mãi nhỏ', 10.0, '2025-02-01', '2025-02-28', 259000.0, 233100.0, N'Hết hạn');

-- Tạo bảng ThongKe
CREATE TABLE ThongKe (
    MaSanPham VARCHAR(100),
    TenSanPham NVARCHAR(200),
    SoSPBanRa INT,
    DoanhThu DECIMAL(15,2)
);




INSERT INTO ThongKe (MaSanPham, TenSanPham, SoSPBanRa, DoanhThu)
VALUES
('SP0001', N'Áo sơ mi nam trắng', 120, 35880000),
('SP0002', N'Áo thun thế chữ', 85, 39015000),
('SP0003', N'Áo form rộng', 60, 29940000),
('SP0004', N'Áo khoác gió', 40, 15960000),
('SP0011', N'Áo thun Mikey', 30, 5970270);


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
