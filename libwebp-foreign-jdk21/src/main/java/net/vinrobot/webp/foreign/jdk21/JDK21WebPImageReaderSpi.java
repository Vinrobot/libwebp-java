package net.vinrobot.webp.foreign.jdk21;

import net.vinrobot.webp.foreign.jdk21.internal.Loader;
import net.vinrobot.webp.imageio.BaseImageReaderSpi;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;

public final class JDK21WebPImageReaderSpi extends BaseImageReaderSpi {
    public JDK21WebPImageReaderSpi() {
        super(JDK21WebPImageReader.class, new Class[]{ImageInputStream.class, byte[].class});
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {
        if (!super.canDecodeInput(source)) {
            return false;
        }

        try {
            // The reader needs the native library to work
            Loader.ensureLibrary();
            return true;
        } catch (Exception ex) {
            // Unable to load native library
            return false;
        }
    }

    @Override
    public ImageReader createReaderInstance(final Object extension) {
        return new JDK21WebPImageReader(this);
    }
}
