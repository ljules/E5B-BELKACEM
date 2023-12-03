package com.example.springaventure.controller.admin


import com.example.springaventure.model.dao.TypeArmureDao
import com.example.springaventure.model.entity.TypeArmure
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * Contrôleur responsable de la gestion des types d'armures dans la partie administrative de l'application.
 */
@Controller
class TypeArmureControleur(
    /** DAO pour l'accès aux données des types d'armures. */
    val typeArmureDao: TypeArmureDao
) {

    /**
     * Affiche la liste des types d'armures dans la page d'index.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/typeArmure")
    fun index(model: Model): String {
        // Récupère tous les types d'armures depuis la base de données
        val typeArmures = this.typeArmureDao.findAll()
        // Ajoute la liste des types d'armures au modèle pour transmission à la vue
        model.addAttribute("typeArmures", typeArmures)
        return "admin/typeArmure/index"
    }

    /**
     * Affiche les détails d'un type d'armure en fonction de son ID.
     *
     * @param id ID du type d'armure à afficher.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/typeArmure/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        val typeArmure = this.typeArmureDao.findById(id).orElseThrow()
        model.addAttribute("typeArmure", typeArmure)
        return "admin/typeArmure/show"
    }

    /**
     * Affiche le formulaire de création d'un nouveau type d'armure.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/typeArmure/create")
    fun create(model: Model): String {
        val nouvelleTypeArmure = TypeArmure(null, "", 1)
        model.addAttribute("nouvelleTypeArmure", nouvelleTypeArmure)
        return "admin/typeArmure/create"
    }

    /**
     * Traite la soumission du formulaire de création de type d'armure.
     *
     * @param nouvelleTypeArmure Nouveau type d'armure à enregistrer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des types d'armures administratifs.
     */
    @PostMapping("/admin/typeArmure")
    fun store(
        @ModelAttribute nouvelleTypeArmure: TypeArmure,
        redirectAttributes: RedirectAttributes
    ): String {
        val savedTypeArmure = this.typeArmureDao.save(nouvelleTypeArmure)
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedTypeArmure.nom} réussi")
        return "redirect:/admin/typeArmure"
    }

    /**
     * Affiche le formulaire d'édition d'un type d'armure en fonction de son ID.
     *
     * @param id ID du type d'armure à éditer.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/typeArmure/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {
        val typeArmure = this.typeArmureDao.findById(id).orElseThrow()
        model.addAttribute("typeArmure", typeArmure)
        return "admin/typeArmure/edit"
    }

    /**
     * Traite la soumission du formulaire d'édition de type d'armure.
     *
     * @param typeArmure Type d'armure modifié à enregistrer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des types d'armures administratifs.
     */
    @PostMapping("/admin/typeArmure/update")
    fun update(
        @ModelAttribute typeArmure: TypeArmure,
        redirectAttributes: RedirectAttributes
    ): String {
        val typeArmureModifier =
            this.typeArmureDao.findById(typeArmure.id ?: 0).orElseThrow()

        // Valorise les attributs de typeArmureModifier, par exemple : typeArmureModifier.nom = typeArmure.nom
        typeArmureModifier.nom = typeArmure.nom
        typeArmureModifier.bonusType = typeArmure.bonusType

        val savedTypeArmure = this.typeArmureDao.save(typeArmureModifier)
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedTypeArmure.nom} réussie")
        return "redirect:/admin/typeArmure"
    }

    /**
     * Traite la demande de suppression d'un type d'armure.
     *
     * @param id ID du type d'armure à supprimer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des types d'armures administratifs.
     */
    @PostMapping("/admin/typeArmure/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {
        val typeArmure =
            this.typeArmureDao.findById(id).orElseThrow()
        this.typeArmureDao.delete(typeArmure)
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${typeArmure.nom} réussie")
        return "redirect:/admin/typeArmure"
    }
}
