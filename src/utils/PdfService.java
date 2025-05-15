package utils;

import DTO.khachHangDTO;
import DTO.xuatHangDTO; // Sử dụng xuatHangDTO vì nó chứa thông tin chi tiết từng mục hàng chờ xuất
import java.util.List;
import java.io.File; // Added for font file handling
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont; // Changed from PDType1Font
import org.apache.pdfbox.pdmodel.font.PDType0Font; // Added for TTF font loading
// import org.apache.pdfbox.pdmodel.font.PDType1Font; // No longer using Standard14Fonts directly for text
// import org.apache.pdfbox.pdmodel.font.Standard14Fonts; // No longer using Standard14Fonts directly for text
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PdfService {

    private static final float MARGIN = 50;
    private static final float FONT_SIZE_NORMAL = 10;
    private static final float FONT_SIZE_LARGE = 16;
    private static final float FONT_SIZE_MEDIUM = 12;
    private static final float LEADING_NORMAL = -1.2f * FONT_SIZE_NORMAL;
    private static final float ROW_HEIGHT_TABLE = 20f;

    // --- IMPORTANT: USER ACTION REQUIRED ---
    // Please provide paths to .ttf font files that support Vietnamese characters.
    // Create a 'fonts' directory in your project (e.g., at the root or in src/main/resources)
    // and place your .ttf files there. Then, update these paths accordingly.
    // Example: "fonts/Arial.ttf" or "src/main/resources/fonts/Arial.ttf"
    // You'll need a regular and a bold version of the font.
    private static final String FONT_REGULAR_PATH = "font/static/Roboto-Regular.ttf"; // PLEASE REPLACE WITH YOUR FONT FILE
    private static final String FONT_BOLD_PATH = "font/static/Roboto-Bold.ttf";   // PLEASE REPLACE WITH YOUR BOLD FONT FILE
    // --- END USER ACTION REQUIRED ---

    private static float drawTableHeader(PDPageContentStream contentStream, PDFont fontBold, float yPosition, float[] columnWidths, String[] headers) throws IOException {
        contentStream.setFont(fontBold, FONT_SIZE_NORMAL);
        float currentX = MARGIN;
        for (int i = 0; i < headers.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(currentX + 2, yPosition - 15); // +2 for padding
            contentStream.showText(headers[i]);
            contentStream.endText();
            currentX += columnWidths[i];
        }
        return yPosition - (ROW_HEIGHT_TABLE * 1.5f); // Khoảng cách sau header
    }

    /**
     * Tạo file PDF hóa đơn.
     * @param items Danh sách các sản phẩm trong phiếu xuất (sẽ trở thành chi tiết hóa đơn).
     * @param customer Thông tin khách hàng.
     * @param filePath Đường dẫn đầy đủ để lưu file PDF.
     * @return true nếu tạo PDF thành công, false nếu thất bại.
     */
    public static boolean generateInvoicePdf(List<xuatHangDTO> items, khachHangDTO customer, String filePath) {
        PDDocument document = new PDDocument();
        PDPageContentStream contentStream = null;
        PDFont fontRegular = null;
        PDFont fontBold = null;

        try {
            // Load fonts
            // Ensure the font files exist at the specified paths and your application has permission to read them.
            try {
                File fontFileRegular = new File(FONT_REGULAR_PATH);
                File fontFileBold = new File(FONT_BOLD_PATH);
                if (!fontFileRegular.exists()) {
                    System.err.println("Lỗi: Không tìm thấy file font thường tại: " + fontFileRegular.getAbsolutePath());
                    System.err.println("Vui lòng kiểm tra lại đường dẫn FONT_REGULAR_PATH trong PdfService.java");
                     // Fallback or throw error - for now, let it proceed to see if PDFBox handles it gracefully or throws
                }
                 if (!fontFileBold.exists()) {
                    System.err.println("Lỗi: Không tìm thấy file font đậm tại: " + fontFileBold.getAbsolutePath());
                    System.err.println("Vui lòng kiểm tra lại đường dẫn FONT_BOLD_PATH trong PdfService.java");
                }
                fontRegular = PDType0Font.load(document, fontFileRegular);
                fontBold = PDType0Font.load(document, fontFileBold);
            } catch (IOException e) {
                System.err.println("Lỗi nghiêm trọng khi tải font: " + e.getMessage());
                e.printStackTrace();
                // If fonts cannot be loaded, we cannot proceed to create a meaningful PDF with text.
                // You might want to create a very basic PDF with an error message or return false immediately.
                // For now, returning false.
                return false;
            }


            PDPage currentPage = new PDPage(PDRectangle.A4);
            document.addPage(currentPage);
            
            DecimalFormat df = new DecimalFormat("#,###");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            contentStream = new PDPageContentStream(document, currentPage);
            
            float yPosition = currentPage.getMediaBox().getHeight() - MARGIN;
            float tableTopY;

            // Header cửa hàng (ví dụ)
            contentStream.beginText();
            contentStream.setFont(fontBold, FONT_SIZE_LARGE);
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("FASHION STORE IRTHN"); // Thay bằng tên cửa hàng thực tế
            contentStream.endText();
            yPosition -= FONT_SIZE_LARGE * 1.5f;

            contentStream.beginText();
            contentStream.setFont(fontRegular, FONT_SIZE_NORMAL);
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Địa chỉ: 273 An Dương Vương, Quận 5, TP. HCM"); // Địa chỉ cửa hàng
            contentStream.newLineAtOffset(0, LEADING_NORMAL);
            contentStream.showText("Điện thoại: 058 283 7353"); // SĐT cửa hàng
            contentStream.newLineAtOffset(0, LEADING_NORMAL);
            contentStream.showText("Email: nhtri1311@gmail.com"); // Email cửa hàng
            contentStream.endText();
            yPosition -= (FONT_SIZE_NORMAL * 1.5f) * 3;

            // Tiêu đề hóa đơn
            contentStream.beginText();
            contentStream.setFont(fontBold, FONT_SIZE_LARGE);
            String invoiceTitle = "HÓA ĐƠN BÁN HÀNG";
            float titleWidth = fontBold.getStringWidth(invoiceTitle) / 1000 * FONT_SIZE_LARGE;
            contentStream.newLineAtOffset((currentPage.getMediaBox().getWidth() - titleWidth) / 2, yPosition);
            contentStream.showText(invoiceTitle);
            contentStream.endText();
            yPosition -= FONT_SIZE_LARGE * 2;

            // Thông tin khách hàng và hóa đơn
            String invoiceId = items.isEmpty() || items.get(0).getMaPX() == null ? "N/A" : items.get(0).getMaPX();
            contentStream.beginText();
            contentStream.setFont(fontBold, FONT_SIZE_NORMAL);
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Khách hàng: ");
            contentStream.setFont(fontRegular, FONT_SIZE_NORMAL);
            contentStream.showText(customer.getHoTen() != null ? customer.getHoTen() : "N/A");
            contentStream.endText();
            
            float rightAlignX = currentPage.getMediaBox().getWidth() - MARGIN - 150; 
            contentStream.beginText();
            contentStream.setFont(fontBold, FONT_SIZE_NORMAL);
            contentStream.newLineAtOffset(rightAlignX, yPosition);
            contentStream.showText("Số HĐ: ");
            contentStream.setFont(fontRegular, FONT_SIZE_NORMAL);
            contentStream.showText(invoiceId);
            contentStream.endText();
            yPosition += LEADING_NORMAL;

            contentStream.beginText();
            contentStream.setFont(fontBold, FONT_SIZE_NORMAL);
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Địa chỉ: ");
            contentStream.setFont(fontRegular, FONT_SIZE_NORMAL);
            contentStream.showText(customer.getDiaChi() != null ? customer.getDiaChi() : "N/A");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(fontBold, FONT_SIZE_NORMAL);
            contentStream.newLineAtOffset(rightAlignX, yPosition);
            contentStream.showText("Ngày HĐ: ");
            contentStream.setFont(fontRegular, FONT_SIZE_NORMAL);
            contentStream.showText(sdf.format(new Date()));
            contentStream.endText();
            yPosition += LEADING_NORMAL;

            contentStream.beginText();
            contentStream.setFont(fontBold, FONT_SIZE_NORMAL);
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Email: ");
            contentStream.setFont(fontRegular, FONT_SIZE_NORMAL);
            contentStream.showText(customer.getEmail() != null ? customer.getEmail() : "N/A");
            contentStream.endText();
            yPosition += LEADING_NORMAL;
            
            contentStream.beginText();
            contentStream.setFont(fontBold, FONT_SIZE_NORMAL);
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("SĐT: ");
            contentStream.setFont(fontRegular, FONT_SIZE_NORMAL);
            contentStream.showText(customer.getSoDienThoai() != null ? customer.getSoDienThoai() : "N/A");
            contentStream.endText();
            yPosition -= (FONT_SIZE_NORMAL * 2.5f); 

            // Bảng chi tiết sản phẩm
            tableTopY = yPosition;
            float tableWidth = currentPage.getMediaBox().getWidth() - 2 * MARGIN;
            float[] columnWidths = {30, tableWidth * 0.30f, tableWidth * 0.08f, tableWidth*0.10f, tableWidth*0.10f, tableWidth * 0.21f, tableWidth * 0.21f};
            String[] headers = {"STT", "Tên Sản Phẩm", "SL", "K.Cỡ", "Màu", "Đơn Giá (VNĐ)", "Thành Tiền (VNĐ)"};
            
            yPosition = drawTableHeader(contentStream, fontBold, yPosition, columnWidths, headers);

            contentStream.setFont(fontRegular, FONT_SIZE_NORMAL);
            double tongCong = 0;
            int stt = 1;
            for (xuatHangDTO item : items) {
                if (yPosition < MARGIN + ROW_HEIGHT_TABLE * 2) { 
                    contentStream.close(); 
                    currentPage = new PDPage(PDRectangle.A4);
                    document.addPage(currentPage);
                    contentStream = new PDPageContentStream(document, currentPage); 
                    yPosition = currentPage.getMediaBox().getHeight() - MARGIN;
                    tableTopY = yPosition; 
                    yPosition = drawTableHeader(contentStream, fontBold, yPosition, columnWidths, headers);
                    contentStream.setFont(fontRegular, FONT_SIZE_NORMAL);
                }

                float currentX = MARGIN;
                String kichCo = item.getKichThuoc() != null ? item.getKichThuoc() : "-";
                String mauSac = item.getMauSac() != null ? item.getMauSac() : "-";
                String[] rowData = {
                    String.valueOf(stt++),
                    item.getTenSanPham() != null ? item.getTenSanPham() : "",
                    item.getSoLuong() != null ? item.getSoLuong() : "0",
                    kichCo,
                    mauSac,
                    item.getDonGia() != null ? df.format(Double.parseDouble(item.getDonGia())) : "0",
                    item.getThanhTien() != null ? df.format(Double.parseDouble(item.getThanhTien())) : "0"
                };
                for (int i = 0; i < rowData.length; i++) {
                    contentStream.beginText();
                    float textWidth = fontRegular.getStringWidth(rowData[i]) / 1000 * FONT_SIZE_NORMAL;
                    float xOffset = currentX + 2; 
                    if (i == 2 || i >= 5) { 
                        xOffset = currentX + columnWidths[i] - textWidth - 2; 
                    }
                    contentStream.newLineAtOffset(xOffset, yPosition - 15);
                    contentStream.showText(rowData[i]);
                    contentStream.endText();
                    currentX += columnWidths[i];
                }
                yPosition -= ROW_HEIGHT_TABLE;
                try {
                    tongCong += Double.parseDouble(item.getThanhTien());
                } catch (NumberFormatException e) {
                    System.err.println("Lỗi parse Thành tiền cho PDF table: " + item.getThanhTien());
                }
            }
            
            float tableBottomY = yPosition + ROW_HEIGHT_TABLE; 
            contentStream.setLineWidth(0.5f);
            contentStream.moveTo(MARGIN, tableTopY -ROW_HEIGHT_TABLE/2 + 5); 
            contentStream.lineTo(MARGIN + tableWidth, tableTopY -ROW_HEIGHT_TABLE/2 + 5);
            contentStream.stroke();
            
            contentStream.moveTo(MARGIN, tableBottomY); 
            contentStream.lineTo(MARGIN + tableWidth, tableBottomY);
            contentStream.stroke();
            
            float tempX = MARGIN;
            for (int i=0; i < columnWidths.length; i++) {
                contentStream.moveTo(tempX, tableTopY -ROW_HEIGHT_TABLE/2 + 5);
                contentStream.lineTo(tempX, tableBottomY);
                contentStream.stroke();
                tempX += columnWidths[i];
            }
            contentStream.moveTo(MARGIN + tableWidth, tableTopY -ROW_HEIGHT_TABLE/2 + 5); 
            contentStream.lineTo(MARGIN + tableWidth, tableBottomY);
            contentStream.stroke();

            // Tổng cộng
            yPosition = tableBottomY - ROW_HEIGHT_TABLE * 1.5f;
            contentStream.beginText();
            contentStream.setFont(fontBold, FONT_SIZE_MEDIUM);
            String tongCongStr = "Tổng cộng: " + df.format(tongCong) + " VNĐ";
            float tongCongWidth = fontBold.getStringWidth(tongCongStr) / 1000 * FONT_SIZE_MEDIUM;
            contentStream.newLineAtOffset(currentPage.getMediaBox().getWidth() - MARGIN - tongCongWidth, yPosition);
            contentStream.showText(tongCongStr);
            contentStream.endText();
            yPosition -= FONT_SIZE_MEDIUM * 2;

            // Lời cảm ơn
            if (yPosition < MARGIN + FONT_SIZE_NORMAL * 2) { 
                 contentStream.close();
                 currentPage = new PDPage(PDRectangle.A4);
                 document.addPage(currentPage);
                 contentStream = new PDPageContentStream(document, currentPage);
                 yPosition = currentPage.getMediaBox().getHeight() - MARGIN;
                 // Cần set lại font cho contentStream mới
                 // contentStream.setFont(fontRegular, FONT_SIZE_NORMAL); // Đã được xử lý trong vòng lặp hoặc trước đó nếu cần thiết
            }
            contentStream.beginText();
            contentStream.setFont(fontRegular, FONT_SIZE_NORMAL); // Đảm bảo font được set trước khi showText
            String thankYouMsg = "Cảm ơn quý khách đã mua hàng!";
            float thankYouWidth = fontRegular.getStringWidth(thankYouMsg) / 1000 * FONT_SIZE_NORMAL;
            contentStream.newLineAtOffset((currentPage.getMediaBox().getWidth() - thankYouWidth) / 2, yPosition);
            contentStream.showText(thankYouMsg);
            contentStream.endText();
            
            contentStream.close(); 

            document.save(filePath);
            System.out.println("DEBUG: Hóa đơn PDF đã được tạo tại: " + filePath);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lỗi IO khi tạo file PDF: " + e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Lỗi định dạng số khi tạo PDF: " + e.getMessage());
            return false;
        // } catch (IllegalArgumentException e) { // Bắt lỗi font cụ thể nếu cần
        //     e.printStackTrace();
        //     System.err.println("Lỗi font: " + e.getMessage() + ". Đảm bảo font hỗ trợ ký tự yêu cầu và đường dẫn font đúng.");
        //     return false;
        } finally {
            if (contentStream != null) {
                try {
                    contentStream.close();
                } catch (IOException e) {
                    // Log hoặc bỏ qua lỗi khi đóng stream phụ
                }
            }
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Lỗi khi đóng PDDocument: " + e.getMessage());
                }
            }
        }
    }
} 