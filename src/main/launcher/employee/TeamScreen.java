package main.launcher.employee;

import java.util.Scanner;

import main.launcher.App;

public class TeamScreen {


    private TeamScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires qui ne contiennent 
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }



        // Programmer les employés pour la soirée (i.e. former l'équipe)
    public static void showTeamFormationScreen(Scanner menuScanner) {
        clearConsole();
        print("==========================================================================\n");
        print("FORMER L'EQUIPE DE CE SOIR :\n");

        print("Avant de pouvoir ouvrir le restaurant, vous devez former l'équipe de ce soir.\n");
        print("Pour former cette équipe, vous devez choisir les employés qui travailleront");
        print("durant tout ce service.\n");

        print("1 - Choisir les serveurs");
        print("2 - Choisir les cuisiniers");
        print("3 - Choisir le barman");
        print("4 - Choisir le manager\n");
        print("5 - Confirmer la selection et créer l'équipe");
        print("\n6 - Page précédente");
        print("7 - Retour au menu principal\n");

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
                EmployeeScreen.showTeamScreen(menuScanner);
                break;
            case 7:
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