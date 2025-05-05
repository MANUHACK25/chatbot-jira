package com.lodgingApplication.web.controller;

import com.lodgingApplication.domain.services.TicketService;
import com.lodgingApplication.dto.TiccketDTO;
import com.lodgingApplication.persistence.entity.Ticket;
import com.lodgingApplication.persistence.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/allTickets")
    public ResponseEntity<List<Ticket>> getAllTicket(){
        return new ResponseEntity<>(ticketService.getAll(), HttpStatus.OK);
    }

    /**
     * agregar el otro service que es de Ticket ByTitle Ticket, por el momento probar
     * para eso el otro ponerlo con Optional
     * */
    @GetMapping("/title/{titleTicket}")
    public ResponseEntity<List<Ticket>> getByTicketTitle(@PathVariable("titleTicket") String title){
        return ticketService.getTicketByTicketTitle(title)
                .map(tickets -> new ResponseEntity<>(tickets, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/userTicket/{userId}")
    public ResponseEntity<List<Usuario>> getByTicketTitle(@PathVariable("userId") Integer ticketId){
        return new ResponseEntity<>(ticketService.getUserTicketbyTicketId(ticketId), HttpStatus.OK);
    }

    @GetMapping("/similarTicket/{prompt}")
    public ResponseEntity<List<Ticket>> getSimilarTicket(@PathVariable("prompt") String prompt){
        return new ResponseEntity<>(ticketService.getMostSimilarTicket(prompt), HttpStatus.OK);
    }


    @GetMapping("/similarTicketUserDTO/{prompt}")
    public ResponseEntity<List<TiccketDTO>> getSimilarTicketUserObject(@PathVariable("prompt") String prompt){
        return new ResponseEntity<>(ticketService.getMOstSimilarObejectTicket2(prompt), HttpStatus.OK);
    }










}
