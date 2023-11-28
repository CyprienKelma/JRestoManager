package main.launcher.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.launcher.App;
import main.place.Restaurant;
import main.staff.Employé;

public class TeamScreen {

    static List<Employé> createEmployésList = Restaurant.getEmployésList();


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

    public static void showSelectServer(Scanner menuScanner) {
        clearConsole();
        print("==========================================================================\n");
        print("CHOISIR LES SERVEURS :\n");

        print("Vous devez choisir 2 serveurs qui travailleront durant tout ce service.\n");

        print("1 - Serveur 1 : A SELECTIONNER");
        print("2 - Serveur 2 : A SELECTIONNER");
        print("\n3 - Confirmer la selection");
        print("\n4 - Page précédente");
        print("5 - Retour au menu principal\n");

        int choixEcran = menuScanner.nextInt();

        switch (choixEcran) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:
                showTeamFormationScreen(menuScanner);
                break;
            case 5:
                App.showMainMenu();
                break;
            default:

        }
    }

    public static void showEmployeeListByType(Scanner menuScanner, String type, int whichOne){

        clearConsole();
        print("==========================================================================\n");
        if(whichOne == 1){
            print("Selectionner un premier " + type + "à ajouter parmis la liste :");
        } else {
            print("Selectionner un " + whichOne + "ème " + type + "à ajouter parmis la liste :");
        }
        
        
        for(int i = 0; i < createEmployésList.size(); i++){
            Employé employé = createEmployésList.get(i);

            if(employé.getClass().getSimpleName().equals(type)){
                print((i + 1) + ") " + type + " : " + employé.getNom() + ", " + employé.getPrenom() + ", "
                    + employé.getSalaire() + " euros/h net.");
            }
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