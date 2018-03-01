# SyntheticSystemLoadTools
A collection of console based tools to simulate synthetic load on CPU, RAM and Disk. Currently in (early) development.

Note: Requires Java Runtime Environment

Disclaimer: This tool may lower the lifespan of system hardware. Execution under own responsibility. I will not assume any responsibility or liability for any hardware damages.


Latest Changelog: 

  - Now also deletes the generated temporary files. Working on Windows 7, untested on any other platform yet. 
    (If not working, the dummy files are stored in the temporary directory of your OS. Deletion of those is safe.)
  - CPULoad console output tidied up.
  
HOW TO RUN:

1. Open command prompt
2. Change to the directory containing the .jar files
3. For example: to start the CPULoad tool type: java -jar CPULoad.jar
4. Program should start in the console and give feedback.
