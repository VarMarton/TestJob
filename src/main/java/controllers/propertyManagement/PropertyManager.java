package controllers.propertyManagement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import controllers.TestJob;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
    private static final Logger LOGGER = LogManager.getLogger();

    private static PropertyManager propertyManager = null;
    private static String propertiesPath = null;

    private Properties prop = new Properties();

    private PropertyManager(String propertiesPath){
        try (FileInputStream input = new FileInputStream(propertiesPath)){
            try {
                prop.load(input);
                PropertyManager.propertiesPath = propertiesPath;
            } catch (IOException e) {
                LOGGER.error(e);
            }
        } catch (IOException e) {
            LOGGER.error(e);
            TestJob.exitRequest(1);
        }
    }

    public static PropertyManager getInstance(String propertiesPath){
        if(PropertyManager.propertiesPath == null){
            PropertyManager.propertyManager = new PropertyManager(propertiesPath);
        }
        else if(!PropertyManager.propertiesPath.equals(propertiesPath)){
            LOGGER.error("PropertyManager is already created with an other filepath!");
        }
        return PropertyManager.propertyManager;
    }

    public String getProperty(String key){
        return prop.getProperty(key);
    }
}
