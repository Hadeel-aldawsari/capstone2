package com.example.ezhal.Service;


import com.example.ezhal.ApiResponse.ApiException;
import com.example.ezhal.Model.RentalItemRequest;
import com.example.ezhal.Model.RentalPackageRequest;
import com.example.ezhal.Model.Review;
import com.example.ezhal.Model.Ticket;
import com.example.ezhal.Repository.*;
import com.example.ezhal.Repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final RentalItemRequestRepository rentalItemRequestRepository;
    private final UserRepository userRepository;
    private final RentalPackageRequestRepository rentalPackageRequestRepository;


    public List<Ticket> getAll(){
        if(ticketRepository.findAll().isEmpty())throw new ApiException("no ticket found");
        return ticketRepository.findAll();
    }

    public void delete(Integer id){
        Ticket t=ticketRepository.findTicketById(id);
        if(t==null)throw new ApiException("no booking found with this id");
        ticketRepository.delete(t);
    }

    public void createTicket(Ticket ticket){
        if(userRepository.findUserById(ticket.getUserId())==null) throw new ApiException("user id not found");

        RentalPackageRequest r= rentalPackageRequestRepository.findRentalPackageRequestsById(ticket.getRequestId());
        if(r==null) throw new ApiException("rental request id not found");
        if(r.getUserId() == ticket.getUserId())throw new ApiException("you can not create ticket for your rental request");

        //check if the booking already has open ticket
        Ticket t= ticketRepository.findTicketByRequestId(ticket.getRequestId());
        if(t!=null){
            if(t.getStatus().equalsIgnoreCase("open")) throw new ApiException("there is already open ticket in this booking");
        }
        ticket.setStatus("open");
        ticketRepository.save(ticket);
    }


    //public


}
