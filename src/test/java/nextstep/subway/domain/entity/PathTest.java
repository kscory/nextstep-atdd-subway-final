package nextstep.subway.domain.entity;

import nextstep.subway.domain.entity.line.Line;
import nextstep.subway.domain.entity.line.LineSections;
import nextstep.subway.domain.entity.path.Path;
import nextstep.subway.fixtures.LineFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

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
            List<Line> lines = List.of(
                    LineFixture.prepareConnectedLine(10L, List.of(0L, 1L, 4L)),
                    LineFixture.prepareConnectedLine(30L, List.of(7L, 4L, 100L))
            );

            LineSections pathLineSections = new LineSections(List.of(
                    lines.get(0).getSections().get(0),
                    lines.get(0).getSections().get(1),
                    lines.get(1).getSections().get(1)
            ));

            Path sut = new Path(pathLineSections);

            // when
            Long actual = sut.additionalBasicFare();

            // then
            assertThat(actual).isEqualTo(30L);
        }
    }
}
