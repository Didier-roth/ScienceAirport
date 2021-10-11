show databases;
use BD_AIRPORT;
CREATE TABLE Agent(
   RegIDAgent int PRIMARY KEY,
   nom VARCHAR(50),
   Prenom VARCHAR(50),
   poste VARCHAR(50),
   dateNaissance DATE
);

CREATE TABLE Avion(
   AvionID VARCHAR(50) PRIMARY KEY,
   nom VARCHAR(50),
   place INT,
   etat VARCHAR(50),
   RegIDAgent int NOT NULL,
   FOREIGN KEY(RegIDAgent) REFERENCES Agent(RegIDAgent)
);

CREATE TABLE Vols(
   NumVol VARCHAR(50) PRIMARY KEY,
   destination VARCHAR(50),
   HeureDepart DATE,
   HeureArrivee DATE,
   HeureArrivePrevue DATE,
   AvionID VARCHAR(50) NOT NULL,
   FOREIGN KEY(AvionID) REFERENCES Avion(AvionID)
);

CREATE TABLE Billet(
   NumBillet VARCHAR(50) PRIMARY KEY,
   nom VARCHAR(50),
   prenom VARCHAR(50),
   regID VARCHAR(50) NOT NULL,
   NumVol VARCHAR(50) NOT NULL,
   FOREIGN KEY(NumVol) REFERENCES Vols(NumVol)
);

CREATE TABLE Bagage(
   BagageID VARCHAR(50) PRIMARY KEY,
   Poids INT,
   type VARCHAR(50),
   RegIDAgent int NOT NULL,
   NumBillet VARCHAR(50) NOT NULL,
   FOREIGN KEY(RegIDAgent) REFERENCES Agent(RegIDAgent),
   FOREIGN KEY(NumBillet) REFERENCES Billet(NumBillet)
);

show tables;

select * from Agent;
select * from Avion;
select * from Bagage;
select * from Billet;
select * from Vols;