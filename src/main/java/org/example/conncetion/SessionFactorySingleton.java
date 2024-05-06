package org.example.conncetion;

import org.example.model.*;
import org.example.model.user.Employee;
import org.example.model.user.Professor;
import org.example.model.user.Student;
import org.example.model.user.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactorySingleton {

    private static class LazyHolder {
        static SessionFactory INSTANCE;

        static {
            var registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();

            INSTANCE = new MetadataSources(registry)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Course.class)
                    .addAnnotatedClass(Student.class)
                    .addAnnotatedClass(Professor.class)
                    .addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(Enrollment.class)
                    .addAnnotatedClass(Grade.class)
                    .buildMetadata()
                    .buildSessionFactory();
        }
    }

    public static SessionFactory getInstance() {
        return LazyHolder.INSTANCE;
    }
}