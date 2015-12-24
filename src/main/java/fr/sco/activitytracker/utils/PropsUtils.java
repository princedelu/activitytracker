package fr.sco.activitytracker.utils;

/**
 * Created by stiffler on 14/03/15.
 */
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.net.URL;

public class PropsUtils {

    private PropsUtils() {  }
    /**
     * Load a properties file from the classpath
     * @param propsName
     * @return Properties
     * @throws Exception
     */
    public static Properties loadClassPath(String propsName) throws Exception {
        Properties props = new Properties();
        URL url = ClassLoader.getSystemResource(propsName);
        props.load(url.openStream());
        return props;
    }

    /**
     * Load a Properties File Path
     * @param propsFilePath
     * @return Properties
     * @throws IOException
     */
    public static Properties loadFilePath(String propsFilePath) throws IOException {
        return loadFile(new java.io.File( propsFilePath));
    }

    /**
     * Load a Properties File
     * @param propsFile
     * @return Properties
     * @throws IOException
     */
    public static Properties loadFile(File propsFile) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(propsFile);
        props.load(fis);
        fis.close();
        return props;
    }
}
