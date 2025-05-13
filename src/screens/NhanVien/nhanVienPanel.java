package screens.NhanVien;

import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import DAO.NhanVienDAO;
import DTO.nhanVienDTO;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Cursor;
import java.util.ArrayList;
import BUS.TaiKhoanBUS;
import DTO.taiKhoanDTO;
import java.awt.Dimension;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import BUS.PhanQuyenBUS;
import java.util.EnumSet;
import BUS.NhanVienBUS;
import DTO.VaiTro;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import utils.FileUtils;
import javax.swing.JEditorPane;

/**
 *
 * @author nson9
 */
public class nhanVienPanel extends javax.swing.JPanel {
    private NhanVienDAO nhanVienDAO;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timestampFormat;
    private DecimalFormat decimalFormat;
    private DefaultTableModel tableModel;
    private TaiKhoanBUS taiKhoanBUS;
    private JComboBox<String> cboVaiTro;
    private taiKhoanDTO taiKhoan;
    private javax.swing.JButton btnImport;  // New button for import
    private javax.swing.JButton btnPrinter; // New button for printing

    /**
     * Creates new form nhanvien
     */
    public nhanVienPanel(taiKhoanDTO taiKhoan) {
        this.taiKhoan = taiKhoan;
        initComponents();
        
        // Khởi tạo DAO và định dạng
        nhanVienDAO = new NhanVienDAO();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        timestampFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        decimalFormat = new DecimalFormat("#,###.##");
        
        // Thiết lập combobox tìm kiếm
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { 
            "Tất cả", "Mã nhân viên", "Tên nhân viên", "Email", "Số điện thoại" 
        }));
        
        // Load dữ liệu nhân viên
        loadNhanVienData();
        
        // Thêm action listener cho nút tìm kiếm
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchNhanVien();
            }
        });

        // Thêm action listener cho nút Thêm
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themNhanVien();
            }
        });

        // Thêm action listener cho nút Sửa
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suaNhanVien();
            }
        });

        // Thêm action listener cho nút Xóa
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaNhanVien();
            }
        });

        // Thêm ComboBox cho VaiTro
        String[] vaiTroOptions = {
            VaiTro.QUAN_TRI.getDisplayName(),
            VaiTro.QUAN_LY_KHO.getDisplayName(),
            VaiTro.QUAN_LY_NHAN_VIEN.getDisplayName(),
            VaiTro.NHAN_VIEN.getDisplayName()
        };
        cboVaiTro = new JComboBox<>(vaiTroOptions);
        
        // Kiểm tra quyền thay đổi vai trò
        if (taiKhoan.getVaiTro() != VaiTro.QUAN_TRI) {
            cboVaiTro.setEnabled(false);
            cboVaiTro.setToolTipText("Chỉ Quản trị viên mới có quyền thay đổi vai trò");
        }
        
        // Thêm sự kiện khi thay đổi vai trò
        cboVaiTro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (taiKhoan.getVaiTro() == VaiTro.QUAN_TRI) {
                    String selectedVaiTro = (String) cboVaiTro.getSelectedItem();
                    String maTaiKhoan = getSelectedMaTaiKhoan();
                    
                    if (maTaiKhoan != null) {
                        boolean success = taiKhoanBUS.capNhatVaiTro(maTaiKhoan, selectedVaiTro);
                        if (success) {
                            JOptionPane.showMessageDialog(null, 
                                "Cập nhật vai trò thành công!", 
                                "Thông báo", 
                                JOptionPane.INFORMATION_MESSAGE);
                            loadNhanVienData(); // Refresh bảng
                        } else {
                            JOptionPane.showMessageDialog(null, 
                                "Cập nhật vai trò thất bại!", 
                                "Lỗi", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }
    
    public javax.swing.JPanel getNhanVienPanel() {
        return containerPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Khởi tạo panel chính
        containerPanel = new javax.swing.JPanel();
        containerPanel.setPreferredSize(new java.awt.Dimension(1000, 700));
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // Khởi tạo các components
        pnlHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        btnExport = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        nhanVienTable = new javax.swing.JTable();
        jPanel33 = new javax.swing.JPanel();
        jButton30 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        containerPanel.setPreferredSize(new java.awt.Dimension(960, 680));
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader.setBackground(new java.awt.Color(12, 150, 156));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Quản lý loại nhân viên");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                .addContainerGap(398, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(368, 368, 368))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        containerPanel.add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 70));

        jPanel9.setBackground(new java.awt.Color(107, 163, 190));

        btnExport.setText("Lưu và xuất file");

        // Add Import button
        btnImport = new javax.swing.JButton("Import");
        ImageIcon importIcon = new ImageIcon("src/icon_img/import_icon.png");
        btnImport.setIcon(new ImageIcon(
                importIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
        btnImport.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnImport.setPreferredSize(new java.awt.Dimension(100, 40));
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utils.FileUtils.importFromExcelForNhanVien(nhanVienTable);
                loadNhanVienData(); // Refresh the table after import
            }
        });

        // Add Print button
        btnPrinter = new javax.swing.JButton("In");
        ImageIcon printIcon = new ImageIcon("src/icon_img/print_icon.png");
        btnPrinter.setIcon(new ImageIcon(
                printIcon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
        btnPrinter.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnPrinter.setPreferredSize(new java.awt.Dimension(100, 40));
        btnPrinter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    // OLD: nhanVienTable.print();
                    NhanVienDAO dao = new NhanVienDAO();
                    List<nhanVienDTO> nhanVienList = dao.getAllNhanVien();

                    if (nhanVienList == null || nhanVienList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Không có dữ liệu nhân viên để in.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    StringBuilder htmlContent = new StringBuilder();
                    htmlContent.append("<html><head><style>");
                    htmlContent.append("body { font-family: Arial, sans-serif; margin: 20px; }");
                    htmlContent.append("h1 { text-align: center; color: #333; }");
                    htmlContent.append(".employee-record { border: 1px solid #ccc; padding: 10px; margin-bottom: 15px; border-radius: 5px; page-break-inside: avoid; }");
                    htmlContent.append(".field-label { font-weight: bold; color: #555; }");
                    htmlContent.append("p { margin: 5px 0; }");
                    htmlContent.append("hr { border: 0; border-top: 1px dashed #ccc; margin: 10px 0; }");
                    htmlContent.append("</style></head><body>");
                    htmlContent.append("<h1>Danh Sách Chi Tiết Nhân Viên</h1>");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat timestampFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

                    for (nhanVienDTO nv : nhanVienList) {
                        htmlContent.append("<div class='employee-record'>");
                        htmlContent.append("<p><span class='field-label'>Mã Nhân Viên:</span> ").append(nv.getMaNhanVien() != null ? nv.getMaNhanVien() : "").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Họ Tên:</span> ").append(nv.getHoTen() != null ? nv.getHoTen() : "").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Giới Tính:</span> ").append(nv.getGioiTinh() != null ? nv.getGioiTinh() : "").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Ngày Sinh:</span> ").append(nv.getNgaySinh() != null ? dateFormat.format(nv.getNgaySinh()) : "Chưa cập nhật").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Số Điện Thoại:</span> ").append(nv.getSoDienThoai() != null ? nv.getSoDienThoai() : "").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Email:</span> ").append(nv.getEmail() != null ? nv.getEmail() : "").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Địa Chỉ:</span> ").append(nv.getDiaChi() != null ? nv.getDiaChi() : "").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Chức Vụ:</span> ").append(nv.getChucVu() != null ? nv.getChucVu() : "").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Mức Lương:</span> ").append(nv.getMucLuong() != null ? decimalFormat.format(nv.getMucLuong()) : "Chưa cập nhật").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Ngày Vào Làm:</span> ").append(nv.getNgayVaoLam() != null ? timestampFormat.format(nv.getNgayVaoLam()) : "Chưa cập nhật").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Mã Quản Lý:</span> ").append(nv.getMaQuanLy() != null ? nv.getMaQuanLy() : "").append("</p>");
                        htmlContent.append("<p><span class='field-label'>Trạng Thái Nhân Viên:</span> ").append(nv.getTrangThai() != null ? nv.getTrangThai() : "").append("</p>");
                        
                        taiKhoanDTO tk = dao.getTaiKhoanByMaNhanVien(nv.getMaNhanVien());
                        if (tk != null) {
                            htmlContent.append("<hr>");
                            htmlContent.append("<p><span class='field-label'>Tên Đăng Nhập (TK):</span> ").append(tk.getTenDangNhap() != null ? tk.getTenDangNhap() : "").append("</p>");
                            // htmlContent.append("<p><span class='field-label'>Mật Khẩu (TK):</span> ").append(tk.getMatKhau() != null ? tk.getMatKhau() : "").append("</p>"); // Consider security if printing passwords
                            htmlContent.append("<p><span class='field-label'>Vai Trò (TK):</span> ").append(tk.getVaiTro() != null ? tk.getVaiTro().getDisplayName() : "").append("</p>");
                            String trangThaiTKStr;
                            switch (tk.getTrangThai()) {
                                case 1: trangThaiTKStr = "Hoạt động"; break;
                                case 0: trangThaiTKStr = "Không hoạt động"; break;
                                case -1: trangThaiTKStr = "Đang xét duyệt"; break;
                                default: trangThaiTKStr = "Không xác định";
                            }
                            htmlContent.append("<p><span class='field-label'>Trạng Thái (TK):</span> ").append(trangThaiTKStr).append("</p>");
                        } else {
                            htmlContent.append("<hr>");
                            htmlContent.append("<p><em>Nhân viên này chưa có tài khoản.</em></p>");
                        }
                        htmlContent.append("</div>");
                    }
                    htmlContent.append("</body></html>");

                    JEditorPane editorPane = new JEditorPane();
                    editorPane.setContentType("text/html");
                    editorPane.setText(htmlContent.toString());
                    editorPane.setEditable(false);
                    
                    // For a better print preview experience, you might put the JEditorPane in a JScrollPane
                    // and show it in a JDialog before printing, but for direct printing:
                    boolean printed = editorPane.print();
                    if (printed) {
                        // JOptionPane.showMessageDialog(null, "Đã gửi lệnh in thành công.", "In Hoàn Tất", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Lệnh in đã bị hủy.", "In Bị Hủy", JOptionPane.WARNING_MESSAGE);
                    }

                } catch (java.awt.print.PrinterException pe) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi in: Không tìm thấy máy in hoặc lỗi máy in.\\n" + pe.getMessage(), "Lỗi In Ấn", JOptionPane.ERROR_MESSAGE);
                     pe.printStackTrace();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi chuẩn bị dữ liệu để in: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
        
        // Add Export functionality 
        btnExport.setIcon(new ImageIcon(
                new ImageIcon("src/icon_img/export_icon.png").getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
        btnExport.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnExport.setPreferredSize(new java.awt.Dimension(340, 40));
        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utils.FileUtils.showExportOptionsForNhanVien(nhanVienTable, "DanhSachNhanVien");
            }
        });

        jPanel17.setBackground(new java.awt.Color(107, 163, 190));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chỉnh sửa"));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton31.setText("Thêm ");
        jPanel17.add(jButton31, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 100, 34));

        jButton32.setText("Sửa");
        jPanel17.add(jButton32, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 100, 34));

        jButton33.setText("Xóa");
        jPanel17.add(jButton33, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 100, 34));

        // Thêm nút thay đổi trạng thái
        btnTrangThai = new javax.swing.JButton();
        btnTrangThai.setText("Khóa");
        btnTrangThai.setPreferredSize(new Dimension(100, 35));
        jPanel17.add(btnTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 30, 100, 34));
        btnTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thayDoiTrangThai();
            }
        });

        jPanel18.setBackground(new java.awt.Color(107, 163, 190));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng thông tin"));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableModel.addColumn("Mã nhân viên");
        tableModel.addColumn("Họ tên");
        tableModel.addColumn("Số điện thoại");
        tableModel.addColumn("Email");
        tableModel.addColumn("Địa chỉ");
        tableModel.addColumn("Vị trí");
        tableModel.addColumn("Tên đăng nhập");
        tableModel.addColumn("Trạng thái tài khoản");
        tableModel.addColumn("Chi tiết");
        
        nhanVienTable.setModel(tableModel);
        nhanVienTable.setShowGrid(true);
        jScrollPane2.setViewportView(nhanVienTable);

        jPanel18.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 940, 290));

        jPanel33.setBackground(new java.awt.Color(107, 163, 190));
        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm\n"));
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton30.setText("Tìm kiếm");
        jPanel33.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 40, 90, 36));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Tìm kiếm");
        jPanel33.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel33.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 300, 30));
        jPanel33.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 320, 30));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(320, 320, 320))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        containerPanel.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));

        add(containerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));
    }

    private void loadNhanVienData() {
        List<nhanVienDTO> nhanVienList = nhanVienDAO.getAllNhanVien();
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        
        for (nhanVienDTO nv : nhanVienList) {
            taiKhoanDTO taiKhoan = nhanVienDAO.getTaiKhoanByMaNhanVien(nv.getMaNhanVien());
            String tenDangNhap = taiKhoan != null ? taiKhoan.getTenDangNhap() : "Chưa có";
            String trangThai = taiKhoan != null ? (taiKhoan.getTrangThai() == 1 ? "Hoạt động" : "Không hoạt động") : "Chưa có";
            
            tableModel.addRow(new Object[]{
                nv.getMaNhanVien(),
                nv.getHoTen(),
                nv.getSoDienThoai(),
                nv.getEmail(),
                nv.getDiaChi(),
                nv.getChucVu(),
                tenDangNhap,
                trangThai,
                "Xem chi tiết"
            });
        }
        
        setupTable(); // Gọi setupTable sau khi load dữ liệu
    }
    
    private void setupTable() {
        // Thiết lập chiều rộng cho các cột
        nhanVienTable.getColumnModel().getColumn(0).setPreferredWidth(100);  // Mã NV
        nhanVienTable.getColumnModel().getColumn(1).setPreferredWidth(150);  // Tên NV
        nhanVienTable.getColumnModel().getColumn(2).setPreferredWidth(120);  // Số điện thoại
        nhanVienTable.getColumnModel().getColumn(3).setPreferredWidth(150);  // Email
        nhanVienTable.getColumnModel().getColumn(4).setPreferredWidth(200);  // Địa chỉ
        nhanVienTable.getColumnModel().getColumn(5).setPreferredWidth(100);  // Vị trí
        nhanVienTable.getColumnModel().getColumn(6).setPreferredWidth(120);  // Tên đăng nhập
        nhanVienTable.getColumnModel().getColumn(7).setPreferredWidth(120);  // Trạng thái tài khoản

        // Thiết lập căn giữa cho một số cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        nhanVienTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);  // Mã NV
        nhanVienTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);  // Tên NV
        nhanVienTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);  // Số điện thoại
        nhanVienTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);  // Email
        nhanVienTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);  // Địa chỉ
        nhanVienTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);  // Vị trí
        nhanVienTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);  // Tên đăng nhập
        nhanVienTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);  // Trạng thái tài khoản

        // Thiết lập màu xanh và gạch chân cho cột "Chi tiết"
        nhanVienTable.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(new Color(0, 0, 255)); // Màu xanh cho chữ
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        // Thiết lập row height
        nhanVienTable.setRowHeight(25);

        // Thêm sự kiện chuột cho cột Chi tiết
        nhanVienTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = nhanVienTable.rowAtPoint(evt.getPoint());
                int col = nhanVienTable.columnAtPoint(evt.getPoint());
                
                if (row >= 0 && col == 8) { // Cột Chi tiết
                    String maNV = nhanVienTable.getValueAt(row, 0).toString(); // Sửa từ cột 1 thành cột 0
                    xemChiTietNhanVien(maNV);
                }
            }
        });

        // Thêm cursor hand khi di chuột qua cột Chi tiết
        nhanVienTable.addMouseMotionListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                int col = nhanVienTable.columnAtPoint(evt.getPoint());
                if (col == 8) { // Cột Chi tiết
                    nhanVienTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    nhanVienTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });

        // Thêm sự kiện chọn dòng trong bảng
        nhanVienTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = nhanVienTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String trangThai = nhanVienTable.getValueAt(selectedRow, 7).toString();
                    btnTrangThai.setText(trangThai.equals("Hoạt động") ? "Khóa" : "Mở Khóa");
                }
            }
        });
    }
    
    private void searchNhanVien() {
        String keyword = jTextField1.getText().trim();
        if (!keyword.isEmpty()) {
            List<nhanVienDTO> searchResults = nhanVienDAO.searchNhanVien(keyword);
            tableModel.setRowCount(0);
            
            for (nhanVienDTO nv : searchResults) {
                taiKhoanDTO taiKhoan = nhanVienDAO.getTaiKhoanByMaNhanVien(nv.getMaNhanVien());
                String tenDangNhap = taiKhoan != null ? taiKhoan.getTenDangNhap() : "Chưa có";
                String trangThai = taiKhoan != null ? (taiKhoan.getTrangThai() == 1 ? "Hoạt động" : "Không hoạt động") : "Chưa có";
                
                tableModel.addRow(new Object[]{
                    nv.getMaNhanVien(),
                    nv.getHoTen(),
                    nv.getSoDienThoai(),
                    nv.getEmail(),
                    nv.getDiaChi(),
                    nv.getChucVu(),
                    tenDangNhap,
                    trangThai,
                    "Xem chi tiết"
                });
            }
        } else {
            loadNhanVienData(); // Nếu từ khóa trống, load lại tất cả dữ liệu
        }
    }

    private void themNhanVien() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Thêm Nhân Viên", true);
        themNhanVienPanel panel = new themNhanVienPanel(this.taiKhoan.getVaiTro());
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        loadNhanVienData(); // Reload data after adding
    }

    private void suaNhanVien() {
        int selectedRow = nhanVienTable.getSelectedRow();
        if (selectedRow >= 0) {
            String maNV = nhanVienTable.getValueAt(selectedRow, 0).toString(); // Sửa từ cột 1 thành cột 0
            try {
                nhanVienDTO nhanVien = nhanVienDAO.getNhanVienByMa(maNV);
                if (nhanVien != null) {
                    JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Sửa Nhân Viên", true);
                    suaNhanVienPanel panel = new suaNhanVienPanel(dialog, nhanVien, this.taiKhoan.getVaiTro());
                    dialog.add(panel);
                    dialog.pack();
                    dialog.setLocationRelativeTo(this);
                    dialog.setVisible(true);
                    loadNhanVienData(); // Reload data after editing
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi lấy thông tin nhân viên: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn nhân viên cần sửa!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void xoaNhanVien() {
        int selectedRow = nhanVienTable.getSelectedRow();
        if (selectedRow >= 0) {
            String maNV = nhanVienTable.getValueAt(selectedRow, 0).toString();
            String tenNV = nhanVienTable.getValueAt(selectedRow, 1).toString();
            
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa nhân viên " + tenNV + " không?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    boolean success = nhanVienDAO.xoaNhanVien(maNV);
                    if (success) {
                        JOptionPane.showMessageDialog(this,
                            "Đã xóa nhân viên thành công!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                        // Cập nhật lại bảng
                        loadNhanVienData();
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Không thể xóa nhân viên!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                        "Lỗi khi xóa nhân viên: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn nhân viên cần xóa",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void xemChiTietNhanVien(String maNV) {
        try {
            nhanVienDTO nhanVien = nhanVienDAO.getNhanVienByMa(maNV);
            if (nhanVien != null) {
                chiTietNhanVienDialog dialog = new chiTietNhanVienDialog((JFrame) SwingUtilities.getWindowAncestor(this), nhanVien);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Không tìm thấy thông tin nhân viên!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi lấy thông tin chi tiết: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void thayDoiTrangThai() {
        int selectedRow = nhanVienTable.getSelectedRow();
        if (selectedRow >= 0) {
            String maNhanVien = (String) nhanVienTable.getValueAt(selectedRow, 0);
            taiKhoanDTO taiKhoan = nhanVienDAO.getTaiKhoanByMaNhanVien(maNhanVien);
            
            if (taiKhoan != null) {
                if (nhanVienDAO.capNhatTrangThaiTaiKhoan(taiKhoan)) {
                    // Cập nhật lại trạng thái trong bảng
                    String trangThai = "";
                    switch (taiKhoan.getTrangThai()) {
                        case -1:
                            trangThai = "Đang xét duyệt";
                            break;
                        case 1:
                            trangThai = "Hoạt động";
                            break;
                        case 0:
                            trangThai = "Không hoạt động";
                            break;
                    }
                    nhanVienTable.setValueAt(trangThai, selectedRow, 7);
                    
                    // Cập nhật text của nút
                    switch (taiKhoan.getTrangThai()) {
                        case -1:
                            btnTrangThai.setText("Duyệt");
                            break;
                        case 1:
                            btnTrangThai.setText("Khóa");
                            break;
                        case 0:
                            btnTrangThai.setText("Mở Khóa");
                            break;
                    }
                    
                    JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thất bại!");
                }
            }
        }
    }

    private String getSelectedMaTaiKhoan() {
        int selectedRow = nhanVienTable.getSelectedRow();
        if (selectedRow >= 0) {
            return nhanVienTable.getValueAt(selectedRow, 0).toString(); // Giả sử cột 0 là MaTaiKhoan
        }
        return null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel containerPanel;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton btnExport;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable nhanVienTable;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JButton btnTrangThai;
    // End of variables declaration//GEN-END:variables
}
