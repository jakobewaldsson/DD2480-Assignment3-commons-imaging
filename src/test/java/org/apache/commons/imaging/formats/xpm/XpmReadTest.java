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
package org.apache.commons.imaging.formats.xpm;

import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.BasicCParser;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.decoder.JpegDecoder;
import org.apache.commons.imaging.internal.Debug;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class XpmReadTest extends XpmBaseTest {

    @Test
    public void test() throws Exception {
        Debug.debug("start");

        final List<File> images = getXpmImages();
        for (final File imageFile : images) {

            Debug.debug("imageFile", imageFile);

            final ImageMetadata metadata = Imaging.getMetadata(imageFile);
            Assertions.assertFalse(metadata instanceof File); // Dummy check to avoid unused warning (it may be null)

            final ImageInfo imageInfo = Imaging.getImageInfo(imageFile);
            assertNotNull(imageInfo);

            final BufferedImage image = Imaging.getBufferedImage(imageFile);
            assertNotNull(image);
        }
    }

    /**
     * Invokes a exception in the parseNextString function in XpmImageParser.java. This exception gets thrown when
     * the cParser has invalid xpm format which is when the byte array is not starting with ". This exception is then
     * asserted through comparing which exception is thrown and the description.
     */
    @Test
    public void exceptionParseNextString(){
        XpmImageParser.XpmHeader header = new XpmImageParser.XpmHeader( 1,  1, 2,
         1,  1,  1,  true);
        byte[] buf = {95, 34}; // _ , "
        ByteArrayInputStream byt = new ByteArrayInputStream(buf);
        BasicCParser cParser = new BasicCParser(byt);
        Exception exception = assertThrows(ImageReadException.class, () -> {
            new XpmImageParser().parsePaletteEntries(header, cParser);
                });
        String expectedMessage = "Parsing XPM file failed, no string found where expected";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        Assertions.assertThrows(ImageReadException.class, () -> new XpmImageParser().parsePaletteEntries(header, cParser));
    }
    /**
     * Invokes a exception in the NextToken function in BasicCParser.java. This exception gets thrown when
     * the cParser has invalid xpm format which is when the byte array is not ending with a ". This exception is then
     * asserted through comparing which exception is thrown and the description.
     */
    @Test
    public void exceptionNextToken() {
        XpmImageParser xpm = new XpmImageParser();
        XpmImageParser.XpmHeader header = new XpmImageParser.XpmHeader( 1,  1, 2,
                1,  1,  1,  true);
        String str = "\"abba";
        byte[] test = str.getBytes();
        ByteArrayInputStream byt = new ByteArrayInputStream(test);
        BasicCParser cParser = new BasicCParser(byt);
        Exception exception = assertThrows(ImageReadException.class, () -> {
            new XpmImageParser().parsePaletteEntries(header, cParser);
        });
        String expectedMessage = "Unterminated string ends XMP file";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    /**
     * Invokes a exception in the parsePaletteEntries function in XpmImageParser.java. This exception gets thrown when
     * the there only exist 1 string as the byte[] input for the cParser. This exception is then
     * asserted through comparing which exception is thrown and the description.
     */
    @Test
    public void exceptionParsePaletteEntries()  {
        XpmImageParser.XpmHeader header = new XpmImageParser.XpmHeader( 1,  1, 2,
                1,  1,  1,  true);
        String str = "\"abba\"}";
        byte[] test = str.getBytes();
        ByteArrayInputStream byt = new ByteArrayInputStream(test);
        BasicCParser cParser = new BasicCParser(byt);
        Exception exception = assertThrows(ImageReadException.class, () -> {
            new XpmImageParser().parsePaletteEntries(header, cParser);
        });
        String expectedMessage = "Parsing XPM file failed, " + "file ended while reading palette";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    /**
     * Checks wheter a break branch is used in the parsePaletteEntries function. This is done by avoiding the isKey boolean
     * in the function, where "m","g4","g","c" and "s" can't be used in the input byte[] of the cParser. This gets asserted
     * through usage of a global boolean which is set to true when
     */
    @Test
    public void CheckParsePaletteEntriesBranch() throws IOException, ImageReadException {
        XpmImageParser xpm = new XpmImageParser();
        XpmImageParser.XpmHeader header = new XpmImageParser.XpmHeader( 2,  1, 1,
                1,  1,  1,  true);
        String str = "\"ccc\" , \"___\"}";
        byte[] test = str.getBytes();
        ByteArrayInputStream byt = new ByteArrayInputStream(test);
        BasicCParser cParser = new BasicCParser(byt);
        assertFalse(xpm.testVariable);
        xpm.parsePaletteEntries(header, cParser);
        assertTrue(xpm.testVariable);
    }
}
