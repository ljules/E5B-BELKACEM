package com.example.springaventure.controller.admin

import com.example.springaventure.model.dao.TypeArmeDao
import com.example.springaventure.model.entity.TypeArme
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * Contrôleur responsable de la gestion des types d'armes dans la partie administrative de l'application.
 */
@Controller
class TypeArmeControleur(
    /** DAO pour l'accès aux données des types d'armes. */
    val typeArmeDao: TypeArmeDao
) {

    /**
     * Affiche la liste des types d'armes dans la page d'index.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/typeArme")
    fun index(model: Model): String {
        // Récupère tous les types d'armes depuis la base de données
        val typeArmes = this.typeArmeDao.findAll()
        // Ajoute la liste des types d'armes au modèle pour transmission à la vue
        model.addAttribute("typeArmes", typeArmes)
        return "admin/typearme/index"
    }

    /**
     * Affiche les détails d'un type d'arme en fonction de son ID.
     *
     * @param id ID du type d'arme à afficher.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/typeArme/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        val typeArme = this.typeArmeDao.findById(id).orElseThrow()
        model.addAttribute("typeArme", typeArme)
        return "admin/typearme/show"
    }

    /**
     * Affiche le formulaire de création d'un nouveau type d'arme.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/typeArme/create")
    fun create(model: Model): String {
        val nouvelleTypeArme = TypeArme(null, "", 1, 6, 2, 20)
        model.addAttribute("nouvelleTypeArme", nouvelleTypeArme)
        return "admin/typearme/create"
    }

    /**
     * Traite la soumission du formulaire de création de type d'arme.
     *
     * @param nouvelleTypeArme Nouveau type d'arme à enregistrer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des types d'armes administratifs.
     */
    @PostMapping("/admin/typeArme")
    fun store(
        @ModelAttribute nouvelleTypeArme: TypeArme,
        redirectAttributes: RedirectAttributes
    ): String {
        val savedTypeArme = this.typeArmeDao.save(nouvelleTypeArme)
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedTypeArme.nom} réussi")
        return "redirect:/admin/typeArme"
    }

    /**
     * Affiche le formulaire d'édition d'un type d'arme en fonction de son ID.
     *
     * @param id ID du type d'arme à éditer.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/typeArme/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {
        val typeArme = this.typeArmeDao.findById(id).orElseThrow()
        model.addAttribute("typeArme", typeArme)
        return "admin/typeArme/edit"
    }

    /**
     * Traite la soumission du formulaire d'édition de type d'arme.
     *
     * @param typeArme Type d'arme modifié à enregistrer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des types d'armes administratifs.
     */
    @PostMapping("/admin/typeArme/update")
    fun update(
        @ModelAttribute typeArme: TypeArme,
        redirectAttributes: RedirectAttributes
    ): String {
        val typeArmeModifier =
            this.typeArmeDao.findById(typeArme.id ?: 0).orElseThrow()

        // Valorise les attributs de typeArmeModifier, par exemple : typeArmeModifier.nom = typeArme.nom
        typeArmeModifier.nom = typeArme.nom
        typeArmeModifier.nbrDes = typeArme.nbrDes
        typeArmeModifier.maxDes = typeArme.maxDes
        typeArmeModifier.limiteCritique = typeArme.limiteCritique
        typeArmeModifier.mutiplicateurCritique = typeArme.mutiplicateurCritique

        val savedTypeArme = this.typeArmeDao.save(typeArmeModifier)
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedTypeArme.nom} réussie")
        return "redirect:/admin/typeArme"
    }

    /**
     * Traite la demande de suppression d'un type d'arme.
     *
     * @param id ID du type d'arme à supprimer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des types d'armes administratifs.
     */
    @PostMapping("/admin/typeArme/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {
        val typeArme =
            this.typeArmeDao.findById(id).orElseThrow()
        this.typeArmeDao.delete(typeArme)
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${typeArme.nom} réussie")
        return "redirect:/admin/typeArme"
    }
}
