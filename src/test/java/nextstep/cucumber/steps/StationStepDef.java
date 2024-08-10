package nextstep.cucumber.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.cucumber.CucumberStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nextstep.subway.acceptance.step.StationStep.역을_생성한다;
import static org.assertj.core.api.Assertions.assertThat;

public class StationStepDef implements En {
    ExtractableResponse<Response> response;

    @Autowired
    CucumberStore cucumberStore;

    public StationStepDef() {

        Given("지하철역들을 생성하고", (DataTable table) -> {
            List<Map<String, String>> entries = table.asMaps();

            entries.forEach(entry -> {
                String station = entry.get("name");
                cucumberStore.stationIdMap.put(station, 역을_생성한다(station).jsonPath().getLong("id"));
            });
        });

        When("{string} 을 생성하면", (String station) -> {
            Map<String, String> params = new HashMap<>();
            params.put("name", station);
            response = RestAssured.given().log().all()
                    .body(params)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .post("/stations")
                    .then().log().all()
                    .extract();
        });

        Then("지하철역이 생성된다", () -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        });

        Then("지하철역 목록 조회 시 {string} 을 찾을 수 있다", (String station) -> {
            List<String> stationNames = RestAssured.given().log().all()
                            .when().get("/stations")
                            .then().log().all()
                            .extract().jsonPath().getList("name", String.class);
            assertThat(stationNames).containsAnyOf(station);
        });
    }

}
