package ca.sait.backup.controller.html.user;


import ca.sait.backup.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
class UserDashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String GetDashboard() {


        return ("/user/dashboard");

    }

}