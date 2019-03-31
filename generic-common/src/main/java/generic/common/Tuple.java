package generic.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class Tuple<X, Y> {
    private final X first;
    private final Y second;
}
