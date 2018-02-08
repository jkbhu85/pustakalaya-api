/*
 * This SQL was written for Oracle 12c database.
 * Author: Jitendra Kumar
 */

-- alias: COUN
CREATE TABLE Countries (
  coun_pk      NUMBER(3) PRIMARY KEY,
  name         VARCHAR2(50) NOT NULL,
  isd_code     VARCHAR2(4),
  abbreviation  VARCHAR2(3)
);

CREATE SEQUENCE countries_seq
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;



-- alias: USRO
CREATE TABLE User_Roles (
  usro_pk   NUMBER(2) PRIMARY KEY,
  role_name VARCHAR2(24) NOT NULL
);

CREATE SEQUENCE user_roles_seq
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;



-- alias: USAS
CREATE TABLE User_Ac_Status (
  usas_pk     NUMBER(2) PRIMARY KEY,
  status_name VARCHAR2(24) NOT NULL
);

CREATE SEQUENCE user_ac_status_seq
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;



-- alias: BOIS
CREATE TABLE Book_Instance_Status (
  bois_pk     NUMBER(2) PRIMARY KEY,
  status_name VARCHAR2(24) NOT NULL
);

CREATE SEQUENCE book_instance_status_seq
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;



-- alias: BOOK
CREATE TABLE Books (
  book_pk         NUMBER(8) PRIMARY KEY,
  title           VARCHAR2(150),
  author          VARCHAR2(250),
  edition         NUMBER(2),
  price           NUMBER(9,2),
  number_of_pages NUMBER(5),
  isbn            VARCHAR2(17),
  publication     VARCHAR2(100),
  CONSTRAINT EDITION_CHK CHECK(edition > 0),
  CONSTRAINT PRICE_CHK CHECK(price >= 0),
  CONSTRAINT NUMBER_OF_PAGES_CHK CHECK(number_of_pages > 0)
);

CREATE SEQUENCE books_seq
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;



-- alias: LIUS
CREATE TABLE Lib_Users(
  lius_pk            NUMBER(7) PRIMARY KEY,
  lius_usro_fk       NUMBER(2),
  lius_usas_fk       NUMBER(2),
  email_uk           VARCHAR2(80) NOT NULL UNIQUE,
  password_hash      CHAR(64),
  password_salt      CHAR(8),
  password_version   NUMBER(2),
  unsuccessful_tries NUMBER(2) DEFAULT 0,
  security_question  VARCHAR2(100),
  security_answer    VARCHAR2(50),
  date_of_birth      DATE,
  first_name         VARCHAR2(30),
  last_name          VARCHAR2(80),
  gender             CHAR(1),
  mobile_number_uk   CHAR(10) UNIQUE,
  lius_coun_fk_monu  NUMBER(3),
  image_path         VARCHAR2(20),
  book_quota         NUMBER(2),
  created_on         DATE,
  CONSTRAINT LIUS_USRO_FK FOREIGN KEY (lius_usro_fk) REFERENCES User_Roles(usro_pk),
  CONSTRAINT LIUS_USAS_FK FOREIGN KEY (lius_usas_fk) REFERENCES User_Ac_Status(usas_pk),
  CONSTRAINT LIUS_COUN_FK_MONU FOREIGN KEY (lius_coun_fk_monu) REFERENCES Countries(coun_pk),
  CONSTRAINT GENDER_CHK CHECK(gender IN ('M', 'F', 'O'))
);

CREATE SEQUENCE lib_users_seq
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;



-- alias: ADDR
CREATE TABLE Addresses (
  addr_pk   NUMBER(8) PRIMARY KEY,
  addr_lius_fk   NUMBER(7) NOT NULL,
  address_type   CHAR(1) NOT NULL,
  address_line_1 VARCHAR2(30),
  address_line_2 VARCHAR2(30),
  city           VARCHAR2(30),
  state          VARCHAR2(30),
  pin            VARCHAR2(8),
  addr_coun_fk   NUMBER(3) NOT NULL,
  CONSTRAINT ADDR_LIUS_FK FOREIGN KEY (addr_lius_fk) REFERENCES Lib_Users(lius_pk),
  CONSTRAINT ADDR_COUN_FK FOREIGN KEY (addr_coun_fk) REFERENCES Countries(coun_pk),
  CONSTRAINT ADDRESS_TYPE_CHK CHECK (address_type IN ('P', 'C'))
);

CREATE SEQUENCE addresses_seq
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;



-- alias: UASH
CREATE TABLE User_Ac_Status_History(
  uash_pk      NUMBER(10) PRIMARY KEY,
  uash_usas_fk NUMBER(2) NOT NULL,
  uash_lius_fk NUMBER(7) NOT NULL,
  start_date   DATE NOT NULL,
  end_date     DATE,
  comments     VARCHAR2(200),
  CONSTRAINT UASH_USAS_FK FOREIGN KEY (uash_usas_fk) REFERENCES User_Ac_Status(usas_pk),
  CONSTRAINT UASH_LIUS_FK FOREIGN KEY (uash_lius_fk) REFERENCES Lib_Users(lius_pk)
);

CREATE SEQUENCE user_ac_status_history_seq
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;



-- alias: USRH
CREATE TABLE User_Role_History (
  usrh_pk       NUMBER(10) PRIMARY KEY,
  usrh_lius_fk_user        NUMBER(7) NOT NULL,
  usrh_lius_fk_assigned_by NUMBER(7) NOT NULL,
  usrh_lius_fk_released_by NUMBER(7),
  usrh_usro_fk  NUMBER(2) NOT NULL,
  assign_date   DATE NOT NULL,
  release_date  DATE,
  reason_of_release       VARCHAR2(200),
  CONSTRAINT USRH_USRO_FK      FOREIGN KEY (usrh_usro_fk) REFERENCES User_Roles(usro_pk),
  CONSTRAINT USRH_LIUS_FK_USER FOREIGN KEY (usrh_lius_fk_user) REFERENCES Lib_Users(lius_pk),
  CONSTRAINT USRH_LIUS_FK_ASBY FOREIGN KEY (usrh_lius_fk_assigned_by) REFERENCES Lib_Users(lius_pk),
  CONSTRAINT USRH_LIUS_FK_REBY FOREIGN KEY (usrh_lius_fk_released_by) REFERENCES Lib_Users(lius_pk)
);

CREATE SEQUENCE user_role_history_seq
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;



-- alias: USIN
CREATE TABLE User_Info(
  usin_pk_fk   NUMBER(7) PRIMARY KEY,
  usin_lius_fk_ac_created_by NUMBER(7),
  usin_usrh_fk NUMBER(10),
  usin_uash_fk NUMBER(10),
  CONSTRAINT USIN_LIUS_FK_USER FOREIGN KEY (usin_pk_fk) REFERENCES Lib_Users(lius_pk),
  CONSTRAINT USIN_LIUS_FK_ACCB FOREIGN KEY (usin_lius_fk_ac_created_by) REFERENCES Lib_Users(lius_pk),
  CONSTRAINT USIN_USRH_FK FOREIGN KEY (usin_usrh_fk) REFERENCES User_Role_History(usrh_pk),
  CONSTRAINT USIN_UASH_FK FOREIGN KEY (usin_uash_fk) REFERENCES User_Ac_Status_History(uash_pk)
);



-- alias: BOIN
CREATE TABLE Book_Instances(
  boin_pk      NUMBER(9) PRIMARY KEY,
  boin_book_fk NUMBER(8),
  boin_bois_fk NUMBER(2),
  boin_lius_fk_added_by   NUMBER(7),
  boin_lius_fk_removed_by NUMBER(7),
  reason_of_removal VARCHAR2(200),
  CONSTRAINT BOIN_BOOK_FK FOREIGN KEY (boin_book_fk) REFERENCES Book_Instance_Status(bois_pk),
  CONSTRAINT BOIN_BOIS_FK FOREIGN KEY (boin_bois_fk) REFERENCES Books(book_pk),
  CONSTRAINT BOIN_LIUS_ADBY_FK FOREIGN KEY (boin_lius_fk_added_by) REFERENCES Lib_Users(lius_pk),
  CONSTRAINT BOIN_LIUS_REBY_FK FOREIGN KEY (boin_lius_fk_removed_by) REFERENCES Lib_Users(lius_pk)
);

ALTER TABLE BOOK_INSTANCES ADD added_on DATE;
ALTER TABLE BOOK_INSTANCES ADD removed_on DATE;

CREATE SEQUENCE book_instances_seq
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;



-- alias: BOAH
CREATE TABLE Book_Assignment_History (
  boah_pk        NUMBER(10) PRIMARY KEY,
  boah_boin_fk           NUMBER(9),
  boah_lius_fk_issued_to NUMBER(7),
  boah_lius_fk_issued_by NUMBER(7),
  assign_date          DATE,
  expected_return_date DATE,
  return_date          DATE,
  amount_fined         NUMBER(9,2) DEFAULT 0,
  comments             VARCHAR2(200),
  CONSTRAINT BOAH_BOIN_FK      FOREIGN KEY (boah_boin_fk) REFERENCES Book_Instances(boin_pk),
  CONSTRAINT BOAH_LIUS_FK_ISTO FOREIGN KEY (boah_lius_fk_issued_to) REFERENCES Lib_Users(lius_pk),
  CONSTRAINT BOAH_LIUS_FK_ISBY FOREIGN KEY (boah_lius_fk_issued_by) REFERENCES Lib_Users(lius_pk),
  CONSTRAINT AMOUNT_FINED_CHK CHECK(amount_fined >= 0)
);


CREATE SEQUENCE book_assignment_history_seq
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;
