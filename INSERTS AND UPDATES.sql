Use Fira_DB;


INSERT INTO Usuaris VALUES('admin',MD5('admin'),true,true);
SELECT count(Username), MD5('admin'),Password FROM Usuaris WHERE Username = 'admin';