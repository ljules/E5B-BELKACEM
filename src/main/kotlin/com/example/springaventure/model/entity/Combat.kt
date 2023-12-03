package com.example.springaventure.model.entity

import jakarta.persistence.*
/**
 * Entité représentant un combat dans le système.
 *
 * @property id Identifiant unique du combat (généré automatiquement).
 * @property nbTour Nombre de tours joués dans le combat.
 * @property estTerminer Indique si le combat est terminé.
 * @property campagne Campagne associée au combat.
 * @property monstre Monstre associé au combat.
 */
@Entity
class Combat constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var nbTour:Int=0,
    var estTerminer:Boolean,
    @ManyToOne
    @JoinColumn(name = "campagne_id")
     var campagne: Campagne,
    @ManyToOne
    @JoinColumn(name = "monstre_id", nullable = false)
     var monstre: Personnage

) {
    /**
     * Méthode représentant le tour d'un monstre dans la campagne.
     * @return Un message décrivant les actions effectuées par le monstre pendant son tour.
     */
    fun tourDeMonstre(): String {

        // Initialiser le message avec des informations sur le tour du monstre
        var msg = ""
        // Générer un nombre aléatoire entre 0 et 100
        val nbAlea = (0..100).random()
        // Le monstre a une faible chance (par exemple, 10%) de boire une potion s'il est blessé
        if (monstre.pointDeVie < monstre.pointDeVieMax / 2 && nbAlea < 10 && monstre.aUnePotion()) {
            // Si la condition est satisfaite, le monstre boit une potion sans la consommer
            msg += monstre.boirePotion(false)
        } else {
            // Sinon, le monstre prend une décision en fonction du nombre aléatoire
            if (nbAlea < 60) {
                // Le monstre attaque le héros avec une probabilité de 60%
                msg += monstre.attaquer(this.campagne.hero!!)
            } else {
                // Le monstre choisit de passer son tour avec une probabilité de 40%
                msg += "${monstre.nom} choisit de passer."
            }
        }
        // Ajouter une balise HTML de saut de ligne à la fin du message
        msg += "<br>"
        // Retourner le message décrivant les actions du monstre pendant son tour
        return msg
    }

    /**
     * Cette méthode gère l'action du héros dans le combat.
     *
     * @param cible La cible de l'action (peut être null selon l'action).
     * @param action L'action à effectuer (attaque, boirePotion, attendre, utiliserItem, etc.).
     * @param item L'item à utiliser dans l'action (peut être null selon l'action).
     * @return Un message décrivant le résultat de l'action du héros.
     */
    fun faireActionHero(cible: Personnage?, action: String, item: Item?): String {
        // Récupération du héros de la campagne
        val hero = this.campagne.hero
        var msg: String

        try {
            // Utilisation d'une expression when pour gérer différents types d'actions
            msg = when (action) {
                "attaquer" -> hero!!.attaquer(cible!!)
                "boirePotion" -> hero!!.boirePotion()
                "attendre" -> " ${hero!!.nom} passe son tour"
                "utiliserItem" -> item!!.utiliser(cible!!)
                // Ajoutez d'autres cas pour chaque type d'action
                else -> {
                    "Action Impossible"
                }
            }
        } catch (error: Exception) {
            // Gestion des erreurs, affichage du message d'erreur et assignation d'un message générique
            println("Message = " + error.message)
            msg = "Une erreur s'est produite"
        }

        // Retourne le message résultant de l'action du héros
        return msg
    }


}