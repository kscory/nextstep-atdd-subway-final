package nextstep.subway.domain.entity.path;

import lombok.Getter;
import nextstep.subway.domain.entity.line.LineSection;
import nextstep.subway.domain.entity.line.LineSections;

import java.util.List;

@Getter
public class Path {
    private final LineSections sections;

    public Path(LineSections sections) {
        this.sections = sections;
    }

    public static Path of(List<LineSection> sections) {
        return new Path(new LineSections(sections));
    }

    public List<Long> getAllStationIds() {
        return this.sections.getAllStationIds();
    }

    public Long totalDistance() {
        return sections.totalDistance();
    }

    public Long totalDuration() {
        return sections.totalDuration();
    }

    public Long additionalBasicFare() {
        return sections.getAdditionalBasicFare();
    }
}
