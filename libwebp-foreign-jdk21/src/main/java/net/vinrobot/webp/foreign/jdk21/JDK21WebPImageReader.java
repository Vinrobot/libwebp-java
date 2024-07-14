package net.vinrobot.webp.foreign.jdk21;

import net.vinrobot.webp.WebPReader;
import net.vinrobot.webp.imageio.BaseImageReader;

import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class JDK21WebPImageReader extends BaseImageReader {
    private static final int DEFAULT_BUFFER_SIZE = 16384;

    public JDK21WebPImageReader(final ImageReaderSpi originatingProvider) {
        super(originatingProvider);
    }

    private static byte[] readAll(final ImageInputStream stream) throws IOException {
        try (final ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = stream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toByteArray();
        }
    }

    @Override
    protected WebPReader getReader(final ImageInputStream stream) throws IOException {
        return this.getReader(readAll(stream));
    }

    @Override
    protected WebPReader getReader(final byte[] data) throws IOException {
        return new JDK21WebPReader(data, 0, data.length);
    }
}
