package Bai5;


import java.sql.Date;

public class Patient {
    int id;
    String name;
    int age;
    String department;
    String disease;
    Date admissionDate;

    public Patient(String name, int age, String department, String disease, Date admissionDate) {
        this.name = name;
        this.age = age;
        this.department = department;
        this.disease = disease;
        this.admissionDate = admissionDate;
    }
}
