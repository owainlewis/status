# Users schema

# --- !Ups

CREATE SEQUENCE incident_id_seq;
CREATE TABLE Incidents(
    id bigint NOT NULL DEFAULT nextval('incident_id_seq'),
    title text NOT NULL,
    status int NOT NULL,
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (id)
);

CREATE SEQUENCE update_id_seq;
CREATE TABLE Updates(
    id bigint NOT NULL DEFAULT nextval('update_id_seq'),
    incident bigint NOT NULL,
    title text NOT NULL,
    description text,
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (incident) REFERENCES Incidents(id),
    PRIMARY KEY (id)
);


# --- !Downs

DROP TABLE Incidents;
DROP SEQUENCE incident_id_seq;

DROP TABLE Updates;
DROP SEQUENCE incident_id_seq;