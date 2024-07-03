package net.vinrobot.webp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface WebPReaderSpi {
    WebPReader newReader(final URL url) throws IOException, WebPDecoderException;

    default WebPReader newReader(final byte[] buffer) throws IOException, WebPDecoderException {
        return newReader(buffer, 0, buffer.length);
    }

    WebPReader newReader(final byte[] buffer, final int offset, final int length) throws IOException, WebPDecoderException;

    WebPReader newReader(final InputStream inputStream) throws IOException, WebPDecoderException;
}
