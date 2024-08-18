package nextstep.subway.domain.service.farecalculator;

import lombok.RequiredArgsConstructor;
import nextstep.subway.domain.service.farecalculator.agepolicy.AgeDiscountPolicy;
import nextstep.subway.domain.service.farecalculator.distancepolicy.DistanceFarePolicy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FareCalculator {
    private final List<DistanceFarePolicy> farePolicies;
    private final List<AgeDiscountPolicy> ageDiscountPolicies;

    public long getFare(long totalDistance, long additionalBasicFare, Integer age) {
        long totalBasicFare = getTotalBasicFare(totalDistance, additionalBasicFare);

        long totalDiscountFare = getTotalDiscountFare(age, totalBasicFare);

        return totalBasicFare - totalDiscountFare;
    }

    private long getTotalBasicFare(long totalDistance, long additionalBasicFare) {
        long distanceBasicFare = farePolicies.stream()
                .mapToLong((policy) -> policy.getFare(totalDistance))
                .sum();

        return distanceBasicFare + additionalBasicFare;
    }

    private long getTotalDiscountFare(Integer age, long totalBasicFare) {
        if (age == null) {
            return 0L;
        }

        return ageDiscountPolicies.stream()
                .mapToLong((policy) -> policy.getDiscountFare(age, totalBasicFare))
                .sum();
    }
}
