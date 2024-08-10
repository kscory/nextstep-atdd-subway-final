package nextstep.cucumber;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CucumberStore {
    public Map<String, Long> stationIdMap = new HashMap<>();
    public Map<String, Long> lineIdMap = new HashMap<>();
}
