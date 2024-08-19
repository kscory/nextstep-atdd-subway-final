package nextstep.subway.domain.service.farecalculator.agepolicy;

public interface AgeDiscountPolicy {
    long getDiscountFare(int age, long totalFare);
}
