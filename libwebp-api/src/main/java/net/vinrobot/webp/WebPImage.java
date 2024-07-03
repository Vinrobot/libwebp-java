package net.vinrobot.webp;

import java.util.List;

public record WebPImage(
    List<WebPFrame> frames,
    WebPMetadata metadata
) {
    public WebPImage {
        frames = List.copyOf(frames);
        if (metadata == null) {
            throw new NullPointerException("metadata must not be null");
        }
    }
}
