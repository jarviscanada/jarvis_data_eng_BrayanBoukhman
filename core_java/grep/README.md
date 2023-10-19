# Introduction
The Grep application, written in Java, emulates the functionality of the Linux grep command. This application scans files, identifies lines that match a given regular expression, and saves them to a file. It starts its search from a designated root directory, conducting a recursive search across all directories and files. Developed using IntelliJ IDE, Core Java, and Maven, this application is also deployed through Docker.
# Quick Start
The application accepts three arguments: `regex` `rootPath` `outFile`
- `regex`: regular expression to match
- `rootPath`: root directory path to search
- `outFile`: output file path

Begin from root project directory.

1. Jar file
```
# compile and package Java code
mvn clean package

# run(example)
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp ".*\bpublic void\b.*" /home/centos/dev/jarvis_data_eng_BrayanBoukhman/core_java/grep/src /tmp/grep.out
```
2. Docker
```
# get docker image
docker pull fuzzyking113/grep

# run(example)
docker run --rm -v `pwd`/data:/data -v `pwd`/log:/log fuzzyking113/grep ".*\bpublic void\b.*" /home/centos/dev/jarvis_data_eng_BrayanBoukhman/core_java/grep/src /tmp/grep.out
```

## Implemenation
The application was implemented in IntelliJ IDE using Core Java which utilized libraries and functionalities such as Collections, Stream API and Lambda expressions. The SLF4J package was used for logging. Maven was used to build the app and manage the dependencies.
## Pseudocode
The `process` method is the main driver of the grep app.
```
matchedLines = []
for file in listFilesRecursively(rootDir)
    for line in readLines(file)
        if containsPattern(line)
            matchedLines.add(line)
writeToFile(matchedLines)
```

## Performance Issue
Running the application might result in an OutOfMemoryError exception, which occurs when the JVM exhausts its heap memory while executing the application. This issue can manifest when processing large data files. There are several potential solutions to mitigate this problem:
- ##### Increase Heap Memory
  - You can specify the minimum/initial and maximum heap memory sizes for the JVM when running the application. This can be accomplished by including additional options during execution:
    - `-Xms` for min/initial heap size
    - `-Xmx` for max heap size

- ##### BufferedReader
  The BufferedReader class enables the program to read data from a file in smaller segments rather than loading it all at once. This approach mitigates the risk of errors by ensuring that only portions of the data file are temporarily stored in the heap.
- ##### Stream APIs
  The Stream API enables the program to perform various intermediate operations on the data before concluding with a terminal operation. Streams adhere to a lazy evaluation approach, meaning that intermediate operations are executed only when a terminal operation is invoked. When used in conjunction with BufferedReader, data is read from a file following a terminal stream operation. This method facilitates processing data one piece at a time, resulting in decreased heap memory utilization.
# Test
The application was tested manually using the following procedure:
1. Create text files placed within a directory.
2. Run the Grep app using IntelliJ with desired arguments
3. Compare output with expected results


Test Cases:
- Number of arguments not equal to 3
- Different regex, rootPath, outFile
- Modify text files
- Change heap size
# Deployment
The application was dockerized using Docker and published on Docker Hub for easier distribution. The Docker image can be obtained by running `docker pull fuzzyking113/grep` on the command line. Source code is managed by GitHub.

Dockerfile used for building Docker image:
```
FROM openjdk:8-alpine
COPY target/grep*.jar /usr/local/app/grep/lib/grep.jar
ENTRYPOINT ["java","-jar","/usr/local/app/grep/lib/grep.jar"]
```

# Improvement
- Enhance performance by modifying methods to return a Stream instead of Collections.
- Include details (such as path, file, and line number) of the matched line in the output file.
- Implement the ability to search threw directories of devices on a network.