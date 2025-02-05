package nextstep.subway.domain.entity.line;

import lombok.Getter;
import nextstep.subway.domain.exception.SubwayDomainException;
import nextstep.subway.domain.exception.SubwayDomainExceptionType;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity(name = "line_sections")
public class LineSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id", nullable = false)
    private Line line;

    @Column(nullable = false)
    private Long upStationId;

    @Column(nullable = false)
    private Long downStationId;

    @Column(nullable = false)
    private Long distance;

    @Column(nullable = false)
    private Long duration;

    @Column(nullable = false)
    private Long position;

    protected LineSection() {}

    public LineSection(Line line, Long upStationId, Long downStationId, Long distance, Long duration) {
        this.line = line;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
        this.duration = duration;
        this.position = 0L;
    }

    public void changePosition(Long position) {
        this.position = position;
    }

    public List<LineSection> split(Long middleStationId, Long firstDistance, Long firstDuration) {
        long secondDistance = distance - firstDistance;
        if (secondDistance <= 0) {
            throw new SubwayDomainException(SubwayDomainExceptionType.INVALID_SECTION_DISTANCE);
        }

        long secondDuration = duration - firstDuration;
        if (secondDuration <= 0) {
            throw new SubwayDomainException(SubwayDomainExceptionType.INVALID_SECTION_DURATION);
        }

        LineSection first = new LineSection(line, upStationId, middleStationId, firstDistance, firstDuration);
        LineSection second = new LineSection(line, middleStationId, downStationId, secondDistance, secondDuration);

        return List.of(first, second);
    }

    public LineSection joinNext(LineSection nextSection) {
        return new LineSection(
                line,
                upStationId,
                nextSection.getDownStationId(),
                distance + nextSection.getDistance(),
                duration + nextSection.getDuration()
        );
    }

    public boolean isPrevSectionThan(LineSection section) {
        return downStationId.equals(section.getUpStationId());
    }

    public boolean isNextSectionThan(LineSection section) {
        return upStationId.equals(section.getDownStationId());
    }

    public boolean isSameUpStation(Long upStationId) {
        return this.upStationId.equals(upStationId);
    }

    public boolean isSameDownStation(Long downStationId) {
        return this.downStationId.equals(downStationId);
    }
}
