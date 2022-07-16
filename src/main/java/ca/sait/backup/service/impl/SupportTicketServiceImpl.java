package ca.sait.backup.service.impl;

import ca.sait.backup.mapper.TicketMessageRepository;
import ca.sait.backup.mapper.TicketRepository;
import ca.sait.backup.model.entity.SupportTicket;
import ca.sait.backup.model.entity.SupportTicketChat;
import ca.sait.backup.model.entity.SupportTicketStatusEnum;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.request.CreateNewSupportTicketRequest;
import ca.sait.backup.model.request.CreateSupportTicketReplyRequest;
import ca.sait.backup.service.SupportTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class SupportTicketServiceImpl implements SupportTicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMessageRepository ticketChatRepository;

    @Override
    public SupportTicket createNewSupportTicket(User user, CreateNewSupportTicketRequest req) {
        SupportTicket supportTicket = new SupportTicket();
        supportTicket.setComplainant(user);
        supportTicket.setTitle(req.getTitle());
        supportTicket.setDescription(req.getTitle());
        supportTicket.setStatus(
            SupportTicketStatusEnum.OPEN
        );

        this.ticketRepository.save(supportTicket);

        return supportTicket;
    }

    @Override
    public List<SupportTicket> getSupportTicketsForUser(User user) {
        List<SupportTicket> tickets = this.ticketRepository.findAllByComplainant(
            new Long(user.getId())
        );

        return tickets;
    }

    @Override
    public SupportTicketChat addNewMessage(User sendingUser, CreateSupportTicketReplyRequest messageRequest) {

        // Get ticket from id
        SupportTicket supportTicket = this.ticketRepository.findOneById(
            messageRequest.getSupportTicketId()
        );

        // Create new support ticket chat instance
        SupportTicketChat ticketMessage = new SupportTicketChat();

        // Populate the message
        ticketMessage.setMessage(
            messageRequest.getMessage()
        );
        ticketMessage.setFrom(
            sendingUser
        );
        ticketMessage.setTicket(
            supportTicket
        );

        // Save new message
        this.ticketChatRepository.save(
            ticketMessage
        );

        // Return new message
        return ticketMessage;

    }

    @Override
    public List<SupportTicket> mediator_GetAllTicketsWithStatus(SupportTicketStatusEnum status) {

        List<SupportTicket> tickets = this.ticketRepository.findAllByStatus(
            status
        );

        return tickets;

    }

    @Override
    public SupportTicket mediator_GetTicketById(Long id) {
        SupportTicket ticket = this.ticketRepository.findOneById(
            id
        );
        return ticket;
    }

    @Override
    public SupportTicket mediator_UpdateTicket(SupportTicket ticket) {
        this.ticketRepository.save(
            ticket
        );
        return ticket;
    }

}
