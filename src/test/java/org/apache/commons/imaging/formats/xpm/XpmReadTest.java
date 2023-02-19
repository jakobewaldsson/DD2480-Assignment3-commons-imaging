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
    @Test
    public void exceptionParseNextString(){
        //parsePaletteEntries(final XpmImageParser.XpmHeader xpmHeader, final BasicCParser cParser)
        //XpmImageParser xpm = new XpmImageParser();
        XpmImageParser.XpmHeader header = new XpmImageParser.XpmHeader( 1,  1, 2,
         1,  1,  1,  true);
        byte[] buf = {95, 34}; // _ , "
        ByteArrayInputStream byt = new ByteArrayInputStream(buf);
        BasicCParser cParser = new BasicCParser(byt);
        //xpm.parsePaletteEntries(header, cParser);
        Exception exception = assertThrows(ImageReadException.class, () -> {
            new XpmImageParser().parsePaletteEntries(header, cParser);
                });
        String expectedMessage = "Parsing XPM file failed, no string found where expected";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        Assertions.assertThrows(ImageReadException.class, () -> new XpmImageParser().parsePaletteEntries(header, cParser));
    }
    @Test
    public void exceptionParsePaletteEntries() throws IOException, ImageReadException {
        //parsePaletteEntries(final XpmImageParser.XpmHeader xpmHeader, final BasicCParser cParser)
        //XpmImageParser xpm = new XpmImageParser();
        XpmImageParser.XpmHeader header = new XpmImageParser.XpmHeader( 1,  1, 2,
                1,  1,  1,  true);
        String str = "\"abba\"}";
        byte[] test = str.getBytes();
        ByteArrayInputStream byt = new ByteArrayInputStream(test);
        BasicCParser cParser = new BasicCParser(byt);
        //xpm.parsePaletteEntries(header, cParser);
        Exception exception = assertThrows(ImageReadException.class, () -> {
            new XpmImageParser().parsePaletteEntries(header, cParser);
        });
        String expectedMessage = "Parsing XPM file failed, " + "file ended while reading palette";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        //Assertions.assertThrows(ImageReadException.class, () -> new XpmImageParser().parsePaletteEntries(header, cParser));
    }
}
