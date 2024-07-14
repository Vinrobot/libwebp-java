package net.vinrobot.webp.jna;

import net.vinrobot.webp.ImageReaderTest;

import javax.imageio.ImageReader;
import java.io.IOException;

class JNAWebPImageReaderTest extends ImageReaderTest {
    @Override
    public Class<? extends ImageReader> getImageReaderClass() {
        return JNAWebPImageReader.class;
    }

    @Override
    public ImageReader newImageReader() {
        try {
            return new JNAWebPImageReaderSpi().createReaderInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
