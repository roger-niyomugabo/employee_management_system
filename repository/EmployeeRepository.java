package repository;

import model.Employee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeRepository {
    private static final String JSON_FILE_PATH = "employees.json";
    private List<Employee> employees;

    public EmployeeRepository() {
        employees = readEmployeesFromJson();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        saveEmployeesToJson();
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        saveEmployeesToJson();
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    private void saveEmployeesToJson() {
        List<String> employeeJsonStrings = employees.stream()
                .map(this::convertEmployeeToJson)
                .collect(Collectors.toList());

        try (BufferedWriter writer = Files.newBufferedWriter(getJsonFilePath())) {
            writer.write("[\n");
            for (int i = 0; i < employeeJsonStrings.size(); i++) {
                writer.write(employeeJsonStrings.get(i));
                if (i < employeeJsonStrings.size() - 1) {
                    writer.write(",\n");
                }
            }
            writer.write("\n]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Employee getEmployeeById(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null; // Employee not found with the given ID
    }

    private List<Employee> readEmployeesFromJson() {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(getJsonFilePath())) {
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            jsonString.deleteCharAt(jsonString.indexOf("[")); // Remove opening [
            jsonString.deleteCharAt(jsonString.lastIndexOf("]")); // Remove closing ]

            String[] employeeJsonStrings = jsonString.toString().split(",");
            for (String employeeJsonString : employeeJsonStrings) {
                Employee employee = convertJsonToEmployee(employeeJsonString.trim());
                employees.add(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // converts employee string to json
    // ID:1,NAME:amani,DESIGNATION:IT
    private Employee convertJsonToEmployee(String jsonString) {
        String[] tokens = jsonString.split(",");
        int id = Integer.parseInt(tokens[0].split(":")[1].trim());
        String name = tokens[1].split(":")[1].trim().replaceAll("\"", "");
        String designation = tokens[2].split(":")[1].trim().replaceAll("\"", "");

        return new Employee(id, name, designation);
    }

    private String convertEmployeeToJson(Employee employee) {
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("{")
                .append("\"id\": ").append(employee.getId()).append(", ")
                .append("\"name\": \"").append(employee.getName()).append("\", ")
                .append("\"designation\": \"").append(employee.getDesignation()).append("\"")
                .append("}");

        return jsonString.toString();
    }

    private Path getJsonFilePath() {
        return Paths.get(JSON_FILE_PATH);
    }
}
