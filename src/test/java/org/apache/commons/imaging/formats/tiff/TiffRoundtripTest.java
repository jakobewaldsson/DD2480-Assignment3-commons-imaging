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

package org.apache.commons.imaging.formats.tiff;

import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.TiffConstants;
import org.apache.commons.imaging.internal.Debug;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TiffRoundtripTest extends TiffBaseTest {

    @Test
    public void test() throws Exception {
        final List<File> images = getTiffImages();
        for (final File imageFile : images) {

            Debug.debug("imageFile", imageFile);

            final ImageMetadata metadata = Imaging.getMetadata(imageFile);
            assertNotNull(metadata);

            final ImageInfo imageInfo = Imaging.getImageInfo(imageFile);
            assertNotNull(imageInfo);

            final BufferedImage image = Imaging.getBufferedImage(imageFile);
            assertNotNull(image);

            final int[] compressions = {
                    TiffConstants.TIFF_COMPRESSION_UNCOMPRESSED,
                    TiffConstants.TIFF_COMPRESSION_LZW,
                    TiffConstants.TIFF_COMPRESSION_PACKBITS,
                    TiffConstants.TIFF_COMPRESSION_DEFLATE_ADOBE
            };
            final TiffImageParser tiffImageParser = new TiffImageParser();
            for (final int compression : compressions) {
                final File tempFile = Files.createTempFile(imageFile.getName() + "-" + compression + ".", ".tif").toFile();
                final TiffImagingParameters params = new TiffImagingParameters();
                params.setCompression(compression);
                try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                    tiffImageParser.writeImage(image, fos, params);
                }
                final BufferedImage image2 = Imaging.getBufferedImage(tempFile);
                assertNotNull(image2);
            }
        }
    }

}
