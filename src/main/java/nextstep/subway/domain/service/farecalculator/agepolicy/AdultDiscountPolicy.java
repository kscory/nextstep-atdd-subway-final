package nextstep.subway.domain.service.farecalculator.agepolicy;

import org.springframework.stereotype.Component;

@Component
public class AdultDiscountPolicy implements AgeDiscountPolicy {
    private static final int MAX_AGE = 6;

    @Override
    public long getDiscountFare(int age, long totalFare) {
        if (age >= MAX_AGE) {
            return 0L;
        }

        return totalFare;
    }
}
