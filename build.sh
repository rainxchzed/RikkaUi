#!/bin/bash
set -e

# Install JDK 17
export JAVA_HOME=$(pwd)/.jdk
mkdir -p $JAVA_HOME
curl -sL https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.10%2B7/OpenJDK17U-jdk_x64_linux_hotspot_17.0.10_7.tar.gz \
  | tar -xz -C $JAVA_HOME --strip-components=1
export PATH=$JAVA_HOME/bin:$PATH

# Nuke any cached Node.js so Gradle downloads the pinned v22.12.0
rm -rf ~/.gradle/nodejs

# Install yarn (Kotlin/JS uses yarn for dependency resolution)
npm install -g yarn

./gradlew wasmJsBrowserDistribution
