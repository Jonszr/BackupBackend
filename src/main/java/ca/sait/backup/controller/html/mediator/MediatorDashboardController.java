package ca.sait.backup.controller.html.mediator;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/mediator")
public class MediatorDashboardController {

    @GetMapping("/dashboard")
    public String GetDashboard() {
        System.out.println("Mediator dashboard hit!");
        return("/mediator/dashboard");
    }



}
