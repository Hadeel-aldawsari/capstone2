package com.example.ezhal.Controller;


import com.example.ezhal.ApiResponse.ApiResponse;
import com.example.ezhal.Model.RentalItemRequest;
import com.example.ezhal.Model.Review;
import com.example.ezhal.Service.ReviewService;
import com.example.ezhal.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {


    private final ReviewService reviewService;

    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(reviewService.getAll());
    }

    @PostMapping("/post-review")
    public ResponseEntity add(@RequestBody @Valid Review review, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        reviewService.postReview(review);
        return ResponseEntity.status(200).body(new ApiResponse("Review posted successfully"));
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity update(@PathVariable Integer id,@RequestBody @Valid Booking booking,Errors errors){
//        if(errors.hasErrors()){
//            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
//        }
//        bookingService.update(id,booking);
//        return ResponseEntity.status(200).body(new ApiResponse("booking updated successfully"));
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        reviewService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("review deleted successfully"));
    }
}
