package klm.pstryk.site.repositories;

import klm.pstryk.site.entities.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long>,
        SearchableRepository<Ticket>
{

}
