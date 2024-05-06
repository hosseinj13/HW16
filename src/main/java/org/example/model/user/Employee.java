package org.example.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@Entity

public class Employee extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Employee number is required")
    @Column(name = "employee_number", unique = true)
    String employeeNumber;

    public static final double BASE_SALARY = 150000000;


   /* @Column(name = "advises")
    @OneToMany(mappedBy = "advisor", cascade = CascadeType.ALL)
    List<Student> advises;*/

}

