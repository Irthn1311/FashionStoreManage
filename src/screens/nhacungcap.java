/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package screens;

import javax.swing.UIManager;

/**
 *
 * @author nson9
 */
public class nhacungcap extends javax.swing.JFrame {

    /**
     * Creates new form ncc
     */
    public nhacungcap() {
        initComponents();
        setupContainerPanel();
    }
    public javax.swing.JPanel getNhaCungCapPanel() {
        return containerPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlContent = new javax.swing.JPanel();
        jButton34 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jTextField26 = new javax.swing.JTextField();
        jTextField27 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jTextField30 = new javax.swing.JTextField();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel60 = new javax.swing.JPanel();
        jPanel59 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jTextField56 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton14 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextField31 = new javax.swing.JTextField();
        jTextField32 = new javax.swing.JTextField();
        jTextField33 = new javax.swing.JTextField();
        jTextField34 = new javax.swing.JTextField();
        jTextField35 = new javax.swing.JTextField();
        jTextField36 = new javax.swing.JTextField();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        pnlHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlContent.setBackground(new java.awt.Color(107, 163, 190));

        jButton34.setText("Lưu và xuất file");

        jPanel17.setBackground(new java.awt.Color(107, 163, 190));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chỉnh sửa"));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton31.setText("Thêm ");
        jPanel17.add(jButton31, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 24, -1, 34));

        jButton32.setText("Sửa");
        jPanel17.add(jButton32, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 24, -1, 34));

        jButton33.setText("Xóa");
        jPanel17.add(jButton33, new org.netbeans.lib.awtextra.AbsoluteConstraints(705, 24, -1, 34));

        jPanel21.setBackground(new java.awt.Color(107, 163, 190));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng thêm"));
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setText("Mã NCC");
        jPanel21.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 29, -1, -1));

        jLabel14.setText("Mã SP");
        jPanel21.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 29, 51, -1));

        jLabel15.setText("Tên SP");
        jPanel21.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 29, 57, -1));

        jLabel16.setText("Tên NCC");
        jPanel21.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 24, -1, 26));

        jLabel17.setText("Năm hợp tác");
        jPanel21.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(499, 29, 77, -1));

        jLabel18.setText("SDT");
        jPanel21.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(722, 29, 56, -1));
        jPanel21.add(jTextField25, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 50, 77, -1));

        jTextField26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField26ActionPerformed(evt);
            }
        });
        jPanel21.add(jTextField26, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 50, 77, -1));

        jTextField27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField27ActionPerformed(evt);
            }
        });
        jPanel21.add(jTextField27, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 77, -1));
        jPanel21.add(jTextField28, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 77, -1));

        jTextField29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField29ActionPerformed(evt);
            }
        });
        jPanel21.add(jTextField29, new org.netbeans.lib.awtextra.AbsoluteConstraints(499, 50, 77, -1));
        jPanel21.add(jTextField30, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 50, 77, -1));

        jButton38.setText("Hủy");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton38, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 20, -1, -1));

        jButton39.setText("Thêm");
        jPanel21.add(jButton39, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 60, -1, -1));

        jLabel7.setText("Địa chỉ");
        jPanel21.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(629, 29, -1, -1));
        jPanel21.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(613, 50, 71, -1));

        jPanel18.setBackground(new java.awt.Color(107, 163, 190));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng thông tin"));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã NCC", "Tên NCC", "Mã SP", "TÊN SP", "Năm hợp tác", "Địa chỉ", "Số điện thoại"
            }
        ));
        jTable2.setShowGrid(true);
        jScrollPane2.setViewportView(jTable2);

        jPanel18.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 24, 920, 90));

        jPanel60.setBackground(new java.awt.Color(107, 163, 190));
        jPanel60.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm"));
        jPanel60.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel59.setBackground(new java.awt.Color(107, 163, 190));
        jPanel59.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mã NCC"));

        jLabel58.setText("Mã NCC ");

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField56, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField56)
                    .addComponent(jLabel58))
                .addContainerGap())
        );

        jPanel60.add(jPanel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 70));

        jPanel5.setBackground(new java.awt.Color(107, 163, 190));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tên NCC"));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("Tên NCC");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 27, 52, -1));
        jPanel5.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 24, 119, -1));

        jPanel60.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 220, 70));

        jPanel7.setBackground(new java.awt.Color(107, 163, 190));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mã SP"));

        jLabel9.setText("Mã SP");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel60.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 22, -1, 70));

        jButton14.setText("Tìm kiếm");
        jPanel60.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(836, 39, -1, 40));

        jPanel8.setBackground(new java.awt.Color(107, 163, 190));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tên SP"));

        jLabel11.setText("Tên SP ");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel60.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(643, 22, -1, 70));

        jPanel22.setBackground(new java.awt.Color(107, 163, 190));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng sửa"));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setText("Mã NCC");
        jPanel22.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 29, -1, -1));

        jLabel20.setText("Mã SP");
        jPanel22.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 29, 51, -1));

        jLabel21.setText("Tên SP");
        jPanel22.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 29, 57, -1));

        jLabel22.setText("Tên NCC");
        jPanel22.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 24, -1, 26));

        jLabel23.setText("Năm hợp tác");
        jPanel22.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(499, 29, 77, -1));

        jLabel24.setText("SDT");
        jPanel22.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(722, 29, 56, -1));
        jPanel22.add(jTextField31, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 50, 77, -1));

        jTextField32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField32ActionPerformed(evt);
            }
        });
        jPanel22.add(jTextField32, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 50, 77, -1));

        jTextField33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField33ActionPerformed(evt);
            }
        });
        jPanel22.add(jTextField33, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 77, -1));
        jPanel22.add(jTextField34, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 77, -1));

        jTextField35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField35ActionPerformed(evt);
            }
        });
        jPanel22.add(jTextField35, new org.netbeans.lib.awtextra.AbsoluteConstraints(499, 50, 77, -1));
        jPanel22.add(jTextField36, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 50, 77, -1));

        jButton40.setText("Hủy");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });
        jPanel22.add(jButton40, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 20, -1, -1));

        jButton41.setText("Thêm");
        jPanel22.add(jButton41, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 60, -1, -1));

        jLabel25.setText("Địa chỉ");
        jPanel22.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(629, 29, -1, -1));
        jPanel22.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(613, 50, 71, -1));

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlContentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlContentLayout.createSequentialGroup()
                        .addGap(284, 284, 284)
                        .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlContentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 939, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel60, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        getContentPane().add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 960, 610));

        pnlHeader.setBackground(new java.awt.Color(12, 150, 156));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Quản lý nhà cung cấp");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(335, 335, 335)
                .addComponent(jLabel1)
                .addContainerGap(395, Short.MAX_VALUE))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 960, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton40ActionPerformed

    private void jTextField35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField35ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField35ActionPerformed

    private void jTextField33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField33ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField33ActionPerformed

    private void jTextField32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField32ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField32ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jTextField29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField29ActionPerformed

    private void jTextField27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField27ActionPerformed

    private void jTextField26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField26ActionPerformed

    private void setupContainerPanel() {
        // Tạo panel container mới
        containerPanel = new javax.swing.JPanel();
        containerPanel.setLayout(null); // Sử dụng null layout để set vị trí thủ công
        
        // Thêm jPanel23 (header) và jPanel34 (content) vào container
        pnlHeader.setBounds(0, 0, 960, 70);
        pnlContent.setBounds(0, 70, 960, 610);
        
        containerPanel.add(pnlHeader);
        containerPanel.add(pnlContent);
        
        // Set kích thước cho container
        containerPanel.setPreferredSize(new java.awt.Dimension(960, 680));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(nhacungcap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(nhacungcap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(nhacungcap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(nhacungcap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new nhacungcap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JPanel containerPanel;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
    // End of variables declaration//GEN-END:variables
}
