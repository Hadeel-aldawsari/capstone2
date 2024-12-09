package com.example.ezhal.Controller;

import com.example.ezhal.ApiResponse.ApiResponse;
import com.example.ezhal.Model.RentalItemRequest;
import com.example.ezhal.Model.RentalPackageRequest;
import com.example.ezhal.Repository.RentalPackageRequestRepository;
import com.example.ezhal.Service.RentalItemRequestService;
import com.example.ezhal.Service.RentalPackageRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rental-package")
public class RentalPackageRequestController {
    private final RentalPackageRequestService rentalPackageRequestService;

    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(rentalPackageRequestService.getAll());
    }

    @PostMapping("/create-request")
    public ResponseEntity createPackageRequest(@RequestBody @Valid RentalPackageRequest  rentalPackageRequest, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        rentalPackageRequestService.createPackageRequest(rentalPackageRequest);
        return ResponseEntity.status(200).body(new ApiResponse("rental request created successfully,it's under review"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updatePackageRequest(@PathVariable Integer id,@RequestBody @Valid RentalPackageRequest rentalPackageRequest,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        rentalPackageRequestService.updatePackageRequest(id,rentalPackageRequest);
        return ResponseEntity.status(200).body(new ApiResponse("rental request updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        rentalPackageRequestService.deletePackageRequest(id);
        return ResponseEntity.status(200).body(new ApiResponse("rental request deleted successfully"));
    }


    //here to do action
    @PutMapping("/action-on-request/provider-id/{providerId}/Rental-id/{RentalId}/status/{status}")
    public ResponseEntity actionOnRequest(@PathVariable Integer providerId,@PathVariable Integer RentalId ,@PathVariable String status){
        rentalPackageRequestService.actionOnRequest(providerId, RentalId,status);
        return ResponseEntity.status(200).body("rental request status updated successfully");
    }

    @PutMapping("/cancel-request/{userId}/{requestId}")
    public ResponseEntity cancelRequest(@PathVariable Integer userId,@PathVariable Integer requestId){
        rentalPackageRequestService.cancelRequest(userId,requestId);
        return ResponseEntity.status(200).body(new ApiResponse("request canceled"));
    }
}
