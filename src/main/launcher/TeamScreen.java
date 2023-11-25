package main.launcher;

import java.util.Scanner;

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

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            default:
                showAddRemoveEmployeeScreen(menuScanner);

        }
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