package ca.sait.backup.controller.html.user;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
class DashboardController {

    @GetMapping("/dashboard")
    public String GetDashboard() {
        return ("/user/dashboard");
    }

}