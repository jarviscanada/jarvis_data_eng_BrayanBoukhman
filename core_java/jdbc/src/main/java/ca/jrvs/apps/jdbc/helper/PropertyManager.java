package ca.jrvs.apps.jdbc.helper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            InputStream inputStream = PropertyManager.class.getClassLoader().getResourceAsStream("properties.txt");
            properties.load(inputStream);
            assert inputStream != null;
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getServer() {
        return properties.getProperty("server");
    }

    public static String getDatabase() {
        return properties.getProperty("database");
    }

    public static int getPort() {
        String portValue = properties.getProperty("port");
        return Integer.parseInt(portValue);
    }

    public static String getUsername() {
        return properties.getProperty("username");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }

    public static String getApiKey() {
        return properties.getProperty("api-key");
    }

}
