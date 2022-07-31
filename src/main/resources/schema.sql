drop table if exists pokemon;
CREATE TABLE pokemon
(
    id      INTEGER      NOT NULL AUTO_INCREMENT,
    name    VARCHAR(128) NOT NULL,
    details VARCHAR(128) NOT NULL,
    picture VARCHAR(128) ,
    pic_byte BLOB,
    PRIMARY KEY (id)
);