package com.example.ezhal.Service;


import com.example.ezhal.ApiResponse.ApiException;
import com.example.ezhal.Model.RentalItemRequest;
import com.example.ezhal.Model.RentalPackageRequest;
import com.example.ezhal.Model.User;
import com.example.ezhal.Repository.RentalItemRequestRepository;
import com.example.ezhal.Repository.RentalPackageRequestRepository;
import com.example.ezhal.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RentalPackageRequestRepository rentalPackageRequestRepository;
    private final RentalItemRequestRepository rentalItemRequestRepository;

    public List<User> getAll(){
        if(userRepository.findAll().isEmpty())throw new ApiException("no user found");
        return userRepository.findAll();
    }

    public void add(User user){
        user.setRegistrationDate(LocalDate.now());
        user.setUserStatus("active");
        userRepository.save(user);
    }

    public void update(Integer id,User user){
        User u=userRepository.findUserById(id);
        if(u==null)throw new ApiException("no user found with this id");

        u.setEmail(user.getEmail());
        u.setUsername(user.getUsername());
        u.setPassword(u.getPassword());
        userRepository.save(u);

    }


    public void delete(Integer id){
        User u=userRepository.findUserById(id);
        if(u==null)throw new ApiException("no user found with this id");

        userRepository.delete(u);
    }


    public void suspendAccount(Integer userId){
        //check active user request
     List<RentalPackageRequest>rp=rentalPackageRequestRepository.getActiveRentalPackageRequestByUserId(userId);
     List<RentalItemRequest>ri=rentalItemRequestRepository.getActiveRentalItemRequestByUserId(userId);
        if(!(rp.isEmpty()) ||!(ri.isEmpty()) )throw new ApiException("you can't suspend your account you have active request");

        User u=userRepository.findUserById(userId);
       u.setUserStatus("suspend");
       userRepository.save(u);
    }







    public List<User> getUserRegisteredToday(){
        List<User>users=userRepository.findUserByRegistrationDate(LocalDate.now());
        if(users.isEmpty())throw new ApiException("no new user registered today");
        return users;
    }





}
