package com.example.ezhal.Repository;


import com.example.ezhal.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findUserById(Integer id);

    //5 End point get user registered by today, I will send today date
    List<User> findUserByRegistrationDate(LocalDate date);






}
