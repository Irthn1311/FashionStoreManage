package utils;

import DTO.khachHangDTO;
import DTO.xuatHangDTO;
import java.io.File;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailService {

    // Thông tin cấu hình SMTP - CẦN ĐƯỢC CẤU HÌNH ĐÚNG
    // Bạn nên lưu trữ các thông tin này trong một file config hoặc cho người dùng
    // nhập
    private static final String SMTP_HOST = "smtp.gmail.com"; // Ví dụ: smtp.gmail.com
    private static final String SMTP_PORT = "587"; // Ví dụ: 587 (TLS) hoặc 465 (SSL)
    private static final String SMTP_USER = "nhuutri1311@gmail.com"; // Email người gửi
    private static final String SMTP_PASSWORD = "ytla zgcz cjsm nidt"; // Mật khẩu ứng dụng nếu dùng Gmail
    private static final boolean SMTP_AUTH = true;
    private static final boolean SMTP_STARTTLS = true;

    /**
     * Gửi email hóa đơn cho khách hàng.
     * 
     * @param customer      Thông tin khách hàng (để lấy email và tên).
     * @param items         Danh sách các sản phẩm trong hóa đơn.
     * @param pdfAttachment File PDF hóa đơn đính kèm (có thể null).
     * @return true nếu gửi email thành công, false nếu thất bại.
     */
    public static boolean sendInvoiceByEmail(khachHangDTO customer, List<xuatHangDTO> items, File pdfAttachment) {
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            System.err.println("Không thể gửi email: Địa chỉ email khách hàng trống.");
            return false;
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", String.valueOf(SMTP_AUTH));
        props.put("mail.smtp.starttls.enable", String.valueOf(SMTP_STARTTLS));

        Authenticator auth = null;
        if (SMTP_AUTH) {
            auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
                }
            };
        }

        Session session = Session.getInstance(props, auth);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customer.getEmail()));
            message.setSubject("Hóa đơn mua hàng từ FASHION STORE IRTHN");

            // Nội dung email (HTML)
            StringBuilder emailContentHtml = new StringBuilder();
            emailContentHtml.append("<html><head><style>");
            emailContentHtml.append(
                    "body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; color: #333; }");
            emailContentHtml.append(
                    ".container { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); max-width: 700px; margin: auto; }");
            emailContentHtml
                    .append(".header { text-align: center; padding-bottom: 20px; border-bottom: 1px solid #eee; }");
            emailContentHtml.append(".header h1 { color: #333; margin:0; font-size: 24px; }");
            emailContentHtml.append(".store-name { font-size: 28px; color: #007bff; margin-bottom: 10px; }");
            emailContentHtml.append("h2 { color: #007bff; margin-top: 0; }");
            emailContentHtml.append("p { line-height: 1.6; }");
            emailContentHtml
                    .append("table { width: 100%; border-collapse: collapse; margin-top: 20px; margin-bottom: 20px; }");
            emailContentHtml.append("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }");
            emailContentHtml.append("th { background-color: #007bff; color: white; text-align: center;}");
            emailContentHtml.append("td.product-name { font-weight: bold; }");
            emailContentHtml.append("td.align-right { text-align: right; }");
            emailContentHtml.append(".total-row td { font-weight: bold; font-size: 1.1em; }");
            emailContentHtml.append(".footer { text-align: center; margin-top: 20px; font-size: 0.9em; color: #777; }");
            emailContentHtml.append(".footer p { margin: 5px 0; }");
            emailContentHtml.append("</style></head><body>");
            emailContentHtml.append("<div class='container'>");
            emailContentHtml.append("<div class='header'><h1 class='store-name'>FASHION STORE IRTHN</h1>");
            emailContentHtml.append("<p>HÓA ĐƠN ĐIỆN TỬ</p></div>");

            emailContentHtml.append("<h2>Kính gửi ")
                    .append(customer.getHoTen() != null ? customer.getHoTen() : "Quý khách").append(",</h2>");
            emailContentHtml.append(
                    "<p>Cảm ơn Quý khách đã mua hàng tại <strong>FASHION STORE IRTHN</strong>. Dưới đây là chi tiết hóa đơn của Quý khách:</p>");

            emailContentHtml.append("<table>");
            emailContentHtml.append(
                    "<thead><tr><th>STT</th><th>Tên Sản Phẩm</th><th>Số Lượng</th><th>Đơn Giá (VNĐ)</th><th>Thành Tiền (VNĐ)</th></tr></thead>");
            emailContentHtml.append("<tbody>");

            double tongCong = 0;
            int stt = 1;
            java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0"); // Format số
            for (xuatHangDTO item : items) {
                emailContentHtml.append("<tr>");
                emailContentHtml.append("<td style='text-align:center;'>").append(stt++).append("</td>");
                emailContentHtml.append("<td class='product-name'>")
                        .append(item.getTenSanPham() != null ? item.getTenSanPham() : "").append("</td>");
                emailContentHtml.append("<td class='align-right'>")
                        .append(item.getSoLuong() != null ? item.getSoLuong() : "0").append("</td>");
                String donGiaFormatted = "0";
                String thanhTienFormatted = "0";
                try {
                    if (item.getDonGia() != null) {
                        donGiaFormatted = df.format(Double.parseDouble(item.getDonGia()));
                    }
                    if (item.getThanhTien() != null) {
                        double thanhTienDouble = Double.parseDouble(item.getThanhTien());
                        thanhTienFormatted = df.format(thanhTienDouble);
                        tongCong += thanhTienDouble;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Lỗi parse số cho email: " + e.getMessage());
                }
                emailContentHtml.append("<td class='align-right'>").append(donGiaFormatted).append("</td>");
                emailContentHtml.append("<td class='align-right'>").append(thanhTienFormatted).append("</td>");
                emailContentHtml.append("</tr>");
            }
            emailContentHtml.append(
                    "<tr class='total-row'><td colspan='4' class='align-right'><strong>Tổng cộng:</strong></td><td class='align-right'><strong>")
                    .append(df.format(tongCong)).append(" VNĐ</strong></td></tr>");
            emailContentHtml.append("</tbody></table>");

            emailContentHtml
                    .append("<p>Nếu Quý khách có bất kỳ thắc mắc nào, vui lòng liên hệ với chúng tôi qua email ")
                    .append(SMTP_USER).append(" hoặc số điện thoại 0582837353.</p>");

            emailContentHtml.append("<div class='footer'>");
            emailContentHtml.append("<p>Trân trọng cảm ơn và hẹn gặp lại!</p>");
            emailContentHtml.append("<p><strong>FASHION STORE IRTHN</strong></p>");
            emailContentHtml.append("<p>273 An Dương Vương, Q5, TP.HCM</p>");
            emailContentHtml.append("<p>SaiGonUniversity</p>");
            emailContentHtml.append("</div>");
            emailContentHtml.append("</div></body></html>");

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setContent(emailContentHtml.toString(), "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textBodyPart);

            if (pdfAttachment != null && pdfAttachment.exists()) {
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.attachFile(pdfAttachment);
                multipart.addBodyPart(attachmentBodyPart);
            }

            message.setContent(multipart);
            Transport.send(message);
            System.out.println("DEBUG: Gửi email thành công đến " + customer.getEmail());
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi gửi email: " + e.getMessage());
            return false;
        }
    }
}
