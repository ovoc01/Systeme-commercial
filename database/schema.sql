-- Active: 1700197213550@@127.0.0.1@5432@module_achat
create database module_achat;
--sequence
create SEQUENCE seq_idServices;
CREATE SEQUENCE seq_idEmploye;
create SEQUENCE seq_idNature;
create SEQUENCE seq_idProduits;
create SEQUENCE seq_idBesoins;
create SEQUENCE seq_idDetails;


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
    constraint fk_produits foreign key(idProduits) references produits(idProduits),
    constraint fk_employe foreign key(idEmploye) references employe(idEmploye),
    constraint fk_besoins foreign key(idBesoins) references besoins(idBesoins)
);


SELECT * from besoins;
select * from details;

drop table employe cascade;
 DROP TABLE besoins;

 select * from employe;