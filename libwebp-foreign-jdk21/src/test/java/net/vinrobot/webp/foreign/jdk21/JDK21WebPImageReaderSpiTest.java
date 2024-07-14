package net.vinrobot.webp.foreign.jdk21;

import net.vinrobot.webp.ImageReaderSpiTest;

import javax.imageio.spi.ImageReaderSpi;

class JDK21WebPImageReaderSpiTest extends ImageReaderSpiTest {
    @Override
    public Class<? extends ImageReaderSpi> getServiceClass() {
        return JDK21WebPImageReaderSpi.class;
    }

    @Override
    public ImageReaderSpi getService() {
        return new JDK21WebPImageReaderSpi();
    }
}
