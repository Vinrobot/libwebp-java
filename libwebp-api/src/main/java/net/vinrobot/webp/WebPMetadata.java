package net.vinrobot.webp;

public record WebPMetadata(
    int canvasWidth,
    int canvasHeight,
    int loopCount,
    int frameCount,
    int backgroundColor
) {
}
