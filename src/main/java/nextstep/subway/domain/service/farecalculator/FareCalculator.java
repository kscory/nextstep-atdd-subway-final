package nextstep.subway.domain.service.farecalculator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FareCalculator {
    private final List<FarePolicy> farePolicies;

    public long getFare(long totalDistance) {
        return farePolicies.stream()
                .mapToLong((policy) -> policy.getFare(totalDistance))
                .sum();
    }
}
