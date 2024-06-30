package net.vinrobot.webp;

import java.net.URL;
import java.util.stream.Stream;

public final class TestResources {
    public static Stream<TestImage> getTestImages() {
        return Stream.concat(getLocalTestImages(), getOfficialLibwebpTestImages());
    }

    public static Stream<TestImage> getLocalTestImages() {
        return Stream.of(
            new TestImage(getResource("/images/test.webp"), 16, 16, 1, new int[]{480, 1280})
        );
    }

    public static Stream<TestImage> getOfficialLibwebpTestImages() {
        return Stream.of(
            new TestImage(getResource("/libwebp-test-data/alpha_color_cache.webp"), 588, 97),
            new TestImage(getResource("/libwebp-test-data/alpha_filter_0_method_0.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/alpha_filter_0_method_1.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/alpha_filter_1.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/alpha_filter_1_method_0.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/alpha_filter_1_method_1.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/alpha_filter_2.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/alpha_filter_2_method_0.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/alpha_filter_2_method_1.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/alpha_filter_3.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/alpha_filter_3_method_0.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/alpha_filter_3_method_1.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/alpha_no_compression.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/bad_palette_index.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/big_endian_bug_393.webp"), 256, 256),
            new TestImage(getResource("/libwebp-test-data/bryce.webp"), 11158, 2156),
            new TestImage(getResource("/libwebp-test-data/bug3.webp"), 95, 95),
            new TestImage(getResource("/libwebp-test-data/color_cache_bits_11.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/dual_transform.webp"), 100, 30),
            new TestImage(getResource("/libwebp-test-data/lossless1.webp"), 1000, 307),
            new TestImage(getResource("/libwebp-test-data/lossless2.webp"), 1000, 307),
            new TestImage(getResource("/libwebp-test-data/lossless3.webp"), 1000, 307),
            new TestImage(getResource("/libwebp-test-data/lossless4.webp"), 256, 256),
            new TestImage(getResource("/libwebp-test-data/lossless_big_random_alpha.webp"), 2048, 2048),
            new TestImage(getResource("/libwebp-test-data/lossless_color_transform.webp"), 512, 512),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_0.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_1.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_2.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_3.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_4.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_5.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_6.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_7.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_8.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_9.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_10.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_11.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_12.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_13.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_14.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_1_15.webp"), 16, 16),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_0.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_1.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_2.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_3.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_4.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_5.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_6.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_7.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_8.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_9.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_10.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_11.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_12.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_13.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_14.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossless_vec_2_15.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/lossy_alpha1.webp"), 1000, 307),
            new TestImage(getResource("/libwebp-test-data/lossy_alpha2.webp"), 1000, 307),
            new TestImage(getResource("/libwebp-test-data/lossy_alpha3.webp"), 1000, 307),
            new TestImage(getResource("/libwebp-test-data/lossy_alpha4.webp"), 100, 100),
            new TestImage(getResource("/libwebp-test-data/lossy_extreme_probabilities.webp"), 800, 534),
            new TestImage(getResource("/libwebp-test-data/lossy_q0_f100.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/near_lossless_75.webp"), 256, 256),
            new TestImage(getResource("/libwebp-test-data/one_color_no_palette.webp"), 100, 100),
            new TestImage(getResource("/libwebp-test-data/segment01.webp"), 160, 160),
            new TestImage(getResource("/libwebp-test-data/segment02.webp"), 160, 160),
            new TestImage(getResource("/libwebp-test-data/segment03.webp"), 160, 160),
            new TestImage(getResource("/libwebp-test-data/small_13x1.webp"), 13, 1),
            new TestImage(getResource("/libwebp-test-data/small_1x1.webp"), 1, 1),
            new TestImage(getResource("/libwebp-test-data/small_1x13.webp"), 1, 13),
            new TestImage(getResource("/libwebp-test-data/small_31x13.webp"), 31, 13),
            new TestImage(getResource("/libwebp-test-data/test-nostrong.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/test.webp"), 128, 128),
            new TestImage(getResource("/libwebp-test-data/very_short.webp"), 63, 66),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-001.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-002.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-003.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-004.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-005.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-006.webp"), 175, 143),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-007.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-008.webp"), 1432, 888),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-009.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-010.webp"), 320, 240),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-011.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-012.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-013.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-014.webp"), 175, 143),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-015.webp"), 320, 240),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-016.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-00-comprehensive-017.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-01-intra-1400.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-02-inter-1402.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-01-intra-1411.webp"), 96, 96),
            new TestImage(getResource("/libwebp-test-data/vp80-02-inter-1412.webp"), 96, 96),
            new TestImage(getResource("/libwebp-test-data/vp80-01-intra-1416.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-01-intra-1417.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-02-inter-1418.webp"), 200, 200),
            new TestImage(getResource("/libwebp-test-data/vp80-02-inter-1424.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1401.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1403.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1407.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1408.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1409.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1410.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1413.webp"), 96, 96),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1414.webp"), 320, 240),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1415.webp"), 320, 240),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1425.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1426.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1427.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1432.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1435.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1436.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-03-segmentation-1437.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-04-partitions-1404.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-04-partitions-1405.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-04-partitions-1406.webp"), 176, 144),
            new TestImage(getResource("/libwebp-test-data/vp80-05-sharpness-1428.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-05-sharpness-1429.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-05-sharpness-1430.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-05-sharpness-1431.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-05-sharpness-1433.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-05-sharpness-1434.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-05-sharpness-1438.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-05-sharpness-1439.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-05-sharpness-1440.webp"), 352, 288),
            new TestImage(getResource("/libwebp-test-data/vp80-05-sharpness-1443.webp"), 1920, 96)
        );
    }

    public static URL getResource(String name) {
        return TestResources.class.getResource(name);
    }

    public record TestImage(
        URL resource,
        int width,
        int height,
        int loopCount,
        TestFrame[] frames
    ) {
        public TestImage(URL resource, int width, int height) {
            this(resource, width, height, 1, new TestFrame[]{new TestFrame(width, height, 0)});
        }

        public TestImage(URL resource, int width, int height, int loopCount, int[] delays) {
            this(resource, width, height, loopCount, new TestFrame[delays.length]);
            int timestamp = 0;
            for (int i = 0; i < delays.length; i++) {
                timestamp += delays[i];
                this.frames[i] = new TestFrame(width, height, timestamp);
            }
        }
    }

    public record TestFrame(
        int width,
        int height,
        int timestamp
    ) {
    }
}
