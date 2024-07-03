package net.vinrobot.webp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public final class WebPDecoder {
    private WebPDecoder() {
    }

    public static WebPImage readAll(final URL url) throws IOException, WebPDecoderException {
        final WebPReaderSpi serviceProvider = getFirstServiceProvider();
        try (final WebPReader reader = serviceProvider.newReader(url)) {
            return readAll(reader);
        }
    }

    public static WebPImage readAll(final byte[] buffer) throws IOException, WebPDecoderException {
        final WebPReaderSpi serviceProvider = getFirstServiceProvider();
        try (final WebPReader reader = serviceProvider.newReader(buffer)) {
            return readAll(reader);
        }
    }

    public static WebPImage readAll(final byte[] buffer, final int offset, final int length) throws IOException, WebPDecoderException {
        final WebPReaderSpi serviceProvider = getFirstServiceProvider();
        try (final WebPReader reader = serviceProvider.newReader(buffer, offset, length)) {
            return readAll(reader);
        }
    }

    public static WebPImage readAll(final InputStream inputStream) throws IOException, WebPDecoderException {
        final WebPReaderSpi serviceProvider = getFirstServiceProvider();
        try (final WebPReader reader = serviceProvider.newReader(inputStream)) {
            return readAll(reader);
        }
    }

    private static WebPReaderSpi getFirstServiceProvider() {
        final ServiceLoader<WebPReaderSpi> serviceLoader = ServiceLoader.load(WebPReaderSpi.class);
        return serviceLoader.findFirst().orElseThrow();
    }

    private static WebPImage readAll(final WebPReader reader) throws WebPDecoderException {
        final List<WebPFrame> frames = new ArrayList<>();
        final WebPMetadata metadata = reader.getMetadata();
        while (reader.hasMoreFrame()) {
            frames.add(reader.nextFrame());
        }
        return new WebPImage(frames, metadata);
    }
}
