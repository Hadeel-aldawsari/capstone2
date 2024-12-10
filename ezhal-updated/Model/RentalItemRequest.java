package com.example.ezhal.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Check(constraints = "status='under review' or status='accepted' or status='rejected' or status='complete' or status='cancel'")
public class RentalItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "item id not null")
    @Positive(message = "item id should be positive number")
    @Column(columnDefinition = "int not null")
    private Integer itemId;

    @NotNull(message = "user id not null")
    @Positive(message = "user id should be positive number")
    @Column(columnDefinition = "int not null")
    private Integer userId;

    @Column(columnDefinition = "timestamp not null default current_timestamp")
    private LocalDate RequestDate;

    @NotNull(message = "start rent Date should no be empty")
    @FutureOrPresent(message = "date should be present or future")
    @Column(columnDefinition = "timestamp not null default current_timestamp")
    private LocalDate startRentDate;

    @NotNull(message = "endRentDate should no be empty")
    @FutureOrPresent(message = "date should be present or future")
    @Column(columnDefinition = "timestamp not null default current_timestamp")
    private LocalDate endRentDate;


    @Pattern(regexp = "under review|accepted|rejected|complete|cancel",message = "status under review|accepted|rejected|complete|cancel")
    @Column(columnDefinition = "varchar(12) not null")
    private String status;

}


