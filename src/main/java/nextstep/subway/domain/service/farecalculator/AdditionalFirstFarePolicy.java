package nextstep.subway.domain.service.farecalculator;

import org.springframework.stereotype.Component;

@Component
public class AdditionalFirstFarePolicy implements FarePolicy {
    private static final long PREV_MAX_DISTANCE = 10;

    private static final long MAX_DISTANCE_DURATION = 40;
    private static final long FARE_DISTANCE_UNIT = 5;
    private static final long FARE_UNIT = 100;

    @Override
    public long getFare(long totalDistance) {
        long additionalDistance = extractAdditionalDistance(totalDistance);
        if (additionalDistance <= 0) {
            return 0;
        }

        return getTotalMultiplyUnit(additionalDistance) * FARE_UNIT;
    }

    private long extractAdditionalDistance(long totalDistance) {
        long restDistance = Math.min(totalDistance - PREV_MAX_DISTANCE, MAX_DISTANCE_DURATION);
        return Math.max(restDistance, 0);
    }

    private long getTotalMultiplyUnit(long additionalDistance) {
        long distanceUnit = Math.floorDiv(additionalDistance, FARE_DISTANCE_UNIT);
        long restUnit = Math.ceil(additionalDistance % FARE_DISTANCE_UNIT) > 0 ? 1 : 0;
        return distanceUnit + restUnit;
    }
}
