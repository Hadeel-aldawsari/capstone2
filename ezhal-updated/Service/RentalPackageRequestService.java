package com.example.ezhal.Service;

import com.example.ezhal.ApiResponse.ApiException;
import com.example.ezhal.Model.*;
import com.example.ezhal.Model.Package;
import com.example.ezhal.Repository.OfficialProviderRepository;
import com.example.ezhal.Repository.PackageRepository;
import com.example.ezhal.Repository.RentalPackageRequestRepository;
import com.example.ezhal.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@AllArgsConstructor
public class RentalPackageRequestService {

    private final RentalPackageRequestRepository rentalPackageRequestRepository;
    private final PackageRepository packageRepository;
    private final OfficialProviderRepository officialProviderRepository;
    private final UserRepository userRepository;


    public List<RentalPackageRequest> getAll() {
        List<RentalPackageRequest> requests = rentalPackageRequestRepository.findAll();
        if (requests.isEmpty()) throw new ApiException("No rental package requests found");
        return requests;
    }


    public void createPackageRequest(RentalPackageRequest rentalPackageRequest) {
        if (packageRepository.findPackageById(rentalPackageRequest.getPackageId()) == null)
            throw new ApiException("Package ID not found");


        //check if the user in the same city
        Package p=packageRepository.findPackageById(rentalPackageRequest.getPackageId());
        User provider=userRepository.findUserById(p.getProviderId());
        User user=userRepository.findUserById(rentalPackageRequest.getUserId());
        if(!(provider.getCity().equalsIgnoreCase(user.getCity())))
            throw new ApiException("User and provider not in the same city");

//        Integer providerId = packageRepository.getProviderIdByPackageId(rentalPackageRequest.getPackageId());
//        if (providerId.equals(rentalPackageRequest.getUserId())) {
//            throw new ApiException("You can't create rental request for your own package");
//        }

        if (userRepository.findUserById(rentalPackageRequest.getUserId()) == null)
            throw new ApiException("there is no user with this id");

        if (rentalPackageRequest.getEndRentDate().isBefore(rentalPackageRequest.getStartRentDate())) {
            throw new ApiException("Invalid end date");
        }


//        if (isPackageBooked(rentalPackageRequest)) {
//            throw new ApiException("This date is unavailable for booking");
//        }

        rentalPackageRequest.setRequestDate(LocalDate.now());
        rentalPackageRequest.setStatus("under review");
        rentalPackageRequestRepository.save(rentalPackageRequest);
    }


    public void updatePackageRequest(Integer id, RentalPackageRequest rentalPackageRequest) {
        RentalPackageRequest existingRequest = rentalPackageRequestRepository.findRentalPackageRequestsById(rentalPackageRequest.getPackageId());
        if (existingRequest == null)
            throw new ApiException("No rental package request found with this ID");

        existingRequest.setStartRentDate(rentalPackageRequest.getStartRentDate());
        existingRequest.setEndRentDate(rentalPackageRequest.getEndRentDate());
        rentalPackageRequestRepository.save(existingRequest);
    }


    public void deletePackageRequest(Integer id) {
        RentalPackageRequest rentalPackageRequest = rentalPackageRequestRepository.findRentalPackageRequestsById(id);
        if(rentalPackageRequest==null)
              throw new ApiException("No rental package request found with this ID");
        rentalPackageRequestRepository.delete(rentalPackageRequest);
    }


    public Boolean isPackageBooked(RentalPackageRequest rentalPackageRequest) {
        RentalPackageRequest existingRequest = rentalPackageRequestRepository.findPackageIdAndDateRange(rentalPackageRequest.getPackageId(), rentalPackageRequest.getStartRentDate(), rentalPackageRequest.getEndRentDate());
        return existingRequest != null;
    }

    // تحديث حالة الطلب
    public void actionOnRequest(Integer providerId, Integer rentalPackageRequestId, String status) {

        if (!(status.equalsIgnoreCase("accepted") || status.equalsIgnoreCase("rejected") || status.equalsIgnoreCase("complete"))) {
            throw new ApiException("Enter valid status");
        }

        RentalPackageRequest rentalPackageRequest = rentalPackageRequestRepository.findRentalPackageRequestsById(rentalPackageRequestId);
        if (rentalPackageRequest == null) throw new ApiException("No rental package request found with this ID");


        if (rentalPackageRequest.getStatus().equalsIgnoreCase("under review")) {
            if (!(status.equalsIgnoreCase("accepted") || status.equalsIgnoreCase("rejected"))) {
                throw new ApiException("Status should be updated to accepted or rejected");
            }
        }

        //check if the provider own the package
        if (packageRepository.getProviderIdByPackageId(rentalPackageRequest.getPackageId()) == providerId) {
            rentalPackageRequest.setStatus(status);
            rentalPackageRequestRepository.save(rentalPackageRequest);
        } else {
            throw new ApiException("This rental package request does not belong to you");
        }
    }


    //cancellation
    public void cancelRequest(Integer userId,Integer requestId) {
        //check if request id exist
        RentalPackageRequest ri = rentalPackageRequestRepository.findRentalPackageRequestsById(requestId);
        if (ri == null) throw new ApiException("there is no request item with this id");

        //check if user own this request
        if (ri.getUserId() != userId)
            throw new ApiException("the user doesn't own this request");

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
            rentalPackageRequestRepository.save(ri);
        } else {
            throw new ApiException("cancellation is only allowed 2 days before the start date.");

        }
    }

}
