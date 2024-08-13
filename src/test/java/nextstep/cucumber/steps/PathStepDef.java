package nextstep.cucumber.steps;

import io.cucumber.java8.En;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.cucumber.CucumberStore;
import nextstep.subway.domain.query.PathQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static nextstep.subway.acceptance.step.PathStep.경로를_조회한다;
import static org.assertj.core.api.Assertions.assertThat;

public class PathStepDef implements En {
    ExtractableResponse<Response> response;

    @Autowired
    CucumberStore cucumberStore;

    public PathStepDef() {
        When( "{string}과 {string} 사이의 최단 거리 경로를 조회하면", (String source, String target) -> {
            Long sourceId = cucumberStore.stationIdMap.get(source);
            Long targetId = cucumberStore.stationIdMap.get(target);
            response = 경로를_조회한다(sourceId, targetId, PathQuery.Type.DISTANCE);
        });

        When("{string}과 {string} 사이의 최단 시간 경로를 조회하면", (String source, String target) -> {
            Long sourceId = cucumberStore.stationIdMap.get(source);
            Long targetId = cucumberStore.stationIdMap.get(target);
            response = 경로를_조회한다(sourceId, targetId, PathQuery.Type.DURATION);
        });

        Then("{string} 역들이 조회된다", (String stations) -> {
            String[] expected = List.of(stations.split(",")).toArray(new String[0]);
            List<String> actual = response.jsonPath().getList("stations.name");
            assertThat(actual).containsExactly(expected);
        });

        Then("최단 거리 {long} 를 반환한다.", (Long distance) -> {
            Long actual = response.jsonPath().getLong("distance");
            assertThat(actual).isEqualTo(distance);
        });

        Then("최단 시간 {long} 를 반환한다.", (Long duration) -> {
            Long actual = response.jsonPath().getLong("duration");
            assertThat(actual).isEqualTo(duration);
        });
    }
}
