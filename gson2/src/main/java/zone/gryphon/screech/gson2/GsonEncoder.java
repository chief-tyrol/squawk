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

package zone.gryphon.screech.gson2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import zone.gryphon.screech.Callback;
import zone.gryphon.screech.RequestEncoder;

import java.nio.ByteBuffer;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

public class GsonEncoder implements RequestEncoder {

    private final Gson gson;

    public GsonEncoder() {
        this(new GsonBuilder().create());
    }

    public GsonEncoder(Gson gson) {
        this.gson = Objects.requireNonNull(gson, "gson");
    }

    @Override
    public <T> void encode(T entity, Callback<ByteBuffer> callback) {
        try {
            callback.onSuccess(ByteBuffer.wrap(gson.toJson(entity).getBytes(UTF_8)));
        } catch (Throwable t) {
            callback.onFailure(t);
        }
    }

    @Override
    public String toString() {
        return "GsonEncoder{Gson@" + gson.hashCode() + '}';
    }
}
