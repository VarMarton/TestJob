CREATE TABLE location (
    id UUID NOT NULL,
    manager_name TEXT,
    phone VARCHAR(50),
    address_primary TEXT,
    address_secondary TEXT,
    country VARCHAR(100),
    town VARCHAR(200),
    postal_code VARCHAR(50),
    CONSTRAINT location_pk PRIMARY KEY (id)
);