package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaGrepImpl implements JavaGrep{

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String rootPath;
    private String regex;
    private String outFile;

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outfile");
        }

        //Use default logger config
        BasicConfigurator.configure();

        JavaGrepImpl javaGrepImpl = new JavaGrepImpl();
        javaGrepImpl.setRegex(args[0]);
        javaGrepImpl.setRootPath(args[1]);
        javaGrepImpl.setOutFile(args[2]);

        try {
            javaGrepImpl.process();
        } catch (Exception e) {
            javaGrepImpl.logger.error("Error: Unable to process", e);
        }

    }

    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();

        List<File> fileList = listFiles(rootPath);

        if(fileList != null){
            for(File file : fileList) {
                List<String> lines = readLines(file);
                for(String line : lines) {
                    if(containsPattern(line)){
                        matchedLines.add(line);
                    }
                }
            }
        }
        writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir) {
        File rootDirFile = new File(rootDir);

        List<File> fileList = new ArrayList<>();

        if(!rootDirFile.isDirectory()){
            throw new IllegalArgumentException("String roottDir must be a path to a directory");
        }

        File[] rooDirList = rootDirFile.listFiles();

        if(rooDirList != null){
            fileList.addAll(Arrays.stream(rooDirList).filter(File::isFile).collect(Collectors.toList()));
            Arrays.stream(rooDirList).filter(File::isDirectory).forEach(file -> fileList.addAll(listFiles(file.getAbsolutePath())));
        }

        return fileList;
    }

    @Override
    public List<String> readLines(File inputFile) {

        List<String> lines = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile))) {
            lines.addAll(bufferedReader.lines().collect(Collectors.toList()));
        } catch (IOException e) {
            logger.error("Error: Failed to read lines", e);
        }
        return lines;
    }

    @Override
    public boolean containsPattern(String line) {
        return line.matches(regex);
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile))){
            for (String line : lines) {
                bufferedWriter.write(line + "\n");
            }
        } catch (IOException e) {
            logger.error("Error: Failed to write lines", e);
        }
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
