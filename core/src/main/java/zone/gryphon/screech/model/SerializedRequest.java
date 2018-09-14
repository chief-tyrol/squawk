/*
 * Copyright 2018-2018 Gryphon Zone
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package zone.gryphon.screech.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.net.URI;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class SerializedRequest {

    @NonNull
    private final String method;

    /**
     * The request uri.
     * <i>Note: will not include query parameters, see {@link #getQueryParams()}</i>
     */
    @NonNull
    private final URI uri;

    /**
     * The request body. Null if the request has no body (e.g. it's a GET request)
     */
    private final RequestBody requestBody;

    /**
     * Headers for the request
     */
    private final List<HttpParam> headers;

    /**
     * Query parameters for the request
     */
    private final List<HttpParam> queryParams;

}
