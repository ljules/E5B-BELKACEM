package com.example.springaventure.model.entity

import jakarta.persistence.*
/**
 * Classe représentant un personnage du jeu.
 *
 * @property id Identifiant unique du personnage.
 * @property nom Nom du personnage.
 * @property attaque Valeur d'attaque du personnage.
 * @property defense Valeur de défense du personnage.
 * @property endurance Valeur d'endurance du personnage.
 * @property vitesse Valeur de vitesse du personnage.
 * @property armeEquipee Arme équipée par le personnage.
 * @property armureEquipee Armure équipée par le personnage.
 * @property accessoire Accessoire équipé par le personnage.
 * @property utilisateur Utilisateur auquel le personnage est associé.
 * @property combats Liste des combats auxquels le personnage a participé en tant que monstre.
 * @property ligneInventaires Liste des objets dans l'inventaire du personnage.
 * @property campagnes Liste des campagnes auxquelles le personnage a participé en tant que héros.
 */
@Entity
open class Personnage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var nom: String,

    var attaque: Int,
    var defense: Int,
    var endurance: Int,
    var vitesse: Int,
    @ManyToOne
    @JoinColumn(name = "arme_equipee_id")
    var armeEquipee: Arme? = null,
    @ManyToOne
    @JoinColumn(name = "armure_equipee_id")
    var armureEquipee: Armure? = null,
    @OneToMany(mappedBy = "monstre", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    open var combats: MutableList<Combat> = mutableListOf(),
    @OneToMany(mappedBy = "personnage", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    open var ligneInventaires: MutableList<LigneInventaire> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    open var utilisateur: Utilisateur? = null,
    @OneToMany(mappedBy = "hero", orphanRemoval = true)
    open var campagnes: MutableList<Campagne> = mutableListOf(),
    @ManyToOne
    @JoinColumn(name = "accessoire_id")
    open var accessoire: Accessoire? = null
) {

    /**
     * Propriété calculée représentant le nombre maximal de points de vie que le personnage peut avoir.
     */
    val pointDeVieMax: Int
        get() = 50 + (10 * (this.endurance))
    /**
     * Points de vie actuels du personnage. La valeur est limitée par le nombre maximal de points de vie.
     */
    var pointDeVie: Int = this.pointDeVieMax
        set(value) {
            field = minOf(value, this.pointDeVieMax)
        }



    fun calculeDefense(): Int {
        var resultat = this.defense / 2
        val scoreArmure =
            (this.armureEquipee?.typeArmure?.bonusType ?: 0) + (this.armureEquipee?.qualite?.bonusQualite ?: 0)
        resultat += scoreArmure
        return resultat

    }

    /**
     * Méthode permettant au personnage d'attaquer un adversaire.
     *
     * @param adversaire La cible de l'attaque.
     * @return Un message décrivant le résultat de l'attaque.
     */
    open fun attaquer(adversaire: Personnage): String {
        // Vérifier si le personnage a une arme équipée
        var degats = this.attaque / 2
        if (armeEquipee != null) {
            // Calculer les dégâts en utilisant les attributs du personnage et la méthode calculerDegat de l'arme
            degats += this.armeEquipee!!.calculerDegats()
        }
        // Appliquer la défense de l'adversaire (au minimum au moins 1 de dégat)
        val degatsInfliges = maxOf(1, degats - adversaire.calculeDefense())


        // Appliquer les dégâts à l'adversaire
        adversaire.pointDeVie = adversaire.pointDeVie - degatsInfliges

        return ("$nom attaque ${adversaire.nom} avec ${armeEquipee?.nom ?: "une attaque de base"} et inflige $degatsInfliges points de dégâts.")
    }

    // Méthode pour équiper une arme de l'inventaire
    open fun equipe(arme: Arme): String {
        val ligneArme = this.ligneInventaires.find({ ligneInventaire -> ligneInventaire.item == arme })
        if (ligneArme != null) {
            armeEquipee = arme
            return "${this.nom} équipe ${this.armeEquipee!!.nom}."
        } else {
            return "$nom n'a pas cette arme dans son inventaire."
        }
    }

    fun equipe(armure: Armure): String {
        val ligneArmure = this.ligneInventaires.find({ ligneInventaire -> ligneInventaire.item == armure })
        if (ligneArmure != null) {
            this.armureEquipee = armure
            return "${this.nom} équipe ${this.armureEquipee!!.nom}."
        } else {
            return "$nom n'a pas cette armure dans son inventaire."
        }
    }

    fun equipe(accessoire: Accessoire): String {
        val ligneAccessoire = this.ligneInventaires.find({ ligneInventaire -> ligneInventaire.item == accessoire })
        if (ligneAccessoire != null) {
            this.accessoire = accessoire
            return "${this.nom} équipe ${this.accessoire!!.nom}."
        } else {
            return "$nom n'a pas cette armure dans son inventaire."
        }
    }

    /**
     * Méthode pour boire une potion de l'inventaire du personnage.
     *
     * @param consomer Spécifie si la potion doit être consommée (décrémentant la quantité) ou non.
     *                 Par défaut, la potion est consommée.
     * @return Un message décrivant l'action effectuée, tel que boire la potion ou l'absence de potion.
     */
    fun boirePotion(consomer: Boolean = true): String {
        // Message par défaut si le personnage n'a pas de potion dans son inventaire
        var msg = "$nom n'a pas de potion dans son inventaire."

        // Vérifier si le personnage a une potion dans son inventaire
        if (this.aUnePotion()) {
            // Filtrer les lignes d'inventaire pour obtenir celles qui contiennent des potions
            val lignePotions: List<LigneInventaire> =
                this.ligneInventaires.filter { ligneInventaire -> ligneInventaire.item is Potion }

            // Utiliser la première potion dans la liste et obtenir le message résultant de l'utilisation
            msg = lignePotions[0].item!!.utiliser(this)

            // Si consomer est false, augmenter la quantité de potions dans l'inventaire
            if (!consomer) {
                lignePotions[0].quantite += 1
            }
        }

        // Retourner le message décrivant l'action effectuée
        return msg
    }

    /**
     * Vérification si le personnage a une potion dans son inventaire.
     *
     * @return true si le personnage a une potion, false sinon.
     */
    fun aUnePotion(): Boolean {
        // Utiliser la méthode any pour vérifier si une ligne d'inventaire contient une Potion
        return this.ligneInventaires.any { ligneInventaire -> ligneInventaire.item is Potion }
    }

    /**
     * Loot l'inventaire de la cible en transférant les items et équipements dans l'inventaire du looteur.
     *
     * @param cible Le personnage dont l'inventaire sera looted.
     * @return Un message décrivant les items looted et les actions effectuées.
     */
    fun loot(cible: Personnage): String {
        // Déséquiper l'arme et l'armure de la cible
        cible.armeEquipee = null
        cible.armureEquipee = null
        // Variable pour stocker les messages générés pendant le loot
        var msg = ""
        // Parcourir chaque ligne d'inventaire de la cible
        for (uneLigne: LigneInventaire in cible.ligneInventaires) {
            // Ajouter les items et quantités lootés à l'inventaire du looteur
            this.ajouterLigneInventaire(uneLigne.item!!, uneLigne.quantite)

            // Construire un message décrivant l'action pour chaque item looté
            msg += "${this.nom} récupère ${uneLigne.quantite} ${uneLigne.item} <br>"
        }

        // Retourner le message global décrivant l'action de loot
        return msg
    }


    /**
     * Méthode permettant d'ajouter une ligne d'inventaire pour un item avec une quantité donnée.
     * Si une ligne d'inventaire pour cet item existe déjà, met à jour la quantité.
     * Si la quantité résultante est inférieure ou égale à zéro, la ligne d'inventaire est supprimée.
     *
     * @param unItem L'item pour lequel ajouter ou mettre à jour la ligne d'inventaire.
     * @param uneQuantite La quantité à ajouter à la ligne d'inventaire existante ou à la nouvelle ligne.
     */
    fun ajouterLigneInventaire(unItem: Item, uneQuantite: Int) {
        // Chercher une ligne d'inventaire existante pour l'item spécifié
        val ligneItem = this.ligneInventaires.find { ligneInventaire -> ligneInventaire.item == unItem }

        // Si aucune ligne d'inventaire n'est trouvée, en créer une nouvelle
        if (ligneItem == null) {
            // Créer un nouvel identifiant pour la ligne d'inventaire
            val ligneInventaireId = LigneInventaireId(this.id!!, unItem.id!!)

            // Ajouter une nouvelle ligne d'inventaire à la liste
            this.ligneInventaires.add(LigneInventaire(ligneInventaireId, unItem, this, uneQuantite))
        } else {
            // Si une ligne d'inventaire existante est trouvée, mettre à jour la quantité
            ligneItem.quantite += uneQuantite

            // Si la quantité résultante est inférieure ou égale à zéro, supprimer la ligne d'inventaire
            if (ligneItem.quantite <= 0) {
                this.ligneInventaires.remove(ligneItem)
            }
        }
    }


    override fun toString(): String {
        return "$nom (PV: $pointDeVie/$pointDeVieMax, Attaque: $attaque, Défense: $defense, Endurance: $endurance, Vitesse: $vitesse)"
    }


}