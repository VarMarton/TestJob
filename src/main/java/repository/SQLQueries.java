package repository;

public class SQLQueries {
    // For saving and updating
    public static final String SAVE_MARKETPLACE = "INSERT INTO marketplace(id,marketplace_name) VALUES (?,?);";
    public static final String UPDATE_MARKETPLACE = "UPDATE marketplace SET marketplace_name = ? WHERE id = ?;";

    public static final String SAVE_LOCATION = "INSERT INTO location (id, manager_name, phone, address_primary, address_secondary, country, town, postal_code) VALUES (?,?,?,?,?,?,?,?);";
    public static final String UPDATE_LOCATION = "UPDATE location SET manager_name = ?, phone = ?, address_primary = ?, address_secondary = ?, country = ?, town = ?, postal_code = ? WHERE id = ?;";

    public static final String SAVE_LISTING_STATUS = "INSERT INTO listing_status(id,status_name) VALUES (?,?);";
    public static final String UPDATE_LISTING_STATUS = "UPDATE listing_status SET status_name = ? WHERE id = ?;";

    public static final String SAVE_LISTING = "INSERT INTO listing (id, title, description, location_id, listing_price, currency, quantity, listing_status, marketplace, upload_time, owner_email_address) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    public static final String UPDATE_LISTING = "UPDATE listing SET title = ?, description = ?, location_id = ?, listing_price = ?, currency = ?, quantity = ?, listing_status = ?, marketplace = ?, upload_time = ?, owner_email_address = ? WHERE id = ?;";

    // For validation
    public static final String GET_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?;";
    public static final String GET_LISTING_STATUS_BY_ID = "SELECT * FROM listing_status WHERE id = ?;";
    public static final String GET_MARKETPLACE_BY_ID = "SELECT * FROM marketplace WHERE id = ?;";

    // For reporting
    public static final String REPORT =
            "SELECT total_count.counted AS total_count,\n" +
            "\t   ebay.counted AS ebay_counted, ebay.sum AS ebay_sum, ebay.average AS ebay_average, \n" +
            "\t   amazon.counted AS amazon_counted, amazon.sum AS amazon_sum, amazon.average AS amazon_average,\n" +
            "\t   best_email_address.email_address AS best_lister_email_address\n" +
            "FROM \n" +
            "\t\t(SELECT count(id) AS counted FROM listing) total_count\n" +
            "\t\t,\n" +
            "\t\t(SELECT count(id) AS counted, sum(listing_price) AS sum, avg(listing_price) AS average \n" +
            "\t\tFROM listing \n" +
            "\t\tWHERE marketplace = (SELECT id FROM marketplace WHERE marketplace_name = 'EBAY')) ebay\n" +
            "\t\t,\n" +
            "\t\t(SELECT count(id) AS counted, sum(listing_price) AS sum, avg(listing_price) AS average \n" +
            "\t\tFROM listing \n" +
            "\t\tWHERE marketplace = (SELECT id FROM marketplace WHERE marketplace_name = 'AMAZON')) amazon\n" +
            "\t\t,\n" +
            "\t\t(SELECT counted.email_address\n" +
            "\t\tFROM (SELECT owner_email_address AS email_address, count(owner_email_address) AS counted \n" +
            "\t\t\t  FROM listing\n" +
            "\t\t\t  GROUP BY email_address ) AS counted\n" +
            "\t\t\t  ,\n" +
            "\t\t\t  (SELECT max(quantity) AS maximum\n" +
            "\t\t\t   FROM (SELECT owner_email_address AS email_address, count(owner_email_address) AS quantity \n" +
            "\t\t\t\t     FROM listing\n" +
            "\t\t\t\t     GROUP BY email_address ) counted) maximum\n" +
            "\t\tWHERE counted.counted = maximum.maximum) best_email_address;";

    public static final String MONTHLY_REPORTS =
            "SELECT to_char(l0.upload_time, 'YYYY-MM') AS month, ebay.counted AS ebay_counted, ebay.sum AS ebay_sum, ebay.average AS ebay_average, \n" +
            "amazon.counted AS amazon_counted, amazon.sum AS amazon_sum, amazon.average AS amazon_average, best_email_address.address AS best_lister_email_address\n" +
            "FROM listing l0,\n" +
            "\t(SELECT maximum.year_and_month AS year_and_month, counted.owner_email_address AS address\n" +
            "\tFROM (SELECT year_and_month, max(counted) maximum\n" +
            "\t\tFROM \n" +
            "\t\t\t(SELECT to_char(upload_time, 'YYYY-MM') AS year_and_month, owner_email_address, count(owner_email_address) AS counted\n" +
            "\t\t\tFROM listing \n" +
            "\t\t\tWHERE upload_time NOTNULL\n" +
            "\t\t\tGROUP BY year_and_month, owner_email_address) addresses_per_month\n" +
            "\t\tGROUP BY year_and_month) maximum\n" +
            "\t\t,\n" +
            "\t\t(SELECT to_char(upload_time, 'YYYY-MM') AS year_and_month, owner_email_address, count(owner_email_address) AS counted\n" +
            "\t\tFROM listing \n" +
            "\t\tWHERE upload_time NOTNULL\n" +
            "\t\tGROUP BY year_and_month, owner_email_address) counted\n" +
            "\tWHERE counted.counted = maximum.maximum AND counted.year_and_month = maximum.year_and_month) best_email_address\n" +
            "\t,\n" +
            "\t(SELECT to_char(upload_time, 'YYYY-MM') AS year_and_month, count(id) AS counted, sum(listing_price) AS sum, avg(listing_price) AS average\n" +
            "\tFROM listing \n" +
            "\tWHERE upload_time NOTNULL AND marketplace = (SELECT id FROM marketplace WHERE marketplace_name = 'EBAY')\n" +
            "\tGROUP BY year_and_month ) ebay \n" +
            "FULL OUTER JOIN \n" +
            "\t(SELECT to_char(upload_time, 'YYYY-MM') AS year_and_month, count(id) AS counted, sum(listing_price) AS sum, avg(listing_price) AS average\n" +
            "\tFROM listing \n" +
            "\tWHERE upload_time NOTNULL AND marketplace = (SELECT id FROM marketplace WHERE marketplace_name = 'AMAZON')\n" +
            "\tGROUP BY year_and_month ) amazon\n" +
            "ON ebay.year_and_month = amazon.year_and_month\n" +
            "WHERE to_char(l0.upload_time, 'YYYY-MM') = best_email_address.year_and_month AND (to_char(l0.upload_time, 'YYYY-MM') = ebay.year_and_month OR to_char(l0.upload_time, 'YYYY-MM') = amazon.year_and_month)\n" +
            "GROUP BY month, ebay.counted, ebay.sum, ebay.average, amazon.counted, amazon.sum, amazon.average, best_email_address.address\n" +
            "ORDER BY month DESC;";
}