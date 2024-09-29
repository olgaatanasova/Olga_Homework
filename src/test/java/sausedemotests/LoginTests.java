package sausedemotests;

import core.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LoginTests extends BaseTest {

    @ParameterizedTest
    @CsvSource({
            "standard_user, secret_sauce",
            "problem_user, secret_sauce",
            "performance_glitch_user, secret_sauce"
    })
    public void userAuthenticated_when_validCredentialsProvided(String username, String password) {
        loginPage.navigate();
        loginPage.fillLoginForm(username, password);
        inventoryPage.waitForPageTitle();
        inventoryPage.assertNavigated();
    }
}
