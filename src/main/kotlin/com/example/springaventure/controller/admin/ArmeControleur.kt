package com.example.springaventure.controller.admin

import com.example.springaventure.model.dao.ArmeDao
import com.example.springaventure.model.dao.QualiteDao
import com.example.springaventure.model.dao.TypeArmeDao
import com.example.springaventure.model.entity.Arme
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * Contrôleur responsable de la gestion des armes dans la partie administrative de l'application.
 */
@Controller
class ArmeControleur(
    /** DAO pour l'accès aux données des armes. */
    val armeDao: ArmeDao,

    /** DAO pour l'accès aux données des qualités d'armes. */
    val qualiteDao: QualiteDao,

    /** DAO pour l'accès aux données des types d'armes. */
    val typeArmeDao: TypeArmeDao
) {

    /**
     * Affiche la liste des armes dans la page d'index.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/arme")
    fun index(model: Model): String {
        val armes = this.armeDao.findAll()
        model.addAttribute("armes", armes)
        return "admin/arme/index"
    }

    /**
     * Affiche les détails d'une arme en fonction de son ID.
     *
     * @param id ID de l'arme à afficher.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/arme/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        val arme = this.armeDao.findById(id).orElseThrow()
        model.addAttribute("arme", arme)
        return "admin/arme/show"
    }

    /**
     * Affiche le formulaire de création d'une nouvelle arme.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/arme/create")
    fun create(model: Model): String {
        val nouvelleArme = Arme(null, "", "", "")
        val qualites = qualiteDao.findAll()
        val typesArmes = typeArmeDao.findAll()
        model.addAttribute("qualites", qualites)
        model.addAttribute("typesArmes", typesArmes)
        model.addAttribute("arme", nouvelleArme)
        return "admin/arme/create"
    }

    /**
     * Traite la soumission du formulaire de création d'arme.
     *
     * @param nouvelleArme Nouvelle arme à enregistrer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des armes administratives.
     */
    @PostMapping("/admin/arme")
    fun store(@ModelAttribute nouvelleArme: Arme, redirectAttributes: RedirectAttributes): String {
        val savedArme = this.armeDao.save(nouvelleArme)
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedArme.nom} réussi")
        return "redirect:/admin/arme"
    }

    /**
     * Affiche le formulaire d'édition d'une arme en fonction de son ID.
     *
     * @param id ID de l'arme à éditer.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/arme/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {
        val arme = this.armeDao.findById(id).orElseThrow()
        val qualites = qualiteDao.findAll()
        val typesArmes = typeArmeDao.findAll()
        model.addAttribute("qualites", qualites)
        model.addAttribute("typesArmes", typesArmes)
        model.addAttribute("arme", arme)
        return "admin/arme/edit"
    }

    /**
     * Traite la soumission du formulaire d'édition d'arme.
     *
     * @param arme Arme modifiée à enregistrer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des armes administratives.
     */
    @PostMapping("/admin/arme/update")
    fun update(@ModelAttribute arme: Arme, redirectAttributes: RedirectAttributes): String {
        val armeModifier = this.armeDao.findById(arme.id ?: 0).orElseThrow()

        // Valorise les attributs de armeModifier, par exemple : armeModifier.nom = arme.nom
        armeModifier.nom = arme.nom
        armeModifier.description = arme.description
        armeModifier.typeArme = arme.typeArme
        armeModifier.qualite = arme.qualite
        armeModifier.cheminImage = arme.cheminImage

        val savedArme = this.armeDao.save(armeModifier)
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedArme.nom} réussie")
        return "redirect:/admin/arme"
    }

    /**
     * Traite la demande de suppression d'une arme.
     *
     * @param id ID de l'arme à supprimer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des armes administratives.
     */
    @PostMapping("/admin/arme/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {
        val arme = this.armeDao.findById(id).orElseThrow()
        this.armeDao.delete(arme)
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${arme.nom} réussie")
        return "redirect:/admin/arme"
    }
}
