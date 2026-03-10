#!/usr/bin/env sh

# Simplified Gradle Wrapper bootstrap script.
# For full wrapper behavior, regenerate via: gradle wrapper

DIR="$(cd "$(dirname "$0")" && pwd)"

JAVA_CMD="java"
if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ]; then
  JAVA_CMD="$JAVA_HOME/bin/java"
fi

CLASSPATH="$DIR/gradle/wrapper/gradle-wrapper.jar"
exec "$JAVA_CMD" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
