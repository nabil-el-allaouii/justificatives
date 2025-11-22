package org.example.justificatives.repository;

import org.example.justificatives.entity.Document;
import org.example.justificatives.entity.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    
    List<Document> findBySociete(Societe societe);
    
    List<Document> findByStatut(Document.StatutDocument statut);
    
    List<Document> findBySocieteAndStatut(Societe societe, Document.StatutDocument statut);
    
    List<Document> findByType(Document.TypeDocument type);
    
    @Query("SELECT d FROM Document d WHERE d.societe = :societe AND YEAR(d.datePiece) = :exercice")
    List<Document> findBySocieteAndExercice(@Param("societe") Societe societe, @Param("exercice") int exercice);
    
    @Query("SELECT d FROM Document d WHERE d.datePiece BETWEEN :dateDebut AND :dateFin")
    List<Document> findByDatePieceBetween(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);
    
    List<Document> findByNumeroPiece(String numeroPiece);
}