package net.vinrobot.webp.foreign.jdk21;

import net.vinrobot.webp.WebPDecoderException;
import net.vinrobot.webp.WebPFrame;
import net.vinrobot.webp.WebPMetadata;
import net.vinrobot.webp.WebPReader;
import net.vinrobot.webp.foreign.jdk21.internal.Loader;
import net.vinrobot.webp.foreign.jdk21.internal.WebPAnimDecoder;
import net.vinrobot.webp.foreign.jdk21.internal.WebPAnimInfo;
import net.vinrobot.webp.foreign.jdk21.internal.WebPData;

import java.lang.foreign.Arena;

public class JDK21WebPReader implements WebPReader {
    private final Arena arena;
    private final WebPData data;
    private final WebPAnimDecoder decoder;
    private WebPMetadata metadata;

    public JDK21WebPReader(final byte[] buffer, final int offset, final int length) {
        Loader.ensureLibrary();

        this.arena = Arena.ofConfined();
        this.data = new WebPData(this.arena, buffer, offset, length);
        this.decoder = new WebPAnimDecoder(this.arena, this.data);
    }

    @Override
    public WebPMetadata getMetadata() throws WebPDecoderException {
        return this.metadata != null ? this.metadata : getMetadata0();
    }

    private synchronized WebPMetadata getMetadata0() throws WebPDecoderException {
        if (this.metadata != null) {
            return this.metadata;
        }
        try (final Arena arena = Arena.ofConfined()) {
            final WebPAnimInfo animInfo = this.decoder.getInfo(arena);
            return this.metadata = new WebPMetadata(
                animInfo.getCanvasWidth(),
                animInfo.getCanvasHeight(),
                animInfo.getLoopCount(),
                animInfo.getFrameCount(),
                animInfo.getBackgroundColor()
            );
        }
    }

    @Override
    public boolean hasMoreFrame() {
        return this.decoder.hasMoreFrames();
    }

    @Override
    public WebPFrame nextFrame() throws WebPDecoderException {
        final WebPMetadata metadata = this.getMetadata();
        final int canvasWidth = metadata.canvasWidth();
        final int canvasHeight = metadata.canvasHeight();
        final int[] pixels = new int[canvasWidth * canvasHeight];
        final int timestamp = this.decoder.getNextFrame(pixels);
        return new WebPFrame(pixels, canvasWidth, canvasHeight, timestamp);
    }

    @Override
    public void close() {
        this.arena.close();
    }
}
