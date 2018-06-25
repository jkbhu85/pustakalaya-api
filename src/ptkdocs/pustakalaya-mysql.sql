/*
 * Project: Pustakalaya
 * Project link: https://github.com/optimus29/pustakalaya
 * Database schema: https://github.com/optimus29/pustakalaya/blob/master/src/ptkdocs/database-schema-doc.html
 * Database: MySQL 5.6
 *
 * @author Jitendra Kumar
 *
 */
 
DROP DATABASE IF EXISTS ptk;
CREATE DATABASE ptk;


CREATE TABLE ptk.Country (
    id      SMALLINT NOT NULL AUTO_INCREMENT,
    name    VARCHAR(50) NOT NULL,
    isdCode VARCHAR(4),
    abbr    VARCHAR(3),
    PRIMARY KEY (id)
);


CREATE TABLE ptk.UserRole (
	id   SMALLINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(24) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE ptk.UserAccountStatus (
    id   SMALLINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(24) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE ptk.BookInstanceStatus (
	id   SMALLINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(24) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE ptk.BookCategory (
	id       SMALLINT NOT NULL AUTO_INCREMENT,
    category VARCHAR(24) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ptk.Currency (
	id            SMALLINT NOT NULL AUTO_INCREMENT,
    currency_code VARCHAR(24) NOT NULL,
    description   VARCHAR(50) NOT NULL,
    countryFk    SMALLINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (countryFk) REFERENCES Country (id)
);

CREATE TABLE ptk.LibUser (
    id                BIGINT NOT NULL AUTO_INCREMENT,
    roleFk            SMALLINT NOT NULL,
    accountStatusFk   SMALLINT NOT NULL,
    emailUk           VARCHAR(80) NOT NULL,
    passwordHash      VARCHAR(64),
    passwordSalt      VARCHAR(8),
    passwordVersion   SMALLINT,
    unsuccessfulTries TINYINT NOT NULL DEFAULT 0,
    securityQuestion  VARCHAR(100),
    securityAnswer    VARCHAR(50),
    dateOfBirth       DATE,
    firstName         VARCHAR(30),
    lastName          VARCHAR(30),
    gender            CHAR(1),
    mobileUk          BIGINT,
    isdCodeFk         SMALLINT,
    imagePath         VARCHAR(100),
    bookQuota         TINYINT,
    createdOn         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    locale            CHAR(5) NOT NULL,
	acCreatedByFk     BIGINT,
    PRIMARY KEY (id),
    UNIQUE (emailUk),
    UNIQUE (mobileUk),
    FOREIGN KEY (acCreatedByFk) REFERENCES LibUser (id),
    FOREIGN KEY (roleFk) REFERENCES UserRole (id),
    FOREIGN KEY (accountStatusFk) REFERENCES UserAccountStatus (id),
    FOREIGN KEY (isdCodeFk) REFERENCES Country (id)
);


CREATE TABLE ptk.NewUser (
	id            VARCHAR(40) NOT NULL,
	emailUk       VARCHAR(80) NOT NULL,
	firstName     VARCHAR(30) NOT NULL,
	lastName      VARCHAR(30),
	locale        CHAR(5) NOT NULL,
	createdOn     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	expiresOn     TIMESTAMP NOT NULL,
	acCreatedByFk BIGINT NOT NULL,
	roleFk        SMALLINT NOT NULL,
	PRIMARY KEY (id),
    UNIQUE (emailUk),
    FOREIGN KEY (acCreatedByFk) REFERENCES LibUser (id),
    FOREIGN KEY (roleFk) REFERENCES UserRole (id)
);


CREATE TABLE ptk.Address (
    id        BIGINT NOT NULL AUTO_INCREMENT,
    userFk    BIGINT NOT NULL,
    line1     VARCHAR(50),
    line2     VARCHAR(50),
    city      VARCHAR(40),
    stateName VARCHAR(40),
    pin       VARCHAR(8),
    countryFk SMALLINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userFk) REFERENCES LibUser (id),
    FOREIGN KEY (countryFk) REFERENCES Country (id)
);


CREATE TABLE ptk.UserAccountStatusHistory (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    userAccountStatusFk SMALLINT NOT NULL,
    userFk      BIGINT NOT NULL,
    createdOn   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    comments    VARCHAR(200),
    PRIMARY KEY (id),
    FOREIGN KEY (userAccountStatusFk) REFERENCES UserAccountStatus (id),
    FOREIGN KEY (userFk) REFERENCES LibUser (id)
);


CREATE TABLE ptk.UserRoleHistory (
    id             BIGINT NOT NULL AUTO_INCREMENT,
    userFk         BIGINT NOT NULL,
    roleFk         SMALLINT NOT NULL,
    assignedByFk   BIGINT NOT NULL,
    assignDate     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    comments       VARCHAR(200),
    PRIMARY KEY (id),
    FOREIGN KEY (userFk) REFERENCES LibUser (id),
    FOREIGN KEY (roleFk) REFERENCES UserRole (id),
    FOREIGN KEY (assignedByFk) REFERENCES LibUser (id)
);


CREATE TABLE ptk.Book (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    title       VARCHAR(150) NOT NULL,
    authors      VARCHAR(250) NOT NULL,
    edition     SMALLINT,
    isbn        VARCHAR(17),
    categoryFk  SMALLINT,
    addedOn     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    addedByFk   BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (addedByFk) REFERENCES LibUser (id),
    FOREIGN KEY (categoryFk) REFERENCES BookCategory (id)
);


CREATE TABLE ptk.BookInstance (
    id              BIGINT NOT NULL AUTO_INCREMENT,
    bookFk          BIGINT NOT NULL,
    price           DECIMAL(9, 2) NOT NULL,
    currencyFk      SMALLINT NOT NULL,
    noOfPages       SMALLINT NOT NULL,
    publication     VARCHAR(100),
    volume          SMALLINT DEFAULT 0,
    statusFk        SMALLINT NOT NULL,
    addedByFk       BIGINT NOT NULL,
    addedOn         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removedByFk     BIGINT,
    removedOn       TIMESTAMP NULL,
    comments        VARCHAR(200),
    PRIMARY KEY (id),
    FOREIGN KEY (bookFk) REFERENCES Book (id),
    FOREIGN KEY (addedByFk) REFERENCES LibUser (id),
    FOREIGN KEY (removedByFk) REFERENCES LibUser (id),
    FOREIGN KEY (currencyFk) REFERENCES Currency (id)
);


CREATE TABLE ptk.BookInstanceHistory (
	id              BIGINT NOT NULL AUTO_INCREMENT,
	instanceFk      BIGINT NOT NULL,
	statusFk        SMALLINT NOT NULL,
	statusChangedBy BIGINT NOT NULL,
    statusChangeOn  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	comments        VARCHAR(150) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (instanceFk) REFERENCES BookInstance (id),
    FOREIGN KEY (statusFk) REFERENCES BookInstanceStatus (id),
	FOREIGN KEY (statusChangedBy) REFERENCES LibUser (id)
);


CREATE TABLE ptk.BookAssignmentHistory (
    id             BIGINT NOT NULL AUTO_INCREMENT,
    bookInstanceFk BIGINT NOT NULL,
    issuedToFk     BIGINT NOT NULL,
    issuedByFk     BIGINT NOT NULL,
    releasedByFk   BIGINT,
    issuedOn       TIMESTAMP NOT NULL,
    expectedReturnDate DATE NOT NULL,
    returnDate     TIMESTAMP NULL,
    amountFined    DECIMAL(9, 2),
    comments       VARCHAR(200),
    PRIMARY KEY (id),
    FOREIGN KEY (bookInstanceFk) REFERENCES BookInstance (id),
    FOREIGN KEY (issuedToFk) REFERENCES LibUser (id),
    FOREIGN KEY (issuedByFk) REFERENCES LibUser (id),
    FOREIGN KEY (releasedByFk) REFERENCES LibUser (id)
);


INSERT INTO ptk.Country VALUES(NULL, 'India', '+91', 'IN');
INSERT INTO ptk.Country VALUES(NULL, 'Nepal', '+977', 'NP');
INSERT INTO ptk.Country VALUES(NULL, 'Sri Lanka', '+94', 'SL');
INSERT INTO ptk.Country VALUES(NULL, 'Bhutan', '+975', 'BT');
INSERT INTO ptk.Country VALUES(NULL, 'United States', '+1', 'US');

INSERT INTO ptk.Currency VALUES(NULL, 'INR', 'Indian Rupee', (SELECT c.id FROM ptk.Country c WHERE c.abbr = 'IN'));
INSERT INTO ptk.Currency VALUES(NULL, 'NPR', 'Nepal Rupee', (SELECT c.id FROM ptk.Country c WHERE c.abbr = 'NP'));
INSERT INTO ptk.Currency VALUES(NULL, 'LKR', 'Sri Lanka Rupee', (SELECT c.id FROM ptk.Country c WHERE c.abbr = 'SL'));
INSERT INTO ptk.Currency VALUES(NULL, 'BTN', 'Ngultrum', (SELECT c.id FROM ptk.Country c WHERE c.abbr = 'BT'));
INSERT INTO ptk.Currency VALUES(NULL, 'USD', 'US Dollar', (SELECT c.id FROM ptk.Country c WHERE c.abbr = 'US'));

INSERT INTO ptk.UserRole VALUES(NULL, 'ADMIN');
INSERT INTO ptk.UserRole VALUES(NULL, 'LIBRARIAN');
INSERT INTO ptk.UserRole VALUES(NULL, 'MEMBER');
INSERT INTO ptk.UserRole VALUES(NULL, 'NONE');

INSERT INTO ptk.UserAccountStatus VALUES(NULL, 'ACTIVE');
INSERT INTO ptk.UserAccountStatus VALUES(NULL, 'CLOSED');
INSERT INTO ptk.UserAccountStatus VALUES(NULL, 'REVOKED');
INSERT INTO ptk.UserAccountStatus VALUES(NULL, 'LOCKED');
INSERT INTO ptk.UserAccountStatus VALUES(NULL, 'INCOMPLETE');

INSERT INTO ptk.BookInstanceStatus VALUES(NULL, 'ISSUED');
INSERT INTO ptk.BookInstanceStatus VALUES(NULL, 'AVAILABLE');
INSERT INTO ptk.BookInstanceStatus VALUES(NULL, 'UNAVAILABLE');
INSERT INTO ptk.BookInstanceStatus VALUES(NULL, 'REMOVED');

INSERT INTO ptk.BookCategory VALUES(NULL, 'TEXTBOOK');
INSERT INTO ptk.BookCategory VALUES(NULL, 'MAGAZINE');

INSERT INTO ptk.LibUser
(
    roleFk,
    accountStatusFk,
    emailUk,
    passwordHash,
    passwordSalt,
    passwordVersion,
    securityQuestion,
    securityAnswer,
    dateOfBirth,
    firstName,
    lastName,
    gender,
    mobileUk,
    isdCodeFk,
    bookQuota,
    createdOn,
    locale
)
VALUES
(
  (SELECT r.id FROM ptk.UserRole r WHERE r.name = 'ADMIN') -- role
, (SELECT s.id FROM ptk.UserAccountStatus s WHERE s.name = 'ACTIVE') -- account status
, "krishna.murlidhar@m.com" -- email
, "0b864241ac67a71198321106380c46c9e53ed83b7c57c54f875edcabcdfa6682" -- password hash
, "abckdkdt" -- password salt
, 1 -- password version
, "The person i love" -- security question
, "none" -- security answer
, "1991-07-01" -- date of birth
, "Krishna" -- first name
, "Murlidhar" -- last name
, "M" -- gender
, "9123456780" -- mobile
, (SELECT c.id FROM ptk.Country c WHERE c.abbr = 'IN') -- isd country
, 8 -- book quota
, sysdate() -- time of creation
, 'hi_IN' -- locale of user
);

INSERT INTO ptk.LibUser
(
    roleFk,
    accountStatusFk,
    emailUk,
    passwordHash,
    passwordSalt,
    passwordVersion,
    securityQuestion,
    securityAnswer,
    dateOfBirth,
    firstName,
    lastName,
    gender,
    mobileUk,
    isdCodeFk,
    bookQuota,
    createdOn,
    locale,
    acCreatedByFk
)
VALUES
(
  (SELECT r.id FROM ptk.UserRole r WHERE r.name = 'ADMIN') -- role
, (SELECT s.id FROM ptk.UserAccountStatus s WHERE s.name = 'ACTIVE') -- account status
, "jitendra.kumar@m.com" -- email
, "0b864241ac67a71198321106380c46c9e53ed83b7c57c54f875edcabcdfa6682" -- password hash
, "abckdkdt" -- password salt
, 1 -- password version
, "Who are you" -- security question
, "unknown" -- security answer
, "1991-07-10" -- date of birth
, "Jitendra" -- first name
, "Kumar" -- last name
, "M" -- gender
, "9123456784" -- mobile
, (SELECT c.id FROM ptk.Country c WHERE c.abbr = 'IN') -- isd country
, 4 -- book quota
, sysdate() -- time of creation
, 'en_US' -- locale of user
, (SELECT u.id FROM ptk.LibUser u WHERE u.emailUk = 'krishna.murlidhar@m.com') -- account created by
);

commit;
