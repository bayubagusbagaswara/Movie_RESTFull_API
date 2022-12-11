
CREATE TABLE movies (
    id serial NOT NULL primary key,
    title character varying(255) NOT NULL,
    description character varying(255),
    image character varying(255),
    rating real,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
