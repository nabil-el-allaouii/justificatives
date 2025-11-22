package org.example.justificatives.repository;

import org.example.justificatives.entity.Societe;
import org.example.justificatives.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    
    Optional<Utilisateur> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<Utilisateur> findByRole(Utilisateur.Role role);
    
    List<Utilisateur> findBySociete(Societe societe);
    
    List<Utilisateur> findByActif(Boolean actif);
}