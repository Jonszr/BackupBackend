package ca.sait.backup.controller.html.mediator.ticket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/mediator/ticket")
public class TicketController {

    @GetMapping("/")
    public String GetTickets() {

        return "/mediator/ticket";
    }

}
