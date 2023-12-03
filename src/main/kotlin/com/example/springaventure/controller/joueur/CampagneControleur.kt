package com.example.springaventure.controller.joueur

import com.example.springaventure.model.dao.CampagneDao
import com.example.springaventure.model.dao.PersonnageDao
import com.example.springaventure.model.entity.Combat
import org.springframework.data.domain.Pageable
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam


/**
 * Contrôleur responsable de la gestion des campagnes du joueur dans l'application.
 */
@Controller
class CampagneControleur(
    /** DAO pour l'accès aux données des campagnes. */
    val campagneDao: CampagneDao,
    /** DAO pour l'accès aux données des personnages. */
    val personnageDao: PersonnageDao
) {

    /**
     * Affiche la liste des campagnes du joueur avec pagination et possibilité de recherche.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @param search Terme de recherche (facultatif) pour filtrer les campagnes par nom.
     * @param pageable Informations sur la pagination (page actuelle, nombre d'éléments par page, etc.).
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/joueur/campagne")
    fun index(
        model: Model,
        @RequestParam(required = false) search: String?,
        pageable: Pageable
    ): String {
        // Récupérer l'objet Principal
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        // Récupérer le nom d'utilisateur à partir de l'objet Principal
        val email: String = authentication.getName()

        // Utiliser la méthode du DAO pour récupérer les campagnes par utilisateur avec pagination et recherche
        val nomRechercher = if (search.isNullOrBlank()) {
            ""
        } else {
            search
        }
        val lesCampagnes = campagneDao.findByNomContainingIgnoreCaseAndUtilisateur_Email(nomRechercher, email, pageable)

        model.addAttribute("campagnes", lesCampagnes)
        // Ajouter le terme de recherche au modèle pour pré-remplir le champ de recherche dans la vue
        model.addAttribute("search", search)
        return "joueur/campagne/index"
    }

    /**
     * Gère la demande de participation à une campagne en cours.
     *
     * @param id ID de la campagne à rejoindre.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Redirection vers la page de combat ou la liste des campagnes.
     */
    @GetMapping("/joueur/campagne/{id}/jouer")
    fun play(@PathVariable id: Long, model: Model): String {
        // Récupération de la campagne à partir de l'identifiant
        var campagne = this.campagneDao.findById(id).orElseThrow()

        // Recherche d'un combat non terminé dans la campagne
        val leCombat: Combat? = campagne.combats.find { combat -> combat.estTerminer == false }

        // Vérification si la campagne est terminée ou s'il n'y a plus de combat
        if (campagne.statut == "terminer" || leCombat == null) {
            // Logique de calcul du score (exemple : points basés sur le nombre de combats et le nombre de tours)
            val nombreDeCombats = campagne.combats.size
            val scoreParCombat = 50 // Points par combat (ajustez selon vos besoins)

            // Calcul du score en fonction du nombre de combats et du nombre de tours effectués
            val score = nombreDeCombats * scoreParCombat - campagne.combats.sumOf { it.nbTour }

            // Mise à jour des statistiques de la campagne
            campagne.statut = "terminer"
            campagne.dernierScore = score

            // Mise à jour du meilleur score si le score actuel est supérieur
            if (score > campagne.meilleurScore) {
                campagne.meilleurScore = score
            }

            // Enregistrement de la campagne mise à jour dans la base de données
            campagneDao.save(campagne)

            // Redirection vers la liste des campagnes
            return "redirect:/joueur/campagne"
        }

        // Réinitialisation du héros et des monstres pour un nouveau combat
        var hero = campagne.hero
        hero!!.pointDeVie = campagne.hero!!.pointDeVieMax
        hero = personnageDao.save(hero)

        for (combat in campagne.combats) {
            val monstre = combat.monstre
            monstre!!.pointDeVie = monstre.pointDeVieMax
            personnageDao.save(monstre)
        }

        // Mise à jour du statut de la campagne en cours
        campagne.statut = "En cours"
        campagne = campagneDao.save(campagne)

        // Ajout du combat en cours au modèle pour l'affichage dans la vue Thymeleaf
        model.addAttribute("combat", leCombat)

        // Redirection vers la page du combat
        return "joueur/combat/index"
    }
}
