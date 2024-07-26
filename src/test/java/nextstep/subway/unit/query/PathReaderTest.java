package nextstep.subway.unit.query;

import nextstep.subway.domain.entity.station.Station;
import nextstep.subway.domain.exception.SubwayDomainException;
import nextstep.subway.domain.exception.SubwayDomainExceptionType;
import nextstep.subway.domain.query.PathReader;
import nextstep.subway.setup.BaseTestSetup;
import nextstep.subway.unit.testing.StationDbUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class PathReaderTest extends BaseTestSetup {

    @Autowired
    private PathReader sut;

    @Autowired
    private StationDbUtil stationDbUtil;

    @Test
    public void sut_throws_error_if_not_found_source_station() {
        // given
        Long source = 22L;
        Station targetStation = stationDbUtil.insertStations("선릉역").get(0);

        // when
        SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.findShortestPath(source, targetStation.getId()));

        // then
        assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.NOT_FOUND_STATION);
    }

    @Test
    public void sut_throws_error_if_not_found_target_station() {
        // given
        Station targetStation = stationDbUtil.insertStations("선릉역").get(0);
        Long target = 22L;

        // when
        SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.findShortestPath(targetStation.getId(), target));

        // then
        assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.NOT_FOUND_STATION);
    }
}

