package com.example.ezhal.Service;

import com.example.ezhal.ApiResponse.ApiException;
import com.example.ezhal.Model.Item;
import com.example.ezhal.Model.Package;
import com.example.ezhal.Model.RentalItemRequest;
import com.example.ezhal.Model.RentalPackageRequest;
import com.example.ezhal.Model.User;
import com.example.ezhal.Repository.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class RentalItemRequestService {

    private final RentalItemRequestRepository rentalItemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public List<RentalItemRequest> getAll(){
        if(rentalItemRequestRepository.findAll().isEmpty())throw new ApiException("no rental Request not found");
        return rentalItemRequestRepository.findAll();
    }


    public void createRequest(RentalItemRequest rentalItemRequest){
        if(itemRepository.getProviderIdByItemId(rentalItemRequest.getItemId())== rentalItemRequest.getUserId())throw new ApiException("you can't create Rental Request for your items");
        if(itemRepository.findItemById(rentalItemRequest.getItemId())==null)
            throw new ApiException("item id not found");

        Item i=itemRepository.findItemById(rentalItemRequest.getItemId());
        User provider=userRepository.findUserById(i.getProviderId());
        User user=userRepository.findUserById(rentalItemRequest.getUserId());
        if(!(provider.getCity().equalsIgnoreCase(user.getCity())))
            throw new ApiException("User and provider not in the same city");

        //check if the user in the same city
        Item item=itemRepository.findItemById(rentalItemRequest.getItemId());
        //User provider=userRepository.findUserById(item.getProviderId());
        //User user=userRepository.findUserById(rentalItemRequest.getUserId());
        if(!(provider.getCity().equalsIgnoreCase(user.getCity())))
            throw new ApiException("User and provider not in the same city");


        if(rentalItemRequest.getEndRentDate().isBefore(rentalItemRequest.getStartRentDate()))
            throw new ApiException("enter valid end date");

        if(isItBooked(rentalItemRequest))
            throw new ApiException("this date is unavailable to book");

        rentalItemRequest.setRequestDate(LocalDate.now());
        rentalItemRequest.setStatus("under review");
        rentalItemRequestRepository.save(rentalItemRequest);
    }


    public void update(Integer id,RentalItemRequest rentalItemRequest){
        RentalItemRequest r=rentalItemRequestRepository.findRentalItemRequestById(id);
        if(r==null)throw new ApiException("no rental request found with this id");
        r.setStartRentDate(rentalItemRequest.getStartRentDate());
        r.setEndRentDate(rentalItemRequest.getEndRentDate());
        rentalItemRequestRepository.save(r);
    }


    public void delete(Integer id){
        RentalItemRequest r=rentalItemRequestRepository.findRentalItemRequestById(id);
        if(r==null)throw new ApiException("no booking found with this id");
        rentalItemRequestRepository.delete(r);
    }


    public Boolean isItBooked(RentalItemRequest rentalItemRequest){
        RentalItemRequest r= rentalItemRequestRepository.findItemIdAndDateRange(rentalItemRequest.getItemId(),rentalItemRequest.getStartRentDate(),rentalItemRequest.getEndRentDate());
        if (r==null)return false;
        return true;
    }

//here for provider
    public void actionOnRequest(Integer providerId,Integer RentalItemRequestId,String status){
        //check valid status
        if(!(status.equalsIgnoreCase("accepted")||status.equalsIgnoreCase("rejected")||status.equalsIgnoreCase("complete")))
            throw new ApiException("enter valid status");

        RentalItemRequest r=rentalItemRequestRepository.findRentalItemRequestById(RentalItemRequestId);
        //check if the booking request exist
        if(r==null)throw new ApiException("there is not rental request with this id");

        //check the flow of updating status
        if(r.getStatus().equalsIgnoreCase("under review")) {
            if (!(status.equalsIgnoreCase("accepted") || status.equalsIgnoreCase("rejected")))
                throw new ApiException("you should updated status to accepted or rejected");
        }

        //check if this provider own this item
        if(itemRepository.getProviderIdByItemId(r.getItemId())==providerId){
            r.setStatus(status);
            rentalItemRequestRepository.save(r);
        }else{
            throw new ApiException("this request doesn't relate to you");
        }
    }


    //cancellation
    public void cancelRequest(Integer userId,Integer requestId) {
        //check if request id exist
        RentalItemRequest ri = rentalItemRequestRepository.findRentalItemRequestById(requestId);
        if (ri == null) throw new ApiException("there is no request item with this id");

        //check if user own this request
        if (ri.getUserId() != userId)
            throw new ApiException("the user doesn't own this request");
        //check status
        if(!(ri.getStatus().equalsIgnoreCase("under review") || ri.getStatus().equalsIgnoreCase("accepted"))  )
            throw new ApiException("request status should be under review or accepted to cancel");

        //check the date , the cancellation allowed before start date by 2 day
        LocalDate currentDate = LocalDate.now();
        LocalDate requestStartDate = ri.getStartRentDate();

        // Calculate the difference in days between the current date and the request start date
        Period period = Period.between(currentDate, requestStartDate);


        // Check if the cancellation is allowed (before 2 days)
        if (period.getDays() >= 2) {
            // Allow cancellation
            ri.setStatus("cancelled");
            rentalItemRequestRepository.save(ri);
        } else {
            throw new ApiException("cancellation is only allowed 2 days before the start date.");

        }
    }


}
