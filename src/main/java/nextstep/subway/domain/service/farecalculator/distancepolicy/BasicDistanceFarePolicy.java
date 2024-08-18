package nextstep.subway.domain.service.farecalculator.distancepolicy;

import org.springframework.stereotype.Component;

@Component
public class BasicDistanceFarePolicy implements DistanceFarePolicy {
    private static final long BASIC_TOTAL_FARE = 1250;

    @Override
    public long getFare(long totalDistance) {
        return BASIC_TOTAL_FARE;
    }
}
