#!/usr/bin/env bash

if [ -z "$ANDROID_NDK" ]; then
  echo "Please set ANDROID_NDK to the Android NDK folder"
  exit 1
fi

if [ -z "$JAVA_HOME" ]; then
  echo "Please set JAVA_HOME to the java folder"
  exit 1
fi

ROOT_DIR=$(pwd)
BUILD_DIR=${ROOT_DIR}/build

function build_for_android {
  ABI=$1
  ANDROID_SYSTEM_VERSION=$2
  BUILD_TYPE_NAME=$3
  echo $BUILD_TYPE_NAME
  if [[ "$BUILD_TYPE_NAME" == "debug" ]]
  then
    BUILD_TYPE="Debug"
  elif [[ "$BUILD_TYPE_NAME" == "release" ]]
  then
    BUILD_TYPE="Release"
  else
    echo "the BUILD_TYPE_NAME in second argument isn't managed : ${BUILD_TYPE_NAME}"
    exit 1
  fi

  ABI_BUILD_DIR=${ROOT_DIR}/build/${ABI}

  cmake -B${ABI_BUILD_DIR} \
        -H. \
        -DCMAKE_BUILD_TYPE=${BUILD_TYPE} \
        -DANDROID_ABI=${ABI} \
        -DCMAKE_RUNTIME_OUTPUT_DIRECTORY=${ROOT_DIR}/prebuilt/${BUILD_TYPE_NAME}/${ABI}/ \
        -DCMAKE_LIBRARY_OUTPUT_DIRECTORY=${ROOT_DIR}/prebuilt/${BUILD_TYPE_NAME}/${ABI}/ \
        -DCMAKE_ARCHIVE_OUTPUT_DIRECTORY=${ROOT_DIR}/prebuilt/${BUILD_TYPE_NAME}/${ABI}/ \
        -DANDROID_PLATFORM=${ANDROID_SYSTEM_VERSION} \
        -DCMAKE_ANDROID_STL=c++_static \
        -DCMAKE_ANDROID_NDK=$ANDROID_NDK \
        -DCMAKE_TOOLCHAIN_FILE=$ANDROID_NDK/build/cmake/android.toolchain.cmake \
        -DANDROID_TOOLCHAIN=clang \
        -DCMAKE_INSTALL_PREFIX=.

  pushd ${ABI_BUILD_DIR}
    make -j5
  popd

  rm -rf ${ABI_BUILD_DIR}
}

build_for_android armeabi-v7a android-21 debug
build_for_android arm64-v8a android-21 debug
build_for_android x86 android-21 debug
build_for_android x86_64 android-21 debug
build_for_android armeabi-v7a android-21 release
build_for_android arm64-v8a android-21 release
build_for_android x86 android-21 release
build_for_android x86_64 android-21 release