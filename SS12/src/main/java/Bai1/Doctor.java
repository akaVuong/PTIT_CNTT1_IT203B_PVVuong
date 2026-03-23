package Bai1;

public class Doctor {
    private String code;
    private String pass;

    public Doctor() {}

    public Doctor(String code, String pass) {
        this.code = code;
        this.pass = pass;
    }

    public String getCode() {
        return code;
    }

    public String getPass() {
        return pass;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
