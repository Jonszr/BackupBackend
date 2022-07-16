package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.SupportTicket;
import ca.sait.backup.model.entity.SupportTicketStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketRepository extends JpaRepository<SupportTicket, Long> {
    SupportTicket findOneById(Long id);
    List<SupportTicket> findAllByComplainant(Long id);

    List<SupportTicket> findAllByStatus(SupportTicketStatusEnum status);
    
}
