package com.p3.helpdesk.ticket;

import com.p3.helpdesk.ticket.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class TicketService {

    private  List<Ticket> tickets = new ArrayList<>(Arrays.asList(
            new Ticket("t12", "Issue", "not getting logged in"),
            new Ticket("t23", "new feature", "need to add this feature")
    ));

    public List<Ticket> getAllTickets() {
        return tickets;
     }

    public Ticket getTicket(String id) {
        for(Ticket t : tickets){
            if(t.getId().equals(id)){
                return t;
            }
        }
        return null;
    }

    public void addTicket(Ticket t){
        tickets.add(t);
    }

    public void updateTicket(String id, Ticket ticket) {
        for(int i=0; i<tickets.size(); i++){
            Ticket t = tickets.get(i);
            if(t.getId().equals(id)){
                tickets.set(i, ticket);
            }
        }
    }

    public void deleteTicket(String id, Ticket ticket) {
        for(Ticket t : tickets){
            if(t.getId().equals(id)){
                tickets.remove(t);
            }
        }
    }
}
