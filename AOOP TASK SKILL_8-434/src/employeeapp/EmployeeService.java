package employeeapp;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeService {
    private List<Employee> employees;

    public EmployeeService() {
        employees = Arrays.asList(
            new Employee(1, "Alice", 55000),
            new Employee(2, "Bob", 60000),
            new Employee(3, "Charlie", 75000),
            new Employee(4, "David", 48000),
            new Employee(5, "Emma", 92000),
            new Employee(6, "Frank", 67000),
            new Employee(7, "Grace", 54000),
            new Employee(8, "Hannah", 89000),
            new Employee(9, "Ivy", 72000),
            new Employee(10, "Jack", 50000)
        );
    }

    // Filtering Employees with salary > 60000
    public List<Employee> filterHighSalaryEmployees(double threshold) {
        return employees.stream()
                .filter(emp -> emp.getSalary() > threshold)
                .collect(Collectors.toList());
    }

    // Sorting Employees by salary (Descending Order)
    public List<Employee> sortEmployeesBySalary() {
        return employees.stream()
                .sorted((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()))
                .collect(Collectors.toList());
    }

    // Finding Employee with Highest Salary
    public Optional<Employee> getHighestPaidEmployee() {
        return employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary));
    }

    // Calculating Average Salary of Employees
    public double getAverageSalary() {
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }
}
