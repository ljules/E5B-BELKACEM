package com.example.springaventure.model.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
/**
 * Entité représentant une bombe dans le système.
 *
 * @param id Identifiant unique de la bombe.
 * @param nom Nom de la bombe.
 * @param description Description de la bombe.
 * @param cheminImage Chemin vers l'image de la bombe.
 * @param nbrDes Nombre de dés utilisés pour calculer les dégâts.
 * @param maxDes Valeur maximale d'un dé pour calculer les dégâts.
 */
@Entity
@DiscriminatorValue("bombe")
class Bombe(
    id: Long,
    nom: String,
    description: String,
    cheminImage: String,
    var nbrDes: Int,
    var maxDes: Int,
) : Item(id, nom, description, cheminImage) {

    /**
     * Utilise la bombe sur un personnage, infligeant des dégâts en fonction du lancer de dés.
     *
     * @param cible Le personnage sur lequel la bombe est utilisée.
     * @return Message décrivant l'action effectuée.
     */
    override fun utiliser(cible: Personnage): String {
        val deDegat = TirageDes(nbrDes, maxDes)
        var degat = deDegat.lance()
        // Réduction des dégâts en fonction de la défense de la cible
        degat = maxOf(1, degat - cible.calculeDefense())
        cible.pointDeVie -= degat
        return ("La/Le ${this.nom} inflige $degat points de dégâts<br>")
    }
}
