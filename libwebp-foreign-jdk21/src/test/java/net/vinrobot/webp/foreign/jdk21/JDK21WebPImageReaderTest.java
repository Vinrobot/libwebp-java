package net.vinrobot.webp.foreign.jdk21;

import net.vinrobot.webp.ImageReaderTest;

import javax.imageio.ImageReader;
import java.io.IOException;

class JDK21WebPImageReaderTest extends ImageReaderTest {
    @Override
    public Class<? extends ImageReader> getImageReaderClass() {
        return JDK21WebPImageReader.class;
    }

    @Override
    public ImageReader newImageReader() {
        try {
            return new JDK21WebPImageReaderSpi().createReaderInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
