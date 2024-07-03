package net.vinrobot.webp.foreign.jdk21.internal;

public enum OperatingSystem {
    UNKNOWN(null, false),
    DARWIN("darwin", false),
    LINUX("linux", true),
    WINDOWS("win32", false),
    SOLARIS("sunos", true),
    FREEBSD("freebsd", true),
    OPENBSD("openbsd", true),
    WINDOWSCE("w32ce", false),
    AIX(null, true),
    GNU(null, false),
    KFREEBSD("kfreebsd", true),
    NETBSD("netbsd", true);

    private final String prefix;
    private final boolean isUnixLike;

    OperatingSystem(String prefix, boolean isUnixLike) {
        this.prefix = prefix;
        this.isUnixLike = isUnixLike;
    }

    public static OperatingSystem fromOSName(final String osName) {
        if (osName.startsWith("Linux")) {
            return LINUX;
        } else if (osName.startsWith("AIX")) {
            return AIX;
        } else if (osName.startsWith("Mac") || osName.startsWith("Darwin")) {
            return DARWIN;
        } else if (osName.startsWith("Windows CE")) {
            return WINDOWSCE;
        } else if (osName.startsWith("Windows")) {
            return WINDOWS;
        } else if (osName.startsWith("Solaris") || osName.startsWith("SunOS")) {
            return SOLARIS;
        } else if (osName.startsWith("FreeBSD")) {
            return FREEBSD;
        } else if (osName.startsWith("OpenBSD")) {
            return OPENBSD;
        } else if (osName.equalsIgnoreCase("gnu")) {
            return GNU;
        } else if (osName.equalsIgnoreCase("gnu/kfreebsd")) {
            return KFREEBSD;
        } else if (osName.equalsIgnoreCase("netbsd")) {
            return NETBSD;
        }
        return UNKNOWN;
    }

    public boolean isUnixLike() {
        return this.isUnixLike;
    }

    public boolean isWindows() {
        return this.equals(WINDOWS) || this.equals(WINDOWSCE);
    }

    public String getPrefix() {
        return this.prefix;
    }
}
