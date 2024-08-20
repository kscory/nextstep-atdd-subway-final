package nextstep.subway.domain.service.farecalculator.agepolicy;

import autoparams.CsvAutoSource;
import org.junit.jupiter.params.ParameterizedTest;

import static org.assertj.core.api.Assertions.assertThat;

class AdultDiscountPolicyTest {
    @ParameterizedTest
    @CsvAutoSource({"6", "7", "10", "30"})
    public void sut_returns_zero_if_age_gte_6(int age, long totalFare, AdultDiscountPolicy sut) {
        // when
        long actual = sut.getDiscountFare(age, totalFare);

        // then
        assertThat(actual).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvAutoSource({"1", "2", "4", "5"})
    public void sut_returns_totalFare_if_age_lt_6(int age, long totalFare, AdultDiscountPolicy sut) {
        // when
        long actual = sut.getDiscountFare(age, totalFare);

        // then
        assertThat(actual).isEqualTo(totalFare);
    }
}