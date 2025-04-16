package screens.SanPham;

import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import DAO.SanPhamDAO;
import DTO.sanPhamDTO;
import java.util.List;

public class sanPhamPanel extends javax.swing.JPanel {
    private SanPhamDAO sanPhamDAO;

    public sanPhamPanel() {
        initComponents();
        sanPhamDAO = new SanPhamDAO();
        loadSanPhamData();
    }

    private void loadSanPhamData() {
        List<sanPhamDTO> sanPhamList = sanPhamDAO.getAllSanPham();
        DefaultTableModel model = (DefaultTableModel) sanPhamTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        int stt = 1;
        for (sanPhamDTO sp : sanPhamList) {
            model.addRow(new Object[] {
                    stt++,
                    sp.getMaSanPham(),
                    sp.getTenSanPham(),
                    sp.getSoLuongTonKho(),
                    sp.getGiaBan(),
                    sp.getImgURL(),
                    sp.getTrangThai()
            });
        }
    }

    public javax.swing.JPanel getSanPhamPanel() {
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
        jScrollPane1 = new javax.swing.JScrollPane();
        sanPhamTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jPanel33 = new javax.swing.JPanel();
        jButton30 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();

        containerPanel.setPreferredSize(new java.awt.Dimension(960, 680));
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader.setBackground(new java.awt.Color(12, 150, 156));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Quản lý sản phẩm");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                                .addContainerGap(430, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(379, 379, 379)));
        pnlHeaderLayout.setVerticalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE)));

        containerPanel.add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 70));

        pnlContent.setBackground(new java.awt.Color(107, 163, 190));

        sanPhamTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "STT", "Mã SP", "Tên SP", "Mã NCC", "Loại Sp", "Màu sắc", "Kích cỡ", "Số Lượng", "Đơn Giá",
                        "Hình Ảnh", "Trạng Thái"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        sanPhamTable.setShowGrid(true);
        jScrollPane1.setViewportView(sanPhamTable);

        jPanel5.setBackground(new java.awt.Color(153, 153, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField3.setText("Img SP");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 7, 258, 176));

        jTextField4.setText("Ma SP ");
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(468, 26, 110, 34));

        jTextField5.setText("Tên Sp ");
        jPanel5.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(468, 78, 110, 33));

        jTextField6.setText("Số Lượng");
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(468, 129, 110, 35));

        jTextField7.setText("Đơn giá");
        jPanel5.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(624, 26, 107, 34));

        jTextField8.setText("Hình Ảnh");
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(624, 78, 107, 33));

        jTextField9.setText("Trạng Thái");
        jPanel5.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(624, 129, 107, 35));

        jPanel33.setBackground(new java.awt.Color(107, 163, 190));
        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm\n"));
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton30.setText("Tìm kiếm");
        jPanel33.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 40, 90, 36));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Tìm kiếm");
        jPanel33.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jComboBox1.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel33.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 300, 30));
        jPanel33.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 320, 30));

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
                pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlContentLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pnlContentLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 962,
                                                Short.MAX_VALUE)
                                        .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(20, Short.MAX_VALUE)));
        pnlContentLayout.setVerticalGroup(
                pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlContentLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 113,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31,
                                        Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 190,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)));

        containerPanel.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(containerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));
    }

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    // Variables declaration - do not modify
    private javax.swing.JPanel containerPanel;
    private javax.swing.JButton jButton30;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable sanPhamTable;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
    // End of variables declaration
}
