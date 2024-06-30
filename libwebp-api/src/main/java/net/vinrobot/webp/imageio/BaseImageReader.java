package net.vinrobot.webp.imageio;

import net.vinrobot.webp.WebPDecoderException;
import net.vinrobot.webp.WebPFrame;
import net.vinrobot.webp.WebPMetadata;
import net.vinrobot.webp.WebPReader;

import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public abstract class BaseImageReader extends ImageReader {
    private final List<Frame> frames = new ArrayList<>();
    private WebPReader reader;
    private WebPMetadata metadata;

    public BaseImageReader(final ImageReaderSpi originatingProvider) {
        super(originatingProvider);
    }

    protected BufferedImage createImage(final int[] pixels, final int width, final int height) {
        assert pixels.length == width * height;
        final ColorModel colorModel = new DirectColorModel(32, 0x000000ff, 0x0000ff00, 0x00ff0000, 0xff000000);
        final SampleModel sampleModel = colorModel.createCompatibleSampleModel(width, height);
        final DataBufferInt dataBufferInt = new DataBufferInt(pixels, width * height);
        final WritableRaster writableRaster = WritableRaster.createWritableRaster(sampleModel, dataBufferInt, null);
        return new BufferedImage(colorModel, writableRaster, false, null);
    }

    @Override
    public void setInput(final Object input, final boolean seekForwardOnly, final boolean ignoreMetadata) {
        super.setInput(input, seekForwardOnly, ignoreMetadata);
        this.resetInternalState();
    }

    @Override
    public void dispose() {
        super.dispose();
        this.setInput(null);
    }

    protected synchronized void resetInternalState() {
        this.metadata = null;
        this.frames.clear();
        if (this.reader != null) {
            try {
                this.reader.close();
                this.reader = null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected WebPReader getReader(final Object input) throws WebPDecoderException, IOException {
        if (input instanceof ImageInputStream stream) {
            return this.getReader(stream);
        } else if (input instanceof byte[]) {
            return this.getReader((byte[]) input);
        } else {
            final String inputClassName = input.getClass().getName();
            throw new IllegalStateException("Unsupported input type: " + inputClassName);
        }
    }

    protected abstract WebPReader getReader(ImageInputStream stream) throws WebPDecoderException, IOException;

    protected abstract WebPReader getReader(byte[] data) throws WebPDecoderException, IOException;

    protected WebPReader getReader() throws WebPDecoderException, IOException {
        final Object input = Objects.requireNonNull(this.getInput());
        if (this.reader == null) {
            synchronized (this) {
                if (this.reader == null) {
                    this.reader = this.getReader(input);
                }
            }
        }
        return Objects.requireNonNull(this.reader);
    }

    protected WebPMetadata getMetadata() throws WebPDecoderException, IOException {
        if (this.metadata == null) {
            final WebPReader reader = this.getReader();
            this.metadata = reader.getMetadata();
        }
        return this.metadata;
    }

    protected void readAllFrames() throws IOException {
        try {
            this.read(Integer.MAX_VALUE);
        } catch (final IndexOutOfBoundsException ex) {
            // Ignore
        }
    }

    @Override
    public int getNumImages(final boolean allowSearch) throws IOException {
        if (allowSearch) {
            this.readAllFrames();
            return this.frames.size();
        }
        try {
            final WebPMetadata metadata = this.getMetadata();
            return metadata.frameCount();
        } catch (WebPDecoderException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    @Override
    public int getWidth(final int imageIndex) throws IOException {
        if (imageIndex < 0) {
            throw new IndexOutOfBoundsException("imageIndex < 0");
        }
        try {
            final WebPMetadata metadata = this.getMetadata();
            if (imageIndex >= metadata.frameCount()) {
                throw new IndexOutOfBoundsException("imageIndex >= frameCount");
            }
            return metadata.canvasWidth();
        } catch (WebPDecoderException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    @Override
    public int getHeight(final int imageIndex) throws IOException {
        if (imageIndex < 0) {
            throw new IndexOutOfBoundsException("imageIndex < 0");
        }
        try {
            final WebPMetadata metadata = this.getMetadata();
            if (imageIndex >= metadata.frameCount()) {
                throw new IndexOutOfBoundsException("imageIndex >= frameCount");
            }
            return metadata.canvasHeight();
        } catch (WebPDecoderException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    @Override
    public Iterator<ImageTypeSpecifier> getImageTypes(final int imageIndex) throws IOException {
        return null;
    }

    @Override
    public IIOMetadata getStreamMetadata() throws IOException {
        try {
            final WebPMetadata metadata = this.getMetadata();
            return new WebPStreamMetadata(metadata.canvasWidth(), metadata.canvasHeight(), metadata.loopCount(), metadata.frameCount(), metadata.backgroundColor());
        } catch (WebPDecoderException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    @Override
    public IIOMetadata getImageMetadata(final int imageIndex) throws IOException {
        this.read(imageIndex);
        final int timestamp = this.frames.get(imageIndex).timestamp;
        return new WebPImageMetadata(timestamp);
    }

    @Override
    public BufferedImage read(final int imageIndex, final ImageReadParam param) throws IOException {
        if (param != null) {
            throw new UnsupportedOperationException("ImageReadParam not supported");
        }
        if (imageIndex < 0) {
            throw new IndexOutOfBoundsException("imageIndex < 0");
        }
        if (imageIndex < this.frames.size()) {
            return this.frames.get(imageIndex).image;
        }

        try {
            final WebPReader reader = this.getReader();
            final WebPMetadata metadata = this.getMetadata();

            while (reader.hasMoreFrame()) {
                final WebPFrame frame = reader.nextFrame();
                final BufferedImage image = createImage(frame.pixels(), metadata.canvasWidth(), metadata.canvasHeight());
                this.frames.add(new Frame(image, frame.timestamp()));
                if (this.frames.size() - 1 == imageIndex) {
                    return image;
                }
            }
        } catch (WebPDecoderException e) {
            throw new IOException(e.getMessage(), e);
        }

        // Should never happen since we check imageIndex < info.frameCount() unless the webp file is malformed.
        throw new IndexOutOfBoundsException("imageIndex >= frameCount");
    }

    protected record Frame(
        BufferedImage image,
        int timestamp
    ) {
    }
}
