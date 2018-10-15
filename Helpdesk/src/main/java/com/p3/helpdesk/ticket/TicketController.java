package com.p3.helpdesk.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TicketController {

    @Autowired
    private TicketService service;

    @RequestMapping("/ticket")
    public List<Ticket> getAllTickets(){
        return service.getAllTickets();
    }

    @RequestMapping("/ticket/{id}")
    public Ticket getTicket(@PathVariable String id){
        return service.getTicket(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/ticket")
    public void addTicket(@RequestBody Ticket ticket){
        service.addTicket(ticket);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/ticket/{id}")
    public void updateTicket(@RequestBody Ticket ticket, @PathVariable String id){
        service.updateTicket(id, ticket);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/ticket/{id}")
    public void deleteTicket(@RequestBody Ticket ticket, @PathVariable String id){
        service.deleteTicket(id, ticket);
    }

}
