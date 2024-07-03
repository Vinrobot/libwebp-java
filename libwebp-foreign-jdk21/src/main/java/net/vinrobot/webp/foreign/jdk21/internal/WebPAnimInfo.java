package net.vinrobot.webp.foreign.jdk21.internal;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;

import static com.google.libwebp.foreign.jdk21.WebPAnimInfo.allocate;
import static com.google.libwebp.foreign.jdk21.WebPAnimInfo.bgcolor$get;
import static com.google.libwebp.foreign.jdk21.WebPAnimInfo.bgcolor$set;
import static com.google.libwebp.foreign.jdk21.WebPAnimInfo.canvas_height$get;
import static com.google.libwebp.foreign.jdk21.WebPAnimInfo.canvas_height$set;
import static com.google.libwebp.foreign.jdk21.WebPAnimInfo.canvas_width$get;
import static com.google.libwebp.foreign.jdk21.WebPAnimInfo.canvas_width$set;
import static com.google.libwebp.foreign.jdk21.WebPAnimInfo.frame_count$get;
import static com.google.libwebp.foreign.jdk21.WebPAnimInfo.frame_count$set;
import static com.google.libwebp.foreign.jdk21.WebPAnimInfo.loop_count$get;
import static com.google.libwebp.foreign.jdk21.WebPAnimInfo.loop_count$set;

public class WebPAnimInfo {
    private final MemorySegment memorySegment;

    public WebPAnimInfo(final SegmentAllocator allocator) {
        this.memorySegment = allocate(allocator);
    }

    public MemorySegment getMemorySegment() {
        return this.memorySegment;
    }

    public int getCanvasWidth() {
        return canvas_width$get(this.memorySegment);
    }

    public void setCanvasWidth(final int value) {
        canvas_width$set(this.memorySegment, value);
    }

    public int getCanvasHeight() {
        return canvas_height$get(this.memorySegment);
    }

    public void setCanvasHeight(final int value) {
        canvas_height$set(this.memorySegment, value);
    }

    public int getLoopCount() {
        return loop_count$get(this.memorySegment);
    }

    public void setLoopCount(final int value) {
        loop_count$set(this.memorySegment, value);
    }

    public int getBackgroundColor() {
        return bgcolor$get(this.memorySegment);
    }

    public void setBackgroundColor(final int value) {
        bgcolor$set(this.memorySegment, value);
    }

    public int getFrameCount() {
        return frame_count$get(this.memorySegment);
    }

    public void setFrameCount(final int value) {
        frame_count$set(this.memorySegment, value);
    }
}
