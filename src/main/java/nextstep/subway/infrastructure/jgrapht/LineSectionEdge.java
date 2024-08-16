package nextstep.subway.infrastructure.jgrapht;

import lombok.Getter;
import nextstep.subway.domain.entity.line.LineSection;
import nextstep.subway.domain.query.PathQuery;
import org.jgrapht.graph.DefaultWeightedEdge;

@Getter
public class LineSectionEdge extends DefaultWeightedEdge {
    private final LineSection section;

    public LineSectionEdge(LineSection section) {
        this.section = section;
    }

    protected double getWeight(PathQuery.Type type) {
        if (type == PathQuery.Type.DISTANCE) {
            return section.getDistance();
        }
        return section.getDuration();
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
