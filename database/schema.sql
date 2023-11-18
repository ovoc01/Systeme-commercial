-- Active: 1700197213550@@127.0.0.1@5432@module_achat
create database module_achat;
--sequence
create SEQUENCE seq_idServices;
CREATE SEQUENCE seq_idEmploye;

--tables
create table services(
    idServices int primary key,
    libelle varchar(255) not null
);

create table employe(
    idEmploye int primary key,
    nom varchar(255) not null,
    prenom varchar(255) not null,
    email varchar(255) not null,
    motDePasse varchar(255) not null,
    idServices int not null,
    constraint fk_services foreign key(idServices) references services(idServices)
);


