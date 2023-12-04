package com.example.springaventure.controller.admin

import com.example.springaventure.model.dao.PotionDao
import com.example.springaventure.model.entity.Potion
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * Contrôleur responsable de la gestion des potions dans la partie administrative de l'application.
 */
@Controller
class PotionControleur(
    /** DAO pour l'accès aux données des potions. */
    val potionDao: PotionDao
) {

    /**
     * Affiche la liste des potions dans la page d'index.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/potion")
    fun index(model: Model): String {
        // Récupère toutes les potions depuis la base de données
        val potions = this.potionDao.findAll()
        // Ajoute la liste des potions au modèle pour transmission à la vue
        model.addAttribute("potions", potions)
        // Retourne le nom de la vue à afficher
        return "admin/potion/index"
    }
    /**
     * Affiche les détails d'une potion en fonction de son ID.
     *
     * @param id ID de l'potion à afficher.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/potion/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        val potion = this.potionDao.findById(id).orElseThrow()
        model.addAttribute("potion", potion)
        return "admin/potion/show"
    }

    /**
     * Affiche le formulaire de création d'une nouvelle potion.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/potion/create")
    fun create(model: Model): String {
        val nouvellePotion = Potion(0,"","","",0)
        model.addAttribute("nouvellePotion", nouvellePotion)
        return "admin/potion/create"
    }

    /**
     * Traite la soumission du formulaire de création d'potion.
     *
     * @param nouvellePotion Nouvelle potion à enregistrer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des potions administratives.
     */
    @PostMapping("/admin/potion")
    fun store(@ModelAttribute nouvellePotion: Potion, redirectAttributes: RedirectAttributes): String {
        val savedPotion = this.potionDao.save(nouvellePotion)
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedPotion.nom} réussi")
        return "redirect:/admin/potion"
    }

    /**
     * Affiche le formulaire d'édition d'une potion en fonction de son ID.
     *
     * @param id ID de l'potion à éditer.
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/potion/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {
        val potion = this.potionDao.findById(id).orElseThrow()
        model.addAttribute("potion", potion)
        return "admin/potion/edit"
    }

    /**
     * Traite la soumission du formulaire d'édition d'potion.
     *
     * @param potion Potion modifiée à enregistrer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des potions administratives.
     */
    @PostMapping("/admin/potion/update")
    fun update(@ModelAttribute potion: Potion, redirectAttributes: RedirectAttributes): String {
        val potionModifier = this.potionDao.findById(potion.id ?: 0).orElseThrow()

        // Valorise les attributs de potionModifier, par exemple : potionModifier.nom = potion.nom
        potionModifier.nom = potion.nom

        potionModifier.cheminImage = potion.cheminImage
        potionModifier.description = potion.description


        val savedPotion = this.potionDao.save(potionModifier)
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedPotion.nom} réussie")
        return "redirect:/admin/potion"
    }

    /**
     * Traite la demande de suppression d'une potion.
     *
     * @param id ID de l'potion à supprimer.
     * @param redirectAttributes Attributs de redirection pour ajouter des messages flash.
     * @return Redirection vers la page d'index des potions administratives.
     */
    @PostMapping("/admin/potion/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {
        val potion = this.potionDao.findById(id).orElseThrow()
        this.potionDao.delete(potion)
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${potion.nom} réussie")
        return "redirect:/admin/potion"
    }
}
