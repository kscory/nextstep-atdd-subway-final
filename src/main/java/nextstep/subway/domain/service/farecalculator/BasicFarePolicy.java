package nextstep.subway.domain.service.farecalculator;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class BasicFarePolicy implements FarePolicy {
    private static final long BASIC_TOTAL_FARE = 1250;

    @Override
    public long getFare(long totalDistance) {
        return BASIC_TOTAL_FARE;
    }
}
