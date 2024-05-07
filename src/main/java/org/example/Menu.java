package org.example;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.NoResultException;
import jakarta.validation.ConstraintViolationException;
import org.example.conncetion.SessionFactorySingleton;
import org.example.enums.*;
import org.example.model.*;
import org.example.model.user.*;
import org.example.service.course.CourseService;
import org.example.service.employee.EmployeeService;
import org.example.service.enrollment.EnrollmentService;
import org.example.service.grade.GradeService;
import org.example.service.professor.ProfessorService;
import org.example.service.student.StudentService;
import org.example.service.user.UserService;
import org.example.util.validation.EmailValidator;
import org.example.util.validation.NationalCodeValidator;
import org.example.util.validation.PasswordValidator;
import org.example.util.validation.PhoneNumberValidator;
import org.hibernate.LazyInitializationException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.util.ApplicationContext.*;

public class Menu {

    UserService userService = getUserService();
    CourseService courseService = getCourseService();
    EmployeeService employeeService = getEmployeeService();
    ProfessorService professorService = getProfessorService();
    StudentService studentService = getStudentService();
    GradeService gradeService = getGradeService();
    EnrollmentService enrollmentService = getEnrollmentService();


    private final Scanner scanner = new Scanner(System.in);
    Professor professor = new Professor();
    Employee employee = new Employee();
    Student student = new Student();
    Course course = new Course();
    Grade grade = new Grade();
    User user = new User();
    Enrollment enrollment = new Enrollment();

    public int getSelectedOption() {
        Scanner scanner = new Scanner(System.in);
        int selectedOption = scanner.nextInt();
        scanner.nextLine();
        return selectedOption;
    }

    public int getNumberFromUser() {
        int num = 0;
        try {
            num = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.nextLine();
        }
        return num;
    }

    public String getStringFromUser() {
        String input = null;
        try {
            input = scanner.next();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return input;
    }

    public String getEmailFromUserInput(Scanner scanner) {
        String email;
        boolean isValidEmail = false;
        do {
            System.out.println("Please enter your email: ");
            email = scanner.next();

            if (EmailValidator.isEmailValid(email)) {
                if (!checkIfEmailExists(email)) {
                    System.out.println("Email address is valid and unique!");
                    isValidEmail = true;
                } else {
                    System.out.println("This email address already exists. Please enter a unique email address.");
                }
            } else {
                System.out.println("Invalid email address. Please enter a valid email address.");
            }
        } while (!isValidEmail);

        return email;
    }

    public String getFormattedSemester(Scanner scanner) {
        String semester = "";
        while (true) {
            System.out.print("Semester (1-1400): ");
            semester = scanner.next();

            if (!semester.isEmpty() && semester.length() < 8) {
                boolean valid = true;
                for (char c : semester.toCharArray()) {
                    if (!Character.isDigit(c) && c != '-') {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    String[] parts = semester.split("-");
                    if (parts.length == 2) {
                        int term = Integer.parseInt(parts[0]);
                        int year = Integer.parseInt(parts[1]);
                        if (year >= 1300 && year <= 1500 && term >= 1 && term <= 2) {
                            break;
                        }
                    }
                }
            }
            System.out.println("Invalid input. Please enter a semester value in the format 'T-YYYY', where YYYY is the year (1300-1500) and T is the term (1-2).");
        }
        return semester;
    }

    public String getPasswordFromUserInput(Scanner scanner) {
        String password;
        boolean isValidPassword = false;
        do {
            System.out.println("Please enter your password: ");
            password = scanner.next();

            if (PasswordValidator.isPasswordValid(password)) {
                System.out.println("Password is valid!");
                isValidPassword = true;
            } else {
                System.out.println("Invalid password. Please enter a valid password.");
            }
        } while (!isValidPassword);
        return password;
    }

    public String getUniqeEmployeeNumber(Scanner scanner) {
        String employeeNumber = null;
        Pattern pattern = Pattern.compile("\\d{10}");
        while (true) {
            try {
                System.out.print("Employee number: ");
                employeeNumber = scanner.next();

                Matcher matcher = pattern.matcher(employeeNumber);
                if (!matcher.matches()) {
                    throw new IllegalArgumentException("Invalid input. Employee number must be a 10-digit number. Please try again.");
                }
                if (checkIfEmployeeNumberExists(employeeNumber)) {
                    System.out.println("This employee number already exists. Please enter a unique employee number.");
                    continue;
                }
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return employeeNumber;
    }

    public String getUniqeStudentNumber(Scanner scanner) {
        String studentNumber = null;
        Pattern pattern = Pattern.compile("\\d{10}");
        while (true) {
            try {
                System.out.print("Student number: ");
                studentNumber = scanner.next();

                Matcher matcher = pattern.matcher(studentNumber);
                if (!matcher.matches()) {
                    throw new IllegalArgumentException("Invalid input. Student number must be a 10-digit number. Please try again.");
                }
                if (checkIfStudentNumberExists(studentNumber)) {
                    System.out.println("This student number already exists. Please enter a unique student number.");
                    continue;
                }
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return studentNumber;
    }

    public String getUniqueProfessorNumber(Scanner scanner) {
        String professorNumber = null;
        Pattern pattern = Pattern.compile("\\d{10}");
        while (true) {
            try {
                System.out.print("Professor number: ");
                professorNumber = scanner.next();

                if (checkIfProfessorNumberExists(professorNumber)) {
                    System.out.println("This professor number already exists. Please enter a unique professor number.");
                    continue;
                }
                Matcher matcher = pattern.matcher(professorNumber);
                if (matcher.matches()) {
                    break;
                } else {
                    System.out.println("Invalid input. Professor number must be a 10-digit number. Please try again.");
                }
            } catch (Exception ex) {
                System.out.println("Invalid input. Please try again.");
            }
        }
        return professorNumber;
    }

    public String getPhoneNumberFromUserInput(Scanner scanner) {
        String phoneNumber;
        boolean isValidPhoneNumber = false;
        do {
            System.out.println("Please enter student's phoneNumber: ");
            phoneNumber = scanner.next();
            if (PhoneNumberValidator.isIranianPhoneNumberValid(phoneNumber)) {
                if (!checkIfPhoneNumberExists(phoneNumber)) {
                    System.out.println("Iranian phone number is valid!");
                    isValidPhoneNumber = true;
                } else {
                    System.out.println("This phoneNumber already exists. Please enter a unique phoneNumber.");
                }
            } else {
                System.out.println("Invalid Iranian phone number. Please enter a valid phone number.");
            }
        } while (!isValidPhoneNumber);

        return phoneNumber;
    }

    public ProfessorType getProfessorType(Scanner scanner) {
        ProfessorType professorType = null;
        while (true) {
            try {
                System.out.print("Professor type (FACULTY_MEMBER, TEACHING_ASSISTANT): ");
                String professorTypeStr = scanner.next().toUpperCase();
                professorType = ProfessorType.valueOf(professorTypeStr);
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid input. Please enter either FACULTY_MEMBER or TEACHING_ASSISTANT.");
            }
        }
        return professorType;
    }

    public int getTeachingUnits(Scanner scanner) {
        int teachingUnits = 0;
        while (true) {
            System.out.print("Teaching units: ");
            if (scanner.hasNextInt()) {
                teachingUnits = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid integer value.");
                scanner.nextLine();
            }
        }
        return teachingUnits;
    }

    public String getNationalCodeFromUserInput(Scanner scanner) {
        String nationalCode;
        boolean isValidNationalCode = false;
        do {
            System.out.println("Please enter student's nationalCode: ");
            nationalCode = scanner.next();
            if (NationalCodeValidator.isNationalCodeValid(nationalCode)) {
                if (!checkIfNationalCodeExists(nationalCode)) {
                    System.out.println("National code is valid!");
                    isValidNationalCode = true;
                } else {
                    System.out.println("This nationalCode already exists. Please enter a unique nationalCode.");
                }
            } else {
                System.out.println("Invalid national code. Please enter a valid national code.");
            }
        } while (!isValidNationalCode);

        return nationalCode;
    }

    public Department getDepartmentFromUserInput(Scanner scanner) {
        Department department = null;
        while (true) {
            System.out.print("Department (e.g., LAW, COMPUTER_SCIENCE): ");
            try {
                department = Department.valueOf(scanner.next().toUpperCase());
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid department. Please enter a valid department.");
            }
        }
        return department;
    }

    public CourseStatus getCourseStatusFromUserInput(Scanner scanner) {
        CourseStatus courseStatus = null;
        while (true) {
            System.out.print("CourseStatus (e.g., AVAILABLE, PASSED, ENROLLED): ");
            try {
                courseStatus = CourseStatus.valueOf(scanner.next().toUpperCase());
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid department. Please enter a valid department.");
            }
        }
        return courseStatus;
    }

    public AcademicTerm getAcademicTermFromUserInput(Scanner scanner) {
        AcademicTerm currentTerm = null;
        while (true) {
            System.out.print("Academic term (e.g., FALL, SPRING): ");
            try {
                currentTerm = AcademicTerm.valueOf(scanner.next().toUpperCase());
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid academic term. Please enter a valid term.");
            }
        }
        return currentTerm;
    }

    public double getValidPreviousGPA(Scanner scanner) {
        double previousGPA = 0.0;
        while (true) {
            try {
                System.out.print("Previous GPA: ");
                previousGPA = scanner.nextDouble();
                scanner.nextLine();
                if (previousGPA < 0 || previousGPA > 20) {
                    throw new IllegalArgumentException("Invalid input. Previous GPA must be between 0 and 20.");
                }
                break;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return previousGPA;
    }

    public String getUniqueCourseName(Scanner scanner) {
        String courseName = null;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Course name: ");
                courseName = scanner.next();
                if (courseName.isBlank()) {
                    throw new IllegalArgumentException("Course name cannot be empty.");
                }
                if (checkIfCourseNameExists(courseName)) {
                    System.out.println("This course name already exists. Please enter a unique course name.");
                    continue;
                }
                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return courseName;
    }

    public int getValidNumberOfUnits(Scanner scanner) {
        int units = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Number of units: ");
                units = scanner.nextInt();
                if (units <= 0) {
                    throw new IllegalArgumentException("Number of units must be positive.");
                }
                if (units > 9) {
                    throw new IllegalArgumentException("Number of units cannot exceed 9.");
                }
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.next();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return units;
    }

    public int getValidYear(Scanner scanner) {
        int year = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Year (1300-1500): ");
                year = scanner.nextInt();
                if (year < 1300 || year > 1500) {
                    throw new IllegalArgumentException("Year must be between %d and %d.");
                }
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.next();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return year;
    }

    public double getValidGrade(Scanner scanner) {
        double grade = 0;
        boolean validGrade = false;

        while (!validGrade) {
            System.out.print("Please enter your grade (0-20): ");
            String input = scanner.next();

            try {
                grade = Double.parseDouble(input);
                if (grade < 0 || grade > 20) {
                    throw new IllegalArgumentException("Grade must be between 0 and 20.");
                }
                validGrade = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid grade. Please enter a valid numeric grade.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return grade;
    }

    public String getUniqeUserName(Scanner scanner) {
        String username = "";
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("please enter your username: ");
                username = scanner.next();
                if (username.isBlank()) {
                    throw new IllegalArgumentException("username cannot be empty.");
                }
                if (username.isEmpty() || username.length() > 30) {
                    throw new IllegalArgumentException("username must be between 1 and 30 characters.");
                }
                if (checkUserNameExists(username)) {
                    System.out.println("This username already exists. Please enter a unique username.");
                    continue;
                }
                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return username;
    }

    public String getValidFirstName(Scanner scanner) {
        String firstName = "";
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("First name: ");
                firstName = scanner.next();
                if (firstName.isBlank()) {
                    throw new IllegalArgumentException("First name cannot be empty.");
                }
                if (firstName.isEmpty() || firstName.length() > 30) {
                    throw new IllegalArgumentException("First name must be between 1 and 30 characters.");
                }
                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return firstName;
    }

    public String getValidLastName(Scanner scanner) {
        String lastName = "";
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Last name: ");
                lastName = scanner.next();
                if (lastName.isBlank()) {
                    throw new IllegalArgumentException("Last name cannot be empty.");
                }
                if (lastName.isEmpty() || lastName.length() > 50) {
                    throw new IllegalArgumentException("Last name must be between 1 and 50 characters.");
                }
                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return lastName;
    }

    /* public List<Course> getCourseListFromUser(Scanner scanner) {
         List<Course> courseList = new ArrayList<>();
         System.out.println("Enter course details for each course:");

         while (true) {
             Course course = new Course();


             System.out.println("Course Name: ");
             String courseName = scanner.nextLine();
             course.setCourseName(courseName);


             Department department = getDepartmentFromUserInput(scanner);
             course.setDepartment(department);

             AcademicTerm term = getAcademicTermFromUserInput(scanner);
             course.setTerm(term);


             int year = getValidYear(scanner);
             course.setYear(year);

             int units = getValidNumberOfUnits(scanner);
             course.setUnits(units);

             System.out.println("Student ID: ");
             course.setStudent(student);

             System.out.println("Professor ID: ");
             course.setProfessor(professor);

             courseList.add(course);

             System.out.println("Do you want to enter another course? (yes/no): ");
             String answer = scanner.next();
             if (!answer.equalsIgnoreCase("yes")) {
                 break;
             }
         }
         return courseList;
     }*/
    public boolean checkIfEmailExists(String email) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            Query<User> query = session.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            User user = query.uniqueResult();
            return user != null;
        }
    }

    public boolean checkIfPhoneNumberExists(String phoneNumber) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            Query<User> query = session.createQuery("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber", User.class);
            query.setParameter("phoneNumber", phoneNumber);
            User user = query.uniqueResult();
            return user != null;
        }
    }

    public boolean checkIfNationalCodeExists(String nationalCode) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            Query<User> query = session.createQuery("SELECT u FROM User u WHERE u.nationalCode = :nationalCode", User.class);
            query.setParameter("nationalCode", nationalCode);
            User user = query.uniqueResult();
            return user != null;
        }
    }

    public boolean checkIfStudentNumberExists(String studentNumber) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            Query<Student> query = session.createQuery("SELECT s FROM Student s WHERE s.studentNumber = :studentNumber", Student.class);
            query.setParameter("studentNumber", studentNumber);
            Student student = query.uniqueResult();
            return student != null;
        }
    }

    public boolean checkIfEmployeeNumberExists(String employeeNumber) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            Query<Employee> query = session.createQuery("SELECT e FROM Employee e WHERE e.employeeNumber = :employeeNumber", Employee.class);
            query.setParameter("employeeNumber", employeeNumber);
            Employee employee = query.uniqueResult();
            return employee != null;
        }
    }

    public boolean checkIfProfessorNumberExists(String professorNumber) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            Query<Professor> query = session.createQuery("SELECT p FROM Professor p WHERE p.professorNumber = :professorNumber", Professor.class);
            query.setParameter("professorNumber", professorNumber);
            Professor professor = query.uniqueResult();
            return professor != null;
        }
    }

    public static boolean checkUserNameExists(String username) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            Query<User> query = session.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            User user = query.uniqueResult();
            return user != null;
        }
    }

    public boolean checkIfCourseNameExists(String courseName) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            Query<Course> query = session.createQuery("SELECT c FROM Course c WHERE c.courseName = :courseName", Course.class);
            query.setParameter("courseName", courseName);
            Course course = query.uniqueResult();
            return course != null;
        }
    }

    public static void main(String[] args) throws Exception {

        try {
            SecurityContextSample securityContext = new SecurityContextSample();
            if (!checkUserNameExists("hosseinj13")) {
                User user = new User("hossein", "javadi", "989197033530", "hosseinj13@yahoo.com", "0510028977", "hosseinj13", "h1374308N@", Department.COMPUTER_SCIENCE);
                securityContext.signUp(user);
                getUserService().saveOrUpdate(user);
            } else {
                System.out.println("User already exists.");
            }
        } catch (ConstraintViolationException e) {
            System.out.println("(username)=(hosseinj13) already exists." + e.getMessage());
        }
        //  securityContext.login("hosseinj13", "h1374308N@");

        Menu userMenu = new Menu();
        boolean userMenuExecuted = false;
        while (!userMenuExecuted) {
            if (userMenu.userMenu()) {
                userMenuExecuted = true;
            }
        }
        if (userMenu.getSelectedOption() != 3) {
            Menu menu = new Menu();
            menu.publicMenu();
        }
//        Menu menu = new Menu();
//        menu.publicMenu();
    }

    public void signup() throws Exception {
        do {
            String username = getUniqeUserName(scanner);
            String email = getEmailFromUserInput(scanner);
            String password = getPasswordFromUserInput(scanner);
            String firstName = getValidFirstName(scanner);
            String lastName = getValidLastName(scanner);
            String phoneNumber = getPhoneNumberFromUserInput(scanner);
            String nationalCode = getNationalCodeFromUserInput(scanner);
            Department department = getDepartmentFromUserInput(scanner);

            User user = new User(firstName, lastName, phoneNumber, email, nationalCode, username, password, department);
            userService.saveOrUpdate(user);
            System.out.println("User registration successful!");
        } while (userMenu());
    }

    public void signIn() throws Exception {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("please enter username: ");
            String username = scanner.next();
            String password = getPasswordFromUserInput(scanner);

            User user = userService.findByUsername(username);
            if (user == null) {
                System.out.println("User not found. Please sign up first.");
                continue;
            } else if (!user.getPassword().equals(password)) {
                System.out.println("Invalid password. Please enter correct password.");
                continue;
            } else {
                System.out.println("WELCOME " + user.getUsername());
                publicMenu();
            }
        } while (true);
    }

    public boolean userMenu() throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        while (!validInput) {
            System.out.println("********** Welcome to the Educational System **********");
            System.out.println("***************** USER AUTHENTICATION *****************");
            //  System.out.println("1-SIGN UP");
            System.out.println("1-SIGN IN");
            System.out.println("2-EXIT");
            System.out.println("Choose your number: ");

            try {
                int number = scanner.nextInt();
                scanner.nextLine();

                if (number < 1 || number > 2) {
                    System.out.println("Invalid input! Please enter a number between 1 and 3.");
                } else {
                    validInput = true;
                    switch (number) {
//                        case 1:
//                            //signup();
//                            break;
                        case 1:
                            signIn();
                            break;
                        case 2:
                            System.out.println("exit");
                            break;
                    }
                }
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine();
            }
        }
        return true;
    }


    private boolean isEmployee(Long id) {
        UserTypes userType = userService.getUserType(id);
        return userType.equals(UserTypes.EMPLOYEE);
    }

    private boolean isStudent(Long id) {
        UserTypes userType = userService.getUserType(id);
        return userType.equals(UserTypes.STUDENT);
    }

    private boolean isProfessor(long id) {
        UserTypes userType = userService.getUserType(id);
        return userType.equals(UserTypes.PROFESSOR);
    }

    public void publicMenu() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.println("***** Main Menu *****");
            System.out.println("1. Login as Education Employee");
            System.out.println("2. Login as Student");
            System.out.println("3. Login as Professor");
            System.out.println("4. Exit");
            System.out.print("Please choose an option: ");
            try {
                choice = scanner.nextInt();
                Long id = user.getId();
                switch (choice) {
                    case 1:
                        if (isEmployee(id)) {
                            loginAsEducationEmployee();
                        }
                        break;
                    case 2:
                        if (isStudent(id)) {
                            loginAsStudent();
                        }
                        break;
                    case 3:
                        if (isProfessor(id)) {
                            loginAsProfessor();
                        }
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        userMenu();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    public void loginAsEducationEmployee() throws Exception {
        int choice = 0;
        while (true) {
            System.out.println("Logging in as Education Employee...");
            System.out.println("1. Register a new student");
            System.out.println("2. Delete a student");
            System.out.println("3. Edit student information");
            System.out.println("4. Register a new professor");
            System.out.println("5. Delete a professor");
            System.out.println("6. Edit professor information");
            System.out.println("7. Register a new employee");
            System.out.println("8. Delete a employee");
            System.out.println("9. Edit employee information");
            System.out.println("10. Add a new course");
            System.out.println("11. Delete a course");
            System.out.println("12. Edit course information");
            System.out.println("13. View pay stub and info");
            System.out.println("14. Exit");
            System.out.print("Please choose an option: ");
            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        // Register a new student
                        createStudent();
                        break;
                    case 2:
                        // Delete a student
                        try {
                            studentService.delete(student);
                        } catch (IllegalArgumentException e) {
                            System.out.println("An error occurred: " + e.getMessage());
                        }
                        break;
                    case 3:
                        // Edit student information
                        updateStudent();
                        break;
                    case 4:
                        // Register a new professor
                        createProfessor();
                        break;
                    case 5:
                        // Delete a professor
                        try {
                            professorService.delete(professor);
                        } catch (IllegalArgumentException e) {
                            System.out.println("An error occurred: " + e.getMessage());
                        }
                        break;
                    case 6:
                        // Edit professor information
                        updateProfessor();
                        break;
                    case 7:
                        // Register a new employee
                        createEmployee();
                        break;
                    case 8:
                        // Delete an employee
                        try {
                            employeeService.delete(employee);
                        } catch (IllegalArgumentException e) {
                            System.out.println("An error occurred: " + e.getMessage());
                        }
                        break;
                    case 9:
                        // Edit employee information
                        updateEmployee();
                        break;
                    case 10:
                        // Add a new course
                        createCourse();
                        break;
                    case 11:
                        // Remove a course
                        try {
                            courseService.delete(course);
                        } catch (IllegalArgumentException e) {
                            System.out.println("An error occurred: " + e.getMessage());
                        }
                        break;
                    case 12:
                        // Edit course information
                        updateCourse();
                        break;
                    case 13:
                        try {
                            System.out.println(displayEmployeePayStubAndInfo());
                        } catch (NullPointerException e) {
                            System.out.println("An error occurred: " + e.getMessage());
                        }
                        break;
                    case 14:
                        System.out.println("Exiting employee menu.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    public void loginAsStudent() {
        Scanner scanner = new Scanner(System.in);
        List<Course> enrolledCourses = new ArrayList<>();
        List<Course> availableCourses = new ArrayList<>();
        List<Course> passedCourses = new ArrayList<>();
        List<Grade> grades = new ArrayList<>();

        int choice = 0;
        while (true) {
            System.out.println("Logging in as Student...");
            System.out.println("1. Display student information");
            System.out.println("2. Display enrolled courses");
            System.out.println("3. Enrollment courses");
            System.out.println("4. Display enrolled courses with grades");
            System.out.println("5. Exit");
            System.out.print("Please choose an option: ");
            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        // View student information
                        try {
                            System.out.println(displayStudentInfo());
                        } catch (NullPointerException e) {
                            System.out.println("An error occurred: " + e.getMessage());
                        }
                        break;
                    case 2:
                        // View enrolled courses
                        try {
                            displayEnrolledCourses();
                            //System.out.println(displayEnrollments());
                        } catch (LazyInitializationException e) {
                            System.out.println("An error occurred: " + e.getMessage());
                        }
                        break;
                    case 3:
                        try {
                            enrollmentCourses();
                        } catch (NullPointerException e) {
                            System.out.println("An error occurred: " + e.getMessage());
                        }
                        break;
                    case 4:
                        try {
//                            displayStudentGrades();
                            displayStudentGradesTwo();
                        } catch (NullPointerException e) {
                            System.out.println("An error occurred: " + e.getMessage());
                        }
                        break;
                    case 5:
                        System.out.println("Exiting student menu.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    public void loginAsProfessor() {
        Scanner scanner = new Scanner(System.in);

        int choice = 0;
        while (true) {
            System.out.println("Logging in as Professor...");
            System.out.println("1. View personal information");
            System.out.println("2. Enter grades for students");
            System.out.println("3. View pay stub and info");
            System.out.println("4. Exit");
            System.out.print("Please choose an option: ");
            try {
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // View personal information
                        try {
                            System.out.println(displayProfessorInfo());
                        } catch (NullPointerException ex) {
                            System.out.println("An error occurred: " + ex.getMessage());
                        }
                        break;
                    case 2:
                        // Enter grades for students
                        try {
                            registerStudentGradeByProfessor();
                        } catch (IllegalArgumentException ex) {
                            System.out.println("An error occurred: " + ex.getMessage());
                        }
                        break;
                    case 3:
                        // View pay stub and info
                        System.out.println("please enter semester: ");
                        String semester = getFormattedSemester(scanner);
                        try {
                            displayProfessorSalary(semester);
                        } catch (NoResultException e) {
                            System.out.println("Professor with this semester was not found." + e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.println("Exiting professor menu.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    /* public static Student createStudent() {
         Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
         Student student = null;

         Set<ConstraintViolation<Student>> violations;
         do {
             System.out.println("Create a new student:");
             System.out.print("Please enter student's first name: ");
             String firstName = scanner.nextLine();

             System.out.print("Please enter student's last name: ");
             String lastName = scanner.nextLine();

             String email;
             do {
                 System.out.println("Please enter student's email: ");
                 email = scanner.nextLine();
                 if (EmailValidator.isEmailValid(email)) {
                     System.out.println("Email address is valid!");
                     break;
                 } else {
                     System.out.println("Invalid email address. Please enter a valid email address.");
                 }
             } while (true);

             String phoneNumber;
             do {
                 System.out.println("Please enter student's phoneNumber: ");
                 phoneNumber = scanner.nextLine();
                 if (PhoneNumberValidator.isIranianPhoneNumberValid(phoneNumber)) {
                     System.out.println("Iranian phone number is valid!");
                     break;
                 } else {
                     System.out.println("Invalid Iranian phone number. Please enter a valid phone number.");
                 }
             } while (true);

             String nationalCode;
             do {
                 System.out.println("Please enter student's nationalCode: ");
                 nationalCode = scanner.nextLine();
                 if (NationalCodeValidator.isNationalCodeValid(nationalCode)) {
                     System.out.println("National code is valid!");
                     break;
                 } else {
                     System.out.println("Invalid national code. Please enter a valid national code.");
                 }
             } while (true);

             Address address = AddressMenu.createAddress();
             User user = new User();
             UserAddress userAddress = new UserAddress(user, address);
             List<UserAddress> userAddresses = UserAddressMenu.createUserAddresses();
             userAddresses.add(userAddress);


             System.out.print("Please enter student's number: ");
             String studentNumber = scanner.nextLine();

             System.out.print("Please enter student's Previous GPA: ");
             double previousGPA = scanner.nextDouble();
             scanner.nextLine(); // Consume newline

             System.out.print("Please enter student's current term: ");
             String currentTerm = scanner.nextLine();

             student = new Student();
             student.setFirstName(firstName);
             student.setLastName(lastName);
             student.setEmail(email);
             student.setPhoneNumber(phoneNumber);
             student.setNationalCode(nationalCode);
             student.setUserAddresses(userAddresses);
             student.setStudentNumber(studentNumber);
             student.setPreviousGPA(previousGPA);
             student.setCurrentTerm(AcademicTerm.valueOf(currentTerm)); // Assuming AcademicTerm is an Enum

             violations = validator.validate(student);
             if (!violations.isEmpty()) {
                 System.out.println("Invalid input. Please try again.");
             }
         } while (!violations.isEmpty());

         return student;
     }*/
    public void createStudent() {

        System.out.println("Create a new student:");

        String username = getUniqeUserName(scanner);

        String password = getPasswordFromUserInput(scanner);

        String firstName = getValidFirstName(scanner);

        String lastName = getValidLastName(scanner);

        String email = getEmailFromUserInput(scanner);

        String phoneNumber = getPhoneNumberFromUserInput(scanner);

        String nationalCode = getNationalCodeFromUserInput(scanner);

        AcademicTerm currentTerm = getAcademicTermFromUserInput(scanner);

        Department department = getDepartmentFromUserInput(scanner);

        double previousGPA = getValidPreviousGPA(scanner);

        String currentSemester = getFormattedSemester(scanner);

        String studentNumber = getUniqeStudentNumber(scanner);

        Student student = new Student();
        student.setUsername(username);
        student.setPassword(password);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setPhoneNumber(phoneNumber);
        student.setNationalCode(nationalCode);
        student.setStudentNumber(studentNumber);
        student.setPreviousGPA(previousGPA);
        student.setCurrentTerm(currentTerm);
        student.setDepartment(department);
        student.setSemester(currentSemester);
        try {
            studentService.saveOrUpdate(student);
        } catch (NullPointerException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    /*public void displayCoursesMenu(Student student) {
        // Retrieve enrolled, passed, and available courses for the student
        List<Course> enrolledCourses = enrollmentService.getEnrolledCourses(student);
        List<Course> passedCourses = gradeService.getPassedCourses(student);
        List<Course> availableCourses = courseService.getAvailableCourses(student);

        // Display the menu
        System.out.println("Enrolled Courses:");
        displayCourses(enrolledCourses);

        System.out.println("Passed Courses:");
        displayCourses(passedCourses);

        System.out.println("Available Courses:");
        displayCourses(availableCourses);

    }*/
    private List<Enrollment> displayEnrollments() {
        Long studentId = getStudentId();
        List<Enrollment> enrollments = enrollmentService.enrollmentCourseByCourseId(studentId);
        for (Enrollment enrollment : enrollments) {
            if (Objects.equals(enrollment.getStudentId(), studentId)) {
                System.out.println("Course found.");
            } else {
                System.out.println("Course not found.");
            }
        }
        return enrollments;
    }

    //    public List<Course> showAllCourses() {
//        List<Course> courses = enrollmentService.findEnrolledCoursesByStudent(student);
//        return courses;
//    }
    public void updateStudent() throws Exception {
        try {
            String firstName = getValidFirstName(scanner);
            String lastName = getValidLastName(scanner);
            student = studentService.findByFullName(firstName, lastName);
        } catch (NoResultException e) {
            System.out.println("this student is not exist");
            loginAsEducationEmployee();
        }
        System.out.println("Edit student details:");
        if (student != null) {

            String username = getUniqeUserName(scanner);
            student.setUsername(username);

            String password = getPasswordFromUserInput(scanner);
            student.setPassword(password);

            System.out.print("First name (" + student.getFirstName() + "): ");
            String firstName = getValidFirstName(scanner);
            if (!firstName.isEmpty()) {
                student.setFirstName(firstName);
            }

            System.out.print("Last name (" + student.getLastName() + "): ");
            String lastName = getValidLastName(scanner);
            if (!lastName.isEmpty()) {
                student.setLastName(lastName);
            }

            String email = getEmailFromUserInput(scanner);
            if (!email.isEmpty()) {
                student.setEmail(email);
            }

            String phoneNumber = getPhoneNumberFromUserInput(scanner);
            if (!phoneNumber.isEmpty()) {
                student.setPhoneNumber(phoneNumber);
            }

            String nationalCode = getNationalCodeFromUserInput(scanner);
            if (!nationalCode.isEmpty()) {
                student.setNationalCode(nationalCode);
            }

            System.out.print("Student number (" + student.getStudentNumber() + "): ");
            String studentNumber = getUniqeStudentNumber(scanner);
            if (!studentNumber.isEmpty()) {
                student.setStudentNumber(studentNumber);
            }

            System.out.print("Previous GPA (" + student.getPreviousGPA() + "): ");
            double previousGPA = getValidPreviousGPA(scanner);
            if (previousGPA != 0.0) {
                student.setPreviousGPA(previousGPA);
            }

            System.out.print("Current term (" + student.getCurrentTerm() + "): ");
            String term = scanner.nextLine();
            if (!term.isEmpty()) {
                student.setCurrentTerm(AcademicTerm.valueOf(term.toUpperCase()));
            }

            Department department = getDepartmentFromUserInput(scanner);
            if (department == null) {
                student.setDepartment(department);
            }
        }
        try {
            studentService.saveOrUpdate(student);
        } catch (IllegalArgumentException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void createEmployee() {

        System.out.println("Create a new employee:");

        String username = getUniqeUserName(scanner);

        String password = getPasswordFromUserInput(scanner);

        String firstName = getValidFirstName(scanner);

        String lastName = getValidLastName(scanner);

        String email = getEmailFromUserInput(scanner);

        String phoneNumber = getPhoneNumberFromUserInput(scanner);

        String nationalCode = getNationalCodeFromUserInput(scanner);

        Department department = getDepartmentFromUserInput(scanner);

        String employeeNumber = getUniqeEmployeeNumber(scanner);

        Employee employee = new Employee();
        employee.setUsername(username);
        employee.setPassword(password);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setPhoneNumber(phoneNumber);
        employee.setDepartment(department);
        employee.setNationalCode(nationalCode);
        employee.setEmployeeNumber(employeeNumber);
        try {
            employeeService.saveOrUpdate(employee);
        } catch (IllegalArgumentException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void updateEmployee() {
        try {
            String firstName = getValidFirstName(scanner);
            String lastName = getValidLastName(scanner);
            employee = employeeService.findByFullName(firstName, lastName);
        } catch (NoResultException e) {
            System.out.println("this employee not exist");
            updateEmployee();
        }
        System.out.println("Update professor information:");

        if (employee != null) {

            String username = getUniqeUserName(scanner);
            employee.setUsername(username);

            String password = getPasswordFromUserInput(scanner);
            employee.setPassword(password);

            System.out.print("First name (" + employee.getFirstName() + "): ");
            String firstName = getValidFirstName(scanner);
            if (!firstName.isEmpty()) {
                employee.setFirstName(firstName);
            }

            System.out.print("Last name (" + employee.getLastName() + "): ");
            String lastName = getValidLastName(scanner);
            if (!lastName.isEmpty()) {
                employee.setLastName(lastName);
            }

            String email = getEmailFromUserInput(scanner);
            if (!email.isEmpty()) {
                employee.setEmail(email);
            }

            String phoneNumber = getPhoneNumberFromUserInput(scanner);
            if (!phoneNumber.isEmpty()) {
                employee.setPhoneNumber(phoneNumber);
            }

            String nationalCode = getNationalCodeFromUserInput(scanner);
            if (!nationalCode.isEmpty()) {
                employee.setNationalCode(nationalCode);
            }

            Department department = getDepartmentFromUserInput(scanner);
            if (department != null) {
                employee.setDepartment(department);
            }

            System.out.print("Employee number (" + employee.getEmployeeNumber() + "): ");
            String employeeNumber = getUniqeEmployeeNumber(scanner);
            if (employeeNumber != null) {
                employee.setEmployeeNumber(employeeNumber);
            }
        }
        try {
            employeeService.saveOrUpdate(employee);
        } catch (IllegalArgumentException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void createProfessor() {

        System.out.println("Create a new professor:");

        String username = getUniqeUserName(scanner);

        String password = getPasswordFromUserInput(scanner);

        String firstName = getValidFirstName(scanner);

        String lastName = getValidLastName(scanner);

        String email = getEmailFromUserInput(scanner);

        String phoneNumber = getPhoneNumberFromUserInput(scanner);

        String nationalCode = getNationalCodeFromUserInput(scanner);

        AcademicTerm currentTerm = getAcademicTermFromUserInput(scanner);

        Department department = getDepartmentFromUserInput(scanner);

        String professorNumber = getUniqueProfessorNumber(scanner);

        List<Course> coursesForProfessor = new ArrayList<>();

        ProfessorType professorType = getProfessorType(scanner);

        String semester = getFormattedSemester(scanner);

        int teachingUnits = getTeachingUnits(scanner);

        Professor professor = new Professor();
        professor.setUsername(username);
        professor.setPassword(password);
        professor.setFirstName(firstName);
        professor.setLastName(lastName);
        professor.setEmail(email);
        professor.setPhoneNumber(phoneNumber);
        professor.setNationalCode(nationalCode);
        professor.setProfessorNumber(professorNumber);
        professor.setProfessorType(professorType);
        professor.setTeachingUnits(teachingUnits);
        professor.setCurrentTerm(currentTerm);
        professor.setSemester(semester);
        professor.setDepartment(department);
        // professor.setCourses(coursesForProfessor);
        try {
            professorService.saveOrUpdate(professor);
        } catch (IllegalArgumentException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void updateProfessor() {
        Professor professor = null;
        try {
            String firstName = getValidFirstName(scanner);
            String lastName = getValidLastName(scanner);
            professor = professorService.findByFullName(firstName, lastName);
        } catch (NoResultException e) {
            System.out.println("this teacher is not exist");
            updateProfessor();
        }
        System.out.println("Update professor information:");
        if (professor != null) {

            String username = getUniqeUserName(scanner);
            employee.setUsername(username);

            String password = getPasswordFromUserInput(scanner);
            employee.setPassword(password);

            System.out.print("First name (" + professor.getFirstName() + "): ");
            String firstName = getValidFirstName(scanner);
            if (!firstName.isEmpty()) {
                professor.setFirstName(firstName);
            }

            System.out.print("Last name (" + professor.getLastName() + "): ");
            String lastName = getValidLastName(scanner);
            if (!lastName.isEmpty()) {
                professor.setLastName(lastName);
            }


            System.out.print("Email (" + professor.getEmail() + "): ");
            String email = getEmailFromUserInput(scanner);
            if (!email.isEmpty()) {
                professor.setEmail(email);
            }

            System.out.print("Phone Number (" + professor.getPhoneNumber() + "): ");
            String phoneNumber = getPhoneNumberFromUserInput(scanner);
            if (!phoneNumber.isEmpty()) {
                professor.setPhoneNumber(phoneNumber);
            }

            System.out.print("NationalCode (" + professor.getNationalCode() + "): ");
            String nationalCode = getNationalCodeFromUserInput(scanner);
            if (!nationalCode.isEmpty()) {
                professor.setNationalCode(nationalCode);
            }

            System.out.print("Professor Number (" + professor.getProfessorNumber() + "): ");
            String professorNumber = getUniqueProfessorNumber(scanner);
            if (!professorNumber.isEmpty()) {
                professor.setProfessorNumber(professorNumber);
            }

            System.out.print("Professor type (" + professor.getProfessorType() + ")");
            ProfessorType professorType = getProfessorType(scanner);
            if (professorType != null) {
                professor.setProfessorType(professorType);
            }

            System.out.print("Teaching units (" + professor.getTeachingUnits() + "): ");
            int teachingUnits = getTeachingUnits(scanner);
            if (teachingUnits != 0) {
                professor.setTeachingUnits(teachingUnits);
            }

            System.out.print("Semester (" + professor.getSemester() + "): ");
            String semester = getFormattedSemester(scanner);
            if (!semester.isEmpty()) {
                professor.setSemester(semester);
            }

            System.out.print("Academic term (" + professor.getCurrentTerm() + "): ");
            AcademicTerm currentTerm = getAcademicTermFromUserInput(scanner);
            if (currentTerm != null) {
                professor.setCurrentTerm(currentTerm);
            }

            System.out.print("Department (" + professor.getDepartment() + "): ");
            Department department = getDepartmentFromUserInput(scanner);
            if (department != null) {
                professor.setDepartment(department);
            }
        }
        try {
            professorService.saveOrUpdate(professor);
        } catch (IllegalArgumentException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void createCourse() {
        Course course = new Course();

        System.out.println("Enter course details:");
        String courseName = getUniqueCourseName(scanner);
        course.setCourseName(courseName);

        AcademicTerm term = getAcademicTermFromUserInput(scanner);
        course.setTerm(term);

        Department department = getDepartmentFromUserInput(scanner);
        course.setDepartment(department);

        int units = getValidNumberOfUnits(scanner);
        course.setUnits(units);

        String semester = getFormattedSemester(scanner);
        course.setSemester(semester);

        try {
            courseService.saveOrUpdate(course);
        } catch (IllegalArgumentException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void updateCourse() throws Exception {
        Course course = null;
        try {
            System.out.println("please enter the lesson name");
            String name = getUniqueCourseName(scanner);
            course = courseService.findByCourseName(name);
        } catch (NoResultException e) {
            System.out.println("this lesson is not exist");
            updateCourse();
        }
        System.out.println("Edit course details:");
        assert course != null;
        System.out.print("Course name (" + course.getCourseName() + "): ");
        String courseName = getUniqueCourseName(scanner);
        if (!courseName.isEmpty()) {
            course.setCourseName(courseName);
        }

      /*  System.out.print("Professor ID (" + course.getProfessor().getId() + "): ");
        long professorId;
        do {
            System.out.print("Professor ID: ");
            try {
                professorId = Long.parseLong(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format. Please enter a valid professor ID.");
            }
        } while (true);
        try {
            Professor professor = professorService.findById(professorId);
            course.setProfessor(professor);

        } catch (NotFoundException ex) {
            System.out.println("Professor not found: " + ex.getMessage());
        }*/

        System.out.print("Academic term (" + course.getTerm() + "): ");
        String term = scanner.nextLine();
        if (!term.isEmpty()) {
            course.setTerm(AcademicTerm.valueOf(term.toUpperCase()));
        }

        System.out.print("Department (" + course.getDepartment() + "): ");
        Department department = getDepartmentFromUserInput(scanner);
        if (department != null) {
            course.setDepartment(department);
        }

        System.out.print("Units (" + course.getUnits() + "): ");
        int units = getValidNumberOfUnits(scanner);
        course.setUnits(units);

        System.out.println("Semester (" + course.getSemester() + "): ");
        String semester = getFormattedSemester(scanner);
        course.setSemester(semester);

        try {
            courseService.saveOrUpdate(course);
        } catch (IllegalArgumentException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

  /*  public void displayEnrolledCourses(Student student) {
        if (student == null) {
            System.out.println("Invalid student. Please provide a valid student.");
            return;
        }

        // Get the student from the database to ensure it's managed
        student = studentService.findById(student.getId());

        // Retrieve enrolled courses from the student
        List<Course> enrolledCourses = student.getEnrolledCourses();

        // Display the enrolled courses
        if (enrolledCourses != null && !enrolledCourses.isEmpty()) {
            System.out.println("Enrolled courses for " + student.getFirstName() + " " + student.getLastName() + ":");
            for (Course course : enrolledCourses) {
                System.out.println(course.getId() + " - " + course.getCourseName());
            }
        } else {
            System.out.println("No enrolled courses found for " + student.getFirstName() + " " + student.getLastName() + ".");
        }
    }*/
//    private Grade getGradeForStudentInCourse(Student student, Course course) {
//        for (Grade grade : student.getGrades()) {
//            if (grade.getCourseId().equals(course)) {
//                return grade;
//            }
//        }
//        return null;
//    }

    //    public void displayEnrolledCoursesWithGrades(Student student) {
//        List<Course> enrolledCourses = student.getEnrolledCourses();
//
//        if (enrolledCourses.isEmpty()) {
//            System.out.println("You have not enrolled in any courses yet.");
//        } else {
//            System.out.println("Enrolled Courses:");
//            for (Course course : enrolledCourses) {
//                Grade grade = getGradeForStudentInCourse(student, course);
//                String gradeInfo = (grade != null) ? "Grade: " + grade.getGrade() : "Grade: Not available";
//                System.out.println("Course Name: " + course.getCourseName() + ", " + gradeInfo);
//            }
//        }
//    }
    public int calculateMaxCredits(Student student) {
        double previousGPA = student.getPreviousGPA();
        int maxCredits;
        if (previousGPA > 18.0) {
            maxCredits = 24;
        } else {
            maxCredits = 20;
        }
        return maxCredits;
    }

    public String displayProfessorInfo() {
        String firstName = getValidFirstName(scanner);
        String lastName = getValidLastName(scanner);
        Professor professor = professorService.findByFullName(firstName, lastName);
        StringBuilder info = new StringBuilder();
        if (professor != null) {
            info.append("First Name: ").append(professor.getFirstName()).append("\n");
            info.append("Last Name: ").append(professor.getLastName()).append("\n");
            info.append("Professor Number: ").append(professor.getProfessorNumber()).append("\n");
            info.append("Email: ").append(professor.getEmail()).append("\n");
            info.append("Department: ").append(professor.getDepartment()).append("\n");
            info.append("Professor Type: ").append(professor.getProfessorType()).append("\n");
            info.append("Teaching Units: ").append(professor.getTeachingUnits()).append("\n");
            info.append("Professor Type: ").append(professor.getProfessorType()).append("\n");

            // Convert the list of professor's teaching courses into a string and add to the information
            /*List<String> courseNames = professor.getCourses().stream()
                    .map(Course::getCourseName)
                    .collect(Collectors.toList());
            info.append("Courses: ").append(String.join(", ", courseNames)).append("\n");*/

            //Convert the list of students under the supervision of the professor into a field and add it to the information
            // Check if the list of students is not empty
      /*  if (this.getStudents() != null) {
            List<String> studentNames = this.getStudents().stream()
                    .map(student -> student.getFirstName() + " " + student.getLastName())
                    .collect(Collectors.toList());
            info.append("Students: ").append(String.join(", ", studentNames)).append("\n");
        }*/
        } else {
            System.out.println("No professor found");
        }
        return info.toString();
    }

    public String displayStudentInfo() {
        String firstName = getValidFirstName(scanner);
        String lastName = getValidLastName(scanner);
        student = studentService.findByFullName(firstName, lastName);
        StringBuilder info = new StringBuilder();
        if (student != null) {
            System.out.println("Student Info:");
            info.append("First Name: ").append(student.getFirstName()).append("\n");
            info.append("Last Name: ").append(student.getLastName()).append("\n");
            info.append("Phone Number: ").append(student.getPhoneNumber()).append("\n");
            info.append("Student Number: ").append(student.getStudentNumber()).append("\n");
            info.append("Email: ").append(student.getEmail()).append("\n");
            info.append("Department: ").append(student.getDepartment() != null ? student.getDepartment() : "N/A").append("\n");
       /* info.append("Enrolled Courses:\n");
        for (Course course : this.enrolledCourses) {
            info.append("- ").append(course.getCourseName()).append("\n");
        }

        info.append("Passed Courses:\n");
        for (Course course : this.passedCourses) {
            info.append("- ").append(course.getCourseName()).append("\n");
        }

        info.append("Available Courses:\n");
        for (Course course : this.availableCourses) {
            info.append("- ").append(course.getCourseName()).append("\n");
        }

        info.append("Grades:\n");
        for (Grade grade : this.grades) {
            info.append("- Course: ").append(grade.getCourse().getCourseName());
            info.append(", Grade: ").append(grade.getGrade()).append("\n");
        }*/
        } else {
            System.out.println("No student found");
        }
        return info.toString();
    }

    public void displayProfessorSalary(String semester) {
        String firstName = getValidFirstName(scanner);
        String lastName = getValidLastName(scanner);
        try {
            professor = professorService.findByFullName(firstName, lastName);
        } catch (NullPointerException e) {
            System.out.println("Professor with this firstName and lastName was not found." + e.getMessage());
        }
        try {
            professorService.findBySemester(semester);
        } catch (IllegalArgumentException e) {
            System.out.println("Professor with this semester was not found." + e.getMessage());
        }
        try {
            int teachingUnits = professor.getTeachingUnits();
            double additionalSalary = 0.0;

            if (professor.getProfessorType() == ProfessorType.FACULTY_MEMBER) {
                additionalSalary = Professor.UNIT_SALARY + (teachingUnits * 1000000);
            } else if (professor.getProfessorType() == ProfessorType.TEACHING_ASSISTANT) {
                additionalSalary = teachingUnits * 1000000;
            }
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedSalary = decimalFormat.format(additionalSalary);
            System.out.println("FirstName: " + professor.getFirstName() + ", LastName: " + professor.getLastName() + ", Professor Number: " + professor.getProfessorNumber() + ", Email: " + professor.getEmail() + ", Teaching Units: " + teachingUnits + ", Salary: " + formattedSalary);
        } catch (NullPointerException e) {
            System.out.println("Professor not found. " + e.getMessage());
        }
    }

    public String displayEmployeePayStubAndInfo() {
        String firstName = getValidFirstName(scanner);
        String lastName = getValidLastName(scanner);
        employee = employeeService.findByFullName(firstName, lastName);
        StringBuilder payStubBuilder = new StringBuilder();
        if (employee != null) {
            // Create pay stub and info
            payStubBuilder.append("First Name: ").append(employee.getFirstName()).append("\n");
            payStubBuilder.append("Last Name: ").append(employee.getLastName()).append("\n");
            payStubBuilder.append("Employee Number: ").append(employee.getEmployeeNumber()).append("\n");
            payStubBuilder.append("Phone Number: ").append(employee.getPhoneNumber()).append("\n");
            payStubBuilder.append("Email: ").append(employee.getEmail()).append("\n");
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedSalary = decimalFormat.format(Employee.BASE_SALARY);
            payStubBuilder.append("Base Salary: ").append(formattedSalary).append(" Rial").append("\n");
        } else {
            System.out.println("No employee found");
        }
        // Return the pay stub and info as a string
        return payStubBuilder.toString();
    }

    public Long getCourseId() {
        course = null;
        try {
            System.out.println("please enter the course name: ");
            String courseName = scanner.next();
            course = courseService.findByCourseName(courseName);
        } catch (NoResultException e) {
            System.out.println("this course is not exist");
        }
        if (course != null) {
            return course.getId();
        }
        return null;
    }

    public Long getStudentId() {
        student = null;
        try {
            System.out.println("please enter the student info: ");
            String firstName = getValidFirstName(scanner);
            String lastName = getValidLastName(scanner);
            student = studentService.findByFullName(firstName, lastName);
        } catch (NoResultException e) {
            System.out.println("this student is not exist");
            getStudentId();
        }
        if (student != null) {
            return student.getId();
        } else {
            System.out.println("wrong student info");
        }
        return null;
    }

    public Long getProfessorId() {
        professor = null;
        try {
            System.out.println("please enter the professor info: ");
            String firstName = getValidFirstName(scanner);
            String lastName = getValidLastName(scanner);
            professor = professorService.findByFullName(firstName, lastName);
        } catch (NoResultException e) {
            System.out.println("this professor is not exist");
            getStudentId();
        }
        if (professor != null) {
            return professor.getId();
        } else {
            System.out.println("wrong professor info");
        }
        return null;
    }

    public void registerStudentGradeByProfessor() {

//        System.out.println("Info of the student whose grade is to be entered: ");
//        String firstName = getValidFirstName(scanner);
//        String lastName = getValidLastName(scanner);
//         student = null;
//        while (student == null) {
//            student = studentService.findByFullName(firstName, lastName);
//            if (student == null) {
//                System.out.println("Student with this first name and last name was not found. Please try again.");
//                firstName = getValidFirstName(scanner);
//                lastName = getValidLastName(scanner);
//            }
//        }
//        System.out.println("Info of the professor who wants to enter a grade: ");
//        String firstNameProfessor = getValidFirstName(scanner);
//        String lastNameProfessor = getValidLastName(scanner);
//         professor = null;
//        while (professor == null) {
//             professor = professorService.findByFullName(firstNameProfessor, lastNameProfessor);
//            if (professor == null) {
//                System.out.println("Professor with this first name and last name was not found. Please try again.");
//                firstNameProfessor = getValidFirstName(scanner);
//                lastNameProfessor = getValidLastName(scanner);
//            }
//        }
//         course = null;
//        while (course == null) {
//            System.out.println("Please enter the course name: ");
//            String courseName = scanner.next();
//            try {
//                course = courseService.findByCourseName(courseName);
//            } catch (NoResultException e) {
//                System.out.println("Course not found: " + e.getMessage());
//            }
//        }
//        boolean isValidGrade = false;
//        double grade = getValidGrade(scanner);
//        String semester = getFormattedSemester(scanner);
//
//        while (!isValidGrade) {
//            try {
//                if ((grade < Grade.MIN_LOW_GRADE) || (grade > Grade.MAX_HIGH_GRADE)) {
//                    throw new IllegalArgumentException("Invalid grade. Grade must be between 0 and 20");
//                }
//                Grade newGrade = new Grade();
//                newGrade.setStudent(student);
//                newGrade.setCourse(course);
//                newGrade.setProfessor(professor);
//                newGrade.setGrade(grade);
//                newGrade.setSemester(semester);
//                gradeService.saveOrUpdate(newGrade);
//                isValidGrade = true;
//            } catch (IllegalArgumentException ex) {
//                System.out.println("Error: " + ex.getMessage());
//                System.out.print("Enter the student's grade (between 0 and 20): ");
//                grade = scanner.nextDouble();
//            }
//        }
//    }
        Long courseId = getCourseId();
        String term = null;
        if (courseId != null) {
            System.out.println("please enter the term and year (1-1400)");
            term = getFormattedSemester(scanner);
        } else {
            registerStudentGradeByProfessor();
        }
        boolean flag = true;
        while (flag) {
            Long studentId = getStudentId();
            Long professorId = getProfessorId();
            if (studentId != null && courseId != null && professorId != null) {
                List<Grade> grades = gradeService.registerStudentGradeByCourseId(courseId);
                int check = 0;
                for (Grade grade : grades) {
                    if (Objects.equals(grade.getStudentId(), studentId) && Objects.equals(grade.getProfessorId(), professorId) && grade.getGrade() > Grade.PASSING_GRADE && grade.getGrade() < Grade.MAX_HIGH_GRADE) {
                        System.out.println("this course is already passed");
                        check = 1;
                        break;
                    } else if (Objects.equals(grade.getStudentId(), studentId) || Objects.equals(grade.getProfessorId(), professorId) || grade.getGrade() < Grade.PASSING_GRADE || grade.getGrade() > Grade.MAX_HIGH_GRADE) {
                        gradeService.delete(grade);
                    }
                }
                if (check == 0) {
                    System.out.println("please enter grade");
                    double grade = getValidGrade(scanner);
                    Grade newGrade = new Grade();
                    newGrade.setStudentId(studentId);
                    newGrade.setCourseId(courseId);
                    newGrade.setProfessorId(professorId);
                    newGrade.setGrade(grade);
                    newGrade.setSemester(term);
                    gradeService.saveOrUpdate(newGrade);
                } else {
                    registerStudentGradeByProfessor();
                }
                System.out.println("do you want to continue:y/n");
                String choose = getStringFromUser();
                if (choose.equals("n")) {
                    flag = false;
                    break;
                }
            }
        }
    }

    public void enrollmentCourses() {
        Long courseId = getCourseId();
        String term = null;
        if (courseId != null) {
            System.out.println("please enter the term and year (1-1400)");
            term = getFormattedSemester(scanner);
        } else {
            enrollmentCourses();
        }
        boolean flag = true;
        while (flag) {
            Long studentId = getStudentId();
            Long professorId = getProfessorId();
            if (studentId != null && courseId != null && professorId != null) {
                int check = 0;
                try {
                    List<Enrollment> enrollments = enrollmentService.enrollmentCourseByCourseId(courseId);
                    for (Enrollment enrollment : enrollments) {
                        if (Objects.equals(enrollment.getStudentId(), studentId) && Objects.equals(enrollment.getProfessorId(), professorId) && grade.getGrade() > Grade.PASSING_GRADE && grade.getGrade() < Grade.MAX_HIGH_GRADE) {
                            System.out.println("this course is already passed");
                            check = 1;
                            break;
                        }
                    }
                } catch (EntityExistsException e) {
                    System.out.println("You are already enrolled in this course." + e.getMessage());
                }

                if (studentId != null && courseId != null && professorId != null) {
                    // Check if the student is already enrolled in the course
                    if (enrollmentService.isEnrolled(studentId, courseId)) {
                        System.out.println("You are already enrolled in this course.");
                        return;
                    }
                }
                // Check if the GPA of the previous semester allows enrollment in the course
                if (!checkGPA(studentId, courseId)) {
                    System.out.println("You do not meet the GPA requirement for this course.");
                    return;
                }
                if (check == 0) {
                    Enrollment enrollment1 = new Enrollment();
                    enrollment1.setStudentId(studentId);
                    enrollment1.setCourseId(courseId);
                    enrollment1.setProfessorId(professorId);
                    enrollment1.setSemester(term);
                    enrollmentService.saveOrUpdate(enrollment1);
                } else {
                    enrollmentCourses();
                }
                System.out.println("do you want to continue:y/n");
                String choose = getStringFromUser();
                if (choose.equals("n")) {
                    flag = false;
                    break;
                }
            }
        }
    }

    private boolean checkGPA(Long studentId, Long courseId) {
        Student student = studentService.findById(studentId);
        Course course = courseService.findById(courseId);

        // Check if the student meets the GPA requirement for the course
        if (student != null && course != null) {
            if (student.getPreviousGPA() >= Grade.HIGH_GPA_THRESHOLD) {
                return course.getUnits() <= Grade.MAX_UNITS_WITH_HIGH_GPA;
            } else {
                return course.getUnits() <= Grade.MAX_UNITS_WITH_LOW_GPA;
            }
        }
        return false;
    }

    public void displayStudentGrades() {
        Long studentId = getStudentId();
        List<Grade> grades = gradeService.findGradeByStudent(studentId);
        if (grades.isEmpty()) {
            System.out.println("You have not received any grades yet.");
        } else {
            System.out.println("Your grades:");
            for (Grade grade : grades) {
                course = courseService.findById(grade.getCourseId());
                System.out.println("Course: " + course.getCourseName() + ", Grade: " + grade.getGrade());
            }
        }
        System.out.println("grades: " + grades);
    }

    public void displayStudentGradesTwo() {
        Long studentId = getStudentId();
        List<Object[]> results = gradeService.findCourseNameAndGradeByStudent(studentId);
        if (results.isEmpty()) {
            System.out.println("You have not received any grades yet.");
        } else {
            System.out.println("Your grades:");
            for (Object[] result : results) {
                String courseName = (String) result[0];
                double gradeValue = (double) result[1];
                System.out.println("Course: " + courseName + ", Grade: " + gradeValue);
            }
        }
    }

    public void displayEnrolledCourses() {
        Long studentId = getStudentId();

        try {
            List<String> enrolledCourseNames = enrollmentService.findEnrolledCourseNamesByStudent(studentId);

            if (enrolledCourseNames.isEmpty()) {
                System.out.println("You are not enrolled in any courses.");
            } else {
                System.out.println("Enrolled Courses:");
                for (String courseName : enrolledCourseNames) {
                    System.out.println(courseName);
                }
            }
        } catch (NoResultException e) {
            System.out.println("Error: No enrolled courses found for the student.");
        }
    }

}





