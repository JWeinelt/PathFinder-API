#!/bin/bash
# Script to run the Minecraft client with PathFinder-API
# This script should be run in an environment with internet access

echo "Setting up Java 8 environment..."
export JAVA_HOME=/usr/lib/jvm/temurin-8-jdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

echo "Java version:"
java -version

echo "Cleaning previous builds..."
./gradlew clean --no-daemon

echo "Setting up Minecraft development environment..."
./gradlew setupDecompWorkspace --no-daemon

echo "Building the mod..."
./gradlew build --no-daemon

echo "Running Minecraft client with PathFinder-API..."
./gradlew runClient --no-daemon

echo "If the above commands succeed, the runClient task should work properly."
echo "The fixes made should prevent the following issues:"
echo "1. ClassNotFoundException from scala.actors.threadpool.Arrays import"
echo "2. Resource loading failures from incorrect File usage"
echo "3. Gradle dependency resolution issues"