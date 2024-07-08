package net.vinrobot.webp;

import java.net.URL;
import java.util.stream.Stream;

public final class TestResources {
    public static Stream<TestImage> getTestImages() {
        return getLocalTestImages();
    }

    public static Stream<TestImage> getLocalTestImages() {
        return Stream.of(
            new TestImage(getResource("/images/test.webp"), 16, 16, 1, new int[]{480, 1280})
        );
    }

    public static URL getResource(String name) {
        return TestResources.class.getResource(name);
    }

    public record TestImage(
        URL resource,
        int width,
        int height,
        int loopCount,
        TestFrame[] frames
    ) {
        public TestImage(URL resource, int width, int height, int loopCount, int[] delays) {
            this(resource, width, height, loopCount, new TestFrame[delays.length]);
            int timestamp = 0;
            for (int i = 0; i < delays.length; i++) {
                timestamp += delays[i];
                this.frames[i] = new TestFrame(width, height, timestamp);
            }
        }
    }

    public record TestFrame(
        int width,
        int height,
        int timestamp
    ) {
    }
}
