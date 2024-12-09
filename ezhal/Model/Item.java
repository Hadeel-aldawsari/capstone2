package com.example.ezhal.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import java.awt.*;

@Data
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "item name should not be null")
    @Column(columnDefinition = "varchar(50) not null")
    private String name;

    @NotNull(message = "category_id  not null")
    @Positive(message = "category_id  should be positive number")
    @Column(columnDefinition = "int not null")
    private Integer categoryId;

    @NotNull(message = "provider id not null")
    @Positive(message = "provider id should be positive number")
    @Column(columnDefinition = "int not null")
    private Integer providerId;

    @NotEmpty(message = "description should not be empty")
    @Size(min=20,message = "description length should be 20 character or more")
    @Column(columnDefinition = "varchar(1000) not null")
    private String description;

    @NotEmpty(message = "image should not be empty")
    @Column(columnDefinition ="varchar(2000) not null")
    private String imageUrl;

    @NotNull(message = "Price should not be null")
    @Positive(message = "Price should be positive")
    @Column(columnDefinition = "DECIMAL(10, 2) not null")
    private Double price;

}

