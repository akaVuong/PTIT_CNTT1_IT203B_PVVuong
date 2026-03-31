package presentation;

import dao.RoomDAO;
import dao.EquipmentDAO;
import dao.UserDAO;
import dao.BookingDAO;
import model.Room;
import model.User;
import model.Equipment;
import model.Booking;
import util.PasswordUtil;
import java.util.Scanner;
import java.util.List;
import service.AuthService;

public class AdminMenu {
    private RoomDAO roomDAO = new RoomDAO();
    private EquipmentDAO equipDAO = new EquipmentDAO();
    private UserDAO userDAO = new UserDAO();
    private BookingDAO bookingDAO = new BookingDAO();

    public void display(User admin, Scanner sc) {
        while (true) {
            System.out.println("\n--- HỆ THỐNG QUẢN TRỊ (ADMIN) ---");
            System.out.println("1. Quản lý Phòng họp (Xem/Thêm/Sửa/Xóa)");
            System.out.println("2. Quản lý Thiết bị (Cập nhật kho)");
            System.out.println("3. Tạo tài khoản SUPPORT STAFF");
            System.out.println("4. PHÊ DUYỆT & PHÂN CÔNG ĐẶT PHÒNG (MỚI)");
            System.out.println("0. Đăng xuất");
            System.out.print("Chọn: ");
            String choice = sc.nextLine();

            if (choice.equals("0")) break;
            switch (choice) {
                case "1": handleRooms(sc); break;
                case "2": handleEquipments(sc); break;
                case "3": createSupportAccount(sc, admin.getDepartmentId()); break;
                case "4": handleApprovals(sc); break;
                default: System.out.println(" Lựa chọn không hợp lệ!");
            }
        }
    }

    private void handleRooms(Scanner sc) {
        while (true) {
            System.out.println("\n====================== DANH SÁCH PHÒNG HỌP ======================");
            // Dòng tiêu đề cho người dùng dễ nhìn
            System.out.printf("%-3s | %-15s | %-12s | %-12s | %-10s\n", "ID", "Tên Phòng", "Sức Chứa", "Vị Trí", "Trạng Thái");
            System.out.println("-----------------------------------------------------------------");

            roomDAO.getAllRooms().forEach(r ->
                    // %-15s nghĩa là: Dành 15 khoảng trống, căn lề trái (-) cho chuỗi (s)
                    System.out.printf("%-3d | %-15s | %-12s | %-12s | [%s]\n",
                            r.getId(),
                            r.getRoomName(),
                            r.getCapacity() + " người",
                            r.getLocation(),
                            r.getStatus())
            );
            System.out.println("-----------------------------------------------------------------");

            System.out.println("[A] Thêm mới | [U] Sửa | [D] Xóa | [B] Quay lại");
            System.out.print("Chọn thao tác: ");            String action = sc.nextLine().toUpperCase();
            if (action.equals("B")) break;

            switch (action) {
                case "A":
                    System.out.print("Tên phòng: "); String name = sc.nextLine();
                    System.out.print("Sức chứa: "); int cap = Integer.parseInt(sc.nextLine());
                    System.out.print("Vị trí: "); String loc = sc.nextLine();
                    roomDAO.addRoom(new Room(0, name, cap, loc, "AVAILABLE"));
                    System.out.println(" Đã thêm phòng mới!");
                    break;

                case "U":
                    System.out.print("Nhập ID phòng cần sửa: ");
                    int idEdit = Integer.parseInt(sc.nextLine());
                    boolean exists = roomDAO.getAllRooms().stream().anyMatch(r -> r.getId() == idEdit);
                    if (!exists) {
                        System.out.println(" LỖI: Không tìm thấy phòng có ID = " + idEdit);
                        break;
                    }
                    System.out.print("Tên mới: "); String nNew = sc.nextLine();
                    System.out.print("Sức chứa mới: "); int cNew = Integer.parseInt(sc.nextLine());
                    System.out.print("Trạng thái (AVAILABLE/OCCUPIED): "); String sNew = sc.nextLine();
                    if (roomDAO.updateRoom(new Room(idEdit, nNew, cNew, "Tầng 1", sNew))) {
                        System.out.println(" Cập nhật thành công!");
                    } else {
                        System.out.println(" Cập nhật thất bại!");
                    }
                    break;

                case "D":
                    System.out.print("Nhập ID phòng cần xóa: ");
                    int idDel = Integer.parseInt(sc.nextLine());
                    boolean existsDel = roomDAO.getAllRooms().stream().anyMatch(r -> r.getId() == idDel);
                    if (!existsDel) {
                        System.out.println(" LỖI: Không tìm thấy phòng để xóa!");
                        break;
                    }
                    System.out.print("Bạn có chắc chắn muốn xóa? (y/n): ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        if (roomDAO.deleteRoom(idDel)) System.out.println(" Đã xóa phòng!");
                        else System.out.println(" Xóa thất bại!");
                    }
                    break;
            }
        }
    }

    private void handleEquipments(Scanner sc) {
        while (true) {
            System.out.println("\n=========================== QUẢN LÝ THIẾT BỊ ===========================");
            // Hiển thị dạng bảng chuyên nghiệp
            System.out.printf("%-3s | %-20s | %-10s | %-10s | %-10s\n", "ID", "Tên thiết bị", "Tổng số", "Sẵn có", "Trạng thái");
            System.out.println("-----------------------------------------------------------------------");

            List<model.Equipment> list = equipDAO.getAll();
            list.forEach(e -> System.out.printf("%-3d | %-20s | %-10d | %-10d | %-10s\n",
                    e.getId(), e.getName(), e.getTotalQty(), e.getAvailableQty(), e.getStatus()));
            System.out.println("-----------------------------------------------------------------------");

            System.out.println("[A] Thêm mới | [U] Sửa | [D] Xóa | [B] Quay lại");
            System.out.print("Chọn thao tác: ");
            String action = sc.nextLine().toUpperCase();
            if (action.equals("B")) break;

            switch (action) {
                case "A": // THÊM THIẾT BỊ + CHECK TRÙNG TÊN
                    System.out.print("Nhập tên thiết bị mới: ");
                    String name = sc.nextLine();
                    if (name.trim().isEmpty()) {
                        System.out.println(" Lỗi: Tên không được để trống!");
                        break;
                    }
                    // Gọi hàm check trùng từ DAO
                    if (equipDAO.isNameExists(name)) {
                        System.out.println(" Lỗi: Tên thiết bị '" + name + "' đã tồn tại!");
                        break;
                    }

                    System.out.print("Số lượng nhập kho: ");
                    int t = Integer.parseInt(sc.nextLine());
                    if (equipDAO.addEquipment(new model.Equipment(0, name, t, t, "GOOD"))) {
                        System.out.println(" Đã thêm thiết bị vào kho!");
                    }
                    break;

                case "U": // SỬA THIẾT BỊ + HIỆN TIN CŨ
                    System.out.print("Nhập ID thiết bị cần sửa: ");
                    int idEdit = Integer.parseInt(sc.nextLine());
                    model.Equipment old = equipDAO.getById(idEdit); // Lấy tin cũ
                    if (old == null) {
                        System.out.println(" Lỗi: Không tìm thấy ID này!");
                        break;
                    }

                    System.out.println("-> Đang sửa: " + old.getName() + " (Hiện có: " + old.getTotalQty() + ")");
                    System.out.print("Tên mới (Bỏ trống để giữ cũ): ");
                    String nNew = sc.nextLine();
                    if (nNew.isEmpty()) nNew = old.getName();

                    System.out.print("Tổng số lượng mới: ");
                    int tNew = Integer.parseInt(sc.nextLine());
                    System.out.print("Số lượng sẵn có: ");
                    int aNew = Integer.parseInt(sc.nextLine());

                    if (equipDAO.updateEquipment(new model.Equipment(idEdit, nNew, tNew, aNew, "GOOD"))) {
                        System.out.println(" Cập nhật thành công!");
                    }
                    break;

                case "D": // XÓA THIẾT BỊ + XÁC NHẬN
                    System.out.print("Nhập ID thiết bị muốn xóa: ");
                    int idDel = Integer.parseInt(sc.nextLine());
                    if (equipDAO.getById(idDel) == null) {
                        System.out.println(" Lỗi: ID không tồn tại!");
                        break;
                    }

                    System.out.print("Bạn có chắc chắn muốn xóa vĩnh viễn? (y/n): ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        if (equipDAO.deleteEquipment(idDel)) {
                            System.out.println(" Đã xóa thiết bị khỏi hệ thống!");
                        } else {
                            System.out.println(" Thất bại: Thiết bị đang bị ràng buộc dữ liệu!");
                        }
                    }
                    break;
            }
        }
    }

    private void createSupportAccount(Scanner sc, int deptId) {
        AuthService authService = new AuthService(); // Khai báo service để dùng logic validate

        System.out.println("\n--- TẠO TÀI KHOẢN SUPPORT STAFF ---");
        System.out.print("Username mới: "); String u = sc.nextLine();
        System.out.print("Password: "); String p = sc.nextLine();
        System.out.print("Họ tên: "); String n = sc.nextLine();
        System.out.print("Số điện thoại: "); String ph = sc.nextLine();

        // GỌI QUA SERVICE THAY VÌ GỌI THẲNG DAO
        String message = authService.registerNewUser(u, p, n, deptId, ph, "SUPPORT");
        System.out.println(message);
    }

    private void handleApprovals(Scanner sc) {
        System.out.println("\n---  PHÊ DUYỆT & PHÂN CÔNG ĐẶT PHÒNG ---");
        List<Booking> pendingList = bookingDAO.getPendingBookings();

        if (pendingList.isEmpty()) {
            System.out.println("  Không có yêu cầu nào đang chờ phê duyệt.");
            return;
        }

        // Nâng cấp tiêu đề bảng: Thêm cột Tên thiết bị và Tồn kho
        System.out.printf("%-6s | %-10s | %-20s | %-10s | %-20s\n",
                "ID Đơn", "Phòng ID", "Thiết bị yêu cầu", "Trong kho", "Lý do");
        System.out.println("---------------------------------------------------------------------------------------");

        for (Booking b : pendingList) {
            String equipName = "Không mượn";
            String stockStatus = "-";

            // Nếu đơn có yêu cầu thiết bị, lấy thông tin từ kho để hiển thị
            if (b.getEquipmentId() > 0) {
                model.Equipment equip = equipDAO.getById(b.getEquipmentId());
                if (equip != null) {
                    equipName = equip.getName();
                    stockStatus = String.valueOf(equip.getAvailableQty());
                }
            }

            System.out.printf("%-6d | %-10d | %-20s | %-10s | %-20s\n",
                    b.getId(),
                    b.getRoomId(),
                    equipName,
                    stockStatus,
                    b.getPurpose());
        }
        System.out.println("---------------------------------------------------------------------------------------");

        try {
            System.out.print("\nNhập ID đơn muốn duyệt (0 bỏ qua): ");
            int bId = Integer.parseInt(sc.nextLine());
            if (bId == 0) return;

            // 1. Kiểm tra đơn có tồn tại trong danh sách chờ không
            Booking selectedBooking = pendingList.stream()
                    .filter(b -> b.getId() == bId)
                    .findFirst()
                    .orElse(null);

            if (selectedBooking == null) {
                System.out.println("  LỖI: ID đơn #" + bId + " không hợp lệ hoặc đã được xử lý!");
                return;
            }

            // 2. TỰ ĐỘNG CHẶN DUYỆT NẾU HẾT THIẾT BỊ
            if (selectedBooking.getEquipmentId() > 0) {
                model.Equipment equip = equipDAO.getById(selectedBooking.getEquipmentId());
                if (equip == null || equip.getAvailableQty() <= 0) {
                    System.out.println("  THÔNG BÁO: Thiết bị '" + (equip != null ? equip.getName() : "ID " + selectedBooking.getEquipmentId()) + "' hiện đã hết trong kho!");
                    System.out.println("  Bạn không thể duyệt đơn này. Vui lòng yêu cầu nhân viên đổi thiết bị hoặc từ chối.");
                    return;
                }
            }

            // 3. Chọn nhân viên Support phụ trách
            System.out.println("\n--- 🛠 DANH SÁCH NHÂN VIÊN HỖ TRỢ ---");
            List<User> supportList = userDAO.getAllUsersByRole("SUPPORT");
            supportList.forEach(u -> System.out.println("ID: " + u.getId() + " | Tên: " + u.getFullName()));

            System.out.print("Nhập ID nhân viên Support phụ trách: ");
            int sId = Integer.parseInt(sc.nextLine());

            boolean isSupportExist = supportList.stream().anyMatch(u -> u.getId() == sId);
            if (!isSupportExist) {
                System.out.println("  LỖI: ID nhân viên Support không tồn tại!");
                return;
            }

            // 4. Thực hiện phê duyệt và giao việc
            if (bookingDAO.approveAndAssign(bId, sId)) {
                System.out.println(" THÀNH CÔNG: Đơn đã được duyệt và giao cho Support xử lý.");
            } else {
                System.out.println(" THẤT BẠI: Lỗi hệ thống khi cập nhật trạng thái!");
            }

        } catch (NumberFormatException e) {
            System.out.println(" LỖI: Vui lòng chỉ nhập các con số!");
        }
    }
}