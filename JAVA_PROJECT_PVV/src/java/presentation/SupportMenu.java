package presentation;

import dao.BookingDAO;
import model.User;
import java.util.Scanner;

public class SupportMenu {
    private BookingDAO bookingDAO = new BookingDAO();

    public void display(User support, Scanner sc) {
        while (true) {
            System.out.println("\n--- 🛠 MENU HỖ TRỢ (SUPPORT STAFF) ---");
            System.out.println("1. Xem danh sách công việc được giao");
            System.out.println("2. Cập nhật trạng thái chuẩn bị phòng");
            System.out.println("0. Đăng xuất");
            System.out.print("Chọn: ");
            String choice = sc.nextLine();

            if (choice.equals("0")) break;

            switch (choice) {
                case "1":
                    // Chỉ xem danh sách công việc (Đã bao gồm Vị trí, Giờ kết thúc, Tên thiết bị)
                    bookingDAO.showAssignedTasks(support.getId());
                    break;
                case "2":
                    handleUpdate(sc, support);
                    break;
                default:
                    System.out.println(" Lựa chọn không hợp lệ!");
            }
        }
    }

    private void handleUpdate(Scanner sc, User support) {
        System.out.println("\n--- 🛠 BƯỚC 1: XEM DANH SÁCH ĐỂ LẤY ID ---");
        bookingDAO.showAssignedTasks(support.getId());

        try {
            System.out.print("\nNhập ID đơn đặt phòng muốn cập nhật (0 để quay lại): ");
            String inputId = sc.nextLine().trim();
            if (inputId.isEmpty() || inputId.equals("0")) return;

            int id = Integer.parseInt(inputId);

            // Giao diện chọn trạng thái trực quan
            System.out.println("\n--- CHỌN TRẠNG THÁI CẬP NHẬT ---");
            System.out.println("1. READY (Đã chuẩn bị đủ thiết bị - Hệ thống sẽ tự trừ kho)");
            System.out.println("2.  MISSING_EQUIPMENT (Đang thiếu thiết bị, cần bổ sung)");
            System.out.print("Chọn (1/2): ");
            String op = sc.nextLine().trim();

            if (!op.equals("1") && !op.equals("2")) {
                System.out.println(" Lỗi: Lựa chọn không hợp lệ!");
                return;
            }

            String status = op.equals("1") ? "READY" : "MISSING_EQUIPMENT";

            // Gửi lệnh update vào Database qua BookingDAO
            // Logic trừ kho tự động cho READY đã được tích hợp trong BookingDAO.updatePrepStatus
            if (bookingDAO.updatePrepStatus(id, status)) {
                System.out.println("\n THÀNH CÔNG: Đơn #" + id + " đã chuyển sang trạng thái [" + status + "]!");
                if (status.equals("READY")) {
                    System.out.println(" [Hệ thống]: Đã tự động cập nhật giảm số lượng thiết bị tương ứng trong kho.");
                }
            } else {
                // Trường hợp ID không tồn tại, không thuộc về support này, hoặc kho đã hết thiết bị
                System.out.println("\n THẤT BẠI: Không thể cập nhật!");
                System.out.println(" Lý do có thể: ID #" + id + " sai, không được giao cho bạn, hoặc kho đã hết thiết bị yêu cầu.");
            }

        } catch (NumberFormatException e) {
            System.out.println(" LỖI: Vui lòng nhập ID là một con số hợp lệ!");
        } catch (Exception e) {
            System.out.println(" CÓ LỖI XẢY RA: " + e.getMessage());
        }
    }
}