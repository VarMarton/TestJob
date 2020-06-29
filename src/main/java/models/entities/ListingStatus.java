package models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListingStatus {
    @JsonProperty("id")
    private int id;
    @JsonProperty("status_name")
    private String statusName;

    public ListingStatus(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
