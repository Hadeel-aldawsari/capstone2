package com.example.ezhal.Repository;

import com.example.ezhal.Model.Review;
import com.example.ezhal.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
   Ticket findTicketById(Integer id);
   Ticket findTicketByRequestId(Integer id);

}
