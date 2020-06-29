package controllers.restApiManagement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.entities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import controllers.propertyManagement.PropertyManager;
import controllers.TestJob;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RestApiController {
    private static final Logger LOGGER = LogManager.getLogger();

    private PropertyManager propertyManager;
    private ObjectMapper objectMapper;

    public RestApiController(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
        this.objectMapper = new ObjectMapper();
    }

    public Entities loadDataFromApi(){
        Entities entities = new Entities();
        try {
            String urlOfMarketplaces = propertyManager.getProperty("marketplace.url");
            String jsonStringOfMarketplaces = this.getJsonStringFromApi(urlOfMarketplaces);
            entities.setMarketplaces(objectMapper.readValue(jsonStringOfMarketplaces, new TypeReference<List<Marketplace>>(){}));

            String urlOfListingStatuses = propertyManager.getProperty("listingStatus.url");
            String jsonStringOfListingStatuses = this.getJsonStringFromApi(urlOfListingStatuses);
            entities.setListingStatuses(objectMapper.readValue(jsonStringOfListingStatuses, new TypeReference<List<ListingStatus>>(){}));

            String urlOfLocations = propertyManager.getProperty("location.url");
            String jsonStringOfLocations = this.getJsonStringFromApi(urlOfLocations);
            entities.setLocations(objectMapper.readValue(jsonStringOfLocations, new TypeReference<List<Location>>(){}));

            String urlOfListings = propertyManager.getProperty("listing.url");
            String jsonStringOfListings = this.getJsonStringFromApi(urlOfListings);
            entities.setListings(objectMapper.readValue(jsonStringOfListings, new TypeReference<List<Listing>>(){}));

        } catch (Exception e) {
            LOGGER.error(e);
            TestJob.exitRequest(3);
        }
        return entities;
    }

    private String getJsonStringFromApi(String request){
        String jsonString = null;

        try{
            URL url = new URL(request);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.addRequestProperty("User-Agent","Client");
            connection.setDoOutput(true);

            try (
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
            ){
                jsonString = reader.readLine();
            }
            catch (Exception e){
                LOGGER.error(e);
            }

            connection.disconnect();
        }catch(Exception e) {
            LOGGER.error(e);
        }

        return jsonString;
    }
}
