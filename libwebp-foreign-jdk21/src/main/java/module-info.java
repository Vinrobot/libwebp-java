module net.vinrobot.webp.foreign.jdk21 {
    requires net.vinrobot.webp;

    exports net.vinrobot.webp.foreign.jdk21;

    provides net.vinrobot.webp.WebPReaderSpi with net.vinrobot.webp.foreign.jdk21.JDK21WebPReaderSpi;
}
