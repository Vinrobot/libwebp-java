package net.vinrobot.webp.jna.internal;

import com.sun.jna.Native;

public final class Loader {
    private static final String LIB_NAME = "libwebp_animdecoder";

    private static LibWebP INSTANCE;

    public static LibWebP getInstance() {
        if (INSTANCE == null) {
            synchronized (Loader.class) {
                if (INSTANCE == null) {
                    INSTANCE = Native.load(LIB_NAME, LibWebP.class);
                }
            }
        }
        return INSTANCE;
    }
}
