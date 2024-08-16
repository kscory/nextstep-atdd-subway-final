package nextstep.subway.infrastructure.jgrapht;

import nextstep.subway.domain.entity.line.Line;
import nextstep.subway.domain.entity.line.LineSection;
import nextstep.subway.domain.entity.line.LineSections;
import nextstep.subway.domain.entity.path.Path;
import nextstep.subway.domain.entity.station.Station;
import nextstep.subway.domain.exception.SubwayDomainException;
import nextstep.subway.domain.exception.SubwayDomainExceptionType;
import nextstep.subway.domain.query.PathFinder;
import nextstep.subway.domain.query.PathQuery;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JgraphtPathFinder implements PathFinder {
    @Override
    public Path find(List<Line> lines, Station source, Station target, PathQuery.Type type) {
        verifySameStation(source, target);

        WeightedMultigraph<Long, LineSectionEdge> graph = initGraph(lines, source, target, type);

        GraphPath<Long, LineSectionEdge> shortestPath = findShortestPath(graph, source, target);

        List<LineSection> sections = shortestPath.getEdgeList().stream()
                .map(LineSectionEdge::getSection)
                .collect(Collectors.toList());
        return Path.of(sections);
    }

    private WeightedMultigraph<Long, LineSectionEdge> initGraph(List<Line> lines, Station source, Station target, PathQuery.Type type) {
        WeightedMultigraph<Long, LineSectionEdge> graph = new WeightedMultigraph<>(LineSectionEdge.class);
        lines.forEach((line) -> addEdges(graph, line.getSections(), type));
        verifyStationExistedInGraph(graph, source, target);
        return graph;
    }

    private void addEdges(WeightedMultigraph<Long, LineSectionEdge> graph, LineSections sections, PathQuery.Type type) {
        sections.forEach((section -> {
            graph.addVertex(section.getUpStationId());
            graph.addVertex(section.getDownStationId());

            LineSectionEdge edge = new LineSectionEdge(section);
            graph.addEdge(section.getUpStationId(), section.getDownStationId(), edge);
            graph.setEdgeWeight(edge, edge.getWeight(type));
        }));
    }

    private GraphPath<Long, LineSectionEdge> findShortestPath(
            WeightedMultigraph<Long, LineSectionEdge> graph,
            Station source,
            Station target
    ) {
        DijkstraShortestPath<Long, LineSectionEdge> dijkstraPath = new DijkstraShortestPath<>(graph);
        GraphPath<Long, LineSectionEdge> path = dijkstraPath.getPath(source.getId(), target.getId());
        if (path == null) {
            throw new SubwayDomainException(SubwayDomainExceptionType.SOURCE_TARGET_NOT_CONNECTED);
        }
        return path;
    }

    private void verifySameStation(Station source, Station target) {
        if (source.isSameStation(target)) {
            throw new SubwayDomainException(SubwayDomainExceptionType.SOURCE_TARGET_SAME_STATION);
        }
    }

    private void verifyStationExistedInGraph(WeightedMultigraph<Long, LineSectionEdge> graph, Station source, Station target) {
        if (!graph.containsVertex(source.getId())) {
            throw new SubwayDomainException(SubwayDomainExceptionType.NOT_FOUND_STATION);
        }

        if (!graph.containsVertex(target.getId())) {
            throw new SubwayDomainException(SubwayDomainExceptionType.NOT_FOUND_STATION);
        }
    }
}
