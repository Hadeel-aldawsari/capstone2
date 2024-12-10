package com.example.ezhal.Repository;

import com.example.ezhal.Model.Item;
import com.example.ezhal.Model.OfficialProvider;
import com.example.ezhal.Model.Package;
import com.example.ezhal.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    Review findReviewById(Integer id);
    //List<Review> findByItemIdOrderByRateDesc(Integer itemId);



    @Query("select i from Item i " +
            "join Review r on i.id = r.itemId " +
            "GROUP BY i.id " +
            "ORDER BY AVG(r.rate) DESC")
    List<Item> findTopRatedItem();


    @Query("select p from Package p " +
            "join Review r on p.id = r.itemId " +
            "GROUP BY p.id " +
            "ORDER BY AVG(r.rate) DESC")
    List<Package> findTopRatedPackage();

}
