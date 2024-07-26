package nextstep.subway.domain.query;

import lombok.RequiredArgsConstructor;
import nextstep.subway.domain.entity.station.Station;
import nextstep.subway.domain.repository.LineRepository;
import nextstep.subway.domain.repository.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PathReader {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    @Transactional(readOnly = true)
    public void findShortestPath(Long source, Long target) {
        Station sourceStation = stationRepository.findByIdOrThrow(source);
        Station targetStation = stationRepository.findByIdOrThrow(target);
    }

}
