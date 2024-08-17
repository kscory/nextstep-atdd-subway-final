package nextstep.subway.domain.service.farecalculator;

import org.springframework.stereotype.Component;

@Component
public class AdditionalSecondFarePolicy implements FarePolicy {
    private static final long PREV_MAX_DISTANCE = 50;

    private static final long FARE_DISTANCE_UNIT = 8;
    private static final long FARE_UNIT = 100;

    @Override
    public long getFare(long totalDistance) {
        long additionalDistance = Math.max(totalDistance - PREV_MAX_DISTANCE, 0);
        if (additionalDistance <= 0) {
            return 0;
        }

        return getTotalMultiplyUnit(additionalDistance) * FARE_UNIT;
    }

    private long getTotalMultiplyUnit(long additionalDistance) {
        long distanceUnit = Math.floorDiv(additionalDistance, FARE_DISTANCE_UNIT);
        long restUnit = Math.ceil(additionalDistance % FARE_DISTANCE_UNIT) > 0 ? 1 : 0;
        return distanceUnit + restUnit;
    }
}
