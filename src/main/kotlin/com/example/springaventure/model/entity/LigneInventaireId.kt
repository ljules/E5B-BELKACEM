package com.example.springaventure.model.entity

import jakarta.persistence.Embeddable

/**
 * Classe représentant l'identifiant composite pour une ligne d'inventaire.
 *
 * @property personnageId L'identifiant du personnage associé à la ligne d'inventaire.
 * @property itemId L'identifiant de l'item associé à la ligne d'inventaire.
 */
@Embeddable
class LigneInventaireId(
    var personnageId: Long,
    var itemId: Long
) : java.io.Serializable {
    /**
     * Retourne une représentation textuelle de l'identifiant composite.
     *
     * @return Une chaîne de caractères contenant les valeurs des propriétés de l'identifiant composite.
     */
    override fun toString(): String {
        return "LigneInventaireId(personnageId=$personnageId, itemId=$itemId)"
    }
}
