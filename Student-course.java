Student.java
class Student {
    int studentID;
    String studentName;
    
    // Constructor
    public Student(int studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;
    }
    
    // Display student info
    public void displayStudent() {
        System.out.println("    Student ID: " + studentID + ", Name: " + studentName);
    }
}
Course.java
class Course {
    int courseID;
    String courseName;
    int seatCapacity;
    Student[] enrolledStudents;
    int enrollmentCount;
    
    // Constructor
    public Course(int courseID, String courseName, int seatCapacity) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.seatCapacity = seatCapacity;
        this.enrolledStudents = new Student[seatCapacity];
        this.enrollmentCount = 0;
    }
    
    // Check if student is already enrolled
    private boolean isStudentEnrolled(int studentID) {
        for (int i = 0; i < enrollmentCount; i++) {
            if (enrolledStudents[i].studentID == studentID) {
                return true;
            }
        }
        return false;
    }
    
    // Enroll a student
    public boolean enrollStudent(Student student) {
        if (enrollmentCount >= seatCapacity) {
            System.out.println("Course is full. Cannot enroll student.");
            return false;
        }
        
        if (isStudentEnrolled(student.studentID)) {
            System.out.println("Student is already enrolled in this course.");
            return false;
        }
        
        enrolledStudents[enrollmentCount] = student;
        enrollmentCount++;
        System.out.println("Student enrolled successfully.");
        return true;
    }
    
    // Drop a student
    public boolean dropStudent(int studentID) {
        for (int i = 0; i < enrollmentCount; i++) {
            if (enrolledStudents[i].studentID == studentID) {
                // Shift students to fill the gap
                for (int j = i; j < enrollmentCount - 1; j++) {
                    enrolledStudents[j] = enrolledStudents[j + 1];
                }
                enrolledStudents[enrollmentCount - 1] = null;
                enrollmentCount--;
                System.out.println("Student dropped successfully.");
                return true;
            }
        }
        System.out.println("Student is not enrolled in this course.");
        return false;
    }
    
    // Display course details
    public void displayCourse() {
        System.out.println("Course ID: " + courseID);
        System.out.println("Course Name: " + courseName);
        System.out.println("Capacity: " + seatCapacity);
        System.out.println("Current Enrollment: " + enrollmentCount);
        
        if (enrollmentCount == 0) {
            System.out.println("No students enrolled.");
        } else {
            System.out.println("Enrolled Students:");
            for (int i = 0; i < enrollmentCount; i++) {
                enrolledStudents[i].displayStudent();
            }
        }
        System.out.println();
    }
}
Main.java
import java.util.Scanner;

class Main {
    static final int MAX_COURSES = 30;
    static final int MAX_STUDENTS = 100;
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide number of courses as command-line argument.");
            return;
        }
        
        int numCourses = Integer.parseInt(args[0]);
        
        if (numCourses > MAX_COURSES) {
            System.out.println("Maximum " + MAX_COURSES + " courses allowed.");
            return;
        }
        
        Scanner sc = new Scanner(System.in);
        Course[] courses = new Course[numCourses];
        Student[] allStudents = new Student[MAX_STUDENTS];
        int studentCount = 0;
        
        // Initialize courses
        for (int i = 0; i < numCourses; i++) {
            System.out.println("\nEnter details for Course " + (i + 1) + ":");
            
            System.out.print("Course Name: ");
            String courseName = sc.nextLine();
            
            System.out.print("Seat Capacity: ");
            int seatCapacity = sc.nextInt();
            sc.nextLine(); // consume newline
            
            courses[i] = new Course(i + 1, courseName, seatCapacity);
        }
        
        // Menu loop
        while (true) {
            System.out.println("\n===== Menu =====");
            System.out.println("1. Enroll a New Student in a Course");
            System.out.println("2. Enroll Existing Student in a Course");
            System.out.println("3. Drop Student from a Course");
            System.out.println("4. Display All Courses with Enrolled Students");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    // Enroll a new student
                    if (studentCount >= MAX_STUDENTS) {
                        System.out.println("Maximum student limit reached.");
                        break;
                    }
                    
                    System.out.print("Enter Student ID: ");
                    int newStudentID = sc.nextInt();
                    sc.nextLine();
                    
                    // Check if student ID already exists
                    boolean exists = false;
                    for (int i = 0; i < studentCount; i++) {
                        if (allStudents[i].studentID == newStudentID) {
                            exists = true;
                            break;
                        }
                    }
                    
                    if (exists) {
                        System.out.println("Student ID already exists. Use option 2 to enroll existing student.");
                        break;
                    }
                    
                    System.out.print("Enter Student Name: ");
                    String studentName = sc.nextLine();
                    
                    System.out.print("Enter Course ID to enroll in: ");
                    int courseID1 = sc.nextInt();
                    
                    if (courseID1 < 1 || courseID1 > numCourses) {
                        System.out.println("Invalid Course ID.");
                        break;
                    }
                    
                    Student newStudent = new Student(newStudentID, studentName);
                    allStudents[studentCount] = newStudent;
                    studentCount++;
                    
                    courses[courseID1 - 1].enrollStudent(newStudent);
                    break;
                    
                case 2:
                    // Enroll existing student
                    System.out.print("Enter Student ID: ");
                    int existingStudentID = sc.nextInt();
                    
                    System.out.print("Enter Course ID: ");
                    int courseID2 = sc.nextInt();
                    
                    if (courseID2 < 1 || courseID2 > numCourses) {
                        System.out.println("Invalid Course ID.");
                        break;
                    }
                    
                    // Find the student
                    Student foundStudent = null;
                    for (int i = 0; i < studentCount; i++) {
                        if (allStudents[i].studentID == existingStudentID) {
                            foundStudent = allStudents[i];
                            break;
                        }
                    }
                    
                    if (foundStudent == null) {
                        System.out.println("Student not found.");
                    } else {
                        courses[courseID2 - 1].enrollStudent(foundStudent);
                    }
                    break;
                    
                case 3:
                    // Drop student from course
                    System.out.print("Enter Student ID: ");
                    int dropStudentID = sc.nextInt();
                    
                    System.out.print("Enter Course ID: ");
                    int courseID3 = sc.nextInt();
                    
                    if (courseID3 < 1 || courseID3 > numCourses) {
                        System.out.println("Invalid Course ID.");
                        break;
                    }
                    
                    courses[courseID3 - 1].dropStudent(dropStudentID);
                    break;
                    
                case 4:
                    // Display all courses
                    System.out.println("\n===== All Courses =====");
                    for (int i = 0; i < numCourses; i++) {
                        courses[i].displayCourse();
                    }
                    break;
                    
                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                    
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
        }
