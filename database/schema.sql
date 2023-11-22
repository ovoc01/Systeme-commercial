-- Active: 1700197213550@@127.0.0.1@5432@module_achat
create database module_achat;
--sequence
create SEQUENCE seq_idServices;
CREATE SEQUENCE seq_idEmploye;
create SEQUENCE seq_idNature;
create SEQUENCE seq_idProduits;
create SEQUENCE seq_idBesoins;
create SEQUENCE seq_idDetails;
 create SEQUENCE seq_idDetails_proforma;
 create SEQUENCE seq_idProforma;
 create SEQUENCE seq_idBondeCommande;
 create SEQUENCE seq_idDetails_bonde_commande;


--tables
create table services(
    idServices int primary key,
    libelle varchar(255) not null
);

create  table employe(
    idEmploye int primary key,
    nom varchar(255) not null,
    prenom varchar(255) not null,
    email varchar(255) not null,
    motDePasse varchar(255) not null,
    idServices int not null,
    mgr int ,
    constraint fk_services foreign key(idServices) references services(idServices),
    constraint fk_employe foreign key(mgr) references employe(idEmploye)
);

create table nature(
    idNature int primary key,
    libelle varchar(255) not null
);

create table produits(
    idProduits int primary key,
    libelle varchar(255) not null,
    idNature int not null,
    constraint fk_nature foreign key(idNature) references nature(idNature)
);

create table besoins(
    idBesoins int primary key,
    dateCreation timestamp not null,
    idEmploye int not null,
    idService int not null,
    etat int not NULL default 0,
    reference VARCHAR not null,
    constraint fk_employe foreign key(idEmploye) references employe(idEmploye),
    constraint fk_service foreign key(idService) references services(idServices)
);

create table besoin_transaction_log(
    idBesoins int not null,
    idEmploye int not null,
    idService int not null,
    etat int not NULL default 0,
    dateCreation timestamp not null,
    constraint fk_employe foreign key(idEmploye) references employe(idEmploye),
    constraint fk_service foreign key(idService) references services(idServices)
);

create table details(
    idDetails int primary key,
    idBesoins int not null,
    idProduits int not null,
    idEmploye int not null,
    quantite int not null,
    etat int not NULL default 0,
    constraint fk_produits foreign key(idProduits) references produits(idProduits),
    constraint fk_employe foreign key(idEmploye) references employe(idEmploye),
    constraint fk_besoins foreign key(idBesoins) references besoins(idBesoins)
);


create table fournisseurs (
    idFournisseurs int primary key,
    libelle VARCHAR not NULL,
    email varchar(255) not null,
    address VARCHAR not NULL
);

create table proforma(
    idProforma int primary key,
    idFournisseurs int not null,
    dateCreation date,
    constraint fk_fournisseurs FOREIGN KEY(idFournisseurs) REFERENCES fournisseurs(idFournisseurs)
);




create table details_proforma(
    idDetailsProforma int primary key,
    idProforma int not null,
    prix float not null,
    idProduits int not null,
    quantite int not null,
    constraint fk_proforma FOREIGN KEY(idProforma) REFERENCES proforma(idProforma),
    constraint fk_produits FOREIGN KEY(idProduits) REFERENCES produits(idProduits)
);

create table bonde_commande(
    idBondeCommande int primary key,
    idFournisseurs int not null,
    dateCreation date,
    etat int not NULL default 0,
    constraint fk_fournisseurs FOREIGN KEY(idFournisseurs) REFERENCES fournisseurs(idFournisseurs)
);

create table details_bonde_commande
(
    idDetailsBondeCommande int primary key,
    idBondeCommande int not null,
    idProduits int not null,
    quantite int not null,
    prix float not null,
    constraint fk_produits FOREIGN KEY(idProduits) REFERENCES produits(idProduits),
    constraint fk_bonde_commande FOREIGN KEY(idBondeCommande) REFERENCES bonde_commande(idBondeCommande)
);

SELECT * from besoins;
select * from details;

drop table employe cascade;
 DROP TABLE details cascade;

 select * from employe;

 select * from besoin_transaction_log;
 
 select * from besoin_transaction_log;
 SELECT * from besoins;

drop table besoins cascade;
drop table details cascade;
drop table besoin_transaction_log cascade;