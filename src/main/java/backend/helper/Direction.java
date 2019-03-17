package backend.helper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Direction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1),
    LEFT_UP(LEFT.dx + UP.dx, LEFT.dy + UP.dy),
    LEFT_DOWN(LEFT.dx + DOWN.dx, LEFT.dy + DOWN.dy),
    RIGHT_UP(RIGHT.dx + UP.dx, RIGHT.dy + UP.dy),
    RIGHT_DOWN(RIGHT.dx + DOWN.dx, RIGHT.dy + DOWN.dy),
    ;

    private final int dx;
    private final int dy;
}
