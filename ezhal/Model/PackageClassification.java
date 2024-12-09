package com.example.ezhal.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;

@Entity
@Data
@NoArgsConstructor
@Check(constraints = "target='female' or target='male' or target='general'")
public class PackageClassification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "category name should not be empty")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "category name should contain letters and spaces only")
    @Column(columnDefinition = "varchar(40) not null")
    private String name;

    @NotEmpty(message = "target should not be empty")
    @Pattern(regexp = "female|male|general",message = "target should be female or male or general")
    @Column(columnDefinition = "varchar(7) not null")
    private String target;

}
