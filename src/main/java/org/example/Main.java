package org.example;

import org.example.enums.AcademicTerm;
import org.example.enums.Department;
import org.example.enums.ProfessorType;
import org.example.model.*;
import org.example.model.user.Employee;
import org.example.model.user.Professor;
import org.example.model.user.Student;
import org.example.util.ApplicationContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws Exception {

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
        /*Menu menu = new Menu();
        menu.publicMenu();*/

        }
    public boolean hasPassedCourse(Course course, Student student) {
        List<Course> passedCourses = new ArrayList<>();
        return false;
    }

    public boolean hasDuplicateCourse(Course course, Student student) {
        List<Course> enrolledCourses = new ArrayList<>();
        return false;
    }


    public void selectCourses(Student student, List<Course> availableCourses) {
        List<Course> selectedCourses = new ArrayList<>();
        double totalUnitsSelected = 0;

        for (Course course : availableCourses) {

            // If the course has not been passed before and it is not a repeated course
            if (!hasPassedCourse(course, student) && !hasDuplicateCourse(course, student)) {
                // If the GPA of the previous semester was above the threshold and the number of selected units was less than the maximum allowed
                if (student.getPreviousGPA() >= Grade.HIGH_GPA_THRESHOLD && totalUnitsSelected < Grade.MAX_UNITS_WITH_HIGH_GPA) {
                    if (totalUnitsSelected + course.getUnits() <= Grade.MAX_UNITS_WITH_HIGH_GPA) {
                        selectedCourses.add(course);
                        totalUnitsSelected += course.getUnits();
                    }
                    // If the GPA of the previous semester was lower than the threshold and the number of selected units was less than the maximum allowed
                } else if (student.getPreviousGPA() < Grade.HIGH_GPA_THRESHOLD && totalUnitsSelected < Grade.MAX_UNITS_WITH_LOW_GPA) {
                    if (totalUnitsSelected + course.getUnits() <= Grade.MAX_UNITS_WITH_LOW_GPA) {
                        selectedCourses.add(course);
                        totalUnitsSelected += course.getUnits();
                    }
                }
            }
        }
        System.out.println(" " + selectedCourses);
    }

  /*  public void saveEnrollments(Student student, List<Course> selectedCourses) {
        for (Course course : selectedCourses) {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setTerm(course.getTerm());
            //  enrollment.setProfessor(course.getProfessor());
            repository.saveOrUpdate(enrollment);
        }
    }*/

  /*  public void addPassedCourse(Course course, Student student, Grade grade) {
        if (Grade.passingGrade <= grade.getGrade())
            student.getPassedCourses().add(course);
    }*/

   /* public void addSelectedCourse(Course course, Student student) {
        student.getSelectedCourses().add(course);
    }*/

    /*public boolean isCoursePassed(Course course, Student student) {
        return student.getPassedCourses().contains(course);
    }*/


   /* public boolean isCourseSelected(Course course, Student student) {
        return student.getSelectedCourses().contains(course);
    }*/

   /* public boolean isDuplicateCourseSelection(Course course, Student student) {
        return student.getSelectedCourses().stream().filter(c -> c.equals(course)).count() > 1;
    }*/

    public void showStudentInfo(Student student) {
        System.out.println(student.toString());
       // loginAsStudent();
    }

    /* public List<Course> getEnrolledCourses(Student student) {
        return repository.findEnrolledCoursesByStudent(student);
    }*/

//        List<Student> students = new ArrayList<>();
//        List<Course> courses = new ArrayList<>();
//        List<Enrollment> enrollments = new ArrayList<>();
//        List<Course> availableCourses = new ArrayList<>();
//        List<Course> passedCourses = new ArrayList<>();
//        List<Course> currentSelectedCourses = new ArrayList<>();
//
//        // Instantiate a Professor entity
//        Professor professor = new Professor();
//        professor.setUsername("hadi89");
//        professor.setPassword("44444444");
//        professor.setFirstName("hadi");
//        professor.setLastName("hadadi");
//        professor.setNationalCode("0510027977");
//        professor.setProfessorNumber("432423525");
//        professor.setEmail("hadii@gmail.com");
//        professor.setPhoneNumber("0965656565");
//        professor.setCurrentTerm(AcademicTerm.FALL);
//        //professor.setStudents(students);
//        // professor.setUserAddresses(addressesForProfessor);
//        professor.setDepartment(Department.COMPUTER_SCIENCE);
//        professor.setProfessorType(ProfessorType.FACULTY_MEMBER);
//        professor.setTeachingUnits(10);
//        ApplicationContext.getProfessorService().saveOrUpdate(professor);
//
//
//        Professor jabari = new Professor();
//        jabari.setUsername("hemed14");
//        jabari.setPassword("1234567");
//        jabari.setFirstName("hamed");
//        jabari.setLastName("jabari");
//        jabari.setNationalCode("0510028377");
//        jabari.setProfessorNumber("4252245245");
//        jabari.setEmail("jabarii@gmail.com");
//        jabari.setPhoneNumber("43343343433");
//        jabari.setCurrentTerm(AcademicTerm.SPRING);
//        jabari.setDepartment(Department.ART);
//        jabari.setProfessorType(ProfessorType.TEACHING_ASSISTANT);
//        jabari.setTeachingUnits(10);
//        ApplicationContext.getProfessorService().saveOrUpdate(jabari);
//
//        Employee employee = new Employee();
//        employee.setUsername("hasanj12");
//        employee.setPassword("123456");
//        employee.setFirstName("hasan");
//        employee.setLastName("hasani");
//        employee.setNationalCode("0510128977");
//        employee.setEmployeeNumber("2453527352");
//        employee.setPhoneNumber("42523415555");
//        employee.setEmail("hasan@gmail.com");
//        employee.setDepartment(Department.CIVIL_ENGINEERING);
//       // employee.setAdvises(students);
//        ApplicationContext.getEmployeeService().saveOrUpdate(employee);
//
//
//        Student student = new Student();
//        student.setUsername("hosseinj");
//        student.setPassword("333344444");
//        student.setFirstName("hossein");
//        student.setLastName("javadi");
//        student.setNationalCode("0510058977");
//        student.setPhoneNumber("09197033530");
//        student.setEmail("hosseinj13@yahoo.com");
//        student.setStudentNumber("43523523r423");
//        student.setDepartment(Department.ART);
//        student.setPreviousGPA(17.20);
//        student.setPassedCourses(passedCourses);
//        student.setAvailableCourses(availableCourses);
//       //student.setRegisteredCourses(currentSelectedCourses);
//        student.setCurrentTerm(AcademicTerm.SPRING);
//       // student.setProfessor(professor);
//        //student.setAdvisor(employee);
//        students.add(student);
//        ApplicationContext.getStudentService().saveOrUpdate(student);
//
//
//        Student hasan = new Student();
//        hasan.setUsername("hasanhh");
//        hasan.setPassword("7777777777");
//        hasan.setFirstName("hasan");
//        hasan.setLastName("hasani");
//        hasan.setNationalCode("0510028877");
//        hasan.setPhoneNumber("09217157394");
//        hasan.setEmail("hassanj13@yahoo.com");
//        hasan.setStudentNumber("2222222");
//        hasan.setDepartment(Department.BUSINESS);
//        hasan.setPreviousGPA(12.20);
//        hasan.setCurrentTerm(AcademicTerm.SPRING);
//      //  hasan.setProfessor(professor);
//       // hasan.setAdvisor(employee);
//        students.add(hasan);
//        ApplicationContext.getStudentService().saveOrUpdate(hasan);
//
//
//        // Create and add some Course entities to the list
//        Course course1 = new Course();
//        course1.setCourseName("Introduction to Programming");
//       // course1.setStudent(student);
//        course1.setProfessor(professor);
//        course1.setYear(2008);
//        course1.setUnits(3);
//        course1.setTerm(AcademicTerm.SPRING);
//        course1.setDepartment(Department.COMPUTER_SCIENCE);
//        courses.add(course1);
//
//        Course course2 = new Course();
//        course2.setCourseName("Database Systems");
//       // course2.setStudent(student);
//        course2.setProfessor(professor);
//        course2.setYear(2000);
//        course2.setUnits(4);
//        course2.setTerm(AcademicTerm.FALL);
//        course2.setDepartment(Department.COMPUTER_SCIENCE);
//        courses.add(course2);
//
//        Course course3 = new Course();
//        course3.setCourseName("Database Systems");
//        course3.setProfessor(jabari);
//        //course3.setStudent(hasan);
//        course3.setYear(2002);
//        course3.setUnits(3);
//        course3.setTerm(AcademicTerm.SPRING);
//        course3.setDepartment(Department.CIVIL_ENGINEERING);
//        courses.add(course3);
//
//
//        professor.setCourses(courses);
//        ApplicationContext.getCourseService().saveOrUpdate(course1);
//        ApplicationContext.getCourseService().saveOrUpdate(course2);
//        ApplicationContext.getCourseService().saveOrUpdate(course3);
//
//
//        Grade grade = new Grade();
//        grade.setCourse(course3);
//        grade.setStudent(student);
//        grade.setYear(1398);
//        grade.setGrade(21.0);
//        grade.setProfessor(professor);
//        grade.setTerm(AcademicTerm.SPRING);
//      //  ApplicationContext.getGradeService().registerStudentGradeByProfessor(grade.getStudent(), grade.getGrade(), grade.getCourse(), grade.getProfessor(), grade.getYear(), grade.getTerm());
//
//
//        //  System.out.println(ApplicationContext.getStudentService().calculateMaxCredits(student));
//
//
////        ApplicationContext.getProfessorService().displayProfessorInfo(professor.getId());
////        ApplicationContext.getStudentService().displayStudentInfo(student);
//
//
//        // ApplicationContext.getCourseRegistrationService().addPassedCourse(course1,student,grade1);
//        //System.out.println(student.getPassedCourses());
//
////        System.out.println(ApplicationContext.getProfessorService().displayProfessorSalary(String.valueOf(professor.getCurrentTerm())));
////        System.out.println(ApplicationContext.getProfessorService().displayProfessorSalary(String.valueOf(jabari.getCurrentTerm())));
//
//
//       /* passedCourses.add(course1);
//        student.setPassedCourses(passedCourses);
//        System.out.println(student.getPassedCourses());*/
//
//        availableCourses.add(course3);
//        student.setAvailableCourses(availableCourses);
//        //System.out.println(student.getAvailableCourses());
//
//        /*currentSelectedCourses.add(course2);
//        student.setSelectedCourses(currentSelectedCourses);
//        System.out.println(student.getSelectedCourses());*/
//
//
//        //System.out.println(ApplicationContext.getStudentService().selectCourses(student, availableCourses, student.getPreviousGPA()));
//
//        // System.out.println(ApplicationContext.getCourseService().showAllCourses().toString());
//
//
//        EmployeeInfoSingleton employeeInfoSingleton = EmployeeInfoSingleton.getInstance();
//        employeeInfoSingleton.setCurrentEmployee(employee);
//
//       /* String payStub = employee.DisplayEmployeePayStubAndInfo();
//        System.out.println(payStub);*/
//
//
//        //for update test
//       /* student.setFirstName("hasan");
//        student.setLastName("javadi");
//        student.setPhoneNumber("09197033530");
//        student.setEmail("hasanj13@yahoo.com");
//        student.setStudentNumber("22222222");
//        student.setDepartment(Department.CHEMICAL_ENGINEERING);
//        student.setAddresses(addressesForStudent);
//        student.setPreviousGPA(12.20);
//        student.setPassedCourses(passedCourses);
//        student.setAvailableCourses(availableCourses);
//        student.setSelectedCourses(currentSelectedCourses);
//        student.setCurrentTerm(AcademicTerm.SPRING);
//        student.setProfessor(professor);
//        student.setAdvisor(employee);
//        students.add(student);
//        ApplicationContext.getStudentService().saveOrUpdate(student);*/
//
//        //for delete test
//        //ApplicationContext.getStudentService().deleteById(5L);
//
//
//        // Register the student for the course
//        Enrollment enrollment = new Enrollment();
//        enrollment.setStudent(student);
//        enrollment.setCourse(course1);
//        enrollment.setYear(2022);
//        enrollment.setTerm(AcademicTerm.FALL);
//        enrollment.setProfessor(professor);
//        // Set the course registration for the student
//        enrollments.add(enrollment);

    }
