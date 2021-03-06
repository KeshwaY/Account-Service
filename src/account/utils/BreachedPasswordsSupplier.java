package account.utils;

import java.util.List;

public class BreachedPasswordsSupplier {

    public static List<String> getBreachedPasswords() {
        return List.of(
                "PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
                "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
                "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember"
        );
    }

}
