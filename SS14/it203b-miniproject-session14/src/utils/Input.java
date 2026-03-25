package utils;

import java.util.Scanner;

public class Input {
    private static final Scanner sc = new Scanner(System.in);

    public static String inputString(String title) {
        while (true) {
            System.out.print(title);
            String input = sc.nextLine();
            if (input.isBlank()) {
                System.out.println("Không được để trống");
            } else if (input.length() < 3) {
                System.out.println("Phải có ít nhất 3 ký tự");
            } else {
                return input;
            }
        }
    }

    public static int inputIntegerPositive(String title) {
        while (true) {
            System.out.print(title);
            try {
                int input = Integer.parseInt(sc.nextLine());
                if (input < 0) {
                    System.out.println("Phải nhập số lớn hơn không");
                } else {
                    return input;
                }
            } catch (NumberFormatException e) {
                System.out.println("Nhập không hợp lệ");
            }
        }
    }

    public static double inputDoublePositive(String title) {
        while (true) {
            System.out.print(title);
            try {
                double input = Double.parseDouble(sc.nextLine());
                if (input < 0) {
                    System.out.println("Phải nhập số lớn hơn 0");
                } else {
                    return input;
                }
            } catch (NumberFormatException e) {
                System.out.println("Nhập không hợp lệ");
            }
        }
    }
}
