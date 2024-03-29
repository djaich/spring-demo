create table AUTHORS
(
    ID            BIGINT  not null
        primary key,
    FIRST_NAME    VARCHAR(200),
    LAST_NAME     VARCHAR(255),
    NATIONALITY   VARCHAR(255),
    YEAR_OF_BIRTH INTEGER not null
);

create table BOOKS
(
    ID        BIGINT not null
        primary key,
    STATUS    VARCHAR(255),
    TITLE     VARCHAR(2000),
    AUTHOR_ID BIGINT,
    constraint FKFJIXH2VYM2CVFJ3UFXJ91JEM7
        foreign key (AUTHOR_ID) references AUTHORS
);

create table USERS
(
    USER_TYPE     VARCHAR(31)  not null,
    ID            BIGINT       not null
        primary key,
    ACTIVE        BOOLEAN      not null,
    AGE           INTEGER      not null,
    FULL_NAME     VARCHAR(255) not null,
    PASSWORD      VARCHAR(255) not null,
    USER_NAME     VARCHAR(255) not null
        unique,
    CITY          VARCHAR(255),
    COUNTRY       VARCHAR(255),
    STREET        VARCHAR(255),
    STREET_NUMBER VARCHAR(255),
    MEMBER_ID     VARCHAR(255)
);

create sequence HIBERNATE_SEQUENCE
    increment by 1;
