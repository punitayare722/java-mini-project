import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student implements Serializable {
    private int studentId;
    private String name;
    private List<Double> grades;

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public void addGrade(double grade) {
        grades.add(grade);
    }

    public double calculateAverage() {
        if (grades.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }

    public String generateReport() {
        double average = calculateAverage();
        return "Student ID: " + studentId + "\n" +
               "Name: " + name + "\n" +
               "Grades: " + grades + "\n" +
               "Average: " + average + "\n";
    }

    public void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(studentId + ".txt"))) {
            out.writeObject(this);
            System.out.println("Record saved for " + name);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public static Student loadFromFile(int studentId) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(studentId + ".txt"))) {
            return (Student) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
            return null;
        }
    }

    // Getter for studentId
    public int getStudentId() {
        return studentId;
    }
}

public class StudentGradingSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Add Student\n2. Add Grade\n3. Generate Report\n4. Save Student\n5. Load Student\n6. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addGrade();
                    break;
                case 3:
                    generateReport();
                    break;
                case 4:
                    saveStudent();
                    break;
                case 5:
                    loadStudent();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        students.add(new Student(studentId, name));
        System.out.println("Student added.");
    }

    private static void addGrade() {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        Student student = findStudentById(studentId);
        if (student != null) {
            System.out.print("Enter grade: ");
            double grade = scanner.nextDouble();
            student.addGrade(grade);
            System.out.println("Grade added.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void generateReport() {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        Student student = findStudentById(studentId);
        if (student != null) {
            System.out.println(student.generateReport());
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void saveStudent() {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        Student student = findStudentById(studentId);
        if (student != null) {
            student.saveToFile();
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void loadStudent() {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        Student student = Student.loadFromFile(studentId);
        if (student != null) {
            students.add(student);
            System.out.println("Student loaded.");
        } else {
            System.out.println("Failed to load student.");
        }
    }

    private static Student findStudentById(int studentId) {
        for (Student student : students) {
            if (studentId == student.getStudentId()) { // Use getter method
                return student;
            }
        }
        return null;
    }
}
