package controllers;

import controllers.databaseManagement.DatabaseController;
import controllers.ftpServerManagement.FTPServerController;
import logging.CSVLogger;
import models.entities.Entities;
import models.validation.InvalidListing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import controllers.propertyManagement.PropertyManager;
import controllers.restApiManagement.RestApiController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TestJob {
    private static final Logger LOGGER = LogManager.getLogger();

    private static boolean exitRequested = false;
    private static int exitCode = -1;

    private final String PROPERTIES_LOCATION = "src/main/resources/config.properties";
    private final String REPORT_LOCATION = "out/report.json";

    private PropertyManager propertyManager;
    private DatabaseController databaseController;
    private FTPServerController ftpServerController;
    private RestApiController restApiController;
    private Entities entities;
    private String reportJson;

    public TestJob(){ }

    public void run(){
        LOGGER.info("TestJob has started.");

        this.propertyManager = PropertyManager.getInstance(PROPERTIES_LOCATION);
        this.checkExitRequest();

        this.databaseController = new DatabaseController(this.propertyManager);
        this.checkExitRequest();

        this.restApiController = new RestApiController(this.propertyManager);
        this.entities = this.restApiController.loadDataFromApi();
        this.checkExitRequest();

        ArrayList<InvalidListing> invalidListings = this.databaseController.saveEntities(this.entities);
        this.checkExitRequest();
        CSVLogger.logInvalidListings("",invalidListings);

        this.reportJson = this.databaseController.getReport();
        this.checkExitRequest();
        this.writeJsonToFile(this.REPORT_LOCATION, this.reportJson);
        this.checkExitRequest();

        this.ftpServerController = new FTPServerController(this.propertyManager);
        this.ftpServerController.sendFileToFTP(this.REPORT_LOCATION);
        this.checkExitRequest();

        LOGGER.info("TestJob has finished.");
    }

    public static void exitRequest(int exitCode){
        TestJob.exitRequested = true;
        TestJob.exitCode = exitCode;
    }

    public static boolean getExitRequested(){
        return TestJob.exitRequested;
    }

    private void writeJsonToFile(String path, String json){
        try {
            File file = new File(path);
            file.getParentFile().mkdirs();
            file.createNewFile();

            try (BufferedWriter out = new BufferedWriter(new FileWriter(file,false))) {
                out.write(json);
            }
        } catch (IOException e) {
            LOGGER.error(e);
            TestJob.exitRequest(3);
        }
    }

    private void checkExitRequest(){
        if(TestJob.exitRequested){
            if(exitCode == 0){
                LOGGER.info("TestJob has finished.");
            }
            else {
                LOGGER.info("TestJob has finished with error.");
            }
            System.exit(exitCode);
        }
    }

}
