package employeeapp;

import java.util.List;
import java.util.Optional;

public class EmployeeApplication {
    public static void main(String[] args) {
        EmployeeService service = new EmployeeService();

        // 1️⃣ Filtering Employees with Salary > 60000
        System.out.println("Employees with salary > 60000:");
        List<Employee> filteredEmployees = service.filterHighSalaryEmployees(60000);
        filteredEmployees.forEach(System.out::println);

        // 2️⃣ Sorting Employees by Salary (Descending Order)
        System.out.println("\nEmployees sorted by salary (Descending):");
        List<Employee> sortedEmployees = service.sortEmployeesBySalary();
        sortedEmployees.forEach(System.out::println);

        // 3️⃣ Finding the Employee with the Highest Salary
        Optional<Employee> highestPaidEmployee = service.getHighestPaidEmployee();
        System.out.println("\nHighest Paid Employee: " + highestPaidEmployee.orElse(null));

        // 4️⃣ Calculating the Average Salary
        double averageSalary = service.getAverageSalary();
        System.out.println("\nAverage Salary of Employees: " + averageSalary);
    }
}
