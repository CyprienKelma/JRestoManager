package main.launcher.monitoring;

import java.io.IOException;
import java.util.Scanner;

import main.launcher.App;
import main.launcher.order.BillsManagement;
import main.place.Restaurant;
import main.place.StatistiqueService;

public class OpenedRestaurantMonitoring {

    OpenedRestaurantMonitoring() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires qui ne contiennent 
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }
    

    public static void showOpenedRestaurantMonitoringScreen(Scanner menuScanner) throws IOException{
        print("--------------------------------------------------------------------------");
        print("1 - Mettre fin au service et fermer le restaurant\n");
        print("2 - Voir les performances actuelles du service");
        print("3 - Afficher les tickets de caisses enregistré");
        print("4 - Retirer un ticket de caisse\n");

        print("5 - Retour au menu principal\n\n");

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
                // Appelle la fonction pour afficher les tickets de caisses sauvegardés
                // => Suivi des performances du restaurant
                // => Impression de la liste de course
                BillsManagement.afficherTousLesTickets(menuScanner);
                break;
            case "4":
                // Redirige vers la fonction pour retirer un ticket de caisse de la liste
                deleteSelectedBill(menuScanner);
                break;
            case "5":
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

    // Fonction pour retirer un ticket de caisse
    public static void deleteSelectedBill(Scanner menuScanner) throws IOException {
        print("\n--------------------------------------------------------------------------");
        print("Donner l'id de la facture en question pour le retirer");
        String choixEcran1 = menuScanner.next();
        
        try {
            // Convertit la selection en int
            int idFacture = Integer.parseInt(choixEcran1);
            
            // Vérifie si l'ID de la facture existe
            if (idFacture > 0 && idFacture <= BillsManagement.getLastBillId()) {
                // Appele la fonction pour retirer la facture
                BillsManagement.retirerBill(idFacture,menuScanner);
            } else {
                // Affiche un message si l'ID de la facture n'est pas valide
                print("L'ID de la facture n'existe pas. Veuillez réessayer.");
                deleteSelectedBill(menuScanner);
            }
        } catch (NumberFormatException e) {
            // Gére une exception si l'entrée utilisateur n'est pas un entier
            print("L'ID de la facture n'est pas valide. Veuillez réessayer.");
            deleteSelectedBill(menuScanner);
        }
    }


    public static void showCloseConfirmationScreen(Scanner menuScanner) throws IOException {
        clearConsole();
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
        clearConsole();
        print("==========================================================================");
        print("                            FERMETURE DU RESTAURANT\n");

        print("Le restaurant a été fermé avec succès.\n");
        print("Les statistiques du service en cours et la liste de cours ont été enregistrées.");
        print("Vous pouvez désormais les consulter dans l'écran de monitoring.");

        print("--------------------------------------------------------------------------");
        print("1 - Indiquer aux employés de nettoyer le magasin\n\n");

        String choixEcran = menuScanner.next();
        if(choixEcran.equals("1")) {
            showCleanRestaurantConfirmation(menuScanner);
        } else {
            showClosedRestaurantNotification(menuScanner);
        }
    }

    public static void showCleanRestaurantConfirmation(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================");
        print("                            FERMETURE DU RESTAURANT\n\n");

        print("A présent, le restaurant est fermé. Néanmoins, n'oubliez pas d'indiquer aux");
        print("employés de nettoyer le magasin avant de partir.\n\n");

        print("--------------------------------------------------------------------------");
        print("1 - Indiquer que les employés ont tous bien néttoyé le magasin");
        print("    et retourner au menu principale\n\n");

        String choixEcran = menuScanner.next();
        if(choixEcran.equals("1")) {
            App.showMainMenu();
        } else {
            showCleanRestaurantConfirmation(menuScanner);
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