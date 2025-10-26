@echo off
REM Compile tests then run them using JUnit console. Requires junit-platform-console-standalone.jar in lib\
set CP=.;lib\*;src\main\java;src\test\java
javac -cp "%CP%" src\test\java\com\mst\MSTAlgorithmsTests.java
java -jar lib\junit-platform-console-standalone-1.10.2.jar --class-path "%CP%" --scan-class-path
