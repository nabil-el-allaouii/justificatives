package org.example.justificatives.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.justificatives.entity.Document;
import org.example.justificatives.entity.Societe;
import org.example.justificatives.entity.Utilisateur;
import org.example.justificatives.repository.DocumentRepository;
import org.example.justificatives.repository.SocieteRepository;
import org.example.justificatives.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    
    private final SocieteRepository societeRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final DocumentRepository documentRepository;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("Loading sample data...");
        
        // Create Societes
        Societe societe1 = new Societe();
        societe1.setRaisonSociale("Maroc Telecom");
        societe1.setIce("001234567890001");
        societe1.setAdresse("Avenue Annakhil, Rabat");
        societe1.setTelephone("0537123456");
        societe1.setEmailContact("contact@maroctelecom.ma");
        societeRepository.save(societe1);
        
        Societe societe2 = new Societe();
        societe2.setRaisonSociale("Cosumar");
        societe2.setIce("001234567890002");
        societe2.setAdresse("Route Côtière, Casablanca");
        societe2.setTelephone("0522987654");
        societe2.setEmailContact("info@cosumar.ma");
        societeRepository.save(societe2);
        
        // Create Utilisateurs - SOCIETE role
        Utilisateur user1 = new Utilisateur();
        user1.setEmail("comptable@maroctelecom.ma");
        user1.setPassword("password123");
        user1.setNomComplet("Ahmed Alami");
        user1.setRole(Utilisateur.Role.SOCIETE);
        user1.setSociete(societe1);
        utilisateurRepository.save(user1);
        
        Utilisateur user2 = new Utilisateur();
        user2.setEmail("finance@cosumar.ma");
        user2.setPassword("password123");
        user2.setNomComplet("Fatima Benani");
        user2.setRole(Utilisateur.Role.SOCIETE);
        user2.setSociete(societe2);
        utilisateurRepository.save(user2);
        
        // Create Utilisateurs - COMPTABLE role
        Utilisateur comptable1 = new Utilisateur();
        comptable1.setEmail("comptable@alamane.ma");
        comptable1.setPassword("password123");
        comptable1.setNomComplet("Hassan Tazi");
        comptable1.setRole(Utilisateur.Role.COMPTABLE);
        comptable1.setSociete(null);
        utilisateurRepository.save(comptable1);
        
        Utilisateur comptable2 = new Utilisateur();
        comptable2.setEmail("expert@alamane.ma");
        comptable2.setPassword("password123");
        comptable2.setNomComplet("Samira Idrissi");
        comptable2.setRole(Utilisateur.Role.COMPTABLE);
        comptable2.setSociete(null);
        utilisateurRepository.save(comptable2);
        
        // Create Documents for Societe 1
        Document doc1 = new Document();
        doc1.setNumeroPiece("FAC-2024-001");
        doc1.setType(Document.TypeDocument.FACTURE_ACHAT);
        doc1.setCategorieComptable("Fournitures de bureau");
        doc1.setDatePiece(LocalDate.of(2024, 1, 15));
        doc1.setMontant(new BigDecimal("5000.00"));
        doc1.setFournisseur("Office Plus");
        doc1.setCheminFichier("uploads/doc1.pdf");
        doc1.setNomFichierOriginal("facture_office_plus.pdf");
        doc1.setStatut(Document.StatutDocument.EN_ATTENTE);
        doc1.setSociete(societe1);
        documentRepository.save(doc1);
        
        Document doc2 = new Document();
        doc2.setNumeroPiece("FAC-2024-002");
        doc2.setType(Document.TypeDocument.FACTURE_VENTE);
        doc2.setCategorieComptable("Services télécommunication");
        doc2.setDatePiece(LocalDate.of(2024, 2, 10));
        doc2.setMontant(new BigDecimal("25000.00"));
        doc2.setFournisseur("Client ABC");
        doc2.setCheminFichier("uploads/doc2.pdf");
        doc2.setNomFichierOriginal("facture_vente_abc.pdf");
        doc2.setStatut(Document.StatutDocument.VALIDE);
        doc2.setSociete(societe1);
        documentRepository.save(doc2);
        
        // Create Documents for Societe 2
        Document doc3 = new Document();
        doc3.setNumeroPiece("FAC-2024-003");
        doc3.setType(Document.TypeDocument.FACTURE_ACHAT);
        doc3.setCategorieComptable("Matières premières");
        doc3.setDatePiece(LocalDate.of(2024, 1, 20));
        doc3.setMontant(new BigDecimal("150000.00"));
        doc3.setFournisseur("Sucre Import SA");
        doc3.setCheminFichier("uploads/doc3.pdf");
        doc3.setNomFichierOriginal("facture_sucre.pdf");
        doc3.setStatut(Document.StatutDocument.EN_ATTENTE);
        doc3.setSociete(societe2);
        documentRepository.save(doc3);
        
        Document doc4 = new Document();
        doc4.setNumeroPiece("TICKET-2024-001");
        doc4.setType(Document.TypeDocument.TICKET_CAISSE);
        doc4.setCategorieComptable("Frais de déplacement");
        doc4.setDatePiece(LocalDate.of(2024, 3, 5));
        doc4.setMontant(new BigDecimal("350.00"));
        doc4.setFournisseur("Station Shell");
        doc4.setCheminFichier("uploads/doc4.pdf");
        doc4.setNomFichierOriginal("ticket_essence.pdf");
        doc4.setStatut(Document.StatutDocument.REJETE);
        doc4.setCommentaireComptable("Document illisible, merci de fournir une copie claire");
        doc4.setSociete(societe2);
        documentRepository.save(doc4);
        
        Document doc5 = new Document();
        doc5.setNumeroPiece("RB-2024-001");
        doc5.setType(Document.TypeDocument.RELEVE_BANCAIRE);
        doc5.setCategorieComptable("Relevés bancaires");
        doc5.setDatePiece(LocalDate.of(2024, 3, 31));
        doc5.setMontant(new BigDecimal("0.00"));
        doc5.setFournisseur("Attijariwafa Bank");
        doc5.setCheminFichier("uploads/doc5.pdf");
        doc5.setNomFichierOriginal("releve_mars_2024.pdf");
        doc5.setStatut(Document.StatutDocument.EN_ATTENTE);
        doc5.setSociete(societe1);
        documentRepository.save(doc5);
        
        log.info("Sample data loaded successfully!");
        log.info("Created {} societes", societeRepository.count());
        log.info("Created {} utilisateurs", utilisateurRepository.count());
        log.info("Created {} documents", documentRepository.count());
    }
}
