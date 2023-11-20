insert into services (idServices, libelle) values (1, 'Service 1');
insert into services (idServices, libelle) values (2, 'Service 2');
insert into services (idServices, libelle) values (3, 'Département achat');
insert into services (idServices, libelle) values (4, 'Département RH');
insert into services (idServices, libelle) values (5, 'Département Finance');

--employe
insert into employe (idEmploye, nom, prenom, email, motDePasse, idServices) values (1, 'Doe', 'John', 'john.doe@gmail.com', 'password', 1);
insert into employe (idEmploye, nom, prenom, email, motDePasse, idServices) values (2, 'Smith', 'Jane', 'jane.smith@gmail.com', 'password', 2);
insert into employe (idEmploye, nom, prenom, email, motDePasse, idServices, mgr) values (3, 'Manager', 'One', 'manager.one@gmail.com', 'password', 1, 1);
insert into employe (idEmploye, nom, prenom, email, motDePasse, idServices, mgr) values (4, 'Manager', 'Two', 'manager.two@gmail.com', 'password', 2, 1);
insert into employe (idEmploye, nom, prenom, email, motDePasse, idServices) values (5, 'Departement', 'Achat', 'departement@gmail.com','12345', 3);
--nature
insert into nature (idNature, libelle) values (1, 'Sanitaire');
insert into nature (idNature, libelle) values (2, 'Fourniture de bureau');

--produits
insert into produits (idProduits, libelle, idNature) values (1, 'Papier', 2);
insert into produits (idProduits, libelle, idNature) values (2, 'Stylo', 2);
insert into produits (idProduits, libelle, idNature) values (3, 'Gel hydroalcoolique', 1);
insert into produits (idProduits, libelle, idNature) values (4, 'Masque', 1);



--fournisseur
insert into fournisseurs (idFournisseurs,libelle, email,address) values (1, 'Super U', 'superu@gmail.com','Ankorondrao');
insert into fournisseurs (idFournisseurs,libelle, email,address) values (2, 'Jumbo', 'jumbo@gmail.com','Ivandry');
insert into fournisseurs (idFournisseurs,libelle, email,address) values (3, 'Score', 'score@gmail.com','Ivato');
insert into fournisseurs(idFournisseurs,libelle, email,address) values (4, 'Maki', 'maki@gmail.com','67ha');

SELECT * from employe;