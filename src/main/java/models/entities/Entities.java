package models.entities;

import java.util.List;

public class Entities {
    private List<Listing> listings;
    private List<ListingStatus> listingStatuses;
    private List<Location> locations;
    private List<Marketplace> marketplaces;

    public Entities(){}

    public void addListing(Listing listing){
        this.listings.add(listing);
    }

    public void addListingStatus(ListingStatus listingStatus){
        this.listingStatuses.add(listingStatus);
    }

    public void addLocation(Location location){
        this.locations.add(location);
    }

    public void addMarketplace(Marketplace marketplace){
        this.marketplaces.add(marketplace);
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    public List<ListingStatus> getListingStatuses() {
        return listingStatuses;
    }

    public void setListingStatuses(List<ListingStatus> listingStatuses) {
        this.listingStatuses = listingStatuses;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Marketplace> getMarketplaces() {
        return marketplaces;
    }

    public void setMarketplaces(List<Marketplace> marketplaces) {
        this.marketplaces = marketplaces;
    }
}
