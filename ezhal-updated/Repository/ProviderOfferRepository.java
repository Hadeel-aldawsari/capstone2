package com.example.ezhal.Repository;

import com.example.ezhal.Model.ProviderOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderOfferRepository extends JpaRepository<ProviderOffer, Integer> {

    List<ProviderOffer> findByCustomRequestId(Integer customRequestId);

    List<ProviderOffer> findByCustomRequestIdOrderByPriceAsc(Integer customRequestId);


    //List<ProviderOffer> findByCustomRequestIdAndStatus(Integer customRequestId, String status);

    ProviderOffer findProviderOfferById(Integer id);
}
