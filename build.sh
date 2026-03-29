#!/bin/bash
set -e

# Vercel runs Amazon Linux 2023 — Node.js v25 (Kotlin 2.3 default) needs libatomic
if command -v dnf &> /dev/null; then
    dnf install -y libatomic
elif command -v yum &> /dev/null; then
    yum install -y libatomic
elif command -v apt-get &> /dev/null; then
    apt-get update && apt-get install -y libatomic1
fi

# Install JDK 17
export JAVA_HOME=$(pwd)/.jdk
mkdir -p $JAVA_HOME
curl -sL https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.10%2B7/OpenJDK17U-jdk_x64_linux_hotspot_17.0.10_7.tar.gz \
  | tar -xz -C $JAVA_HOME --strip-components=1
export PATH=$JAVA_HOME/bin:$PATH

export GRADLE_OPTS="-Xmx2048m -XX:+UseSerialGC"
./gradlew wasmJsBrowserDistribution --no-parallel --max-workers=1
