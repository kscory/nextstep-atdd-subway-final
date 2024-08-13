package nextstep.subway.domain.service.farecalculator;

public interface FarePolicy {
    long getFare(long totalDistance);
}
