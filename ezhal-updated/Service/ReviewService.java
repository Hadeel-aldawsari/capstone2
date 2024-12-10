package com.example.ezhal.Service;

import com.example.ezhal.ApiResponse.ApiException;
import com.example.ezhal.Model.*;
import com.example.ezhal.Model.Package;
import com.example.ezhal.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RentalItemRequestRepository rentalItemRequestRepository;
    private final RentalPackageRequestRepository rentalPackageRequestRepository;

    public List<Review> getAll(){
        if(reviewRepository.findAll().isEmpty())throw new ApiException("no review found");
        return reviewRepository.findAll();
    }

    public List<Item> findTopRatedItem(){
        List<Item> topRated = reviewRepository.findTopRatedItem();
        if (topRated.isEmpty()) {
            throw new ApiException("No top-rated item found");
        }
        return topRated;
    }

    public List<Package>findTopRatedPackage(){
        List<Package> topRated = reviewRepository.findTopRatedPackage();
        if (topRated.isEmpty()) {
            throw new ApiException("No top-rated packages found");
        }
        return topRated;
    }

    public void postItemReview(Review review){
        User u=userRepository.findUserById(review.getUserId());
        if(u==null ) throw new ApiException("user id not found");
        RentalItemRequest r=rentalItemRequestRepository.findRentalItemRequestById(review.getItemId());
        if(r==null) throw new ApiException("item id not found");

        List<RentalItemRequest> rl=rentalItemRequestRepository.findRentalItemRequestByUserId(review.getUserId());

        Boolean canReview=false;
        for(RentalItemRequest request:rl){
            if(request.getItemId()==review.getItemId()){
               if( r.getStatus().equalsIgnoreCase("complete")){
                   canReview=true;
               }
            }
        }

        if(!canReview) throw new ApiException("you should have this item in your past complete request");

        if(!(r.getStatus().equalsIgnoreCase("complete")))throw new ApiException("you can't add review until the request status be complete");

        reviewRepository.save(review);
    }

    public void posPackageReview(Review review){
        User u=userRepository.findUserById(review.getUserId());
        if(u==null ) throw new ApiException("user id not found");
        RentalPackageRequest r=rentalPackageRequestRepository.findRentalPackageRequestsById(review.getItemId());
        if(r==null) throw new ApiException("item id not found");

        List<RentalPackageRequest> rl=rentalPackageRequestRepository.findRentalPackageRequestsByUserId(review.getUserId());

        Boolean canReview=false;
        for(RentalPackageRequest request:rl){
            if(request.getPackageId()==review.getItemId()){
                if( r.getStatus().equalsIgnoreCase("complete")){
                    canReview=true;
                }
            }
        }

        if(!canReview) throw new ApiException("you should have this item in your past complete request");

        if(!(r.getStatus().equalsIgnoreCase("complete")))throw new ApiException("you can't add review until the request status be complete");

        reviewRepository.save(review);
    }

    public void delete(Integer id){
        Review r=reviewRepository.findReviewById(id);
        if(r==null)throw new ApiException("no review found with this id");

        reviewRepository.delete(r);
    }



}
