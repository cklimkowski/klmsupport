package klm.pstryk.site.repositories;

import klm.pstryk.site.entities.TicketComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TicketCommentRepository
        extends CrudRepository<TicketComment, Long>
{
    Page<TicketComment> getByTicketId(long ticketId, Pageable p);
}
