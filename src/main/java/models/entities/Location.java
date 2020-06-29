package models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Location {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("manager_name")
    private String managerName;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("address_primary")
    private String addressPrimary;
    @JsonProperty("address_secondary")
    private String addressSecondary;
    @JsonProperty("country")
    private String country;
    @JsonProperty("town")
    private String town;
    @JsonProperty("postal_code")
    private String postalCode;

    public Location(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressPrimary() {
        return addressPrimary;
    }

    public void setAddressPrimary(String addressPrimary) {
        this.addressPrimary = addressPrimary;
    }

    public String getAddressSecondary() {
        return addressSecondary;
    }

    public void setAddressSecondary(String addressSecondary) {
        this.addressSecondary = addressSecondary;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
