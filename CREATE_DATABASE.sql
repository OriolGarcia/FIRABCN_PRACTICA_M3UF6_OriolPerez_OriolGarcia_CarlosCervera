CREATE DATABASE Fira_DB;

CREATE USER 'Fira_Manager'@'localhost' IDENTIFIED BY 'Fira_Manager';
GRANT ALL PRIVILEGES ON Fira_DB.* TO 'Fira_Manager'@'localhost';
GRANT ALL PRIVILEGES ON Fira_DB.* TO 'root'@'localhost';

USE Fira_DB;

CREATE TABLE Usuaris(
Username VARCHAR(100),
Password VARCHAR (500),
PermisosAdmin boolean,
Actiu boolean,
PRIMARY KEY(Username)
);

CREATE TABLE TipusEmpresa(
TipusID INT auto_increment NOT NULL,
TitolTipus VARCHAR (100),
Ambit VARCHAR(100),
PRIMARY KEY(TipusID)
);

CREATE TABLE Fires(
FiraID INT auto_increment NOT NULL,
Titol VARCHAR (200),
Ubicacio VARCHAR(400),
`Superficie Fira` DECIMAL,
DataInici DATE,
DataFi DATE,
PRIMARY KEY(FiraID)
);

CREATE TABLE Empreses (
EmpresaID INT AUTO_INCREMENT NOT NULL,
Nom VARCHAR(200),
CIF VARCHAR(20),
`Persona de contacte` VARCHAR (200),
Telefon VARCHAR(15),
Tipus INT,
Fira INT,
PRIMARY KEY (EmpresaID),
FOREIGN KEY(Tipus) REFERENCES TipusEmpresa(`TipusID`),
FOREIGN KEY(Fira) REFERENCES Fires(`FiraID`)
);

CREATE TABLE ECONOMIAFIRA(
FiraID INT,
DATA DATE,
NumVisitants INT,
Recaptació DECIMAL,
PRIMARY KEY (FiraID,DATA),
FOREIGN KEY(FiraID) REFERENCES Fires(`FiraID`)
);

CREATE TABLE Estands (
EstandID INT auto_increment NOT NULL,
Nom VARCHAR(200),
SuperficieEstand DECIMAL,
QuotaEstand DECIMAL,
DataInici DATE,
DataFi DATE,
Fira INT,
Empresa INT,
PRIMARY KEY (EstandID),
FOREIGN KEY(Fira) REFERENCES Fires(`FiraID`),
FOREIGN KEY(Empresa) REFERENCES Empreses(`EmpresaID`)
);

INSERT INTO Usuaris VALUES('admin',MD5('admin'),true,true);
SELECT COUNT(Username), MD5('admin'),Password FROM Usuaris WHERE Username = 'admin';

DROP SCHEMA Fira_DB;

SELECT Username, Password FROM Usuaris;

INSERT INTO TipusEmpresa VALUES (NULL,'Informatica','Informatica');

ALTER TABLE Estands ADD Fira INT;
ALTER TABLE Estands ADD CONSTRAINT FOREIGN KEY(Fira) REFERENCES Fires(`FiraID`);

ALTER TABLE Estands ADD Empresa INT;
ALTER TABLE Estands ADD CONSTRAINT FOREIGN KEY(Empresa) REFERENCES Empreses(`EmpresaID`);
use Fira_db;
ALTER TABLE Fires ADD `Preu Entrada` Decimal after `Superficie Fira`;


