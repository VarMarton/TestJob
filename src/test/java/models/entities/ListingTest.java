package models.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

public class ListingTest {

    @Test
    @DisplayName("Check if upload time is in yyyy/MM/dd format and convertible to java.sql.Date .")
    void getUploadTimeAsSQLDateTest() throws ParseException {
        Listing listing = new Listing();

        listing.setUploadTime(null);
        assertNull(listing.getUploadTimeAsSQLDate());

        listing.setUploadTime("2020-02-02");
        assertNull(listing.getUploadTimeAsSQLDate());

        listing.setUploadTime("test");
        assertNull(listing.getUploadTimeAsSQLDate());

        listing.setUploadTime("2020/02/02");
        DateFormat sourceFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date expected = new java.sql.Date(sourceFormat.parse("2020/02/02").getTime());
        assertEquals(expected, listing.getUploadTimeAsSQLDate());
    }
}
