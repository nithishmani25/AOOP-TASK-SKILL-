package sis;

public class SISApplication {
    public static void main(String[] args) {
        StudentRepository repository = new StudentRepository();
        StudentService service = new StudentService(repository);

        // Adding students
        Student student1 = new UndergraduateStudent("U001", "Alice", 20, "Computer Science");
        Student student2 = new GraduateStudent("G001", "Bob", 25, "Machine Learning");

        service.registerStudent(student1);
        service.registerStudent(student2);

        // Display all students
        System.out.println("All Registered Students:");
        for (Student student : service.getAllStudents()) {
            System.out.println(student.getId() + " - " + student.getName() + " - " + student.getStudentType());
        }
    }
}
