package com.example.ezhal.Repository;

import com.example.ezhal.Model.CustomRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomRequestRepository extends JpaRepository<CustomRequest, Integer> {

    List<CustomRequest> findByUserId(Integer userId);

    CustomRequest findCustomRequestById(Integer reqId);
}
