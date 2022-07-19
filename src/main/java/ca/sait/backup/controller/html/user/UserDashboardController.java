package ca.sait.backup.controller.html.user;


import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
class UserDashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/dashboard")
    public String GetDashboard(HttpServletRequest request) {

        // Grab session container
        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );

        System.out.println(sessionContainer.getEmail());

        return ("/user/dashboard");

    }

}