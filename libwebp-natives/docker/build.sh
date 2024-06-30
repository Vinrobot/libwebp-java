#!/usr/bin/env sh

TARGET="${TARGET:-${1:-all}}"
IMAGE="libwebp-build-$TARGET"

set -ex

exec docker build . -f docker/Dockerfile -t "$IMAGE" --target "$TARGET"
