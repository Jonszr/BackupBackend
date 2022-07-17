package ca.sait.backup.controller.html.mediator;


import ca.sait.backup.model.entity.SupportTicket;
import ca.sait.backup.model.entity.SupportTicketStatusEnum;
import ca.sait.backup.service.SupportTicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/mediator")
public class MediatorDashboardController {

    @Autowired
    SupportTicketService ticketService;

    @GetMapping("/dashboard")
    public String GetDashboard(Model model) {

        final int MAX_RECENT_TICKETS = 7;

        // Grab some open tickets
        List<SupportTicket> recentTickets = this.ticketService.mediator_GetAllTicketsWithStatus(
            SupportTicketStatusEnum.OPEN
        );

        // Check if there's not enough tickets
        if (recentTickets.size() < MAX_RECENT_TICKETS) {
            // Add stale tickets as well
            recentTickets.addAll(
                this.ticketService.mediator_GetAllTicketsWithStatus(
                    SupportTicketStatusEnum.PENDING
                )
            );
        }

        // We want to limit the number of tickets to only 7 max
        if (recentTickets.size() > MAX_RECENT_TICKETS) {
            recentTickets = recentTickets.subList(0, MAX_RECENT_TICKETS);
        }

        model.addAttribute("tickets", recentTickets);

        return("/mediator/dashboard");
    }



}
