package com.example.ezhal.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class ProviderOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Provider ID should not be null")
    @Positive(message = "Provider ID should be positive")
    private Integer providerId;

    @NotNull(message = "Custom Request ID should not be null")
    @Positive(message = "Custom Request ID should be positive")
    private Integer customRequestId;

    @NotNull(message = "Offer Price should not be null")
    @Positive(message = "Offer Price should be positive")
    @Column(columnDefinition = "DECIMAL(10, 2) not null")
    private Double price;

    @Column(columnDefinition = "varchar(1000) not null")
    private String offerDetails;

    @Column(columnDefinition = "varchar(10) default 'pending'")
    private String status; // status: pending, accepted, rejected
}
