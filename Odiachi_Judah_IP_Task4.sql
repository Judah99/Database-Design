DROP TABLE IF EXISTS Emerg_Contact;
DROP TABLE IF EXISTS Phone_Number;
DROP TABLE IF EXISTS Needs;
DROP TABLE IF EXISTS Insurance_Type;
DROP TABLE IF EXISTS Donor_Credit_Card;
DROP TABLE IF EXISTS Donor_Cheque;
DROP TABLE IF EXISTS Care;
DROP TABLE IF EXISTS Serve;
DROP TABLE IF EXISTS Report;
DROP TABLE IF EXISTS Lead;
DROP TABLE IF EXISTS Expenses;
DROP TABLE IF EXISTS Business;
DROP TABLE IF EXISTS Church;
DROP TABLE IF EXISTS Sponsor;
DROP TABLE IF EXISTS Teams;
DROP TABLE IF EXISTS Org_Credit_Card;
DROP TABLE IF EXISTS Org_Cheque;

DROP TABLE IF EXISTS Donation;
DROP TABLE IF EXISTS Affiliate;
DROP TABLE IF EXISTS Organization;


DROP TABLE IF EXISTS Insurance;
DROP TABLE IF EXISTS Donor;
DROP TABLE IF EXISTS Employees;
DROP TABLE IF EXISTS Clients;
DROP TABLE IF EXISTS Volunteers;
--DROP TABLE IF EXISTS Cheque;
DROP TABLE IF EXISTS Person;

CREATE TABLE Person(
    SSN VARCHAR(12) PRIMARY KEY,
    pname VARCHAR(30),
    birth_date  DATE,
    race VARCHAR(20),
    gender VARCHAR(6),
    profession VARCHAR(16),
    mailing_list BIT,
    mailIng_address VARCHAR(40),
    email VARCHAR(23)
);

CREATE TABLE Emerg_Contact(
    SSN VARCHAR(12),
    Contact_Name VARCHAR(30),
    Contact_Info VARCHAR(20),
    Relationship VARCHAR(20),
    CONSTRAINT PK_EMERG_CONTACT PRIMARY KEY (SSN, CONTACT_NAME),
    CONSTRAINT FK_SSN FOREIGN KEY (SSN) REFERENCES PERSON(SSN)
);

CREATE TABLE phone_Number(
    phone_Number VARCHAR(15),
    SSN VARCHAR(12),
    CONSTRAINT PK_PHONE_NUMBER PRIMARY KEY (SSN, PHONE_NUMBER),
    CONSTRAINT FK_PSSN FOREIGN KEY(SSN) REFERENCES PERSON(SSN)
);

CREATE TABLE Clients(
    SSN VARCHAR(12),
    doctor_phone VARCHAR(15),
    doctor_name VARCHAR(30),
    attorney_name VARCHAR(30), 
    attorney_phone VARCHAR(30), 
    date_assigned DATE,
    PRIMARY KEY (SSN),
    CONSTRAINT FK_CSSN FOREIGN KEY(SSN) REFERENCES PERSON(SSN)
);

CREATE TABLE Needs(
    SSN VARCHAR(12),
    visiting INT,
    shopping INT,
    housekeeping INT,
    transportation INT,
    CONSTRAINT PK_NEEDS PRIMARY KEY (SSN, VISITING,SHOPPING,HOUSEKEEPING,TRANSPORTATION),
    CONSTRAINT FK_NSSN FOREIGN KEY(SSN) REFERENCES Clients(SSN)
);
 CREATE NONCLUSTERED INDEX IX_NEEDS ON dbo.NEEDS(TRANSPORTATION);
--DROP INDEX IX_NEEDS ON dbo.NEEDS(TRANSPORTATION)

CREATE TABLE Insurance(
    policy_ID VARCHAR(15) PRIMARY KEY,
    provider_ID VARCHAR(10),
    provider_ADDRESS VARCHAR(30),
    SSN VARCHAR(12),
    CONSTRAINT FK_ISSN FOREIGN KEY (SSN) REFERENCES PERSON(SSN)
);

CREATE TABLE Insurance_Type(
    Insurance_Type VARCHAR(10),
    policy_ID VARCHAR(15),
    CONSTRAINT PK_INS_TYPE PRIMARY KEY (INSURANCE_TYPE,POLICY_ID),
    CONSTRAINT FK_INS_TYPE FOREIGN KEY (policy_ID) REFERENCES INSURANCE(POLICY_ID)
);
CREATE NONCLUSTERED INDEX IX_INSURANCE_TYPE ON dbo.INSURANCE_TYPE(INSURANCE_TYPE);
--DROP INDEX IX_INSURANCE_TYPE ON dbo.INSURANCE_TYPE(INSURANCE_TYPE); 

CREATE TABLE Volunteers(
    SSN VARCHAR(12) PRIMARY KEY,
    date_joined DATE,
    date_recent_training DATE,
    location_recent_training VARCHAR(15),
    CONSTRAINT FK_VSSN FOREIGN KEY (SSN) REFERENCES PERSON(SSN)
    
);

CREATE TABLE Employees(
    SSN VARCHAR(12) PRIMARY KEY,
    salary INT,
    marital_status VARCHAR(10),
    hire_date DATE,
    CONSTRAINT FK_ESSN FOREIGN KEY (SSN) REFERENCES PERSON(SSN)

);


CREATE TABLE Donor(
    SSN VARCHAR(12) PRIMARY KEY,
    donor_date DATE,
    amount INT,
    donor_type VARCHAR(12),
    campaign_name VARCHAR(12),
    anonymity BIT,
    CONSTRAINT FK_DSSN FOREIGN KEY (SSN) REFERENCES PERSON(SSN)
);

CREATE TABLE Donor_Credit_Card(
    SSN VARCHAR(12),
    card_no VARCHAR(20),
    card_type VARCHAR(10),
    expiration_date DATE,
    CONSTRAINT PK_CC PRIMARY KEY (SSN,card_no),
    CONSTRAINT FK_CCSSN FOREIGN KEY (SSN) REFERENCES DONOR(SSN)
);

CREATE TABLE Donor_Cheque(
    SSN VARCHAR(12),
    cheque_no CHAR(4),
    CONSTRAINT PK_CHEQUE PRIMARY KEY (SSN,cheque_no),
    CONSTRAINT FK_DONSSN FOREIGN KEY (SSN) REFERENCES DONOR(SSN)

);

CREATE TABLE Teams(
    team_Name VARCHAR(15) PRIMARY KEY,
    team_type VARCHAR(10),
    date_formed DATE,
);
CREATE NONCLUSTERED INDEX IX_TEAMS ON dbo.TEAMS(DATE_FORMED);
--DROP INDEX IX_TEAMS ON dbo.TEAMS(DATE_FORMED);

CREATE TABLE Lead(
    Member_SSN VARCHAR(12),
    Leader_SSN VARCHAR(12),
    CONSTRAINT PK_LEAD PRIMARY KEY (Member_SSN, Leader_SSN),
    CONSTRAINT FK_LEAD FOREIGN KEY (Member_SSN) REFERENCES VOLUNTEERS(SSN),
    CONSTRAINT FK_LEAD2 FOREIGN KEY (Leader_SSN) REFERENCES VOLUNTEERS(SSN)
)

CREATE TABLE Care(
    SSN VARCHAR(12),
    team_Name VARCHAR(15),
    activity BIT,
    CONSTRAINT PK_CARE PRIMARY KEY (SSN,team_Name),
    CONSTRAINT FK_CARE FOREIGN KEY (SSN) REFERENCES CLIENTS(SSN),
    CONSTRAINT FK_CARE2 FOREIGN KEY (team_Name) REFERENCES TEAMS(team_Name)
);
 CREATE NONCLUSTERED INDEX IX_CARE ON dbo.CARE(TEAM_NAME);
--DROP INDEX IX_CARE ON dbo.CARE(TEAMNAME);

CREATE TABLE Serve(
    team_Name VARCHAR(15),
    SSN VARCHAR(12),
    serve_hours INT,
    activity BIT,
    CONSTRAINT PK_SERVE PRIMARY KEY(team_Name,SSN,serve_hours),
    CONSTRAINT FK_SERVE FOREIGN KEY(team_Name) REFERENCES TEAMS(team_Name),
    CONSTRAINT FK_SERVE2 FOREIGN KEY(SSN) REFERENCES  VOLUNTEERS(SSN)
);
   CREATE NONCLUSTERED INDEX IX_SERVE ON dbo.SERVE(TEAM_NAME);
--DROP INDEX IX_SERVE ON dbo.SERVE(TEAMNAME);

CREATE TABLE Report(
    SSN VARCHAR(12),
    team_Name VARCHAR(15),
    report_date DATE,
    content_description VARCHAR(30), 
    CONSTRAINT PK_REPORT PRIMARY KEY (SSN,team_Name),
    CONSTRAINT FK_RSSN FOREIGN KEY (SSN) REFERENCES Employees(SSN),
    CONSTRAINT FK_TNSSN FOREIGN KEY (team_Name) REFERENCES Employees(team_Name)
);

CREATE TABLE Expenses(
    amount INT,
    expenses_date DATE,
    expenses_description VARCHAR(30),
    SSN VARCHAR(12), 
    CONSTRAINT PK_EXPENSES PRIMARY KEY (expenses_description,SSN),
    CONSTRAINT FK_EXPENSES FOREIGN KEY (SSN) REFERENCES PERSON(SSN),
 );
CREATE NONCLUSTERED INDEX IX_EXPENSES ON dbo.EXPENSES(EXPENSES_DATE)
-- DROP INDEX IX_EXPENSES ON dbo.EXPENSES(EXP_DATE);


CREATE TABLE Organization(
    ORG_NAME VARCHAR(25) PRIMARY KEY,
    mailing_address VARCHAR(25),
    phone_Number VARCHAR(25),
    contact_person VARCHAR(30), 
);

CREATE TABLE Business(
    org_Name VARCHAR(25) PRIMARY KEY,
    org_type VARCHAR(25),
    bus_size  VARCHAR(25),
    website VARCHAR(35),
    CONSTRAINT FK_BORG_NAME FOREIGN KEY (org_Name) REFERENCES Organization(org_Name) 
);

CREATE TABLE Church(
    org_Name VARCHAR(25) PRIMARY KEY,
    religious_affiliation VARCHAR(25),
    CONSTRAINT FK_CORG_NAME FOREIGN KEY (org_Name) REFERENCES Organization(org_Name) 
);

CREATE TABLE Sponsor(
    org_Name VARCHAR(25),
    team_Name VARCHAR(15),
    CONSTRAINT PK_SPONSOR PRIMARY KEY (team_Name, org_Name),
    CONSTRAINT FK_SPTEAM_NAME FOREIGN KEY (team_Name) REFERENCES TEAMS(team_Name),
    CONSTRAINT FK_SPORG_NAME FOREIGN KEY (org_Name) REFERENCES Organization(org_Name)
);
 CREATE NONCLUSTERED INDEX IX_SPONSOR ON dbo.SPONSOR(ORG_NAME);
--DROP INDEX IX_SPONSOR ON dbo.SPONSOR(ORG_NAME); 

CREATE TABLE Donation(
    org_name VARCHAR(25),
    donation_date DATE,
    amount INT, 
    donation_type VARCHAR(25),
    campaign_name VARCHAR(25),
    anonymity BIT,
    CONSTRAINT PK_DONATION PRIMARY KEY (org_Name, donation_date, amount, donation_type),
    CONSTRAINT FK_DORG_NAME FOREIGN KEY (org_Name) REFERENCES ORGANIZATION(org_Name) 
);

CREATE TABLE Org_Cheque(
    org_Name VARCHAR(25),
    donation_date  DATE,
    amount  INT, 
    donation_type  VARCHAR(25),
    cheque_no VARCHAR(5),
    CONSTRAINT PK_Org_Cheque PRIMARY KEY (org_Name, donation_date, amount, donation_type, cheque_no),
    CONSTRAINT FK_Org_Cheque FOREIGN KEY (org_Name, donation_date, amount, donation_type) REFERENCES DONATION(org_Name, donation_date, amount, donation_type),
);

CREATE TABLE Org_Credit_Card(
    org_Name VARCHAR(25),
    donation_date  DATE,
    amount INT, 
    donation_type VARCHAR(25),
    card_no VARCHAR(20),
    card_type VARCHAR(10),
    expiration_date DATE,
    CONSTRAINT PK_Org_Credit_Card PRIMARY KEY (org_Name, donation_date, amount, donation_type, card_no),
    CONSTRAINT FK_Org_Credit_Card FOREIGN KEY (org_Name, donation_date, amount, donation_type) REFERENCES DONATION(org_Name, donation_date, amount, donation_type),
);

CREATE TABLE Affiliate(
    SSN VARCHAR(12),
    Org_Name VARCHAR(25),
    CONSTRAINT PK_AFFILIATE PRIMARY KEY (SSN, Org_Name),
    CONSTRAINT FK_AF FOREIGN KEY (SSN) REFERENCES PERSON(SSN),
    CONSTRAINT FK_AF2 FOREIGN KEY (Org_Name) REFERENCES Organization(Org_Name),
);
