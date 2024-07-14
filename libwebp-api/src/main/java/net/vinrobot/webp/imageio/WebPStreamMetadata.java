package net.vinrobot.webp.imageio;

import org.w3c.dom.Node;

import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;

public final class WebPStreamMetadata extends IIOMetadata {
    public static final String NATIVE_METADATA_FORMAT_NAME = "net_vinrobot_imageio_webp_stream_1.0";
    public static final String NATIVE_METADATA_FORMAT_CLASS_NAME = WebPStreamMetadata.class.getName();

    private final int canvasWidth;
    private final int canvasHeight;
    private final int loopCount;
    private final int frameCount;
    private final int backgroundColor;

    public WebPStreamMetadata(int canvasWidth, int canvasHeight, int loopCount, int frameCount, int backgroundColor) {
        super(false, NATIVE_METADATA_FORMAT_NAME, NATIVE_METADATA_FORMAT_CLASS_NAME, null, null);
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.loopCount = loopCount;
        this.frameCount = frameCount;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public Node getAsTree(final String formatName) {
        if (formatName.equals(nativeMetadataFormatName)) {
            return getNativeTree();
        } else {
            throw new IllegalArgumentException("Unsupported format name: " + formatName);
        }
    }

    public Node getNativeTree() {
        final IIOMetadataNode documentNode = new IIOMetadataNode("Document");
        documentNode.setAttribute("CanvasWidth", String.valueOf(this.canvasWidth));
        documentNode.setAttribute("CanvasHeight", String.valueOf(this.canvasHeight));
        documentNode.setAttribute("LoopCount", String.valueOf(this.loopCount));
        documentNode.setAttribute("FrameCount", String.valueOf(this.frameCount));
        documentNode.setAttribute("BackgroundColor", String.valueOf(this.backgroundColor));
        return documentNode;
    }

    @Override
    public void mergeTree(final String formatName, final Node root) {
        throw new IllegalStateException("Metadata is read-only");
    }

    @Override
    public void reset() {
        throw new IllegalStateException("Metadata is read-only");
    }
}
