package screens.KhuyenMai;

import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import DAO.KhuyenMaiDAO;
import DTO.KhuyenMai;
import java.util.List;
import java.text.SimpleDateFormat;

public class khuyenMaiPanel extends javax.swing.JPanel {
    private KhuyenMaiDAO khuyenMaiDAO;
    private SimpleDateFormat dateFormat;

    public khuyenMaiPanel() {
        initComponents();
        
        // Khởi tạo DAO và định dạng ngày
        khuyenMaiDAO = new KhuyenMaiDAO();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        // Thiết lập combobox tìm kiếm
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { 
            "Tất cả", "Mã KM", "Tên chương trình", "Trạng thái" 
        }));
        
        // Load dữ liệu khuyến mãi
        loadKhuyenMaiData();
        
        // Thêm action listener cho nút tìm kiếm
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchKhuyenMai();
            }
        });
    }
    
    private void loadKhuyenMaiData() {
        List<KhuyenMai> khuyenMaiList = khuyenMaiDAO.getAllKhuyenMai();
        DefaultTableModel model = (DefaultTableModel) khuyenMaiTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        
        int stt = 1;
        for (KhuyenMai km : khuyenMaiList) {
            model.addRow(new Object[]{
                stt++,
                km.getMaKhuyenMai(),
                km.getTenChuongTrinh(),
                dateFormat.format(km.getNgayBatDau()),
                dateFormat.format(km.getNgayKetThuc()),
                String.format("%.2f%%", km.getGiamGia()),
                "",  // Giá mới
                "",  // Khác
                km.getTrangThai()
            });
        }
    }
    
    private void searchKhuyenMai() {
        String keyword = jTextField1.getText().trim();
        if (!keyword.isEmpty()) {
            List<KhuyenMai> searchResults = khuyenMaiDAO.searchKhuyenMai(keyword);
            DefaultTableModel model = (DefaultTableModel) khuyenMaiTable.getModel();
            model.setRowCount(0);
            
            int stt = 1;
            for (KhuyenMai km : searchResults) {
                model.addRow(new Object[]{
                    stt++,
                    km.getMaKhuyenMai(),
                    km.getTenChuongTrinh(),
                    dateFormat.format(km.getNgayBatDau()),
                    dateFormat.format(km.getNgayKetThuc()),
                    String.format("%.2f%%", km.getGiamGia()),
                    "",  // Giá mới
                    "",  // Khác
                    km.getTrangThai()
                });
            }
        } else {
            loadKhuyenMaiData(); // Nếu từ khóa trống, load lại tất cả dữ liệu
        }
    }

    public javax.swing.JPanel getKhuyenMaiPanel() {
        return containerPanel;
    }

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
        jPanel17 = new javax.swing.JPanel();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        khuyenMaiTable = new javax.swing.JTable();
        jButton34 = new javax.swing.JButton();
        jPanel33 = new javax.swing.JPanel();
        jButton30 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();

        containerPanel.setPreferredSize(new java.awt.Dimension(960, 680));
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader.setBackground(new java.awt.Color(12, 150, 156));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Quản lý Hàng Khuyến Mãi");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                .addContainerGap(373, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(351, 351, 351))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        containerPanel.add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 70));

        pnlContent.setBackground(new java.awt.Color(107, 163, 190));

        jPanel17.setBackground(new java.awt.Color(107, 163, 190));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chỉnh sửa"));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton31.setText("Thêm ");
        jPanel17.add(jButton31, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 24, -1, 34));

        jButton32.setText("Sửa");
        jPanel17.add(jButton32, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, -1, 34));

        jButton33.setText("Xóa");
        jPanel17.add(jButton33, new org.netbeans.lib.awtextra.AbsoluteConstraints(703, 24, -1, 34));

        jPanel18.setBackground(new java.awt.Color(107, 163, 190));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng thông tin\n"));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        khuyenMaiTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "STT", "Mã KM", "Tên chương trình", "Ngày bắt đầu", "Ngày kết thúc", 
                "Giảm giá", "Giá mới", "Khác", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        khuyenMaiTable.setShowGrid(true);
        jScrollPane2.setViewportView(khuyenMaiTable);

        jPanel18.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 24, 920, 290));

        jButton34.setText("Lưu và xuất file");

        jPanel33.setBackground(new java.awt.Color(107, 163, 190));
        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm\n"));
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton30.setText("Tìm kiếm");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });
        jPanel33.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 40, 90, 36));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Tìm kiếm");
        jPanel33.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel33.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 300, 30));
        jPanel33.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 320, 30));

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(317, 317, 317)
                        .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 961, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        containerPanel.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(containerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));
    }

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    // Variables declaration - do not modify
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable khuyenMaiTable;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
    // End of variables declaration
}
