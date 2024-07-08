package net.vinrobot.webp.jna;

import net.vinrobot.webp.WebPReaderSpi;
import net.vinrobot.webp.WebPReaderTest;
import org.junit.jupiter.api.BeforeAll;

public class JNAWebPReaderTest extends WebPReaderTest {
    static JNAWebPReaderSpi spi;

    @BeforeAll
    static void setUp() {
        spi = new JNAWebPReaderSpi();
    }

    @Override
    public WebPReaderSpi getService() {
        return spi;
    }
}
