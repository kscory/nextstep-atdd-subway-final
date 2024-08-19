package nextstep.subway.domain.service.farecalculator.agepolicy;

import autoparams.CsvAutoSource;
import org.junit.jupiter.params.ParameterizedTest;

import static org.assertj.core.api.Assertions.assertThat;

class ChildDiscountPolicyTest {
    @ParameterizedTest
    @CsvAutoSource({"3", "4", "5"})
    public void sut_returns_zero_if_age_lt_6(int age, long totalFare, ChildDiscountPolicy sut) {
        // when
        long actual = sut.getDiscountFare(age, totalFare);

        // then
        assertThat(actual).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvAutoSource({"6, 300", "8, 200", "12, 350"})
    public void sut_returns_zero_if_total_fare_lte_350(int age, long totalFare, ChildDiscountPolicy sut) {
        // when
        long actual = sut.getDiscountFare(age, totalFare);

        // then
        assertThat(actual).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvAutoSource({"6, 1200, 425", "7, 1000, 325", "9, 2000, 825", "12, 1500, 575"})
    public void sut_returns_discount_fare_if_age_gte_6_and_lt_13(
            int age,
            long totalFare,
            long expected,
            ChildDiscountPolicy sut
    ) {
        // when
        long actual = sut.getDiscountFare(age, totalFare);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvAutoSource({"13", "15", "20", "40", "60"})
    public void sut_returns_zero_if_age_gte_13(int age, long totalFare, ChildDiscountPolicy sut) {
        // when
        long actual = sut.getDiscountFare(age, totalFare);

        // then
        assertThat(actual).isEqualTo(0);
    }
}