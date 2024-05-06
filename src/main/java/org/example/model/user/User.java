package org.example.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.base.entity.BaseEntity;
import org.example.enums.Department;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
public class User extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name")
    String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name")
    String lastName;

    @NotBlank(message = "Phone number is required")
   // @Column(unique = true)
    String phoneNumber;


    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
   // @Column(unique = true)
    String email;


    //@Column(unique = true)
    String nationalCode;


    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    @Column(unique = true)
    String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    Department department;

    public User(String firstName, String lastName, String phoneNumber, String email, String nationalCode, String username, String password, Department department) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.nationalCode = nationalCode;
        this.username = username;
        this.password = password;
        this.department = department;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}