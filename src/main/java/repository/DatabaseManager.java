package repository;

import models.entities.Listing;
import models.entities.ListingStatus;
import models.entities.Location;
import models.entities.Marketplace;
import models.report.MonthlyReport;
import models.report.Report;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class DatabaseManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static ArrayList<DatabaseManager> managers = new ArrayList<>();

    private String url;
    private String username;
    private String password;
    private Connection connection = null;

    private DatabaseManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DatabaseManager getInstance(String url, String username, String password){
        for (DatabaseManager databaseManager : managers) {
            if (databaseManager.url.equals(url) && databaseManager.username.equals(username)) {
                return databaseManager;
            }
        }
        DatabaseManager manager = new DatabaseManager(url,username,password);
        if(manager.reconnectToDatabase()){
            managers.add(manager);
        }
        else {
            manager = null;
        }
        return manager;
    }

    public boolean reconnectToDatabase() {
        boolean success = false;
        if(this.connection == null){
            try {
                this.connection = DriverManager.getConnection(this.url, this.username, this.password);
                success = true;
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
        else{
            LOGGER.warn("The connection is already exists. Please disconnect first.");
        }
        return success;
    }

    public void closeConnection(){
        if(this.connection != null){
            try {
                this.connection.close();
                this.connection = null;
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
        else {
            LOGGER.warn("Not able to close connection to database, because there is no connection!");
        }

    }

    public void saveMarketplace(Marketplace marketplace){
        if(this.connection != null){
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SAVE_MARKETPLACE)){
                preparedStatement.setLong(1, marketplace.getId());
                preparedStatement.setString(2, marketplace.getMarketplaceName());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                LOGGER.warn(e);

                try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.UPDATE_MARKETPLACE)){
                    preparedStatement.setLong(2, marketplace.getId());
                    preparedStatement.setString(1, marketplace.getMarketplaceName());

                    preparedStatement.executeUpdate();

                } catch (SQLException ex) {
                    LOGGER.error(ex);
                }
            }
        }
        else{
            LOGGER.warn("No connection. Not able to work with table marketplace!");
        }
    }

    public void saveLocation(Location location){
        if(this.connection != null){
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SAVE_LOCATION)){
                int placeholder = 0;
                preparedStatement.setObject(++placeholder, location.getId(), Types.OTHER);
                preparedStatement.setString(++placeholder, location.getManagerName());
                preparedStatement.setString(++placeholder, location.getPhone());
                preparedStatement.setString(++placeholder, location.getAddressPrimary());
                preparedStatement.setString(++placeholder, location.getAddressSecondary());
                preparedStatement.setString(++placeholder, location.getCountry());
                preparedStatement.setString(++placeholder, location.getTown());
                preparedStatement.setString(++placeholder, location.getPostalCode());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                LOGGER.warn(e);

                try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.UPDATE_LOCATION)){
                    int placeholder = 0;
                    preparedStatement.setString(++placeholder, location.getManagerName());
                    preparedStatement.setString(++placeholder, location.getPhone());
                    preparedStatement.setString(++placeholder, location.getAddressPrimary());
                    preparedStatement.setString(++placeholder, location.getAddressSecondary());
                    preparedStatement.setString(++placeholder, location.getCountry());
                    preparedStatement.setString(++placeholder, location.getTown());
                    preparedStatement.setString(++placeholder, location.getPostalCode());
                    preparedStatement.setObject(++placeholder, location.getId(), Types.OTHER);

                    preparedStatement.executeUpdate();

                } catch (SQLException ex) {
                    LOGGER.error(ex);
                }
            }
        }
        else{
            LOGGER.warn("No connection. Not able to work with table marketplace!");
        }
    }

    public void saveListingStatus(ListingStatus listingStatus){
        if(this.connection != null){
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SAVE_LISTING_STATUS)){
                preparedStatement.setInt(1, listingStatus.getId());
                preparedStatement.setString(2, listingStatus.getStatusName());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                LOGGER.warn(e);

                try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.UPDATE_LISTING_STATUS)){
                    preparedStatement.setInt(2, listingStatus.getId());
                    preparedStatement.setString(1, listingStatus.getStatusName());

                    preparedStatement.executeUpdate();

                } catch (SQLException ex) {
                    LOGGER.error(ex);
                }
            }
        }
        else{
            LOGGER.warn("No connection. Not able to work with table marketplace!");
        }
    }

    public void saveListing(Listing listing){
        if(this.connection != null){
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SAVE_LISTING)){
                int placeholder = 0;
                preparedStatement.setObject(++placeholder, UUID.fromString(listing.getId()), Types.OTHER);
                preparedStatement.setString(++placeholder, listing.getTitle());
                preparedStatement.setString(++placeholder, listing.getDescription());
                preparedStatement.setObject(++placeholder, UUID.fromString(listing.getInventoryItemLocationId()), Types.OTHER);
                preparedStatement.setDouble(++placeholder, Double.parseDouble(listing.getListingPrice()));
                preparedStatement.setString(++placeholder, listing.getCurrency());
                preparedStatement.setInt(++placeholder, Integer.parseInt(listing.getQuantity()));
                preparedStatement.setInt(++placeholder, Integer.parseInt(listing.getListingStatus()));
                preparedStatement.setInt(++placeholder, Integer.parseInt(listing.getMarketplace()));
                preparedStatement.setDate(++placeholder, listing.getUploadTimeAsSQLDate());
                preparedStatement.setString(++placeholder, listing.getOwnerEmailAddress());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                LOGGER.warn(e);

                try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.UPDATE_LISTING)){
                    int placeholder = 0;
                    preparedStatement.setString(++placeholder, listing.getTitle());
                    preparedStatement.setString(++placeholder, listing.getDescription());
                    preparedStatement.setObject(++placeholder, UUID.fromString(listing.getInventoryItemLocationId()), Types.OTHER);
                    preparedStatement.setDouble(++placeholder, Double.parseDouble(listing.getListingPrice()));
                    preparedStatement.setString(++placeholder, listing.getCurrency());
                    preparedStatement.setInt(++placeholder, Integer.parseInt(listing.getQuantity()));
                    preparedStatement.setInt(++placeholder, Integer.parseInt(listing.getListingStatus()));
                    preparedStatement.setInt(++placeholder, Integer.parseInt(listing.getMarketplace()));
                    preparedStatement.setDate(++placeholder, listing.getUploadTimeAsSQLDate());
                    preparedStatement.setString(++placeholder, listing.getOwnerEmailAddress());
                    preparedStatement.setObject(++placeholder, UUID.fromString(listing.getId()), Types.OTHER);

                    preparedStatement.executeUpdate();

                } catch (SQLException ex) {
                    LOGGER.error(ex);
                }
            }
        }
        else{
            LOGGER.warn("No connection. Not able to work with table marketplace!");
        }
    }

    public boolean isValidLocationId(UUID uuid){
        boolean isValid = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.GET_LOCATION_BY_ID)){
            preparedStatement.setObject(1, uuid, Types.OTHER);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    isValid = true;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
        }
        return isValid;
    }

    public boolean isValidListingStatusId(int id){
        boolean isValid = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.GET_LISTING_STATUS_BY_ID)){
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    isValid = true;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
        }
        return isValid;
    }

    public boolean isValidMarketplaceId(int id){
        boolean isValid = false;
        if(getMarketplaceName(id) != null){
            isValid = true;
        }
        return isValid;
    }

    public String getMarketplaceName(int id){
        String marketplaceName = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.GET_MARKETPLACE_BY_ID)){
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    marketplaceName = resultSet.getString("marketplace_name");
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
        }
        return marketplaceName;
    }

    public Report getReport(){
        Report report = new Report();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.REPORT)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    report.setTotalListingCount(resultSet.getInt("total_count"));
                    report.setTotalEBayCount(resultSet.getInt("ebay_counted"));
                    report.setTotalEBayPrice(resultSet.getDouble("ebay_sum"));
                    report.setAverageEBayPrice(resultSet.getDouble("ebay_average"));
                    report.setTotalAmazonCount(resultSet.getInt("amazon_counted"));
                    report.setTotalAmazonPrice(resultSet.getDouble("amazon_sum"));
                    report.setAverageAmazonPrice(resultSet.getDouble("amazon_average"));
                    report.addBestListerEmailAddress(resultSet.getString("best_lister_email_address"));

                    while (resultSet.next()){
                        report.addBestListerEmailAddress(resultSet.getString("best_lister_email_address"));
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
        }
        return report;
    }

    public ArrayList<MonthlyReport> getMonthlyReports(){
        ArrayList<MonthlyReport> monthlyReports = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.MONTHLY_REPORTS)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                MonthlyReport monthlyReport = new MonthlyReport();
                if(resultSet.next()){
                    monthlyReport.setMonth(resultSet.getString("month"));
                    monthlyReport.setTotalEBayCount(resultSet.getInt("ebay_counted"));
                    monthlyReport.setTotalEBayPrice(resultSet.getDouble("ebay_sum"));
                    monthlyReport.setAverageEBayPrice(resultSet.getDouble("ebay_average"));
                    monthlyReport.setTotalAmazonCount(resultSet.getInt("amazon_counted"));
                    monthlyReport.setTotalAmazonPrice(resultSet.getDouble("amazon_sum"));
                    monthlyReport.setAverageAmazonPrice(resultSet.getDouble("amazon_average"));
                    monthlyReport.addBestListerEmailAddress(resultSet.getString("best_lister_email_address"));

                    while(resultSet.next()){
                        String month = resultSet.getString("month");
                        if(month.equals(monthlyReport.getMonth())){
                            monthlyReport.addBestListerEmailAddress(resultSet.getString("best_lister_email_address"));
                        }
                        else {
                            monthlyReports.add(monthlyReport);
                            monthlyReport = new MonthlyReport();
                            monthlyReport.setMonth(resultSet.getString("month"));
                            monthlyReport.setTotalEBayCount(resultSet.getInt("ebay_counted"));
                            monthlyReport.setTotalEBayPrice(resultSet.getDouble("ebay_sum"));
                            monthlyReport.setAverageEBayPrice(resultSet.getDouble("ebay_average"));
                            monthlyReport.setTotalAmazonCount(resultSet.getInt("amazon_counted"));
                            monthlyReport.setTotalAmazonPrice(resultSet.getDouble("amazon_sum"));
                            monthlyReport.setAverageAmazonPrice(resultSet.getDouble("amazon_average"));
                            monthlyReport.addBestListerEmailAddress(resultSet.getString("best_lister_email_address"));

                        }
                    }
                    monthlyReports.add(monthlyReport);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
        }
        return monthlyReports;
    }
}
