package com.example.springaventure.model.entity

import jakarta.persistence.*


/**
 * Classe représentant le type d'armure, décrivant les caractéristiques communes des armures d'un même type.
 *
 * @property id Identifiant unique du type d'armure.
 * @property nom Nom du type d'armure.
 * @property bonusType Bonus spécifique associé à ce type d'armure.
 * @property armures Liste des armures associées à ce type d'armure.
 */
@Entity
class TypeArmure(@Id
                 @GeneratedValue(strategy = GenerationType.IDENTITY)
                 @Column(name = "id", nullable = false)
                  var id: Long? = null,

    // Nom du type d'armure
                 var nom: String,

    // Bonus spécifique associé à ce type d'armure
                 var bonusType: Int) {

    // Liste des armures associées à ce type d'armure
    @OneToMany(mappedBy = "typeArmure", cascade = [CascadeType.REMOVE])
     var armures: MutableList<Armure> = mutableListOf()

    // Vous pouvez ajouter d'autres méthodes ou propriétés au besoin
}
