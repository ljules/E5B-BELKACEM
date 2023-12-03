package com.example.springaventure.model.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
/**
 * Classe représentant une potion dans le jeu.
 *
 * @property id Identifiant unique de la potion.
 * @property nom Nom de la potion.
 * @property description Description de la potion.
 * @property cheminImage Chemin vers l'image de la potion.
 * @property soin Valeur de soin que la potion procure.
 */
@Entity
@DiscriminatorValue("potion")
class Potion(
    // L'identifiant unique de la potion
    id: Long? = null,

    // Le nom de la potion
    nom: String,

    // La description de la potion
    description: String,

    // Chemin vers l'image de la potion
    cheminImage: String?,

    // La propriété spécifique à la potion, le soin qu'elle procure
    val soin: Int
) : Item(id, nom, description, cheminImage) {

    /**
     * Utilise l'objet de type Potion de Soin sur un personnage cible, augmentant ses points de vie.
     *
     * @param cible Le personnage sur lequel appliquer la potion de soin.
     * @return Un message décrivant l'action effectuée, notamment le soin apporté.
     */
    override fun utiliser(cible: Personnage): String {
        // Stocker la valeur initiale des points de vie de la cible
        val pvInitiale = cible.pointDeVie

        // Ajouter le montant de soin aux points de vie de la cible
        cible.pointDeVie += soin

        // Ajouter une ligne d'inventaire pour enregistrer l'utilisation de la potion de soin
        cible.ajouterLigneInventaire(this, -1)

        // Construire et retourner un message décrivant l'action effectuée
        return "${this.nom} soigne ${cible.nom} pour ${cible.pointDeVie - pvInitiale} PV"
    }
}
