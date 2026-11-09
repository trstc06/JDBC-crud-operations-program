import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StudentOperations{

    private final String url = "jdbc:postgresql://localhost:5432/students_db";
    private final String user = "postgres";
    private final String password = "Tejko2006";

    //get and display all students
    public void getAllStudents(){
        String SQL = "SELECT * FROM students ORDER BY student_id";
        try(Connection conn = DriverManager.getConnection(url, user, password); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SQL)){
            while(rs.next()){
                System.out.println(rs.getInt("student_id") + " | " + rs.getString("first_name") + " | " + rs.getString("last_name") + " | " + rs.getString("email") + " | " + rs.getDate("enrollment_date"));
            }

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    //add a student
    public void addStudent(String firstName, String lastName, String email, Date enrollmentDate){
        String SQL = "INSERT INTO students(first_name,last_name,email,enrollment_date) VALUES(?,?,?,?)";
        try(Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conn.prepareStatement(SQL)){

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setDate(4, enrollmentDate);
            pstmt.executeUpdate();
            System.out.println("Student added successfully!");

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    //update a student's email based on student_id
    public void updateStudentEmail(int studentId, String newEmail){
        String SQL = "UPDATE students SET email=? WHERE student_id=?";
        try(Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conn.prepareStatement(SQL)){

            pstmt.setString(1, newEmail);
            pstmt.setInt(2, studentId);
            int rows = pstmt.executeUpdate();
            if(rows > 0){
                System.out.println("Student email updated!");
            }else{
                System.out.println("No student has the id " + studentId);
            }

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    //delete a student based on student_id
    public void deleteStudent(int studentId){
        String SQL = "DELETE FROM students WHERE student_id=?";

        try(Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conn.prepareStatement(SQL)){

            pstmt.setInt(1, studentId);
            int rows = pstmt.executeUpdate();
            if(rows > 0){
                System.out.println("Student deleted!");
            }else{
                System.out.println("No student has the id " + studentId);
            }

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args){
        StudentOperations dbOps = new StudentOperations();
        Scanner scanner = new Scanner(System.in);
        //get all students
        System.out.println("Would you like to see all students? (yes/no)");
        if(scanner.nextLine().equalsIgnoreCase("yes")){
            dbOps.getAllStudents();
        }
        //add a student
        System.out.println("Would you like to add a student? (yes/no)");
        if(scanner.nextLine().equalsIgnoreCase("yes")){
            dbOps.addStudent("Alice", "Martin", "alice.martin@example.com", Date.valueOf("2024-01-15"));
        }
        //update a student's email
        System.out.println("Would you like to update a student's email? (yes/no)");
        if(scanner.nextLine().equalsIgnoreCase("yes")){
            dbOps.updateStudentEmail(1, "john.doe.new@example.com");
        }
        //delete a student
        System.out.println("Would you like to delete a student? (yes/no)");
        if(scanner.nextLine().equalsIgnoreCase("yes")){
            dbOps.deleteStudent(3);
        }
        scanner.close();
    }
}