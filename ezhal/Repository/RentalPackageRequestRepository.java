package com.example.ezhal.Repository;

import com.example.ezhal.Model.RentalItemRequest;
import com.example.ezhal.Model.RentalPackageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalPackageRequestRepository extends JpaRepository<RentalPackageRequest,Integer> {
    RentalPackageRequest findRentalPackageRequestsById(Integer id);

    @Query("select p from RentalPackageRequest p where p.packageId = :packageId and ((p.startRentDate <= :endRentDate and p.endRentDate >= :startRentDate) or (p.startRentDate >= :startRentDate and p.startRentDate <= :endRentDate))")
    RentalPackageRequest findPackageIdAndDateRange(Integer packageId, LocalDate startRentDate, LocalDate endRentDate);

    @Query("select r from RentalItemRequest r where r.userId = :userId AND r.status = 'accepted'")
    List<RentalPackageRequest> getActiveRentalPackageRequestByUserId(Integer userId);


}
