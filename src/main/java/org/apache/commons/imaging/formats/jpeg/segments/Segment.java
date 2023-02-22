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
package org.apache.commons.imaging.formats.jpeg.segments;

import java.io.PrintWriter;

import org.apache.commons.imaging.common.BinaryFileParser;

import java.util.Map;
import java.util.Collections;
import java.util.HashMap;

class SegmentTypes {
    public static final Map<Integer, String> segmentTypesMap;
    static {
        Map<Integer, String> segmentTypesTemp = new HashMap<>();
        segmentTypesTemp.put(0xffc0, "Start Of Frame, Baseline Dct, Huffman coding");
        segmentTypesTemp.put(0xffc1, "Start Of Frame, Extended sequential Dct, Huffman coding");
        segmentTypesTemp.put(0xffc2, "Start Of Frame, Progressive Dct, Huffman coding");
        segmentTypesTemp.put(0xffc3, "Start Of Frame, Lossless (sequential), Huffman coding");

        segmentTypesTemp.put(0xffc5, "Start Of Frame, Differential sequential Dct, Huffman coding");
        segmentTypesTemp.put(0xffc6, "Start Of Frame, Differential progressive Dct, Huffman coding");
        segmentTypesTemp.put(0xffc7, "Start Of Frame, Differential lossless (sequential), Huffman coding");

        segmentTypesTemp.put(0xffc8, "Start Of Frame, Reserved for JPEG extensions, arithmetic coding");
        segmentTypesTemp.put(0xffc9, "Start Of Frame, Extended sequential Dct, arithmetic coding");
        segmentTypesTemp.put(0xffca, "Start Of Frame, Progressive Dct, arithmetic coding");
        segmentTypesTemp.put(0xffcb, "Start Of Frame, Lossless (sequential), arithmetic coding");

        segmentTypesTemp.put(0xffcd, "Start Of Frame, Differential sequential Dct, arithmetic coding");
        segmentTypesTemp.put(0xffce, "Start Of Frame, Differential progressive Dct, arithmetic coding");
        segmentTypesTemp.put(0xffcf, "Start Of Frame, Differential lossless (sequential), arithmetic coding");

        segmentTypesTemp.put(0xffc4, "Define Huffman table(s)");
        segmentTypesTemp.put(0xffcc, "Define arithmetic coding conditioning(s)");

        segmentTypesTemp.put(0xffd0, "Restart with modulo 8 count 0");
        segmentTypesTemp.put(0xffd1, "Restart with modulo 8 count 1");
        segmentTypesTemp.put(0xffd2, "Restart with modulo 8 count 2");
        segmentTypesTemp.put(0xffd3, "Restart with modulo 8 count 3");
        segmentTypesTemp.put(0xffd4, "Restart with modulo 8 count 4");
        segmentTypesTemp.put(0xffd5, "Restart with modulo 8 count 5");
        segmentTypesTemp.put(0xffd6, "Restart with modulo 8 count 6");
        segmentTypesTemp.put(0xffd7, "Restart with modulo 8 count 7");

        segmentTypesTemp.put(0xffd8, "Start of image");
        segmentTypesTemp.put(0xffd9, "End of image");
        segmentTypesTemp.put(0xffda, "Start of scan");
        segmentTypesTemp.put(0xffdb, "Define quantization table(s)");
        segmentTypesTemp.put(0xffdc, "Define number of lines");
        segmentTypesTemp.put(0xffdd, "Define restart interval");
        segmentTypesTemp.put(0xffde, "Define hierarchical progression");
        segmentTypesTemp.put(0xffdf, "Expand reference component(s)");

        segmentTypesTemp.put(0xfffe, "Comment");
        segmentTypesTemp.put(0xff01, "For temporary private use in arithmetic coding");
        segmentTypesMap = Collections.unmodifiableMap(segmentTypesTemp);
    }
}

public abstract class Segment extends BinaryFileParser {
    public final int marker;
    public final int length;

    public Segment(final int marker, final int length) {
        // super();

        this.marker = marker;
        this.length = length;
    }

    public void dump(final PrintWriter pw) {
        // empty
    }

    public abstract String getDescription();

    @Override
    public String toString() {
        return "[Segment: " + getDescription() + "]";
    }

    public String getSegmentType() {
        String type = SegmentTypes.segmentTypesMap.get(marker);

        if (type != null) {
            return type;
        }

        if ((marker >= 0xff02) && (marker <= 0xffbf)) {
            return "Reserved";
        }
        if ((marker >= 0xffe0) && (marker <= 0xffef)) {
            return "APP" + (marker - 0xffe0);
        }
        if ((marker >= 0xfff0) && (marker <= 0xfffd)) {
            return "JPG" + (marker - 0xffe0);
        }

        return "Unknown";

    }

}
