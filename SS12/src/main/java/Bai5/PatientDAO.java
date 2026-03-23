package Bai5;


import java.sql.*;
import java.util.Scanner;

public class PatientDAO {

    // 1. Hiển thị danh sách
    public void listPatients(Connection conn) throws Exception {
        String sql = "SELECT * FROM Patients";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getInt("age") + " | " +
                            rs.getString("department")
            );
        }
    }

    // 2. Thêm bệnh nhân (PreparedStatement chống SQL Injection)
    public void addPatient(Connection conn, Scanner sc) throws Exception {
        String sql = "INSERT INTO Patients(name, age, department, disease, admission_date) VALUES(?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        System.out.print("Tên: ");
        String name = sc.nextLine(); // xử lý được L'Oréal 👍

        System.out.print("Tuổi: ");
        int age = Integer.parseInt(sc.nextLine());

        System.out.print("Khoa: ");
        String dept = sc.nextLine();

        System.out.print("Bệnh: ");
        String disease = sc.nextLine();

        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setString(3, dept);
        ps.setString(4, disease);
        ps.setDate(5, new Date(System.currentTimeMillis()));

        ps.executeUpdate();
        System.out.println("✔ Thêm thành công");
    }

    // 3. Cập nhật bệnh án
    public void updateDisease(Connection conn, Scanner sc) throws Exception {
        String sql = "UPDATE Patients SET disease=? WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);

        System.out.print("Nhập ID: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Bệnh mới: ");
        String disease = sc.nextLine();

        ps.setString(1, disease);
        ps.setInt(2, id);

        ps.executeUpdate();
        System.out.println("✔ Cập nhật thành công");
    }

    // 4. Xuất viện + tính phí (Stored Procedure)
    public void discharge(Connection conn, Scanner sc) throws Exception {
        CallableStatement cs = conn.prepareCall("{call CALCULATE_DISCHARGE_FEE(?, ?)}");

        System.out.print("Nhập ID bệnh nhân: ");
        int id = Integer.parseInt(sc.nextLine());

        cs.setInt(1, id);
        cs.registerOutParameter(2, Types.DECIMAL);

        cs.execute();

        double fee = cs.getDouble(2);

        System.out.println("💰 Tổng viện phí: " + fee);
    }
}
