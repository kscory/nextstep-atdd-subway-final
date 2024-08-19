package nextstep.subway.domain.entity;

import autoparams.AutoSource;
import nextstep.subway.domain.command.LineCommand;
import nextstep.subway.domain.entity.line.Line;
import nextstep.subway.domain.entity.line.LineSection;
import nextstep.subway.domain.entity.line.LineSections;
import nextstep.subway.domain.exception.SubwayDomainException;
import nextstep.subway.domain.exception.SubwayDomainExceptionType;
import nextstep.subway.fixtures.LineFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LineTest {
    @DisplayName("init")
    @Nested
    class Init {
        @ParameterizedTest
        @AutoSource
        public void sut_returns_new_line(LineCommand.CreateLine command) {
            // when
            Line actual = Line.init(command);

            // then
            assertAll("assert init",
                    () -> assertThat(actual.getName()).isEqualTo(command.getName()),
                    () -> assertThat(actual.getColor()).isEqualTo(command.getColor()),
                    () -> assertThat(actual.getAdditionalBasicFare()).isEqualTo(command.getAdditionalBasicFare()),

                    // section
                    () -> assertThat(actual.getSections().size()).isEqualTo(1),
                    () -> assertThat(actual.getSections().getLastSection().getUpStationId()).isEqualTo(command.getUpStationId()),
                    () -> assertThat(actual.getSections().getLastSection().getDownStationId()).isEqualTo(command.getDownStationId()),
                    () -> assertThat(actual.getSections().getLastSection().getDistance()).isEqualTo(command.getDistance()),
                    () -> assertThat(actual.getSections().getLastSection().getDuration()).isEqualTo(command.getDuration())
            );
        }
    }

    @DisplayName("update")
    @Nested
    class Update {
        @ParameterizedTest
        @AutoSource
        public void sut_updated(Line sut, LineCommand.UpdateLine command) {
            // when
            sut.update(command);


            // then
            assertThat(sut.getName()).isEqualTo(command.getName());
            assertThat(sut.getColor()).isEqualTo(command.getColor());
        }
    }

    @DisplayName("addSection")
    @Nested
    class AddSection {
        @Test
        public void sut_throws_if_upStation_not_existed_but_not_first_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.addSection(5L, 7L, 5L, 10L));

            // then
            assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.INVALID_STATION);
        }

        @Test
        public void sut_throws_if_downStation_existed_but_not_first_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.addSection(4L, 3L, 5L, 10L));

            // then
            assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.INVALID_STATION);
        }

        @Test
        public void sut_throws_if_upStation_existed_but_first_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.addSection(3L, 1L, 5L, 10L));

            // then
            assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.INVALID_STATION);
        }

        @Test
        public void sut_throws_if_distance_greater_than_inserted_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.addSection(2L, 9L, 10L, 5L));

            // then
            assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.INVALID_SECTION_DISTANCE);
        }

        @Test
        public void sut_throws_if_duration_greater_than_inserted_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.addSection(2L, 9L, 4L, 20L));

            // then
            assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.INVALID_SECTION_DURATION);
        }

        @Test
        public void sut_add_section_if_empty() {
            // given
            Line sut = new Line("11", "cc", 99L, new LineSections());

            // when
            sut.addSection(1L, 2L, 10L, 10L);

            // then
            assertThat(sut.getSections().size()).isEqualTo(1);
        }

        @Test
        public void sut_add_section_if_first_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            sut.addSection(99L, 1L, 10L, 5L);

            // then
            assertThat(sut.getSections().getAllStationIds()).isEqualTo(List.of(99L, 1L, 2L, 3L, 4L));
            assertThat(sut.getSections().getFirstSection().getDistance()).isEqualTo(10L);
            assertThat(sut.getSections().getFirstSection().getDuration()).isEqualTo(5L);
        }

        @Test
        public void sut_add_section_if_last_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            sut.addSection(4L, 5L, 10L, 5L);

            // then
            assertThat(sut.getSections().getAllStationIds()).isEqualTo(List.of(1L, 2L, 3L, 4L, 5L));
            assertThat(sut.getSections().getLastSection().getDistance()).isEqualTo(10L);
            assertThat(sut.getSections().getLastSection().getDuration()).isEqualTo(5L);
        }

        @Test
        public void sut_add_section_if_middle_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            sut.addSection(3L, 88L, 5L, 8L);

            // then
            assertThat(sut.getSections().getAllStationIds()).isEqualTo(List.of(1L, 2L, 3L, 88L, 4L));
        }

        @Test
        public void sut_split_distance_if_middle_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            sut.addSection(1L, 88L, 4L, 5L);

            // then
            assertThat(sut.getSections().get(0).getDistance()).isEqualTo(4L);
            assertThat(sut.getSections().get(1).getDistance()).isEqualTo(6L);
        }

        @Test
        public void sut_split_duration_if_middle_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            sut.addSection(1L, 88L, 4L, 5L);

            // then
            assertThat(sut.getSections().get(0).getDuration()).isEqualTo(5L);
            assertThat(sut.getSections().get(1).getDuration()).isEqualTo(15L);
        }
    }

    @DisplayName("deleteSection")
    @Nested
    class DeleteSection {
        @Test
        public void sut_throws_if_section_size_one() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L);

            // when
            SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.deleteSection(1L));

            // then
            assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.INVALID_SECTION_SIZE);
        }

        @Test
        public void sut_throws_if_not_includes_station() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.deleteSection(999L));

            // then
            assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.NOT_FOUND_STATION_ON_LINE);
        }

        @Test
        public void sut_delete_section_if_first_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            sut.deleteSection(1L);

            // then
            assertThat(sut.getSections().getAllStationIds()).isEqualTo(List.of(2L, 3L, 4L));
        }

        @Test
        public void sut_delete_section_if_last_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            sut.deleteSection(4L);

            // then
            assertThat(sut.getSections().getAllStationIds()).isEqualTo(List.of(1L, 2L, 3L));
        }

        @Test
        public void sut_delete_section_if_middle_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            sut.deleteSection(2L);

            // then
            assertThat(sut.getSections().getAllStationIds()).isEqualTo(List.of(1L, 3L, 4L));
        }

        @Test
        public void sut_join_distance_if_middle_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            sut.deleteSection(2L);

            // then
            assertThat(sut.getSections().get(0).getDistance()).isEqualTo(20L);
        }

        @Test
        public void sut_join_duration_if_middle_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L);

            // when
            sut.deleteSection(2L);

            // then
            assertThat(sut.getSections().get(0).getDuration()).isEqualTo(40L);
        }
    }

    @DisplayName("findSectionByUpDownStationId")
    @Nested
    class FindSectionByUpDownStationId {
        @Test
        public void sut_returns_section() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L, 5L);

            // when
            Optional<LineSection> actual = sut.findSectionByUpDownStationId(3L, 4L);

            // then
            assertThat(actual).isNotEmpty();
            assertThat(actual.get().getUpStationId()).isEqualTo(3L);
            assertThat(actual.get().getDownStationId()).isEqualTo(4L);
            assertThat(actual.get().getDistance()).isEqualTo(10L);
            assertThat(actual.get().getDuration()).isEqualTo(20L);
        }

        @Test
        public void sut_returns_empty_if_not_found() {
            // given
            Line sut = LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L, 5L);

            // when
            Optional<LineSection> actual = sut.findSectionByUpDownStationId(3L, 5L);

            // then
            assertThat(actual).isEmpty();
        }
    }
}
