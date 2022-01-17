package account.auth.user;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final MongoTemplate mongoTemplate;

    public CustomUserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean findUserByEmailAndUpdatePassword(String email, String newPassword) {
        Query query = new Query().addCriteria(where("email").is(email));
        Update update = new Update();
        update.set("password", newPassword);
        return mongoTemplate.update(User.class)
                .matching(query)
                .apply(update)
                .first()
                .getMatchedCount() == 1;
    }

}
