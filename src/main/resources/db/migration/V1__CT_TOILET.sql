create table TOILETS (
    id uuid not null,
    name varchar(255),
    latitude double precision,
    longitude double precision,
    type varchar(255),
    version integer not null,
    tenant_id uuid,
    created_at timestamp with time zone default now(),
    updated_at timestamp with time zone default now(),
    primary key (id)
);

create table address (
    id uuid not null,
    city varchar(255),
    country varchar(255),
    postcode varchar(255),
    street varchar(255),
    street_number varchar(255),
    toilet_id uuid,
    created_at timestamp with time zone default now(),
    updated_at timestamp with time zone default now(),
    primary key (id)
);

create table tenant (
    id uuid not null,
    name varchar(255),
    created_at timestamp with time zone default now(),
    updated_at timestamp with time zone default now(),
    primary key (id)
);