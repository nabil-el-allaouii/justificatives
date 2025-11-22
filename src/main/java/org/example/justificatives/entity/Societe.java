package org.example.justificatives.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "societes")
@Data
@EqualsAndHashCode(callSuper = false)
public class Societe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(nullable = false)
    private String raisonSociale;
    
    @NotBlank
    @Column(unique = true, nullable = false)
    private String ice;
    
    @NotBlank
    @Column(nullable = false)
    private String adresse;
    
    @NotBlank
    @Column(nullable = false)
    private String telephone;
    
    @Email
    @NotBlank
    @Column(nullable = false)
    private String emailContact;
    
    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();
    
    @OneToMany(mappedBy = "societe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Utilisateur> utilisateurs;
    
    @OneToMany(mappedBy = "societe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;
}