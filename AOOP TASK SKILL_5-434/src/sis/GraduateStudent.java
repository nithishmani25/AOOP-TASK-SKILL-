package sis;

public class GraduateStudent extends Student {
    private String researchTopic;

    public GraduateStudent(String id, String name, int age, String researchTopic) {
        super(id, name, age);
        this.researchTopic = researchTopic;
    }

    public String getResearchTopic() {
        return researchTopic;
    }

    @Override
    public String getStudentType() {
        return "Graduate";
    }
}
