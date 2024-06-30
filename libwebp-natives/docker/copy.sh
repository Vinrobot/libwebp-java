#!/usr/bin/env sh

IMAGE="$1"
SRC="$2"
DST="$3"

set -e

CONTAINER_ID=$(docker create "$IMAGE")

RET=0; docker cp "$CONTAINER_ID:$SRC" "$DST" || RET=$?

docker rm "$CONTAINER_ID" >/dev/null

exit $RET
