package net.vinrobot.webp.foreign.jdk21.internal;

import java.io.IOException;
import java.nio.file.Path;

public final class Loader {
    private static final String LIB_NAME = "libwebp_animdecoder";

    private static boolean initialized = false;

    public static void ensureLibrary() throws IOException {
        if (!initialized) {
            synchronized (Loader.class) {
                if (!initialized) {
                    final Path libPath = Platform.extractFromResourcePath(LIB_NAME);
                    System.load(libPath.toString());
                    initialized = true;
                }
            }
        }
    }
}
