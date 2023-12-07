package main.launcher.monitoring;

import java.io.IOException;
import java.util.Scanner;

import main.launcher.App;
import main.place.StatistiqueService;

public class ClosedRestaurantMonitoring {
    

    public static void showClosedRestaurantMonitoringScreen(Scanner menuScanner) throws IOException{
        print("--------------------------------------------------------------------------\n");
        print("1 - Voir les performances du dernier service");
        print("2 - Voir la liste de course du dernier services");
        print("3 - Gérer les tables\n");

        print("4 - Ouvrir le restaurant\n");
        print("--------------------------------------------------------------------------\n");

        print("5 - Retour au menu principal\n\n");

        String choixEcran = menuScanner.next();

        switch(choixEcran){
            case "1":
                // Renvoie vers le suivit des performances du services précédent
                // showLastServicePerformance(menuScanner)
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
                // Ouvre le restaurant
                break;
            case "5":
                // Appelle la fonction de l'écran de monitoring
                // => Suivi des performances du restaurant
                // => Impression de la liste de course
                App.showMainMenu();
                break;
            default:
                showClosedRestaurantMonitoringScreen(menuScanner);
                break;
        }
    }

    public static void showLastServicePerformance(Scanner menuScanner) throws IOException{
        print("--------------------------------------------------------------------------\n");
        print("PERFORMANCES DU DERNIER SERVICE\n");
        print("--------------------------------------------------------------------------\n");
        print("Nombre de transaction effectués : " + 0 + "\n");
        print("Nombre de clients venu : " + 0 + "\n");
        print("Nombre de plats vendus : " + 0 + "\n");
        print("Nombre de boissons vendues : " + 0 + "\n");
        print("Plats les plus selectionnés : " + 0 + "\n");
        print("Boissons les plus selectionnées : " + 0 + "\n");
        print("--------------------------------------------------------------------------\n");
        print("");
        print("Appuyez sur Entrée pour continuer\n");
        menuScanner.nextLine();
        showClosedRestaurantMonitoringScreen(menuScanner);
    }

    public static void showLastServiceShoppingList(Scanner menuScanner) throws IOException{
        print("==========================================================================");
        print("LISTE DE COURSE DU DERNIER SERVICE\n\n");
        print("Voici la liste des aliments et boissons à commander afin de re-remplir le");
        print("stock du restaurant pour le prochain service\n\n");

        print("Liste de course : \n");
        print("--------------------------------------------------------------------------\n");
        



        print("--------------------------------------------------------------------------\n");
        print("1 - Imprimer la liste de course\n");
        String choixEcran = menuScanner.next();

        if(choixEcran.equals("1")){
            // TODO : Imprime la liste de course
            
        } else {
            showLastServiceShoppingList(menuScanner);
        }
        
    }


    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        print("\n");
    }
}