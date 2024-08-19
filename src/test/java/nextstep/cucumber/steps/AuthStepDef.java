package nextstep.cucumber.steps;

import io.cucumber.java8.En;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.cucumber.CucumberStore;
import org.springframework.beans.factory.annotation.Autowired;

import static nextstep.auth.acceptance.AuthSteps.이메일_패스워드_로그인;

public class AuthStepDef implements En {

    @Autowired
    CucumberStore cucumberStore;

    public AuthStepDef() {
        Given("{string}, {string} 으로 이메일 패스워드 로그인을 하고", (String email, String password) -> {
            ExtractableResponse<Response> response = 이메일_패스워드_로그인(email, password);
            cucumberStore.tokenEmailMap.put(email, response.jsonPath().getString("accessToken"));
        });
    }
}
