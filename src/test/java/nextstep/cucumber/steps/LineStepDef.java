package nextstep.cucumber.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.cucumber.CucumberStore;
import nextstep.subway.controller.dto.CreateLineRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static nextstep.subway.acceptance.step.LineStep.구간을_추가한다;
import static nextstep.subway.acceptance.step.LineStep.노선을_생성한다;

public class LineStepDef implements En {
    ExtractableResponse<Response> response;

    @Autowired
    CucumberStore cucumberStore;

    public LineStepDef() {
        Given("노선들을 생성하고", (DataTable table) -> {
            List<Map<String, String>> entries = table.asMaps();

            entries.forEach(entry -> {
                String lineName = entry.get("name");
                CreateLineRequest request = new CreateLineRequest(
                        lineName,
                        entry.get("color"),
                        cucumberStore.stationIdMap.get(entry.get("upStation")),
                        cucumberStore.stationIdMap.get(entry.get("downStation")),
                        Long.parseLong(entry.get("distance")),
                        Long.parseLong(entry.get("duration")),
                        Long.parseLong(entry.get("additionalBasicFare"))
                );
                ExtractableResponse<Response> lineResponse = 노선을_생성한다(request);
                cucumberStore.lineIdMap.put(lineName, lineResponse.jsonPath().getLong("id"));
            });
        });

        Given("구간들을 추가하고", (DataTable table) -> {
            List<Map<String, String>> entries = table.asMaps();

            entries.forEach(entry -> 구간을_추가한다(
                    cucumberStore.lineIdMap.get(entry.get("line")),
                    cucumberStore.stationIdMap.get(entry.get("upStation")),
                    cucumberStore.stationIdMap.get(entry.get("downStation")),
                    Long.parseLong(entry.get("distance")),
                    Long.parseLong(entry.get("duration"))
            ));
        });
    }
}
