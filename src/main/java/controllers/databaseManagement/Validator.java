package controllers.databaseManagement;

import models.entities.Listing;
import org.apache.commons.validator.routines.EmailValidator;
import repository.DatabaseManager;

import java.util.ArrayList;
import java.util.UUID;

public class Validator {
    private final int MAX_DECIMAL = 2;
    private final int LENGTH_OF_CURRENCY = 3;
    
    private DatabaseManager databaseManager;

    public Validator(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public ArrayList<String> validateListing(Listing listing){
        ArrayList<String> invalidFields = new ArrayList<>();

        if(!isValidListingId(listing.getId())) invalidFields.add("id");
        if(!isValidListingTitleOrDescription(listing.getTitle())) invalidFields.add("title");
        if(!isValidListingTitleOrDescription(listing.getDescription())) invalidFields.add("description");
        if(!isValidLocationId(listing.getInventoryItemLocationId())) invalidFields.add("location_id");
        if(!isValidListingPrice(listing.getListingPrice())) invalidFields.add("listing_price");
        if(!isValidCurrency(listing.getCurrency())) invalidFields.add("currency");
        if(!isValidQuantity(listing.getQuantity())) invalidFields.add("quantity");
        if(!isValidListingStatus(listing.getListingStatus())) invalidFields.add("listing_status");
        if(!isValidMarketplace(listing.getMarketplace())) invalidFields.add("marketplace");
        if(!isValidEmailAddress(listing.getOwnerEmailAddress())) invalidFields.add("owner_email_address");

        return invalidFields;
    }

    public boolean isValidListingId(String id){
        boolean isValid = false;
        if(id != null) {
            try {
                UUID.fromString(id);
                isValid = true;
            }
            catch (Exception ignored){}
        }
        return isValid;
    }

    public boolean isValidListingTitleOrDescription(String titleOrDescription){
        boolean isValid = false;
        if(titleOrDescription != null) {
            if(titleOrDescription.length() > 0) {
                isValid = true;
            }
        }
        return isValid;
    }

    public boolean isValidLocationId(String locationId){
        boolean isValid = false;
        if(locationId != null){
            try {
                UUID uuid = UUID.fromString(locationId);
                if(databaseManager.isValidLocationId(uuid)){
                    isValid = true;
                }
            }
            catch (Exception ignored){}
        }
        return isValid;
    }

    public boolean isValidListingPrice(String price){
        boolean isValid = false;
        if(price != null){
            try {
                double priceAsDouble = Double.parseDouble(price);
                if(priceAsDouble > 0){
                    if(price.indexOf('.') < 0) {
                        isValid = true;
                    }
                    else if((price.length() - price.indexOf('.') - 1) <= MAX_DECIMAL){
                        isValid = true;
                    }
                }
            }
            catch (Exception ignored){}
        }
        return isValid;
    }

    public boolean isValidCurrency(String currency){
        boolean isValid = false;
        if(currency != null){
            if(currency.length() == LENGTH_OF_CURRENCY){
                isValid = true;
            }
        }
        return isValid;
    }

    public boolean isValidQuantity(String quantity){
        boolean isValid = false;
        if(quantity != null){
            try {
                int quantityAsInt = Integer.parseInt(quantity);
                if(quantityAsInt > 0){
                    isValid = true;
                }
            }
            catch (Exception ignored){}
        }
        return isValid;
    }

    public boolean isValidListingStatus(String listingStatus){
        boolean isValid = false;
        if(listingStatus != null){
            try {
                int listingStatusId = Integer.parseInt(listingStatus);
                if(databaseManager.isValidListingStatusId(listingStatusId)){
                    isValid = true;
                }
            }
            catch (Exception ignored){}
        }
        return isValid;
    }

    public boolean isValidMarketplace(String marketplace){
        boolean isValid = false;
        if(marketplace != null){
            try {
                int marketplaceId = Integer.parseInt(marketplace);
                if(databaseManager.isValidMarketplaceId(marketplaceId)){
                    isValid = true;
                }
            }
            catch (Exception ignored){}
        }
        return isValid;
    }

    public boolean isValidEmailAddress(String email){
        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(email);
    }
}
