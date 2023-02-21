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
package org.apache.commons.imaging.color;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ColorCieLabTest {

    private ColorCieLab color;
    private ColorCieLab colorCopy;

    private ColorDin99Lab colorDin99;
    private ColorDin99Lab colorDin99Copy;
    private ColorDin99Lab color_L99;
    private ColorDin99Lab color_a99;
    private ColorDin99Lab color_b99;

    @BeforeEach
    public void setUp() {
        color = new ColorCieLab(1.0, 2.0, 3.0);
        colorCopy = new ColorCieLab(1.0, 2.0, 3.0);
        colorDin99 = new ColorDin99Lab(0.1, 0.2, 0.3);
        colorDin99Copy = new ColorDin99Lab(0.1, 0.2, 0.3);
        color_L99 = new ColorDin99Lab(0.5, 0.2, 0.3);
        color_a99 = new ColorDin99Lab(0.1, 0.5, 0.3);
        color_b99 = new ColorDin99Lab(0.1, 0.2, 0.5);
    }

    @Test
    public void testLAssignment() {
        assertEquals(1.0, color.L, 0.0);
    }

    @Test
    public void testAAssignment() {
        assertEquals(2.0, color.a, 0.0);
    }

    @Test
    public void testBAssignment() {
        assertEquals(3.0, color.b, 0.0);
    }

    @Test
    public void testToString() {
        assertEquals("{L: 1.0, a: 2.0, b: 3.0}", color.toString());
    }

    @Test
    public void testHashCodeAndEquals() {
        assertTrue(color.equals(colorCopy) && colorCopy.equals(color));
        assertThat(color.hashCode(), is(colorCopy.hashCode()));
    }

    @Test
    public void testl99Assignment() {
        assertEquals(0.1, colorDin99.L99, 0.0);
    }

    @Test
    public void testa99Assignment() {
        assertEquals(0.2, colorDin99.a99, 0.0);
    }

    @Test
    public void testb99Assignment() {
        assertEquals(0.3, colorDin99.b99, 0.0);
    }

    @Test
    public void testToStringDin99() {
        assertEquals("{L: 0.1, a: 0.2, b: 0.3}", colorDin99.toString());
    }

    @Test
    public void testEquals() {
        assertTrue(colorDin99.equals(colorDin99Copy));
        assertTrue(colorDin99.equals(colorDin99));
        assertFalse(colorDin99.equals(color_L99));
        assertFalse(colorDin99.equals(color_a99));
        assertFalse(colorDin99.equals(color_b99));
        assertFalse(colorDin99.equals(null));

    }

    @Test
    public void testHashCode() {
        assertThat(colorDin99.hashCode(), is(colorDin99Copy.hashCode()));
    }

}
