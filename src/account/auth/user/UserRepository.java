package account.auth.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>, CustomUserRepository {
    Optional<User> findByEmail(String email);
}
