package net.vinrobot.webp;

import java.io.Closeable;

public interface WebPReader extends Closeable {
    WebPMetadata getMetadata() throws WebPDecoderException;

    boolean hasMoreFrame() throws WebPDecoderException;

    WebPFrame nextFrame() throws WebPDecoderException;
}
