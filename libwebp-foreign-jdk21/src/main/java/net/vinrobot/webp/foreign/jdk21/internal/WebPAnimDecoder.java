package net.vinrobot.webp.foreign.jdk21.internal;

import com.google.libwebp.foreign.jdk21.LibWebP;
import net.vinrobot.webp.WebPDecoderException;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;

public class WebPAnimDecoder {
    private static final long INT_SIZE = LibWebP.C_INT.byteSize();
    private static final int WEBP_DEMUX_ABI_VERSION = 0x0107;
    private static final int FALSE = 0;

    private final MemorySegment memorySegment;

    public WebPAnimDecoder(final Arena arena, final WebPData webpData) {
        final MemorySegment unsafeData = LibWebP.WebPAnimDecoderNewInternal(webpData.getMemorySegment(), LibWebP.NULL(), WEBP_DEMUX_ABI_VERSION);
        if (unsafeData.address() == LibWebP.NULL().address()) {
            throw new NullPointerException("Failed to create WebPAnimDecoder");
        }
        this.memorySegment = unsafeData.reinterpret(arena, LibWebP::WebPAnimDecoderDelete);
    }

    public WebPAnimInfo getInfo(final SegmentAllocator allocator) throws WebPDecoderException {
        final WebPAnimInfo webPAnimInfo = new WebPAnimInfo(allocator);
        if (LibWebP.WebPAnimDecoderGetInfo(this.memorySegment, webPAnimInfo.getMemorySegment()) == FALSE) {
            throw new WebPDecoderException("Failed getting decoder info");
        }
        return webPAnimInfo;
    }

    public boolean hasMoreFrames() {
        return LibWebP.WebPAnimDecoderHasMoreFrames(this.memorySegment) != FALSE;
    }

    public int getNextFrame(final int[] pixels) throws WebPDecoderException {
        try (final Arena arena = Arena.ofConfined()) {
            final MemorySegment bufferRef = arena.allocate(LibWebP.C_POINTER);
            final MemorySegment timestampRef = arena.allocate(LibWebP.C_INT);

            if (LibWebP.WebPAnimDecoderGetNext(this.memorySegment, bufferRef, timestampRef) == FALSE) {
                throw new WebPDecoderException("Error decoding next frame");
            }

            final MemorySegment unsafeBuffer = bufferRef.get(LibWebP.C_POINTER, 0);
            if (unsafeBuffer.address() == LibWebP.NULL().address()) {
                throw new NullPointerException("Received invalid buffer address");
            }

            final MemorySegment safeBuffer = unsafeBuffer.reinterpret(INT_SIZE * pixels.length);
            MemorySegment.copy(safeBuffer, ValueLayout.JAVA_INT, 0, pixels, 0, pixels.length);

            return timestampRef.get(LibWebP.C_INT, 0);
        }
    }
}
