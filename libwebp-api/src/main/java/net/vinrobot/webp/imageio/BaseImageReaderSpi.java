package net.vinrobot.webp.imageio;

import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Locale;

public abstract class BaseImageReaderSpi extends ImageReaderSpi {
    private static final String VENDOR_NAME = "Vinrobot";
    private static final String VERSION = "1.0";
    private static final String[] NAMES = {"webp", "WEBP", "wbp", "WBP"};
    private static final String[] SUFFIXES = {"wbp", "webp"};
    private static final String[] MIME_TYPES = {"image/webp", "image/x-webp"};

    public BaseImageReaderSpi(final Class<? extends ImageReader> readerClass, final Class<?>[] inputTypes) {
        this(VENDOR_NAME, VERSION, readerClass, inputTypes);
    }

    public BaseImageReaderSpi(final String vendorName, final String version, final Class<? extends ImageReader> readerClass, final Class<?>[] inputTypes) {
        this(vendorName, version, NAMES, SUFFIXES, MIME_TYPES, readerClass.getName(), inputTypes, null, false, null, null, null, null, false, null, null, null, null);
    }

    public BaseImageReaderSpi(final String vendorName, final String version, final String[] names, final String[] suffixes, final String[] MIMETypes, final String readerClassName, final Class<?>[] inputTypes, final String[] writerSpiNames, final boolean supportsStandardStreamMetadataFormat, final String nativeStreamMetadataFormatName, final String nativeStreamMetadataFormatClassName, final String[] extraStreamMetadataFormatNames, final String[] extraStreamMetadataFormatClassNames, final boolean supportsStandardImageMetadataFormat, final String nativeImageMetadataFormatName, final String nativeImageMetadataFormatClassName, final String[] extraImageMetadataFormatNames, final String[] extraImageMetadataFormatClassNames) {
        super(vendorName, version, names, suffixes, MIMETypes, readerClassName, inputTypes, writerSpiNames, supportsStandardStreamMetadataFormat, nativeStreamMetadataFormatName, nativeStreamMetadataFormatClassName, extraStreamMetadataFormatNames, extraStreamMetadataFormatClassNames, supportsStandardImageMetadataFormat, nativeImageMetadataFormatName, nativeImageMetadataFormatClassName, extraImageMetadataFormatNames, extraImageMetadataFormatClassNames);
    }

    protected static int readInt(final byte[] buffer, final int offset) {
        return buffer[offset] | buffer[offset + 1] << 8 | buffer[offset + 2] << 16 | buffer[offset + 3] << 24;
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {
        if (source instanceof ImageInputStream) {
            return canDecodeInput((ImageInputStream) source);
        } else if (source instanceof byte[]) {
            return canDecodeInput((byte[]) source);
        } else {
            return false;
        }
    }

    public boolean canDecodeInput(final ImageInputStream stream) throws IOException {
        final ByteOrder originalOrder = stream.getByteOrder();
        stream.mark();

        try {
            // RIFF native order is Little Endian
            stream.setByteOrder(ByteOrder.LITTLE_ENDIAN);

            // Check file header
            // https://developers.google.com/speed/webp/docs/riff_container#webp_file_header

            if (stream.readInt() != WebP.RIFF_MAGIC) {
                return false;
            }

            stream.readInt(); // Skip file size

            if (stream.readInt() != WebP.WEBP_MAGIC) {
                return false;
            }

            // Check first chunk type
            switch (stream.readInt()) {
                case WebP.CHUNK_VP8L, WebP.CHUNK_VP8X, WebP.CHUNK_VP8_:
                    break;
                default:
                    return false;
            }

            return true;
        } finally {
            stream.setByteOrder(originalOrder);
            stream.reset();
        }
    }

    public boolean canDecodeInput(final byte[] data) {
        // Check file header
        // https://developers.google.com/speed/webp/docs/riff_container#webp_file_header

        if (data.length < 16 || readInt(data, 0) != WebP.RIFF_MAGIC || readInt(data, 8) != WebP.WEBP_MAGIC) {
            return false;
        }

        switch (readInt(data, 12)) {
            case WebP.CHUNK_VP8L, WebP.CHUNK_VP8X, WebP.CHUNK_VP8_:
                break;
            default:
                return false;
        }

        return true;
    }

    @Override
    public String getDescription(final Locale locale) {
        return "Google WebP File Format (WebP) Reader";
    }
}
