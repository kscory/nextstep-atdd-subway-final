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
    public void sut_returns_total_fare(long totalDistance, long expected) {
        // when
        long actual = sut.getFare(totalDistance);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}