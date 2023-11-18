insert into services (idServices, libelle) values (1, 'Service 1');
insert into services (idServices, libelle) values (2, 'Service 2');

--employe
insert into employe (idEmploye, nom, prenom, email, motDePasse, idServices) values (1, 'Doe', 'John', 'john.doe@gmail.com', 'password', 1);
insert into employe (idEmploye, nom, prenom, email, motDePasse, idServices) values (2, 'Smith', 'Jane', 'jane.smith@gmail.com', 'password', 2);


--nature
insert into nature (idNature, libelle) values (1, 'Sanitaire');
insert into nature (idNature, libelle) values (2, 'Fourniture de bureau');

--produits
insert into produits (idProduits, libelle, idNature) values (1, 'Papier', 2);
insert into produits (idProduits, libelle, idNature) values (2, 'Stylo', 2);
insert into produits (idProduits, libelle, idNature) values (3, 'Gel hydroalcoolique', 1);
insert into produits (idProduits, libelle, idNature) values (4, 'Masque', 1);
