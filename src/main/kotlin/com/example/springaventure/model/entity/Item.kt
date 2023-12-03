package com.example.springaventure.model.entity

import jakarta.persistence.*
// Utilise l'héritage avec une seule table pour stocker les données de toutes les sous-classes dans la même table.
// La colonne "discriminateur" est utilisée pour différencier les types d'objets stockés dans la table.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminateur")
/**
 * Classe abstraite représentant un item du système.
 *
 * @property id Identifiant unique de l'item (généré automatiquement).
 * @property nom Nom de l'item.
 * @property description Description de l'item.
 * @property cheminImage Chemin vers l'image de l'item.
 * @property ligneInventaires Liste des lignes d'inventaire associées à cet item.
 */
// Déclare la classe comme une entité JPA
@Entity
// Déclare la classe comme abstraite, ce qui signifie qu'elle ne peut pas être instanciée directement.
open abstract class Item constructor(
    // Clé primaire auto-incrémentée
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    // Nom de l'item
    var nom: String,
    // Description de l'item
    var description: String,
    // Chemin vers l'image de l'item
    var cheminImage: String?,
    @OneToMany(mappedBy = "item", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    open var ligneInventaires: MutableList<LigneInventaire> = mutableListOf()
) {

    /**
     * Méthode abstraite permettant d'utiliser l'objet sur une cible (personnage).
     *
     * @param cible Le personnage sur lequel l'objet est utilisé.
     * @return Un message décrivant le résultat de l'utilisation de l'objet.
     */
    abstract fun utiliser(cible: Personnage): String

    /**
     * Retourne une représentation textuelle de l'objet.
     *
     * @return Une chaîne de caractères contenant le nom et la description de l'objet.
     */
    override fun toString(): String {
        return "${nom} (nom='$nom', description='$description')"
    }
}
