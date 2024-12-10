package com.example.ezhal.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class CustomRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "User ID should not be null")
    @Positive(message = "User ID should be positive")
    private Integer userId;

    @NotNull(message = "start  Date should no be empty")
    @FutureOrPresent(message = "start date should be present or future")
    @Column(columnDefinition = "timestamp not null default current_timestamp")
    private LocalDate requestedStartDate;

    @NotNull(message = "endDate should no be empty")
    @FutureOrPresent(message = "end date should be present or future")
    @Column(columnDefinition = "timestamp not null default current_timestamp")
    private LocalDate requestedEndDate;

    @NotEmpty(message = "special Request Details should not be empty")
    @Column(columnDefinition = "varchar(1000) not null")
    private String specialRequestDetails;

    //@NotEmpty(message = "image should not be empty")
    @Column(columnDefinition ="varchar(2000) not null")
    private String imageUrl;

    @NotNull(message = "User ID should not be null")
    @Positive(message = "User ID should be positive")
    @Column(columnDefinition = "int not null")
    private Integer packageClassificationId;

    @Column(columnDefinition = "timestamp not null default current_timestamp")
    private LocalDate RequestDate;

    @Pattern(regexp = "sent|assigned|complete|cancel",message = "sent|assained|complete|cancel")
    @Column(columnDefinition = "varchar(20) default 'sent'")
    private String status;
}
