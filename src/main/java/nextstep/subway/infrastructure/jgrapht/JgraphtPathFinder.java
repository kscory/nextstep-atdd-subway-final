package nextstep.subway.infrastructure.jgrapht;

import nextstep.subway.domain.entity.line.Line;
import nextstep.subway.domain.entity.line.LineSection;
import nextstep.subway.domain.entity.line.LineSections;
import nextstep.subway.domain.entity.station.Station;
import nextstep.subway.domain.exception.SubwayDomainException;
import nextstep.subway.domain.exception.SubwayDomainExceptionType;
import nextstep.subway.domain.query.PathFinder;
import nextstep.subway.domain.query.PathQuery;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JgraphtPathFinder implements PathFinder {
    @Override
    public PathResult find(List<Line> lines, Station source, Station target, PathQuery.Type type) {
        verifySameStation(source, target);

        WeightedMultigraph<Long, DefaultWeightedEdge> graph = initGraph(lines, source, target, type);

        GraphPath<Long, DefaultWeightedEdge> shortestPath = findShortestPath(graph, source, target);

        Pair<Long, Long> distanceAndDuration = this.calculateDistanceAndDuration(lines, shortestPath.getVertexList());

        return new PathResult(shortestPath.getVertexList(), distanceAndDuration.getFirst(), distanceAndDuration.getSecond());
//        return new PathResult(shortestPath.getVertexList(), (long) shortestPath.getWeight(), null);
    }

    private WeightedMultigraph<Long, DefaultWeightedEdge> initGraph(List<Line> lines, Station source, Station target, PathQuery.Type type) {
        WeightedMultigraph<Long, DefaultWeightedEdge> graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        lines.forEach((line) -> addEdges(graph, line.getSections(), type));
        verifyStationExistedInGraph(graph, source, target);
        return graph;
    }

    private void addEdges(WeightedMultigraph<Long, DefaultWeightedEdge> graph, LineSections sections, PathQuery.Type type) {
        sections.forEach((section -> {
            graph.addVertex(section.getUpStationId());
            graph.addVertex(section.getDownStationId());

            if (type == PathQuery.Type.DISTANCE) {
                graph.setEdgeWeight(graph.addEdge(section.getUpStationId(), section.getDownStationId()), section.getDistance());
                return;
            }

            graph.setEdgeWeight(graph.addEdge(section.getUpStationId(), section.getDownStationId()), section.getDuration());
        }));
    }

    private GraphPath<Long, DefaultWeightedEdge> findShortestPath(
            WeightedMultigraph<Long, DefaultWeightedEdge> graph,
            Station source,
            Station target
    ) {
        DijkstraShortestPath<Long, DefaultWeightedEdge> dijkstraPath = new DijkstraShortestPath<>(graph);
        GraphPath<Long, DefaultWeightedEdge> path = dijkstraPath.getPath(source.getId(), target.getId());
        if (path == null) {
            throw new SubwayDomainException(SubwayDomainExceptionType.SOURCE_TARGET_NOT_CONNECTED);
        }
        return path;
    }

    private Pair<Long, Long> calculateDistanceAndDuration(List<Line> lines, List<Long> connectedStationIds) {

        List<Long> distances = new ArrayList<>();
        List<Long> durations = new ArrayList<>();
        for (int i=0; i<connectedStationIds.size() -1; i++) {
            Long upStationId = connectedStationIds.get(i);
            Long downStationId = connectedStationIds.get(i + 1);

            for (Line line : lines) {
                Optional<LineSection> section = line.findSectionByUpDownStationId(upStationId, downStationId);
                if (section.isPresent()) {
                    distances.add(section.get().getDistance());
                    durations.add(section.get().getDuration());
                    break;
                }
            }
        }

        Long totalDistance = distances.stream().mapToLong(Long::longValue).sum();
        Long totalDuration = durations.stream().mapToLong(Long::longValue).sum();
        return Pair.of(totalDistance, totalDuration);
    }

    private void verifySameStation(Station source, Station target) {
        if (source.isSameStation(target)) {
            throw new SubwayDomainException(SubwayDomainExceptionType.SOURCE_TARGET_SAME_STATION);
        }
    }

    private void verifyStationExistedInGraph(WeightedMultigraph<Long, DefaultWeightedEdge> graph, Station source, Station target) {
        if (!graph.containsVertex(source.getId())) {
            throw new SubwayDomainException(SubwayDomainExceptionType.NOT_FOUND_STATION);
        }

        if (!graph.containsVertex(target.getId())) {
            throw new SubwayDomainException(SubwayDomainExceptionType.NOT_FOUND_STATION);
        }
    }
}
