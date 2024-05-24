CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE toilets
(
    id          UUID  primary key,
    description TEXT,
    latitude    DECIMAL(9, 6) not null,
    longitude   DECIMAL(9, 6) not null,
    version     INTEGER not null,
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at   TIMESTAMP WITH TIME ZONE not null
);

create table owners (
    id          UUID  primary key,
    customer_id  uuid not null,
    toilet_id uuid not null,
    version     INTEGER not null,
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at   TIMESTAMP WITH TIME ZONE not null,
    CONSTRAINT unique_customer_toilet UNIQUE (customer_id, toilet_id)

);


create table items (
    id          UUID  primary key,
    description TEXT,
    version     INTEGER not null,
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at   TIMESTAMP WITH TIME ZONE not null,
    toilet_id uuid not null,
    CONSTRAINT fk_item_toilet FOREIGN KEY (toilet_id) REFERENCES toilets(id)
    
)