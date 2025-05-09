package airalo.steps.specific;

import io.qameta.allure.Step;
import me.airalo.myportal.AiraloApi;
import me.airalo.myportal.payloads.PayloadsAiralo;
import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class ApiAuth {
    private static final String client_id = "7e29e2facf83359855f746fc490443e6";
    private static final String client_secret = "e5NNajm6jNAzrWsKoAdr41WfDiMeS1l6IcGdhmbb";
    PayloadsAiralo payloadsAiralo = new PayloadsAiralo();

    @Step
    public String getAccessToken() {
        payloadsAiralo
                .setClientId(client_id)
                .setClientSecret(client_secret);

        return AiraloApi.postAuthEndpoints(payloadsAiralo, "token")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.token_type", equalTo("Bearer"))
                .body(containsString("expires_in"))
                .body("meta.message", equalTo("success"))
                .extract().path("data.access_token");
    }
}
