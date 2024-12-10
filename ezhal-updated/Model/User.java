package com.example.ezhal.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
//@Check(constraints = "gender IN ('female', 'male')")
//@Check(constraints = "userStatus IN ('active', 'suspend')")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "user name should not be empty")
    @Size(min=5,message = "name Length more than 4")
    @Column(columnDefinition ="varchar(20) not null unique")
    private String username;

    @NotEmpty(message = "password should not be empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()]).{8,20}$", message = "Password must be between 8 and 20 characters long, and include at least one digit, one lowercase letter, one uppercase letter, and one special character (!@#&()).")
    @Column(columnDefinition ="varchar(20) not null")
    private String password;

    @NotEmpty(message = "email should not be empty")
    @Email(message = "enter valid email")
    @Column(columnDefinition ="varchar(30) not null unique")
    private String email;

    @Column(columnDefinition = "timestamp not null default current_timestamp")
    private LocalDate registrationDate;

    @NotEmpty(message = "city should not be empty")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "city name should contain letters and spaces only")
    @Column(columnDefinition = "varchar(40) not null")
    private String city;


    @NotEmpty(message = "gender should not be empty")
    @Pattern(regexp = "female|male",message = "gender should be female or female")
    @Column(columnDefinition = "varchar(6) not null")
    private String gender;


    @Column(columnDefinition = "varchar(8) not null")
    private String userStatus;


}
