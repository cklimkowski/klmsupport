package klm.pstryk.site;

import klm.pstryk.site.entities.Attachment;
import klm.pstryk.site.entities.Ticket;
import klm.pstryk.site.entities.TicketComment;
import klm.pstryk.site.repositories.AttachmentRepository;
import klm.pstryk.site.repositories.SearchResult;
import klm.pstryk.site.repositories.TicketCommentRepository;
import klm.pstryk.site.repositories.TicketRepository;
import klm.pstryk.site.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultTicketService implements TicketService
{
    @Inject TicketRepository ticketRepository;
    @Inject TicketCommentRepository commentRepository;
    @Inject AttachmentRepository attachmentRepository;
    @Inject UserRepository userRepository;

    @Override
    @Transactional
    public List<Ticket> getAllTickets()
    {
        Iterable<Ticket> i = this.ticketRepository.findAll();
        List<Ticket> l = new ArrayList<>();
        i.forEach(l::add);
        return l;
    }

    @Override
    @Transactional
    public Page<SearchResult<Ticket>> search(String query, boolean useBooleanMode,
                                             Pageable pageable)
    {
        return this.ticketRepository.search(query, useBooleanMode, pageable);
    }

    @Override
    @Transactional
    public Ticket getTicket(long id)
    {
        Ticket ticket = this.ticketRepository.findOne(id);
        ticket.getNumberOfAttachments();
        return ticket;
    }

    @Override
    @Transactional
    public void save(Ticket ticket)
    {
        if(ticket.getId() < 1)
            ticket.setDateCreated(Instant.now());

        this.ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void deleteTicket(long id)
    {
        this.ticketRepository.delete(id);
    }

    @Override
    @Transactional
    public Page<TicketComment> getComments(long ticketId, Pageable pageable)
    {
        return this.commentRepository.getByTicketId(ticketId, pageable);
    }

    @Override
    @Transactional
    public void save(TicketComment comment, long ticketId)
    {
        if(comment.getId() < 1)
        {
            comment.setTicketId(ticketId);
            comment.setDateCreated(Instant.now());
        }

        this.commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(long id)
    {
        this.commentRepository.delete(id);
    }

    @Override
    @Transactional
    public Attachment getAttachment(long id)
    {
        Attachment attachment = this.attachmentRepository.findOne(id);
        if(attachment != null)
            attachment.getContents();
        return attachment;
    }
}
