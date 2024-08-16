package nextstep.subway.domain.service.farecalculator;

import autoparams.AutoSource;
import autoparams.Repeat;
import org.junit.jupiter.params.ParameterizedTest;

import static org.assertj.core.api.Assertions.assertThat;

class BasicFarePolicyTest {
    @ParameterizedTest
    @AutoSource
    @Repeat(5)
    public void sut_returns_1250(long totalDistance, BasicFarePolicy sut) {
        // when
        long actual = sut.getFare(totalDistance);

        // then
        assertThat(actual).isEqualTo(1250);
    }
}