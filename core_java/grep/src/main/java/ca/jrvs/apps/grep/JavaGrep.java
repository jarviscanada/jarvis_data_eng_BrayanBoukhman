package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {
    /**
     * Top level search workflow
     * @throws IOException
     */
    void process() throws IOException;

    /**
     * Traverse a given directory and return all files
     * @param rootDir input directory
     * @return files under rootDir
     */
    List<File> listFiles(String rootDir);

    /**
     * Read a file and return all the lines
     * @param inputFile file to be read
     * @return BufferedReader
     * @throws IllegalArgumentException if given inputFIle is not a file
     */
    BufferedReader readLines(File inputFile) throws FileNotFoundException;

    /**
     * check if a line contains the regex pattern (passed by user)
     * @param line input string
     * @return true if there is a match
     */
    boolean containsPattern(String line);

    void writeToFile(List<String> lines) throws IOException;

    String getRootPath();

    void setRootPath(String rootPath);

    String getRegex();

    void setRegex(String regex);

    String getOutFile();

    void setOutFile(String outFile);

}
