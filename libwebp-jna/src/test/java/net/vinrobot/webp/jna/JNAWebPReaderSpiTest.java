package net.vinrobot.webp.jna;

import net.vinrobot.webp.WebPReaderSpi;
import net.vinrobot.webp.WebPReaderSpiTest;
import org.junit.jupiter.api.BeforeAll;

class JNAWebPReaderSpiTest extends WebPReaderSpiTest {
    static JNAWebPReaderSpi spi;

    @BeforeAll
    static void setUp() {
        spi = new JNAWebPReaderSpi();
    }

    @Override
    public Class<? extends WebPReaderSpi> getServiceClass() {
        return JNAWebPReaderSpi.class;
    }

    @Override
    public WebPReaderSpi getService() {
        return spi;
    }
}
