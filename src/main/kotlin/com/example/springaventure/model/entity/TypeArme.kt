package com.example.springaventure.model.entity

import jakarta.persistence.*
/**
 * Classe représentant le type d'arme, décrivant les caractéristiques communes des armes d'un même type.
 *
 * @property id Identifiant unique du type d'arme.
 * @property nom Nom du type d'arme.
 * @property nbrDes Nombre de dés utilisés pour les dégâts de l'arme.
 * @property maxDes Nombre maximum sur chaque dé utilisé pour les dégâts.
 * @property mutiplicateurCritique Multiplicateur de dégâts en cas de coup critique.
 * @property limiteCritique Limite pour déclencher un coup critique (comparée à un jet de dé).
 * @property armes Liste des armes associées à ce type d'arme.
 */
@Entity
class TypeArme constructor(
    // Identifiant unique du type d'arme
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
     var id: Long? = null,

    // Nom du type d'arme
    var nom: String,

    // Nombre de dés utilisés pour les dégâts de l'arme
    var nbrDes: Int,

    // Nombre maximum sur chaque dé utilisé pour les dégâts
    var maxDes: Int,

    // Multiplicateur de dégâts en cas de coup critique
    var mutiplicateurCritique: Int,

    // Limite pour déclencher un coup critique (comparée à un jet de dé)
    var limiteCritique: Int,

    // Liste des armes associées à ce type d'arme
    @OneToMany(mappedBy = "typeArme", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var armes: MutableList<Arme> = mutableListOf()
) {
    // Vous pouvez ajouter d'autres méthodes ou propriétés au besoin
}
