package utils;

import DTO.khachHangDTO;
import DTO.xuatHangDTO;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class PrintService {

    private static final String FONT_FAMILY = "Arial"; // Nên dùng font có sẵn trên hầu hết hệ thống hoặc font đã nhúng trong PDF
    private static final int FONT_SIZE_NORMAL = 10;
    private static final int FONT_SIZE_LARGE = 16;
    private static final int FONT_SIZE_MEDIUM = 12;
    private static final float LEADING_NORMAL_PRINT = FONT_SIZE_NORMAL * 1.2f;
    private static final float ROW_HEIGHT_TABLE_PRINT = FONT_SIZE_NORMAL * 2f; // Cao hơn chút cho dễ đọc

    public static boolean printInvoice(List<xuatHangDTO> items, khachHangDTO customer) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new InvoicePrintable(items, customer));

        if (job.printDialog()) {
            try {
                job.print();
                return true;
            } catch (PrinterException ex) {
                System.err.println("Lỗi khi in hóa đơn: " + ex.getMessage());
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện in: " + ex.getMessage(), "Lỗi In Ấn", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false; // User cancelled the print dialog
    }

    private static class InvoicePrintable implements Printable {
        private final List<xuatHangDTO> items;
        private final khachHangDTO customer;
        private final DecimalFormat df = new DecimalFormat("#,###");
        private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        private final Font fontRegular;
        private final Font fontBold;
        private final Font fontLargeBold;
        private final Font fontMediumBold;
        
        // List to hold lines of text for the entire bill, for pagination
        private List<String[]> billPagesText; 
        private int currentPageIndex = -1; // Track which "page" of text lines we are on
        private List<List<String[]>> paginatedItems; // Paginated product items


        public InvoicePrintable(List<xuatHangDTO> items, khachHangDTO customer) {
            this.items = items;
            this.customer = customer;
            this.fontRegular = new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE_NORMAL);
            this.fontBold = new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE_NORMAL);
            this.fontLargeBold = new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE_LARGE);
            this.fontMediumBold = new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE_MEDIUM);
        }

        @Override
        public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            FontRenderContext frc = g2d.getFontRenderContext();

            float yPosition = 20; // Start a bit lower from the top margin
            float margin = (float) pf.getImageableX() > 30 ? (float) pf.getImageableX() : 30; // Use page format margin or default
            float pageHeight = (float) pf.getImageableHeight();
            float pageWidth = (float) pf.getImageableWidth();


            if (paginatedItems == null) { // First time print or first page
                paginateItems(g2d, pf); // Calculate pagination for items
            }
            
            if (pageIndex >= paginatedItems.size()) {
                return NO_SUCH_PAGE;
            }

            // === Vẽ Header Cửa Hàng ===
            g2d.setFont(fontLargeBold);
            String storeName = "FASHION STORE IRTHN"; // Cần cập nhật tên cửa hàng
            drawCenteredString(g2d, storeName, pageWidth / 2, yPosition);
            yPosition += getStringHeight(storeName, fontLargeBold, frc) * 1.5f;

            g2d.setFont(fontRegular);
            String address = "Địa chỉ: 273 An Dương Vương, Quận 5, TP. HCM"; // Cập nhật
            drawCenteredString(g2d, address, pageWidth / 2, yPosition);
            yPosition += getStringHeight(address, fontRegular, frc);
            String phone = "Điện thoại: 058 283 7353"; // Cập nhật
            drawCenteredString(g2d, phone, pageWidth / 2, yPosition);
            yPosition += getStringHeight(phone, fontRegular, frc);
            String emailStore = "Email: nhtri1311@gmail.com"; // Cập nhật
            drawCenteredString(g2d, emailStore, pageWidth / 2, yPosition);
            yPosition += getStringHeight(emailStore, fontRegular, frc) * 2f;


            // === Tiêu đề hóa đơn ===
            g2d.setFont(fontLargeBold);
            String invoiceTitle = "HÓA ĐƠN BÁN HÀNG";
            drawCenteredString(g2d, invoiceTitle, pageWidth / 2, yPosition);
            yPosition += getStringHeight(invoiceTitle, fontLargeBold, frc) * 2f;

            // === Thông tin khách hàng và hóa đơn ===
            String invoiceId = items.isEmpty() || items.get(0).getMaPX() == null ? "N/A" : items.get(0).getMaPX();
            
            g2d.setFont(fontBold);
            g2d.drawString("Khách hàng:", margin, yPosition);
            g2d.setFont(fontRegular);
            g2d.drawString(customer.getHoTen() != null ? customer.getHoTen() : "N/A", margin + 80, yPosition);
            
            g2d.setFont(fontBold);
            g2d.drawString("Số HĐ:", pageWidth - margin - 100, yPosition);
            g2d.setFont(fontRegular);
            g2d.drawString(invoiceId, pageWidth - margin - 50, yPosition);
            yPosition += LEADING_NORMAL_PRINT * 1.2f;

            g2d.setFont(fontBold);
            g2d.drawString("Địa chỉ:", margin, yPosition);
            g2d.setFont(fontRegular);
            g2d.drawString(customer.getDiaChi() != null ? customer.getDiaChi() : "N/A", margin + 80, yPosition);

            g2d.setFont(fontBold);
            g2d.drawString("Ngày HĐ:", pageWidth - margin - 100, yPosition);
            g2d.setFont(fontRegular);
            g2d.drawString(sdf.format(new Date()), pageWidth - margin - 50, yPosition);
            yPosition += LEADING_NORMAL_PRINT * 1.2f;

            g2d.setFont(fontBold);
            g2d.drawString("Email:", margin, yPosition);
            g2d.setFont(fontRegular);
            g2d.drawString(customer.getEmail() != null ? customer.getEmail() : "N/A", margin + 80, yPosition);
            yPosition += LEADING_NORMAL_PRINT * 1.2f;

            g2d.setFont(fontBold);
            g2d.drawString("SĐT:", margin, yPosition);
            g2d.setFont(fontRegular);
            g2d.drawString(customer.getSoDienThoai() != null ? customer.getSoDienThoai() : "N/A", margin + 80, yPosition);
            yPosition += LEADING_NORMAL_PRINT * 2.5f;


            // === Bảng chi tiết sản phẩm ===
            float tableTopY = yPosition;
            float tableUsableWidth = pageWidth - 2 * margin;
            float[] columnWidths = {
                tableUsableWidth * 0.08f, // STT
                tableUsableWidth * 0.35f, // Tên SP
                tableUsableWidth * 0.10f, // SL
                tableUsableWidth * 0.12f, // K.Cỡ
                tableUsableWidth * 0.10f, // Màu
                tableUsableWidth * 0.125f, // Đơn Giá
                tableUsableWidth * 0.125f  // Thành Tiền
            };
            String[] headers = {"STT", "Tên SP", "SL", "K.Cỡ", "Màu", "Đ.Giá", "T.Tiền"};

            // Vẽ header bảng
            g2d.setFont(fontBold);
            float currentX = margin;
            for (int i = 0; i < headers.length; i++) {
                g2d.drawString(headers[i], currentX + 2, yPosition);
                currentX += columnWidths[i];
            }
            yPosition += LEADING_NORMAL_PRINT * 1.5f;
            float headerBottomY = yPosition - (LEADING_NORMAL_PRINT * 0.5f) ;
            g2d.drawLine((int)margin, (int)headerBottomY, (int)(pageWidth - margin), (int)headerBottomY);


            // Vẽ các dòng sản phẩm cho trang hiện tại
            g2d.setFont(fontRegular);
            List<String[]> itemsForCurrentPage = paginatedItems.get(pageIndex);
            for (String[] rowData : itemsForCurrentPage) {
                currentX = margin;
                for (int i = 0; i < rowData.length; i++) {
                    float xOffset = currentX + 2;
                    if (i == 2 || i >= 5) { // SL, Đơn giá, Thành tiền - canh phải
                         float textWidth = (float) g2d.getFontMetrics().stringWidth(rowData[i]);
                         xOffset = currentX + columnWidths[i] - textWidth - 2;
                    }
                    g2d.drawString(rowData[i], xOffset, yPosition);
                    currentX += columnWidths[i];
                }
                yPosition += ROW_HEIGHT_TABLE_PRINT * 0.8f; // Giảm khoảng cách dòng chút
            }
            
            float tableBottomY = yPosition - (ROW_HEIGHT_TABLE_PRINT * 0.2f);
             g2d.drawLine((int)margin, (int)tableBottomY, (int)(pageWidth - margin), (int)tableBottomY);


            // === Tổng cộng (chỉ hiển thị ở trang cuối) ===
            if (pageIndex == paginatedItems.size() - 1) {
                double tongCong = 0;
                for (xuatHangDTO item : this.items) { // Tính tổng từ list gốc
                    try {
                        tongCong += Double.parseDouble(item.getThanhTien());
                    } catch (NumberFormatException e) { /* ignore */ }
                }
                yPosition = pageHeight - margin - (LEADING_NORMAL_PRINT * 4); // Vị trí gần cuối trang
                
                g2d.setFont(fontMediumBold);
                String tongCongStr = "Tổng cộng: " + df.format(tongCong) + " VNĐ";
                float tongCongWidth = (float) g2d.getFontMetrics().stringWidth(tongCongStr);
                g2d.drawString(tongCongStr, pageWidth - margin - tongCongWidth, yPosition);
                yPosition += LEADING_NORMAL_PRINT * 2f;

                // === Lời cảm ơn (chỉ hiển thị ở trang cuối) ===
                g2d.setFont(fontRegular);
                String thankYouMsg = "Cảm ơn quý khách đã mua hàng!";
                drawCenteredString(g2d, thankYouMsg, pageWidth / 2, yPosition);
            }
            
            // Vẽ số trang
            g2d.setFont(fontRegular);
            String pageNumStr = "Trang " + (pageIndex + 1) + "/" + paginatedItems.size();
            drawCenteredString(g2d, pageNumStr, pageWidth / 2, pageHeight - margin /2 );


            return PAGE_EXISTS;
        }
        
        private float getStringWidth(String text, Font font, FontRenderContext frc) {
            return (float) font.getStringBounds(text, frc).getWidth();
        }

        private float getStringHeight(String text, Font font, FontRenderContext frc) {
            return (float) font.getStringBounds(text, frc).getHeight();
        }
        
        private void drawCenteredString(Graphics2D g2d, String text, float centerX, float y) {
            float textWidth = (float) g2d.getFontMetrics(g2d.getFont()).stringWidth(text);
            g2d.drawString(text, centerX - textWidth / 2, y);
        }

        private void paginateItems(Graphics2D g2dForMetrics, PageFormat pf) {
            paginatedItems = new ArrayList<>();
            List<String[]> currentPageItemsData = new ArrayList<>();
            
            float margin = (float) pf.getImageableX() > 30 ? (float) pf.getImageableX() : 30;
            float pageHeight = (float) pf.getImageableHeight();
            // Estimate starting Y after headers, adjust this based on actual header drawing
            float yPosition = 20 + getStringHeight("Header", fontLargeBold, g2dForMetrics.getFontRenderContext()) * 5.5f + // Store info
                              getStringHeight("Title", fontLargeBold, g2dForMetrics.getFontRenderContext()) * 2f + // Title
                              (LEADING_NORMAL_PRINT * 1.2f) * 4 + LEADING_NORMAL_PRINT * 2.5f + // Customer info
                              LEADING_NORMAL_PRINT * 1.5f; // Table header

            float availableHeightForItems = pageHeight - yPosition - margin - (LEADING_NORMAL_PRINT * 6); // Reserve space for total and footer

            int stt = 1;
            for (xuatHangDTO item : this.items) {
                if (yPosition + (ROW_HEIGHT_TABLE_PRINT * 0.8f) > pageHeight - margin - (LEADING_NORMAL_PRINT * 6) ) { // Check if new item fits
                    paginatedItems.add(new ArrayList<>(currentPageItemsData));
                    currentPageItemsData.clear();
                    yPosition = 20 + getStringHeight("Header", fontLargeBold, g2dForMetrics.getFontRenderContext()) * 5.5f +
                                getStringHeight("Title", fontLargeBold, g2dForMetrics.getFontRenderContext()) * 2f +
                                (LEADING_NORMAL_PRINT * 1.2f) * 4 + LEADING_NORMAL_PRINT * 2.5f +
                                LEADING_NORMAL_PRINT * 1.5f; // Reset Y for new page
                }

                String kichCo = item.getKichThuoc() != null ? item.getKichThuoc() : "-";
                String mauSac = item.getMauSac() != null ? item.getMauSac() : "-";
                String donGiaStr = item.getDonGia() != null ? df.format(Double.parseDouble(item.getDonGia())) : "0";
                String thanhTienStr = item.getThanhTien() != null ? df.format(Double.parseDouble(item.getThanhTien())) : "0";

                currentPageItemsData.add(new String[]{
                    String.valueOf(stt++),
                    item.getTenSanPham() != null ? item.getTenSanPham() : "",
                    item.getSoLuong() != null ? item.getSoLuong() : "0",
                    kichCo,
                    mauSac,
                    donGiaStr,
                    thanhTienStr
                });
                yPosition += ROW_HEIGHT_TABLE_PRINT * 0.8f;
            }
            if (!currentPageItemsData.isEmpty()) {
                paginatedItems.add(currentPageItemsData); // Add the last page
            }

             if (paginatedItems.isEmpty() && !this.items.isEmpty()) {
                // This case might happen if availableHeightForItems is too small from the start for even one item.
                // Or if items list is not empty but somehow no pages were added.
                // Add at least one page, even if it might overflow, or handle error.
                // For now, just add current items if any, assuming they should fit a page.
                paginatedItems.add(new ArrayList<>(currentPageItemsData));
                 if (currentPageItemsData.isEmpty()) { // If still empty and items exist, force add them.
                     List<String[]> forcePage = new ArrayList<>();
                      stt = 1; // reset stt for this forced page
                     for (xuatHangDTO item : this.items) {
                          String kichCo = item.getKichThuoc() != null ? item.getKichThuoc() : "-";
                          String mauSac = item.getMauSac() != null ? item.getMauSac() : "-";
                          String donGiaStr = item.getDonGia() != null ? df.format(Double.parseDouble(item.getDonGia())) : "0";
                          String thanhTienStr = item.getThanhTien() != null ? df.format(Double.parseDouble(item.getThanhTien())) : "0";
                           forcePage.add(new String[]{
                                String.valueOf(stt++),
                                item.getTenSanPham() != null ? item.getTenSanPham() : "",
                                item.getSoLuong() != null ? item.getSoLuong() : "0",
                                kichCo,
                                mauSac,
                                donGiaStr,
                                thanhTienStr
                            });
                     }
                     if (!forcePage.isEmpty()) paginatedItems.add(forcePage);
                 }
            }
            if (paginatedItems.isEmpty() && this.items.isEmpty()){ // No items to print
                 paginatedItems.add(new ArrayList<>()); // Add one empty page data so print method doesn't fail for no_such_page immediately.
            }

        }
    }
} 