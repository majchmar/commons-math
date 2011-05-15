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
package org.apache.commons.math.geometry.euclidean.oneD;

import java.util.List;

import org.apache.commons.math.geometry.euclidean.oneD.Interval;
import org.apache.commons.math.geometry.euclidean.oneD.IntervalsSet;
import org.apache.commons.math.geometry.euclidean.oneD.Point1D;
import org.apache.commons.math.geometry.partitioning.Region;
import org.apache.commons.math.util.FastMath;
import org.junit.Assert;
import org.junit.Test;

public class IntervalsSetTest {

    @Test
    public void testInterval() {
        IntervalsSet set = new IntervalsSet(2.3, 5.7);
        Assert.assertEquals(3.4, set.getSize(), 1.0e-10);
        Assert.assertEquals(4.0, ((Point1D) set.getBarycenter()).getAbscissa(), 1.0e-10);
        Assert.assertEquals(Region.Location.BOUNDARY, set.checkPoint(new Point1D(2.3)));
        Assert.assertEquals(Region.Location.BOUNDARY, set.checkPoint(new Point1D(5.7)));
        Assert.assertEquals(Region.Location.OUTSIDE,  set.checkPoint(new Point1D(1.2)));
        Assert.assertEquals(Region.Location.OUTSIDE,  set.checkPoint(new Point1D(8.7)));
        Assert.assertEquals(Region.Location.INSIDE,   set.checkPoint(new Point1D(3.0)));
        Assert.assertEquals(2.3, set.getInf(), 1.0e-10);
        Assert.assertEquals(5.7, set.getSup(), 1.0e-10);
    }

    @Test
    public void testInfinite() {
        IntervalsSet set = new IntervalsSet(9.0, Double.POSITIVE_INFINITY);
        Assert.assertEquals(Region.Location.BOUNDARY, set.checkPoint(new Point1D(9.0)));
        Assert.assertEquals(Region.Location.OUTSIDE,  set.checkPoint(new Point1D(8.4)));
        for (double e = 1.0; e <= 6.0; e += 1.0) {
            Assert.assertEquals(Region.Location.INSIDE,
                                set.checkPoint(new Point1D(FastMath.pow(10.0, e))));
        }
        Assert.assertTrue(Double.isInfinite(set.getSize()));
        Assert.assertEquals(9.0, set.getInf(), 1.0e-10);
        Assert.assertTrue(Double.isInfinite(set.getSup()));

        set = (IntervalsSet) set.getComplement();
        Assert.assertEquals(9.0, set.getSup(), 1.0e-10);
        Assert.assertTrue(Double.isInfinite(set.getInf()));

    }

    @Test
    public void testMultiple() {
        IntervalsSet set = (IntervalsSet)
        Region.intersection(Region.union(Region.difference(new IntervalsSet(1.0, 6.0),
                                                           new IntervalsSet(3.0, 5.0)),
                                                           new IntervalsSet(9.0, Double.POSITIVE_INFINITY)),
                                                           new IntervalsSet(Double.NEGATIVE_INFINITY, 11.0));
        Assert.assertEquals(5.0, set.getSize(), 1.0e-10);
        Assert.assertEquals(5.9, ((Point1D) set.getBarycenter()).getAbscissa(), 1.0e-10);
        Assert.assertEquals(Region.Location.OUTSIDE,  set.checkPoint(new Point1D(0.0)));
        Assert.assertEquals(Region.Location.OUTSIDE,  set.checkPoint(new Point1D(4.0)));
        Assert.assertEquals(Region.Location.OUTSIDE,  set.checkPoint(new Point1D(8.0)));
        Assert.assertEquals(Region.Location.OUTSIDE,  set.checkPoint(new Point1D(12.0)));
        Assert.assertEquals(Region.Location.INSIDE,   set.checkPoint(new Point1D(1.2)));
        Assert.assertEquals(Region.Location.INSIDE,   set.checkPoint(new Point1D(5.9)));
        Assert.assertEquals(Region.Location.INSIDE,   set.checkPoint(new Point1D(9.01)));
        Assert.assertEquals(Region.Location.BOUNDARY, set.checkPoint(new Point1D(5.0)));
        Assert.assertEquals(Region.Location.BOUNDARY, set.checkPoint(new Point1D(11.0)));
        Assert.assertEquals( 1.0, set.getInf(), 1.0e-10);
        Assert.assertEquals(11.0, set.getSup(), 1.0e-10);

        List<Interval> list = set.asList();
        Assert.assertEquals(3, list.size());
        Assert.assertEquals( 1.0, list.get(0).getLower(), 1.0e-10);
        Assert.assertEquals( 3.0, list.get(0).getUpper(), 1.0e-10);
        Assert.assertEquals( 5.0, list.get(1).getLower(), 1.0e-10);
        Assert.assertEquals( 6.0, list.get(1).getUpper(), 1.0e-10);
        Assert.assertEquals( 9.0, list.get(2).getLower(), 1.0e-10);
        Assert.assertEquals(11.0, list.get(2).getUpper(), 1.0e-10);

    }

}
