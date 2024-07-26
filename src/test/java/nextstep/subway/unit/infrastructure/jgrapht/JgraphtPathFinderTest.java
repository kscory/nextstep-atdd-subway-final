package nextstep.subway.unit.infrastructure.jgrapht;


import autoparams.AutoSource;
import nextstep.subway.domain.entity.line.Line;
import nextstep.subway.domain.entity.station.Station;
import nextstep.subway.domain.exception.SubwayDomainException;
import nextstep.subway.domain.exception.SubwayDomainExceptionType;
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
        SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.find(lines, source, target));

        // then
        assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.SOURCE_TARGET_SAME_STATION);
    }
}
