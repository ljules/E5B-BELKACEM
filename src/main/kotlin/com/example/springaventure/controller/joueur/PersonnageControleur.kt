package com.example.springaventure.controller.joueur

import com.example.springaventure.model.dao.PersonnageDao
import com.example.springaventure.model.dao.UtilisateurDao
import com.example.springaventure.model.entity.Personnage
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * Contrôleur responsable de la gestion des personnages du joueur dans l'application.
 */
@Controller
class PersonnageControleur(
    /** DAO pour l'accès aux données des personnages. */
    val personnageDao: PersonnageDao,
    /** DAO pour l'accès aux données des utilisateurs. */
    val utilisateurDao: UtilisateurDao
) {

    /**
     * Affiche la liste des personnages du joueur.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/joueur/personnage")
    fun index(model: Model): String {
        // Récupérer l'objet Principal
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        // Récupérer le nom d'utilisateur à partir de l'objet Principal
        val email: String = authentication.getName()
        // Récupérer l'utilisateur
        val utilisateur = utilisateurDao.findByEmail(email)!!

        val personnages = this.personnageDao.findByUtilisateur_IdOrderByIdDesc(utilisateur.id!!)

        model.addAttribute("personnages", personnages)
        return "joueur/personnage/index"
    }

    /**
     * Affiche les détails d'un personnage spécifique.
     *
     * @param id ID du personnage à afficher.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/joueur/personnage/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        val personnage = this.personnageDao.findById(id).orElseThrow()
        model.addAttribute("personnage", personnage)
        return "joueur/personnage/show"
    }

    /**
     * Affiche le formulaire de création d'un nouveau personnage.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/joueur/personnage/create")
    fun create(model: Model): String {
        val nouvellePersonnage = Personnage(null, "", 1, 1, 1, 1)
        model.addAttribute("nouvellePersonnage", nouvellePersonnage)
        return "joueur/personnage/create"
    }

    /**
     * Gère la création d'un nouveau personnage.
     *
     * @param nouvellePersonnage Instance du nouveau personnage à créer.
     * @param redirectAttributes Attributs de redirection pour transmettre des messages à la vue.
     * @return Redirection vers la liste des personnages.
     */
    @PostMapping("/joueur/personnage")
    fun store(
        @ModelAttribute nouvellePersonnage: Personnage,
        redirectAttributes: RedirectAttributes
    ): String {
        // Récupérer l'objet Principal
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        // Récupérer le nom d'utilisateur à partir de l'objet Principal
        val email: String = authentication.getName()
        // Récupérer l'utilisateur
        val utilisateur = utilisateurDao.findByEmail(email)!!
        nouvellePersonnage.utilisateur = utilisateur
        val savedPersonnage = this.personnageDao.save(nouvellePersonnage)
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedPersonnage.nom} réussi")
        return "redirect:/joueur/personnage"
    }

    /**
     * Affiche le formulaire de modification d'un personnage existant.
     *
     * @param id ID du personnage à éditer.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/joueur/personnage/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {
        val personnage = this.personnageDao.findById(id).orElseThrow()
        model.addAttribute("personnage", personnage)
        return "joueur/personnage/edit"
    }

    /**
     * Gère la mise à jour d'un personnage existant.
     *
     * @param personnage Instance du personnage avec les modifications.
     * @param redirectAttributes Attributs de redirection pour transmettre des messages à la vue.
     * @return Redirection vers la liste des personnages.
     */
    @PostMapping("/joueur/personnage/update")
    fun update(
        @ModelAttribute personnage: Personnage,
        redirectAttributes: RedirectAttributes
    ): String {
        val personnageModifier = this.personnageDao.findById(personnage.id ?: 0).orElseThrow()
        // Récupérer l'objet Principal
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        // Récupérer le nom d'utilisateur à partir de l'objet Principal
        val email: String = authentication.getName()
        // Récupérer l'utilisateur
        val utilisateur = utilisateurDao.findByEmail(email)!!

        personnageModifier.nom = personnage.nom
        personnageModifier.attaque = personnage.attaque
        personnageModifier.defense = personnage.defense
        personnageModifier.endurance = personnage.endurance
        personnageModifier.vitesse = personnage.vitesse
        personnageModifier.utilisateur = utilisateur
        val savedPersonnage = this.personnageDao.save(personnageModifier)
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedPersonnage.nom} réussie")
        return "redirect:/joueur/personnage"
    }

    /**
     * Gère la suppression d'un personnage.
     *
     * @param id ID du personnage à supprimer.
     * @param redirectAttributes Attributs de redirection pour transmettre des messages à la vue.
     * @return Redirection vers la liste des personnages.
     */
    @PostMapping("/joueur/personnage/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {
        val personnage = this.personnageDao.findById(id).orElseThrow()
        this.personnageDao.delete(personnage)
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${personnage.nom} réussie")
        return "redirect:/joueur/personnage"
    }
}
