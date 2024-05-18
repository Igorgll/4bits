package com.bits.bits.util;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@UtilityClass
public class ImageCompressor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageCompressor.class);

    private static final float QUALITY = 0.65f;

    private static final String IMAGE_FORMAT = "jpeg";

    public static byte[] compressImage(byte[] imageBytes) throws IOException {
        LOGGER.info("Original image size: " + imageBytes.length + " bytes");
        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
        BufferedImage image = ImageIO.read(bais);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageOutputStream ios = ImageIO.createImageOutputStream(baos);

        ImageWriter writer = ImageIO.getImageWritersByFormatName(IMAGE_FORMAT).next();
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(QUALITY);
        }

        writer.write(null, new IIOImage(image, null, null), param);
        writer.dispose();
        ios.close();

        byte[] compressedImageBytes = baos.toByteArray();
        LOGGER.info("Compressed image size: " + compressedImageBytes.length + " bytes");

        return baos.toByteArray();
    }

}
