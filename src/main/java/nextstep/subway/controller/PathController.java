package nextstep.subway.controller;

import lombok.RequiredArgsConstructor;
import nextstep.auth.domain.entity.LoginMember;
import nextstep.configuration.auth.AuthenticationPrincipal;
import nextstep.subway.domain.query.PathQuery;
import nextstep.subway.domain.query.PathReader;
import nextstep.subway.domain.view.PathView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paths")
@RequiredArgsConstructor
public class PathController {

    private final PathReader pathReader;

    @GetMapping("")
    public ResponseEntity<PathView.Main> getPath(
            @RequestParam Long source,
            @RequestParam Long target,
            @RequestParam PathQuery.Type type,
            @AuthenticationPrincipal(required = false) LoginMember loginMember
        ) {
        Integer age = loginMember == null ? null : loginMember.getAge();
        PathQuery.Query query = new PathQuery.Query(source, target, type, age);
        return ResponseEntity.ok().body(pathReader.findShortestPath(query));
    }
}
