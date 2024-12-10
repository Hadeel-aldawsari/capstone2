package com.example.ezhal.Controller;

import com.example.ezhal.ApiResponse.ApiResponse;
import com.example.ezhal.Model.CustomRequest;
import com.example.ezhal.Service.CustomRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/custom-request")
@AllArgsConstructor
public class CustomRequestController {

    private final CustomRequestService customRequestService;

    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(customRequestService.getAll());
    }

    @PostMapping("/create-custom-request/userid/{userId}")
    public ResponseEntity createCustomRequest(@PathVariable Integer userId, @RequestBody CustomRequest customRequest, Errors errors) {
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        customRequestService.createCustomRequest(customRequest, userId);
        return ResponseEntity.status(200).body(new ApiResponse("custom request created successfully"));
    }

    @GetMapping("/get-my-custom-request/userId/{userId}")
 public ResponseEntity getMyCustomRequest(@PathVariable Integer userId){
        return ResponseEntity.status(200).body(customRequestService.getMyCustomRequest(userId));
 }


}
