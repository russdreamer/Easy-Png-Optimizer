package main.java.com.toolittlespot.elements;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JpegElement extends ImageElement {


    public JpegElement(File file, int rowNumber) {
        super(file, rowNumber);
    }

    @Override
    public boolean optimize(ApplicationArea application) {
        JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
        jpegParams.setCompressionMode(ImageWriteParam.MODE_DEFAULT);
        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        BufferedImage image;
        try {
            image = ImageIO.read(new File(getTempFilePath()));
            Files.deleteIfExists(Paths.get(getTempFilePath()));
            jpgWriter.setOutput(new FileImageOutputStream( new File( getTempFilePath() )));
            jpgWriter.write(null, new IIOImage(image, null, null), jpegParams);

            return true;
        } catch (IOException e) {
            return false;
        }
        finally {
            jpgWriter.dispose();
        }
    }
}
