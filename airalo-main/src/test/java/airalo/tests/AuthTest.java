package airalo.tests;

import lombok.extern.slf4j.Slf4j;
import airalo.steps.specific.ApiAuth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class AuthTest {
    private static String accessToken;
    private static final ApiAuth API_AUTH = new ApiAuth();

    @Test
    @DisplayName("Use login endpoint to return Access token")
    void userLogsInAndGetsAccessToken() {
        log.info("1.Use login endpoint to return Access token - 200 OK");
        accessToken = API_AUTH.getAccessToken();
        log.info("Test OK");
    }
}
