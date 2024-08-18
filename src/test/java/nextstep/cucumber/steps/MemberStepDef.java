package nextstep.cucumber.steps;

import io.cucumber.java8.En;
import nextstep.cucumber.CucumberStore;
import nextstep.member.acceptance.MemberSteps;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberStepDef implements En {

    @Autowired
    CucumberStore cucumberStore;

    public MemberStepDef() {
        Given("email: {string}, password: {string}, age: {int} 의 회원을 생성하고", MemberSteps::회원_생성_요청);
    }
}
