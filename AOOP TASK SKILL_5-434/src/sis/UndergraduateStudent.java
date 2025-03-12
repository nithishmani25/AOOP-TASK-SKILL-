package sis;

public class UndergraduateStudent extends Student {
    private String major;

    public UndergraduateStudent(String id, String name, int age, String major) {
        super(id, name, age);
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    @Override
    public String getStudentType() {
        return "Undergraduate";
    }
}
