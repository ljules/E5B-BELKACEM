package com.example.springaventure.controller.admin

import com.example.springaventure.model.dao.ArmureDao
import com.example.springaventure.model.dao.QualiteDao
import com.example.springaventure.model.dao.TypeArmureDao
import com.example.springaventure.model.entity.Armure
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * Contrôleur responsable de la gestion des armures dans la partie administrative de l'application.
 */
@Controller
class ArmureControleur(
    /** DAO pour l'accès aux données des armures. */
    val armureDao: ArmureDao,

    /** DAO pour l'accès aux données des qualités d'armures. */
    val qualiteDao: QualiteDao,

    /** DAO pour l'accès aux données des types d'armures. */
    val typeArmureDao: TypeArmureDao
) {

    /**
     * Affiche la liste des armures dans la page d'index.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/armure")
    fun index(model: Model): String {
        val armures = this.armureDao.findAll()
        model.addAttribute("armures", armures)
        return "admin/armure/index"
    }

    /**
     * Affiche les détails d'une armure en fonction de son ID.
     *
     * @param id ID de l'armure à afficher.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/armure/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        val armure = this.armureDao.findById(id).orElseThrow()
        model.addAttribute("armure", armure)
        return "admin/armure/show"
    }

    /**
     * Affiche le formulaire de création d'une nouvelle armure.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/armure/create")
    fun create(model: Model): String {
        val nouvelleArmure = Armure(null, "", "", "")
        val qualites = qualiteDao.findAll()
        val typesArmure = typeArmureDao.findAll()
        model.addAttribute("qualites", qualites)
        model.addAttribute("typesArmure", typesArmure)
        model.addAttribute("nouvelleArmure", nouvelleArmure)
        return "admin/armure/create"
    }

    /**
     * Traite la soumission du formulaire de création d'armure.
     *
     * @param nouvelleArmure Nouvelle armure à enregistrer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des armures administratives.
     */
    @PostMapping("/admin/armure")
    fun store(@ModelAttribute nouvelleArmure: Armure, redirectAttributes: RedirectAttributes): String {
        val savedArmure = this.armureDao.save(nouvelleArmure)
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedArmure.nom} réussi")
        return "redirect:/admin/armure"
    }

    /**
     * Affiche le formulaire d'édition d'une armure en fonction de son ID.
     *
     * @param id ID de l'armure à éditer.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/armure/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {
        val armure = this.armureDao.findById(id).orElseThrow()
        val qualites = qualiteDao.findAll()
        val typesArmure = typeArmureDao.findAll()
        model.addAttribute("qualites", qualites)
        model.addAttribute("typesArmure", typesArmure)
        model.addAttribute("armure", armure)
        return "admin/armure/edit"
    }

    /**
     * Traite la soumission du formulaire d'édition d'armure.
     *
     * @param armure Armure modifiée à enregistrer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des armures administratives.
     */
    @PostMapping("/admin/armure/update")
    fun update(@ModelAttribute armure: Armure, redirectAttributes: RedirectAttributes): String {
        val armureModifier = this.armureDao.findById(armure.id ?: 0).orElseThrow()

        // Valorise les attributs de armureModifier, par exemple : armureModifier.nom = armure.nom
        armureModifier.nom = armure.nom
        armureModifier.qualite = armure.qualite
        armureModifier.cheminImage = armure.cheminImage
        armureModifier.description = armure.description
        armureModifier.typeArmure = armure.typeArmure

        val savedArmure = this.armureDao.save(armureModifier)
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedArmure.nom} réussie")
        return "redirect:/admin/armure"
    }

    /**
     * Traite la demande de suppression d'une armure.
     *
     * @param id ID de l'armure à supprimer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des armures administratives.
     */
    @PostMapping("/admin/armure/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {
        val armure = this.armureDao.findById(id).orElseThrow()
        this.armureDao.delete(armure)
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${armure.nom} réussie")
        return "redirect:/admin/armure"
    }
}
