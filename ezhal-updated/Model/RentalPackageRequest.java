package com.example.ezhal.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Check(constraints = "status='under review' or status='accepted' or status='rejected' or status='complete' or status='cancel'")
public class RentalPackageRequest {

        @Id
        @GeneratedValue(strategy =
                GenerationType.IDENTITY)
        private Integer id;

        @NotNull(message = "package id should not be null")
        @Positive(message = "package id should be a positive number")
        private Integer packageId;

        @NotNull(message = "user id should not be null")
        @Positive(message = "user id should be a positive number")
        private Integer userId;

      @Column(columnDefinition = "timestamp not null default current_timestamp")
       private LocalDate RequestDate;

        @NotNull(message = "start rent date should not be null")
        @FutureOrPresent(message = "start rent date should be present or future")
        private LocalDate startRentDate;

        @NotNull(message = "end rent date should not be null")
        @FutureOrPresent(message = "end rent date should be present or future")
        private LocalDate endRentDate;

        @Pattern(regexp = "under review|accepted|rejected|complete|cancel", message = "status should be under review, accepted, rejected, or complete")
        @Column(columnDefinition = "varchar(18) not null")
        private String status;



}
