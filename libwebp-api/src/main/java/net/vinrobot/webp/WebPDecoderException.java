package net.vinrobot.webp;

import java.io.Serial;

public class WebPDecoderException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public WebPDecoderException(String message) {
        super(message);
    }
}
