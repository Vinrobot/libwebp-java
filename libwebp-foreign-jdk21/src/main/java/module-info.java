module net.vinrobot.webp.foreign.jdk21 {
    requires net.vinrobot.webp;
    requires java.desktop;

    exports net.vinrobot.webp.foreign.jdk21;

    provides net.vinrobot.webp.WebPReaderSpi with net.vinrobot.webp.foreign.jdk21.JDK21WebPReaderSpi;
    provides javax.imageio.spi.ImageReaderSpi with net.vinrobot.webp.foreign.jdk21.JDK21WebPImageReaderSpi;
}
