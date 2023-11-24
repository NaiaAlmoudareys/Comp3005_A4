import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class StudentInfo {


    private Connection openConnection() throws SQLException {
        return this.getConnection();
    }



    private void printStudentDetails(ResultSet rs) throws SQLException {
        int student_id = rs.getInt("student_id");
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");
        String email = rs.getString("email");
        Date enrollment_date = rs.getDate("enrollment_date");
        System.out.println("Student: " + student_id + " Name: " + first_name + " " + last_name + " Email: " + email + " Enrollment Date: " + enrollment_date);
    }


    public void getAllStudents() {
        String query = "SELECT * FROM students";
        try (Connection connection = openConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {


            while (rs.next()) {
                printStudentDetails(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void prepareStudentDetails(PreparedStatement preparedStatement, String first_name, String last_name, String email, Date enrollment_date) throws SQLException {
        preparedStatement.setString(1, first_name);
        preparedStatement.setString(2, last_name);
        preparedStatement.setString(3, email);
        preparedStatement.setDate(4, new java.sql.Date(enrollment_date.getTime()));
    }

    public void addStudent(String first_name, String last_name, String email, Date enrollment_date) {
        String query = "INSERT INTO students (first_name,last_name,email,enrollment_date) VALUES(?,?,?,?) " + "ON CONFLICT (email) DO NOTHING";
        try (Connection connection = openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            prepareStudentDetails(preparedStatement, first_name, last_name, email, enrollment_date);
            int addRow = preparedStatement.executeUpdate();
            if (addRow > 0) {
                System.out.println("A new student " + first_name + " " + last_name + " was added  ");
            }else{
                System.out.println("this email exists in the table ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateDetails(PreparedStatement preparedStatement, String new_email, int student_id) throws SQLException {
        preparedStatement.setString(1, new_email);
        preparedStatement.setInt(2, student_id);
    }



    public void updateStudentEmail(int student_id, String new_email) {
        String query = "UPDATE students SET email = ? WHERE student_id = ?" ;
        try (Connection connection = openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            updateDetails(preparedStatement, new_email, student_id);
            int addRow = preparedStatement.executeUpdate();
            if (addRow > 0) {
                System.out.println("The Student email" + student_id + " was updated  to " + new_email + "successfully");
            }else{
                System.out.println("No student found with the following ID ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void deleteStudent(int student_id) {
        String query = "DELETE FROM students WHERE student_id = ?";
        try (Connection connection = openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, student_id);
            int addRow = preparedStatement.executeUpdate();
            if (addRow > 0) {
                System.out.println("the Student " + student_id + "  was deleted ");
            }else{
                System.out.println("No student found with the following ID ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        final String URL = "jdbc:postgresql://localhost:5432/A4";
        final String USER = "postgres";
        final String PASSWORD = "Naya2002";
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conn != null) { System.out.println("Connected to PostgreSQL successfully!");
            } else { System.out.println("Failed to establish connection.");
            }
            return conn;
        } catch (SQLException e) { throw new RuntimeException("Error connecting to the database", e);
        }
    }

    private static String validateEmail() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the new student's email");
        String new_email = scanner.nextLine();
        if (!new_email.contains("@") ||  new_email.isEmpty()  || !new_email.contains(".")) {
            System.out.println("Invalid input");
            return null;
        }
        return new_email;
    }
    public static void main(String[] args) {
        getConnection();
        Scanner scanner = new Scanner(System.in);
        StudentInfo studentInfo = new StudentInfo();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int student_id;

        while (true) {
            System.out.println("1: Print all students");
            System.out.println("2: Add a student");
            System.out.println("3: Update a student's email");
            System.out.println("4: Delete a student");
            System.out.println("0: Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    studentInfo.getAllStudents();
                    break;
                case 2:

                    System.out.println("Enter the student's first name");
                    String first_name = scanner.nextLine();

                    if(first_name.isEmpty()){
                        System.out.println("invalid input");
                        break;
                    }

                    System.out.println("Enter the student's last name");
                    String last_name = scanner.nextLine();


                    if(last_name.isEmpty()){
                        System.out.println("invalid input");
                        break;
                    }


                    String email = validateEmail();
                    if (email == null) {
                        System.out.println("Invalid email. Operation cancelled.");
                        break;
                    }


                    SimpleDateFormat myDate = new SimpleDateFormat("yyyy-MM-dd");
                    myDate.setLenient(false);

                    while (true) {
                        System.out.println("Enter the student's enrollment date (yyyy-MM-dd):");
                        String dateStr = scanner.nextLine();
                        try {
                            Date enrollment_date = myDate.parse(dateStr);
                            studentInfo.addStudent(first_name, last_name, email, enrollment_date);
                            break;
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format .");
                            break;
                        }
                    }



                    break;

                case 3:
                    System.out.println("which student do you want to update ");

                    System.out.println("Enter the student's id :");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a numeric student ID ");
                        return;
                    }
                    student_id = scanner.nextInt();
                    scanner.nextLine();






                    String new_email = validateEmail();
                    if (new_email == null) {
                        System.out.println("Invalid email. Operation cancelled.");
                        break;
                    }


                    studentInfo.updateStudentEmail(student_id, new_email);


                    break;
                case 4:

                    System.out.println("Enter the student's id :");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a numeric student ID ");
                        return;
                    }
                    student_id = scanner.nextInt();
                    scanner.nextLine();
                    studentInfo.deleteStudent(student_id);

                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}





