package screens.HoaDon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import DAO.HoaDonDAO;
import DTO.hoaDonDTO;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.sql.Timestamp;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Image;

public class HoaDonPanel extends javax.swing.JPanel {
    private HoaDonDAO hoaDonDAO;
    private SimpleDateFormat dateFormat;
    private DecimalFormat decimalFormat;

    public HoaDonPanel() {
        initComponents();
        setupUI();

        // Khởi tạo DAO và định dạng
        hoaDonDAO = new HoaDonDAO();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        decimalFormat = new DecimalFormat("#,###.##");

        setupSearchComponents();
        setupTable();
        // Tải dữ liệu hóa đơn từ cơ sở dữ liệu
        loadHoaDonData();
        setupListeners();
        setupTableListeners();
        setupIcons();
    }

    // Phương thức để thiết lập các biểu tượng
    private void setupIcons() {
        try {
            ImageIcon invoiceIcon = new ImageIcon(getClass().getResource("/icon_img/invoice.png"));
            Image scaledInvoiceIcon = invoiceIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            jLabel5.setIcon(new ImageIcon(scaledInvoiceIcon));
            jLabel5.setHorizontalTextPosition(JLabel.RIGHT);
            jLabel5.setIconTextGap(10);

            ImageIcon searchIcon = new ImageIcon(getClass().getResource("/icon_img/search.png"));
            Image scaledSearchIcon = searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            jButton30.setIcon(new ImageIcon(scaledSearchIcon));
            jButton30.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            jButton30.setIconTextGap(5);

            ImageIcon addIcon = new ImageIcon(getClass().getResource("/icon_img/add.png"));
            Image scaledAddIcon = addIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            jButton31.setIcon(new ImageIcon(scaledAddIcon));
            jButton31.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            jButton31.setIconTextGap(5);

            ImageIcon editIcon = new ImageIcon(getClass().getResource("/icon_img/edit.png"));
            Image scaledEditIcon = editIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            jButton32.setIcon(new ImageIcon(scaledEditIcon));
            jButton32.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            jButton32.setIconTextGap(5);

            ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/icon_img/delete.png"));
            Image scaledDeleteIcon = deleteIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            jButton33.setIcon(new ImageIcon(scaledDeleteIcon));
            jButton33.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            jButton33.setIconTextGap(5);

            ImageIcon exportIcon = new ImageIcon(getClass().getResource("/icon_img/export_icon.png"));
            Image scaledExportIcon = exportIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            jButton34.setIcon(new ImageIcon(scaledExportIcon));
            jButton34.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            jButton34.setIconTextGap(5);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Không thể tải biểu tượng: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupTable() {
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(50);  // STT
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(80);  // Mã HĐ
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);  // Mã SP
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(120); // Tên SP
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(80);  // Mã KH
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(120); // Tên KH
        jTable2.getColumnModel().getColumn(6).setPreferredWidth(60);  // Kích cỡ
        jTable2.getColumnModel().getColumn(7).setPreferredWidth(60);  // Màu sắc
        jTable2.getColumnModel().getColumn(8).setPreferredWidth(60);  // Số lượng
        jTable2.getColumnModel().getColumn(9).setPreferredWidth(80);  // Đơn giá
        jTable2.getColumnModel().getColumn(10).setPreferredWidth(80); // Thành tiền
        jTable2.getColumnModel().getColumn(11).setPreferredWidth(100); // Thời gian
        jTable2.getColumnModel().getColumn(12).setPreferredWidth(80); // Hình thức TT
        jTable2.getColumnModel().getColumn(13).setPreferredWidth(80); // Trạng Thái
        jTable2.getColumnModel().getColumn(14).setPreferredWidth(60); // Chi tiết

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        jTable2.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);  // STT
        jTable2.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);  // Mã HĐ
        jTable2.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);  // Mã SP
        jTable2.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);  // Mã KH
        jTable2.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);  // Kích cỡ
        jTable2.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);  // Màu sắc
        jTable2.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);  // Số lượng
        jTable2.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);  // Đơn giá
        jTable2.getColumnModel().getColumn(10).setCellRenderer(centerRenderer); // Thành tiền
        jTable2.getColumnModel().getColumn(13).setCellRenderer(centerRenderer); // Trạng Thái
        jTable2.getColumnModel().getColumn(14).setCellRenderer(centerRenderer); // Chi tiết

        jTable2.getColumnModel().getColumn(14).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(new Color(0, 0, 255));
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        jTable2.setRowHeight(25);
    }

    private void setupTableListeners() {
        jTable2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = jTable2.rowAtPoint(e.getPoint());
                int col = jTable2.columnAtPoint(e.getPoint());
                
                int lastColumnIndex = jTable2.getColumnCount() - 1;
                
                if (row >= 0 && col == lastColumnIndex) {
                    showChiTietHoaDon(row);
                }
            }
        });

        jTable2.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int col = jTable2.columnAtPoint(e.getPoint());
                if (col == jTable2.getColumnCount() - 1) {
                    jTable2.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    jTable2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    private void showChiTietHoaDon(int row) {
        try {
            String maHoaDon = getTableValueAsString(row, 1);
            hoaDonDTO hd = hoaDonDAO.getHoaDonByMa(maHoaDon); // Lấy trực tiếp từ cơ sở dữ liệu
            if (hd == null) {
                JOptionPane.showMessageDialog(this,
                    "Hóa đơn không tồn tại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            chiTietHoaDonDialog dialog = new chiTietHoaDonDialog(null, hd);
            dialog.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Có lỗi xảy ra khi hiển thị thông tin chi tiết: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getTableValueAsString(int row, int column) {
        Object value = jTable2.getValueAt(row, column);
        return value != null ? value.toString() : "";
    }

    private void loadHoaDonData() {
        try {
            List<hoaDonDTO> hoaDonList = hoaDonDAO.getAllHoaDon();
            updateTableData(hoaDonList);
            if (hoaDonList.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Không có dữ liệu hóa đơn trong cơ sở dữ liệu!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTableData(List<hoaDonDTO> hoaDonList) {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);

        int stt = 1;
        for (hoaDonDTO hd : hoaDonList) {
            String thoiGianStr = hd.getThoiGian() != null ? dateFormat.format(hd.getThoiGian()) : "";
            model.addRow(new Object[]{
                stt++,
                hd.getMaHoaDon(),
                hd.getMaSanPham(),
                hd.getTenSanPham(),
                hd.getMaKhachHang(),
                hd.getTenKhachHang(),
                hd.getKichCo(),
                hd.getMauSac(),
                hd.getSoLuong(),
                decimalFormat.format(hd.getDonGia()),
                decimalFormat.format(hd.getThanhTien()),
                thoiGianStr,
                hd.getHinhThucThanhToan(),
                hd.getTrangThai(),
                "Xem chi tiết"
            });
        }
    }

    // Cập nhật STT sau khi xóa dòng
    private void updateTableSTT() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(i + 1, i, 0); // Cập nhật cột STT
        }
    }

    private void searchHoaDon() {
        String keyword = jTextField1.getText().trim();
        String searchType = jComboBox1.getSelectedItem().toString();
        String soLuongTu = jTextFieldSoLuongTu.getText().trim();
        String soLuongDen = jTextFieldSoLuongDen.getText().trim();
        String thanhTienTu = jTextFieldThanhTienTu.getText().trim();
        String thanhTienDen = jTextFieldThanhTienDen.getText().trim();

        try {
            // Nếu không có điều kiện tìm kiếm, tải lại toàn bộ dữ liệu
            if (keyword.isEmpty() && soLuongTu.isEmpty() && soLuongDen.isEmpty() && thanhTienTu.isEmpty() && thanhTienDen.isEmpty()) {
                loadHoaDonData();
                return;
            }

            // Lấy danh sách hóa đơn từ DAO
            List<hoaDonDTO> searchResults = hoaDonDAO.searchHoaDon(keyword);

            // Lọc thêm theo số lượng và thành tiền nếu có
            if (!soLuongTu.isEmpty() || !soLuongDen.isEmpty() || !thanhTienTu.isEmpty() || !thanhTienDen.isEmpty()) {
                searchResults = searchResults.stream().filter(hd -> {
                    boolean match = true;

                    // Lọc theo số lượng
                    if (!soLuongTu.isEmpty()) {
                        try {
                            int soLuongMin = Integer.parseInt(soLuongTu);
                            match = match && hd.getSoLuong() >= soLuongMin;
                        } catch (NumberFormatException ex) {
                            match = false;
                        }
                    }
                    if (!soLuongDen.isEmpty()) {
                        try {
                            int soLuongMax = Integer.parseInt(soLuongDen);
                            match = match && hd.getSoLuong() <= soLuongMax;
                        } catch (NumberFormatException ex) {
                            match = false;
                        }
                    }

                    // Lọc theo thành tiền
                    if (!thanhTienTu.isEmpty()) {
                        try {
                            double thanhTienMin = Double.parseDouble(thanhTienTu);
                            match = match && hd.getThanhTien() >= thanhTienMin;
                        } catch (NumberFormatException ex) {
                            match = false;
                        }
                    }
                    if (!thanhTienDen.isEmpty()) {
                        try {
                            double thanhTienMax = Double.parseDouble(thanhTienDen);
                            match = match && hd.getThanhTien() <= thanhTienMax;
                        } catch (NumberFormatException ex) {
                            match = false;
                        }
                    }

                    return match;
                }).collect(java.util.stream.Collectors.toList());
            }

            updateTableData(searchResults);
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Không tìm thấy hóa đơn nào khớp với tiêu chí tìm kiếm",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tìm kiếm hóa đơn: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetSearchFields() {
        jTextField1.setText("");
        jComboBox1.setSelectedIndex(0);
        jTextFieldSoLuongTu.setText("");
        jTextFieldSoLuongDen.setText("");
        jTextFieldThanhTienTu.setText("");
        jTextFieldThanhTienDen.setText("");
        loadHoaDonData();
    }

    private void setupSearchComponents() {
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { 
            "Tất cả", "Mã hóa đơn", "Tên khách hàng", "Mã sản phẩm", "Tên sản phẩm" 
        }));

        jButton30.addActionListener(e -> searchHoaDon());

        jButtonReset.addActionListener(e -> resetSearchFields());

        jTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchHoaDon();
                }
            }
        });

        jTextFieldSoLuongTu.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchHoaDon();
                }
            }
        });

        jTextFieldSoLuongDen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchHoaDon();
                }
            }
        });

        jTextFieldThanhTienTu.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchHoaDon();
                }
            }
        });

        jTextFieldThanhTienDen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchHoaDon();
                }
            }
        });
    }

    private void setupListeners() {
        // Nút Thêm
        jButton31.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog();
                dialog.setTitle("Thêm Hóa Đơn Mới");
                dialog.setModal(true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                
                themHoaDonPanel themPanel = new themHoaDonPanel();
                dialog.add(themPanel);
                
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                
                loadHoaDonData();
            }
        });

        // Nút Sửa
        jButton32.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jTable2.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        String maHoaDon = getTableValueAsString(selectedRow, 1);
                        hoaDonDTO hd = hoaDonDAO.getHoaDonByMa(maHoaDon);
                        if (hd == null) {
                            JOptionPane.showMessageDialog(null,
                                "Hóa đơn không tồn tại!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        JDialog dialog = new JDialog();
                        dialog.setTitle("Sửa Thông Tin Hóa Đơn");
                        dialog.setModal(true);
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        
                        suaHoaDonPanel suaPanel = new suaHoaDonPanel(dialog, hd);
                        dialog.add(suaPanel);
                        
                        dialog.pack();
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                        
                        loadHoaDonData();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                            "Có lỗi xảy ra khi hiển thị form sửa thông tin: " + ex.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Vui lòng chọn hóa đơn cần sửa",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Nút Xóa
        jButton33.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jTable2.getSelectedRow();
                if (selectedRow >= 0) {
                    // Vô hiệu hóa nút Xóa để tránh nhấp chuột lặp lại
                    jButton33.setEnabled(false);

                    try {
                        String maHoaDon = getTableValueAsString(selectedRow, 1);
                        hoaDonDTO hd = hoaDonDAO.getHoaDonByMa(maHoaDon);
                        if (hd == null) {
                            JOptionPane.showMessageDialog(null,
                                "Hóa đơn không tồn tại!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Gọi xoaHoaDonPanel để xóa
                        new xoaHoaDonPanel(hd);

                        // Xóa dòng khỏi bảng thay vì tải lại toàn bộ dữ liệu
                        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                        model.removeRow(selectedRow);
                        updateTableSTT(); // Cập nhật lại STT
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                            "Lỗi khi xóa hóa đơn: " + ex.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    } finally {
                        // Kích hoạt lại nút Xóa
                        jButton33.setEnabled(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Vui lòng chọn hóa đơn cần xóa",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Nút Xuất file
        jButton34.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Chức năng xuất file đang được phát triển");
            }
        });
    }

    public javax.swing.JPanel getHoaDonPanel() {
        return containerPanel;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        containerPanel = new javax.swing.JPanel();
        containerPanel.setPreferredSize(new java.awt.Dimension(1000, 700));
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jButton30 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabelSoLuong = new javax.swing.JLabel();
        jTextFieldSoLuongTu = new javax.swing.JTextField();
        jTextFieldSoLuongDen = new javax.swing.JTextField();
        jLabelSoLuongTu = new javax.swing.JLabel();
        jLabelSoLuongDen = new javax.swing.JLabel();
        jLabelThanhTien = new javax.swing.JLabel();
        jTextFieldThanhTienTu = new javax.swing.JTextField();
        jTextFieldThanhTienDen = new javax.swing.JTextField();
        jLabelThanhTienTu = new javax.swing.JLabel();
        jLabelThanhTienDen = new javax.swing.JLabel();
        jButtonReset = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton34 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(960, 680));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        containerPanel.setPreferredSize(new java.awt.Dimension(960, 680));
        containerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader.setBackground(new java.awt.Color(12, 150, 156));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jLabel5.setText("QUẢN LÝ HÓA ĐƠN");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(383, 383, 383)
                .addComponent(jLabel5)
                .addContainerGap(402, Short.MAX_VALUE))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(18, 18, 18))
        );

        containerPanel.add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 70));

        pnlContent.setBackground(new java.awt.Color(107, 163, 190));

        jPanel33.setBackground(new java.awt.Color(107, 163, 190));
        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm"));
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel1.setText("Tìm kiếm:");
        jPanel33.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel33.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 25, 140, 30));
        jPanel33.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 25, 140, 30));

        jLabelThanhTien.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelThanhTien.setText("Thành tiền:");
        jPanel33.add(jLabelThanhTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 65, -1, -1));

        jLabelThanhTienTu.setFont(new java.awt.Font("Segoe UI", 0, 12));
        jLabelThanhTienTu.setText("Từ:");
        jPanel33.add(jLabelThanhTienTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 65, -1, 30));
        jPanel33.add(jTextFieldThanhTienTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 65, 60, 30));

        jLabelThanhTienDen.setFont(new java.awt.Font("Segoe UI", 0, 12));
        jLabelThanhTienDen.setText("Đến:");
        jPanel33.add(jLabelThanhTienDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 65, -1, 30));
        jPanel33.add(jTextFieldThanhTienDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 65, 60, 30));

        jLabelSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelSoLuong.setText("Số lượng:");
        jPanel33.add(jLabelSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 65, -1, -1));

        jLabelSoLuongTu.setFont(new java.awt.Font("Segoe UI", 0, 12));
        jLabelSoLuongTu.setText("Từ:");
        jPanel33.add(jLabelSoLuongTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 65, -1, 30));
        jPanel33.add(jTextFieldSoLuongTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 65, 60, 30));

        jLabelSoLuongDen.setFont(new java.awt.Font("Segoe UI", 0, 12));
        jLabelSoLuongDen.setText("Đến:");
        jPanel33.add(jLabelSoLuongDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 65, -1, 30));
        jPanel33.add(jTextFieldSoLuongDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 65, 60, 30));

        jButton30.setText("Tìm kiếm");
        jPanel33.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 25, 110, 30));

        jButtonReset.setText("Reset");
        jPanel33.add(jButtonReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 65, 110, 30));

        jPanel17.setBackground(new java.awt.Color(107, 163, 190));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chỉnh sửa"));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton31.setText("Thêm ");
        jPanel17.add(jButton31, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, -1, 49));

        jButton32.setText("Sửa");
        jPanel17.add(jButton32, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, -1, 49));

        jButton33.setText("Xóa");
        jPanel17.add(jButton33, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 20, -1, 49));

        jPanel18.setBackground(new java.awt.Color(107, 163, 190));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng thông tin"));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "STT", "Mã HD", "Mã SP", "Tên SP", "Mã KH", "Tên KH", "Kích cỡ", "Màu sắc", "Số lượng", "Đơn giá", "Thành tiền", "Thời gian", "Hình thức TT", "Trạng Thái", "Chi tiết"
            }
        ) {
            boolean[] canEdit = new boolean[] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable2.setShowGrid(true);
        jScrollPane2.setViewportView(jTable2);

        jPanel18.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 920, 290));

        jButton34.setText("Xuất file xuất hàng");

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 961, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlContentLayout.createSequentialGroup()
                            .addGap(325, 325, 325)
                            .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlContentLayout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 961, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 961, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        containerPanel.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1000, 630));

        add(containerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));
    }

    private void setupUI() {
        jPanel33.setPreferredSize(new java.awt.Dimension(961, 120));
        jPanel17.setPreferredSize(new java.awt.Dimension(961, 90));
        jPanel18.setPreferredSize(new java.awt.Dimension(961, 352));
    }

    // Khai báo biến
    private javax.swing.JPanel containerPanel;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelSoLuong;
    private javax.swing.JLabel jLabelSoLuongTu;
    private javax.swing.JLabel jLabelSoLuongDen;
    private javax.swing.JLabel jLabelThanhTien;
    private javax.swing.JLabel jLabelThanhTienTu;
    private javax.swing.JLabel jLabelThanhTienDen;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextFieldSoLuongTu;
    private javax.swing.JTextField jTextFieldSoLuongDen;
    private javax.swing.JTextField jTextFieldThanhTienTu;
    private javax.swing.JTextField jTextFieldThanhTienDen;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
}