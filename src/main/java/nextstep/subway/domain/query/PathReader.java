package nextstep.subway.domain.query;

import lombok.RequiredArgsConstructor;
import nextstep.subway.domain.entity.line.Line;
import nextstep.subway.domain.entity.path.Path;
import nextstep.subway.domain.entity.station.Station;
import nextstep.subway.domain.repository.LineRepository;
import nextstep.subway.domain.repository.StationRepository;
import nextstep.subway.domain.service.farecalculator.FareCalculator;
import nextstep.subway.domain.view.PathView;
import nextstep.subway.domain.view.StationView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PathReader {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;
    private final PathFinder pathFinder;
    private final FareCalculator fareCalculator;

    @Transactional(readOnly = true)
    public PathView.Main findShortestPath(PathQuery.Query query) {
        Station sourceStation = stationRepository.findByIdOrThrow(query.source);
        Station targetStation = stationRepository.findByIdOrThrow(query.target);
        List<Line> lines = lineRepository.findAll();

        Path path = pathFinder.find(lines, sourceStation, targetStation, query.type);

        List<StationView.Main> stations = stationRepository.findAllById(path.getAllStationIds()).stream()
                .map((station -> new StationView.Main(station.getId(), station.getName())))
                .collect(Collectors.toList());

        long fare = fareCalculator.getFare(path.totalDistance());

        return new PathView.Main(
                stations,
                path.totalDistance(),
                path.totalDuration(),
                fare
        );
    }

}
