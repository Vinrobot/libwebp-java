package net.vinrobot.webp;

public record WebPFrame(
    int[] pixels,
    int width,
    int height,
    int timestamp
) {
    public WebPFrame {
        if (pixels == null) {
            throw new NullPointerException("pixels must not be null");
        }
    }
}
