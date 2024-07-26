package nextstep.subway.domain.query;

import nextstep.subway.domain.entity.line.Line;
import nextstep.subway.domain.entity.station.Station;
import nextstep.subway.domain.view.PathView;

import java.util.List;

public interface PathFinder {
    PathView.Main find(List<Line> lines, Station source, Station target);
}
