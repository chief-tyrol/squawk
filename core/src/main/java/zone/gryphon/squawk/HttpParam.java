package zone.gryphon.squawk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class HttpParam {

    private final String key;

    private final String value;

}
