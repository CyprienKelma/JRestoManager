package main.launcher;

import java.util.Scanner;
import main.place.Restaurant;
import main.staff.*;

/*
 * Classe qui gère l'écran de gestion des employés (5)
 * Comprend les sous-écrans suivants :
 *  A. Ajouter/Supprimer un employé
 *  B. Programmer les employés pour la soirée (i.e. former l'équipe) (B)
 */
public class TeamScreen {

    private TeamScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Ce sont des classes utilitaires qui ne contiennent que des méthodes statiques
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    // Ecran principal de gestion des employés (5)
    public static void showTeamScreen(Scanner menuScanner) {
        clearConsole();
        print("==========================================================================\n");
        print("GESTION DES EMPLOYÉS\n");
        print("1 - Ajouter/Supprimer un employé");
        print("2 - Programmer les employés pour la soirée (i.e. former l'équipe)");
        print("\n3- Retour au menu principal\n");
        int choixEcran = menuScanner.nextInt();
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

                break;
            case 4:

                break;
            default:
                showAddRemoveEmployeeScreen(menuScanner);

        }
    }

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

    private static void addNewEmployee(Scanner menuScanner, String string) {
        clearConsole();
        print("==========================================================================\n");
        print("AJOUTER UN EMPLOYÉ :\n");

        print("Veuillez entrer le nom de l'employé :\n");
        String nom = menuScanner.next();

        print("Veuillez entrer le prénom de l'employé :\n");
        String prenom = menuScanner.next();

        print("Veuillez entrer le salaire de l'employé (paye pour un service complet) :\n");
        double salaire = menuScanner.nextDouble();

        switch (string) {
            case "Serveur":
                Serveur serveur = new Serveur(nom, prenom, salaire);
                break;
            case "Cuisinier":
                Cuisinier cuisinier = new Cuisinier(nom, prenom, salaire);
                break;
            case "Barman":
                Barman barman = new Barman(nom, prenom, salaire);
                break;
            case "Manager":
                Manager manager = new Manager(nom, prenom, salaire);
                break;
            default:
                throw new IllegalArgumentException("Classe TeamScreen - Type d'employé inatendu : " + string);
                break;
        }
    }

    private static void showRemoveEmployee(Scanner menuScanner) {

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