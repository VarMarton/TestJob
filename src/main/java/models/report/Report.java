package models.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Report{
    @JsonProperty("total_listing_count")
    private int totalListingCount;
    @JsonProperty("total_ebay_listing_count")
    private int totalEBayCount;
    @JsonProperty("total_ebay_listing_price")
    private double totalEBayPrice;
    @JsonProperty("average_ebay_listing_price")
    private double averageEBayPrice;
    @JsonProperty("total_amazon_listing_count")
    private int totalAmazonCount;
    @JsonProperty("total_amazon_listing_price")
    private double totalAmazonPrice;
    @JsonProperty("average_amazon_listing_price")
    private double averageAmazonPrice;
    @JsonProperty("best_lister_email_address")
    private ArrayList<String> bestListerEmailAddresses = new ArrayList<>();
    @JsonProperty("monthly_reports")
    private ArrayList<MonthlyReport> monthlyReports = new ArrayList<>();

    public Report() {}

    public int getTotalListingCount() {
        return totalListingCount;
    }

    public void setTotalListingCount(int totalListingCount) {
        this.totalListingCount = totalListingCount;
    }

    public int getTotalEBayCount() {
        return totalEBayCount;
    }

    public void setTotalEBayCount(int totalEBayCount) {
        this.totalEBayCount = totalEBayCount;
    }

    public double getTotalEBayPrice() {
        return totalEBayPrice;
    }

    public void setTotalEBayPrice(double totalEBayPrice) {
        this.totalEBayPrice = totalEBayPrice;
    }

    public double getAverageEBayPrice() {
        return averageEBayPrice;
    }

    public void setAverageEBayPrice(double averageEBayPrice) {
        this.averageEBayPrice = averageEBayPrice;
    }

    public int getTotalAmazonCount() {
        return totalAmazonCount;
    }

    public void setTotalAmazonCount(int totalAmazonCount) {
        this.totalAmazonCount = totalAmazonCount;
    }

    public double getTotalAmazonPrice() {
        return totalAmazonPrice;
    }

    public void setTotalAmazonPrice(double totalAmazonPrice) {
        this.totalAmazonPrice = totalAmazonPrice;
    }

    public double getAverageAmazonPrice() {
        return averageAmazonPrice;
    }

    public void setAverageAmazonPrice(double averageAmazonPrice) {
        this.averageAmazonPrice = averageAmazonPrice;
    }

    public ArrayList<String> getBestListerEmailAddresses() {
        return bestListerEmailAddresses;
    }

    public void setBestListerEmailAddresses(ArrayList<String> bestListerEmailAddresses) {
        this.bestListerEmailAddresses = bestListerEmailAddresses;
    }

    public void addBestListerEmailAddress(String emailAddress){
        this.bestListerEmailAddresses.add(emailAddress);
    }

    public ArrayList<MonthlyReport> getMonthlyReports() {
        return monthlyReports;
    }

    public void setMonthlyReports(ArrayList<MonthlyReport> monthlyReports) {
        this.monthlyReports = monthlyReports;
    }
}
