package org.syscom.module_achat.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.syscom.module_achat.data.entity.Employe;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Integer> {
   Employe findByEmail(String email);
   Employe findByEmailAndMotDePasse(String email, String motDePasse);
   
}