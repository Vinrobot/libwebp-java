package net.vinrobot.webp.jna;

import net.vinrobot.webp.WebPDecoderException;
import net.vinrobot.webp.WebPFrame;
import net.vinrobot.webp.WebPMetadata;
import net.vinrobot.webp.WebPReader;
import net.vinrobot.webp.jna.internal.LibWebP;
import net.vinrobot.webp.jna.internal.Loader;
import net.vinrobot.webp.jna.internal.WebPAnimDecoder;
import net.vinrobot.webp.jna.internal.WebPData;

public class JNAWebPReader implements WebPReader {
    private static final LibWebP LIBWEBP = Loader.getInstance();

    private final WebPData data;
    private final WebPAnimDecoder decoder;

    public JNAWebPReader(final byte[] buffer, final int offset, final int length) {
        this.data = new WebPData(LIBWEBP, buffer, offset, length);
        this.decoder = new WebPAnimDecoder(LIBWEBP, this.data);
    }

    @Override
    public WebPMetadata getMetadata() throws WebPDecoderException {
        return this.decoder.getInfo();
    }

    @Override
    public boolean hasMoreFrame() {
        return this.decoder.hasMoreFrames();
    }

    @Override
    public WebPFrame nextFrame() throws WebPDecoderException {
        return decoder.getNext(decoder.getInfo());
    }

    @Override
    public void close() {
        this.data.close();
        this.decoder.close();
    }
}
