package main.launcher.monitoring;

import java.io.IOException;
import java.util.Scanner;

import main.launcher.App;
import main.place.Restaurant;
import main.place.StatistiqueService;

public class OpenedRestaurantMonitoring {
    

    public static void showOpenedRestaurantMonitoringScreen(Scanner menuScanner) throws IOException{
        print("--------------------------------------------------------------------------");
        print("1 - Mettre fin au service et fermer le restaurant");
        print("2 - Voir les performances actuelles");

        print("3 - Retour au menu principal\n\n");

        String choixEcran = menuScanner.next();

        switch(choixEcran) { 
            case "1":
                // Renvoie vers une page de confirmation de fermeture du restaurant
                showCloseConfirmationScreen(menuScanner);
                break;
            case "2":
                /// Renvoie vers les statistiques actuels du restaurant
                showActualServicePerformance(menuScanner);
                break;
            case "3":
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


    public static void showCloseConfirmationScreen(Scanner menuScanner) throws IOException {
        print("==========================================================================");
        print("                            FERMETURE DU RESTAURANT\n");

        print("Cette action va mettre fin au service en cours et fermer le restaurant.");
        print("Cela signie que les clients en cours de service ne pourront plus commander.");
        print("Les commandes en cours de préparation seront annulées et les clients seront");
        print("invités à quitter le restaurant.\n\n");

        print("De plus, les statistiques du service en cours seront enregistrées jusqu'au");
        print("prochain service.\n");
        print("Etes-vous sûr de vouloir fermer le restaurant ?\n");

        print("--------------------------------------------------------------------------");
        print("1 - Oui, fermer le restaurant");
        print("2 - Annuler et retourner au menu de monitoring\n\n");

        String choixEcran = menuScanner.next();
        if(choixEcran.equals("1")) {
            // On ferme le restaurant
            Restaurant.setIsOpen(false);
            // On redirige vers l'écran de notification de fermeture du restaurant
            showClosedRestaurantNotification(menuScanner);
        } else if(choixEcran.equals("2")) {
            // On redirige vers l'écran de monitoring du restaurant ouvert
            MonitoringScreen.showMonitoringScreen(menuScanner);
        } else {
            showCloseConfirmationScreen(menuScanner);
        }
    }

    public static void showClosedRestaurantNotification(Scanner menuScanner) throws IOException{
        print("==========================================================================");
        print("                            FERMETURE DU RESTAURANT\n");

        print("Le restaurant a été fermé avec succès.\n");
        print("Les statistiques du service en cours et la liste de cours ont été enregistrées.");
        print("Vous pouvez désormais les consulter dans l'écran de monitoring.");

        print("--------------------------------------------------------------------------");
        print("1 - Retour au menu principal\n\n");

        String choixEcran = menuScanner.next();
        if(choixEcran.equals("1")) {
            // On redirige vers le menu principal
            App.showMainMenu();
        } else {
            showClosedRestaurantNotification(menuScanner);
        }
    }

    // Affiche divers statisques du dernier service effectué
    public static void showActualServicePerformance(Scanner menuScanner) throws IOException{
        clearConsole();
        print("==========================================================================");
        print("                     PERFORMANCES DU SERVICE ACTUELS\n\n");

        print("Voici les performances du services actuellement en cours :");
        print("--------------------------------------------------------------------------");
        // On affiche les stats du dernier service
        StatistiqueService.showActualStatistique();
        print("--------------------------------------------------------------------------\n\n");
        print("1 - Page précédente\n");
        String choixEcran = menuScanner.next();

        if(choixEcran.equals("1")){
            MonitoringScreen.showMonitoringScreen(menuScanner);
        } else {
            showActualServicePerformance(menuScanner);
        }
    }

    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        for(int i = 0; i < 50; i++) {
            print("\n");
        }
    }
}