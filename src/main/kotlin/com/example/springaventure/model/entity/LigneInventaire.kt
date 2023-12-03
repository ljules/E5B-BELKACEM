package com.example.springaventure.model.entity

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId

/**
 * Classe représentant une ligne d'inventaire associant un item à un personnage.
 *
 * @property ligneInventaireId Identifiant composite de la ligne d'inventaire.
 * @property item L'item associé à la ligne d'inventaire.
 * @property personnage Le personnage associé à la ligne d'inventaire.
 * @property quantite La quantité de l'item dans la ligne d'inventaire.
 */
@Entity
class LigneInventaire(
    @EmbeddedId
    var ligneInventaireId: LigneInventaireId? = null,

    @MapsId("itemId")
    @ManyToOne
    @JoinColumn(name = "item_id")
    var item: Item? = null,

    @MapsId("personnageId")
    @ManyToOne
    @JoinColumn(name = "personnage_id")
    var personnage: Personnage? = null,

    var quantite: Int
) {
    /**
     * Retourne une représentation textuelle de la ligne d'inventaire.
     *
     * @return Une chaîne de caractères contenant des informations sur la ligne d'inventaire.
     */
    override fun toString(): String {
        return "LigneInventaire(id=$ligneInventaireId, item=${item?.nom}, personnage=${personnage?.nom}, quantite=$quantite)"
    }
}
