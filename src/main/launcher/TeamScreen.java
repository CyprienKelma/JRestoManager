package main.launcher;

import java.util.List;
import java.util.Scanner;
import main.place.Restaurant;
import main.staff.*;

/*
 * Classe qui gère l'écran de gestion des employés (5)
 * Comprend les sous-écrans suivants :
 *  A. Ajouter/Supprimer un employé
 *  B. Programmer les employés pour la soirée (i.e. former l'équipe)
 */
public class TeamScreen {

    private TeamScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Ce sont des classes utilitaires qui ne contiennent que des méthodes statiques
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    // Ecran principal de gestion des employés (5)
    public static void showTeamScreen(Scanner menuScanner) {
        clearConsole(); // Nettoie la console pour une meilleure lisibilité
        print("==========================================================================\n");
        print("GESTION DES EMPLOYÉS\n");
        print("1 - Ajouter/Supprimer un employé");
        print("2 - Programmer les employés pour la soirée (i.e. former l'équipe)");

        print("\n3- Retour au menu principal\n");
        int choixEcran = menuScanner.nextInt();

        switch (choixEcran) {
            case 1:
                showAddRemoveEmployeeScreen(menuScanner);
                break;
            case 2:
                showTeamFormationScreen(menuScanner);
                break;
            case 3:
                App.showMainMenu();
                break;
            default:
                showTeamScreen(menuScanner);
        }
    }

    // Sous-écran A : Ajouter/Supprimer un employé
    public static void showAddRemoveEmployeeScreen(Scanner menuScanner) {
        clearConsole();
        print("==========================================================================\n");
        print("AJOUTER/SUPPRIMER UN EMPLOYÉ :\n");
        print("1 - Ajouter un employé");
        print("2 - Supprimer un employé");

        print("\n3 - Page précédente\n");
        print("4 - Retour au menu principal\n");
        int choixEcran = menuScanner.nextInt();

        switch (choixEcran) {
            case 1:
                showAddEmployee(menuScanner);
                break;
            case 2:
                showRemoveEmployee(menuScanner);
                break;
            case 3:
                showTeamScreen(menuScanner);
                break;
            case 4:
                App.showMainMenu();
                break;
            default:
                showAddRemoveEmployeeScreen(menuScanner);

        }
    }

    // Sous-écran A1 : Ajouter un employé
    private static void showAddEmployee(Scanner menuScanner) {
        clearConsole();
        print("==========================================================================\n");
        print("AJOUTER UN EMPLOYÉ :\n");
        print("Quel type d'employé voulez-vous ajouter ?\n");

        print("1 - Serveur");
        print("2 - Cuisinier");
        print("3 - Barman");
        print("4 - Manager");
        print("\n5 - Page précédente\n");
        print("6 - Retour au menu principal\n");
        int choixEcran = menuScanner.nextInt();

        switch (choixEcran) {
            case 1:
                addNewEmployee(menuScanner, "Serveur");
                break;
            case 2:
                addNewEmployee(menuScanner, "Cuisinier");
                break;
            case 3:
                addNewEmployee(menuScanner, "Barman");
                break;
            case 4:
                addNewEmployee(menuScanner, "Manager");
                break;
            case 5:
                showAddRemoveEmployeeScreen(menuScanner);
                break;
            case 6:
                App.showMainMenu();
                break;
            default:
                showAddEmployee(menuScanner);
        }

    }

    /**
     * Ajoute un nouvelle employé au restaurant
     *
     * @param menuScanner Le scanner pour naviguer dans le menu
     * @param typeEmployé Le type d'employé à ajouter (Serveur, Cuisinier, Barman,
     *                    Manager)
     * @throws IllegalArgumentException Dans le cas ou le type d'employé est
     *                                  inatendu
     */
    private static void addNewEmployee(Scanner menuScanner, String typeEmployé) {
        clearConsole();
        print("==========================================================================\n");
        print("AJOUTER UN EMPLOYÉ :\n");

        print("Veuillez entrer le nom de l'employé :\n");
        String nom = menuScanner.next();

        print("Veuillez entrer le prénom de l'employé :\n");
        String prenom = menuScanner.next();

        print("Veuillez entrer le salaire de l'employé (la paye pour un service complet) :\n");
        double salaire = menuScanner.nextDouble();

        switch (typeEmployé) {

            // Dans le cas ou l'on ajoute un serveur
            case "Serveur":
                Serveur serveur = new Serveur(nom, prenom, salaire);
                Restaurant.getEmployésList().add(serveur);

                showConfirmationEmployeeChange(menuScanner, typeEmployé, true);
                break;

            // Dans le cas ou l'on ajoute un cuisinier
            case "Cuisinier":
                Cuisinier cuisinier = new Cuisinier(nom, prenom, salaire);
                Restaurant.getEmployésList().add(cuisinier);

                showConfirmationEmployeeChange(menuScanner, typeEmployé, true);
                break;

            // Pour un barman
            case "Barman":
                Barman barman = new Barman(nom, prenom, salaire);
                Restaurant.getEmployésList().add(barman);

                showConfirmationEmployeeChange(menuScanner, typeEmployé, true);
                break;

            // Et enfin pour un manager
            case "Manager":
                Manager manager = new Manager(nom, prenom, salaire);
                Restaurant.getEmployésList().add(manager);

                showConfirmationEmployeeChange(menuScanner, typeEmployé, true);
                break;
            default:
                throw new IllegalArgumentException("Classe TeamScreen - Type d'employé inatendu : " + typeEmployé);
        }
    }

    // Sous-écran A2 : Supprimer un employé
    private static void showRemoveEmployee(Scanner menuScanner) {

        List<Employé> employésList = Restaurant.getEmployésList();

        if (employésList.isEmpty()) {
            clearConsole();
            print("==========================================================================\n");
            print("Désolé, impossible de supprimer un employé.");
            print("La liste des employés est vide.\n");
            print("1 - Revenir à la page précédente\n");
            int input = menuScanner.nextInt();

            if (input == 1) {
                showAddRemoveEmployeeScreen(menuScanner);
            } else {
                showRemoveEmployee(menuScanner);
            }
        }

    }

    // Affiche un message de confirmation, suivit d'un menu pour choisir la suite
    // Précise en paramètre si l'on ajoute un employé (isAdd = true) ou le supprime
    // (isAdd = false). En fonction des cas les menus seront différents
    public static void showConfirmationEmployeeChange(Scanner menuScanner, String typeEmployé, boolean isAdd) {
        clearConsole();
        print("==========================================================================\n");
        if (isAdd) {
            print("Le " + typeEmployé + " a bien été ajouté à la liste des employés !\n");
        } else {
            print("Le " + typeEmployé + " a bien été supprimé de la liste des employés !\n");
        }
        print("Que voulez vous faire maintenant :\n");
        if (isAdd) {
            print("1 - Ajouter un autre employé");
        } else {
            print("1 - Supprimer un autre employé");
        }
        print("2 - Revenir à la page précédente\n");
        print("3 - Retour au menu principal\n");

        int choixEcran = menuScanner.nextInt();

        if (choixEcran == 1 && isAdd)
            showAddEmployee(menuScanner);
        else if (choixEcran == 1 && !isAdd)
            showRemoveEmployee(menuScanner);
        else if (choixEcran == 2)
            showAddRemoveEmployeeScreen(menuScanner);
        else if (choixEcran == 3)
            App.showMainMenu();
    }

    // Sous-écran B : Programmer les employés pour la soirée (i.e. former l'équipe)
    public static void showTeamFormationScreen(Scanner menuScanner) {
        clearConsole();
        print("==========================================================================\n");
        print("FORMER L'EQUIPE DE CE SOIR :\n");
        print("1 - Choisir les serveurs");
        print("2 - Choisir les cuisiniers");
        print("3 - Choisir le barman");
        print("4 - Choisir le manager");
        print("\n5 - Page précédente\n");
        print("6 - Retour au menu principal\n");

        int choixEcran = menuScanner.nextInt();

        switch (choixEcran) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:
                App.showMainMenu();
                break;
            default:

        }
    }

    // utiliser pour afficher un texte plus rapidement
    public static void print(String text) {
        System.out.println(text);
    }

    // Fonction qui permet de nettoyer la console pour une meilleure lisibilité
    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }
}