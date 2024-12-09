package com.example.ezhal.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "item name should not be null")
    @Column(columnDefinition = "varchar(50) not null")
    private String packageName;

    @NotNull(message = "package classification id  not null")
    @Positive(message = "package classification id should be positive number")
    @Column(columnDefinition = "int not null")
    private Integer  packageClassificationId;

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

    @NotNull(message = "number of item should not null")
    @Positive(message = "number of item should be positive number")
    @Column(columnDefinition = "int not null")
    private Integer numberOfItem;

    @NotNull(message = "Price should not be null")
    @Positive(message = "Price should be positive")
    @Column(columnDefinition = "DECIMAL(10, 2) not null")
    private Double price;
}
