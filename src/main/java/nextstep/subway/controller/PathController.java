package nextstep.subway.controller;

import lombok.RequiredArgsConstructor;
import nextstep.subway.domain.query.PathReader;
import nextstep.subway.domain.view.PathView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/paths")
@RequiredArgsConstructor
public class PathController {

    private final PathReader pathReader;

    @GetMapping("")
    public ResponseEntity<PathView.Main> getPath(
            @RequestParam Long source,
            @RequestParam Long target
    ) {
        pathReader.findShortestPath(source, target);

        return ResponseEntity.ok().body(new PathView.Main(new ArrayList<>(), 10L));
    }
}
