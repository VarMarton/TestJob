CREATE TABLE listing (
    id UUID NOT NULL,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    location_id UUID NOT NULL REFERENCES location(id),
    listing_price NUMERIC(20,2) NOT NULL,
    currency CHAR(3) NOT NULL,
    quantity INTEGER NOT NULL,
    listing_status INTEGER NOT NULL REFERENCES listing_status(id),
    marketplace INTEGER NOT NULL REFERENCES marketplace(id),
    upload_time DATE,
    owner_email_address TEXT NOT NULL,
    CONSTRAINT listing_pk PRIMARY KEY (id)
);