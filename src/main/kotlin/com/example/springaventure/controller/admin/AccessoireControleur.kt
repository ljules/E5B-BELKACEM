package com.example.springaventure.controller.admin

import com.example.springaventure.model.dao.AccessoireDao
import com.example.springaventure.model.dao.QualiteDao
import com.example.springaventure.model.dao.TypeAccessoireDao
import com.example.springaventure.model.entity.Accessoire
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
/**
 * Contrôleur responsable de la gestion des accessoires dans la partie administrative de l'application.
 */
@Controller
class AccessoireControleur(
    /** DAO pour l'accès aux données des accessoires. */
    val accessoireDao: AccessoireDao,

    /** DAO pour l'accès aux données des types d'accessoires. */
    val typeAccessoireDao: TypeAccessoireDao,

    /** DAO pour l'accès aux données des qualités d'accessoires. */
    val qualiteDao: QualiteDao
) {

    /**
     * Affiche la liste des accessoires dans la page d'index.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/accessoire")
    fun index(model: Model): String {
        val accessoires = this.accessoireDao.findAll()
        model.addAttribute("accessoires", accessoires)
        return "admin/accessoire/index"
    }

    /**
     * Affiche les détails d'un accessoire en fonction de son ID.
     *
     * @param id ID de l'accessoire à afficher.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/accessoire/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        val accessoire = this.accessoireDao.findById(id).orElseThrow()
        model.addAttribute("accessoire", accessoire)
        return "admin/accessoire/show"
    }

    /**
     * Affiche le formulaire de création d'un nouvel accessoire.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/accessoire/create")
    fun create(model: Model): String {
        val nouvelleAccessoire = Accessoire(null, "", "", "")
        val types = typeAccessoireDao.findAll()
        val qualites = qualiteDao.findAll()
        model.addAttribute("types", types)
        model.addAttribute("qualites", qualites)
        model.addAttribute("nouvelleAccessoire", nouvelleAccessoire)
        return "admin/accessoire/create"
    }

    /**
     * Traite la soumission du formulaire de création d'accessoire.
     *
     * @param nouvelleAccessoire Nouvel accessoire à enregistrer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des accessoires administratifs.
     */
    @PostMapping("/admin/accessoire")
    fun store(@ModelAttribute nouvelleAccessoire: Accessoire, redirectAttributes: RedirectAttributes): String {
        val savedAccessoire = this.accessoireDao.save(nouvelleAccessoire)
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedAccessoire.nom} réussi")
        return "redirect:/admin/accessoire"
    }

    /**
     * Affiche le formulaire d'édition d'un accessoire en fonction de son ID.
     *
     * @param id ID de l'accessoire à éditer.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/accessoire/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {
        val accessoire = this.accessoireDao.findById(id).orElseThrow()
        val types = typeAccessoireDao.findAll()
        val qualites = qualiteDao.findAll()
        model.addAttribute("types", types)
        model.addAttribute("qualites", qualites)
        model.addAttribute("accessoire", accessoire)
        return "admin/accessoire/edit"
    }

    /**
     * Traite la soumission du formulaire d'édition d'accessoire.
     *
     * @param accessoire Accessoire modifié à enregistrer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des accessoires administratifs.
     */
    @PostMapping("/admin/accessoire/update")
    fun update(@ModelAttribute accessoire: Accessoire, redirectAttributes: RedirectAttributes): String {
        val accessoireModifier = this.accessoireDao.findById(accessoire.id ?: 0).orElseThrow()
        accessoireModifier.nom = accessoire.nom
        accessoireModifier.qualite = accessoire.qualite
        accessoireModifier.cheminImage = accessoire.cheminImage
        accessoireModifier.description = accessoire.description
        accessoireModifier.typeAccessoire = accessoire.typeAccessoire
        val savedAccessoire = this.accessoireDao.save(accessoireModifier)
        redirectAttributes.addFlashAttribute(
            "msgSuccess",
            "Modification de ${savedAccessoire.nom} réussie"
        )
        return "redirect:/admin/accessoire"
    }

    /**
     * Traite la demande de suppression d'un accessoire.
     *
     * @param id ID de l'accessoire à supprimer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des accessoires administratifs.
     */
    @PostMapping("/admin/accessoire/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {
        val accessoire = this.accessoireDao.findById(id).orElseThrow()
        this.accessoireDao.delete(accessoire)
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${accessoire.nom} réussie")
        return "redirect:/admin/accessoire"
    }
}
