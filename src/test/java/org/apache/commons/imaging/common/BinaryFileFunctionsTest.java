/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package org.apache.commons.imaging.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;

import org.apache.commons.imaging.ImagingTest;
import org.junit.jupiter.api.Test;

public class BinaryFileFunctionsTest extends ImagingTest {

    @Test
    public void testFloatToByteConversion() {
        final byte[] bytesLE = ByteConversions.toBytes(1.0f, ByteOrder.LITTLE_ENDIAN);
        assertEquals(ByteConversions.toFloat(bytesLE, ByteOrder.LITTLE_ENDIAN), 1.0f, 0f);

        final byte[] bytesBE = ByteConversions.toBytes(1.0f, ByteOrder.BIG_ENDIAN);
        assertEquals(ByteConversions.toFloat(bytesBE, ByteOrder.BIG_ENDIAN), 1.0f, 0f);
    }

    @Test
    public void testDoubleToByteConversion() {
        final byte[] bytesLE = ByteConversions.toBytes(1.0, ByteOrder.LITTLE_ENDIAN);
        assertEquals(ByteConversions.toDouble(bytesLE, ByteOrder.LITTLE_ENDIAN), 1.0, 0);

        final byte[] bytesBE = ByteConversions.toBytes(1.0, ByteOrder.BIG_ENDIAN);
        assertEquals(ByteConversions.toDouble(bytesBE, ByteOrder.BIG_ENDIAN), 1.0, 0);
    }
    @Test
    public void testStartWithsNormal(){
        byte[] haystack = new byte[8];
        byte[] needle = new byte[1];
        haystack[0] = 1;
        needle[0] = 1;
        assert BinaryFunctions.startsWith(haystack,needle) == true;
        needle[0] = 0;
        assert BinaryFunctions.startsWith(haystack,needle) == false;
        needle = new byte[9];
        needle[0] = 1;
        assert BinaryFunctions.startsWith(haystack,needle) == false;
    }
    @Test
    public void testStarWithsNull(){
        byte[] haystack = new byte[8];
        byte[] needle = new byte[1];
        assert BinaryFunctions.startsWith(null,needle) == false;
        needle = null;
        assert BinaryFunctions.startsWith(haystack,needle) == false;
    }
    @Test
    public void testRead3Bytes(){
        byte[] byteArrayBig = { 0, 1, 2 };
        InputStream is = new ByteArrayInputStream(byteArrayBig);
        try {
            assert(BinaryFunctions.read3Bytes("Unused",is,"exception", ByteOrder.BIG_ENDIAN)==258);
        }catch (IOException exception){
            fail();
        }
        byte[] byteArrayLittle = { 2, 1, 0 };
        is = new ByteArrayInputStream(byteArrayLittle);
        try {
            assert(BinaryFunctions.read3Bytes("Unused",is,"exception", ByteOrder.LITTLE_ENDIAN)==258);
        }catch (IOException exception){
            fail();
        }

    }
    @Test
    public void testSearchQuad(){
        byte[] byteArray = {0,0,0,0};
        InputStream is = new ByteArrayInputStream(byteArray);
        try{
            assert(BinaryFunctions.searchQuad(0,is)==true);
        }catch (IOException exception){
            fail();
        }

    }
}
