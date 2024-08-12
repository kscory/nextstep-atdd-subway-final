package nextstep.subway.acceptance.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.domain.query.PathQuery;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class PathStep {
    public static ExtractableResponse<Response> 경로를_조회한다(Long source, Long target, PathQuery.Type type) {

        Map<String, Object> params = new HashMap<>();
        params.put("source", source);
        params.put("target", target);
        params.put("type", type);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParams(params)
                .when().get("/paths")
                .then().log().all()
                .extract();
    }
}
