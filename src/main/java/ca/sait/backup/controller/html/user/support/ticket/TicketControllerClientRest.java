package ca.sait.backup.controller.html.mediator.ticket;

import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.SupportTicket;
import ca.sait.backup.model.entity.SupportTicketChat;
import ca.sait.backup.model.entity.SupportTicketStatusEnum;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.request.CreateNewSupportTicketRequest;
import ca.sait.backup.model.request.CreateSupportTicketReplyRequest;
import ca.sait.backup.model.request.GetAllSupportTicketsForStatusRequest;
import ca.sait.backup.model.request.ModifyTicketRequest;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.service.SupportTicketService;
import ca.sait.backup.service.UserService;
import ca.sait.backup.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/v1/pri/user/ticket")
public class TicketControllerClientRest {

    @Autowired
    private UserService userService;

    @Autowired
    private SupportTicketService ticketService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/create")
    public JsonData createNewTicket(@RequestBody CreateNewSupportTicketRequest createRequest, HttpServletRequest request) {

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );

        User user = this.userService.dev_GetUserById(
            sessionContainer.getUserId()
        );

        SupportTicket supportTicket = this.ticketService.createNewSupportTicket(
            user,
            createRequest
        );

        System.out.println("New support ticket created: " + supportTicket.getTitle());

        return JsonData.buildSuccess("");
    }


    @PostMapping("/status")
    public JsonData changeTicketStatus(@RequestBody ModifyTicketRequest modReq) {
        SupportTicket ticket = this.ticketService.mediator_GetTicketById(
                modReq.getTicketId()
        );

        ticket.setStatus(
            SupportTicketStatusEnum.valueOf(
                    modReq.getNewStatus().toUpperCase()
            )
        );

        this.ticketService.mediator_UpdateTicket(
                ticket
        );

        return JsonData.buildSuccess("");
    }

    @PostMapping("/createMessage")
    public JsonData changeTicketStatus(@RequestBody CreateSupportTicketReplyRequest msgReq, HttpServletRequest request) {

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );

        User user = this.userService.dev_GetUserById(
            sessionContainer.getUserId()
        );

        SupportTicket ticket = this.ticketService.mediator_GetTicketById(
            msgReq.getSupportTicketId()
        );

        SupportTicketChat message = this.ticketService.addNewMessage(
            user,
            msgReq
        );

        return JsonData.buildSuccess("");

    }


}
