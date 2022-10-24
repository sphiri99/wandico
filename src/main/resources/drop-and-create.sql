drop table if exists pokemon;
drop table if exists user_details_role;
drop table if exists user_account_details;
drop table if exists order_details;
CREATE TABLE pokemon
(
    id      INTEGER      NOT NULL AUTO_INCREMENT,
    name    VARCHAR(128) NOT NULL,
    details VARCHAR(128) NOT NULL,
    picture VARCHAR(128) ,
    pic_byte BLOB,
    PRIMARY KEY (id)
);

CREATE TABLE user_details_role
(
    id      INTEGER      NOT NULL AUTO_INCREMENT,
    name    VARCHAR(128) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE user_account_details
(
    id      INTEGER      NOT NULL AUTO_INCREMENT,
    username    VARCHAR(128) NOT NULL,
    pass VARCHAR(128) NOT NULL,
    pass_confirm VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE order_details
(
    id      INTEGER      NOT NULL AUTO_INCREMENT,
    client_name    VARCHAR(128) NOT NULL,
    item_name    VARCHAR(128) NOT NULL,
    created_on VARCHAR(128) NOT NULL,
    quantity INTEGER  NOT NULL,
    est_turnaround_time VARCHAR(128) NOT NULL,
    due_date VARCHAR(128) NOT NULL,
    status VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);