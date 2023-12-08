package main.launcher.monitoring;

import java.io.IOException;
import java.util.Scanner;

import main.launcher.App;
import main.place.Restaurant;
import main.place.StatistiqueService;
import main.place.Table;

public class ClosedRestaurantMonitoring {
    

    public static void showClosedRestaurantMonitoringScreen(Scanner menuScanner) throws IOException{
        print("--------------------------------------------------------------------------\n");
        print("1 - Voir les performances du dernier service");
        print("2 - Voir la liste de course du dernier service");
        print("3 - Gérer les tables\n");

        print("4 - Ouvrir le restaurant");
        print("--------------------------------------------------------------------------\n");

        print("5 - Retour au menu principal\n\n");

        String choixEcran = menuScanner.next();

        switch(choixEcran){
            case "1":
                // Renvoie vers le suivit des performances du services précédent
                showLastServicePerformance(menuScanner);
                break;
            case "2":
                // Renvoie vers la liste de course du services précédent
                showLastServiceShoppingList(menuScanner);
                break;
            case "3":
                // Renvoie vers la gestion des tables, pour en ajouter ou en supprimer
                showTableManagementScreen(menuScanner);
                break;
            case "4":
                // On remet les stats à zéro afin de prendre en compte que le nouveau service
                StatistiqueService.resetAllStatistique();

                // On définie le restaurant comme ouvert
                Restaurant.setIsOpen(true);
                // Et on redirige vers l'écran de confirmation d'ouverture du restaurant
                MonitoringScreen.showMonitoringScreen(menuScanner);
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
        clearConsole();
        print("==========================================================================");
        print("                     PERFORMANCES DU DERNIER SERVICE\n\n\n");

        print("Voici les performances du dernier service effectué par le restaurant");
        print("--------------------------------------------------------------------------\n");
        // On affiche les stats du dernier service
        StatistiqueService.showActualStatistique();
        print("--------------------------------------------------------------------------\n\n");
        print("Appuyez sur Entrée pour continuer\n");
        menuScanner.nextLine();
        showClosedRestaurantMonitoringScreen(menuScanner);
    }

    public static void showLastServiceShoppingList(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================");
        print("LISTE DE COURSE DU DERNIER SERVICE\n\n");
        print("Voici la liste des aliments et boissons à commander afin de re-remplir le");
        print("stock du restaurant pour le prochain service\n\n");

        print("Liste de course : \n");
        print("--------------------------------------------------------------------------\n");
        



        print("--------------------------------------------------------------------------\n");
        print("1 - Imprimer la liste de course\n");
        print("2 - Page précédente\n\n");
        String choixEcran = menuScanner.next();

        if(choixEcran.equals("1")){
            // TODO : Imprime la liste de course
            
        } else {
            showLastServiceShoppingList(menuScanner);
        }
        
    }

    public static void showTableManagementScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================");
        print("                         GESTION DES TABLES\n\n");

        print("Composition actuelle des tables du restaurant :");
        print("--------------------------------------------------------------------------");
        Table.showTables(Restaurant.getTablesList());
        print("--------------------------------------------------------------------------\n");
        print("1 - Définir un nouveau plan de table");
        print("2 - Page précédente\n\n");


        String choixEcran = menuScanner.next();

        switch(choixEcran){
            case "1":
                // Redirige vers un menu d'affichage des tables
                setNewTablePlan(menuScanner);
                break;
            case "2":
                // Retour au menu précédent
                showClosedRestaurantMonitoringScreen(menuScanner);
                break;
            default:
                showTableManagementScreen(menuScanner);
                break;
        }
    }

    public static void setNewTablePlan(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================");
        print("                         GESTION DES TABLES\n\n");

        print("Composition actuelle des tables du restaurant :");
        print("--------------------------------------------------------------------------");
        Table.showTables(Restaurant.getTablesList());
        print("--------------------------------------------------------------------------\n\n");
        print("Composer un plan de table : \n");
        print("Pour définir un plan de table, veuillez indiquer le nombre de table à ajouter,");
        print("ainsi que leurs nombres de couverts respectifs :\n");

        print("Nombre de tables à ajouter : ");
        String nbrTable = menuScanner.next();
        // On test si le nombre de couvert est raisonable, sinon on redemande
        if(Integer.parseInt(nbrTable) < 1 || Integer.parseInt(nbrTable) > 1000){
            setNewTablePlan(menuScanner);
        }

        print("Nombre de couverts pour chacune de ces tables : ");
        String nbrCouvert = menuScanner.next();
        // On test si le nombre de couvert est raisonable, sinon on redemande
        if(Integer.parseInt(nbrCouvert) < 1 || Integer.parseInt(nbrCouvert) > 1000){
            setNewTablePlan(menuScanner);
        }

        // On ajoute les tables au restaurant
        Table.addSomeTable(Integer.parseInt(nbrTable), Integer.parseInt(nbrCouvert));

        // Ensuite on redirige vers la page précédente
        showTableManagementScreen(menuScanner);
    }


    public static void showOpenedConfirmationScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================");
        print("                         RESTAURANT OUVERT\n\n");
        print("Le restaurant est maintenant ouvert, les employés peuvent commancer à travailler");
        print("Lorsque vous voudrez mettre fin au service, revenez ici pour fermer le restaurant.\n\n");
        print("Appuyez sur Entrée pour continuer\n");
        menuScanner.nextLine();
        App.showMainMenu();
    }


    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        print("\n");
    }
}