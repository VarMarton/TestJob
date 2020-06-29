package models.validation;

import java.util.ArrayList;

public class InvalidListing {
    private String id;
    private String marketplaceName;
    private ArrayList<String> invalidFields;

    public InvalidListing(){}

    public InvalidListing(String id, String marketplaceName, ArrayList<String> invalidFields) {
        this.id = id;
        this.marketplaceName = marketplaceName;
        this.invalidFields = invalidFields;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarketplaceName() {
        return marketplaceName;
    }

    public void setMarketplaceName(String marketplaceName) {
        this.marketplaceName = marketplaceName;
    }

    public ArrayList<String> getInvalidFields() {
        return invalidFields;
    }

    public void setInvalidFields(ArrayList<String> invalidFields) {
        this.invalidFields = invalidFields;
    }
}
