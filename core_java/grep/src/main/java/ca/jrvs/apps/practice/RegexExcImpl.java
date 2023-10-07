package ca.jrvs.apps.practice;

public class RegexExcImpl implements RegexExc{

    @Override
    public boolean matchJpeg(String filename) {
        return filename.matches("(?i).+\\.(jpg|jpeg)$");
    }

    @Override
    public boolean matchIP(String ip) {
        return ip.matches("^(\\d{1,3}\\.){3}\\d{1,3}$");
    }

    @Override
    public boolean isEmptyLine(String line) {
        return line.matches("^\\s*$");
    }
}