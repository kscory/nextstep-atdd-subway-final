package nextstep.subway.domain.service.farecalculator.agepolicy;

import autoparams.CsvAutoSource;
import org.junit.jupiter.params.ParameterizedTest;

import static org.assertj.core.api.Assertions.assertThat;

class TeenagerDiscountPolicyTest {
    @ParameterizedTest
    @CsvAutoSource({"3", "11", "12"})
    public void sut_returns_zero_if_age_lt_13(int age, long totalFare, TeenagerDiscountPolicy sut) {
        // when
        long actual = sut.getDiscountFare(age, totalFare);

        // then
        assertThat(actual).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvAutoSource({"13, 300", "16, 200", "18, 350"})
    public void sut_returns_zero_if_total_fare_lte_350(int age, long totalFare, TeenagerDiscountPolicy sut) {
        // when
        long actual = sut.getDiscountFare(age, totalFare);

        // then
        assertThat(actual).isEqualTo(0);
    }


    @ParameterizedTest
    @CsvAutoSource({"13, 1200, 170", "15, 1000, 130", "16, 2000, 330", "18, 1500, 230"})
    public void sut_returns_discount_fare_if_age_gte_13_and_lt_19(
            int age,
            long totalFare,
            long expected,
            TeenagerDiscountPolicy sut
    ) {
        // when
        long actual = sut.getDiscountFare(age, totalFare);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvAutoSource({"19", "20", "33", "40", "60"})
    public void sut_returns_zero_if_age_gte_19(int age, long totalFare, TeenagerDiscountPolicy sut) {
        // when
        long actual = sut.getDiscountFare(age, totalFare);

        // then
        assertThat(actual).isEqualTo(0);
    }
}