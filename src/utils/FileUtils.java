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
                    JOptionPane.showMessageDialog(null, "Xuất file Word thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Word: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void showExportOptions(JTable table, String title) {
        String[] options = {"Excel", "Word"};
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
                        for (int j = 0; j < model.getColumnCount(); j++) {
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
                        model.addRow(rowData);
                    }
                }
                JOptionPane.showMessageDialog(null, "Import dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi import dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 