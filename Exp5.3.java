import java.io.*;
import java.util.*;

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String designation;
    private double salary;

    public Employee(int id, String name, String designation, double salary) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void displayEmployee() {
        System.out.println("Employee ID: " + id + ", Name: " + name +
                           ", Designation: " + designation + ", Salary: " + salary);
    }
}

public class Main {
    private static final String FILE_NAME = "employees.ser";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Delete Employee");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    displayAllEmployees();
                    break;
                case 3:
                    deleteEmployee();
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void addEmployee() {
        System.out.print("Enter Employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Employee Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Employee Designation: ");
        String designation = scanner.nextLine();

        System.out.print("Enter Employee Salary: ");
        double salary = scanner.nextDouble();

        Employee employee = new Employee(id, name, designation, salary);
        List<Employee> employees = readEmployeesFromFile();
        employees.add(employee);

        saveEmployeesToFile(employees);
        System.out.println("✅ Employee added successfully!");
    }

    public static void displayAllEmployees() {
        List<Employee> employees = readEmployeesFromFile();
        if (employees.isEmpty()) {
            System.out.println("❗ No employee data available.");
            return;
        }

        System.out.println("\nEmployee List:");
        for (Employee emp : employees) {
            emp.displayEmployee();
        }
    }

    public static void deleteEmployee() {
        List<Employee> employees = readEmployeesFromFile();
        if (employees.isEmpty()) {
            System.out.println("❗ No employee data available to delete.");
            return;
        }

        System.out.print("Enter Employee ID to delete: ");
        int idToDelete = scanner.nextInt();

        boolean found = false;
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == idToDelete) {
                iterator.remove();
                found = true;
            }
        }

        if (found) {
            saveEmployeesToFile(employees);
            System.out.println("✅ Employee deleted successfully!");
        } else {
            System.out.println("❌ Employee with ID " + idToDelete + " not found.");
        }
    }

    private static void saveEmployeesToFile(List<Employee> employees) {
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(employees);
        } catch (IOException e) {
            System.out.println("❌ Error: Unable to save employees.");
        }
    }

    private static List<Employee> readEmployeesFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) return new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<Employee>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
