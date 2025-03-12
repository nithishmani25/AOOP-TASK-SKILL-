package sis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class StudentRepositoryTest {
    private StudentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new StudentRepository();
    }

    @Test
    void testAddStudent() {
        Student student = new UndergraduateStudent("U001", "Alice", 20, "Computer Science");
        repository.addStudent(student);

        assertEquals(1, repository.getAllStudents().size());
    }

    @Test
    void testGetStudentById() {
        Student student = new GraduateStudent("G001", "Bob", 25, "Machine Learning");
        repository.addStudent(student);

        Optional<Student> foundStudent = repository.getStudentById("G001");
        assertTrue(foundStudent.isPresent());
        assertEquals("Bob", foundStudent.get().getName());
    }

    @Test
    void testRemoveStudent() {
        Student student = new UndergraduateStudent("U002", "Charlie", 22, "Physics");
        repository.addStudent(student);
        repository.removeStudent("U002");

        assertEquals(0, repository.getAllStudents().size());
    }
}
