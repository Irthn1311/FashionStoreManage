package utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.text.DecimalFormat;
import java.sql.Timestamp; // For HoaDonDTO
import java.math.BigDecimal; // For NhanVienDTO MucLuong

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;

// DTOs
import DTO.khachHangDTO;
import DTO.sanPhamDTO; // Using sanPhamDTO consistently for products
import DTO.hoaDonDTO;
import DTO.PhieuNhapDTO;
import DTO.khuyenMaiDTO;
import DTO.nhanVienDTO;
import DTO.nhaCungCapDTO;
import DTO.taiKhoanDTO; // For NhanVien

// BUS/Service layers
import BUS.KhachHangBUS;
import BUS.ProductService;
import BUS.HoaDonBUS;
import DAO.HoaDonDAO;
import BUS.PhieuNhapBUS;
import BUS.KhuyenMaiService;
import BUS.NhanVienBUS;
import DAO.NhanVienDAO;
import DAO.TaiKhoanDAO; // For NhanVien import
import BUS.NhaCungCapBUS;
import DAO.LoaiSanPhamDAO; // For fetching all products (returns sanPhamDTO)
import DAO.KhuyenMaiDAO; // For fetching all promotions
import DAO.PhieuNhapDAO; // For fetching all phieu nhap
import DAO.KhachHangDAO; // For fetching all khach hang
import DAO.NhaCungCapDAO; // For fetching all nha cung cap


public class FileUtils {

    private static SimpleDateFormat genericDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat genericTimestampFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static DecimalFormat genericDecimalFormat = new DecimalFormat("#,###.##");

    private static String[] parseCSVLine(String line) {
        List<String> values = new java.util.ArrayList<>();
        StringBuilder currentValue = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    currentValue.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                values.add(currentValue.toString());
                currentValue = new StringBuilder();
            } else {
                currentValue.append(c);
            }
        }
        values.add(currentValue.toString());
        return values.toArray(new String[0]);
    }

    //<editor-fold defaultstate="collapsed" desc="Generic Export/Import (Old methods - to be replaced or removed if not used elsewhere)">
    public static void exportToExcel(JTable table, String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File(title + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet(title);
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < model.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(model.getColumnName(i));
                }
                for (int i = 0; i < model.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Cell cell = row.createCell(j);
                        Object value = model.getValueAt(i, j);
                        if (value != null) {
                            if (value instanceof Number) {
                                cell.setCellValue(((Number) value).doubleValue());
                            } else {
                            cell.setCellValue(value.toString());
                        }
                    }
                }
                }
                for (int i = 0; i < model.getColumnCount(); i++) {
                    sheet.autoSizeColumn(i);
                }
                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void exportToWord(JTable table, String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Word");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Word files (*.docx)", "docx"));
        fileChooser.setSelectedFile(new File(title + ".docx"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".docx")) {
                filePath += ".docx";
            }

            try (XWPFDocument document = new XWPFDocument()) {
                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setBold(true);
                titleRun.setFontSize(16);
                titleRun.setText(title);
                titleRun.addBreak();

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    XWPFParagraph rowParagraph = document.createParagraph();
                    StringBuilder rowText = new StringBuilder();
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        rowText.append(model.getColumnName(j)).append(": ");
                        Object value = model.getValueAt(i, j);
                        rowText.append(value == null ? "" : value.toString());
                        if (j < model.getColumnCount() - 1) {
                            rowText.append(" | ");
                    }
                    }
                    XWPFRun rowRun = rowParagraph.createRun();
                    rowRun.setText(rowText.toString());
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    document.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Word thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Word: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void exportToCSV(JTable table, String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file CSV");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV files", "csv"));
        fileChooser.setSelectedFile(new File(title + ".csv"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                filePath += ".csv";
            }

            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8")) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                writer.write("\uFEFF"); // BOM for UTF-8

                for (int i = 0; i < model.getColumnCount(); i++) {
                    writer.write("\"" + model.getColumnName(i) + "\"");
                    if (i < model.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.write("\n");

                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        String cellValue = value == null ? "" : value.toString();
                        cellValue = "\"" + cellValue.replace("\"", "\"\"") + "\"";
                        writer.write(cellValue);
                        if (j < model.getColumnCount() - 1) {
                            writer.write(",");
                        }
                    }
                    writer.write("\n");
                }
                JOptionPane.showMessageDialog(null, "Xuất file CSV thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file CSV: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void importFromFile(JTable table) { // Generic Excel import, might be phased out
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file để import");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files", "xlsx", "xls"));

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (Workbook workbook = WorkbookFactory.create(selectedFile)) {
                Sheet sheet = workbook.getSheetAt(0);
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0); // Clear existing data

                // Skip header row, iterate data rows
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        Object[] rowData = new Object[model.getColumnCount()];
                        // Assuming the last column in JTable is always "Xem chi tiết" or similar action button
                        int dataColumnCount = model.getColumnCount() -1; 
                        for (int j = 0; j < dataColumnCount; j++) {
                            Cell cell = row.getCell(j);
                            if (cell != null) {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        rowData[j] = cell.getStringCellValue();
                                        break;
                                    case NUMERIC:
                                        if (DateUtil.isCellDateFormatted(cell)) {
                                            rowData[j] = cell.getDateCellValue();
                                        } else {
                                        rowData[j] = cell.getNumericCellValue();
                                        }
                                        break;
                                    case BOOLEAN:
                                        rowData[j] = cell.getBooleanCellValue();
                                        break;
                                    case FORMULA:
                                        rowData[j] = cell.getCellFormula(); // Or evaluate formula if needed
                                        break;
                                    default:
                                        rowData[j] = "";
                                }
                            }
                        }
                        // If JTable has an action column like "Xem chi tiết", add placeholder
                        if(model.getColumnCount() > dataColumnCount) {
                           rowData[dataColumnCount] = model.getColumnName(dataColumnCount); // Or a default action string
                        }
                        model.addRow(rowData);
                    }
                }
                JOptionPane.showMessageDialog(null, "Import dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi import dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Specific Show Export Options">
    public static void showExportOptionsForLoaiSanPham(JTable table, String title) {
        String[] options = { "Excel (.xlsx)", "Word (.docx)" };
        int choice = JOptionPane.showOptionDialog(null, "Chọn định dạng file xuất cho Sản Phẩm:", "Xuất File Sản Phẩm",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            exportToLoaiSanPhamExcel(table, title);
        } else if (choice == 1) {
            exportToLoaiSanPhamWord(table, title);
        }
    }

    public static void showExportOptionsForHoaDon(JTable table, String title) {
        String[] options = { "Excel (.xlsx)", "Word (.docx)" };
        int choice = JOptionPane.showOptionDialog(null, "Chọn định dạng file xuất cho Hóa Đơn:", "Xuất File Hóa Đơn",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            exportToHoaDonExcel(table, title);
        } else if (choice == 1) {
            exportToHoaDonWord(table, title);
        }
    }
    
    public static void showExportOptionsForPhieuNhap(JTable table, String title) {
        String[] options = { "Excel (.xlsx)", "Word (.docx)" };
        int choice = JOptionPane.showOptionDialog(null, "Chọn định dạng file xuất cho Phiếu Nhập:", "Xuất File Phiếu Nhập",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            exportToPhieuNhapExcel(table, title);
        } else if (choice == 1) {
            exportToPhieuNhapWord(table, title);
        }
    }

    public static void showExportOptionsForKhuyenMai(JTable table, String title) {
        String[] options = { "Excel (.xlsx)", "Word (.docx)" };
        int choice = JOptionPane.showOptionDialog(null, "Chọn định dạng file xuất cho Khuyến Mãi:", "Xuất File Khuyến Mãi",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            exportToKhuyenMaiExcel(table, title);
        } else if (choice == 1) {
            exportToKhuyenMaiWord(table, title);
        }
    }

    public static void showExportOptionsForNhanVien(JTable table, String title) {
        String[] options = { "Excel (.xlsx)", "Word (.docx)" };
        int choice = JOptionPane.showOptionDialog(null, "Chọn định dạng file xuất cho Nhân Viên:", "Xuất File Nhân Viên",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            exportToNhanVienExcel(table, title);
        } else if (choice == 1) {
            exportToNhanVienWord(table, title);
        }
    }

    public static void showExportOptionsForNhaCungCap(JTable table, String title) {
        String[] options = { "Excel (.xlsx)", "Word (.docx)" };
        int choice = JOptionPane.showOptionDialog(null, "Chọn định dạng file xuất cho Nhà Cung Cấp:", "Xuất File Nhà Cung Cấp",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            exportToNhaCungCapExcel(table, title);
        } else if (choice == 1) {
            exportToNhaCungCapWord(table, title);
        }
    }
    
    public static void showExportOptionsForKhachHang(JTable table, String title) {
        String[] options = { "Excel (.xlsx)", "Word (.docx)" };
        int choice = JOptionPane.showOptionDialog(null, "Chọn định dạng file xuất cho Khách Hàng:", "Xuất File Khách Hàng",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            exportToKhachHangExcel(table, title);
        } else if (choice == 1) {
            exportToKhachHangWord(table, title);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="LoaiSanPham (Product) Specific File Operations">
    public static void exportToLoaiSanPhamExcel(JTable table, String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel Sản Phẩm");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File(title + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            LoaiSanPhamDAO sanPhamDAO = new LoaiSanPhamDAO(); 
            List<sanPhamDTO> sanPhamList = sanPhamDAO.getAllSanPham(); 

            String[] headers = {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Mã Nhà Cung Cấp", "Mã Danh Mục", "Màu Sắc",
                "Size", "Số Lượng Tồn Kho", "Giá Bán", "ImgURL", "Trạng Thái" // Mã Kho Hàng removed for now
            };

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet(title);
                
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }

                int rowNum = 1;
                for (sanPhamDTO sp : sanPhamList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(sp.getMaSanPham());
                    row.createCell(1).setCellValue(sp.getTenSanPham());
                    row.createCell(2).setCellValue(sp.getMaNhaCungCap());
                    row.createCell(3).setCellValue(sp.getMaDanhMuc());
                    row.createCell(4).setCellValue(sp.getMauSac());
                    row.createCell(5).setCellValue(sp.getSize());
                    row.createCell(6).setCellValue(sp.getSoLuongTonKho());
                    row.createCell(7).setCellValue(sp.getGiaBan());
                    row.createCell(8).setCellValue(sp.getImgURL());
                    row.createCell(9).setCellValue(sp.getTrangThai());
                    // MaKhoHang line removed: row.createCell(10).setCellValue(sp.getMaKhoHang()); 
                }

                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Excel Sản Phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel Sản Phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void exportToLoaiSanPhamWord(JTable table, String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Word Sản Phẩm");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Word files (*.docx)", "docx"));
        fileChooser.setSelectedFile(new File(title + ".docx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".docx")) {
                filePath += ".docx";
            }

            LoaiSanPhamDAO sanPhamDAO = new LoaiSanPhamDAO();
            List<sanPhamDTO> sanPhamList = sanPhamDAO.getAllSanPham(); 

            try (XWPFDocument document = new XWPFDocument()) {
                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setBold(true);
                titleRun.setFontSize(16);
                titleRun.setText("DANH SÁCH SẢN PHẨM");
                titleRun.addBreak();

                for (sanPhamDTO sp : sanPhamList) {
                    addParagraph(document, "Mã Sản Phẩm: " + (sp.getMaSanPham() != null ? sp.getMaSanPham() : ""));
                    addParagraph(document, "Tên Sản Phẩm: " + (sp.getTenSanPham() != null ? sp.getTenSanPham() : ""));
                    addParagraph(document, "Mã Nhà Cung Cấp: " + (sp.getMaNhaCungCap() != null ? sp.getMaNhaCungCap() : ""));
                    addParagraph(document, "Mã Danh Mục: " + (sp.getMaDanhMuc() != null ? sp.getMaDanhMuc() : ""));
                    addParagraph(document, "Màu Sắc: " + (sp.getMauSac() != null ? sp.getMauSac() : ""));
                    addParagraph(document, "Size: " + (sp.getSize() != null ? sp.getSize() : ""));
                    addParagraph(document, "Số Lượng Tồn Kho: " + sp.getSoLuongTonKho());
                    addParagraph(document, "Giá Bán: " + sp.getGiaBan());
                    addParagraph(document, "ImgURL: " + (sp.getImgURL() != null ? sp.getImgURL() : ""));
                    addParagraph(document, "Trạng Thái: " + (sp.getTrangThai() != null ? sp.getTrangThai() : ""));
                    // MaKhoHang line removed: addParagraph(document, "Mã Kho Hàng: " + (sp.getMaKhoHang() != null ? sp.getMaKhoHang() : ""));
                    document.createParagraph().createRun().addBreak();
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    document.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Word Sản Phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Word Sản Phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void importFromExcelForLoaiSanPham(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel Sản phẩm để import (*.xlsx)");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            LoaiSanPhamDAO sanPhamDAO = new LoaiSanPhamDAO(); 
                int successCount = 0;
                int failCount = 0;
            StringBuilder errorMessages = new StringBuilder("Chi tiết lỗi/bỏ qua:\n");

            try (Workbook workbook = new XSSFWorkbook(new FileInputStream(selectedFile))) {
                Sheet sheet = workbook.getSheetAt(0);
                List<String> existingMaSPs = sanPhamDAO.getAllSanPham().stream()
                                               .map(sanPhamDTO::getMaSanPham)
                                               .collect(Collectors.toList());

                // Expected headers: Mã SP(0), Tên SP(1), Mã NCC(2), Mã Danh Mục(3), Màu sắc(4), Size(5), Số lượng(6), Đơn giá(7), ImgURL(8), Trạng Thái(9)
                // Mã Kho Hàng (column 10) is not processed here until sanPhamDTO is updated.
                for (int i = 1; i <= sheet.getLastRowNum(); i++) { 
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    try {
                        String maSanPham = getCellStringValue(row.getCell(0)); 
                        if (maSanPham == null || maSanPham.trim().isEmpty()) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Mã sản phẩm trống.\n");
                            failCount++;
                            continue;
                        }
                        if (existingMaSPs.contains(maSanPham)) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Mã sản phẩm '").append(maSanPham).append("' đã tồn tại.\n");
                                failCount++;
                                continue;
                            }

                        sanPhamDTO sp = new sanPhamDTO();
                        sp.setMaSanPham(maSanPham);
                        sp.setTenSanPham(getCellStringValue(row.getCell(1)));
                        sp.setMaNhaCungCap(getCellStringValue(row.getCell(2)));
                        sp.setMaDanhMuc(getCellStringValue(row.getCell(3))); 
                        sp.setMauSac(getCellStringValue(row.getCell(4)));
                        sp.setSize(getCellStringValue(row.getCell(5)));
                        sp.setSoLuongTonKho((int) getCellNumericValue(row.getCell(6)));
                        sp.setGiaBan(getCellNumericValue(row.getCell(7)));
                        sp.setImgURL(getCellStringValue(row.getCell(8)));
                        sp.setTrangThai(getCellStringValue(row.getCell(9)));
                        
                        // MaKhoHang processing removed: sp.setMaKhoHang(getCellStringValue(row.getCell(10)));
                        // String maKhoHangValue = getCellStringValue(row.getCell(10));
                        // if (maKhoHangValue != null && !maKhoHangValue.trim().isEmpty()) {
                        //    // If sanPhamDTO is updated, uncomment and use: sp.setMaKhoHang(maKhoHangValue);
                        //    errorMessages.append("Dòng ").append(i + 1).append(": Mã kho hàng '").append(maKhoHangValue).append("' cho SP '").append(maSanPham).append("' không được import vì DTO chưa hỗ trợ.\n");
                        // }
                                                
                        if (sanPhamDAO.themLoaiSanPham(sp)) { 
                                successCount++;
                            existingMaSPs.add(maSanPham);
                            } else {
                            errorMessages.append("Dòng ").append(i + 1).append(": Không thể thêm sản phẩm '").append(maSanPham).append("'. Kiểm tra dữ liệu (VD: Mã NCC, Mã Danh Mục có tồn tại không?).\n");
                                failCount++;
                            }
                    } catch (Exception e) {
                        errorMessages.append("Dòng ").append(i + 1).append(": Lỗi xử lý - ").append(e.getMessage()).append("\n");
                            failCount++;
                    }
                }
                String message = String.format("Import hoàn tất!\nThành công: %d\nThất bại/Bỏ qua: %d", successCount, failCount);
                if (failCount > 0 || errorMessages.length() > "Chi tiết lỗi/bỏ qua:\n".length()) {
                    JTextArea textArea = new JTextArea(errorMessages.toString());
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
                    JOptionPane.showMessageDialog(null, scrollPane, "Kết quả Import Sản Phẩm", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, message, "Kết quả Import Sản Phẩm", JOptionPane.INFORMATION_MESSAGE);
                }

                    } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi import file Excel Sản phẩm: " + e.getMessage(), "Lỗi Import", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="HoaDon Specific File Operations">
    public static void exportToHoaDonExcel(JTable table, String title) {
        // exportToExcel(table, title);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel Hóa Đơn");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File(title + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            HoaDonDAO hoaDonDAO = new HoaDonDAO();
            List<hoaDonDTO> hoaDonList = hoaDonDAO.getAllHoaDon();

            String[] headers = {
                "Mã Hóa Đơn", "Mã Sản Phẩm", "Tên Sản Phẩm", "Kích Cỡ", "Màu Sắc", "Số Lượng",
                "Mã Khách Hàng", "Tên Khách Hàng", "Thành Tiền", "Đơn Giá",
                "Hình Thức Thanh Toán", "Thời Gian", "Trạng Thái"
            };

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet(title);
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }

                int rowNum = 1;
                for (hoaDonDTO hd : hoaDonList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(hd.getMaHoaDon());
                    row.createCell(1).setCellValue(hd.getMaSanPham());
                    row.createCell(2).setCellValue(hd.getTenSanPham());
                    row.createCell(3).setCellValue(hd.getKichCo());
                    row.createCell(4).setCellValue(hd.getMauSac());
                    row.createCell(5).setCellValue(hd.getSoLuong());
                    row.createCell(6).setCellValue(hd.getMaKhachHang());
                    row.createCell(7).setCellValue(hd.getTenKhachHang());
                    row.createCell(8).setCellValue(hd.getThanhTien());
                    row.createCell(9).setCellValue(hd.getDonGia());
                    row.createCell(10).setCellValue(hd.getHinhThucThanhToan());
                    row.createCell(11).setCellValue(hd.getThoiGian() != null ? genericTimestampFormat.format(hd.getThoiGian()) : "");
                    row.createCell(12).setCellValue(hd.getTrangThai());
                }

                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Excel Hóa Đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel Hóa Đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void exportToHoaDonWord(JTable table, String title) {
        // exportToWord(table, title);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Word Hóa Đơn");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Word files (*.docx)", "docx"));
        fileChooser.setSelectedFile(new File(title + ".docx"));
        
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".docx")) {
                filePath += ".docx";
            }

            HoaDonDAO hoaDonDAO = new HoaDonDAO();
            List<hoaDonDTO> hoaDonList = hoaDonDAO.getAllHoaDon();

            try (XWPFDocument document = new XWPFDocument()) {
                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setBold(true);
                titleRun.setFontSize(16);
                titleRun.setText("DANH SÁCH HÓA ĐƠN");
                titleRun.addBreak();

                for (hoaDonDTO hd : hoaDonList) {
                    addParagraph(document, "Mã Hóa Đơn: " + hd.getMaHoaDon());
                    addParagraph(document, "Mã Sản Phẩm: " + hd.getMaSanPham());
                    addParagraph(document, "Tên Sản Phẩm: " + hd.getTenSanPham());
                    addParagraph(document, "Kích Cỡ: " + hd.getKichCo());
                    addParagraph(document, "Màu Sắc: " + hd.getMauSac());
                    addParagraph(document, "Số Lượng: " + hd.getSoLuong());
                    addParagraph(document, "Mã Khách Hàng: " + hd.getMaKhachHang());
                    addParagraph(document, "Tên Khách Hàng: " + hd.getTenKhachHang());
                    addParagraph(document, "Thành Tiền: " + genericDecimalFormat.format(hd.getThanhTien()));
                    addParagraph(document, "Đơn Giá: " + genericDecimalFormat.format(hd.getDonGia()));
                    addParagraph(document, "Hình Thức Thanh Toán: " + hd.getHinhThucThanhToan());
                    addParagraph(document, "Thời Gian: " + (hd.getThoiGian() != null ? genericTimestampFormat.format(hd.getThoiGian()) : ""));
                    addParagraph(document, "Trạng Thái: " + hd.getTrangThai());
                    document.createParagraph().createRun().addBreak();
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    document.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Word Hóa Đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Word Hóa Đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void importFromExcelForHoaDon(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel Hóa Đơn để import (*.xlsx)");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            HoaDonDAO hoaDonDAO = new HoaDonDAO(); 
            int successCount = 0;
            int failCount = 0;
            StringBuilder errorMessages = new StringBuilder("Chi tiết lỗi/bỏ qua:\n");

            try (Workbook workbook = new XSSFWorkbook(new FileInputStream(selectedFile))) {
                Sheet sheet = workbook.getSheetAt(0);
                // Headers: Mã HD(0), Mã SP(1), Tên SP(2), Kích Cỡ(3), Màu Sắc(4), Số Lượng(5), Mã KH(6), Tên KH(7), Thành Tiền(8), Đơn Giá(9), Hình thức TT(10), Thời Gian(11), Trạng Thái(12)
                List<String> existingMaHDs = hoaDonDAO.getAllHoaDon().stream()
                                               .map(hoaDonDTO::getMaHoaDon)
                                               .collect(Collectors.toList());

                for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header row
                    Row row = sheet.getRow(i);
                    if (row == null) continue;
                    try {
                        String maHoaDon = getCellStringValue(row.getCell(0)); // Index changed
                         if (maHoaDon == null || maHoaDon.trim().isEmpty()) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Mã hóa đơn trống.\n");
                        failCount++;
                            continue;
                        }
                        if (existingMaHDs.contains(maHoaDon)) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Mã hóa đơn '").append(maHoaDon).append("' đã tồn tại.\n");
                            failCount++;
                            continue;
                        }

                        hoaDonDTO hd = new hoaDonDTO();
                        hd.setMaHoaDon(maHoaDon);
                        hd.setMaSanPham(getCellStringValue(row.getCell(1)));
                        hd.setTenSanPham(getCellStringValue(row.getCell(2)));
                        hd.setKichCo(getCellStringValue(row.getCell(3)));
                        hd.setMauSac(getCellStringValue(row.getCell(4)));
                        hd.setSoLuong((int) getCellNumericValue(row.getCell(5)));
                        hd.setMaKhachHang(getCellStringValue(row.getCell(6)));
                        hd.setTenKhachHang(getCellStringValue(row.getCell(7)));
                        hd.setThanhTien(getCellNumericValue(row.getCell(8)));
                        hd.setDonGia(getCellNumericValue(row.getCell(9)));
                        hd.setHinhThucThanhToan(getCellStringValue(row.getCell(10)));
                        
                        Cell thoiGianCell = row.getCell(11);
                        if (thoiGianCell != null) {
                            if (thoiGianCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(thoiGianCell)) {
                                hd.setThoiGian(new Timestamp(thoiGianCell.getDateCellValue().getTime()));
                } else {
                                String thoiGianStr = getCellStringValue(thoiGianCell);
                                if (thoiGianStr != null && !thoiGianStr.trim().isEmpty()) {
                                    try {
                                        Date parsedDate = null;
                                        try { // Prioritize yyyy-MM-dd HH:mm:ss as it's more common in DB exports
                                             parsedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(thoiGianStr);
                                        } catch (java.text.ParseException e1) {
                                            try {
                                                 parsedDate = genericTimestampFormat.parse(thoiGianStr); // "dd/MM/yyyy HH:mm:ss"
                                            } catch (java.text.ParseException e2) {
                                                try {
                                                     parsedDate = genericDateFormat.parse(thoiGianStr); // "dd/MM/yyyy" -> time will be 00:00:00
                                                } catch (java.text.ParseException e3) {
                                                     try { // Default Java Date.toString() format
                                                        parsedDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.ENGLISH).parse(thoiGianStr);
                                                     } catch (java.text.ParseException e4){
                                                        errorMessages.append("Dòng ").append(i + 1).append(": Lỗi định dạng Thời gian '").append(thoiGianStr).append("'. Không thể phân tích.\n");
                                                        hd.setThoiGian(null); // Set to null explicitly on parse failure
                                                     }
                                                }
                                            }
                                        }
                                        if (parsedDate != null) {
                                             hd.setThoiGian(new Timestamp(parsedDate.getTime()));
                                        } else if (hd.getThoiGian() == null && !errorMessages.toString().contains("Thời gian '" + thoiGianStr)) {
                                            // Ensure it's null if not set by try-catch and no error appended for it
                                                 hd.setThoiGian(null);
                                            }
                                    } catch (Exception e) { 
                                        errorMessages.append("Dòng ").append(i + 1).append(": Lỗi xử lý Thời gian '").append(thoiGianStr).append("': ").append(e.getMessage()).append("\n");
                                        hd.setThoiGian(null);
                                    }
                                } else {
                                    hd.setThoiGian(null); // Empty string in cell
                                }
                            }
                        } else {
                            hd.setThoiGian(null); // Null cell
                        }

                        hd.setTrangThai(getCellStringValue(row.getCell(12)));
                        
                        if (hoaDonDAO.addHoaDon(hd)) { 
                            successCount++;
                            existingMaHDs.add(maHoaDon);
                } else {
                            errorMessages.append("Dòng ").append(i + 1).append(": Không thể thêm hóa đơn '").append(maHoaDon).append("'. Kiểm tra dữ liệu (VD: Mã SP, Mã KH có tồn tại không?).\n");
                            failCount++;
                        }
                    } catch (Exception e) {
                        errorMessages.append("Dòng ").append(i + 1).append(": Lỗi xử lý - ").append(e.getMessage()).append("\n");
                        failCount++;
                    }
                }
                 String message = String.format("Import hoàn tất!\nThành công: %d\nThất bại/Bỏ qua: %d", successCount, failCount);
                if (failCount > 0 || errorMessages.length() > "Chi tiết lỗi/bỏ qua:\n".length()) {
                    JTextArea textArea = new JTextArea(errorMessages.toString());
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
                    JOptionPane.showMessageDialog(null, scrollPane, "Kết quả Import Hóa Đơn", JOptionPane.WARNING_MESSAGE);
            } else {
                    JOptionPane.showMessageDialog(null, message, "Kết quả Import Hóa Đơn", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi import file Excel Hóa Đơn: " + e.getMessage(), "Lỗi Import", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="PhieuNhap Specific File Operations">
    public static void exportToPhieuNhapExcel(JTable table, String title) {
            // exportToExcel(table, title);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel Phiếu Nhập");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File(title + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO(); // Or PhieuNhapBUS if it has getAll
            List<PhieuNhapDTO> phieuNhapList = phieuNhapDAO.getAll();

            String[] headers = {
                "Mã Phiếu Nhập", "Mã Nhà Cung Cấp", "Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng",
                "Thời Gian", "Đơn Giá", "Thành Tiền", "Hình Thức Thanh Toán", "Trạng Thái"
            };
            // Order adjusted: MaPN, MaNCC, MaSP, TenSP, SoLuong, ThoiGian, DonGia, ThanhTien, HTTT, TrangThai

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet(title);
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }

                int rowNum = 1;
                for (PhieuNhapDTO pn : phieuNhapList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(pn.getMaPhieuNhap());
                    row.createCell(1).setCellValue(pn.getMaNhaCungCap());
                    row.createCell(2).setCellValue(pn.getMaSanPham());
                    row.createCell(3).setCellValue(pn.getTenSanPham());
                    row.createCell(4).setCellValue(pn.getSoLuong());
                    row.createCell(5).setCellValue(pn.getThoiGian() != null ? genericTimestampFormat.format(pn.getThoiGian()) : "");
                    row.createCell(6).setCellValue(pn.getDonGia());
                    row.createCell(7).setCellValue(pn.getThanhTien());
                    row.createCell(8).setCellValue(pn.getHinhThucThanhToan());
                    row.createCell(9).setCellValue(pn.getTrangThai());
                }

                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Excel Phiếu Nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel Phiếu Nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void exportToPhieuNhapWord(JTable table, String title) {
            // exportToWord(table, title);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Word Phiếu Nhập");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Word files (*.docx)", "docx"));
        fileChooser.setSelectedFile(new File(title + ".docx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".docx")) {
                filePath += ".docx";
            }
            
            PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO();
            List<PhieuNhapDTO> phieuNhapList = phieuNhapDAO.getAll();

            try (XWPFDocument document = new XWPFDocument()) {
                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setBold(true);
                titleRun.setFontSize(16);
                titleRun.setText("DANH SÁCH PHIẾU NHẬP");
                titleRun.addBreak();

                for (PhieuNhapDTO pn : phieuNhapList) {
                    addParagraph(document, "Mã Phiếu Nhập: " + pn.getMaPhieuNhap());
                    addParagraph(document, "Mã Nhà Cung Cấp: " + pn.getMaNhaCungCap());
                    addParagraph(document, "Mã Sản Phẩm: " + pn.getMaSanPham());
                    addParagraph(document, "Tên Sản Phẩm: " + pn.getTenSanPham());
                    addParagraph(document, "Số Lượng: " + pn.getSoLuong());
                    addParagraph(document, "Thời Gian: " + (pn.getThoiGian() != null ? genericTimestampFormat.format(pn.getThoiGian()) : ""));
                    addParagraph(document, "Đơn Giá: " + genericDecimalFormat.format(pn.getDonGia()));
                    addParagraph(document, "Thành Tiền: " + genericDecimalFormat.format(pn.getThanhTien()));
                    addParagraph(document, "Hình Thức Thanh Toán: " + pn.getHinhThucThanhToan());
                    addParagraph(document, "Trạng Thái: " + pn.getTrangThai());
                    document.createParagraph().createRun().addBreak();
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    document.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Word Phiếu Nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Word Phiếu Nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void importFromExcelForPhieuNhap(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel Phiếu Nhập để import (*.xlsx)");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
            PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO(); // For checking existence
            int successCount = 0;
            int failCount = 0;
            StringBuilder errorMessages = new StringBuilder("Chi tiết lỗi/bỏ qua:\n");

            try (Workbook workbook = new XSSFWorkbook(new FileInputStream(selectedFile))) {
                Sheet sheet = workbook.getSheetAt(0);
                // Headers: Mã PN(0), Mã NCC(1), Mã SP(2), Tên SP(3), Số lượng(4), Thời gian(5), Đơn giá(6), Thành tiền(7), HTTT(8), Trạng thái(9)
                List<String> existingMaPNs = phieuNhapDAO.getAll().stream() // Assuming PhieuNhapDAO has getAll()
                                               .map(PhieuNhapDTO::getMaPhieuNhap)
                                               .collect(Collectors.toList());

                for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header row
                    Row row = sheet.getRow(i);
                    if (row == null) continue;
                    try {
                        String maPhieuNhap = getCellStringValue(row.getCell(0)); // Index adjusted
                        if (maPhieuNhap == null || maPhieuNhap.trim().isEmpty()) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Mã phiếu nhập trống.\n");
                                failCount++;
                            continue;
                        }
                        if (existingMaPNs.contains(maPhieuNhap)) {
                             errorMessages.append("Dòng ").append(i + 1).append(": Mã phiếu nhập '").append(maPhieuNhap).append("' đã tồn tại.\n");
                            failCount++;
                                continue;
                            }

                        PhieuNhapDTO pn = new PhieuNhapDTO();
                        pn.setMaPhieuNhap(maPhieuNhap);
                        pn.setMaNhaCungCap(getCellStringValue(row.getCell(1)));
                        pn.setMaSanPham(getCellStringValue(row.getCell(2)));
                        pn.setTenSanPham(getCellStringValue(row.getCell(3))); // Added TenSanPham
                        pn.setSoLuong((int) getCellNumericValue(row.getCell(4)));
                        
                        Cell thoiGianCell = row.getCell(5);
                        if (thoiGianCell != null) {
                            if (thoiGianCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(thoiGianCell)) {
                                pn.setThoiGian(new Timestamp(thoiGianCell.getDateCellValue().getTime()));
                            } else {
                                String thoiGianStr = getCellStringValue(thoiGianCell);
                                if (thoiGianStr != null && !thoiGianStr.trim().isEmpty()) {
                                    try {
                                        Date parsedDate = null;
                                        try {
                                            parsedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(thoiGianStr);
                                        } catch (java.text.ParseException e1) {
                                            try {
                                                 parsedDate = genericTimestampFormat.parse(thoiGianStr); // "dd/MM/yyyy HH:mm:ss"
                                            } catch (java.text.ParseException e2) {
                                                try {
                                                     parsedDate = genericDateFormat.parse(thoiGianStr); // "dd/MM/yyyy"
                                                } catch (java.text.ParseException e3) {
                                                     try {
                                                        parsedDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.ENGLISH).parse(thoiGianStr);
                                                     } catch (java.text.ParseException e4){
                                                        errorMessages.append("Dòng ").append(i + 1).append(": Lỗi định dạng Thời gian '").append(thoiGianStr).append("'. Không thể phân tích.\n");
                                                        // pn.setThoiGian(null); // DTO might not allow null for Date, ensure it's handled or set to current if appropriate
                                                     }
                                                }
                                            }
                                        }
                                        if (parsedDate != null) {
                                            pn.setThoiGian(new Timestamp(parsedDate.getTime()));
                                        } else if (pn.getThoiGian() == null && !errorMessages.toString().contains("Thời gian '" + thoiGianStr)){
                                            // pn.setThoiGian(new Date()); // Default to now if parsing fails and not logged. Or handle as error.
                                        }
                                    } catch (Exception e) { 
                                        errorMessages.append("Dòng ").append(i + 1).append(": Lỗi xử lý Thời gian '").append(thoiGianStr).append("': ").append(e.getMessage()).append("\n");
                                        // pn.setThoiGian(new Date()); 
                                    }
                                } else {
                                    // pn.setThoiGian(new Date()); // If cell is empty, default to now or handle as error
                                }
                            }
                        } else {
                            // pn.setThoiGian(new Date()); // If cell is null, default to now or handle as error
                        }
                        // Ensure ThoiGian is set, otherwise DB might reject null if column is NOT NULL
                        if (pn.getThoiGian() == null) {
                             errorMessages.append("Dòng ").append(i + 1).append(": Thời gian không hợp lệ hoặc trống. Bỏ qua.\n");
                             failCount++;
                             continue;
                        }

                        pn.setDonGia(getCellNumericValue(row.getCell(6)));
                        pn.setThanhTien(getCellNumericValue(row.getCell(7)));
                        pn.setHinhThucThanhToan(getCellStringValue(row.getCell(8)));
                        pn.setTrangThai(getCellStringValue(row.getCell(9)));
                        
                        if (phieuNhapBUS.createPhieuNhap(pn)) {
                            successCount++;
                            existingMaPNs.add(maPhieuNhap);
                        } else {
                             errorMessages.append("Dòng ").append(i + 1).append(": Không thể thêm phiếu nhập '").append(maPhieuNhap).append("'. Kiểm tra Mã NCC, Mã SP.\n");
                            failCount++;
                        }
                    } catch (Exception e) {
                        errorMessages.append("Dòng ").append(i + 1).append(": Lỗi xử lý - ").append(e.getMessage()).append("\n");
                        failCount++;
                    }
                }
                 String message = String.format("Import hoàn tất!\nThành công: %d\nThất bại/Bỏ qua: %d", successCount, failCount);
                if (failCount > 0 || errorMessages.length() > "Chi tiết lỗi/bỏ qua:\n".length()) {
                    JTextArea textArea = new JTextArea(errorMessages.toString());
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
                    JOptionPane.showMessageDialog(null, scrollPane, "Kết quả Import Phiếu Nhập", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, message, "Kết quả Import Phiếu Nhập", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi import file Excel Phiếu Nhập: " + e.getMessage(), "Lỗi Import", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="KhuyenMai Specific File Operations">
    public static void exportToKhuyenMaiExcel(JTable table, String title) {
        // exportToExcel(table, title);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel Khuyến Mãi");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File(title + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO(); // Or KhuyenMaiService
            List<khuyenMaiDTO> khuyenMaiList = khuyenMaiDAO.getAllKhuyenMai();

            String[] headers = {
                "Mã Khuyến Mãi", "Mã Sản Phẩm", "Tên Sản Phẩm", "Tên Chương Trình",
                "Giảm Giá (%)", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Giá Cũ", "Giá Mới", "Trạng Thái"
            };

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet(title);
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }

                int rowNum = 1;
                for (khuyenMaiDTO km : khuyenMaiList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(km.getMaKhuyenMai());
                    row.createCell(1).setCellValue(km.getMaSanPham());
                    row.createCell(2).setCellValue(km.getTenSanPham());
                    row.createCell(3).setCellValue(km.getTenChuongTrinh());
                    row.createCell(4).setCellValue(km.getGiamGia()); // Store as number, display as % if needed
                    row.createCell(5).setCellValue(km.getNgayBatDau() != null ? genericDateFormat.format(km.getNgayBatDau()) : "");
                    row.createCell(6).setCellValue(km.getNgayKetThuc() != null ? genericDateFormat.format(km.getNgayKetThuc()) : "");
                    row.createCell(7).setCellValue(km.getGiaCu());
                    row.createCell(8).setCellValue(km.getGiaMoi());
                    row.createCell(9).setCellValue(km.getTrangThai());
                }

                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Excel Khuyến Mãi thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel Khuyến Mãi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void exportToKhuyenMaiWord(JTable table, String title) {
        // exportToWord(table, title);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Word Khuyến Mãi");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Word files (*.docx)", "docx"));
        fileChooser.setSelectedFile(new File(title + ".docx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".docx")) {
                filePath += ".docx";
            }

            KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
            List<khuyenMaiDTO> khuyenMaiList = khuyenMaiDAO.getAllKhuyenMai();

            try (XWPFDocument document = new XWPFDocument()) {
                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setBold(true);
                titleRun.setFontSize(16);
                titleRun.setText("DANH SÁCH KHUYẾN MÃI");
                titleRun.addBreak();

                for (khuyenMaiDTO km : khuyenMaiList) {
                    addParagraph(document, "Mã Khuyến Mãi: " + km.getMaKhuyenMai());
                    addParagraph(document, "Mã Sản Phẩm: " + km.getMaSanPham());
                    addParagraph(document, "Tên Sản Phẩm: " + km.getTenSanPham());
                    addParagraph(document, "Tên Chương Trình: " + km.getTenChuongTrinh());
                    addParagraph(document, "Giảm Giá: " + km.getGiamGia() + "%");
                    addParagraph(document, "Ngày Bắt Đầu: " + (km.getNgayBatDau() != null ? genericDateFormat.format(km.getNgayBatDau()) : ""));
                    addParagraph(document, "Ngày Kết Thúc: " + (km.getNgayKetThuc() != null ? genericDateFormat.format(km.getNgayKetThuc()) : ""));
                    addParagraph(document, "Giá Cũ: " + genericDecimalFormat.format(km.getGiaCu()));
                    addParagraph(document, "Giá Mới: " + genericDecimalFormat.format(km.getGiaMoi()));
                    addParagraph(document, "Trạng Thái: " + km.getTrangThai());
                    document.createParagraph().createRun().addBreak();
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    document.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Word Khuyến Mãi thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Word Khuyến Mãi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void importFromExcelForKhuyenMai(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel Khuyến Mãi để import (*.xlsx)");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            KhuyenMaiService khuyenMaiService = new KhuyenMaiService();
            KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO(); // For checking existence
            int successCount = 0;
            int failCount = 0;
            StringBuilder errorMessages = new StringBuilder("Chi tiết lỗi/bỏ qua:\n");
            
            try (Workbook workbook = new XSSFWorkbook(new FileInputStream(selectedFile))) {
                Sheet sheet = workbook.getSheetAt(0);
                // Headers: Mã KM(0), Mã SP(1), Tên SP(2), Tên CT(3), Ngày BĐ(4), Ngày KT(5), Giảm giá(6), Giá cũ(7), Giá mới(8), Trạng thái(9)
                // Note: Original import read headers: STT(0), Mã KM(1), Mã SP(2), Tên SP(3), Tên CT(4), Ngày BĐ(5), Ngày KT(6), Giảm giá(7), Giá cũ(8), Giá mới(9), Trạng thái(10)
                // Current export order: Mã KM, Mã SP, Tên SP, Tên CT, Giảm Giá, Ngày BĐ, Ngày KT, Giá Cũ, Giá Mới, Trạng Thái
                // Adjusting import to match current export headers.
                List<String> existingMaKMs = khuyenMaiDAO.getAllKhuyenMai().stream() // Assuming getAllKhuyenMai exists
                                               .map(khuyenMaiDTO::getMaKhuyenMai)
                                               .collect(Collectors.toList());

                for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header row
                    Row row = sheet.getRow(i);
                    if (row == null) continue;
                    try {
                        String maKhuyenMai = getCellStringValue(row.getCell(0)); // Index adjusted
                        if (maKhuyenMai == null || maKhuyenMai.trim().isEmpty()) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Mã khuyến mãi trống.\n");
                                failCount++;
                            continue;
                        }
                         if (existingMaKMs.contains(maKhuyenMai)) {
                             errorMessages.append("Dòng ").append(i + 1).append(": Mã khuyến mãi '").append(maKhuyenMai).append("' đã tồn tại.\n");
                            failCount++;
                                continue;
                            }

                        khuyenMaiDTO km = new khuyenMaiDTO();
                        km.setMaKhuyenMai(maKhuyenMai);
                        km.setMaSanPham(getCellStringValue(row.getCell(1)));
                        km.setTenSanPham(getCellStringValue(row.getCell(2)));
                        km.setTenChuongTrinh(getCellStringValue(row.getCell(3)));

                        Cell ngayBDCell = row.getCell(5); // Index for Ngay Bat Dau (original was 5)
                        Cell ngayKTCell = row.getCell(6); // Index for Ngay Ket Thuc (original was 6)

                        Date ngayBD = null;
                        Date ngayKT = null;

                        if (ngayBDCell != null) {
                            if (ngayBDCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(ngayBDCell)) {
                                ngayBD = ngayBDCell.getDateCellValue();
                                } else {
                                String ngayBDStr = getCellStringValue(ngayBDCell);
                                if (ngayBDStr != null && !ngayBDStr.trim().isEmpty()) {
                                    try { ngayBD = genericDateFormat.parse(ngayBDStr); }
                                    catch (java.text.ParseException pe) { errorMessages.append("Dòng ").append(i + 1).append(": Lỗi định dạng Ngày Bắt Đầu '").append(ngayBDStr).append("'.\n");}
                                }
                            }
                        }
                        km.setNgayBatDau(ngayBD);
                        
                        if (ngayKTCell != null) {
                             if (ngayKTCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(ngayKTCell)) {
                                ngayKT = ngayKTCell.getDateCellValue();
                        } else {
                                String ngayKTStr = getCellStringValue(ngayKTCell);
                                if (ngayKTStr != null && !ngayKTStr.trim().isEmpty()) {
                                     try { ngayKT = genericDateFormat.parse(ngayKTStr);}
                                     catch (java.text.ParseException pe) { errorMessages.append("Dòng ").append(i + 1).append(": Lỗi định dạng Ngày Kết Thúc '").append(ngayKTStr).append("'.\n");}
                                }
                            }
                        }
                        km.setNgayKetThuc(ngayKT);

                        if (km.getNgayBatDau() == null || km.getNgayKetThuc() == null) {
                             errorMessages.append("Dòng ").append(i + 1).append(": Ngày bắt đầu hoặc ngày kết thúc không hợp lệ cho KM '").append(maKhuyenMai).append("'. Bỏ qua.\n");
                             failCount++;
                             continue;
                        }
                        
                        String giamGiaStr = getCellStringValue(row.getCell(4)); // Index for Giam Gia (original was 7)
                        if (giamGiaStr != null && !giamGiaStr.isEmpty()) {
                            try {
                                double giamGiaValue = Double.parseDouble(giamGiaStr.replace("%", "").trim());
                                km.setGiamGia(giamGiaValue);
                            } catch (NumberFormatException nfe) {
                                 errorMessages.append("Dòng ").append(i + 1).append(": Lỗi định dạng Giảm Giá '").append(giamGiaStr).append("'.\n");
                                 km.setGiamGia(0); // Default or skip
                            }
                        } else {
                             km.setGiamGia(0);
                        }

                        km.setGiaCu(getCellNumericValue(row.getCell(7))); // Index for Gia Cu (original was 8)
                        km.setGiaMoi(getCellNumericValue(row.getCell(8))); // Index for Gia Moi (original was 9)
                        km.setTrangThai(getCellStringValue(row.getCell(9))); // Index for Trang Thai (original was 10)
                        
                        if (khuyenMaiService.addKhuyenMai(km)) {
                                successCount++;
                            existingMaKMs.add(maKhuyenMai);
                            } else {
                            errorMessages.append("Dòng ").append(i + 1).append(": Không thể thêm khuyến mãi '").append(maKhuyenMai).append("'. Kiểm tra Mã SP.\n");
                                failCount++;
                            }
                    } catch (Exception e) {
                        errorMessages.append("Dòng ").append(i + 1).append(": Lỗi xử lý - ").append(e.getMessage()).append("\n");
                            failCount++;
                    }
                }
                 String message = String.format("Import hoàn tất!\nThành công: %d\nThất bại/Bỏ qua: %d", successCount, failCount);
                if (failCount > 0 || errorMessages.length() > "Chi tiết lỗi/bỏ qua:\n".length()) {
                    JTextArea textArea = new JTextArea(errorMessages.toString());
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
                    JOptionPane.showMessageDialog(null, scrollPane, "Kết quả Import Khuyến Mãi", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, message, "Kết quả Import Khuyến Mãi", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi import file Excel Khuyến Mãi: " + e.getMessage(), "Lỗi Import", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="NhanVien Specific File Operations">
    public static void exportToNhanVienExcel(JTable table, String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel Nhân Viên");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File(title + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            NhanVienDAO nhanVienDAO = new NhanVienDAO();
            List<nhanVienDTO> nhanVienList = nhanVienDAO.getAllNhanVien();

            String[] headers = {
                "Mã Nhân Viên", "Họ Tên", "Giới Tính", "Ngày Sinh", "Số Điện Thoại", "Email", "Địa Chỉ",
                "Chức Vụ", "Mức Lương", "Ngày Vào Làm", "Mã Quản Lý", "Trạng Thái Nhân Viên",
                "Tên Đăng Nhập (TK)", "Mật Khẩu (TK)", "Vai Trò (TK)", "Trạng Thái (TK)"
            };

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet(title);
                
                // Create header row
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }

                // Create data rows
                int rowNum = 1;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat timestampFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                for (nhanVienDTO nv : nhanVienList) {
                    Row row = sheet.createRow(rowNum++);
                    taiKhoanDTO tk = nhanVienDAO.getTaiKhoanByMaNhanVien(nv.getMaNhanVien());

                    row.createCell(0).setCellValue(nv.getMaNhanVien());
                    row.createCell(1).setCellValue(nv.getHoTen());
                    row.createCell(2).setCellValue(nv.getGioiTinh());
                    row.createCell(3).setCellValue(nv.getNgaySinh() != null ? dateFormat.format(nv.getNgaySinh()) : "");
                    row.createCell(4).setCellValue(nv.getSoDienThoai());
                    row.createCell(5).setCellValue(nv.getEmail());
                    row.createCell(6).setCellValue(nv.getDiaChi());
                    row.createCell(7).setCellValue(nv.getChucVu());
                    row.createCell(8).setCellValue(nv.getMucLuong() != null ? nv.getMucLuong().doubleValue() : 0.0);
                    row.createCell(9).setCellValue(nv.getNgayVaoLam() != null ? timestampFormat.format(nv.getNgayVaoLam()) : "");
                    row.createCell(10).setCellValue(nv.getMaQuanLy());
                    row.createCell(11).setCellValue(nv.getTrangThai());

                    if (tk != null) {
                        row.createCell(12).setCellValue(tk.getTenDangNhap());
                        row.createCell(13).setCellValue(tk.getMatKhau()); // Security consideration: Exporting plain passwords
                        row.createCell(14).setCellValue(tk.getVaiTro() != null ? tk.getVaiTro().getDisplayName() : "");
                        row.createCell(15).setCellValue(tk.getTrangThai() == 1 ? "Hoạt động" : (tk.getTrangThai() == 0 ? "Không hoạt động" : "Đang xét duyệt"));
                } else {
                        for(int i = 12; i <= 15; i++) {
                            row.createCell(i).setCellValue(""); // Empty if no account info
                        }
                    }
                }

                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Excel Nhân Viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel Nhân Viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void exportToNhanVienWord(JTable table, String title) {
        // For Word export, we might need to fetch full data similarly if we want all fields.
        // For now, let's assume the current Word export (based on JTable) might be sufficient,
        // or it can be enhanced later if requested. This focuses on Excel import/export.
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Word Nhân Viên");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Word files (*.docx)", "docx"));
        fileChooser.setSelectedFile(new File(title + ".docx"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".docx")) {
                filePath += ".docx";
            }

            NhanVienDAO nhanVienDAO = new NhanVienDAO();
            List<nhanVienDTO> nhanVienList = nhanVienDAO.getAllNhanVien();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timestampFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            try (XWPFDocument document = new XWPFDocument()) {
                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setBold(true);
                titleRun.setFontSize(18);
                titleRun.setText("DANH SÁCH NHÂN VIÊN");
                titleRun.addBreak();

                for (nhanVienDTO nv : nhanVienList) {
                    taiKhoanDTO tk = nhanVienDAO.getTaiKhoanByMaNhanVien(nv.getMaNhanVien());

                    addParagraph(document, "Mã Nhân Viên: " + (nv.getMaNhanVien() != null ? nv.getMaNhanVien() : ""));
                    addParagraph(document, "Họ Tên: " + (nv.getHoTen() != null ? nv.getHoTen() : ""));
                    addParagraph(document, "Giới Tính: " + (nv.getGioiTinh() != null ? nv.getGioiTinh() : ""));
                    addParagraph(document, "Ngày Sinh: " + (nv.getNgaySinh() != null ? dateFormat.format(nv.getNgaySinh()) : ""));
                    addParagraph(document, "Số Điện Thoại: " + (nv.getSoDienThoai() != null ? nv.getSoDienThoai() : ""));
                    addParagraph(document, "Email: " + (nv.getEmail() != null ? nv.getEmail() : ""));
                    addParagraph(document, "Địa Chỉ: " + (nv.getDiaChi() != null ? nv.getDiaChi() : ""));
                    addParagraph(document, "Chức Vụ: " + (nv.getChucVu() != null ? nv.getChucVu() : ""));
                    addParagraph(document, "Mức Lương: " + (nv.getMucLuong() != null ? nv.getMucLuong().toString() : ""));
                    addParagraph(document, "Ngày Vào Làm: " + (nv.getNgayVaoLam() != null ? timestampFormat.format(nv.getNgayVaoLam()) : ""));
                    addParagraph(document, "Mã Quản Lý: " + (nv.getMaQuanLy() != null ? nv.getMaQuanLy() : ""));
                    addParagraph(document, "Trạng Thái Nhân Viên: " + (nv.getTrangThai() != null ? nv.getTrangThai() : ""));

                    if (tk != null) {
                        addParagraph(document, "--- Thông tin tài khoản ---");
                        addParagraph(document, "Tên Đăng Nhập: " + (tk.getTenDangNhap() != null ? tk.getTenDangNhap() : ""));
                        // Not exporting password to Word for security
                        addParagraph(document, "Vai Trò: " + (tk.getVaiTro() != null ? tk.getVaiTro().getDisplayName() : ""));
                        String trangThaiTKStr = tk.getTrangThai() == 1 ? "Hoạt động" : (tk.getTrangThai() == 0 ? "Không hoạt động" : "Đang xét duyệt");
                        addParagraph(document, "Trạng Thái Tài Khoản: " + trangThaiTKStr);
                    } else {
                        addParagraph(document, "--- Nhân viên chưa có tài khoản ---");
                    }
                    document.createParagraph().createRun().addBreak(); // Add a blank line between employees
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    document.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Word Nhân Viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Word Nhân Viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Helper method for Word export
    private static void addParagraph(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
    }
    
    public static void importFromExcelForNhanVien(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel Nhân Viên để import (*.xlsx)");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            NhanVienDAO nhanVienDAO = new NhanVienDAO(); 
            DAO.TaiKhoanDAO taiKhoanDAO = new DAO.TaiKhoanDAO();
            int successCount = 0;
            int failCount = 0;
            // int skippedCount = 0; // Re-evaluate how skipped is counted
            StringBuilder errorMessages = new StringBuilder("Chi tiết lỗi/bỏ qua:\n");

            try (Workbook workbook = new XSSFWorkbook(new FileInputStream(selectedFile))) {
                Sheet sheet = workbook.getSheetAt(0);
                List<String> existingMaNVs = nhanVienDAO.getAllNhanVien().stream()
                                               .map(nhanVienDTO::getMaNhanVien)
                                               .collect(Collectors.toList());
                Set<String> existingTenDangNhap = taiKhoanDAO.getAllTaiKhoan().stream()
                                                  .map(taiKhoanDTO::getTenDangNhap)
                                                  .collect(Collectors.toSet());                               

                for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header row (index 0)
                    Row row = sheet.getRow(i);
                    if (row == null || row.getCell(0) == null || getCellStringValue(row.getCell(0)).trim().isEmpty()) { // Check if MaNV is empty
                        // errorMessages.append("Dòng ").append(i + 1).append(": Hàng trống hoặc Mã Nhân Viên trống, bỏ qua.\n");
                        // skippedCount++; // Count actually skipped rows
                        continue;
                    }
                    
                    boolean rowHasError = false;
                    nhanVienDTO nv = new nhanVienDTO();
                    taiKhoanDTO tk = new taiKhoanDTO();

                    try {
                        // Excel Headers: MaNV, HoTen, GioiTinh, NgaySinh, SDT, Email, DiaChi, ChucVu, MucLuong, NgayVaoLam, MaQL, TrangThaiNV, TenDN(TK), MatKhau(TK), VaiTro(TK), TrangThai(TK)
                        // Indices:       0,    1,      2,        3,       4,     5,      6,        7,        8,         9,            10,      11,          12,           13,           14,            15

                        String maNhanVien = getCellStringValue(row.getCell(0));
                        // MaNhanVien trống đã được kiểm tra ở trên.
                        if (existingMaNVs.contains(maNhanVien)) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Mã nhân viên '").append(maNhanVien).append("' đã tồn tại. Bỏ qua.\n");
                            // skippedCount++; // This is more like a "failed due to duplication"
                            failCount++; // Consider this a failure to import this specific record
                            continue;
                        }
                        nv.setMaNhanVien(maNhanVien);

                        nv.setHoTen(getCellStringValue(row.getCell(1)));
                        nv.setGioiTinh(getCellStringValue(row.getCell(2)));

                        Cell ngaySinhCell = row.getCell(3);
                        if (ngaySinhCell != null && !getCellStringValue(ngaySinhCell).trim().isEmpty()) {
                            String ngaySinhStr = getCellStringValue(ngaySinhCell);
                            try {
                                if (ngaySinhCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(ngaySinhCell)) {
                                     nv.setNgaySinh(new java.sql.Date(ngaySinhCell.getDateCellValue().getTime()));
                                } else if (ngaySinhStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) { // dd/MM/yyyy
                                    Date parsedUtilDate = genericDateFormat.parse(ngaySinhStr); 
                                    nv.setNgaySinh(new java.sql.Date(parsedUtilDate.getTime()));
                                } else if (ngaySinhStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) { // yyyy-MM-dd
                                     Date parsedUtilDate = new SimpleDateFormat("yyyy-MM-dd").parse(ngaySinhStr);
                                    nv.setNgaySinh(new java.sql.Date(parsedUtilDate.getTime()));
                                }
                                 else {
                                    errorMessages.append("Dòng ").append(i + 1).append(": Định dạng Ngày sinh '").append(ngaySinhStr).append("' không được hỗ trợ. Dự kiến dd/MM/yyyy hoặc yyyy-MM-dd.\n");
                                    nv.setNgaySinh(null); 
                                    rowHasError = true;
                                }
                            } catch (java.text.ParseException pe) {
                                errorMessages.append("Dòng ").append(i + 1).append(": Lỗi định dạng Ngày sinh '").append(ngaySinhStr).append("'.\n");
                                nv.setNgaySinh(null); 
                                rowHasError = true;
                            }
                        } else {
                            nv.setNgaySinh(null); 
                        }

                        nv.setSoDienThoai(getCellStringValue(row.getCell(4)));
                        nv.setEmail(getCellStringValue(row.getCell(5)));
                        nv.setDiaChi(getCellStringValue(row.getCell(6)));
                        nv.setChucVu(getCellStringValue(row.getCell(7)));
                        
                        Cell mucLuongCell = row.getCell(8);
                        if (mucLuongCell != null && !getCellStringValue(mucLuongCell).trim().isEmpty()) {
                            try {
                                nv.setMucLuong(BigDecimal.valueOf(getCellNumericValue(mucLuongCell)));
                            } catch (NumberFormatException e) {
                                errorMessages.append("Dòng ").append(i + 1).append(": Lỗi định dạng Mức lương '").append(getCellStringValue(mucLuongCell)).append("'.\n");
                                nv.setMucLuong(BigDecimal.ZERO); 
                                rowHasError = true;
                            }
                        } else {
                             nv.setMucLuong(BigDecimal.ZERO);
                        }

                        Cell ngayVaoLamCell = row.getCell(9);
                        if (ngayVaoLamCell != null && !getCellStringValue(ngayVaoLamCell).trim().isEmpty()) {
                            String ngayVaoLamStr = getCellStringValue(ngayVaoLamCell);
                            try {
                                if (ngayVaoLamCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(ngayVaoLamCell)) {
                                    nv.setNgayVaoLam(new java.sql.Timestamp(ngayVaoLamCell.getDateCellValue().getTime()));
                                } else if (ngayVaoLamStr.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}")) { // dd/MM/yyyy HH:mm:ss
                                Date parsedTimestamp = genericTimestampFormat.parse(ngayVaoLamStr);
                                nv.setNgayVaoLam(new java.sql.Timestamp(parsedTimestamp.getTime()));
                                } else if (ngayVaoLamStr.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")) { // yyyy-MM-dd HH:mm:ss
                                     Date parsedTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ngayVaoLamStr);
                                     nv.setNgayVaoLam(new java.sql.Timestamp(parsedTimestamp.getTime()));
                                } else if (ngayVaoLamStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) { // dd/MM/yyyy (time defaults to 00:00:00)
                                    Date parsedDate = genericDateFormat.parse(ngayVaoLamStr);
                                    nv.setNgayVaoLam(new java.sql.Timestamp(parsedDate.getTime())); 
                                } else if (ngayVaoLamStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) { // yyyy-MM-dd
                                     Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(ngayVaoLamStr);
                                        nv.setNgayVaoLam(new java.sql.Timestamp(parsedDate.getTime()));
                                }
                                 else {
                                     errorMessages.append("Dòng ").append(i + 1).append(": Định dạng Ngày vào làm '").append(ngayVaoLamStr).append("' không được hỗ trợ.\n");
                                     nv.setNgayVaoLam((java.sql.Timestamp) null); 
                                     rowHasError = true;
                                 }
                            } catch (java.text.ParseException e) {
                                        errorMessages.append("Dòng ").append(i + 1).append(": Lỗi định dạng Ngày vào làm '").append(ngayVaoLamStr).append("'.\n");
                                        nv.setNgayVaoLam((java.sql.Timestamp) null); 
                                        rowHasError = true;
                            }
                        } else {
                            nv.setNgayVaoLam((java.sql.Timestamp) null); 
                        }
                        
                        nv.setMaQuanLy(getCellStringValue(row.getCell(10)));
                        nv.setTrangThai(getCellStringValue(row.getCell(11)));
                        if (nv.getTrangThai() == null || nv.getTrangThai().trim().isEmpty()) {
                            nv.setTrangThai("Hoạt động"); 
                        }

                        String tenDangNhap = getCellStringValue(row.getCell(12));
                        String matKhau = getCellStringValue(row.getCell(13));
                        String vaiTroStr = getCellStringValue(row.getCell(14));
                        String trangThaiTKExcelStr = getCellStringValue(row.getCell(15));
                        int trangThaiTKValue;

                        if ("Hoạt động".equalsIgnoreCase(trangThaiTKExcelStr)) {
                            trangThaiTKValue = 1;
                        } else if ("Không hoạt động".equalsIgnoreCase(trangThaiTKExcelStr)) {
                            trangThaiTKValue = 0;
                        } else if ("Đang xét duyệt".equalsIgnoreCase(trangThaiTKExcelStr)) {
                            trangThaiTKValue = -1;
                        } else {
                            trangThaiTKValue = 1; // Default
                            if(trangThaiTKExcelStr != null && !trangThaiTKExcelStr.trim().isEmpty()) {
                                errorMessages.append("Dòng ").append(i + 1).append(": Trạng thái tài khoản '").append(trangThaiTKExcelStr).append("' không hợp lệ, mặc định là Hoạt động.\n");
                            }
                        }

                        boolean hasTaiKhoanInfo = (tenDangNhap != null && !tenDangNhap.trim().isEmpty());

                        if (rowHasError) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Nhân viên '").append(maNhanVien).append("' có lỗi dữ liệu, không được thêm.\n");
                            failCount++;
                            continue; 
                        }

                        if (nhanVienDAO.themNhanVien(nv)) { // themNhanVien should only take nv, not tk
                            existingMaNVs.add(maNhanVien);
                            boolean taiKhoanProcessedSuccessfully = true;

                            if (hasTaiKhoanInfo) {
                                if (existingTenDangNhap.contains(tenDangNhap)){
                                     errorMessages.append("Dòng ").append(i + 1).append(": Tên đăng nhập '").append(tenDangNhap).append("' đã tồn tại. Tài khoản cho NV '").append(maNhanVien).append("' sẽ không được tạo.\n");
                                     taiKhoanProcessedSuccessfully = false;
                                } else {
                                    String maTaiKhoan = taiKhoanDAO.taoMaTaiKhoanMoi();
                                    if (maTaiKhoan == null) {
                                        errorMessages.append("Dòng ").append(i + 1).append(": Không thể tạo mã tài khoản cho NV '").append(maNhanVien).append("'.\n");
                                        taiKhoanProcessedSuccessfully = false;
                                    } else {
                                        tk.setMaTaiKhoan(maTaiKhoan);
                                        tk.setTenDangNhap(tenDangNhap);
                                        tk.setMatKhau(matKhau); 
                                        tk.setVaiTro(vaiTroStr); // TaiKhoanDAO.themTaiKhoan should handle string to Enum mapping if needed
                                        tk.setTrangThai(trangThaiTKValue);
                                        tk.setMaNhanVien(maNhanVien);
                                        try {
                                            if (!taiKhoanDAO.themTaiKhoan(tk)) {
                                                errorMessages.append("Dòng ").append(i + 1).append(": Thêm NV '").append(maNhanVien).append("' thành công nhưng không thể tạo tài khoản '").append(tenDangNhap).append("'.\n");
                                                taiKhoanProcessedSuccessfully = false;
                                            } else {
                                                 existingTenDangNhap.add(tenDangNhap);
                                            }
                                        } catch (Exception sqlEx) { // Catch specific exceptions from TaiKhoanDAO if possible
                                            errorMessages.append("Dòng ").append(i + 1).append(": Lỗi khi thêm tài khoản '").append(tenDangNhap).append("' cho NV '").append(maNhanVien).append("': ").append(sqlEx.getMessage()).append("\n");
                                            taiKhoanProcessedSuccessfully = false;
                                        }
                                    }
                                }
                            }
                            if (taiKhoanProcessedSuccessfully || !hasTaiKhoanInfo) {
                            successCount++;
                        } else {
                                // NV added, but TK failed. This is a partial success / warning scenario.
                                // Currently counted as success for NV, but error logged for TK.
                                // If NV add should be rolled back on TK fail, more complex transaction needed.
                                // For now, let's consider it a success for NV part.
                                successCount++; 
                                // But we might want a separate counter for "NV added, TK failed"
                            }
                        } else {
                            errorMessages.append("Dòng ").append(i + 1).append(": Không thể thêm nhân viên '").append(maNhanVien).append("' vào DB.\n");
                            failCount++;
                        }
                            } catch (Exception e) {
                        String maNvForRow = getCellStringValue(row.getCell(0)); // Try to get MaNV for error message
                        errorMessages.append("Dòng ").append(i + 1).append(" (Mã NV: ").append(maNvForRow != null ? maNvForRow : "Không rõ").append("): Lỗi xử lý không xác định - ").append(e.getMessage()).append("\n");
                        e.printStackTrace(); 
                        failCount++;
                    }
                } 
                
                // int totalProcessed = successCount + failCount + skippedCount;
                String message = String.format("Import hoàn tất!\nThành công: %d\nThất bại/Bỏ qua: %d", successCount, failCount);


                if (failCount > 0 || errorMessages.length() > "Chi tiết lỗi/bỏ qua:\n".length()) {
                    JTextArea textArea = new JTextArea(errorMessages.toString());
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    scrollPane.setPreferredSize(new java.awt.Dimension(600, 400));
                    JOptionPane.showMessageDialog(null, scrollPane, "Kết quả Import Nhân Viên", JOptionPane.WARNING_MESSAGE);
                            } else {
                    JOptionPane.showMessageDialog(null, message, "Kết quả Import Nhân Viên", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi nghiêm trọng khi import file Excel Nhân Viên: " + e.getMessage(), "Lỗi Import", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="NhaCungCap Specific File Operations">
    public static void exportToNhaCungCapExcel(JTable table, String title) {
        // exportToExcel(table, title);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel Nhà Cung Cấp");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File(title + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
            List<nhaCungCapDTO> nccList = nhaCungCapDAO.getAllNhaCungCap();

            String[] headers = {
                "Mã Nhà Cung Cấp", "Tên Nhà Cung Cấp", "Loại Sản Phẩm Cung Cấp", "Năm Hợp Tác",
                "Địa Chỉ", "Email", "Số Điện Thoại", "Trạng Thái"
            };

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet(title);
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }

                int rowNum = 1;
                for (nhaCungCapDTO ncc : nccList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(ncc.getMaNhaCungCap());
                    row.createCell(1).setCellValue(ncc.getTenNhaCungCap());
                    row.createCell(2).setCellValue(ncc.getLoaiSP());
                    row.createCell(3).setCellValue(ncc.getNamHopTac());
                    row.createCell(4).setCellValue(ncc.getDiaChi());
                    row.createCell(5).setCellValue(ncc.getEmail());
                    row.createCell(6).setCellValue(ncc.getSoDienThoai());
                    row.createCell(7).setCellValue(ncc.getTrangThai());
                }

                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Excel Nhà Cung Cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel Nhà Cung Cấp: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void exportToNhaCungCapWord(JTable table, String title) {
        // exportToWord(table, title);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Word Nhà Cung Cấp");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Word files (*.docx)", "docx"));
        fileChooser.setSelectedFile(new File(title + ".docx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".docx")) {
                filePath += ".docx";
            }
            
            NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
            List<nhaCungCapDTO> nccList = nhaCungCapDAO.getAllNhaCungCap();

            try (XWPFDocument document = new XWPFDocument()) {
                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setBold(true);
                titleRun.setFontSize(16);
                titleRun.setText("DANH SÁCH NHÀ CUNG CẤP");
                titleRun.addBreak();

                for (nhaCungCapDTO ncc : nccList) {
                    addParagraph(document, "Mã NCC: " + ncc.getMaNhaCungCap());
                    addParagraph(document, "Tên NCC: " + ncc.getTenNhaCungCap());
                    addParagraph(document, "Loại SP Cung Cấp: " + ncc.getLoaiSP());
                    addParagraph(document, "Năm Hợp Tác: " + ncc.getNamHopTac());
                    addParagraph(document, "Địa Chỉ: " + ncc.getDiaChi());
                    addParagraph(document, "Email: " + ncc.getEmail());
                    addParagraph(document, "SĐT: " + ncc.getSoDienThoai());
                    addParagraph(document, "Trạng Thái: " + ncc.getTrangThai());
                    document.createParagraph().createRun().addBreak();
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    document.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Word Nhà Cung Cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Word Nhà Cung Cấp: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void importFromExcelForNhaCungCap(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel Nhà Cung Cấp để import (*.xlsx)");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            NhaCungCapBUS nhaCungCapBUS = new NhaCungCapBUS();
            NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO(); // For checking existence
            int successCount = 0;
            int failCount = 0;
            StringBuilder errorMessages = new StringBuilder("Chi tiết lỗi/bỏ qua:\n");

            try (Workbook workbook = new XSSFWorkbook(new FileInputStream(selectedFile))) {
                Sheet sheet = workbook.getSheetAt(0);
                // Headers: Mã NCC(0), Tên NCC(1), Loại SP(2), Năm HT(3), Địa chỉ(4), Email(5), SĐT(6), Trạng thái(7)
                // Original import: STT(0), Mã NCC(1), Tên NCC(2), Loại SP(3), Năm HT(4), Địa chỉ(5), Email(6), SĐT(7), Trạng thái(8)
                // Adjusting to current export:
                 List<String> existingMaNCCs = nhaCungCapDAO.getAllNhaCungCap().stream()
                                               .map(nhaCungCapDTO::getMaNhaCungCap)
                                               .collect(Collectors.toList());

                for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header row
                    Row row = sheet.getRow(i);
                    if (row == null) continue;
                    try {
                        String maNhaCungCap = getCellStringValue(row.getCell(0)); // Index adjusted
                        if (maNhaCungCap == null || maNhaCungCap.trim().isEmpty()) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Mã nhà cung cấp trống.\n");
                                failCount++;
                            continue;
                        }
                        if (existingMaNCCs.contains(maNhaCungCap)) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Mã nhà cung cấp '").append(maNhaCungCap).append("' đã tồn tại.\n");
                            failCount++;
                            continue;
                        }

                        nhaCungCapDTO ncc = new nhaCungCapDTO();
                        ncc.setMaNhaCungCap(maNhaCungCap);
                        ncc.setTenNhaCungCap(getCellStringValue(row.getCell(1)));
                        ncc.setLoaiSP(getCellStringValue(row.getCell(2)));
                        
                        Cell namHTCell = row.getCell(3);
                        if (namHTCell != null && namHTCell.getCellType() == CellType.NUMERIC) {
                            ncc.setNamHopTac((int)namHTCell.getNumericCellValue());
                        } else if (namHTCell != null && !getCellStringValue(namHTCell).trim().isEmpty()){
                            try {
                                ncc.setNamHopTac(Integer.parseInt(getCellStringValue(namHTCell)));
                            } catch (NumberFormatException e){
                                errorMessages.append("Dòng ").append(i + 1).append(": Lỗi định dạng Năm hợp tác '").append(getCellStringValue(namHTCell)).append("'.\n");
                                ncc.setNamHopTac(0); // Default or error
                            }
                        } else {
                            ncc.setNamHopTac(0); // Default if empty
                        }

                        ncc.setDiaChi(getCellStringValue(row.getCell(4)));
                        ncc.setEmail(getCellStringValue(row.getCell(5)));
                        ncc.setSoDienThoai(getCellStringValue(row.getCell(6)));
                        ncc.setTrangThai(getCellStringValue(row.getCell(7)));
                        
                        if (nhaCungCapBUS.themNhaCungCap(ncc)) { // themNhaCungCap from BUS
                                successCount++;
                            existingMaNCCs.add(maNhaCungCap);
                        } else {
                            errorMessages.append("Dòng ").append(i + 1).append(": Không thể thêm nhà cung cấp '").append(maNhaCungCap).append("'.\n");
                            failCount++;
                        }
                    } catch (Exception e) {
                        errorMessages.append("Dòng ").append(i + 1).append(": Lỗi xử lý - ").append(e.getMessage()).append("\n");
                        failCount++;
                    }
                }
                String message = String.format("Import hoàn tất!\nThành công: %d\nThất bại/Bỏ qua: %d", successCount, failCount);
                if (failCount > 0 || errorMessages.length() > "Chi tiết lỗi/bỏ qua:\n".length()) {
                    JTextArea textArea = new JTextArea(errorMessages.toString());
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
                    JOptionPane.showMessageDialog(null, scrollPane, "Kết quả Import Nhà Cung Cấp", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, message, "Kết quả Import Nhà Cung Cấp", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi import file Excel Nhà Cung Cấp: " + e.getMessage(), "Lỗi Import", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="KhachHang Specific File Operations">
    public static void exportToKhachHangExcel(JTable table, String title) {
        // exportToExcel(table, title);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel Khách Hàng");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File(title + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            KhachHangDAO khachHangDAO = new KhachHangDAO();
            List<khachHangDTO> khList = khachHangDAO.getAllKhachHang();

            String[] headers = {
                "Mã Khách Hàng", "Họ Tên", "Giới Tính", "Số Điện Thoại", "Email", "Địa Chỉ", "Ngày Sinh"
            };

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet(title);
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }

                int rowNum = 1;
                for (khachHangDTO kh : khList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(kh.getMaKhachHang());
                    row.createCell(1).setCellValue(kh.getHoTen());
                    row.createCell(2).setCellValue(kh.getGioiTinh());
                    row.createCell(3).setCellValue(kh.getSoDienThoai());
                    row.createCell(4).setCellValue(kh.getEmail());
                    row.createCell(5).setCellValue(kh.getDiaChi());
                    row.createCell(6).setCellValue(kh.getNgaySinh() != null ? genericDateFormat.format(kh.getNgaySinh()) : "");
                }

                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Excel Khách Hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel Khách Hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void exportToKhachHangWord(JTable table, String title) {
        // exportToWord(table, title);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Word Khách Hàng");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Word files (*.docx)", "docx"));
        fileChooser.setSelectedFile(new File(title + ".docx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".docx")) {
                filePath += ".docx";
            }
            
            KhachHangDAO khachHangDAO = new KhachHangDAO();
            List<khachHangDTO> khList = khachHangDAO.getAllKhachHang();

            try (XWPFDocument document = new XWPFDocument()) {
                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setBold(true);
                titleRun.setFontSize(16);
                titleRun.setText("DANH SÁCH KHÁCH HÀNG");
                titleRun.addBreak();

                for (khachHangDTO kh : khList) {
                    addParagraph(document, "Mã Khách Hàng: " + kh.getMaKhachHang());
                    addParagraph(document, "Họ Tên: " + kh.getHoTen());
                    addParagraph(document, "Giới Tính: " + kh.getGioiTinh());
                    addParagraph(document, "SĐT: " + kh.getSoDienThoai());
                    addParagraph(document, "Email: " + kh.getEmail());
                    addParagraph(document, "Địa Chỉ: " + kh.getDiaChi());
                    addParagraph(document, "Ngày Sinh: " + (kh.getNgaySinh() != null ? genericDateFormat.format(kh.getNgaySinh()) : ""));
                    document.createParagraph().createRun().addBreak();
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    document.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Word Khách Hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Word Khách Hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void importFromExcelForKhachHang(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel Khách Hàng để import (*.xlsx)");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            KhachHangBUS khachHangBUS = new KhachHangBUS();
            KhachHangDAO khachHangDAO = new KhachHangDAO(); // For checking existence
            int successCount = 0;
            int failCount = 0;
            StringBuilder errorMessages = new StringBuilder("Chi tiết lỗi/bỏ qua:\n");

            try (Workbook workbook = new XSSFWorkbook(new FileInputStream(selectedFile))) {
                Sheet sheet = workbook.getSheetAt(0);
                // Headers: Mã KH(0), Họ Tên(1), Giới Tính(2), SĐT(3), Email(4), Địa Chỉ(5), Ngày Sinh(6)
                // Original: STT(0), Mã KH(1), Họ Tên(2), Giới Tính(3), SĐT(4), Email(5), Địa Chỉ(6), Ngày Sinh(7)
                // Adjusting to current export:
                List<String> existingMaKHs = khachHangDAO.getAllMaKhachHang(); // From DAO

                for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header row
                    Row row = sheet.getRow(i);
                    if (row == null) continue;
                    try {
                        String maKhachHang = getCellStringValue(row.getCell(0)); // Index adjusted
                        if (maKhachHang == null || maKhachHang.trim().isEmpty()) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Mã khách hàng trống.\n");
                        failCount++;
                            continue;
                        }
                        if (existingMaKHs.contains(maKhachHang)) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Mã khách hàng '").append(maKhachHang).append("' đã tồn tại.\n");
                            failCount++;
                            continue;
                        }

                        khachHangDTO kh = new khachHangDTO();
                        kh.setMaKhachHang(maKhachHang);
                        kh.setHoTen(getCellStringValue(row.getCell(1)));
                        kh.setGioiTinh(getCellStringValue(row.getCell(2)));
                        kh.setSoDienThoai(getCellStringValue(row.getCell(3)));
                        kh.setEmail(getCellStringValue(row.getCell(4)));
                        kh.setDiaChi(getCellStringValue(row.getCell(5)));
                        
                        Cell ngaySinhCell = row.getCell(6); // Index adjusted
                        boolean khNgaySinhValid = true;
                        if (ngaySinhCell != null && !getCellStringValue(ngaySinhCell).trim().isEmpty()) {
                                String ngaySinhStr = getCellStringValue(ngaySinhCell);
                            try {
                                if (ngaySinhCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(ngaySinhCell)) {
                                    kh.setNgaySinh(new java.sql.Date(ngaySinhCell.getDateCellValue().getTime()));
                                } else if (ngaySinhStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) { // dd/MM/yyyy
                                    Date parsedUtilDate = genericDateFormat.parse(ngaySinhStr);
                                    kh.setNgaySinh(new java.sql.Date(parsedUtilDate.getTime()));
                                } else if (ngaySinhStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) { // yyyy-MM-dd
                                     Date parsedUtilDate = new SimpleDateFormat("yyyy-MM-dd").parse(ngaySinhStr);
                                            kh.setNgaySinh(new java.sql.Date(parsedUtilDate.getTime()));
                                        }
                                 else {
                                    errorMessages.append("Dòng ").append(i + 1).append(": Định dạng Ngày sinh '").append(ngaySinhStr).append("' không được hỗ trợ. Dự kiến dd/MM/yyyy hoặc yyyy-MM-dd.\n");
                                        kh.setNgaySinh(null); 
                                    khNgaySinhValid = false;
                                    }
                            } catch (java.text.ParseException pe) {
                                errorMessages.append("Dòng ").append(i + 1).append(": Lỗi định dạng Ngày sinh '").append(ngaySinhStr).append("'.\n");
                                     kh.setNgaySinh(null);
                                khNgaySinhValid = false;
                            }
                        } else {
                            kh.setNgaySinh(null);
                             // Depending on DB constraints, null might be an error.
                             // If NgaySinh is NOT NULL in DB, then khNgaySinhValid should be false.
                        }

                        if (!khNgaySinhValid) {
                            // Error message already appended for NgaySinh
                            // errorMessages.append("Dòng ").append(i + 1).append(": Khách hàng '").append(maKhachHang).append("' không được thêm do lỗi Ngày sinh.\n");
                            failCount++;
                            continue; 
                        }
                        
                        if (khachHangBUS.themKhachHang(kh)) {
                            successCount++;
                            existingMaKHs.add(maKhachHang);
                        } else {
                             errorMessages.append("Dòng ").append(i + 1).append(": Không thể thêm khách hàng '").append(maKhachHang).append("'.\n");
                            failCount++;
                }
            } catch (Exception e) {
                        errorMessages.append("Dòng ").append(i + 1).append(": Lỗi xử lý - ").append(e.getMessage()).append("\n");
                        failCount++;
                    }
                }
                String message = String.format("Import hoàn tất!\nThành công: %d\nThất bại/Bỏ qua: %d", successCount, failCount);
                if (failCount > 0 || errorMessages.length() > "Chi tiết lỗi/bỏ qua:\n".length()) {
                    JTextArea textArea = new JTextArea(errorMessages.toString());
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
                    JOptionPane.showMessageDialog(null, scrollPane, "Kết quả Import Khách Hàng", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, message, "Kết quả Import Khách Hàng", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi import file Excel Khách Hàng: " + e.getMessage(), "Lỗi Import", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Helper methods for cell values">
    private static String getCellStringValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                // Format numeric as string to avoid ".0" for integers
                return new DecimalFormat("#.##########").format(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                 try {
                    return cell.getStringCellValue().trim(); // Get cached formula value as string
                } catch (IllegalStateException e) { // If formula result is numeric
                    return new DecimalFormat("#.##########").format(cell.getNumericCellValue());
                }
            default:
                return "";
        }
    }

    private static double getCellNumericValue(Cell cell) {
        if (cell == null) return 0;
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue().trim());
            } catch (NumberFormatException e) {
                return 0; // Or throw error
            }
        }
        return 0;
    }
    //</editor-fold>

    // Remove old CSV import methods if they are fully replaced by Excel imports
    // public static void importFromCSVForProduct(JTable table) { ... }
    // public static void importFromCSVForKhuyenMai(JTable table) { ... } -> This was already specific, but used CSV. Now Excel.
    // public static void importFromCSVForHoaDon(JTable table) { ... }
    // public static void importFromCSV(JTable table) { ... } -> KhachHang's old generic CSV import
}