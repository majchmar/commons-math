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
package org.apache.commons.math.exception;

import java.util.Locale;
import java.util.Arrays;

import org.apache.commons.math.exception.util.LocalizedFormats;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link MathRuntimeException}.
 * 
 * @version $Id$
 */
public class MathRuntimeExceptionTest {
    @Test
    public void testMessageChain() {
        final MathRuntimeException mre = new MathRuntimeException();
        final String sep = ": ";
        final String m1 = "column index (0)";
        mre.addMessage(LocalizedFormats.COLUMN_INDEX, 0);
        final String m2 = "got 1x2 but expected 3x4";
        mre.addMessage(LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 1, 2, 3, 4);
        final String m3 = "It didn't work out";

        try {
            try {
                throw mre;
            } catch (MathRuntimeException e) {
                e.addMessage(LocalizedFormats.SIMPLE_MESSAGE, m3);
                throw e;
            }
        } catch (MathRuntimeException e) {
            Assert.assertEquals(e.getMessage(Locale.US, sep),
                                m1 + sep + m2 + sep + m3);
        }
    }

    @Test
    public void testContext() {
        final MathRuntimeException mre = new MathRuntimeException();

        final String[] keys = {"Key 1", "Key 2"};
        final Object[] values = {"Value 1", Integer.valueOf(2)};

        for (int i = 0; i < keys.length; i++) {
            mre.setContext(keys[i], values[i]);
        }

        // Check that all keys are present.
        Assert.assertTrue(mre.getContextKeys().containsAll(Arrays.asList(keys)));

        // Check that all values are correctly stored.
        for (int i = 0; i < keys.length; i++) {
            Assert.assertEquals(values[i], mre.getContext(keys[i]));
        }

        // Check behaviour on missing key.
        Assert.assertNull(mre.getContext("xyz"));
    }
}
