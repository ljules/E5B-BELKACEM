package com.example.springaventure.controller.admin

import com.example.springaventure.model.dao.BombeDao
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
/**
 * Contrôleur responsable de la gestion des bombes dans la partie administrative de l'application.
 */
@Controller
class BombeControleur(
    /** DAO pour l'accès aux données des bombes. */
    val bombeDao: BombeDao
) {

    /**
     * Affiche la liste des bombes dans la page d'index.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/bombe")
    fun index(model: Model): String {
        // Récupère toutes les bombes depuis la base de données
        val bombes = this.bombeDao.findAll()
        // Ajoute la liste des bombes au modèle pour transmission à la vue
        model.addAttribute("bombes", bombes)
        // Retourne le nom de la vue à afficher
        return "admin/bombe/index"
    }
}
