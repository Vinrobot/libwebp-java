// Generated by jextract

package com.google.libwebp.foreign.jdk21;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$1 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$1() {}
    static final VarHandle const$0 = constants$0.const$4.varHandle(MemoryLayout.PathElement.groupElement("size"));
    static final FunctionDescriptor const$1 = FunctionDescriptor.of(RuntimeHelper.POINTER,
        RuntimeHelper.POINTER,
        RuntimeHelper.POINTER,
        JAVA_INT
    );
    static final MethodHandle const$2 = RuntimeHelper.downcallHandle(
        "WebPAnimDecoderNewInternal",
        constants$1.const$1
    );
    static final StructLayout const$3 = MemoryLayout.structLayout(
        JAVA_INT.withName("canvas_width"),
        JAVA_INT.withName("canvas_height"),
        JAVA_INT.withName("loop_count"),
        JAVA_INT.withName("bgcolor"),
        JAVA_INT.withName("frame_count"),
        MemoryLayout.sequenceLayout(4, JAVA_INT).withName("pad")
    ).withName("WebPAnimInfo");
    static final VarHandle const$4 = constants$1.const$3.varHandle(MemoryLayout.PathElement.groupElement("canvas_width"));
    static final VarHandle const$5 = constants$1.const$3.varHandle(MemoryLayout.PathElement.groupElement("canvas_height"));
}

