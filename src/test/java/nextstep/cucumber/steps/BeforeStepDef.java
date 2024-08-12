package nextstep.cucumber.steps;

import io.cucumber.java8.En;
import nextstep.util.DatabaseCleaner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class BeforeStepDef implements En {
    @Autowired
    private ApplicationContext applicationContext;

    public BeforeStepDef() {
        Before(() -> {
            DatabaseCleaner.clean(applicationContext);
        });
    }
}