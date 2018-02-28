# SyntheticSystemLoadTools
A collection of console based tools to simulate synthetic load on CPU, RAM and Disk. Currently in (early) development.

Note: Requires Java Runtime Environment

Attention: The IOLoad.jar generates a custom set amount of .txt files in the temporary directory of the OS.
These generated files will not be deleted by the application (yet). Currently working on that.
For now they have to be deleted manually by changing to the temporary directory (%TEMP% on Windows / $TMPDIR on Unix)

HOW TO RUN:

1. Open command prompt
2. Change to the directory containing the .jar files
3. For example: to start the CPULoad tool type: java -jar CPULoad.jar
4. Program should start in the console and give feedback.
