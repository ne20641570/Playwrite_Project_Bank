package config;

import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            properties = PropertyUtils.loadProperties("src/main/resources/config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void setProperties(Properties properties) {
        ConfigReader.properties = properties;
    }
}
