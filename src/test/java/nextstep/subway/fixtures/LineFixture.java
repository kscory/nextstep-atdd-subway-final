package nextstep.subway.fixtures;

import nextstep.subway.domain.entity.line.Line;
import nextstep.subway.domain.entity.line.LineSections;
import org.springframework.data.util.Pair;

import java.util.UUID;

public class LineFixture {
    public static Line prepareRandom(Long upStationId, Long downStationId) {
        Line line = new Line(UUID.randomUUID().toString(), UUID.randomUUID().toString(), new LineSections());
        line.addSection(upStationId, downStationId, 10L);
        return line;
    }

    @SafeVarargs
    public static Line prepareLineOne(Pair<Long, Long>... upDownStationIds) {
        Line line = new Line("1호선", "#0052A4", new LineSections());

        for (Pair<Long, Long> upDownStation : upDownStationIds) {
            line.addSection(upDownStation.getFirst(), upDownStation.getSecond(), 10L);
        }
        return line;
    }
}
