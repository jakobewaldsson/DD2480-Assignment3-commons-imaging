package org.apache.commons.imaging.common;

import java.awt.image.DirectColorModel;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryFunctionsTest{

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
