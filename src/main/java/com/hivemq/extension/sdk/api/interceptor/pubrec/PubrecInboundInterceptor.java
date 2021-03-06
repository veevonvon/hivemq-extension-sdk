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

package com.hivemq.extension.sdk.api.interceptor.pubrec;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.async.TimeoutFallback;
import com.hivemq.extension.sdk.api.interceptor.Interceptor;
import com.hivemq.extension.sdk.api.interceptor.pubrec.parameter.PubrecInboundInput;
import com.hivemq.extension.sdk.api.interceptor.pubrec.parameter.PubrecInboundOutput;
import com.hivemq.extension.sdk.api.packets.pubrec.ModifiablePubrecPacket;

import java.time.Duration;

/**
 * Interface for the inbound PUBREC interception.
 * <p>
 * Interceptors are always called by the same Thread for all messages from the same client.
 * <p>
 * If the same instance is shared between multiple clients it can be called in different Threads and must therefore be
 * thread-safe.
 * <p>
 * When the method {@link #onInboundPubrec(PubrecInboundInput, PubrecInboundOutput)} throws an exception or a call to
 * {@link PubrecInboundOutput#async(Duration)} times out with {@link TimeoutFallback#FAILURE}, HiveMQ will ignore this
 * interceptor and will:
 * <ol>
 *   <li>Log the exception
 *   <li>Revert the changes to the {@link ModifiablePubrecPacket} made by the interceptor
 *   <li>Call the next {@link PubrecInboundInterceptor} or send the PUBREC to the server if no interceptor is left
 * </ol>
 *
 * @author Yannick Weber
 * @since 4.3.0, CE 2020.1
 */
@FunctionalInterface
public interface PubrecInboundInterceptor extends Interceptor {

    /**
     * When a {@link PubrecInboundInterceptor} is set through any extension, this method gets called for every inbound
     * PUBREC packet from any MQTT client.
     * <p>
     * When the extension is enabled after HiveMQ is already running, this method will also be called for future PUBRECs
     * of clients that are already connected.
     *
     * @param pubrecInboundInput  The {@link PubrecInboundInput} parameter.
     * @param pubrecInboundOutput The {@link PubrecInboundOutput} parameter.
     * @since 4.3.0, CE 2020.1
     */
    void onInboundPubrec(
            @NotNull PubrecInboundInput pubrecInboundInput, @NotNull PubrecInboundOutput pubrecInboundOutput);
}
