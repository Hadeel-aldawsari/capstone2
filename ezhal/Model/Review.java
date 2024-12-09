package com.example.ezhal.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;


@Data
@NoArgsConstructor
@Entity
@Check(constraints = "rate>=1 and rate<=5")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "item id  not null")
    @Positive(message = "item id should be positive number")
    @Column(columnDefinition = "int not null")
    private Integer itemId;

    @NotNull(message = "user id not null")
    @Positive(message = "user id should be positive number")
    @Column(columnDefinition = "int not null")
    private Integer userId;

    @NotEmpty(message = "description should not be empty")
    @Size(min=20,message = "description length should be 20 character or more")
    @Column(columnDefinition = "varchar(1000) not null")
    private String description;

    @NotNull(message = "rate should not be empty")
    @Positive(message = "enter valid rate from 1 to 5 ")
    @Min(value=1,message="enter valid rate from 1 to 5 ")
    @Max(value = 5,message = "enter valid rate from 1 to 5 ")
    @Column(columnDefinition = "int not null")
    private Integer rate;
}
