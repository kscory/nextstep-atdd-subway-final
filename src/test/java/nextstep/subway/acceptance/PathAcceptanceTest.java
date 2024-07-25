package nextstep.subway.acceptance;

import nextstep.subway.setup.BaseTestSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("지하철 경로 검색 인수 테스트")
class PathAcceptanceTest extends BaseTestSetup {

    /**
     * Given: 지하철 노선 및 구간들이 등록되어 있고
     * When: 등록되지 않은 출발역으로 경로를 조회하면
     * Then: 오류가 발생한다.
     */
    @Test
    void 등록되어_있지_않은_출발역의_경로_조회시_오류_발생_테스트() {

    }

    /**
     * Given: 지하철 노선 및 구간들이 등록되어 있고
     * When: 등록되지 않은 도착역으로 경로를 조회하면
     * Then: 오류가 발생한다.
     */
    @Test
    void 등록되어_있지_않은_도착역의_경로_조회시_오류_발생_테스트() {

    }

    /**
     * Given: 지하철 노선 및 구간들이 등록되어 있고
     * When: 출발역과 도착역이 동일한 경로를 조회하면
     * Then: 오류가 발생한다.
     */
    @Test
    void 출발역과_도착역이_동일한경우_경로_조회시_오류_발생_테스트() {

    }

    /**
     * Given: 지하철 노선 및 구간들이 등록되어 있고
     * When: 출발역과 도착역이 연결이 되어 있지 않은 경로를 조회하면
     * Then: 오류가 발생한다.
     */
    @Test
    void 출발역과_도착역이_연결되지_않은_경로_조회시_오류_발생_테스트() {

    }

    /**
     * Given: 지하철 노선 및 구간들이 등록되어 있고
     * When: 두 역의 경로를 조회하면
     * Then: 경로에 있는 모든 역의 목록 및 경로의 최단거리를 반환한다.
     */
    @Test
    void 경로_조회_테스트() {

    }
}