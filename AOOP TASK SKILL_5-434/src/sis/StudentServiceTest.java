package sis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {
    private StudentService service;
    private StudentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new StudentRepository();
        service = new StudentService(repository);
    }

    @Test
    void testRegisterStudent() {
        Student student = new UndergraduateStudent("U001", "Alice", 20, "Computer Science");
        service.registerStudent(student);

        assertEquals(1, service.getAllStudents().size());
    }

    @Test
    void testFindStudentById() {
        Student student = new GraduateStudent("G001", "Bob", 25, "Machine Learning");
        service.registerStudent(student);

        Optional<Student> foundStudent = service.findStudentById("G001");
        assertTrue(foundStudent.isPresent());
        assertEquals("Bob", foundStudent.get().getName());
    }

    @Test
    void testRemoveStudent() {
        Student student = new UndergraduateStudent("U002", "Charlie", 22, "Physics");
        service.registerStudent(student);
        service.removeStudent("U002");

        assertEquals(0, service.getAllStudents().size());
    }
}
