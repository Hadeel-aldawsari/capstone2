package com.example.ezhal.Service;

import com.example.ezhal.ApiResponse.ApiException;
import com.example.ezhal.Model.Category;
import com.example.ezhal.Model.CustomRequest;
import com.example.ezhal.Model.ProviderOffer;
import com.example.ezhal.Repository.CustomRequestRepository;
import com.example.ezhal.Repository.ProviderOfferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomRequestService {

    private final CustomRequestRepository customRequestRepository;
    private final ProviderOfferRepository providerOfferRepository;

    public List<CustomRequest> getAll(){
        if(customRequestRepository.findAll().isEmpty())throw new ApiException("no custom request found found");
        return customRequestRepository.findAll();
    }

    public void createCustomRequest(CustomRequest customRequest, Integer userId) {
        customRequest.setUserId(userId);
        if(customRequest.getRequestedEndDate().isBefore(customRequest.getRequestedStartDate()))
            throw new ApiException("enter valid end date");

        customRequest.setStatus("sent");
        customRequest.setRequestDate(LocalDate.now());
        customRequestRepository.save(customRequest);
    }

   //get all offer for this request
    public List<ProviderOffer> getOffersForRequest(Integer customRequestId) {
        List<ProviderOffer> offers = providerOfferRepository.findByCustomRequestId(customRequestId);
        if (offers.isEmpty()) {
            throw new ApiException("No offers found for this custom request");
        }
        return offers;
    }




}
