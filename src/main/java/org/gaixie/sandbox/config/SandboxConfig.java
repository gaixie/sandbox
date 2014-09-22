package org.gaixie.sandbox.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sandbox 配置，加载 sandbox.properties 配置文件。
 */
public class SandboxConfig {
    private static final Logger logger = LoggerFactory.getLogger(SandboxConfig.class);
    private static String PROPERTIES_FILE = "sandbox.properties";
    private static Properties prop;

    static {
        prop = new Properties();
        try {
            prop.load(SandboxConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
            logger.info("Successfully loaded "+PROPERTIES_FILE+".");
        } catch (Exception e) {
            logger.error("Failed to load file: "+PROPERTIES_FILE+".", e);
        }
    }

    // 这个类就不用这样实例化了。:-)
    private SandboxConfig() {}

    /**
     * Retrieve a property value
     * @param     key Name of the property
     * @return    String Value of property requested, null if not found
     */
    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    /**
     * 得到 sandbox 的当前配置。
     * @return Properties
     */
    public static Properties getProperties() {
        return prop;
    }
}
