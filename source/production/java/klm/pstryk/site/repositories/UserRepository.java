package klm.pstryk.site.repositories;

import klm.pstryk.site.entities.UserPrincipal;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserPrincipal, Long>
{
    UserPrincipal getByUsername(String username);
}
