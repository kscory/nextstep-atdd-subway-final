package nextstep.subway.domain.infrastructure.jgrapht;


import autoparams.AutoSource;
import nextstep.subway.domain.entity.line.Line;
import nextstep.subway.domain.entity.path.Path;
import nextstep.subway.domain.entity.station.Station;
import nextstep.subway.domain.exception.SubwayDomainException;
import nextstep.subway.domain.exception.SubwayDomainExceptionType;
import nextstep.subway.domain.query.PathQuery;
import nextstep.subway.fixtures.LineFixture;
import nextstep.subway.infrastructure.jgrapht.JgraphtPathFinder;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class JgraphtPathFinderTest {
    @ParameterizedTest
    @AutoSource
    public void sut_throws_error_if_source_equal_to_target(JgraphtPathFinder sut) {
        // given
        Station source = new Station(0L, UUID.randomUUID().toString());
        Station target = new Station(0L, UUID.randomUUID().toString());
        List<Line> lines = new ArrayList<>();

        // when
        SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.find(lines, source, target, PathQuery.Type.DISTANCE));

        // then
        assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.SOURCE_TARGET_SAME_STATION);
    }

    @ParameterizedTest
    @AutoSource
    public void sut_throws_error_if_source_not_includes_in_graph(JgraphtPathFinder sut) {
        // given
        Station source = new Station(0L, UUID.randomUUID().toString());
        Station target = new Station(100L, UUID.randomUUID().toString());
        List<Line> lines = List.of(
                LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L),
                LineFixture.prepareConnectedLine(98L, 99L, 100L)
        );

        // when
        SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.find(lines, source, target, PathQuery.Type.DISTANCE));

        // then
        assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.NOT_FOUND_STATION);
    }

    @ParameterizedTest
    @AutoSource
    public void sut_throws_error_if_target_not_includes_in_graph(JgraphtPathFinder sut) {
        // given
        Station source = new Station(0L, UUID.randomUUID().toString());
        Station target = new Station(100L, UUID.randomUUID().toString());
        List<Line> lines = List.of(
                LineFixture.prepareConnectedLine(1L, 2L, 3L, 4L),
                LineFixture.prepareConnectedLine(98L, 99L)
        );

        // when
        SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.find(lines, source, target, PathQuery.Type.DISTANCE));

        // then
        assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.NOT_FOUND_STATION);
    }

    @ParameterizedTest
    @AutoSource
    public void sut_throws_error_if_source_and_target_not_connected(JgraphtPathFinder sut) {
        // given
        Station source = new Station(0L, UUID.randomUUID().toString());
        Station target = new Station(100L, UUID.randomUUID().toString());
        List<Line> lines = List.of(
                LineFixture.prepareConnectedLine(0L, 1L, 2L, 3L, 4L),
                LineFixture.prepareConnectedLine(98L, 99L, 100L)
        );

        // when
        SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.find(lines, source, target, PathQuery.Type.DISTANCE));

        // then
        assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.SOURCE_TARGET_NOT_CONNECTED);
    }

    @ParameterizedTest
    @AutoSource
    public void sut_returns_shortest_distance_section_list(JgraphtPathFinder sut) {
        // given
        Station source = new Station(0L, UUID.randomUUID().toString());
        Station target = new Station(100L, UUID.randomUUID().toString());
        List<Line> lines = List.of(
                LineFixture.prepareConnectedLine(0L, 1L, 2L, 3L, 4L),
                LineFixture.prepareConnectedShortDurationLine(6L, 1L, 98L, 99L, 100L),
                LineFixture.prepareConnectedLine(7L, 4L, 98L, 100L)
        );

        // when
        Path actual = sut.find(lines, source, target, PathQuery.Type.DISTANCE);

        // then
        assertThat(actual.totalDistance()).isEqualTo(30);
        assertThat(actual.totalDuration()).isEqualTo(45);
        assertThat(actual.getAllStationIds()).usingRecursiveComparison().isEqualTo(List.of(0L, 1L, 98L, 100L));
    }

    @ParameterizedTest
    @AutoSource
    public void sut_returns_shortest_duration_section_list(JgraphtPathFinder sut) {
        // given
        Station source = new Station(0L, UUID.randomUUID().toString());
        Station target = new Station(100L, UUID.randomUUID().toString());
        List<Line> lines = List.of(
                LineFixture.prepareConnectedLine(0L, 1L, 2L, 3L, 4L),
                LineFixture.prepareConnectedShortDurationLine(6L, 1L, 98L, 99L, 100L),
                LineFixture.prepareConnectedLine(7L, 4L, 98L, 100L)
        );

        // when
        Path actual = sut.find(lines, source, target, PathQuery.Type.DURATION);

        // then
        assertThat(actual.totalDistance()).isEqualTo(40);
        assertThat(actual.totalDuration()).isEqualTo(35);
        assertThat(actual.getAllStationIds()).usingRecursiveComparison().isEqualTo(List.of(0L, 1L, 98L, 99L, 100L));
    }
}
