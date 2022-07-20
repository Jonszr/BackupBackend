package ca.sait.backup.controller.html.user;


import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserNotificationEnum;
import ca.sait.backup.service.NotificationService;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private NotificationService notificationService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/dashboard")
    public String GetDashboard(Model model, HttpServletRequest request) {

        // Expose session variables
        this.sessionService.exposeEssentialVariables(request, model);

        // Temporary just for testing
        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );

        return ("/user/dashboard");

    }

}