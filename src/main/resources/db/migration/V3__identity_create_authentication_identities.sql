CREATE TABLE authentication_identities (
    id UUID NOT NULL,
    user_id UUID NOT NULL,
    provider VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    phone_number VARCHAR(20),
    external_identifier VARCHAR(255),
    password_hash VARCHAR(255),
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,

    CONSTRAINT pk_authentication_identities
        PRIMARY KEY (id),

    CONSTRAINT fk_authentication_identities_user
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
);