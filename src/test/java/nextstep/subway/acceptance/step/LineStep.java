package nextstep.subway.acceptance.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import nextstep.subway.controller.dto.AddSectionRequest;
import nextstep.subway.controller.dto.CreateLineRequest;
import nextstep.subway.controller.dto.UpdateLineRequest;

public class LineStep {
    public static ExtractableResponse<Response> 일호선을_생성한다(Long upStationId, Long downStationId, Long distance, Long duration) {
        return 노선을_생성한다(new CreateLineRequest("1호선", "#0052A4", upStationId, downStationId, distance, duration, 0L));
    }

    public static ExtractableResponse<Response> 이호선을_생성한다(Long upStationId, Long downStationId, Long distance, Long duration) {
        return 노선을_생성한다(new CreateLineRequest("2호선", "#00A84D", upStationId, downStationId, distance, duration, 0L));
    }

    public static ExtractableResponse<Response> 삼호선을_생성한다(Long upStationId, Long downStationId, Long distance, Long duration) {
        return 노선을_생성한다(new CreateLineRequest("3호선", "#EF7C1C", upStationId, downStationId, distance, duration, 0L));
    }

    public static ExtractableResponse<Response> 사호선을_생성한다(Long upStationId, Long downStationId, Long distance, Long duration) {
        return 노선을_생성한다(new CreateLineRequest("4호선", "#00A4E3", upStationId, downStationId, distance, duration, 0L));
    }

    public static ExtractableResponse<Response> 분당선을_생성한다(Long upStationId, Long downStationId, Long distance, Long duration) {
        return 노선을_생성한다(new CreateLineRequest("분당선", "#F5A200", upStationId, downStationId, distance, duration, 0L));
    }

    public static ExtractableResponse<Response> 노선을_생성한다(CreateLineRequest request) {
        return RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/lines")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 노선_목록을_조회한다() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/lines")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 노선을_조회한다(Long id) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/lines/" + id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 노선을_수정한다(Long id, String name, String color, Long additionalBasicFare) {
        UpdateLineRequest params = new UpdateLineRequest(name, color, additionalBasicFare);
        return RestAssured
                .given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/lines/" + id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 노선을_삭제한다(Long id) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/lines/" + id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 구간을_추가한다(
            Long id,
            Long upStationId,
            Long downStationId,
            Long distance,
            Long duration
    ) {
        return RestAssured
                .given().log().all()
                .body(new AddSectionRequest(upStationId, downStationId, distance, duration))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/lines/" + id + "/sections")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 구간을_삭제한다(Long lineId, Long stationId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/lines/" + lineId + "/sections?stationId=" + stationId)
                .then().log().all()
                .extract();
    }
}
