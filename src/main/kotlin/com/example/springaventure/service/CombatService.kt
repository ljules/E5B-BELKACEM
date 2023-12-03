package com.example.springaventure.service

import com.example.springaventure.model.dao.CombatDao
import com.example.springaventure.model.dao.PersonnageDao
import com.example.springaventure.model.entity.Combat
import com.example.springaventure.model.entity.Item
import com.example.springaventure.model.entity.Personnage
import org.springframework.stereotype.Service


@Service
class CombatService (var personnageDao: PersonnageDao,var combatDao: CombatDao) {


    /**
     * Cette méthode gère le tour de combat entre le héros et le monstre.
     *
     * @param unCombat Le combat en cours.
     * @param cible La cible de l'action du héros (peut être null selon l'action).
     * @param action L'action à effectuer (attaque, boirePotion, attendre, utiliserItem, etc.).
     * @param item L'item à utiliser dans l'action (peut être null selon l'action).
     * @return Une liste de messages décrivant les actions effectuées pendant le tour.
     */
    fun combattre(unCombat: Combat, cible: Personnage?, action: String, item: Item?): List<String> {
        // Récupération du héros et du monstre du combat
        val hero = unCombat.campagne.hero
        val monstre = unCombat.monstre

        // Initialisation des messages pour le héros et le monstre
        var msgHero: String
        var msgMonstre: String

        // Initialisation de la liste de résultats
        var resultat = mutableListOf<String>()

        // Incrémentation du compteur de tours
        unCombat.nbTour++
        resultat.add("Tour ${unCombat.nbTour} <br>")

        // Vérification de la vitesse pour déterminer l'ordre d'action entre le héros et le monstre
        if (hero!!.vitesse >= monstre.vitesse) {
            // Le héros agit en premier
            msgHero = "<p class='text-primary'> ${unCombat.faireActionHero(cible, action, item)}<p>"
            resultat.add(msgHero)

            // Si le combat n'est pas terminé, le monstre agit ensuite
            if (verificationFinCombat(hero, monstre) == null) {
                msgMonstre = "<p class='text-danger'> ${unCombat.tourDeMonstre()}<p>"
                resultat.add(msgMonstre)
            }
        } else {
            // Le monstre agit en premier
            msgMonstre = "<p class='text-danger'> ${unCombat.tourDeMonstre()}<p>"
            resultat.add(msgMonstre)

            // Si le combat n'est pas terminé, le héros agit ensuite
            if (verificationFinCombat(hero, monstre) == null) {
                msgHero = "<p class='text-primary'> ${unCombat.faireActionHero(cible, action, item)}<p>"
                resultat.add(msgHero)
            }
        }

        // Sauvegarde des modifications dans la base de données
        this.personnageDao.save(hero)
        this.personnageDao.save(monstre)
        this.combatDao.save(unCombat)

        // Retourne la liste des messages résultant du tour de combat
        return resultat
    }


    /**
     * Cette méthode vérifie si le combat est terminé en fonction des points de vie du héros et du monstre.
     *
     * @param hero Le héros du combat.
     * @param monstre Le monstre du combat.
     * @return Le personnage gagnant s'il y en a un, sinon null (le combat n'est pas encore terminé).
     */
    fun verificationFinCombat(hero: Personnage, monstre: Personnage): Personnage? {
        // Vérifier si le héros ou le monstre n'a plus de points de vie
        return when {
            hero.pointDeVie <= 0 -> monstre // Le monstre gagne si le héros est vaincu
            monstre.pointDeVie <= 0 -> hero // Le héros gagne si le monstre est vaincu
            else -> null // Le combat n'est pas encore terminé
        }
    }

}