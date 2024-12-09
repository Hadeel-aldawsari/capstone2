package com.example.ezhal.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class OfficialProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Provider name should not be empty")
    @Size(min = 3, max = 50, message = "Provider name length should be between 3 and 50 characters")
    @Column(columnDefinition = "varchar(50) not null unique")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "provider Name should contain letters and spaces only")
    private String providerName;

    @NotEmpty(message = "Provider registration number should not be empty")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String registrationNumber;  // رقم السجل التجاريي

    @NotEmpty(message = "Provider address should not be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String providerAddress;

    @NotEmpty(message = "Provider phone number should not be empty")
    @Pattern(regexp = "^05[0-9]{8}$", message = "Phone number should start with 05 and contain exactly 10 digits")
    @Column(columnDefinition = "varchar(10) not null")
    private String phoneNumber;

    @NotEmpty(message = "Provider email should not be empty")
    @Column(columnDefinition = "varchar(30) not null unique")
    @Email(message = "enter valid email")
    private String email;

    @Column(columnDefinition = "timestamp not null")
    @PastOrPresent(message = "establishes date should be past or present")
    private LocalDate establishmentDate;  // تاريخ التأسيس


    @NotEmpty(message = "Service Area should not be empty")
    @Pattern(regexp = "^[A-Za-z\\s,]+$", message = "Service area should only contain letters, spaces, and commas")
    @Column(columnDefinition = "varchar(255)")
    private String serviceArea;


}
