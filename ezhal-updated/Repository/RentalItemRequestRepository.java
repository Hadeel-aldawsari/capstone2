package com.example.ezhal.Repository;

import com.example.ezhal.Model.RentalItemRequest;
import com.example.ezhal.Model.Category;
import com.example.ezhal.Model.RentalItemRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalItemRequestRepository extends JpaRepository<RentalItemRequest,Integer> {
    RentalItemRequest findRentalItemRequestById(Integer id);


    @Query("select b from RentalItemRequest b where b.itemId = :itemId and ((b.startRentDate <= :endRentDate and b.endRentDate >= :startRentDate) or (b.startRentDate >= :startRentDate and b.startRentDate <= :endRentDate))")
    RentalItemRequest findItemIdAndDateRange(Integer itemId, LocalDate startRentDate, LocalDate endRentDate);

    List<RentalItemRequest> findRentalItemRequestByUserId(Integer id);


    @Query("select r from RentalItemRequest r where r.userId = :userId AND r.status = 'accepted'")
    List<RentalItemRequest> getActiveRentalItemRequestByUserId(Integer userId);

    //RentalItemRequest find



}
