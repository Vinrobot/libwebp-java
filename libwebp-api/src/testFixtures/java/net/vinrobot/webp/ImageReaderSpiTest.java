package net.vinrobot.webp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.imageio.ImageIO;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class ImageReaderSpiTest {

    public abstract Class<? extends ImageReaderSpi> getServiceClass();

    public abstract ImageReaderSpi getService();

    @Test
    void serviceLoader() {
        // GIVEN
        final Class<? extends ImageReaderSpi> serviceClass = getServiceClass();
        final ServiceLoader<ImageReaderSpi> serviceLoader = ServiceLoader.load(ImageReaderSpi.class);

        // WHEN
        final boolean found = serviceLoader.stream()
            .map(ServiceLoader.Provider::type)
            .anyMatch(serviceClass::equals);

        // THEN
        assertTrue(found);
    }

    @ParameterizedTest
    @MethodSource("net.vinrobot.webp.TestResources#getTestImages")
    void canDecodeByteArray(final TestResources.TestImage testData) throws IOException {
        // GIVEN
        final ImageReaderSpi serviceProvider = getService();
        final byte[] imageData;
        try (final InputStream inputStream = testData.resource().openStream()) {
            imageData = inputStream.readAllBytes();
        }

        // WHEN
        final boolean canDecode = serviceProvider.canDecodeInput(imageData);

        // THEN
        assertTrue(canDecode);
    }

    @ParameterizedTest
    @MethodSource("net.vinrobot.webp.TestResources#getTestImages")
    void canDecodeImageInputStream(final TestResources.TestImage testData) throws IOException {
        // GIVEN
        try (final InputStream inputStream = testData.resource().openStream();
             final ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream)) {
            final ImageReaderSpi imageReaderSpi = getService();

            // WHEN
            final boolean canDecode = imageReaderSpi.canDecodeInput(imageInputStream);

            // THEN
            assertTrue(canDecode);
        }
    }
}
