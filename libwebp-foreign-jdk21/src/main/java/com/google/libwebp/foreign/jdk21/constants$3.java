// Generated by jextract

package com.google.libwebp.foreign.jdk21;

import java.lang.invoke.MethodHandle;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$3 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$3() {}
    static final FunctionDescriptor const$0 = FunctionDescriptor.of(JAVA_INT,
        RuntimeHelper.POINTER
    );
    static final MethodHandle const$1 = RuntimeHelper.downcallHandle(
        "WebPAnimDecoderHasMoreFrames",
        constants$3.const$0
    );
    static final MethodHandle const$2 = RuntimeHelper.downcallHandle(
        "WebPAnimDecoderDelete",
        constants$0.const$2
    );
    static final MemorySegment const$3 = MemorySegment.ofAddress(0L);
}


