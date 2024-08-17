package nextstep.subway.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.subway.domain.command.LineCommand;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateLineRequest {
    private String name;
    private String color;
    private Long additionalBasicFare;

    public LineCommand.UpdateLine toCommand(Long id) {
        return new LineCommand.UpdateLine(id, name, color, additionalBasicFare);
    }
}
