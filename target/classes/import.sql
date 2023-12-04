insert into qualite (bonus_qualite, couleur, nom) values (0,'grey','commun'),(1,'blue','rare'),(2,'pink','epique'),(3,'orange','légendaire');
-- insert into type_arme (limite_critique, max_des, mutiplicateur_critique, nbr_des, nom) values (18, 10, 2, 2, 'Épée'),(20, 8, 3, 1, 'Arc'),(15, 12, 1, 3, 'Hache');
insert into role (nom) values ('admin');
insert into role (nom) values ('joueur');
insert into utilisateur ( email, mdp) values ('admin@email.com','$2a$10$MJn5SDUCgsm3XEyvELGQI.lcCzCXtxhA8hunr9jX9yvDd6/FkjxYO');
insert into utilisateur_roles (roles_id, utilisateur_id) VALUES (1,1), (2,1);
-- Type Arme
insert into type_arme (limite_critique, max_des, mutiplicateur_critique, nbr_des, nom) values(20, 6, 2, 1, 'Épée courte'),(18, 8, 3, 2, 'Hache de bataille'),(15, 10, 4, 2, 'Arc long'),(25, 4, 5, 1, 'Dague'),(22, 12, 2, 1, 'Marteau de guerre'),(16, 6, 3, 2, 'Fleuret'),(28, 8, 4, 1, 'Lance'),(18, 10, 2, 2, 'Poignard'),(20, 6, 3, 1, 'Bouclier offensif'),(15, 8, 4, 2, 'Arc court');

-- Type Armure
insert into type_armure (bonus_type, nom) values(2, 'Cuir léger'),(4, 'Mailles'),(6, 'Plaques'),(3, 'Cotte de mailles'),(5, 'Armure de cuir renforcé'),(2, 'Tunique en soie'),(4, 'Brigandine'),(1, 'Armure de cuir souple'),(3, 'Habit de moine'),(2, 'Vêtements de voyage');
-- Type Accessoire
insert into type_accessoire (type_bonus, nom) values('attaque', 'Amulette de Puissance'),('defense', 'Bouclier de Résistance'),('vitesse', 'Bottes d\'Agilité'),('endurance', 'Bague d\'Endurance'),('attaque', 'Grimoire de Magie Noire'),('vitesse', 'Talisman de Rapidité'),('endurance', 'Collier d\'Endurance'),('attaque', 'Arcane de Destruction'),('defense', 'Ecu de Protection'),('vitesse', 'Tunique de Vitesse');
-- Arme
insert into item (qualite_id, type_arme_id, discriminateur, chemin_image, description, nom) values(1, 1, 'arme', '', 'Une épée longue avec des gravures détaillées.', 'Épée Longue Élégante'),(2, 2, 'arme', '', 'Une hache imposante avec une lame tranchante.', 'Hache de Guerre Lourde'),(3, 3, 'arme', '', 'Un arc long en bois de qualité supérieure.', 'Arc Long de Précision'),(4, 4, 'arme', '', 'Une dague légère et agile pour les coups rapides.', 'Dague Furtive'),(2, 1, 'arme', '', 'Une masse lourde avec des pointes en acier.', 'Masse Écrasante'),(2, 6, 'arme', '', 'Un bâton magique avec des runes gravées.', 'Bâton Arcanique'),(3, 7, 'arme', '', 'Une lance à la pointe acérée pour les attaques à distance.', 'Lance de Précision'),(4, 8, 'arme', '', 'Une rapière élégante conçue pour les attaques rapides.', 'Rapière Élancée'),(2, 10, 'arme', '', 'Un fouet agile pour des attaques à distance.', 'Fouet Rapide'),(1, 9, 'arme', '', 'Un marteau massif pour des attaques puissantes.', 'Marteau de Guerre'),(3, 1, 'arme', '', 'Une grande hallebarde avec une portée impressionnante.', 'Hallebarde Massive'),(4, 2, 'arme', '', 'Un katana tranchant avec un maniement rapide.', 'Katana Tranchant'),(1, 3, 'arme', '', 'Un arc court pour les tirs rapides et précis.', 'Arc Court de Rapidité'),(2, 4, 'arme', '', 'Un poignard enchanté pour des attaques magiques.', 'Poignard Enchanté'),(3, 5, 'arme', '', 'Un maillet équilibré pour des frappes précises.', 'Maillet Équilibré'),(4, 6, 'arme', '', 'Un sceptre magique orné de joyaux.', 'Sceptre Magistral'),(1, 7, 'arme', '', 'Une lance légère pour des mouvements rapides.', 'Lance Légère'),(2, 8, 'arme', '', 'Un épée courte idéale pour les combats rapprochés.', 'Épée Courte de Combat'),(3, 9, 'arme', '', 'Un marteau de guerre avec une tête imposante.', 'Marteau de Guerre Imposant'),(3, 10, 'arme', '', 'Un fouet en cuir pour des attaques de zone.', 'Fouet de Zone'),(1, 1, 'arme', '', 'Un grand marteau avec une force écrasante.', 'Grand Marteau Écrasant'),(2, 2, 'arme', '', 'Un sabre exotique avec des motifs élaborés.', 'Sabre Exotique'),(3, 3, 'arme', '', 'Un arc composite pour des tirs puissants.', 'Arc Composite Puissant'),(4, 4, 'arme', '', 'Un poignard empoisonné pour des attaques furtives.', 'Poignard Empoisonné'),(1, 5, 'arme', '', 'Une masse énergétique avec des effets magiques.', 'Masse Énergétique Magique');

-- Armure
insert into item (qualite_id, type_armure_id, discriminateur, chemin_image, description, nom) values(1, 1, 'armure', '', 'Une armure lourde en plaques d''acier.', 'Armure de Plates Robuste'),(2, 2, 'armure', '', 'Une armure en mailles offrant une bonne protection.', 'Armure de Mailles Standard'),(3, 3, 'armure', '', 'Une armure en cuir souple et légère.', 'Armure de Cuir Léger'),(4, 4, 'armure', '', 'Une armure magique avec des propriétés protectrices.', 'Armure Magique Enchantée'),(1, 5, 'armure', '', 'Un bouclier massif offrant une défense robuste.', 'Bouclier de Défense Massif'),(2, 6, 'armure', '', 'Un heaume imposant pour une protection de la tête.', 'Heaume de Protection Imposant'),(3, 7, 'armure', '', 'Des gantelets en acier pour protéger les mains.', 'Gantelets en Acier Protecteurs'),(4, 8, 'armure', '', 'Des bottes solides pour une protection des pieds.', 'Bottes Solides de Protection'),(1, 9, 'armure', '', 'Une armure légère avec des renforts en acier.', 'Armure Légère avec Renforts'),(2, 10, 'armure', '', 'Un manteau magique avec des effets protecteurs.', 'Manteau Magique Protecteur');

-- Accesoire
insert into item (qualite_id, type_accessoire_id, discriminateur, chemin_image, description, nom) values(1, 1, 'accessoire', '', 'Un anneau magique améliorant l''attaque.', 'Anneau de Puissance'),(2, 2, 'accessoire', '', 'Un pendentif qui renforce la défense.', 'Pendentif de Protection'),(3, 3, 'accessoire', '', 'Des gants magiques augmentant la vitesse.', 'Gants d''Agilité Magiques'),(4, 4, 'accessoire', '', 'Un médaillon offrant une résistance aux sorts.', 'Médaillon de Résistance Magique'),(1, 5, 'accessoire', '', 'Une ceinture améliorant la force physique.', 'Ceinture de Force'),(2, 6, 'accessoire', '', 'Des boucles d''oreilles qui améliorent la perception.', 'Boucles d''Oreilles de Perception'),(3, 7, 'accessoire', '', 'Une amulette qui apporte de la chance.', 'Amulette de Chance'),(4, 8, 'accessoire', '', 'Des bottes magiques augmentant l''endurance.', 'Bottes d''Endurance Magiques'),(1, 9, 'accessoire', '', 'Un bracelet qui améliore la régénération.', 'Bracelet de Régénération'),(2, 10, 'accessoire', '', 'Une cape qui rend invisible pendant un court laps de temps.', 'Cape d''Invisibilité');

-- Potion
insert into item (soin, discriminateur, chemin_image, description, nom) values(20, 'potion', '', 'Une petite potion de soin.', 'Potion Mineure de Soin'),(50, 'potion', '', 'Une potion de soin de taille moyenne.', 'Potion de Soin Standard'),(100, 'potion', '', 'Une grande potion de soin.', 'Potion Majeure de Soin'),(200, 'potion', '', 'Une potion de soin exceptionnelle.', 'Potion Supérieure de Soin');
-- Bombe
insert into item (nbr_des, max_des, discriminateur, description, nom) values(2, 6, 'bombe', 'Bombe explosive qui inflige des dégâts légers.', 'Bombe explosive'),(3, 8, 'bombe', 'Bombe incendiaire causant des dégâts moyens sur une zone étendue.', 'Bombe incendiaire'),(4, 10, 'bombe', 'Bombe fumigène qui obscurcit la vision et désoriente les ennemis.', 'Bombe fumigène'),(2, 4, 'bombe', 'Bombe sonore assourdissante infligeant des dégâts légers et désorientant les cibles.', 'Bombe sonore'),(3, 6, 'bombe', 'Bombe paralysante qui immobilise les ennemis pendant un court laps de temps.', 'Bombe paralysante'),(4, 8, 'bombe', 'Bombe hallucinogène provoquant des illusions chez les adversaires.', 'Bombe hallucinogène'),(3, 10, 'bombe', 'Bombe aveuglante qui provoque une cécité temporaire chez les ennemis.', 'Bombe aveuglante'),(2, 6, 'bombe', 'Bombe empoisonnée infligeant des dégâts au fil du temps.', 'Bombe empoisonnée'),(4, 12, 'bombe', 'Bombe corrosive qui cause des dégâts importants aux armures et aux structures.', 'Bombe corrosive'),(3, 8, 'bombe', 'Bombe électrique paralysant les ennemis avec des décharges électriques.', 'Bombe électrique');

-- Monstre 1
INSERT INTO personnage (nom, attaque, defense, endurance, vitesse, point_de_vie,utilisateur_id,arme_equipee_id,armure_equipee_id,accessoire_id) VALUES ('Goblin', 10, 5, 20, 15, 70,1,1,27,36);
INSERT INTO ligne_inventaire (quantite, item_id, personnage_id) VALUES (1,1,1);
-- Monstre 2
INSERT INTO personnage (nom, attaque, defense, endurance, vitesse, point_de_vie,utilisateur_id) VALUES ('Dragon Rouge', 50, 30, 100, 40, 150,1);

-- Monstre 3
INSERT INTO personnage (nom, attaque, defense, endurance, vitesse, point_de_vie,utilisateur_id,arme_equipee_id) VALUES ('Sorcier des Ombres', 25, 15, 60, 25, 100,1,12);

-- Monstre 4
INSERT INTO personnage (nom, attaque, defense, endurance, vitesse, point_de_vie, utilisateur_id,arme_equipee_id,armure_equipee_id,accessoire_id) VALUES ('Orc Berserker', 35, 20, 80, 30, 120,1,2,32,41);

-- Monstre 5
INSERT INTO personnage (nom, attaque, defense, endurance, vitesse, point_de_vie, utilisateur_id,arme_equipee_id,accessoire_id) VALUES ('Liche Maléfique', 40, 25, 90, 35, 110,1,8,45);

-- Monstre 6
INSERT INTO personnage (nom, attaque, defense, endurance, vitesse, point_de_vie,utilisateur_id) VALUES ('Chimère Élémentaire', 55, 35, 120, 45, 180,1);
-- Hero 7
INSERT INTO personnage (nom, attaque, defense, endurance, vitesse, point_de_vie,utilisateur_id,armure_equipee_id,arme_equipee_id) VALUES ('Paladin', 37, 45, 75, 30, 110,1,28,4);
INSERT INTO ligne_inventaire (quantite, item_id, personnage_id) VALUES (4,47,7),(1,4,7),(1,28,7);
INSERT INTO ligne_inventaire (quantite, item_id, personnage_id) VALUES (4,47,1),(1,51,1);
INSERT INTO ligne_inventaire (quantite, item_id, personnage_id) VALUES (3,48,2),(5,52,2);
INSERT INTO ligne_inventaire (quantite, item_id, personnage_id) VALUES (1,12,3),(1,49,3),(5,53,3);
INSERT INTO ligne_inventaire (quantite, item_id, personnage_id) VALUES (1,7,4),(1,2,4),(5,47,4);
INSERT INTO ligne_inventaire (quantite, item_id, personnage_id) VALUES (1,12,5),(1,48,5);
INSERT INTO ligne_inventaire (quantite, item_id, personnage_id) VALUES (1,18,6),(1,51,6);

INSERT INTO campagne (nom, date_maj, dernier_score, meilleur_score, statut, utilisateur_id,hero_id) VALUES ('Campagne1', '2023-01-01', 0, 0, 'En cours', 1,7);
INSERT INTO campagne (nom, date_maj, dernier_score, meilleur_score, statut, utilisateur_id,hero_id) VALUES ('Campagne2', '2023-02-01', 0, 0, 'En cours', 1,7);

insert into combat (nb_tour ,est_terminer, campagne_id, monstre_id) VALUES (0,false,1,1),(0,false,1,2),(0,false,1,3);
insert into combat (nb_tour ,est_terminer, campagne_id, monstre_id) VALUES (0,false,2,1),(0,false,2,2),(0,false,2,5),(0,false,2,6);
