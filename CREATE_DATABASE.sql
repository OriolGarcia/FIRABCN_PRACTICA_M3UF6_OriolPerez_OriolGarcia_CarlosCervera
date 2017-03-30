CREATE DATABASE Fira_DB;

CREATE USER 'Fira_Manager'@'localhost' IDENTIFIED BY 'Fira_Manager';
GRANT ALL PRIVILEGES ON Fira_DB.* TO 'Fira_Manager'@'localhost';
GRANT ALL PRIVILEGES ON Fira_DB.* TO 'root'@'localhost';
Use Fira_DB;
CREATE TABLE Usuaris(
Username VARCHAR(100),
Password VARCHAR (500),
PermisosAdmin boolean,
Actiu boolean

);
create table TipusEmpresa(
TipusID INT auto_increment NOT NULL,
TitolTipus VARCHAR (100),
Ambit VARCHAR(100),
primary Key(TipusID)
);
Create table Fires(
FiraID INT auto_increment NOT NULL,
Titol VARCHAR (200),
Ubicacio VARCHAR(400),
`Superficie Fira` DECIMAL,
DataInici date,
DataFi date,
PRIMARY KEY(FiraID));

create table Empreses (
EmpresaID INT AUTO_INCREMENT NOT NULL,
Nom VARCHAR(200),
CIF VARCHAR(20),
`Persona de contacte` VARCHAR (200),
Telefon VARCHAR(15),
Tipus INT,
PRIMARY KEY (EmpresaID),
 foreign key (Tipus) references TipusEmpresa(`TipusID`)
);
CREATE TABLE ECONOMIAFIRA(
FiraID INT,
DATA DATE,
NumVisitants INT,
Recaptació decimal,
Primary key (FiraID,DATA),
 foreign key (FiraID) references Fires(`FiraID`)

);
CREATE TABLE Estands (
EstandID INT auto_increment NOT NULL,
Nom VARCHAR(200),
SuperficieEstand DECIMAL,
QuotaEstand DECIMAL,
DataInici DATE,
DataFi DATE,
Fira INT,
PRIMARY KEY (EstandID),
 foreign key (Fira) references Fires(`FiraID`)


);

INSERT INTO Usuaris VALUES('admin','admin',true,true);