//Interface cho các hành động của User
interface UserActions {

    // Default method để ghi lại hoạt động của User
    default void logActivity(String activity) {
        System.out.println("User: " + activity);
    }
}

//Interface cho các hành động của Admin
interface AdminActions {

    // Default method trùng tên với interface trên
    default void logActivity(String activity) {
        System.out.println("Admin: " + activity);
    }
}

// Lớp SuperAdmin implement cả 2 interface
class SuperAdmin implements UserActions, AdminActions {

    //Bắt buộc override để giải quyết Diamond Problem
    @Override
    public void logActivity(String activity) {

        // Chọn gọi phương thức của AdminActions
        AdminActions.super.logActivity(activity);
    }
}

public class bai5 {
    public static void main(String[] args) {
        SuperAdmin superAdmin = new SuperAdmin();
        superAdmin.logActivity("Deleted a user");
    }
}
