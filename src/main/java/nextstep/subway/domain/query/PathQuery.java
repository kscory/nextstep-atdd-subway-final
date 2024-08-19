package nextstep.subway.domain.query;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PathQuery {
    public enum Type {
        DURATION, DISTANCE
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Query {
        Long source;
        Long target;
        Type type;
        Integer age;
    }
}
