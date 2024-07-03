package net.vinrobot.webp.foreign.jdk21.internal;

import com.google.libwebp.foreign.jdk21.LibWebP;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import static com.google.libwebp.foreign.jdk21.WebPData.allocate;
import static com.google.libwebp.foreign.jdk21.WebPData.bytes$get;
import static com.google.libwebp.foreign.jdk21.WebPData.bytes$set;
import static com.google.libwebp.foreign.jdk21.WebPData.size$get;
import static com.google.libwebp.foreign.jdk21.WebPData.size$set;

public class WebPData {
    private static final long BYTE_SIZE = LibWebP.C_CHAR.byteSize();

    private final MemorySegment memorySegment;

    public WebPData(final Arena arena, final byte[] buffer, final int offset, final int length) {
        if (buffer == null) {
            throw new NullPointerException("buffer == null");
        } else if (buffer.length == 0 || length == 0) {
            throw new IllegalArgumentException("buffer.length == 0");
        }

        final MemorySegment unsafeData = LibWebP.WebPMalloc(length);
        if (unsafeData.address() == LibWebP.NULL().address()) {
            throw new NullPointerException("Failed to allocate memory for WebPData");
        }

        final MemorySegment safeData = unsafeData.reinterpret(BYTE_SIZE * length, arena, LibWebP::WebPFree);
        MemorySegment.copy(buffer, offset, safeData, ValueLayout.JAVA_BYTE, 0, length);

        final MemorySegment webPData = allocate(arena);
        bytes$set(webPData, safeData);
        size$set(webPData, length);

        this.memorySegment = webPData;
    }

    public MemorySegment getMemorySegment() {
        return this.memorySegment;
    }

    public MemorySegment getBytes() {
        return bytes$get(this.memorySegment);
    }

    public long getSize() {
        return size$get(this.memorySegment);
    }
}
