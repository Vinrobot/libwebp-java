package net.vinrobot.webp.jna;

import net.vinrobot.webp.imageio.BaseImageReaderSpi;
import net.vinrobot.webp.jna.internal.Loader;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;

public final class JNAWebPImageReaderSpi extends BaseImageReaderSpi {
    public JNAWebPImageReaderSpi() {
        super(JNAWebPImageReader.class, new Class[]{ImageInputStream.class, byte[].class});
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {
        if (!super.canDecodeInput(source)) {
            return false;
        }

        try {
            // The reader needs the native library to work
            return Loader.getInstance() != null;
        } catch (Exception ex) {
            // Unable to load native library
            return false;
        }
    }

    @Override
    public ImageReader createReaderInstance(final Object extension) {
        return new JNAWebPImageReader(this);
    }
}
