package com.example.ezhal.Controller;

import com.example.ezhal.ApiResponse.ApiResponse;
import com.example.ezhal.Model.Ticket;
import com.example.ezhal.Service.ReviewService;
import com.example.ezhal.Service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ticket")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(ticketService.getAll());
    }

    @PostMapping("/create-ticket")
    public ResponseEntity createTicket( @RequestBody @Valid Ticket ticket, Errors errors){

        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }


        ticketService.createTicket(ticket);
        return ResponseEntity.status(200).body(new ApiResponse("ticket created successfully"));

    }

}
