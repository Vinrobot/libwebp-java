package net.vinrobot.webp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class WebPReaderTest {
    public abstract WebPReaderSpi getService();

    @ParameterizedTest
    @MethodSource("net.vinrobot.webp.TestResources#getTestImages")
    public void decode(TestResources.TestImage testData) throws IOException, WebPDecoderException {
        // GIVEN
        final WebPReaderSpi service = getService();
        final URL imageUrl = testData.resource();

        // WHEN
        final List<WebPFrame> frames = new ArrayList<>();
        final WebPMetadata metadata;
        try (final WebPReader reader = service.newReader(imageUrl)) {
            metadata = reader.getMetadata();
            while (reader.hasMoreFrame()) {
                frames.add(reader.nextFrame());
            }
        }

        // THEN
        assertEquals(testData.width(), metadata.canvasWidth());
        assertEquals(testData.height(), metadata.canvasHeight());
        assertEquals(testData.loopCount(), metadata.loopCount());

        final TestResources.TestFrame[] expectedFrames = testData.frames();
        assertEquals(expectedFrames.length, metadata.frameCount());
        assertEquals(expectedFrames.length, frames.size());

        for (int i = 0; i < expectedFrames.length; ++i) {
            final TestResources.TestFrame expectedFrame = expectedFrames[i];
            final WebPFrame actualFrame = frames.get(i);

            assertEquals(expectedFrame.width(), actualFrame.width());
            assertEquals(expectedFrame.height(), actualFrame.height());
            assertEquals(expectedFrame.timestamp(), actualFrame.timestamp());
        }
    }
}
