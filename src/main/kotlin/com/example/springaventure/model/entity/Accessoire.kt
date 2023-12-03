package com.example.springaventure.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

/**
 * Entité représentant un accessoire dans le système.
 *
 * @param id Identifiant unique de l'accessoire.
 * @param nom Nom de l'accessoire.
 * @param description Description de l'accessoire.
 * @param cheminImage Chemin vers l'image de l'accessoire.
 * @param qualite Qualité de l'accessoire (relation ManyToOne).
 * @param typeAccessoire Type d'accessoire auquel il appartient (relation ManyToOne).
 * @param personnages Liste des personnages équipant cet accessoire (relation OneToMany).
 */
@Entity
class Accessoire(
    id: Long?,
    nom: String,
    description: String,
    cheminImage: String,
    @ManyToOne
    @JoinColumn(name = "qualite_id")
    var qualite: Qualite? = null,
    @ManyToOne
    @JoinColumn(name = "type_accessoire_id")
    var typeAccessoire: TypeAccessoire? = null,
    @OneToMany(mappedBy = "accessoire", orphanRemoval = true)
    var personnages: MutableList<Personnage> = mutableListOf()
) : Item(id, nom, description, cheminImage) {

    /**
     * Utilise l'accessoire sur un personnage cible.
     *
     * @param cible Personnage sur lequel l'accessoire est utilisé.
     * @return Message décrivant l'action effectuée.
     */
    override fun utiliser(cible: Personnage): String {
        return cible.equipe(this)
    }
}
