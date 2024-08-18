package nextstep.subway.domain.service.farecalculator;

import lombok.RequiredArgsConstructor;
import nextstep.subway.domain.service.farecalculator.distancepolicy.DistanceFarePolicy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FareCalculator {
    private final List<DistanceFarePolicy> farePolicies;

    public long getFare(long totalDistance) {
        return getFare(totalDistance, 0L);
    }

    public long getFare(
            long totalDistance,
            long additionalBasicFare
    ) {
        long distanceBasicFare = farePolicies.stream()
                .mapToLong((policy) -> policy.getFare(totalDistance))
                .sum();

        return distanceBasicFare + additionalBasicFare;
    }
}
