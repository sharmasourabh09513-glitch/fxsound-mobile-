@echo off
setlocal

set DIR=%~dp0
rem Prefer JAVA_HOME if it points to a real java.exe, otherwise fall back to PATH.
set JAVA_EXE=
if defined JAVA_HOME (
  if exist "%JAVA_HOME%\bin\java.exe" (
    set JAVA_EXE=%JAVA_HOME%\bin\java.exe
  )
)
if not defined JAVA_EXE (
  set JAVA_EXE=java.exe
)

set CLASSPATH=%DIR%gradle\wrapper\gradle-wrapper.jar

"%JAVA_EXE%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
