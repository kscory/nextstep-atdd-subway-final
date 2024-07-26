package nextstep.subway.infrastructure.jgrapht;

import nextstep.subway.domain.entity.line.Line;
import nextstep.subway.domain.entity.station.Station;
import nextstep.subway.domain.exception.SubwayDomainException;
import nextstep.subway.domain.exception.SubwayDomainExceptionType;
import nextstep.subway.domain.query.PathFinder;
import nextstep.subway.domain.view.PathView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JgraphtPathFinder implements PathFinder {
    @Override
    public PathView.Main find(List<Line> lines, Station source, Station target) {
        verifySameStation(source, target);
        return null;
    }

    private void verifySameStation(Station source, Station target) {
        if (source.isSameStation(target)) {
            throw new SubwayDomainException(SubwayDomainExceptionType.SOURCE_TARGET_SAME_STATION);
        }
    }
}
