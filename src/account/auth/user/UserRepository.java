package account.auth.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>, CustomUserRepository {
    Optional<User> findByEmail(String email);

    Optional<User> findByRolesIsContaining(String role);

    @Query(value = "{}", sort = "{'_id': 1}")
    List<User> getAllASC();
}
