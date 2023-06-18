package main;

import model.Employee;
import repository.EmployeeRepository;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepository();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("Choose an option:");
            System.out.println("1. Add an employee");
            System.out.println("2. Retrieve all employees");
            System.out.println("3. Retrieve an employee");
            System.out.println("4. Delete an employee");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter employee ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter employee name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter employee designation: ");
                    String designation = scanner.nextLine();

                    Employee employee = new Employee(id, name, designation);
                    repository.addEmployee(employee);
                    System.out.println("Employee added successfully.");
                    break;
                case 2:
                    List<Employee> allEmployees = repository.getAllEmployees();
                    if (allEmployees.isEmpty()) {
                        System.out.println("No employees found.");
                    } else {
                        System.out.println("All Employees:");
                        for (Employee emp : allEmployees) {
                            System.out.println(emp.getName() + " - " + emp.getDesignation());
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter employee ID to retrieve: ");
                    int retrieveId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    Employee retrievedEmployee = repository.getEmployeeById(retrieveId);
                    if (retrievedEmployee == null) {
                        System.out.println("Employee not found with the given ID.");
                    } else {
                        System.out.println("Retrieved Employee:");
                        System.out.println(retrievedEmployee.getName() + " - " + retrievedEmployee.getDesignation());
                    }
                    break;
                case 4:
                    System.out.print("Enter employee ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    Employee deleteEmployee = repository.getEmployeeById(deleteId);
                    if (deleteEmployee == null) {
                        System.out.println("Employee not found with the given ID.");
                    } else {
                        repository.removeEmployee(deleteEmployee);
                        System.out.println("Employee deleted successfully.");
                    }
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println(); // Print an empty line for better readability
        }

        // Close the scanner
        scanner.close();
    }
}
