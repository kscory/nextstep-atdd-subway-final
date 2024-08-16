package nextstep.subway.domain.service.farecalculator;

import autoparams.CsvAutoSource;
import org.junit.jupiter.params.ParameterizedTest;

import static org.assertj.core.api.Assertions.assertThat;

class AdditionalSecondFarePolicyTest {
    @ParameterizedTest
    @CsvAutoSource({"3", "5", "10", "13", "35", "38", "43", "50"})
    public void sut_returns_zero_fare_if_distance_lte_50(
            long totalDistance,
            AdditionalSecondFarePolicy sut
    ) {
        // when
        long actual = sut.getFare(totalDistance);

        // then
        assertThat(actual).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvAutoSource({"51, 100", "58, 100", "59, 200", "66, 200", "67, 300", "74, 300", "75, 400", "82, 400"})
    public void sut_returns_additional_fare_if_distance_gt_50(
            long totalDistance,
            long expected,
            AdditionalSecondFarePolicy sut
    ) {
        // when
        long actual = sut.getFare(totalDistance);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}