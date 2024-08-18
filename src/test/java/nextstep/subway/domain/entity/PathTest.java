package nextstep.subway.domain.entity;

import nextstep.subway.domain.entity.line.LineSections;
import nextstep.subway.domain.entity.path.Path;
import nextstep.subway.fixtures.LineFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {
    @DisplayName("totalDistance")
    @Nested
    class TotalDistance {
        @Test
        public void sut_returns_total_distances() {
            // given
            LineSections lineSections = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L).getSections();
            Path sut = new Path(lineSections);

            // when
            Long actual = sut.totalDistance();

            // then
            assertThat(actual).isEqualTo(30L);
        }
    }

    @DisplayName("totalDuration")
    @Nested
    class TotalDuration {
        @Test
        public void sut_returns_total_durations() {
            // given
            LineSections lineSections = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L).getSections();
            Path sut = new Path(lineSections);

            // when
            Long actual = sut.totalDuration();

            // then
            assertThat(actual).isEqualTo(60L);
        }
    }

    @DisplayName("additionalBasicFare")
    @Nested
    class AdditionalBasicFare {
        @Test
        public void sut_returns_additional_basic_fare() {
            // given
            LineSections lineSections = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L).getSections();
            Path sut = new Path(lineSections);

            // when
            Long actual = sut.totalDuration();

            // then
            assertThat(actual).isEqualTo(60L);
        }
    }
}
