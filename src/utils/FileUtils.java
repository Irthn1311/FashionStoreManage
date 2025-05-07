package utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import DTO.khachHangDTO;
import BUS.KhachHangBUS;

public class FileUtils {
    public static void exportToExcel(JTable table, String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files", "xlsx"));
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

                // Create header row
                Row headerRow = sheet.createRow(0);
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                for (int i = 0; i < model.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(model.getColumnName(i));
                }

                // Create data rows
                for (int i = 0; i < model.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Cell cell = row.createCell(j);
                        Object value = model.getValueAt(i, j);
                        if (value != null) {
                            cell.setCellValue(value.toString());
                        }
                    }
                }

                // Auto-size columns
                for (int i = 0; i < model.getColumnCount(); i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Excel thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel: " + e.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void exportToWord(JTable table, String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Word");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Word files", "docx"));
        fileChooser.setSelectedFile(new File(title + ".docx"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".docx")) {
                filePath += ".docx";
            }

            try (XWPFDocument document = new XWPFDocument()) {
                // Add title
                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setBold(true);
                titleRun.setFontSize(16);
                titleRun.setText(title);
                titleRun.addBreak();

                // Create table
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                XWPFTable wordTable = document.createTable(model.getRowCount() + 1, model.getColumnCount());

                // Add header row
                XWPFTableRow headerRow = wordTable.getRow(0);
                for (int i = 0; i < model.getColumnCount(); i++) {
                    headerRow.getCell(i).setText(model.getColumnName(i));
                }

                // Add data rows
                for (int i = 0; i < model.getRowCount(); i++) {
                    XWPFTableRow row = wordTable.getRow(i + 1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        row.getCell(j).setText(value == null ? "" : value.toString());
                    }
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    document.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất file Word thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Word: " + e.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
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

                // Ghi BOM để Excel nhận diện UTF-8
                writer.write("\uFEFF");

                // Ghi tiêu đề cột
                for (int i = 0; i < model.getColumnCount(); i++) {
                    writer.write("\"" + model.getColumnName(i) + "\"");
                    if (i < model.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.write("\n");

                // Ghi dữ liệu dòng
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        String cellValue = value == null ? "" : value.toString();
                        // Thoát các ký tự đặc biệt trong CSV
                        cellValue = "\"" + cellValue.replace("\"", "\"\"") + "\"";
                        writer.write(cellValue);
                        if (j < model.getColumnCount() - 1) {
                            writer.write(",");
                        }
                    }
                    writer.write("\n");
                }

                JOptionPane.showMessageDialog(null, "Xuất file CSV thành công!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file CSV: " + e.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void importFromFile(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file để import");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files", "xlsx", "xls"));

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (Workbook workbook = WorkbookFactory.create(selectedFile)) {
                Sheet sheet = workbook.getSheetAt(0);
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);

                // Skip header row
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        Object[] rowData = new Object[model.getColumnCount()];
                        for (int j = 0; j < model.getColumnCount() - 1; j++) {
                            Cell cell = row.getCell(j);
                            if (cell != null) {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        rowData[j] = cell.getStringCellValue();
                                        break;
                                    case NUMERIC:
                                        rowData[j] = cell.getNumericCellValue();
                                        break;
                                    default:
                                        rowData[j] = "";
                                }
                            }
                        }
                        // Add "Xem chi tiết" to the last column
                        rowData[model.getColumnCount() - 1] = "Xem chi tiết";
                        model.addRow(rowData);
                    }
                }
                JOptionPane.showMessageDialog(null, "Import dữ liệu thành công!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi import dữ liệu: " + e.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void importFromCSV(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file CSV để import");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV files", "csv"));

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();

                // Đọc header
                String headerLine = reader.readLine();
                if (headerLine == null) {
                    throw new IOException("File CSV trống");
                }

                // Đọc dữ liệu và lưu vào database
                String line;
                int successCount = 0;
                int failCount = 0;
                StringBuilder errorMessages = new StringBuilder();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                BUS.KhachHangBUS khachHangBUS = new BUS.KhachHangBUS();
                List<String> existingMaKHs = khachHangBUS.getAllMaKhachHang();

                while ((line = reader.readLine()) != null) {
                    try {
                        String[] values = parseCSVLine(line);
                        // Bỏ qua cột STT (0) và cột cuối ("Xem chi tiết")
                        if (values.length >= 9) {
                            String maKH = values[1];
                            String hoTen = values[2];
                            String gioiTinh = values[3];
                            String soDienThoai = values[4];
                            String email = values[5];
                            String diaChi = values[6];
                            String ngaySinhStr = values[7];
                            java.sql.Date ngaySinh = null;
                            if (ngaySinhStr != null && !ngaySinhStr.trim().isEmpty()
                                    && !ngaySinhStr.equals("Chưa cập nhật")) {
                                try {
                                    java.util.Date utilDate = sdf.parse(ngaySinhStr);
                                    ngaySinh = new java.sql.Date(utilDate.getTime());
                                } catch (Exception pe) {
                                    throw new Exception("Sai định dạng ngày sinh: " + ngaySinhStr);
                                }
                            }
                            if (existingMaKHs.contains(maKH)) {
                                failCount++;
                                errorMessages.append("Bỏ qua khách hàng trùng mã: ").append(maKH).append("\n");
                                continue;
                            }
                            khachHangDTO kh = new khachHangDTO(
                                    maKH, hoTen, gioiTinh, soDienThoai, email, diaChi, ngaySinh);
                            if (khachHangBUS.themKhachHang(kh)) {
                                successCount++;
                                existingMaKHs.add(maKH); // cập nhật danh sách đã tồn tại
                            } else {
                                failCount++;
                                errorMessages.append("Lỗi khi thêm khách hàng ").append(maKH).append("\n");
                            }
                        } else {
                            failCount++;
                            errorMessages.append("Sai số lượng cột: ").append(line).append("\n");
                        }
                    } catch (Exception e) {
                        failCount++;
                        errorMessages.append("Lỗi xử lý dòng: ").append(line).append("\n");
                        errorMessages.append("Chi tiết lỗi: ").append(e.getMessage()).append("\n");
                    }
                }

                // Hiển thị kết quả import
                String message = String.format(
                        "Import hoàn tất!\nSố bản ghi thành công: %d\nSố bản ghi bị bỏ qua/trùng/lỗi: %d",
                        successCount, failCount);

                if (failCount > 0) {
                    message += "\n\nChi tiết lỗi:\n" + errorMessages.toString();
                    JOptionPane.showMessageDialog(null, message, "Kết quả import", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, message, "Kết quả import", JOptionPane.INFORMATION_MESSAGE);
                }

                // Cập nhật lại bảng
                model.setRowCount(0);
                List<DTO.khachHangDTO> danhSachKhachHang = khachHangBUS.getAllKhachHang();
                for (DTO.khachHangDTO kh : danhSachKhachHang) {
                    model.addRow(new Object[] {
                            model.getRowCount() + 1,
                            kh.getMaKhachHang(),
                            kh.getHoTen(),
                            kh.getGioiTinh(),
                            kh.getSoDienThoai(),
                            kh.getEmail(),
                            kh.getDiaChi(),
                            kh.getNgaySinh() != null
                                    ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(kh.getNgaySinh())
                                    : "Chưa cập nhật",
                            "Xem chi tiết"
                    });
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi import dữ liệu: " + e.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static String[] parseCSVLine(String line) {
        List<String> values = new java.util.ArrayList<>();
        StringBuilder currentValue = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    // Xử lý trường hợp dấu ngoặc kép được escape
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

    public static void showExportOptions(JTable table, String title) {
        String[] options = { "Excel", "Word", "CSV" };
        int choice = JOptionPane.showOptionDialog(null,
                "Chọn định dạng file xuất",
                "Xuất file",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            exportToExcel(table, title);
        } else if (choice == 1) {
            exportToWord(table, title);
        } else if (choice == 2) {
            exportToCSV(table, title);
        }
    }
}