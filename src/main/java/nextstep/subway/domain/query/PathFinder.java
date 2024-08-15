package nextstep.subway.domain.query;

import nextstep.subway.domain.entity.line.Line;
import nextstep.subway.domain.entity.path.Path;
import nextstep.subway.domain.entity.station.Station;

import java.util.List;

public interface PathFinder {
    Path find(List<Line> lines, Station source, Station target, PathQuery.Type type);
}
