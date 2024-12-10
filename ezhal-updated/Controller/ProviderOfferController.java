package com.example.ezhal.Controller;

import com.example.ezhal.ApiResponse.ApiResponse;
import com.example.ezhal.Model.ProviderOffer;
import com.example.ezhal.Repository.OfficialProviderRepository;
import com.example.ezhal.Service.ProviderOfferService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/provider-offer")
@AllArgsConstructor
public class ProviderOfferController {

    private final ProviderOfferService providerOfferService;


    //user accept offer
    @PutMapping("/accept/offer-id/{offerId}/user-id/{userId}")
    public ResponseEntity acceptOffer(@PathVariable Integer offerId, @PathVariable Integer userId) {
        providerOfferService.acceptOffer(offerId, userId);
        return ResponseEntity.status(200).body(new ApiResponse("offer accepted"));
    }


    //creat offer
    @PostMapping("/create-offer")
    public ResponseEntity<String> createOfferOnCustomRequest(@RequestBody @Valid ProviderOffer providerOffer, Errors errors) {
      if(errors.hasErrors()){
          return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
      }
        providerOfferService.createOfferOnCustomRequest(providerOffer);
        return ResponseEntity.ok("Offer created successfully.");
    }

    //user display offer in dec order depending on the price
    @GetMapping("/get-offers/by-custom/{customRequestId}")
    public ResponseEntity getOffersForCustomRequest(@PathVariable Integer customRequestId) {
        return ResponseEntity.status(200).body(providerOfferService.getOffersForCustomRequest(customRequestId));
    }


    @PutMapping("/make-offer-complete/providerid/{providerId}/offerid/{offerId}")
    public ResponseEntity makeOfferComplete(@PathVariable Integer providerId,@PathVariable Integer offerId){
       providerOfferService.makeOfferComplete(providerId,offerId);
       return ResponseEntity.status(200).body(new ApiResponse("offer updated to completed"));
    }
}
