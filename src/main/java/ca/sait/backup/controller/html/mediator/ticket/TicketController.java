package ca.sait.backup.controller.html.mediator.ticket;

import ca.sait.backup.model.entity.SupportTicket;
import ca.sait.backup.model.entity.SupportTicketStatusEnum;
import ca.sait.backup.service.SupportTicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/mediator/ticket")
public class TicketController {

    @Autowired
    SupportTicketService ticketService;

    @GetMapping("/")
    public String GetTickets(Model model) throws Exception {

        List<SupportTicket> openTickets = this.ticketService.mediator_GetAllTicketsWithStatus(
            SupportTicketStatusEnum.OPEN
        );

        List<SupportTicket> staleTickets = this.ticketService.mediator_GetAllTicketsWithStatus(
            SupportTicketStatusEnum.PENDING
        );

        List<SupportTicket> closedTickets = this.ticketService.mediator_GetAllTicketsWithStatus(
            SupportTicketStatusEnum.CLOSED
        );

        List<SupportTicket> allTickets = new ArrayList<SupportTicket>();

        allTickets.addAll(openTickets);
        allTickets.addAll(staleTickets);
        allTickets.addAll(closedTickets);

        ArrayList<SupportTicket> firstPage = new ArrayList<SupportTicket>();

        for (int x = 0; x < allTickets.size(); x++) {

            SupportTicket ticket = (SupportTicket)allTickets.get(x);

            firstPage.add(
                ticket
            );

        }

        model.addAttribute("tickets", firstPage);

        return "/mediator/ticket";
    }

    @GetMapping("/{ticketId}")
    public String GetSpecificTicket(@PathVariable("ticketId") Long ticketId, Model model) {

        SupportTicket ticket = this.ticketService.mediator_GetTicketById(
            ticketId
        );

        List<SupportTicket> userTickets = this.ticketService.getSupportTicketsForUser(
            ticket.getComplainant()
        );

        model.addAttribute("ticket", ticket);
        model.addAttribute("userTickets", userTickets);

        //ticket.getChat().get(0).getFrom().getName().substring

        return "/mediator/specific_ticket";
    }



}
