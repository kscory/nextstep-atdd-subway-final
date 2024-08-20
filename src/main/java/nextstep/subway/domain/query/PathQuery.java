package nextstep.subway.domain.query;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

public class PathQuery {
    public enum Type {
        DURATION, DISTANCE
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Query {
        private Long source;
        private Long target;
        private Type type;
        private Optional<Integer> age;
    }
}
