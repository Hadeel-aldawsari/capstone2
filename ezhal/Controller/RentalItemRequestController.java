package com.example.ezhal.Controller;

import com.example.ezhal.ApiResponse.ApiResponse;
import com.example.ezhal.Model.RentalItemRequest;
import com.example.ezhal.Model.Category;
import com.example.ezhal.Service.RentalItemRequestService;
import com.example.ezhal.Service.CategoryService;
import com.example.ezhal.Service.RentalItemRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rental-item")
 public class RentalItemRequestController {
    private final RentalItemRequestService rentalItemRequestService;

    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(rentalItemRequestService.getAll());
    }

    @PostMapping("/create-request")
    public ResponseEntity createRequest(@RequestBody @Valid RentalItemRequest rentalItemRequest, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        rentalItemRequestService.createRequest(rentalItemRequest);
        return ResponseEntity.status(200).body(new ApiResponse("rental request created successfully,it's under review"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Integer id,@RequestBody @Valid RentalItemRequest rentalItemRequest,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        rentalItemRequestService.update(id,rentalItemRequest);
        return ResponseEntity.status(200).body(new ApiResponse("rental request updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        rentalItemRequestService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("rental request deleted successfully"));
    }


    @PutMapping("/action-on-request/provider-id/{providerId}/Rental-id/{RentalId}/status/{status}")
    public ResponseEntity actionOnRequest(@PathVariable Integer providerId,@PathVariable Integer RentalId ,@PathVariable String status){
        rentalItemRequestService.actionOnRequest(providerId, RentalId,status);
        return ResponseEntity.status(200).body("rental request status updated successfully");
    }

    @PutMapping("/cancel-request/{userId}/{requestId}")
    public ResponseEntity cancelRequest(@PathVariable Integer userId,@PathVariable Integer requestId){
        rentalItemRequestService.cancelRequest(userId,requestId);
        return ResponseEntity.status(200).body(new ApiResponse("request canceled"));
    }
}
