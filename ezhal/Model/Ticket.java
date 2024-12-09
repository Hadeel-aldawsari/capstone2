package com.example.ezhal.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Check(constraints = "status='open' or status='close'")
@Check(constraints = "issue='wrong item' or issue='damaged product' or issue='late delivery' or issue='other'")
@Entity
@NoArgsConstructor
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "item id  not null")
    @Positive(message = "item id should be positive number")
    @Column(columnDefinition = "int not null")
    private Integer requestId;

    @NotNull(message = "user id not null")
    @Positive(message = "user id should be positive number")
    @Column(columnDefinition = "int not null")
    private Integer userId;

    @NotEmpty(message = "description should not be empty")
    @Size(min=20,message = "description length should be 20 character or more")
    @Column(columnDefinition = "varchar(1000) not null")
    private String description;

    @NotEmpty(message = "issue should not be empty")
    @Pattern(regexp = "wrong item|damaged product|late delivery|other")
    @Column(columnDefinition = "varchar(18) not null")
    private String issue;



    @Pattern(regexp = "open|closed")
    @Column(columnDefinition = "varchar(7) not null")
    private String status;


}
