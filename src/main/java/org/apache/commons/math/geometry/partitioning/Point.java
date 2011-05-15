/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.math.geometry.partitioning;

/** This interface represents a generic point to be used in a space partition.
 * <p>Points are completely virtual entities with no specification at
 * all, so this class is essentially a marker interface with no
 * methods. This allows to perform partition in traditional euclidean
 * n-dimensions spaces, but also in more exotic universes like for
 * example the surface of the unit sphere.</p>
 * @version $Revision$ $Date$
 */
public interface Point {
    // nothing here, this is only a marker interface
}
