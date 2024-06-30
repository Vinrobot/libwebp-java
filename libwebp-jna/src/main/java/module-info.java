module net.vinrobot.webp.jna {
    requires com.sun.jna;
    requires net.vinrobot.webp;
    requires java.desktop;

    exports net.vinrobot.webp.jna;

    provides net.vinrobot.webp.WebPReaderSpi with net.vinrobot.webp.jna.JNAWebPReaderSpi;
    provides javax.imageio.spi.ImageReaderSpi with net.vinrobot.webp.jna.JNAWebPImageReaderSpi;
}
