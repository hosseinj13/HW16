package org.example.util;


import org.example.conncetion.SessionFactorySingleton;
import org.example.repository.course.CourseRepository;
import org.example.repository.course.CourseRepositoryImpl;
import org.example.repository.employee.EmployeeRepository;
import org.example.repository.employee.EmployeeRepositoryImpl;
import org.example.repository.enrollment.EnrollmentRepository;
import org.example.repository.enrollment.EnrollmentRepositoryImpl;
import org.example.repository.grade.GradeRepository;
import org.example.repository.grade.GradeRepositoryImpl;
import org.example.repository.professor.ProfessorRepository;
import org.example.repository.professor.ProfessorRepositoryImpl;
import org.example.repository.student.StudentRepository;
import org.example.repository.student.StudentRepositoryImpl;
import org.example.repository.user.UserRepository;
import org.example.repository.user.UserRepositoryImpl;
import org.example.service.course.CourseService;
import org.example.service.course.CourseServiceImpl;
import org.example.service.employee.EmployeeService;
import org.example.service.employee.EmployeeServiceImpl;
import org.example.service.enrollment.EnrollmentService;
import org.example.service.enrollment.EnrollmentServiceImpl;
import org.example.service.grade.GradeService;
import org.example.service.grade.GradeServiceImpl;
import org.example.service.professor.ProfessorService;
import org.example.service.professor.ProfessorServiceImpl;
import org.example.service.student.StudentService;
import org.example.service.student.StudentServiceImpl;
import org.example.service.user.UserService;
import org.example.service.user.UserServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ApplicationContext {

    static final SessionFactory SESSION_FACTORY;
    static final Session SESSION;

    private static final UserRepository USER_REPOSITORY;
    private static final CourseRepository COURSE_REPOSITORY;
    private static final StudentRepository STUDENT_REPOSITORY;
    private static final EmployeeRepository EMPLOYEE_REPOSITORY;
    private static final ProfessorRepository PROFESSOR_REPOSITORY;
    private static final EnrollmentRepository ENROLLMENT_REPOSITORY;
    private static final GradeRepository GRADE_REPOSITORY;



    private static final UserService USER_SERVICE;
    private static final CourseService COURSE_SERVICE;
    private static final StudentService STUDENT_SERVICE;
    private static final EmployeeService EMPLOYEE_SERVICE;
    private static final ProfessorService PROFESSOR_SERVICE;
    private static final EnrollmentService ENROLLMENT_SERVICE;
    private static final GradeService GRADE_SERVICE;




    static {
        SESSION_FACTORY = SessionFactorySingleton.getInstance();
        SESSION = SESSION_FACTORY.openSession();

        USER_REPOSITORY = new UserRepositoryImpl(SESSION);
        USER_SERVICE = new UserServiceImpl(USER_REPOSITORY);

        COURSE_REPOSITORY = new CourseRepositoryImpl(SESSION);
        COURSE_SERVICE = new CourseServiceImpl(COURSE_REPOSITORY);

        EMPLOYEE_REPOSITORY = new EmployeeRepositoryImpl(SESSION);
        EMPLOYEE_SERVICE = new EmployeeServiceImpl(EMPLOYEE_REPOSITORY);

        PROFESSOR_REPOSITORY = new ProfessorRepositoryImpl(SESSION);
        PROFESSOR_SERVICE = new ProfessorServiceImpl(PROFESSOR_REPOSITORY);

        ENROLLMENT_REPOSITORY = new EnrollmentRepositoryImpl(SESSION);
        ENROLLMENT_SERVICE = new EnrollmentServiceImpl(ENROLLMENT_REPOSITORY);


        GRADE_REPOSITORY = new GradeRepositoryImpl(SESSION);
        GRADE_SERVICE = new GradeServiceImpl(GRADE_REPOSITORY);

        STUDENT_REPOSITORY = new StudentRepositoryImpl(SESSION);
        STUDENT_SERVICE = new StudentServiceImpl(STUDENT_REPOSITORY);

    }

    public static UserService getUserService() {
        return USER_SERVICE;
    }

    public static CourseService getCourseService() {
        return COURSE_SERVICE;
    }

    public static StudentService getStudentService() {
        return STUDENT_SERVICE;
    }

    public static ProfessorService getProfessorService() {
        return PROFESSOR_SERVICE;
    }

    public static EnrollmentService getEnrollmentService() {
        return ENROLLMENT_SERVICE;
    }


    public static EmployeeService getEmployeeService() {
        return EMPLOYEE_SERVICE;
    }

    public static GradeService getGradeService() {
        return GRADE_SERVICE;
    }

}
