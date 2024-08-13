package nextstep.subway.infrastructure.jgrapht;

import lombok.Getter;
import nextstep.subway.domain.entity.line.LineSection;
import nextstep.subway.domain.query.PathQuery;
import org.jgrapht.graph.DefaultWeightedEdge;

@Getter
public class LineSectionEdge extends DefaultWeightedEdge {
    private Long distance;
    private Long duration;

    public LineSectionEdge(LineSection section) {
        this.distance = section.getDistance();
        this.duration = section.getDuration();
    }

    protected double getWeight(PathQuery.Type type) {
        if (type == PathQuery.Type.DISTANCE) {
            return distance;
        }
        return duration;
    }

    @Override
    protected double getWeight() {
        return super.getWeight();
    }

    @Override
    protected Long getSource() {
        return (Long) super.getSource();
    }

    @Override
    protected Long getTarget() {
        return (Long) super.getTarget();
    }
}
