package Bai4;

import java.util.*;

public class BenhNhanDTO {
    private int maBenhNhan;
    private String tenBenhNhan;
    private List<DichVu> dsDichVu = new ArrayList<>();

    public BenhNhanDTO(int maBenhNhan, String tenBenhNhan) {
        this.maBenhNhan = maBenhNhan;
        this.tenBenhNhan = tenBenhNhan;
    }

    public List<DichVu> getDsDichVu() {
        return dsDichVu;
    }
}
