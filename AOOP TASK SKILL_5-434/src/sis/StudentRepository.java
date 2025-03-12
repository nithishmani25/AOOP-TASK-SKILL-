package sis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository {
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public Optional<Student> getStudentById(String id) {
        return students.stream().filter(student -> student.getId().equals(id)).findFirst();
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public void removeStudent(String id) {
        students.removeIf(student -> student.getId().equals(id));
    }
}
