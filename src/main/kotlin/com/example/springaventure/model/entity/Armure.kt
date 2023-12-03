package com.example.springaventure.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany


/**
 * Entité représentant une armure dans le système.
 *
 * @param id Identifiant unique de l'armure.
 * @param nom Nom de l'armure.
 * @param description Description de l'armure.
 * @param cheminImage Chemin vers l'image de l'armure.
 * @param typeArmure Type d'armure à laquelle elle appartient (relation ManyToOne).
 * @param qualite Qualité de l'armure (relation ManyToOne).
 */
@Entity
class Armure(
    id: Long?,
    nom: String,
    description: String,
    cheminImage: String,
    @ManyToOne
    @JoinColumn(name = "type_armure_id")
    var typeArmure: TypeArmure? = null,
    @ManyToOne
    @JoinColumn(name = "qualite_id")
    var qualite: Qualite? = null
) :
    Item(id, nom, description, cheminImage) {

    @OneToMany(mappedBy = "armureEquipee")
    var personnages: MutableList<Personnage> = mutableListOf()

    /**
     * Équipe l'armure sur un personnage, permettant au personnage d'augmenter sa défense.
     *
     * @param cible Le personnage sur lequel l'armure est équipée.
     * @return Message décrivant l'action effectuée.
     */
    override fun utiliser(cible: Personnage): String {
        return cible.equipe(this)
    }

    /**
     * Retourne une chaîne de caractères représentant l'armure, y compris son type, sa qualité et ses caractéristiques.
     *
     * @return Une chaîne de caractères représentant l'armure.
     */
    override fun toString(): String {
        return "${qualite?.couleur} ${qualite?.nom} $nom ${qualite?.bonusQualite} (type=${typeArmure?.nom})"
    }
}
