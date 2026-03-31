package presentation;

import dao.RoomDAO;
import dao.BookingDAO;
import dao.EquipmentDAO; // Cần import thêm DAO thiết bị
import model.User;
import model.Booking;
import model.Equipment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;

public class EmployeeMenu {
    private RoomDAO roomDAO = new RoomDAO();
    private BookingDAO bookingDAO = new BookingDAO();
    private EquipmentDAO equipDAO = new EquipmentDAO(); // Khai báo để lấy danh sách thiết bị
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void display(User user, Scanner sc) {
        while (true) {
            System.out.println("\n--- MENU NHÂN VIÊN (ĐẶT PHÒNG) ---");
            System.out.println("1. Xem danh sách phòng");
            System.out.println("2. Thực hiện đặt phòng");
            System.out.println("3. Xem lịch họp của tôi & Kiểm tra trạng thái Ready");
            System.out.println("0. Đăng xuất");
            System.out.print("Chọn: ");
            String choice = sc.nextLine();

            if (choice.equals("0")) break;

            if (choice.equals("1")) {
                handleShowRooms();
            } else if (choice.equals("2")) {
                handleBooking(user, sc);
            } else if (choice.equals("3")) {
                bookingDAO.showMyBookings(user.getId());
            }
        }
    }

    private void handleShowRooms() {
        System.out.println("\n=========================== DANH SÁCH PHÒNG HỌP ===========================");
        System.out.printf("%-3s | %-15s | %-12s | %-12s | %-10s\n", "ID", "Tên Phòng", "Sức Chứa", "Vị Trí", "Trạng Thái");
        System.out.println("---------------------------------------------------------------------------");

        List<model.Room> rooms = roomDAO.getAllRooms();
        for (model.Room r : rooms) {
            System.out.printf("%-3d | %-15s | %-12s | %-12s | [%s]\n",
                    r.getId(),
                    r.getRoomName(),
                    r.getCapacity() + " người",
                    r.getLocation(),
                    r.getStatus());
        }
        System.out.println("---------------------------------------------------------------------------");
    }

    private void handleBooking(model.User user, Scanner sc) {
        try {
            System.out.println("\n---  THỰC HIỆN ĐẶT PHÒNG HỌP ---");

            // 1. Nhập và Kiểm tra ID Phòng
            System.out.print("Nhập ID phòng muốn đặt: ");
            String roomIdInput = sc.nextLine().trim();
            if (roomIdInput.isEmpty()) return;

            int roomId = Integer.parseInt(roomIdInput);
            boolean exists = roomDAO.getAllRooms().stream().anyMatch(r -> r.getId() == roomId);
            if (!exists) {
                System.out.println(" LỖI: Không tìm thấy phòng có ID = " + roomId);
                return;
            }

            // 2. Nhập và Validate Thời gian
            System.out.print("Giờ bắt đầu (yyyy-MM-dd HH:mm): ");
            LocalDateTime start = LocalDateTime.parse(sc.nextLine().trim(), formatter);

            if (start.isBefore(LocalDateTime.now())) {
                System.out.println(" LỖI: Không thể đặt phòng cho thời gian đã trôi qua!");
                return;
            }

            System.out.print("Giờ kết thúc (yyyy-MM-dd HH:mm): ");
            LocalDateTime end = LocalDateTime.parse(sc.nextLine().trim(), formatter);

            if (!end.isAfter(start)) {
                System.out.println(" LỖI: Giờ kết thúc phải sau giờ bắt đầu!");
                return;
            }

            // 3. Kiểm tra trùng lịch
            if (!bookingDAO.isRoomAvailable(roomId, start, end)) {
                System.out.println(" XUNG ĐỘT: Khung giờ này đã có người đặt!");
                return;
            }

            // 4. HIỂN THỊ DANH SÁCH THIẾT BỊ ĐỂ CHỌN
            System.out.println("\n--- DANH SÁCH THIẾT BỊ TRONG KHO ---");
            List<Equipment> equipList = equipDAO.getAll();
            System.out.printf("%-3s | %-20s | %-10s\n", "ID", "Tên thiết bị", "Sẵn có");
            equipList.forEach(e -> System.out.printf("%-3d | %-20s | %-10d\n", e.getId(), e.getName(), e.getAvailableQty()));

            System.out.print("Nhập ID thiết bị muốn mượn (0 nếu không mượn): ");
            int equipId = Integer.parseInt(sc.nextLine().trim());

            // Kiểm tra tồn kho trước khi gửi đơn
            if (equipId > 0) {
                Equipment selected = equipDAO.getById(equipId);
                if (selected == null || selected.getAvailableQty() <= 0) {
                    System.out.println(" LỖI: Thiết bị này hiện đã hết hoặc không tồn tại!");
                    return;
                }
            }

            System.out.print("Lý do cuộc họp: ");
            String purpose = sc.nextLine().trim();

            // 5. Lưu đơn với equipmentId đã chọn
            model.Booking newBooking = new model.Booking(0, user.getId(), roomId, equipId, start, end, purpose, "PENDING");
            if (bookingDAO.createBooking(newBooking)) {
                System.out.println("\n THÀNH CÔNG: Đã gửi yêu cầu đặt phòng kèm thiết bị!");
                System.out.println(" Vui lòng đợi Admin kiểm tra tồn kho và phê duyệt.");
            } else {
                System.out.println(" Thất bại: Lỗi hệ thống!");
            }

        } catch (Exception e) {
            System.out.println(" LỖI: Dữ liệu nhập không hợp lệ hoặc sai định dạng!");
        }
    }
}