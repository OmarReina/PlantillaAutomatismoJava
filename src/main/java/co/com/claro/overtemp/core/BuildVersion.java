package co.com.claro.overtemp.core;

import com.claro.logger.ClaroLogger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.PostConstruct;

/**
 *
 * @author <a href="mailto:betancourtks@globalhitss.com">Sebastian
 * Betancourt</a>
 */
public class BuildVersion {

    public static Properties properties = new Properties();
    public static String fechaVersion;
    public static String version;

    @PostConstruct
    public static void init() {
        try {
            InputStream values = BuildVersion.class.getClassLoader().getResourceAsStream("build.properties");
            if (values != null) {
                properties.load(values);
                fechaVersion = properties.getProperty("build.date", "dd/MM/yyyy HH:mm");
                version = properties.getProperty("build.version");
            }
        } catch (IOException ex) {
            ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
            StackTraceElement[] stackTrace = ex.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
                ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
                ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
            };
        }
    }
}
