package net.vinrobot.webp.foreign.jdk21;

import net.vinrobot.webp.WebPReaderSpi;
import net.vinrobot.webp.WebPReaderTest;
import org.junit.jupiter.api.BeforeAll;

public class JDK21WebPReaderTest extends WebPReaderTest {
    static JDK21WebPReaderSpi spi;

    @BeforeAll
    static void setUp() {
        spi = new JDK21WebPReaderSpi();
    }

    @Override
    public WebPReaderSpi getService() {
        return spi;
    }
}
