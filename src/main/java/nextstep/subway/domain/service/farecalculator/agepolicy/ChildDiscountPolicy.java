package nextstep.subway.domain.service.farecalculator.agepolicy;

import org.springframework.stereotype.Component;

@Component
public class ChildDiscountPolicy implements AgeDiscountPolicy {
    private static final int MIN_AGE = 6;
    private static final int MAX_AGE = 13;

    private static final long BASIC_DISCOUNT_FARE = 350;
    private static final double ADDITIONAL_DISCOUNT_FARE_RATE = 0.5;

    @Override
    public long getDiscountFare(int age, long totalFare) {
        if (age < MIN_AGE || age >= MAX_AGE) {
            return 0L;
        }

        if (totalFare <= BASIC_DISCOUNT_FARE) {
            return 0L;
        }

        return (long) Math.ceil((totalFare - BASIC_DISCOUNT_FARE) * ADDITIONAL_DISCOUNT_FARE_RATE);
    }
}
