/*-
 * ========================LICENSE_START=================================
 * Bucket4j
 * %%
 * Copyright (C) 2015 - 2020 Vladimir Bukhtoyarov
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

package io.github.bucket4j.distributed.proxy.generic.compare_and_swap;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Describes the set of operations that {@link AbstractCompareAndSwapBasedProxyManager} typically performs in reaction to user request.
 * The typical flow is following:
 * <ol>
 *     <li>getStateData - {@link #getStateData()}</li>
 *     <li>compareAndSwap - {@link #compareAndSwap(byte[], byte[])}</li>
 *     <li>Return to first step if CAS was unsuccessful</li>
 * </ol>
 */
public interface AsyncCompareAndSwapOperation {

    /**
     * Reads data if it exists
     *
     * @return persisted data or empty optional if data not exists
     */
    CompletableFuture<Optional<byte[]>> getStateData();

    /**
     * Compares and swap data associated with key
     *
     * @param originalData previous bucket state(can be null).
     * @param newData new bucket state
     *
     * @return {@code true} if data changed, {@code false} if another parallel transaction achieved success instead of current transaction
     */
    CompletableFuture<Boolean> compareAndSwap(byte[] originalData, byte[] newData);

}
