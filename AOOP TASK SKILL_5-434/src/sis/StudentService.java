package sis;

import java.util.List;
import java.util.Optional;

public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public void registerStudent(Student student) {
        repository.addStudent(student);
    }

    public Optional<Student> findStudentById(String id) {
        return repository.getStudentById(id);
    }

    public List<Student> getAllStudents() {
        return repository.getAllStudents();
    }

    public void removeStudent(String id) {
        repository.removeStudent(id);
    }
}
