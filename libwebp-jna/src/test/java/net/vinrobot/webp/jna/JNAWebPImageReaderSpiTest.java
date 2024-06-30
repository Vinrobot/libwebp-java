package net.vinrobot.webp.jna;

import net.vinrobot.webp.ImageReaderSpiTest;

import javax.imageio.spi.ImageReaderSpi;

class JNAWebPImageReaderSpiTest extends ImageReaderSpiTest {
    @Override
    public Class<? extends ImageReaderSpi> getServiceClass() {
        return JNAWebPImageReaderSpi.class;
    }

    @Override
    public ImageReaderSpi getService() {
        return new JNAWebPImageReaderSpi();
    }
}
