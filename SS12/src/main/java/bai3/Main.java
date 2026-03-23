package bai3;

public class Main {
    public static void main(String[] args) {
        SurgeryDAO dao = new SurgeryDAO();
        double cost = dao.getSurgeryFee(505);
        System.out.println("Chi phí phẫu thuật: " + cost);
    }
}
