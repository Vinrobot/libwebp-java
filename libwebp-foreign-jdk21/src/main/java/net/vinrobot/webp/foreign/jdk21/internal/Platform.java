package net.vinrobot.webp.foreign.jdk21.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class Platform {
    private Platform() {
        throw new IllegalCallerException();
    }

    public static Path extractFromResourcePath(final String libName) throws IOException {
        return extractFromResourcePath(libName, null);
    }

    public static Path extractFromResourcePath(final String libName, ClassLoader loader) throws IOException {
        if (loader == null) {
            loader = Thread.currentThread().getContextClassLoader();
            // Context class loader is not guaranteed to be set
            if (loader == null) {
                loader = Platform.class.getClassLoader();
            }
        }

        final String osName = System.getProperty("os.name");
        final String osArch = System.getProperty("os.arch");
        final OperatingSystem os = OperatingSystem.fromOSName(osName);
        final String resourcePrefix = getNativeLibraryResourcePrefix(os, osArch, osName);
        final String resourcePath = resourcePrefix + "/" + mapSharedLibraryName(os, libName);
        final URL url = loader.getResource(resourcePath);
        if (url == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }

        final Path tempDir = getCacheDir(os, "libwebp-java");
        tempDir.toFile().mkdirs();

        final Path tempFile = Files.createTempFile(tempDir, "lib", os.isWindows() ? ".dll" : null);
        tempFile.toFile().deleteOnExit();

        try (final InputStream is = url.openStream()) {
            Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }
        return tempFile;
    }

    public static String mapSharedLibraryName(final OperatingSystem os, final String libName) {
        final String mappedName = System.mapLibraryName(libName);
        if (OperatingSystem.DARWIN.equals(os) && mappedName.endsWith(".jnilib")) {
            // On MacOSX, System.mapLibraryName() returns the .jnilib extension
            // (the suffix for JNI libraries); ordinarily shared libraries have
            // a .dylib suffix
            return mappedName.substring(0, mappedName.lastIndexOf(".jnilib")) + ".dylib";
        }
        return mappedName;
    }

    public static String getNativeLibraryResourcePrefix(OperatingSystem os, String arch, String name) {
        String osPrefix = os.getPrefix();
        if (osPrefix == null) {
            osPrefix = name.toLowerCase();
            final int space = osPrefix.indexOf(" ");
            if (space != -1) {
                osPrefix = osPrefix.substring(0, space);
            }
        }
        arch = Platform.getCanonicalArchitecture(arch);
        return osPrefix + "-" + arch;
    }

    public static Path getCacheDir(final OperatingSystem os, final String appName) {
        return getCacheDir(os).resolve(appName);
    }

    public static Path getCacheDir(final OperatingSystem os) {
        if (OperatingSystem.DARWIN.equals(os)) {
            // https://developer.apple.com/library/archive/documentation/FileManagement/Conceptual/FileSystemProgrammingGuide/MacOSXDirectories/MacOSXDirectories.html
            return Path.of(System.getProperty("user.home"), "Library", "Caches");
        } else if (os.isUnixLike()) {
            // https://standards.freedesktop.org/basedir-spec/basedir-spec-latest.html
            // The XDG_CACHE_DIR is expected to be per user
            final String xdgCacheHome = System.getenv("XDG_CACHE_HOME");
            if (xdgCacheHome == null || xdgCacheHome.isBlank()) {
                return Path.of(System.getProperty("user.home"), ".cache");
            } else {
                return Path.of(xdgCacheHome);
            }
        } else {
            // Loading DLLs via System.load() under a directory with a unicode
            // name will fail on windows, so use a hash code of the user's
            // name in case the user's name contains non-ASCII characters
            final int userHash = System.getProperty("user.name").hashCode();
            final String tmpDir = System.getProperty("java.io.tmpdir");
            return Path.of(tmpDir, String.valueOf(userHash));
        }
    }

    public static String getCanonicalArchitecture(String arch) {
        arch = arch.toLowerCase().trim();
        if ("powerpc".equals(arch)) {
            arch = "ppc";
        } else if ("powerpc64".equals(arch)) {
            arch = "ppc64";
        } else if ("i386".equals(arch) || "i686".equals(arch)) {
            arch = "x86";
        } else if ("x86_64".equals(arch) || "amd64".equals(arch)) {
            arch = "x86-64";
        } else if ("zarch_64".equals(arch)) {
            arch = "s390x";
        }
        // Work around OpenJDK mis-reporting os.arch
        // https://bugs.openjdk.java.net/browse/JDK-8073139
        if ("ppc64".equals(arch) && "little".equals(System.getProperty("sun.cpu.endian"))) {
            arch = "ppc64le";
        }
        return arch;
    }
}
