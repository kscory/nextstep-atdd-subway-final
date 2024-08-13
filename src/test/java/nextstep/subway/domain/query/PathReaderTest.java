package nextstep.subway.domain.query;

import nextstep.subway.domain.entity.line.Line;
import nextstep.subway.domain.entity.station.Station;
import nextstep.subway.domain.exception.SubwayDomainException;
import nextstep.subway.domain.exception.SubwayDomainExceptionType;
import nextstep.subway.domain.view.PathView;
import nextstep.util.BaseTestSetup;
import nextstep.subway.domain.testing.LineDbUtil;
import nextstep.subway.domain.testing.StationDbUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class PathReaderTest extends BaseTestSetup {

    @Autowired
    private PathReader sut;

    @Autowired
    private StationDbUtil stationDbUtil;

    @Autowired
    private LineDbUtil lineDbUtil;

    @Test
    public void sut_throws_error_if_not_found_source_station() {
        // given
        Station targetStation = stationDbUtil.insertStations("선릉역").get(0);
        PathQuery.Query query = new PathQuery.Query(22L, targetStation.getId(), PathQuery.Type.DISTANCE);


        // when
        SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.findShortestPath(query));

        // then
        assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.NOT_FOUND_STATION);
    }

    @Test
    public void sut_throws_error_if_not_found_target_station() {
        // given
        Station targetStation = stationDbUtil.insertStations("선릉역").get(0);
        PathQuery.Query query = new PathQuery.Query(targetStation.getId(), 22L, PathQuery.Type.DISTANCE);

        // when
        SubwayDomainException actual = (SubwayDomainException) catchThrowable(() -> sut.findShortestPath(query));

        // then
        assertThat(actual.getExceptionType()).isEqualTo(SubwayDomainExceptionType.NOT_FOUND_STATION);
    }

    @Test
    public void sut_returns_shortest_distance_path() {
        // given
        List<Station> stations = stationDbUtil.insertStations("삼성역", "잠실역", "선릉역", "삼성역");
        Line line = lineDbUtil.insertLine(stations.get(0).getId(), stations.get(1).getId());
        lineDbUtil.insertSection(line, stations.get(1).getId(), stations.get(2).getId(), 10L, 20L);
        lineDbUtil.insertSection(line, stations.get(2).getId(), stations.get(3).getId(), 10L, 20L);
        PathQuery.Query query = new PathQuery.Query(stations.get(0).getId(), stations.get(2).getId(), PathQuery.Type.DISTANCE);

        // when
        PathView.Main actual = sut.findShortestPath(query);

        // then
        assertThat(actual.getStations().size()).isEqualTo(3);
        assertThat(actual.getDistance()).isEqualTo(20L);
        assertThat(actual.getDuration()).isEqualTo(40L);
//        assertThat(actual.getFare()).isEqualTo(1450L);
    }
}

