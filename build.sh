#!/bin/bash
set -e

# Install JDK
export JAVA_HOME=$(pwd)/.jdk
mkdir -p $JAVA_HOME
curl -sL https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.10%2B7/OpenJDK17U-jdk_x64_linux_hotspot_17.0.10_7.tar.gz \
  | tar -xz -C $JAVA_HOME --strip-components=1
export PATH=$JAVA_HOME/bin:$PATH

# Remove cached incompatible Node.js versions (v25 requires libatomic)
rm -rf ~/.gradle/nodejs/node-v25*

./gradlew wasmJsBrowserDistribution