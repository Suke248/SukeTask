package libraries;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    public static String getConfigValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//config//config.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }
}
