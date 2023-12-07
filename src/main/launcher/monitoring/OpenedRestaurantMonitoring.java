package main.launcher.monitoring;

import java.io.IOException;
import java.util.Scanner;

import main.launcher.App;

public class OpenedRestaurantMonitoring {
    

    public static void showOpenedRestaurantMonitoringScreen(Scanner menuScanner) throws IOException{
        print("--------------------------------------------------------------------------\n");
        print("1 - Mettre fin au service et fermer le restaurant");
        print("2 - Voir les performances actuelles");

        print("3 - Retour au menu principal\n\n");

        String choixEcran = menuScanner.next();

        switch(choixEcran){
            case "1":
                // Renvoie vers le suivit des performances du services précédent
                // showLastServicePerformance(menuScanner);
                break;
            case "2":
                // Renvoie vers la liste de course du services précédent
                // showLastServiceShoppingList(menuScanner);
                break;
            case "3":
                // Renvoie vers la gestion des tables, pour en ajouter ou en supprimer
                // showTableManagementScreen(menuScanner);
                break;
            case "4":
                // Appelle la fonction de l'écran de monitoring
                // => Suivi des performances du restaurant
                // => Impression de la liste de course
                App.showMainMenu();
                break;
            default:
                print("Erreur de saisie, veuillez réessayer\n");
                showOpenedRestaurantMonitoringScreen(menuScanner);
                break;
        }
    }


    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        print("\n");
    }
}