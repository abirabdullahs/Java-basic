Employee.java
class Employee {
    int employeeID;
    String name;
    int daysPresent;
    
    // Constructor
    public Employee(int employeeID, String name, int daysPresent) {
        this.employeeID = employeeID;
        this.name = name;
        this.daysPresent = daysPresent;
    }
    
    // Mark attendance (increase days)
    public void markAttendance() {
        daysPresent++;
        System.out.println("Attendance marked. Days present: " + daysPresent);
    }
    
    // Correct attendance (decrease days)
    public boolean correctAttendance() {
        if (daysPresent > 0) {
            daysPresent--;
            System.out.println("Attendance corrected. Days present: " + daysPresent);
            return true;
        } else {
            System.out.println("Days present is already zero. Cannot decrease.");
            return false;
        }
    }
    
    // Display employee info
    public void displayEmployee() {
        System.out.println("  Employee ID: " + employeeID + 
                         ", Name: " + name + 
                         ", Days Present: " + daysPresent);
    }
}
Department.java
class Department {
    int departmentID;
    Employee[] employees;
    int employeeCount;
    static final int MAX_EMPLOYEES = 50;
    
    // Constructor
    public Department(int departmentID) {
        this.departmentID = departmentID;
        this.employees = new Employee[MAX_EMPLOYEES];
        this.employeeCount = 0;
    }
    
    // Add employee to department
    public boolean addEmployee(int employeeID, String name, int daysPresent) {
        if (employeeCount >= MAX_EMPLOYEES) {
            System.out.println("Cannot add more employees. Maximum limit reached.");
            return false;
        }
        employees[employeeCount] = new Employee(employeeID, name, daysPresent);
        employeeCount++;
        return true;
    }
    
    // Find employee by ID
    private Employee findEmployee(int employeeID) {
        for (int i = 0; i < employeeCount; i++) {
            if (employees[i].employeeID == employeeID) {
                return employees[i];
            }
        }
        return null;
    }
    
    // Mark attendance for an employee
    public void markAttendance(int employeeID) {
        Employee emp = findEmployee(employeeID);
        if (emp == null) {
            System.out.println("Employee not found.");
        } else {
            emp.markAttendance();
        }
    }
    
    // Correct attendance for an employee
    public void correctAttendance(int employeeID) {
        Employee emp = findEmployee(employeeID);
        if (emp == null) {
            System.out.println("Employee not found.");
        } else {
            emp.correctAttendance();
        }
    }
    
    // Display all employees in department
    public void displayDepartment() {
        System.out.println("Department ID: " + departmentID);
        if (employeeCount == 0) {
            System.out.println("No employees registered.");
        } else {
            for (int i = 0; i < employeeCount; i++) {
                employees[i].displayEmployee();
            }
        }
        System.out.println();
    }
}
Main.java
import java.util.Scanner;

class Main {
    static final int MAX_DEPARTMENTS = 10;
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide number of departments as command-line argument.");
            return;
        }
        
        int numDepartments = Integer.parseInt(args[0]);
        
        if (numDepartments > MAX_DEPARTMENTS) {
            System.out.println("Maximum " + MAX_DEPARTMENTS + " departments allowed.");
            return;
        }
        
        Scanner sc = new Scanner(System.in);
        Department[] departments = new Department[numDepartments];
        
        // Initialize departments and add employees
        for (int i = 0; i < numDepartments; i++) {
            departments[i] = new Department(i + 1);
            
            System.out.println("\nHow many employees to register in department " + (i + 1) + "?");
            int numEmployees = sc.nextInt();
            
            for (int j = 0; j < numEmployees; j++) {
                System.out.println("\nEnter details for Employee " + (j + 1) + ":");
                
                System.out.print("Employee ID: ");
                int employeeID = sc.nextInt();
                sc.nextLine(); // consume newline
                
                System.out.print("Name: ");
                String name = sc.nextLine();
                
                System.out.print("Days Present: ");
                int daysPresent = sc.nextInt();
                
                departments[i].addEmployee(employeeID, name, daysPresent);
            }
        }
        
        // Menu loop
        while (true) {
            System.out.println("\n===== Menu =====");
            System.out.println("1. Mark Attendance (Increase Days)");
            System.out.println("2. Correct Attendance (Decrease Days)");
            System.out.println("3. Display All Department Info");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            int choice = sc.nextInt();
            
            switch (choice) {
                case 1:
                    // Mark attendance
                    System.out.print("Enter Department ID: ");
                    int deptID1 = sc.nextInt();
                    System.out.print("Enter Employee ID: ");
                    int empID1 = sc.nextInt();
                    
                    if (deptID1 > 0 && deptID1 <= numDepartments) {
                        departments[deptID1 - 1].markAttendance(empID1);
                    } else {
                        System.out.println("Invalid Department ID.");
                    }
                    break;
                    
                case 2:
                    // Correct attendance
                    System.out.print("Enter Department ID: ");
                    int deptID2 = sc.nextInt();
                    System.out.print("Enter Employee ID: ");
                    int empID2 = sc.nextInt();
                    
                    if (deptID2 > 0 && deptID2 <= numDepartments) {
                        departments[deptID2 - 1].correctAttendance(empID2);
                    } else {
                        System.out.println("Invalid Department ID.");
                    }
                    break;
                    
                case 3:
                    // Display all departments
                    System.out.println("\n===== All Department Info =====");
                    for (int i = 0; i < numDepartments; i++) {
                        departments[i].displayDepartment();
                    }
                    break;
                    
                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                    
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
