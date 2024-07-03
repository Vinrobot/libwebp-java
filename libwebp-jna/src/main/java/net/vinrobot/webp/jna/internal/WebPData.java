package net.vinrobot.webp.jna.internal;

import com.sun.jna.Pointer;

import java.io.Closeable;
import java.util.Objects;

public final class WebPData implements Closeable {
    private final LibWebP lib;
    private LibWebP.WebPData struct;

    public WebPData(final LibWebP lib, final byte[] buffer) {
        this(lib, buffer, 0, buffer.length);
    }

    public WebPData(final LibWebP lib, final byte[] buffer, final int offset, final int length) {
        if (buffer == null) {
            throw new NullPointerException("rawData == null");
        } else if (buffer.length == 0) {
            throw new IllegalArgumentException("rawData.length == 0");
        }

        this.lib = Objects.requireNonNull(lib, "lib == null");

        final Pointer bytes = lib.WebPMalloc(buffer.length);
        if (bytes == null) {
            throw new NullPointerException("Failed to allocate memory for WebPData");
        }
        bytes.write(0, buffer, offset, length);

        this.struct = new LibWebP.WebPData();
        this.struct.bytes = bytes;
        this.struct.length = new LibWebP.Size_T(buffer.length);
    }

    LibWebP.WebPData getStruct() {
        return this.struct;
    }

    @Override
    public void close() {
        if (this.struct != null) {
            this.lib.WebPFree(this.struct.bytes);
            this.struct.bytes = null;
            this.struct = null;
        }
    }
}
