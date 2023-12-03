package com.example.springaventure.controller.joueur

import com.example.springaventure.model.dao.CombatDao
import com.example.springaventure.model.dao.ItemDao
import com.example.springaventure.model.dao.PersonnageDao
import com.example.springaventure.model.entity.Item
import com.example.springaventure.model.entity.Personnage
import com.example.springaventure.service.CombatService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
class CombatControleur(
    val combatService: CombatService,
    val combatDao: CombatDao,
    val personnageDao: PersonnageDao,
    val itemDao: ItemDao
) {

    @GetMapping("/joueur/combat/{idCombat}")
    fun show(@PathVariable idCombat: Long, model: Model): String {
        val combat = combatDao.findById(idCombat).orElseThrow()
        // Vérification de la fin du combat avant d'effectuer l'attaque
        if (combatService.verificationFinCombat(combat.campagne.hero!!, combat.monstre) != null) {
            if (combat!!.campagne.hero!!.pointDeVie > 0) {
                combat.estTerminer = true
                combatDao.save(combat)
                combat.campagne.hero!!.loot(combat.monstre)
            }

            // Redirection vers la page de la campagne si le combat est terminé
            return "redirect:/joueur/campagne/${combat.campagne.id}/jouer"
        }
        model.addAttribute("combat", combat)
        return "joueur/combat/index"
    }

    /**
     * Cette méthode gère l'attaque du héros contre une cible dans le combat.
     *
     * @param idCombat L'identifiant du combat en cours.
     * @param idCible L'identifiant de la cible du héros.
     * @param redirectAttributes Le modèle Spring utilisé pour passer des données à la vue Thymeleaf.
     * @return Le nom de la vue Thymeleaf à afficher après l'attaque.
     */
    @PostMapping("/joueur/combat/{idCombat}/attaque/{idCible}")
    fun attaquer(
        @PathVariable idCombat: Long,
        @PathVariable idCible: Long,
        redirectAttributes: RedirectAttributes
    ): String {
        // Récupération du combat avant le tour et de la cible à partir de leurs identifiants
        val combatAvantTour = combatDao.findById(idCombat).orElseThrow()
        val cible = personnageDao.findById(idCible).orElseThrow()

        // Exécution de l'attaque et récupération des messages de combat
        val lesMessages = combatService.combattre(combatAvantTour, cible, "attaquer", null)

        // Ajout des messages et du combat au modèle pour affichage dans la vue Thymeleaf
        redirectAttributes.addFlashAttribute("messages", lesMessages)


        return "redirect:/joueur/combat/${idCombat}"
    }

    /**
     * Cette méthode gère l'action de boire une potion du héros dans le combat.
     *
     * @param idCombat L'identifiant du combat en cours.
     * @param redirectAttributes Le modèle Spring utilisé pour passer des données à la vue Thymeleaf.
     * @return Le nom de la vue Thymeleaf à afficher après avoir bu la potion.
     */
    @PostMapping("/joueur/combat/{idCombat}/boirePotion")
    fun boirePotion(
        @PathVariable idCombat: Long,
        redirectAttributes: RedirectAttributes
    ): String {
        // Récupération du combat avant de boire la potion
        val combatAvantPotion = combatDao.findById(idCombat).orElseThrow()

        // Exécution de l'action "boirePotion" et récupération des messages de combat
        val lesMessages =
            combatService.combattre(combatAvantPotion, combatAvantPotion.campagne.hero!!, "boirePotion", null)

        // Ajout des messages et du combat au modèle pour affichage dans la vue Thymeleaf
        redirectAttributes.addFlashAttribute("messages", lesMessages)


        return "redirect:/joueur/combat/${idCombat}"
    }

    /**
     * Cette méthode gère l'action d'attendre du héros dans le combat.
     *
     * @param idCombat L'identifiant du combat en cours.
     * @param redirectAttributes Le modèle Spring utilisé pour passer des données à la vue Thymeleaf.
     * @return Le nom de la vue Thymeleaf à afficher après avoir attendu.
     */
    @PostMapping("/joueur/combat/{idCombat}/attendre")
    fun attendre(
        @PathVariable idCombat: Long,
        redirectAttributes: RedirectAttributes
    ): String {
        // Récupération du combat avant d'attendre
        val combatAvantAttendre = combatDao.findById(idCombat).orElseThrow()

        // Exécution de l'action "attendre" et récupération des messages de combat
        val lesMessages = combatService.combattre(combatAvantAttendre, null, "attendre", null)

        // Ajout des messages et du combat au modèle pour affichage dans la vue Thymeleaf
        redirectAttributes.addFlashAttribute("messages", lesMessages)


        return "redirect:/joueur/combat/${idCombat}"
    }

    /**
     * Cette méthode gère l'action d'utiliser un item du héros dans le combat.
     *
     * @param idCombat L'identifiant du combat en cours.
     * @param idCible L'identifiant de la cible du héros.
     * @param idItem L'identifiant de l'item à utiliser.
     * @param model Le modèle Spring utilisé pour passer des données à la vue Thymeleaf.
     * @return Le nom de la vue Thymeleaf à afficher après avoir utilisé l'item.
     */
    @GetMapping("/joueur/combat/{idCombat}/utiliser")
    fun utiliserItem(
        @PathVariable idCombat: Long,
        @RequestParam idCible: Long,
        @RequestParam idItem: Long,
        model: Model
    ): String {
        // Récupération du combat avant d'utiliser l'item
        val combatAvantItem = combatDao.findById(idCombat).orElseThrow()

        // Vérification de la fin du combat avant d'utiliser l'item
        if (combatService.verificationFinCombat(combatAvantItem.campagne.hero!!, combatAvantItem.monstre) != null) {
            // Redirection vers la page de la campagne si le combat est terminé
            return "redirect:/joueur/campagne/${combatAvantItem.campagne.id}/play"
        }
        val cible: Personnage
        if (idCible != null) {
            // Récupération de la cible à partir de son identifiant
            cible = personnageDao.findById(idCible).orElseThrow()
        } else {
            cible = combatAvantItem.campagne.hero!!
        }
        // Récupération de l'item à partir de son identifiant
        val item: Item = itemDao.findById(idItem).orElseThrow()

        // Exécution de l'action "utiliserItem" et récupération des messages de combat
        val lesMessages = combatService.combattre(combatAvantItem, cible, "utiliserItem", item)

        // Récupération du combat après avoir utilisé l'item
        val combatApresItem = combatDao.findById(idCombat).orElseThrow()

        // Ajout des messages et du combat au modèle pour affichage dans la vue Thymeleaf
        model.addAttribute("messages", lesMessages)
        model.addAttribute("combat", combatApresItem)

        // Retourne le nom de la vue Thymeleaf à afficher
        return "joueur/combat/index"
    }
}
