package com.example.springaventure.model.entity

import jakarta.persistence.*
/**
 * Classe représentant le type d'accessoire, décrivant la catégorie à laquelle appartient un accessoire.
 *
 * @property id Identifiant unique du type d'accessoire.
 * @property nom Nom du type d'accessoire.
 * @property typeBonus Type de bonus associé au type d'accessoire.
 * @property accessoires Liste des accessoires associés à ce type d'accessoire.
 */
@Entity
class TypeAccessoire(
    // Identifiant unique du type d'accessoire
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    // Nom du type d'accessoire
    var nom: String,

    // Type de bonus associé au type d'accessoire
    var typeBonus: String,
    // Liste des accessoires associés à ce type d'accessoire
    @OneToMany(mappedBy = "typeAccessoire", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var accessoires: MutableList<Accessoire> = mutableListOf()
) {

}
