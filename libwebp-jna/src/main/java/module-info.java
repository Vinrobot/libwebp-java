module net.vinrobot.webp.jna {
    requires com.sun.jna;
    requires net.vinrobot.webp;

    exports net.vinrobot.webp.jna;

    provides net.vinrobot.webp.WebPReaderSpi with net.vinrobot.webp.jna.JNAWebPReaderSpi;
}
