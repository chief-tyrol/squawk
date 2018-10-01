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

import zone.gryphon.screech.ResponseDecoderFactory;
import zone.gryphon.screech.testing.BaseJsonDeserializerTest;

public class GsonDecoderTest extends BaseJsonDeserializerTest {

    @Override
    protected ResponseDecoderFactory createFactory() {
        return new GsonDecoderFactory();
    }
}