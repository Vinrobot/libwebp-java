package net.vinrobot.webp.jna.internal;

import com.sun.jna.IntegerType;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

import java.io.Serial;

public interface LibWebP extends Library {
    int WEBP_DEMUX_ABI_VERSION = 0x0108;

    /*
    [webp/types.h]
        // Allocates 'size' bytes of memory. Returns NULL upon error. Memory
        // must be deallocated by calling WebPFree(). This function is made available
        // by the core 'libwebp' library.
        WEBP_EXTERN void* WebPMalloc(size_t size);
    */
    Pointer WebPMalloc(int size);

    /*
    [webp/types.h]
        // Releases memory returned by the WebPDecode*() functions (from decode.h).
        WEBP_EXTERN void WebPFree(void* ptr);
    */
    void WebPFree(Pointer pointer);

    /*
    [webp/demux.h]
        // Internal, version-checked, entry point.
        WEBP_EXTERN WebPAnimDecoder* WebPAnimDecoderNewInternal(
            const WebPData*, const WebPAnimDecoderOptions*, int);

        // Creates and initializes a WebPAnimDecoder object.
        // Parameters:
        //   webp_data - (in) WebP bitstream. This should remain unchanged during the
        //                    lifetime of the output WebPAnimDecoder object.
        //   dec_options - (in) decoding options. Can be passed NULL to choose
        //                      reasonable defaults (in particular, color mode MODE_RGBA
        //                      will be picked).
        // Returns:
        //   A pointer to the newly created WebPAnimDecoder object, or NULL in case of
        //   parsing error, invalid option or memory error.
        static WEBP_INLINE WebPAnimDecoder* WebPAnimDecoderNew(
            const WebPData* webp_data, const WebPAnimDecoderOptions* dec_options) {
          return WebPAnimDecoderNewInternal(webp_data, dec_options,
                                            WEBP_DEMUX_ABI_VERSION);
        }
    */
    Pointer WebPAnimDecoderNewInternal(WebPData webp_data, Structure dec_options, int version);

    /*
    [webp/demux.h]
        // Get global information about the animation.
        // Parameters:
        //   dec - (in) decoder instance to get information from.
        //   info - (out) global information fetched from the animation.
        // Returns:
        //   True on success.
        WEBP_EXTERN int WebPAnimDecoderGetInfo(const WebPAnimDecoder* dec,
                                               WebPAnimInfo* info);
    */
    int WebPAnimDecoderGetInfo(Pointer dec, WebPAnimInfo info);

    /*
    [webp/demux.h]
        // Check if there are more frames left to decode.
        // Parameters:
        //   dec - (in) decoder instance to be checked.
        // Returns:
        //   True if 'dec' is not NULL and some frames are yet to be decoded.
        //   Otherwise, returns false.
        WEBP_EXTERN int WebPAnimDecoderHasMoreFrames(const WebPAnimDecoder* dec);
    */
    int WebPAnimDecoderHasMoreFrames(Pointer dec);

    /*
    [webp/demux.h]
        // Fetch the next frame from 'dec' based on options supplied to
        // WebPAnimDecoderNew(). This will be a fully reconstructed canvas of size
        // 'canvasWidth * 4 * canvasHeight', and not just the frame sub-rectangle. The
        // returned buffer 'buf' is valid only until the next call to
        // WebPAnimDecoderGetNext(), WebPAnimDecoderReset() or WebPAnimDecoderDelete().
        // Parameters:
        //   dec - (in/out) decoder instance from which the next frame is to be fetched.
        //   buf - (out) decoded frame.
        //   timestamp - (out) timestamp of the frame in milliseconds.
        // Returns:
        //   False if any of the arguments are NULL, or if there is a parsing or
        //   decoding error, or if there are no more frames. Otherwise, returns true.
        WEBP_EXTERN int WebPAnimDecoderGetNext(WebPAnimDecoder* dec,
                                               uint8_t** buf, int* timestamp);
    */
    int WebPAnimDecoderGetNext(Pointer dec, PointerByReference buf, IntByReference timestamp);

    /*
    [webp/demux.h]
        // Deletes the WebPAnimDecoder object.
        // Parameters:
        //   dec - (in/out) decoder instance to be deleted
        WEBP_EXTERN void WebPAnimDecoderDelete(WebPAnimDecoder* dec);
    */
    void WebPAnimDecoderDelete(Pointer dec);

    class Size_T extends IntegerType {
        public static final Size_T ZERO = new Size_T();
        @Serial
        private static final long serialVersionUID = 1L;

        public Size_T() {
            this(0);
        }

        public Size_T(long value) {
            super(Native.SIZE_T_SIZE, value, true);
        }
    }

    /*
	[webp/mux_types.h]
	    // Data type used to describe 'raw' data, e.g., chunk data
	    // (ICC profile, metadata) and WebP compressed image data.
	    // 'bytes' memory must be allocated using WebPMalloc() and such.
	    struct WebPData {
	      const uint8_t* bytes;
	      size_t size;
	    };
	*/
    @Structure.FieldOrder({"bytes", "length"})
    class WebPData extends Structure {
        public Pointer bytes;
        public Size_T length;
    }

    /*
	[webp/demux.h]
	    // Global information about the animation..
	    struct WebPAnimInfo {
	      uint32_t canvas_width;
	      uint32_t canvas_height;
	      uint32_t loop_count;
	      uint32_t bgcolor;
	      uint32_t frame_count;
	      uint32_t pad[4];   // padding for later use
	    };
	*/
    @Structure.FieldOrder({
        "canvas_width", "canvas_height", "loop_count", "bgcolor", "frame_count", "pad"
    })
    class WebPAnimInfo extends Structure {
        public int canvas_width;
        public int canvas_height;
        public int loop_count;
        public int bgcolor;
        public int frame_count;
        public int[] pad = new int[4];
    }
}
