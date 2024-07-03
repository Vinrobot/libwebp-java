package net.vinrobot.webp.foreign.jdk21;

import net.vinrobot.webp.WebPDecoderException;
import net.vinrobot.webp.WebPReader;
import net.vinrobot.webp.WebPReaderSpi;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class JDK21WebPReaderSpi implements WebPReaderSpi {
    @Override
    public WebPReader newReader(final URL url) throws IOException, WebPDecoderException {
        final URLConnection connection = url.openConnection();
        try (final InputStream inputStream = connection.getInputStream()) {
            return newReader(inputStream);
        }
    }

    @Override
    public WebPReader newReader(final InputStream inputStream) throws IOException, WebPDecoderException {
        return newReader(inputStream.readAllBytes());
    }

    @Override
    public WebPReader newReader(final byte[] buffer, final int offset, final int length) throws IOException, WebPDecoderException {
        return new JDK21WebPReader(buffer, offset, length);
    }
}
