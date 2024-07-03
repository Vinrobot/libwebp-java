package net.vinrobot.webp.foreign.jdk21;

import net.vinrobot.webp.WebPReaderSpi;
import net.vinrobot.webp.WebPReaderSpiTest;
import org.junit.jupiter.api.BeforeAll;

class JDK21WebPReaderSpiTest extends WebPReaderSpiTest {
    static JDK21WebPReaderSpi spi;

    @BeforeAll
    static void setUp() {
        spi = new JDK21WebPReaderSpi();
    }

    @Override
    public Class<? extends WebPReaderSpi> getServiceClass() {
        return JDK21WebPReaderSpi.class;
    }

    @Override
    public WebPReaderSpi getService() {
        return spi;
    }
}
