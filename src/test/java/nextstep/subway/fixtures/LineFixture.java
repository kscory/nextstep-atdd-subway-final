package nextstep.subway.fixtures;

import nextstep.subway.domain.entity.line.Line;
import nextstep.subway.domain.entity.line.LineSections;
import nextstep.subway.domain.entity.station.Station;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class LineFixture {
    public static Line prepareRandom(Long upStationId, Long downStationId) {
        Line line = new Line(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 0L, new LineSections());
        line.addSection(upStationId, downStationId, 10L, 20L);
        return line;
    }

    public static Line prepareConnectedLine(Long additionalBasicFare, List<Long> upDownStationIds) {
        Line line = new Line(UUID.randomUUID().toString(), UUID.randomUUID().toString(), additionalBasicFare, new LineSections());
        IntStream
                .range(0, upDownStationIds.size()-1)
                .forEach((i) -> line.addSection(upDownStationIds.get(i), upDownStationIds.get(i+1), 10L, 20L));
        return line;
    }

    public static Line prepareConnectedLine(Long... upDownStationIds) {
        Line line = new Line(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 0L, new LineSections());
        IntStream
                .range(0, upDownStationIds.length-1)
                .forEach((i) -> line.addSection(upDownStationIds[i], upDownStationIds[i+1], 10L, 20L));
        return line;
    }

    public static Line prepareConnectedShortDurationLine(Long... upDownStationIds) {
        Line line = new Line(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 0L, new LineSections());
        IntStream
                .range(0, upDownStationIds.length-1)
                .forEach((i) -> line.addSection(upDownStationIds[i], upDownStationIds[i+1], 10L, 5L));
        return line;
    }

    public static Line prepareConnectedLine(Station... upDownStations) {
        Line line = new Line(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 0L, new LineSections());
        IntStream
                .range(0, upDownStations.length-1)
                .forEach((i) -> line.addSection(upDownStations[i].getId(), upDownStations[i+1].getId(), 10L, 20L));
        return line;
    }
}
