package com.example.springaventure.controller.admin

import com.example.springaventure.model.dao.TypeAccessoireDao
import com.example.springaventure.model.entity.TypeAccessoire
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * Contrôleur responsable de la gestion des types d'accessoires dans la partie administrative de l'application.
 */
@Controller
class TypeAccessoireControleur(
    /** DAO pour l'accès aux données des types d'accessoires. */
    val typeAccessoireDao: TypeAccessoireDao
) {

    /**
     * Affiche la liste des types d'accessoires dans la page d'index.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/typeAccessoire")
    fun index(model: Model): String {
        // Récupère tous les types d'accessoires depuis la base de données
        val typeAccessoires = this.typeAccessoireDao.findAll()
        // Ajoute la liste des types d'accessoires au modèle pour transmission à la vue
        model.addAttribute("typeAccessoires", typeAccessoires)
        return "admin/typeaccessoire/index"
    }

    /**
     * Affiche les détails d'un type d'accessoire en fonction de son ID.
     *
     * @param id ID du type d'accessoire à afficher.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/typeAccessoire/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        val typeAccessoire = this.typeAccessoireDao.findById(id).orElseThrow()
        model.addAttribute("typeAccessoire", typeAccessoire)
        return "admin/typeaccessoire/show"
    }

    /**
     * Affiche le formulaire de création d'un nouveau type d'accessoire.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/typeAccessoire/create")
    fun create(model: Model): String {
        val nouveauTypeAccessoire = TypeAccessoire(null, "", "")
        model.addAttribute("nouveauTypeAccessoire", nouveauTypeAccessoire)
        return "admin/typeAccessoire/create"
    }

    /**
     * Traite la soumission du formulaire de création de type d'accessoire.
     *
     * @param nouveauTypeAccessoire Nouveau type d'accessoire à enregistrer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des types d'accessoires administratifs.
     */
    @PostMapping("/admin/typeAccessoire")
    fun store(
        @ModelAttribute nouveauTypeAccessoire: TypeAccessoire,
        redirectAttributes: RedirectAttributes
    ): String {
        val savedTypeAccessoire = this.typeAccessoireDao.save(nouveauTypeAccessoire)
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedTypeAccessoire.nom} réussi")
        return "redirect:/admin/typeAccessoire"
    }

    /**
     * Affiche le formulaire d'édition d'un type d'accessoire en fonction de son ID.
     *
     * @param id ID du type d'accessoire à éditer.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/typeAccessoire/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {
        val typeAccessoire = this.typeAccessoireDao.findById(id).orElseThrow()
        model.addAttribute("typeAccessoire", typeAccessoire)
        return "admin/typeAccessoire/edit"
    }

    /**
     * Traite la soumission du formulaire d'édition de type d'accessoire.
     *
     * @param typeAccessoire Type d'accessoire modifié à enregistrer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des types d'accessoires administratifs.
     */
    @PostMapping("/admin/typeAccessoire/update")
    fun update(
        @ModelAttribute typeAccessoire: TypeAccessoire,
        redirectAttributes: RedirectAttributes
    ): String {
        val typeAccessoireModifier =
            this.typeAccessoireDao.findById(typeAccessoire.id ?: 0).orElseThrow()

        // Valorise les attributs de typeAccessoireModifier, par exemple : typeAccessoireModifier.nom = typeAccessoire.nom
        typeAccessoireModifier.nom = typeAccessoire.nom
        typeAccessoireModifier.typeBonus = typeAccessoire.typeBonus

        val savedTypeAccessoire = this.typeAccessoireDao.save(typeAccessoireModifier)
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedTypeAccessoire.nom} réussie")
        return "redirect:/admin/typeAccessoire"
    }

    /**
     * Traite la demande de suppression d'un type d'accessoire.
     *
     * @param id ID du type d'accessoire à supprimer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des types d'accessoires administratifs.
     */
    @PostMapping("/admin/typeAccessoire/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {
        val typeAccessoire =
            this.typeAccessoireDao.findById(id).orElseThrow()
        this.typeAccessoireDao.delete(typeAccessoire)
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${typeAccessoire.nom} réussie")
        return "redirect:/admin/typeAccessoire"
    }
}
