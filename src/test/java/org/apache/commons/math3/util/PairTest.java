/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.apache.commons.math3.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link Pair}.
 */
public class PairTest {

    @Test
    public void testAccessor() {
        final Pair<Integer, Double> p
            = new Pair<Integer, Double>(new Integer(1), new Double(2));
        Assert.assertEquals(new Integer(1), p.getKey());
        Assert.assertEquals(new Double(2), p.getValue(), Math.ulp(1d));
    }

    @Test
    public void testEquals() {
        Pair<Integer, Double> p1 = new Pair<Integer, Double>(null, null);
        Assert.assertFalse(p1.equals(null));

        Pair<Integer, Double> p2 = new Pair<Integer, Double>(null, null);
        Assert.assertTrue(p1.equals(p2));

        p1 = new Pair<Integer, Double>(new Integer(1), new Double(2));
        Assert.assertFalse(p1.equals(p2));

        p2 = new Pair<Integer, Double>(new Integer(1), new Double(2));
        Assert.assertTrue(p1.equals(p2));

        Pair<Integer, Float> p3 = new Pair<Integer, Float>(new Integer(1), new Float(2));
        Assert.assertFalse(p1.equals(p3));
    }

    @Test
    public void testHashCode() {
        final MyInteger m1 = new MyInteger(1);
        final MyInteger m2 = new MyInteger(1);

        final Pair<MyInteger, MyInteger> p1 = new Pair<MyInteger, MyInteger>(m1, m1);
        final Pair<MyInteger, MyInteger> p2 = new Pair<MyInteger, MyInteger>(m2, m2);
        // Same contents, same hash code.
        Assert.assertTrue(p1.hashCode() == p2.hashCode());

        // Different contents, different hash codes.
        m2.set(2);
        Assert.assertFalse(p1.hashCode() == p2.hashCode());

        // Test cache.

        final MyInteger m3 = new MyInteger(1);
        final Pair<MyInteger, MyInteger> p3 = new Pair<MyInteger, MyInteger>(m3, m3, true);
        final int hC3 = p3.hashCode();
        // Contents change will not affect the hash code because it is cached.
        m3.set(3);
        Assert.assertTrue(hC3 == p3.hashCode());

        final Pair<MyInteger, MyInteger> p4 = new Pair<MyInteger, MyInteger>(p3, false);
        // p3 and p4 do not have the same hash code because p4 was contructed after m3
        // was changed.
        Assert.assertFalse(p4.hashCode() == p3.hashCode());
        final int hC4 = p4.hashCode();
        // Contents change will affect the hash code because it is not cached.
        m3.set(4);
        Assert.assertFalse(hC4 == p4.hashCode());
    }

    /**
     * A mutable integer.
     */
    private static class MyInteger {
        private int i;

        public MyInteger(int i) {
            this.i = i;
        }

        public void set(int i) {
            this.i = i;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (!(o instanceof MyInteger)) {
                return false;
            } else {
                return i == ((MyInteger) o).i;
            }
        }

        @Override
        public int hashCode() {
            return i;
        }
    }
}
