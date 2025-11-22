package org.example.justificatives.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
@EqualsAndHashCode(callSuper = false)
public class Document {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(nullable = false)
    private String numeroPiece;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeDocument type;
    
    @NotBlank
    @Column(nullable = false)
    private String categorieComptable;
    
    @NotNull
    @Column(nullable = false)
    private LocalDate datePiece;
    
    @NotNull
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal montant;
    
    @NotBlank
    @Column(nullable = false)
    private String fournisseur;
    
    @NotBlank
    @Column(nullable = false)
    private String cheminFichier;
    
    @NotBlank
    @Column(nullable = false)
    private String nomFichierOriginal;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDocument statut = StatutDocument.EN_ATTENTE;
    
    private LocalDateTime dateValidation;
    
    @Column(length = 1000)
    private String commentaireComptable;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "societe_id", nullable = false)
    private Societe societe;
    
    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();
    
    private LocalDateTime dateModification;
    
    @PreUpdate
    public void preUpdate() {
        dateModification = LocalDateTime.now();
    }
    
    public enum TypeDocument {
        FACTURE_ACHAT("Facture d'achat"),
        FACTURE_VENTE("Facture de vente"),
        TICKET_CAISSE("Ticket de caisse"),
        RELEVE_BANCAIRE("Relevé bancaire");
        
        private final String libelle;
        
        TypeDocument(String libelle) {
            this.libelle = libelle;
        }
        
        public String getLibelle() {
            return libelle;
        }
    }
    
    public enum StatutDocument {
        EN_ATTENTE, VALIDE, REJETE
    }
}