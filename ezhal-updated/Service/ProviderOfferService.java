package com.example.ezhal.Service;

import com.example.ezhal.ApiResponse.ApiException;
import com.example.ezhal.Model.*;
import com.example.ezhal.Repository.CustomRequestRepository;
import com.example.ezhal.Repository.OfficialProviderRepository;
import com.example.ezhal.Repository.ProviderOfferRepository;
import com.example.ezhal.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProviderOfferService {

    private final ProviderOfferRepository providerOfferRepository;
    private final OfficialProviderRepository officialProviderRepository;
    private final UserRepository userRepository;
    private final CustomRequestRepository customRequestRepository;

    //accept offer
    public void acceptOffer(Integer offerId, Integer userId) {

        ProviderOffer offer = providerOfferRepository.findProviderOfferById(offerId);
        if(offer==null)throw new ApiException("Offer not found");

        //make sure user is the own of the request
        CustomRequest customRequest = customRequestRepository.findCustomRequestById(offer.getCustomRequestId());
        if (!customRequest.getUserId().equals(userId)) {
            throw new ApiException("This custom request doesn't belong to the user");
        }
    //sent
        if (!"sent".equals(offer.getStatus())) {
            throw new ApiException("This offer has already been processed");
        }


        offer.setStatus("assigned");
        providerOfferRepository.save(offer);

        //reject rest offer
        List<ProviderOffer> otherOffers = providerOfferRepository.findByCustomRequestId(offer.getCustomRequestId());
        for (ProviderOffer otherOffer : otherOffers) {
            if (!otherOffer.getId().equals(offerId)) {//to skip accepted offer and reject the rest
                otherOffer.setStatus("rejected");
                providerOfferRepository.save(otherOffer);
            }
        }
    }



    //get offers for specific request order by low price
    public List<ProviderOffer> getOffersForCustomRequest(Integer customRequestId) {
        List<ProviderOffer> offers=providerOfferRepository.findByCustomRequestIdOrderByPriceAsc(customRequestId);
        if(offers.isEmpty())throw new ApiException("there is no offers on this request");
        return offers;
    }


//this method for official provider
    public void createOfferOnCustomRequest(ProviderOffer providerOffer) {

      CustomRequest custom= customRequestRepository.findCustomRequestById(providerOffer.getCustomRequestId());
      OfficialProvider official_provider=officialProviderRepository.findOfficialProviderById(providerOffer.getProviderId());
        User user=userRepository.findUserById(custom.getUserId());

        if (custom==null) {
            throw new ApiException("Custom request not found");
        }

        //check if the provider in the same city of user before create the offer
        if(!(official_provider.getServiceArea().contains(user.getCity())))
          throw new ApiException("sorry the user it's not in your service area");


      //default status
        providerOffer.setStatus("sent");

       //save offer
        providerOfferRepository.save(providerOffer);
    }


    //here for provider
    public void makeOfferComplete(Integer providerId,Integer offerId){

        //check the offer request is exist
        ProviderOffer offer=providerOfferRepository.findProviderOfferById(offerId);
        if(offer==null)throw new ApiException("there is no offer this id");

        //check if the provider own this offer
        if(providerId!=offer.getProviderId())
           throw new ApiException("you don't own this offer");

        //check valid status
        if(!(offer.getStatus().equalsIgnoreCase("accepted")))
            throw new ApiException("you can not make it complete until be accepted ");

        if((offer.getStatus().equalsIgnoreCase("rejected")))
            throw new ApiException("you can not make rejected offer complete ");

        //check the flow of updating status


        }

}
