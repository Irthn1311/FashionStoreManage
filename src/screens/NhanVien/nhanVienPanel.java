package screens.NhanVien;

import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import DAO.NhanVienDAO;
import DTO.nhanVienDTO;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

/**
 *
 * @author nson9
 */
public class nhanVienPanel extends javax.swing.JPanel {
    private NhanVienDAO nhanVienDAO;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timestampFormat;
    private DecimalFormat decimalFormat;

    /**
     * Creates new form nhanvien
     */
    public nhanVienPanel() {
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
        jButton34 = new javax.swing.JButton();
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

        jButton34.setText("Lưu và xuất file");

        jPanel17.setBackground(new java.awt.Color(107, 163, 190));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chỉnh sửa"));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton31.setText("Thêm ");
        jPanel17.add(jButton31, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, -1, 34));

        jButton32.setText("Sửa");
        jPanel17.add(jButton32, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, -1, 34));

        jButton33.setText("Xóa");
        jPanel17.add(jButton33, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, -1, 34));

        jPanel18.setBackground(new java.awt.Color(107, 163, 190));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng thông tin"));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nhanVienTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "STT", "Mã NV", "Tên NV", "Mã TK", "Năm sinh", "Giới tính", "Địa chỉ", 
                "Chức vụ", "Mức lương", "Năm vào làm", "Email", "Số điện thoại", "Chi tiết"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false,
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
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
                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        DefaultTableModel model = (DefaultTableModel) nhanVienTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        
        int stt = 1;
        for (nhanVienDTO nv : nhanVienList) {
            String ngaySinhStr = "";
            String ngayVaoLamStr = "";
            
            try {
                if (nv.getNgaySinh() != null) {
                    ngaySinhStr = dateFormat.format(nv.getNgaySinh());
                }
                if (nv.getNgayVaoLam() != null) {
                    ngayVaoLamStr = timestampFormat.format(nv.getNgayVaoLam());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            model.addRow(new Object[]{
                stt++,
                nv.getMaNhanVien(),
                nv.getHoTen(),
                nv.getMaTaiKhoan(),
                ngaySinhStr,
                nv.getGioiTinh(),
                nv.getDiaChi(),
                nv.getChucVu(),
                decimalFormat.format(nv.getMucLuong()),
                ngayVaoLamStr,
                nv.getEmail(),
                nv.getSoDienThoai(),
                "Xem chi tiết"
            });
        }
    }
    
    private void searchNhanVien() {
        String keyword = jTextField1.getText().trim();
        if (!keyword.isEmpty()) {
            List<nhanVienDTO> searchResults = nhanVienDAO.searchNhanVien(keyword);
            DefaultTableModel model = (DefaultTableModel) nhanVienTable.getModel();
            model.setRowCount(0);
            
            int stt = 1;
            for (nhanVienDTO nv : searchResults) {
                String ngaySinhStr = "";
                String ngayVaoLamStr = "";
                
                try {
                    if (nv.getNgaySinh() != null) {
                        ngaySinhStr = dateFormat.format(nv.getNgaySinh());
                    }
                    if (nv.getNgayVaoLam() != null) {
                        ngayVaoLamStr = timestampFormat.format(nv.getNgayVaoLam());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                model.addRow(new Object[]{
                    stt++,
                    nv.getMaNhanVien(),
                    nv.getHoTen(),
                    nv.getMaTaiKhoan(),
                    ngaySinhStr,
                    nv.getGioiTinh(),
                    nv.getDiaChi(),
                    nv.getChucVu(),
                    decimalFormat.format(nv.getMucLuong()),
                    ngayVaoLamStr,
                    nv.getEmail(),
                    nv.getSoDienThoai(),
                    "Xem chi tiết"
                });
            }
        } else {
            loadNhanVienData(); // Nếu từ khóa trống, load lại tất cả dữ liệu
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel containerPanel;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
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
    // End of variables declaration//GEN-END:variables
}
