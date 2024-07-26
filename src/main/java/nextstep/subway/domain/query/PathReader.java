package nextstep.subway.domain.query;

import lombok.RequiredArgsConstructor;
import nextstep.subway.domain.entity.line.Line;
import nextstep.subway.domain.entity.station.Station;
import nextstep.subway.domain.repository.LineRepository;
import nextstep.subway.domain.repository.StationRepository;
import nextstep.subway.domain.view.PathView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PathReader {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;
    private final PathFinder pathFinder;

    @Transactional(readOnly = true)
    public PathView.Main findShortestPath(Long source, Long target) {
        Station sourceStation = stationRepository.findByIdOrThrow(source);
        Station targetStation = stationRepository.findByIdOrThrow(target);
        List<Line> lines = lineRepository.findAll();
        return pathFinder.find(lines, sourceStation, targetStation);
    }

}
