package ca.sait.backup.controller.html.admin;


import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.SupportTicket;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserRole;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.service.SupportTicketService;
import ca.sait.backup.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SupportTicketService supportTicketService;

    @GetMapping("/")
    public String GetDashboard(Model model, HttpServletRequest request) {

        this.sessionService.exposeEssentialVariables(
            request, model
        );


        ArrayList<User> allUsers = new ArrayList<User>();

        for (UserRole role: UserRole.values()) {
            List<User> users = this.userService.dev_GetUsersByRole(
                role
            );
            String attrName = role.toString().toLowerCase() + "_list";
            allUsers.addAll(users);
            model.addAttribute(attrName, users);
        }

        model.addAttribute("allUsers_list", allUsers);

        return ("/admin/admin_dashboard");
    }

    @GetMapping("/edit/{userId}")
    public String EditUser(@PathVariable("userId") Long userId, Model model, HttpServletRequest request) {

        List<SupportTicket> ticketList = this.supportTicketService.getSupportTicketsForUser(
            new User(userId)
        );

        model.addAttribute("userTickets", ticketList);

        return ("/admin/edit_user");
    }

    @GetMapping("/fragment")
    public String fragment() {
        return ("/fragment/top-user-bar");
    }


}
