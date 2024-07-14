module net.vinrobot.webp {
    requires java.desktop;

    exports net.vinrobot.webp;
    exports net.vinrobot.webp.imageio;

    uses net.vinrobot.webp.WebPReaderSpi;
}
