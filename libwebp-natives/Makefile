DOCKER_CP=./docker/copy.sh

DOCKER_PREFIX=libwebp-build-

docker-build-%: docker/Dockerfile
	docker build . -f docker/Dockerfile -t "$(DOCKER_PREFIX)$*" --target "$*"

linux-x64: % : docker-build-%
	$(DOCKER_CP) "$(DOCKER_PREFIX)$@" /libwebp/output/linux-x64/lib/libwebp.so src/linux-x64/resources/linux-x86-64/liblibwebp_animdecoder.so

windows-x86: % : docker-build-%
	$(DOCKER_CP) "$(DOCKER_PREFIX)$@" /libwebp/output/windows-x86/lib/libwebp.dll src/windows-x86/resources/win32-x86/libwebp_animdecoder.dll

windows-x64: % : docker-build-%
	$(DOCKER_CP) "$(DOCKER_PREFIX)$@" /libwebp/output/windows-x64/lib/libwebp.dll src/windows-x64/resources/win32-x86-64/libwebp_animdecoder.dll

darwin-x64: % : docker-build-%
	$(DOCKER_CP) "$(DOCKER_PREFIX)$@" /libwebp/output/darwin-x64/lib/libwebp.dylib src/darwin-x64/resources/darwin-x86-64/liblibwebp_animdecoder.dylib

darwin-aarch64: % : docker-build-%
	$(DOCKER_CP) "$(DOCKER_PREFIX)$@" /libwebp/output/darwin-aarch64/lib/libwebp.dylib src/darwin-aarch64/resources/darwin-aarch64/liblibwebp_animdecoder.dylib

all: % : docker-build-% linux-x64 windows-x86 windows-x64 darwin-x64 darwin-aarch64
