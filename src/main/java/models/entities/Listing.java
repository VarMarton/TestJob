package models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Listing {
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("location_id")
    private String inventoryItemLocationId;
    @JsonProperty("listing_price")
    private String listingPrice;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("listing_status")
    private String listingStatus;
    @JsonProperty("marketplace")
    private String marketplace;
    @JsonProperty("upload_time")
    private String uploadTime;
    @JsonProperty("owner_email_address")
    private String ownerEmailAddress;

    public Listing(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInventoryItemLocationId() {
        return inventoryItemLocationId;
    }

    public void setInventoryItemLocationId(String inventoryItemLocationId) {
        this.inventoryItemLocationId = inventoryItemLocationId;
    }

    public String getListingPrice() {
        return listingPrice;
    }

    public void setListingPrice(String listingPrice) {
        this.listingPrice = listingPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getListingStatus() {
        return listingStatus;
    }

    public void setListingStatus(String listingStatus) {
        this.listingStatus = listingStatus;
    }

    public String getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(String marketplace) {
        this.marketplace = marketplace;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getOwnerEmailAddress() {
        return ownerEmailAddress;
    }

    public void setOwnerEmailAddress(String ownerEmailAddress) {
        this.ownerEmailAddress = ownerEmailAddress;
    }

    public Date getUploadTimeAsSQLDate(){
        Date date = null;
        if(this.uploadTime != null){
            DateFormat sourceFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                date = new java.sql.Date(sourceFormat.parse(this.uploadTime).getTime());
            } catch (ParseException ignored) {
            }
        }
        return date;
    }
}
