package nextstep.subway.domain.service.farecalculator;

import autoparams.CsvAutoSource;
import org.junit.jupiter.params.ParameterizedTest;

import static org.assertj.core.api.Assertions.assertThat;

class AdditionalFirstFarePolicyTest {
    @ParameterizedTest
    @CsvAutoSource({"3", "5", "7", "9", "10"})
    public void sut_returns_zero_fare_if_distance_lte_10(
            long totalDistance,
            AdditionalFirstFarePolicy sut
    ) {
        // when
        long actual = sut.getFare(totalDistance);

        // then
        assertThat(actual).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvAutoSource({"11, 100", "12, 100", "15, 100", "16, 200", "38, 600", "40, 600", "42, 700", "45, 700", "46, 800", "50, 800", "55, 800"})
    public void sut_returns_additional_fare_if_distance_gt_10_and_lte_50(
            long totalDistance,
            long expected,
            AdditionalFirstFarePolicy sut
    ) {
        // when
        long actual = sut.getFare(totalDistance);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvAutoSource({"50", "55", "51", "80", "100", "120", "180"})
    public void sut_returns_800_if_distance_gt_50(long totalDistance, AdditionalFirstFarePolicy sut) {
        // when
        long actual = sut.getFare(totalDistance);

        // then
        assertThat(actual).isEqualTo(800);
    }
}