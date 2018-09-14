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

package zone.gryphon.screech;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.BufferingResponseListener;
import org.eclipse.jetty.client.util.ByteBufferContentProvider;
import zone.gryphon.screech.model.ResponseBody;
import zone.gryphon.screech.model.SerializedRequest;
import zone.gryphon.screech.model.SerializedResponse;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.Consumer;

public class JettyScreechClient implements Client, Closeable {

    private static HttpClient createAndConfigureClient() {
        HttpClient client = new HttpClient();
        client.setMaxConnectionsPerDestination(Short.MAX_VALUE);
        client.setMaxRequestsQueuedPerDestination(Short.MAX_VALUE);
        return client;
    }

    private final HttpClient client;

    public JettyScreechClient() {
        this(createAndConfigureClient());
    }

    public JettyScreechClient(HttpClient client) {
        this.client = client;

        try {
            this.client.start();
        } catch (Exception e) {
            throw new RuntimeException("Failed to start jetty client", e);
        }
    }

    @Override
    public void request(SerializedRequest request, Callback<SerializedResponse> callback) {
        convert(request).send(new BufferingResponseListener() {

            @Override
            public void onComplete(Result result) {
                try {
                    callback.onSuccess(convert(result, getContent(), getEncoding(), getMediaType()));
                } catch (Throwable e) {
                    callback.onError(e);
                }
            }
        });
    }

    private SerializedResponse convert(Result result, byte[] body, String encoding, String contentType) throws Throwable {

        // does not include 4xx/5xx status codes, indicates something like a read timeout, connection reset, etc...
        if (result.isFailed()) {
            throw result.getFailure();
        }

        ResponseBody responseBody = ResponseBody.from(ByteBuffer.wrap(body), contentType, encoding);

        Map<String, Collection<String>> headers = new HashMap<>();

        result.getResponse().getHeaders().forEach(header -> headers.computeIfAbsent(header.getName(), ignored -> new ArrayList<>()).add(header.getValue()));

        return SerializedResponse.builder()
                .status(result.getResponse().getStatus())
                .responseBody(responseBody)
                .headers(Collections.unmodifiableList(Collections.emptyList()))
                .build();
    }

    private org.eclipse.jetty.client.api.Request convert(SerializedRequest request) {
        org.eclipse.jetty.client.api.Request jettyRequest = client.newRequest(request.getUri())
                .method(request.getMethod());

        if (request.getHeaders() != null) {
//            request.getHeaders().forEach((key, values) -> values.forEach(value -> jettyRequest.header(key, value)));
        }

        if (request.getQueryParams() != null) {
//            request.getQueryParams().forEach((key, values) -> values.forEach(value -> jettyRequest.param(key, value)));
        }

        if (request.getRequestBody() != null) {
            jettyRequest.content(new ByteBufferContentProvider(request.getRequestBody().getContentType(), request.getRequestBody().getBody()));
        }

        return jettyRequest;
    }

    @Override
    public void close() throws IOException {
        try {
            this.client.stop();
        } catch (Exception e) {
            throw new IOException("Failed to close client", e);
        }
    }


}
