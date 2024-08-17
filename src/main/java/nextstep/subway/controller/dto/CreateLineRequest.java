package nextstep.subway.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.subway.domain.command.LineCommand;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateLineRequest {
    private String name;
    private String color;
    private Long upStationId;
    private Long downStationId;
    private Long distance;
    private Long duration;
    private Long additionalBasicFare;

    public LineCommand.CreateLine toCommand() {
        return new LineCommand.CreateLine(
                name,
                color,
                upStationId,
                downStationId,
                distance,
                duration,
                additionalBasicFare
        );
    }
}
