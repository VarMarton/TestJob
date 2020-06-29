package logging;

import models.validation.InvalidListing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class CSVLogger {
    private static final Logger LOGGER = LogManager.getLogger(CSVLogger.class);

    public static void logInvalidListings(String path, ArrayList<InvalidListing> invalidListings){
        for(InvalidListing invalidListing : invalidListings){
            for (String invalidListingField : invalidListing.getInvalidFields()){
                LOGGER.info("Invalid listing",invalidListing.getId(), invalidListing.getMarketplaceName(), invalidListingField);
            }
        }
    }
}
