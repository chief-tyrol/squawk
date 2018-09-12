package zone.gryphon.screech.model;

import lombok.Builder;
import lombok.Value;

import java.nio.ByteBuffer;

@Value
@Builder(toBuilder = true)
public class RequestBody {

    private final ByteBuffer body;

    private final String contentType;

}
