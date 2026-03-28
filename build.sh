#!/bin/bash
set -e

# Install JDK
export JAVA_HOME=$(pwd)/.jdk
mkdir -p $JAVA_HOME
curl -sL https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.10%2B7/OpenJDK17U-jdk_x64_linux_hotspot_17.0.10_7.tar.gz \
  | tar -xz -C $JAVA_HOME --strip-components=1
export PATH=$JAVA_HOME/bin:$PATH

# Pre-place Node 20 where Kotlin expects Node 25
# so it skips downloading v25 (which needs libatomic)
NODE_KOTLIN_PATH="/root/.gradle/nodejs/node-v25.0.0-linux-x64"
mkdir -p $NODE_KOTLIN_PATH
curl -sL https://nodejs.org/dist/v20.11.0/node-v20.11.0-linux-x64.tar.gz \
  | tar -xz -C $NODE_KOTLIN_PATH --strip-components=1

./gradlew wasmJsBrowserDistribution