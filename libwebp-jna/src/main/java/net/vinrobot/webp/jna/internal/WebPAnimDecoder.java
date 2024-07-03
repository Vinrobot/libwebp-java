package net.vinrobot.webp.jna.internal;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import net.vinrobot.webp.WebPDecoderException;
import net.vinrobot.webp.WebPFrame;
import net.vinrobot.webp.WebPMetadata;

import java.io.Closeable;
import java.util.Objects;

public final class WebPAnimDecoder implements Closeable {
    private static final int JNA_FALSE = 0;

    private final LibWebP lib;
    private Pointer decoder;

    public WebPAnimDecoder(final LibWebP lib, final WebPData data) {
        Objects.requireNonNull(data, "data == null");
        final LibWebP.WebPData struct = Objects.requireNonNull(data.getStruct(), "data.struct == null");
        this.lib = Objects.requireNonNull(lib, "lib == null");

        this.decoder = lib.WebPAnimDecoderNewInternal(struct, null, LibWebP.WEBP_DEMUX_ABI_VERSION);
        if (this.decoder == null) {
            throw new NullPointerException("Failed initializing decoder");
        }
    }

    public WebPMetadata getInfo() throws WebPDecoderException {
        if (this.decoder == null) {
            throw new IllegalStateException("WebPAnimDecoder already closed");
        }

        final LibWebP.WebPAnimInfo struct = new LibWebP.WebPAnimInfo();
        if (this.lib.WebPAnimDecoderGetInfo(this.decoder, struct) == JNA_FALSE) {
            throw new WebPDecoderException("Failed getting decoder info");
        }
        return new WebPMetadata(struct.canvas_width, struct.canvas_height, struct.loop_count, struct.frame_count, struct.bgcolor);
    }

    public boolean hasMoreFrames() {
        if (this.decoder == null) {
            throw new IllegalStateException("WebPAnimDecoder already closed");
        }

        return this.lib.WebPAnimDecoderHasMoreFrames(this.decoder) != JNA_FALSE;
    }

    public WebPFrame getNext(final WebPMetadata info) throws WebPDecoderException {
        if (this.decoder == null) {
            throw new IllegalStateException("WebPAnimDecoder already closed");
        }

        Objects.requireNonNull(info, "info == null");

        final PointerByReference bufferRef = new PointerByReference();
        final IntByReference timestampRef = new IntByReference();

        if (this.lib.WebPAnimDecoderGetNext(this.decoder, bufferRef, timestampRef) == JNA_FALSE) {
            throw new WebPDecoderException("Error decoding next frame");
        }

        final Pointer buffer = bufferRef.getValue();
        if (buffer == null) {
            throw new WebPDecoderException("Error decoding next frame");
        }

        final int[] pixels = buffer.getIntArray(0, info.canvasWidth() * info.canvasHeight());
        final int timestamp = timestampRef.getValue();

        return new WebPFrame(pixels, info.canvasWidth(), info.canvasHeight(), timestamp);
    }

    @Override
    public void close() {
        if (this.decoder != null) {
            this.lib.WebPAnimDecoderDelete(this.decoder);
            this.decoder = null;
        }
    }
}
