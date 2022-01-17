package account.auth.user;

public interface CustomUserRepository {
    boolean findUserByEmailAndUpdatePassword(String email, String newPassword);
}
