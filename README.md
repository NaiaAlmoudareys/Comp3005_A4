# Comp3005_A4
Comp 3005 assignment 4 question 1 answer 

Naia Almoudareys
101173126

this assignment contains 
1. StudentInfo.java
6. Q1.sql 
7. readme.txt


NOTE :
you must have SQL downloaded and running
you must have JDK installed and running 
you must have postgresql-42.7.0.jar downloaded


how to work with the code 
1. go to pg admin and create a new database and execute the sql script that is provided -> q1.sql
2. run the code that is in the sql
	1. select the entire 'create table' block and run it 
	2. select the entire 'insert to table' block and run it
3. open the command prompt or the terminal 
4. go to the directory where the file path is using cd
5. compile and run the code 
	java -cp postgresql-42.7.0.jar StudentInfo.java ( will also run your code)

a drop down menu will be shown 
1. print all the student 
2. add a new student ( will ask for the first name, last name , email, enrollment date)
3. upate a student email ( will ask for a student id and the new email)
4. delete a student ( will ask for a student id and delete a student)
5. exit 




what the functions do :
1. openConnection() -> A helper function : opens a connection which will be called in all other functions
2. printStudentDetails(ResultSet rs)-> a function that will gather all the information from the table and prints them in a userfriendly way .
3. getAllStudents() ->a function that will get all the attribute of the student and print them
4.  prepareStudentDetails(PreparedStatement preparedStatement, String first_name, String last_name, String email, Date enrollment_date) ->Prepares the details of a student for insertion in the database.
5. addStudent( String first_name, String last_name, String email, Date enrollment_date)->a function that will add a new student to the student's table
6.updateDetails(PreparedStatement preparedStatement, String new_email, int student_id ) -> this function will update the new information 
7. updateStudentEmail(int student_id, String new_email)->a function that will update an email that is related to a student using the student_id
8. deleteStudent(int student_id) ->a function that will delete the student_id from the table


 
