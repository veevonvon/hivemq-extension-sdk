/*
 * Copyright 2018-present HiveMQ GmbH
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
 */

package com.hivemq.extension.sdk.api.interceptor.suback;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.interceptor.Interceptor;
import com.hivemq.extension.sdk.api.interceptor.suback.parameter.SubackOutboundInput;
import com.hivemq.extension.sdk.api.interceptor.suback.parameter.SubackOutboundOutput;

/**
 * Interface for the SUBACK outbound interception.
 * <p>
 * Interceptors are always called by the same Thread for all SUBACK messages from the same client.
 * <p>
 * If the same instance is shared between multiple clients it can be called in different Threads and must therefore be
 * thread-safe.
 *
 * @author Robin Atherton
 * @since 4.3.0, CE 2020.1
 */
@FunctionalInterface
public interface SubackOutboundInterceptor extends Interceptor {

    /**
     * When a {@link SubackOutboundInterceptor} is set through any extension, this method gets called for every outbound
     * SUBACK packet from any MQTT client.
     * <p>
     * When the extension is enabled after HiveMQ is already running this method will also be called for future SUBACK
     * of clients that are already connected.
     *
     * @param subackOutboundInput  The {@link SubackOutboundInput} parameter.
     * @param subackOutboundOutput The {@link SubackOutboundOutput} parameter.
     * @since 4.3.0, CE 2020.1
     */
    void onOutboundSuback(
            @NotNull SubackOutboundInput subackOutboundInput, @NotNull SubackOutboundOutput subackOutboundOutput);
}
