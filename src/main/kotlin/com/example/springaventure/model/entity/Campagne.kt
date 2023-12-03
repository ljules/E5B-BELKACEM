package com.example.springaventure.model.entity

import jakarta.persistence.*
import java.time.LocalDate
/**
 * Entité représentant une campagne dans le système.
 *
 * @property id Identifiant unique de la campagne (généré automatiquement).
 * @property nom Nom de la campagne.
 * @property dateMaj Date de la dernière mise à jour de la campagne.
 * @property dernierScore Dernier score enregistré pour la campagne.
 * @property meilleurScore Meilleur score enregistré pour la campagne.
 * @property statut Statut actuel de la campagne (en cours, terminée, etc.).
 * @property combats Liste des combats associés à la campagne.
 * @property utilisateur Utilisateur associé à la campagne.
 * @property hero Héros associé à la campagne.
 */
@Entity
class Campagne constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var nom: String,
    var dateMaj: LocalDate,
    var dernierScore: Int,
    var meilleurScore: Int,
    var statut: String,
    @OneToMany(mappedBy = "campagne", cascade = [CascadeType.REMOVE])
    var combats: MutableList<Combat> = mutableListOf(),
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    var utilisateur: Utilisateur? = null,
    @ManyToOne
    @JoinColumn(name = "hero_id")
    var hero: Personnage? = null
) {
}
