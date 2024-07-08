package net.vinrobot.webp;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;
import java.util.ServiceLoader;

import static net.vinrobot.webp.TestResources.getLocalTestImages;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class WebPReaderSpiTest {
    public URL getTestResource() {
        return getLocalTestImages().findFirst().orElseThrow().resource();
    }

    public abstract Class<? extends WebPReaderSpi> getServiceClass();

    public abstract WebPReaderSpi getService();

    @Test
    void serviceLoader() {
        // GIVEN
        final Class<? extends WebPReaderSpi> serviceClass = getServiceClass();
        final ServiceLoader<WebPReaderSpi> serviceLoader = ServiceLoader.load(WebPReaderSpi.class);

        // WHEN
        final boolean found = serviceLoader.stream()
            .map(ServiceLoader.Provider::type)
            .anyMatch(serviceClass::equals);

        // THEN
        assertTrue(found);
    }

    @Test
    void newReaderURL() throws WebPDecoderException, IOException {
        // GIVEN
        final WebPReaderSpi service = getService();
        final URL url = getTestResource();

        // WHEN
        try (final WebPReader reader = service.newReader(url)) {
            // THEN
            assertNotNull(reader);
            assertNotNull(reader.getMetadata());
            assertTrue(reader.hasMoreFrame());
        }
    }

    @Test
    void newReaderInputStream() throws WebPDecoderException, IOException {
        // GIVEN
        final WebPReaderSpi service = getService();
        try (final InputStream inputStream = getTestResource().openStream()) {
            // WHEN
            try (final WebPReader reader = service.newReader(inputStream)) {
                // THEN
                assertNotNull(reader);
                assertNotNull(reader.getMetadata());
                assertTrue(reader.hasMoreFrame());
            }
        }
    }

    @Test
    void newReaderBuffer1() throws WebPDecoderException, IOException {
        // GIVEN
        final WebPReaderSpi service = getService();
        final byte[] data;
        try (final InputStream is = getTestResource().openStream()) {
            data = is.readAllBytes();
        }

        // WHEN
        try (final WebPReader reader = service.newReader(data)) {
            // THEN
            assertNotNull(reader);
            assertNotNull(reader.getMetadata());
            assertTrue(reader.hasMoreFrame());
        }
    }

    @Test
    void newReaderBuffer2() throws WebPDecoderException, IOException {
        // GIVEN
        final WebPReaderSpi service = getService();
        final byte[] data;
        final int offset;
        final int length;
        try (final InputStream is = getTestResource().openStream()) {
            byte[] tmp = is.readAllBytes();
            offset = new Random().nextInt(tmp.length / 4, tmp.length * 3 / 4);
            length = tmp.length;
            data = new byte[offset + length];
            System.arraycopy(tmp, 0, data, offset, length);
        }

        // WHEN
        try (final WebPReader reader = service.newReader(data, offset, length)) {
            // THEN
            assertNotNull(reader);
            assertNotNull(reader.getMetadata());
            assertTrue(reader.hasMoreFrame());
        }
    }
}
