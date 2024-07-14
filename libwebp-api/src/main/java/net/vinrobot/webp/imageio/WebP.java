package net.vinrobot.webp.imageio;

/**
 * WebP container format constants.
 *
 * @see <a href="https://developers.google.com/speed/webp/docs/riff_container">WebP Container
 * Specification</a>
 */
public class WebP {
    public static final int RIFF_MAGIC = 'R' | 'I' << 8 | 'F' << 16 | 'F' << 24;
    public static final int WEBP_MAGIC = 'W' | 'E' << 8 | 'B' << 16 | 'P' << 24;

    public static final int CHUNK_VP8_ = 'V' | 'P' << 8 | '8' << 16 | ' ' << 24;
    public static final int CHUNK_VP8L = 'V' | 'P' << 8 | '8' << 16 | 'L' << 24;
    public static final int CHUNK_VP8X = 'V' | 'P' << 8 | '8' << 16 | 'X' << 24;
}
