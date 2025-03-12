import java.util.*;

// Employee class implementing Comparable and Cloneable
class Employee implements Comparable<Employee>, Cloneable {
    private int id;
    private String name;
    private double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }

    // Comparable: Sort by Employee ID
    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id);
    }

    // Cloneable: Deep Copy
    @Override
    public Employee clone() {
        try {
            return (Employee) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported", e);
        }
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", name='" + name + '\'' + ", salary=" + salary + '}';
    }
}

// Comparator for sorting by Name
class NameComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        return e1.getName().compareTo(e2.getName());
    }
}

// Comparator for sorting by Salary
class SalaryComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        return Double.compare(e1.getSalary(), e2.getSalary());
    }
}

public class EmployeeSortingDemo {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(102, "Alice", 55000));
        employees.add(new Employee(101, "Bob", 60000));
        employees.add(new Employee(103, "Charlie", 50000));

        // Sorting using Comparable (by ID)
        Collections.sort(employees);
        System.out.println("Sorted by ID (Comparable): " + employees);

        // Sorting using Comparator (by Name)
        Collections.sort(employees, new NameComparator());
        System.out.println("Sorted by Name (Comparator): " + employees);

        // Sorting using Comparator (by Salary)
        Collections.sort(employees, new SalaryComparator());
        System.out.println("Sorted by Salary (Comparator): " + employees);

        // Cloning an Employee
        Employee clonedEmp = employees.get(0).clone();
        System.out.println("Cloned Employee: " + clonedEmp);

        // Iterating through Employees
        System.out.println("Iterating through employees:");
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
