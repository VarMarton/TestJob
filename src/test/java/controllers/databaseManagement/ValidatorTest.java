package controllers.databaseManagement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {
    static Validator validator;

    @BeforeAll
    static void init(){
        validator = new Validator(null);
    }

    @Test
    void isValidListingIdTest(){
        assertTrue(validator.isValidListingId(UUID.randomUUID().toString()));
        assertFalse(validator.isValidListingId("test"));
        assertFalse(validator.isValidListingId(null));
    }

    @Test
    void isValidListingTitleOrDescriptionTest(){
        assertTrue(validator.isValidListingTitleOrDescription("test"));
        assertFalse(validator.isValidListingId(""));
        assertFalse(validator.isValidListingId(null));
    }

    @Test
    void isValidListingPriceTest(){
        assertTrue(validator.isValidListingPrice("100.1"));
        assertTrue(validator.isValidListingPrice("100.01"));
        assertFalse(validator.isValidListingPrice("100.011"));
        assertFalse(validator.isValidListingPrice("-100.011"));
        assertFalse(validator.isValidListingPrice("test"));
        assertFalse(validator.isValidListingPrice(null));
    }

    @Test
    void isValidCurrencyTest(){
        assertTrue(validator.isValidCurrency("HUF"));
        assertFalse(validator.isValidCurrency("HU"));
        assertFalse(validator.isValidCurrency("test"));
        assertFalse(validator.isValidCurrency(null));
    }

    @Test
    void isValidQuantityTest(){
        assertTrue(validator.isValidQuantity("55"));
        assertFalse(validator.isValidQuantity("test"));
        assertFalse(validator.isValidQuantity("55.5"));
        assertFalse(validator.isValidQuantity(null));
    }

    @Test
    void isValidEmailAddressTest(){
        assertTrue(validator.isValidEmailAddress("example@test.com"));
        assertFalse(validator.isValidEmailAddress("test"));
        assertFalse(validator.isValidEmailAddress("test.com"));
        assertFalse(validator.isValidEmailAddress("exampletest.com"));
        assertFalse(validator.isValidEmailAddress("example@test"));
        assertFalse(validator.isValidEmailAddress(null));
    }
}
