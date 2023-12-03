package com.example.springaventure.model.entity

import jakarta.persistence.*
/**
 * Entité représentant une arme dans le système.
 *
 * @param id Identifiant unique de l'arme.
 * @param nom Nom de l'arme.
 * @param description Description de l'arme.
 * @param cheminImage Chemin vers l'image de l'arme.
 * @param qualite Qualité de l'arme (relation ManyToOne).
 * @param typeArme Type d'arme auquel elle appartient (relation ManyToOne).
 * @param personnages Liste des personnages équipant cette arme (relation OneToMany).
 */
@Entity
@DiscriminatorValue("arme")
class Arme constructor(
    id: Long?,
    nom: String,
    description: String,
    cheminImage: String?,

    @ManyToOne
    @JoinColumn(name = "qualite_id")
    var qualite: Qualite? = null,

    @ManyToOne
    @JoinColumn(name = "type_arme_id")
    var typeArme: TypeArme? = null,

    @OneToMany(mappedBy = "armeEquipee")
    var personnages: MutableList<Personnage> = mutableListOf(),
    // Faire l'association avec TypeArme
) : Item(id, nom, description, cheminImage) {

    /**
     * Calcule les dégâts infligés par l'arme en fonction de son type et de sa qualité.
     * Les dégâts peuvent inclure des coups critiques et des bonus de qualité.
     *
     * @return Les dégâts infligés par l'arme.
     */
    fun calculerDegats(): Int {
        // Exemple : 1d6 +2 ( cad un nombre entre 1 et 6 plus le modificateur 2)
        //      On tire 1 dè de 6 (min: 1 max:6 )
        //      Si on tire 6 alors on multiplie par le multiplicateur de critique du type de l'arme ( par exemple 3)
        //      Sinon on garde le nombre du tirage tel quelle
        //      On ajoute le bonus de qualite au dégat ici 2

        // Instanciation d'un tirage de dés
        val deDegat = TirageDes(this.typeArme!!.nbrDes, typeArme!!.maxDes)
        // on lance les dés
        var resultatLancer = deDegat.lance()
        val deCritique = TirageDes(1, 20).lance()
        if (deCritique >= typeArme!!.limiteCritique) {
            // Coup critique (si le nombre tiré correspond au maximum)

            resultatLancer = resultatLancer * typeArme!!.mutiplicateurCritique
        }
        resultatLancer += this.qualite!!.bonusQualite
        return resultatLancer
    }

    /**
     * Équipe l'arme sur un personnage, permettant au personnage de l'utiliser pour attaquer.
     *
     * @param cible Le personnage sur lequel l'arme est équipée.
     */
    override fun utiliser(cible: Personnage):String {
        return cible.equipe(this)
    }

    /**
     * Retourne une chaîne de caractères représentant l'arme, y compris son type, sa qualité et ses caractéristiques.
     *
     * @return Une chaîne de caractères représentant l'arme.
     */
    override fun toString(): String {
        return "${qualite?.couleur} ${typeArme?.nom} ${qualite?.nom}  Dégâts :${typeArme?.nbrDes}d${typeArme?.maxDes} +${qualite?.bonusQualite}"

    }
}