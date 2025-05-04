
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
	MauSac VARCHAR(100),
	Size NVARCHAR(50),
    SoLuongTonKho INT,
	GiaBan DECIMAL(15,2),
	ImgURL VARCHAR(500),
    TrangThai NVARCHAR(50),
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap)
);




INSERT INTO SanPham (MaSanPham, TenSanPham, MaNhaCungCap, MaDanhMuc, MauSac, Size, SoLuongTonKho, GiaBan, ImgURL, TrangThai)
VALUES
-- Dữ liệu từ danh sách 1 (bổ sung MauSac mặc định là 'Khác')
('SP001', N'Áo sơ mi nam trắng', 'NCC001', 'DM001', N'Trắng', 'L', 120, 299000, 'https://example.com/images/aosomi.jpg', N'Còn hàng'),
('SP002', N'Quần jean nam xanh', 'NCC002', 'DM002', N'Xanh', 'M', 80, 459000, 'https://example.com/images/quanjean.jpg', N'Còn hàng'),
('SP003', N'Áo khoác dù nữ', 'NCC003', 'DM003', N'Đen', 'S', 60, 499000, 'https://example.com/images/aokhoac.jpg', N'Còn hàng'),
('SP004', N'Váy maxi nữ', 'NCC004', 'DM004', N'Hồng', 'M', 75, 399000, 'https://example.com/images/vaymaxi.jpg', N'Còn hàng'),
('SP005', N'Giày thể thao nam', 'NCC005', 'DM005', N'Trắng', '42', 50, 750000, 'https://example.com/images/giaythethao.jpg', N'Còn hàng'),
('SP006', N'Túi xách nữ thời trang', 'NCC006', 'DM006', N'Nâu', 'Free size', 40, 550000, 'https://example.com/images/tuixach.jpg', N'Còn hàng'),
('SP007', N'Mũ lưỡi trai', 'NCC007', 'DM007', N'Đen', 'Free size', 200, 150000, 'https://example.com/images/muluoitrai.jpg', N'Còn hàng'),
('SP008', N'Đồng hồ đeo tay', 'NCC008', 'DM008', N'Bạc', 'Free size', 30, 1200000, 'https://example.com/images/dongho.jpg', N'Còn hàng'),
('SP009', N'Thắt lưng da nam', 'NCC009', 'DM009', N'Nâu', 'Free size', 100, 299000, 'https://example.com/images/thatlung.jpg', N'Còn hàng'),
('SP010', N'Áo phông nữ', 'NCC010', 'DM010', N'Đen', 'S', 90, 199000, 'https://example.com/images/aophong.jpg', N'Còn hàng'),
('SP011', N'Áo Thun Nam', 'NCC001', 'DM001', N'Đen', 'L', 50, 199009, 'https://example.com/images/aothun1.jpg', N'Còn hàng'),
('SP012', N'Quần Jeans Nữ', 'NCC002', 'DM002', N'Xanh', 'M', 30, 399000, 'https://example.com/images/jeansnu.jpg', N'Còn hàng'),
('SP013', N'Áo Khoác Hoodie', 'NCC003', 'DM003', N'Đỏ', 'XL', 20, 499000, 'images/hoodie.jpg', N'Hết hàng'),
('SP014', N'Váy Công Sở', 'NCC004', 'DM004', N'Hồng', 'S', 40, 299000, 'images/vay.jpg', N'Còn hàng'),
('SP015', N'Áo Sơ Mi Nam', 'NCC001', 'DM001', N'Trắng', 'M', 60, 259000, 'images/somi.jpg', N'Còn hàng');





CREATE TABLE NhaCungCap_SanPham (
    MaNhaCungCap VARCHAR(100),
    MaSanPham VARCHAR(100),
    PRIMARY KEY (MaNhaCungCap, MaSanPham),
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham)
);
INSERT INTO NhaCungCap_SanPham (MaNhaCungCap, MaSanPham) VALUES
('NCC001', 'SP001'), -- Công ty Vải Việt cung cấp Áo sơ mi
('NCC002', 'SP002'), -- JeanPro cung cấp Quần jean
('NCC003', 'SP005'), -- Sneaker World cung cấp Giày thể thao
('NCC004', 'SP003'), -- Hoodie House cung cấp Áo khoác
('NCC005', 'SP007'), -- Phụ Kiện Đỏ cung cấp Mũ
('NCC006', 'SP006'), -- Sport Bag Co. cung cấp Túi xách
('NCC007', 'SP001'), -- Sơ Mi Plus cũng cung cấp Áo sơ mi
('NCC008', 'SP008'), -- Vớ Việt cung cấp Đồng hồ (giả định)
('NCC009', 'SP009'), -- Sunglasses Hub cung cấp Thắt lưng
('NCC010', 'SP010'); -- Túi Xách Trẻ cung cấp Áo phông


-- Tạo bảng HoaDon
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
    GioXuatHang VARCHAR(50),
    ThoiGian TIMESTAMP,
    TrangThai NVARCHAR(50),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham),
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKhachHang)
);

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
    LoaiSP NVARCHAR(100),
    KichThuoc NVARCHAR(50),
    MauSac NVARCHAR(50),
    SoLuong INT,
    ThoiGian TIMESTAMP,
    DonGia DECIMAL(15,2),
    ThanhTien DECIMAL(15,2),
    TrangThai NVARCHAR(50),
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKhachHang),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham)
);


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
    ('KM001', 'SP011', 'Áo thun nam', 'Quý 1 năm nay', 30.0, '2025-01-01', '2025-03-31', 150000.0, 105000.0, 'Hoạt động'),
    ('KM002', 'SP012', 'Quần jeans nữ', 'Khuyến mãi đầu năm', 20.0, '2025-01-15', '2025-02-15', 300000.0, 240000.0, 'Hết hạn'),
    ('KM003', 'SP013', 'Giày thể thao', 'Mùa lễ hội', 25.0, '2025-04-01', '2025-04-30', 500000.0, 375000.0, 'Chưa bắt đầu'),
    ('KM004', 'SP014', 'Balo du lịch', 'Khuyến mãi hè', 15.0, '2025-05-01', '2025-06-30', 400000.0, 340000.0, 'Chưa bắt đầu'),
    ('KM005', 'SP015', 'Mũ lưỡi trai', 'Khuyến mãi nhỏ', 10.0, '2025-02-01', '2025-02-28', 100000.0, 90000.0, 'Hết hạn');

-- Tạo bảng ThongKe
CREATE TABLE ThongKe (
    MaSanPham VARCHAR(100),
    TenSanPham NVARCHAR(200),
    SoSPBanRa INT,
    DoanhThu DECIMAL(15,2)
);


-- Thêm dữ liệu mẫu cho bảng HoaDon
INSERT INTO HoaDon (
    MaHoaDon, MaSanPham, TenSanPham, KichCo, MauSac, SoLuong, MaKhachHang,
    ThanhTien, DonGia, HinhThucThanhToan, GioXuatHang, TrangThai
) VALUES
('HD001', 'SP001', N'Áo Thun Nam', N'L', N'Đen', 2, 'KH001', 500000, 250000, N'Tiền mặt', '14:00', N'Hoàn thành'),
('HD002', 'SP002', N'Quần Jeans', N'M', N'Xanh', 1, 'KH002', 400000, 400000, N'Chuyển khoản', '10:30',  N'Chờ giao'),
('HD003', 'SP003', N'Giày Sneaker', N'42', N'Trắng', 1, 'KH003', 1200000, 1200000, N'Tiền mặt', '15:45', N'Đang xử lý'),
('HD004', 'SP004', N'Áo Khoác Hoodie', N'XL', N'Xám', 1, 'KH001', 650000, 650000, N'Chuyển khoản', '09:15',  N'Hoàn thành'),
('HD005', 'SP005', N'Mũ Lưỡi Trai', N'Free Size', N'Đỏ', 3, 'KH004', 450000, 150000, N'Tiền mặt', '13:20', N'Đã hủy'),
('HD006', 'SP006', N'Balo Thể Thao', N'M', N'Đen', 1, 'KH005', 700000, 700000, N'Chuyển khoản', '16:10', N'Hoàn thành'),
('HD007', 'SP007', N'Áo Sơ Mi Nam', N'L', N'Trắng', 2, 'KH002', 600000, 300000, N'Tiền mặt', '11:40', N'Chờ giao'),
('HD008', 'SP008', N'Vớ Nam', N'Free Size', N'Xám', 5, 'KH006', 250000, 50000, N'Chuyển khoản', '17:05', N'Hoàn thành'),
('HD009', 'SP009', N'Kính Mát', N'M', N'Đen', 1, 'KH007', 950000, 950000, N'Tiền mặt', '12:25', N'Đang xử lý'),
('HD010', 'SP010', N'Túi Đeo Chéo', N'S', N'Nâu', 1, 'KH008', 850000, 850000, N'Chuyển khoản', '08:55', N'Hoàn thành');

-- Thêm dữ liệu mẫu cho bảng XuatHang
INSERT INTO XuatHang (
    MaPX, MaKhachHang, HoTen, MaSanPham, TenSanPham, LoaiSP, KichThuoc, MauSac,
    SoLuong, DonGia, ThanhTien, TrangThai
) VALUES
('PX001', 'KH001', N'guyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'Áo sơ mi', N'M', N'Trắng', 2,300000, 600000, N'Hoàn thành'),
('PX002', 'KH001', N'guyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'Áo sơ mi', N'L', N'Trắng', 1,  300000, 300000, N'Đã xuất'),
('PX003', 'KH001', N'guyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'Áo sơ mi', N'S', N'Trắng', 3, 300000, 900000, N'Đang xử lý'),
('PX004', 'KH001', N'guyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'Áo sơ mi', N'XL', N'Trắng', 2,  300000, 600000, N'Chờ xác nhận'),
('PX005', 'KH001', N'guyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'Áo sơ mi', N'M', N'Trắng', 4,  300000, 1200000, N'Đang giao'),
('PX006', 'KH001', N'guyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'Áo sơ mi', N'L', N'Trắng', 1, 300000, 300000, N'Hoàn thành'),
('PX007', 'KH001', N'guyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'Áo sơ mi', N'L', N'Trắng', 2,  300000, 600000, N'Hoàn thành'),
('PX008', 'KH001', N'guyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'Áo sơ mi', N'M', N'Trắng', 1,  300000, 300000, N'Đã xuất'),
('PX009', 'KH001', N'guyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'Áo sơ mi', N'L', N'Trắng', 1,  300000, 300000, N'Đang xử lý'),
('PX010', 'KH001', N'guyễn Văn An', 'SP001', N'Áo sơ mi nam trắng', N'Áo sơ mi', N'M', N'Trắng', 2,  300000, 600000, N'Đã giao');


-- Thêm dữ liệu mẫu cho bảng NhapHang
INSERT INTO NhapHang (MaPN, MaNhaCungCap, LoaiSP, MaSanPham, TenSanPham, MauSac, KichThuoc, SoLuong, DonGia, ThanhTien, TrangThai)
VALUES
('PN001', 'NCC001', N'Áo sơ mi', 'SP001', N'Áo sơ mi nam trắng', N'Trắng', N'L', 50, 250000, 12500000, N'Đã nhập'),
('PN002', 'NCC001', N'Áo sơ mi', 'SP001', N'Áo sơ mi nam trắng', N'Trắng', N'M', 30, 250000, 7500000, N'Đã nhập'),
('PN003', 'NCC001', N'Áo sơ mi', 'SP001', N'Áo sơ mi nam trắng', N'Trắng', N'S', 40, 250000, 10000000, N'Đã nhập'),
('PN004', 'NCC001', N'Áo sơ mi', 'SP001', N'Áo sơ mi nam trắng', N'Trắng', N'XL', 25, 250000, 6250000, N'Chưa nhập'),
('PN005', 'NCC001', N'Áo sơ mi', 'SP001', N'Áo sơ mi nam trắng', N'Trắng', N'XXL', 15, 250000, 3750000, N'Chưa nhập'),
('PN006', 'NCC001', N'Áo sơ mi', 'SP001', N'Áo sơ mi nam trắng', N'Xanh nhạt', N'L', 35, 260000, 9100000, N'Đã nhập'),
('PN007', 'NCC001', N'Áo sơ mi', 'SP001', N'Áo sơ mi nam trắng', N'Xanh nhạt', N'M', 45, 260000, 11700000, N'Đã nhập'),
('PN008', 'NCC001', N'Áo sơ mi', 'SP001', N'Áo sơ mi nam trắng', N'Xanh nhạt', N'S', 20, 260000, 5200000, N'Chưa nhập'),
('PN009', 'NCC001', N'Áo sơ mi', 'SP001', N'Áo sơ mi nam trắng', N'Xanh nhạt', N'XL', 10, 260000, 2600000, N'Chưa nhập'),
('PN010', 'NCC001', N'Áo sơ mi', 'SP001', N'Áo sơ mi nam trắng', N'Xanh nhạt', N'XXL', 5, 260000, 1300000, N'Đã nhập');


INSERT INTO ThongKe (MaSanPham, TenSanPham, SoSPBanRa, DoanhThu)
VALUES
('SP001', N'Áo sơ mi nam trắng', 120, 30000000),
('SP001', N'Áo sơ mi nam trắng', 85, 21250000),
('SP001', N'Áo sơ mi nam trắng', 60, 15000000),
('SP001', N'Áo sơ mi nam trắng', 40, 10000000),
('SP001', N'Áo sơ mi nam trắng', 30, 7500000);


INSERT INTO KhuyenMai (MaKhuyenMai, TenChuongTrinh, GiamGia, NgayBatDau, NgayKetThuc, TrangThai, GiaMoi, Khac)
VALUES
('KM001', N'Ưu đãi mùa hè', 10.00, '2024-06-01', '2024-06-30', N'Hoạt động', 225000, N'Giảm giá mùa hè cho áo sơ mi nam trắng'),
('KM002', N'Black Friday', 20.00, '2024-11-25', '2024-11-29', N'Chưa bắt đầu', 200000, N'Ưu đãi đặc biệt Black Friday'),
('KM003', N'Giáng sinh vui vẻ', 15.00, '2024-12-20', '2024-12-26', N'Chưa bắt đầu', 212500, N'Mừng Giáng Sinh, giảm giá áo sơ mi nam'),
('KM004', N'Tết nguyên đán', 25.00, '2025-01-20', '2025-01-31', N'Chưa bắt đầu', 187500, N'Ưu đãi chào năm mới'),
('KM005', N'Ngày của cha', 12.50, '2024-06-10', '2024-06-20', N'Hoạt động', 218750, N'Khuyến mãi dành cho ngày của cha'),
('KM006', N'Khuyến mãi chào hè', 18.00, '2024-05-01', '2024-05-15', N'Kết thúc', 205000, N'Chương trình ưu đãi đặc biệt tháng 5'),
('KM007', N'Quốc Khánh 2/9', 15.00, '2024-08-28', '2024-09-05', N'Chưa bắt đầu', 212500, N'Ưu đãi ngày Quốc Khánh'),
('KM008', N'Sinh nhật công ty', 22.00, '2024-07-15', '2024-07-20', N'Chưa bắt đầu', 195000, N'Giảm giá nhân dịp sinh nhật công ty'),
('KM009', N'Tri ân khách hàng', 17.00, '2024-09-10', '2024-09-20', N'Chưa bắt đầu', 207500, N'Khuyến mãi tri ân khách hàng thân thiết'),
('KM010', N'Lễ hội mua sắm cuối năm', 30.00, '2024-12-01', '2024-12-10', N'Chưa bắt đầu', 175000, N'Giảm giá lớn dịp cuối năm');

CREATE TABLE PhieuNhap (
    MaPhieuNhap INT PRIMARY KEY IDENTITY(1,1),
    NgayNhap DATETIME DEFAULT GETDATE(),
    MaSanPham VARCHAR(100),
    SoLuongNhap INT,
    MaNhaCungCap VARCHAR(100),
    MaNhanVien VARCHAR(100),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham),
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap),
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien)
);
