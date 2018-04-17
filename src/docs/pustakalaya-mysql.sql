/*
 * This SQL is for MySQL database.
 * 
 * Database name: lms
 *
 * @author Jitendra Kumar
 *
 */

CREATE TABLE Countries (
    id      SMALLINT NOT NULL AUTO_INCREMENT,
    name    VARCHAR(50) NOT NULL,
    isdCode VARCHAR(4),
    abbr    VARCHAR(3),
    PRIMARY KEY (id)
);


CREATE TABLE UserRoles (
	id   SMALLINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(24) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE UserAccountStatus (
    id   SMALLINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(24) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE BookInstanceStatus (
	id   SMALLINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(24) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE Books (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    title       VARCHAR(150) NOT NULL,
    author      VARCHAR(250) NOT NULL,
    edition     SMALLINT,
    price       DECIMAL(9 , 2 ) NOT NULL,
    noOfPages   SMALLINT NOT NULL,
    isbn        VARCHAR(17),
    publication VARCHAR(100),
    PRIMARY KEY (id)
);


CREATE TABLE LibUsers (
    id                BIGINT NOT NULL AUTO_INCREMENT,
    userRoleFk        SMALLINT NOT NULL,
    accountStatusFk   SMALLINT NOT NULL,
    emailUk           VARCHAR(80) NOT NULL,
    passwordHash      VARCHAR(64),
    passwordSalt      VARCHAR(8),
    passwordVersion   SMALLINT,
    unsuccessfulTries TINYINT,
    securityQuestion  VARCHAR(100),
    securityAnswer    VARCHAR(50),
    dateOfBirth       DATE,
    firstName         VARCHAR(30),
    lastName          VARCHAR(30),
    gender            CHAR(1),
    mobileUk          BIGINT,
    isdCodeFk         SMALLINT,
    imagePath         VARCHAR(80),
    bookQuota         TINYINT,
    accountCreated    DATETIME,
    PRIMARY KEY (id),
    UNIQUE (emailUk),
    UNIQUE (mobileUk),
    FOREIGN KEY (userRoleFk)
        REFERENCES UserRoles (id),
    FOREIGN KEY (accountStatusFk)
        REFERENCES UserAccountStatus (id),
    FOREIGN KEY (isdCodeFk)
        REFERENCES Countries (id)
);


ALTER TABLE LibUsers ADD COLUMN acCreatedByFk BIGINT;
ALTER TABLE LibUsers ADD CONSTRAINT FOREIGN KEY(acCreatedByFk) REFERENCES LibUsers (id);



CREATE TABLE Addresses (
    id        BIGINT NOT NULL AUTO_INCREMENT,
    userFk    BIGINT NOT NULL,
    line1     VARCHAR(30),
    line2     VARCHAR(30),
    city      VARCHAR(30),
    stateName VARCHAR(30),
    pin       VARCHAR(8),
    countryFk SMALLINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userFk)
        REFERENCES LibUsers (id),
    FOREIGN KEY (countryFk)
        REFERENCES Countries (id)
);


CREATE TABLE UserAccountStatusHistory (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    userAccountStatusFk SMALLINT NOT NULL,
    userFk      BIGINT NOT NULL,
    startDate   DATETIME,
    endDate     DATETIME,
    description VARCHAR(200),
    PRIMARY KEY (id),
    FOREIGN KEY (userAccountStatusFk)
        REFERENCES UserAccountStatus (id),
    FOREIGN KEY (userFk)
        REFERENCES LibUsers (id)
);


CREATE TABLE UserRoleHistory (
    id               BIGINT NOT NULL AUTO_INCREMENT,
    userFk           BIGINT NOT NULL,
    roleAssignedByFk BIGINT NOT NULL,
    roleReleasedByFk BIGINT NOT NULL,
    userRoleFk       SMALLINT NOT NULL,
    assignDate       DATETIME,
    releaseDate      DATETIME,
    reasonOfRelease  VARCHAR(200),
    PRIMARY KEY (id),
    FOREIGN KEY (userFk)
        REFERENCES LibUsers (id),
    FOREIGN KEY (roleAssignedByFk)
        REFERENCES LibUsers (id),
    FOREIGN KEY (roleReleasedByFk)
        REFERENCES LibUsers (id),
    FOREIGN KEY (userRoleFk)
        REFERENCES UserRoles (id)
);

ALTER TABLE LibUsers ADD COLUMN currentUserRoleHistoryFk BIGINT;
ALTER TABLE LibUsers ADD CONSTRAINT FOREIGN KEY(currentUserRoleHistoryFk) REFERENCES UserRoleHistory (id);


ALTER TABLE LibUsers ADD COLUMN currentUserAcStatusHistoryFk BIGINT;
ALTER TABLE LibUsers ADD CONSTRAINT FOREIGN KEY(currentUserAcStatusHistoryFk) REFERENCES UserAccountStatusHistory (id);



CREATE TABLE BookInstance (
    id              BIGINT NOT NULL AUTO_INCREMENT,
    bookFk          BIGINT NOT NULL,
    addedByFk       BIGINT NOT NULL,
    removedByFk     BIGINT,
    reasonOfRemoval VARCHAR(200),
    addedOn         DATETIME NOT NULL,
    removedOn       DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (bookFk)
        REFERENCES Books (id),
    FOREIGN KEY (addedByFk)
        REFERENCES LibUsers (id),
    FOREIGN KEY (removedByFk)
        REFERENCES LibUsers (id)
);


CREATE TABLE BookAssignmentHistory (
    id             BIGINT NOT NULL AUTO_INCREMENT,
    bookInstanceFk BIGINT NOT NULL,
    issuedToFk     BIGINT NOT NULL,
    issuedByFk     BIGINT NOT NULL,
    releasedByFk   BIGINT,
    assignDate     DATETIME NOT NULL,
    expectedReturnDate DATETIME NOT NULL,
    returnDate     DATETIME,
    amountFined    DECIMAL(7 , 2 ),
    comments       VARCHAR(200),
    PRIMARY KEY (id),
    FOREIGN KEY (bookInstanceFk)
        REFERENCES BookInstance (id),
    FOREIGN KEY (issuedToFk)
        REFERENCES LibUsers (id),
    FOREIGN KEY (issuedByFk)
        REFERENCES LibUsers (id),
    FOREIGN KEY (releasedByFk)
        REFERENCES LibUsers (id)
);


INSERT INTO Countries VALUES(null, 'India', '+91', 'IN');
INSERT INTO Countries VALUES(null, 'United States', '+1', 'US');
INSERT INTO Countries VALUES(null, 'Nepal', '+977', 'NP');
INSERT INTO Countries VALUES(null, 'Sri Lanka', '+94', 'SL');
INSERT INTO Countries VALUES(null, 'Bhutan', '+975', 'BT');


INSERT INTO UserRoles VALUES(null, 'NONE');
INSERT INTO UserRoles VALUES(null, 'MEMBER');
INSERT INTO UserRoles VALUES(null, 'LIBRARIAN');
INSERT INTO UserRoles VALUES(null, 'ADMIN');


INSERT INTO UserAccountStatus VALUES(null, 'ACTIVE');
INSERT INTO UserAccountStatus VALUES(null, 'CLOSED');
INSERT INTO UserAccountStatus VALUES(null, 'REVOKED');
INSERT INTO UserAccountStatus VALUES(null, 'LOCKED');
INSERT INTO UserAccountStatus VALUES(null, 'INCOMPLETE');


INSERT INTO BookInstanceStatus VALUES(null, 'ISSUED');
INSERT INTO BookInstanceStatus VALUES(null, 'AVAILABLE');
INSERT INTO BookInstanceStatus VALUES(null, 'REMOVED');