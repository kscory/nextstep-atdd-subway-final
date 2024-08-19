package nextstep.subway.domain.service.farecalculator;

import autoparams.CsvAutoSource;
import nextstep.util.BaseTestSetup;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class FareCalculatorTest extends BaseTestSetup {

    @Autowired
    FareCalculator sut;

    @ParameterizedTest
    @CsvAutoSource({
            "3, 1250",
            "5, 1250",
            "10, 1250",
            "11, 1350",
            "15, 1350",
            "45, 1950",
            "46, 2050",
            "50, 2050",
            "51, 2150",
            "58, 2150",
            "59, 2250",
            "66, 2250",
            "75, 2450",
            "82, 2450"
    })
    public void sut_returns_total_fare_if_additionalBasicFare_zero(long totalDistance, long expected) {
        // when
        long actual = sut.getFare(totalDistance, 0L, null);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvAutoSource({
            "3, 400, 1650",
            "5, 900, 2150",
            "8, 300, 1550",
            "9, 300, 1550",
    })
    public void sut_returns_total_fare_if_additionalBasicFare_existed(long totalDistance, long additionalBasicFare, long expected) {
        // when
        long actual = sut.getFare(totalDistance, additionalBasicFare, null);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvAutoSource({
            "1, 6, 800",
            "3, 12, 800",
            "5, 13, 1070",
            "5, 18, 1070",
            "5, 19, 1250",
    })
    public void sut_returns_total_fare_if_age_existed(long totalDistance, int age, long expected) {
        // when
        long actual = sut.getFare(totalDistance, 0L, age);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvAutoSource({
            "11, 100, 6, 900",
            "15, 100, 13, 1230",
            "45, 100, 18, 1710",
            "46, 100, 19, 2150",
            "59, 100, 50, 2350",
    })
    public void sut_returns_total_fare_if_all_existed(long totalDistance, long additionalBasicFare, int age, long expected) {
        // when
        long actual = sut.getFare(totalDistance, additionalBasicFare, age);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}