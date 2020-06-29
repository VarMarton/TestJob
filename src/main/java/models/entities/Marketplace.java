package models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Marketplace {
    @JsonProperty("id")
    private int id;
    @JsonProperty("marketplace_name")
    private String marketplaceName;

    public Marketplace(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarketplaceName() {
        return marketplaceName;
    }

    public void setMarketplaceName(String marketplaceName) {
        this.marketplaceName = marketplaceName;
    }
}
