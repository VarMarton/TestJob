package controllers.databaseManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.entities.*;
import models.report.Report;
import models.validation.InvalidListing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import controllers.propertyManagement.PropertyManager;
import repository.DatabaseManager;
import controllers.TestJob;

import java.io.IOException;
import java.util.ArrayList;

public class DatabaseController {
    private static final Logger LOGGER = LogManager.getLogger();

    private DatabaseManager databaseManager;

    public DatabaseController(PropertyManager propertyManager){
        final String URL = propertyManager.getProperty("database.url");
        final String USERNAME = propertyManager.getProperty("database.username");
        final String PASSWORD = propertyManager.getProperty("database.password");

        DatabaseManager databaseManager = DatabaseManager.getInstance(URL,USERNAME,PASSWORD);

        if(databaseManager == null){
            TestJob.exitRequest(2);
        }
        else{
            this.databaseManager = databaseManager;
            databaseManager.closeConnection();
        }
    }

    public ArrayList<InvalidListing> saveEntities(Entities entities){
        if(!databaseManager.reconnectToDatabase()){
            TestJob.exitRequest(2);
        }

        for (Marketplace marketplace : entities.getMarketplaces()) {
            databaseManager.saveMarketplace(marketplace);
        }
        for (Location location : entities.getLocations()) {
            databaseManager.saveLocation(location);
        }
        for (ListingStatus listingStatus : entities.getListingStatuses()){
            databaseManager.saveListingStatus(listingStatus);
        }

        Validator validator = new Validator(databaseManager);
        ArrayList<InvalidListing> invalidListings = new ArrayList<>();

        for(Listing listing : entities.getListings()){
            ArrayList<String> invalidFields = validator.validateListing(listing);
            if(invalidFields.size() == 0){
                databaseManager.saveListing(listing);
            }
            else {
                invalidListings.add(createInvalidListing(listing, invalidFields));
            }
        }

        this.databaseManager.closeConnection();

        return invalidListings;
    }

    private InvalidListing createInvalidListing(Listing listing, ArrayList<String> invalidFields){
        InvalidListing invalidListing = new InvalidListing();
        invalidListing.setId(listing.getId());
        try{
            int marketplaceId = Integer.parseInt(listing.getMarketplace());
            invalidListing.setMarketplaceName(databaseManager.getMarketplaceName(marketplaceId));
        }
        catch (Exception ignored){
            invalidListing.setMarketplaceName("null");
        }
        invalidListing.setInvalidFields(invalidFields);
        return invalidListing;
    }

    public String getReport(){
        if(!databaseManager.reconnectToDatabase()){
            TestJob.exitRequest(2);
        }

        String json = null;
        Report report = databaseManager.getReport();
        report.setMonthlyReports(databaseManager.getMonthlyReports());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(report);
        } catch (IOException e) {
            LOGGER.error(e);
            TestJob.exitRequest(3);
        }
        this.databaseManager.closeConnection();

        return json;
    }
}
