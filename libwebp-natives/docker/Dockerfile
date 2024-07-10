FROM debian:12 AS base

WORKDIR /libwebp
COPY vendor/libwebp .
COPY docker/export.c .

RUN set -ex \
    && apt update \
    && apt install -y \
        autoconf \
        automake \
		libtool \
        make


FROM base AS windows-deps
RUN set -ex \
	&& apt install -y \
		g++-mingw-w64 \
		gcc-mingw-w64 \
    && autoreconf -fi


FROM windows-deps AS windows-x86
ENV TARGET=i686-w64-mingw32
ENV OUTPUT=/libwebp/output/windows-x86
RUN set -ex \
	&& ./configure --prefix="$OUTPUT" --host="$TARGET" CFLAGS='-fPIC -O2 -g -DWEBP_DLL' \
	&& make -j$(nproc) \
	&& make install \
    && cd "$OUTPUT/lib" \
    && "$TARGET"-gcc -c -g -I.. -DWEBP_DLL /libwebp/export.c \
    && "$TARGET"-gcc -shared -static-libgcc -o libwebp.dll export.o libwebp.a libwebpdemux.a \
    && "$TARGET"-strip --strip-debug libwebp.dll


FROM windows-deps AS windows-x64
ENV TARGET=x86_64-w64-mingw32
ENV OUTPUT=/libwebp/output/windows-x64
RUN set -ex \
	&& ./configure --prefix="$OUTPUT" --host="$TARGET" CFLAGS='-fPIC -O2 -g -DWEBP_DLL' \
	&& make -j$(nproc) \
	&& make install \
    && cd "$OUTPUT/lib" \
    && "$TARGET"-gcc -c -g -I.. -DWEBP_DLL /libwebp/export.c \
    && "$TARGET"-gcc -shared -o libwebp.dll export.o libwebp.a libwebpdemux.a \
    && "$TARGET"-strip --strip-debug libwebp.dll


FROM base AS windows-all
COPY --from=windows-x86 /libwebp/output/ output/
COPY --from=windows-x64 /libwebp/output/ output/


FROM base AS linux-deps
RUN set -ex \
	&& apt install -y \
		gcc \
    && autoreconf -fi


FROM linux-deps AS linux-x64
ENV TARGET=x86_64-linux-gnu
ENV OUTPUT=/libwebp/output/linux-x64
RUN set -ex \
	&& ./configure --prefix="$OUTPUT" --host="$TARGET" CFLAGS='-fPIC -O2 -g' \
	&& make -j$(nproc) \
	&& make install \
    && cd "$OUTPUT/lib" \
    && "$TARGET"-gcc -c -g -I.. /libwebp/export.c \
    && "$TARGET"-gcc -shared -o libwebp.so export.o libwebp.a libwebpdemux.a \
    && strip --strip-debug libwebp.so


FROM base AS linux-all
COPY --from=linux-x64 /libwebp/output/ output/


FROM ghcr.io/crazy-max/osxcross:13.1-debian AS osxcross
FROM base AS darwin-deps
ENV PATH="/osxcross/bin:$PATH"
ENV LD_LIBRARY_PATH="/osxcross/lib:$LD_LIBRARY_PATH"
RUN --mount=type=bind,from=osxcross,source=/osxcross,target=/osxcross set -ex \
    && apt install -y \
      clang \
	&& autoreconf -fi


FROM darwin-deps AS darwin-x64
ENV TARGET=x86_64-apple-darwin22.2
ENV OUTPUT=/libwebp/output/darwin-x64
RUN --mount=type=bind,from=osxcross,source=/osxcross,target=/osxcross set -ex \
	&& CC="$TARGET"-clang CXX="$TARGET"-clang++ ./configure --prefix="$OUTPUT" --host="$TARGET" CFLAGS='-fPIC -O2 -g' \
	&& make -j$(nproc) \
	&& make install \
    && cd "$OUTPUT/lib" \
    && "$TARGET"-clang -c -g -I.. /libwebp/export.c \
    && "$TARGET"-clang -shared -o libwebp.dylib export.o libwebp.a libwebpdemux.a \
    && "$TARGET"-strip -S libwebp.dylib


FROM darwin-deps AS darwin-aarch64
ENV TARGET=aarch64-apple-darwin22.2
ENV OUTPUT=/libwebp/output/darwin-aarch64
RUN --mount=type=bind,from=osxcross,source=/osxcross,target=/osxcross set -ex \
	&& CC="$TARGET"-clang CXX="$TARGET"-clang++ ./configure --prefix="$OUTPUT" --host="$TARGET" CFLAGS='-fPIC -O2 -g' \
	&& make -j$(nproc) \
	&& make install \
    && cd "$OUTPUT/lib" \
    && "$TARGET"-clang -c -g -I.. /libwebp/export.c \
    && "$TARGET"-clang -shared -o libwebp.dylib export.o libwebp.a libwebpdemux.a \
    && "$TARGET"-strip -S libwebp.dylib


FROM base AS darwin-all
COPY --from=darwin-x64 /libwebp/output/ output/
COPY --from=darwin-aarch64 /libwebp/output/ output/


FROM base AS all
COPY --from=windows-all /libwebp/output/ output/
COPY --from=linux-all /libwebp/output/ output/
COPY --from=darwin-all /libwebp/output/ output/

RUN find output -type f \
        -iname "*.so" \
        -or -iname "*.dylib" \
        -or -iname "*.dll" > output/files.txt
