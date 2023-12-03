package com.example.springaventure.model.entity

import jakarta.persistence.*
/**
 * Classe représentant la qualité d'un objet (arme, armure, accessoire) dans le jeu.
 *
 * @property id Identifiant unique de la qualité.
 * @property nom Nom de la qualité.
 * @property couleur Couleur associée à la qualité.
 * @property bonusQualite Bonus de qualité qui peut influencer les attributs des objets.
 * @property armes Liste des armes associées à cette qualité.
 * @property armures Liste des armures associées à cette qualité.
 * @property accessoires Liste des accessoires associés à cette qualité.
 */
@Entity
class Qualite constructor(
    // Identifiant unique de la qualité
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    // Nom de la qualité
    var nom: String,

    // Couleur associée à la qualité
    var couleur: String,

    // Bonus de qualité qui peut influencer les attributs des objets
    var bonusQualite: Int,

    // Association entre Qualite et Arme (une qualité peut avoir plusieurs armes)
    @OneToMany(mappedBy = "qualite", cascade = [CascadeType.REMOVE])
    var armes: MutableList<Arme> = mutableListOf(),

    // Association entre Qualite et Armure (une qualité peut avoir plusieurs armures)
    @OneToMany(mappedBy = "qualite", cascade = [CascadeType.REMOVE])
    var armures: MutableList<Armure> = mutableListOf(),

    // Association entre Qualite et Accessoire (une qualité peut avoir plusieurs accessoires)
    @OneToMany(mappedBy = "qualite", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var accessoires: MutableList<Accessoire> = mutableListOf()
)
