package Bai4;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        HospitalDAO dao = new HospitalDAO();
        List<BenhNhanDTO> list = dao.getAllBenhNhan();
        for (BenhNhanDTO bn : list) {
            System.out.println("BN: " + bn);
            for (DichVu dv : bn.getDsDichVu()) {
                System.out.println("   DV: " + dv);
            }
        }
    }
}
