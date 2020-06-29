package controllers.ftpServerManagement;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import controllers.propertyManagement.PropertyManager;
import controllers.TestJob;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FTPServerController {
    private static final Logger LOGGER = LogManager.getLogger();

    private boolean success;

    private final String HOSTNAME;
    private final String USERNAME;
    private final String PASSWORD;

    public FTPServerController(PropertyManager propertyManager){
        this.HOSTNAME = propertyManager.getProperty("ftp.hostname");
        this.USERNAME = propertyManager.getProperty("ftp.username");
        this.PASSWORD = propertyManager.getProperty("ftp.password");
    }

    public void sendFileToFTP(String filePath){
        this.success = true;

        FTPClient client = new FTPClient();
        FileInputStream fis = null;

        try {
            client.connect(this.HOSTNAME);
            client.login(this.USERNAME, this.PASSWORD);

            File file = new File(filePath);
            String filename = file.getAbsolutePath();
            fis = new FileInputStream(filename);

            client.storeFile(file.getName(), fis);
            client.logout();
        } catch (IOException e) {
            LOGGER.error(e);
            this.success = false;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                client.disconnect();
            } catch (IOException e) {
                LOGGER.error(e);
                TestJob.exitRequest(4);
            }
        }
        if(!this.success){
            TestJob.exitRequest(4);
        }
    }
}
