package main.launcher.monitoring;

import java.io.IOException;
import java.util.Scanner;

import main.launcher.App;
import main.launcher.employee.TeamScreen;
import main.launcher.order.BillsManagement;
import main.place.Restaurant;
import main.place.StatistiqueService;
import main.place.Table;

public class ClosedRestaurantMonitoring {

    private ClosedRestaurantMonitoring() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires qui ne contiennent 
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    // Permet de savoir si la liste de course a été enregistrée dans un fichier
    // dans la fonction showLastServiceShoppingList
    private static boolean isSaved = false;
    

    public static void showClosedRestaurantMonitoringScreen(Scanner menuScanner) throws IOException {
        isSaved = false;

        print("--------------------------------------------------------------------------");
        print("1 - Voir les performances du dernier service");
        print("2 - Voir la liste de course du dernier service");
        print("3 - Gérer les tables\n");
        print("4 - Afficher les tickets de caisses enregistré");
        print("5 - Retirer un ticket de caisse\n");

        print("6 - Ouvrir le restaurant");
        print("--------------------------------------------------------------------------\n");

        print("7 - Retour au menu principal\n\n");

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
                // Appelle la fonction pour afficher les tickets de caisses
                // => Suivi des performances du restaurant
                // => Impression de la liste de course
                BillsManagement.afficherTousLesTickets(menuScanner);
                break;
            case "5":
                OpenedRestaurantMonitoring.deleteSelectedBill(menuScanner);
                break;
            case "6":
                // On redirige d'abord vers une fonction qui vérifie si le restaurant peut ouvrir
                // (Si l'équipe est formée et s'il y a assez de table pour accueillir les clients)
                tryToOpenRestaurant(menuScanner);
                break;
            case "7":
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

    // Affiche divers statisques du dernier service effectué
    public static void showLastServicePerformance(Scanner menuScanner) throws IOException{
        clearConsole();
        print("==========================================================================");
        print("                     PERFORMANCES DU DERNIER SERVICE\n\n\n");

        print("Voici les performances du dernier service effectué par le restaurant");
        print("--------------------------------------------------------------------------\n");
        // On affiche les stats du dernier service
        StatistiqueService.showActualStatistique();
        print("--------------------------------------------------------------------------\n\n");
        print("1 - Page précédente\n");
        String choixEcran = menuScanner.next();

        if(choixEcran.equals("1")){
            MonitoringScreen.showMonitoringScreen(menuScanner);
        } else {
            showLastServicePerformance(menuScanner);
        }
    }

    // Affiche la liste de course du dernier service
    // On part d'une quantité "par défaut" de chaque ingrédient, et en enlève dès que
    // l'on en utilise un dans une recette. Ainsi, on peut savoir ce qu'il faut commander
    // à la fin du service pour re-remplir le stock
    public static void showLastServiceShoppingList(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================");
        print("LISTE DE COURSE DU DERNIER SERVICE\n\n");
        print("Voici la liste des aliments et boissons à commander afin de re-remplir le");
        print("stock du restaurant pour le prochain service\n\n");

        print("Liste de course : \n");
        print("--------------------------------------------------------------------------");
        
        ShoppingList.getLastServiceShoppingList(menuScanner);

        print("--------------------------------------------------------------------------");
        print("1 - Imprimer la liste de course\n");
        if(isSaved){
            print("La liste de course a été enregistrée avec succès !\n");
        }
        print("2 - Page précédente\n\n");
        String choixEcran = menuScanner.next();

        if(choixEcran.equals("1")){
            // TODO : Imprime la liste de course
            ShoppingList.saveShoppingListToFile();
            isSaved = true;
            showLastServiceShoppingList(menuScanner);
        } else {
            MonitoringScreen.showMonitoringScreen(menuScanner);
        }
        
    }

    

    // Permet d'afficher et de gérer les tables du restaurant
    public static void showTableManagementScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================");
        print("                         GESTION DES TABLES\n\n");

        print("Vous pouvez ici ajouter ou supprimer des tables du restaurant");
        print("Pour un service optimal, il est recommandé d'avoir au moins 6 table.\n");
        print("Par défaut, le restaurant comporte 10 tables, de 2, 4 et 8 couverts\n");
        print("Composition actuelle des tables du restaurant :");
        print("--------------------------------------------------------------------------");
        Table.showTables(Restaurant.getTablesList());
        print("--------------------------------------------------------------------------\n");
        print("1 - Ajouter des tables");
        print("2 - Réinitialiser la selection");
        print("3 - Page précédente\n\n");


        String choixEcran = menuScanner.next();

        switch(choixEcran){
            case "1":
                // Redirige vers un menu d'affichage des tables
                setNewTablePlan(menuScanner);
                break;
            case "2":
                // On supprime toutes les tables du restaurant
                Restaurant.getTablesList().clear();
                // On redirige vers la page précédente
                showTableManagementScreen(menuScanner);
                break;
            case "3":
                // Retour au menu précédent
                MonitoringScreen.showMonitoringScreen(menuScanner);
                break;
            default:
                showTableManagementScreen(menuScanner);
                break;
        }
    }

    // Permet de définir un plan de table personnalisé, si l'on veut changer de celui par défaut
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

    // On vérifie si le restaurant peut ouvrir
    public static void tryToOpenRestaurant(Scanner menuScanner) throws IOException{

        // Dans le cas où il n'y a pas de table
        if(Restaurant.getTablesList().isEmpty()){
            clearConsole();
            print("==========================================================================");
            print("                            OUVERTURE DU RESTAURANT\n");
            print("ERREUR : Vous ne pouvez pas ouvrir le restaurant car il n'y a pas de table.");
            print("Veuillez en ajouter dans l'écran de gestion des tables de l'écran monitoring.\n\n");
            print("1 - Accéder à l'écran de gestion des tables");
            print("2 - Page précédente\n\n");

            String input = menuScanner.next();
            if (input.equals("1")){
                // S'il le souhaite, l'utilisateur peut directement se rendre dans l'écran de gestion de table
                showTableManagementScreen(menuScanner);
            } else if(input.equals("2")){
                MonitoringScreen.showMonitoringScreen(menuScanner);
            } else {
                tryToOpenRestaurant(menuScanner);
            }
        }

        // Dans le cas où il n'y a pas d'équipe formé ET confirmé dans l'écran de gestion des employés
        if(!Restaurant.isEquipeActuelleCreated()) {
            clearConsole();
            print("==========================================================================");
            print("                            OUVERTURE DU RESTAURANT\n");
            print("Vous ne pouvez pas ouvrir le restaurant car il n'y a pas d'équipe.");
            print("Veuillez en former une dans l'écran de gestion des employés.\n\n");
            print("1 - Accéder à l'écran de formation d'équipe");
            print("2 - Page précédente\n\n");

            String input = menuScanner.next();
            if (input.equals("1")){
                // S'il le souhaite, l'utilisateur peut directement se rendre dans l'écran de gestion de table
                TeamScreen.showTeamFormationScreen(menuScanner);
            } else if(input.equals("2")){
                MonitoringScreen.showMonitoringScreen(menuScanner);
            } else {
                tryToOpenRestaurant(menuScanner);
            }
        }

        // On remet les stats à zéro afin de prendre en compte que le nouveau service
        StatistiqueService.resetAllStatistique();

        // On peut ouvrir le restaurant
        Restaurant.setIsOpen(true);

        // Et on redirige vers l'écran de confirmation d'ouverture du restaurant
        showOpenConfirmationScreen(menuScanner);
    }

    // Ecran qui demande une confirmation avant d'ouvrir pour de bon le restaurant
    public static void showOpenConfirmationScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================");
        print("                            OUVERTURE DU RESTAURANT\n");

        print("Cette action va lancer un nouveau service et ouvrir le restaurant.");
        print("Cela signie que tout les employés vont pouvoir commencer à travailler.\n");

        print("De plus, les statistiques et la liste de course du dernier service seront");
        print("remplacés par celles de ce nouveau service.\n");

        print("Vérifiez donc que vous avez bien rempli le stock;\n\n");

        print("Etes-vous sûr de vouloir ouvrir le restaurant ?\n");

        print("--------------------------------------------------------------------------");
        print("1 - Oui, ouvrir le restaurant");
        print("2 - Non, annuler et retourner au menu de monitoring\n\n");

        String choixEcran = menuScanner.next();
        if(choixEcran.equals("1")) {
            // On ouvre le restaurant
            Restaurant.setIsOpen(true);

            // On reset l'affiche de la notification de sauvegarde du ticket de caisse
            ShoppingList.setIsSaved(false);
            // On redirige vers l'écran de notification d'ouverture du restaurant
            showOpenedConfirmationScreen(menuScanner);
        } else if(choixEcran.equals("2")) {
            // On redirige vers l'écran de monitoring du restaurant fermé
            showClosedRestaurantMonitoringScreen(menuScanner);
        } else {
            showOpenConfirmationScreen(menuScanner);
        }
    }


    public static void showOpenedConfirmationScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================");
        print("                         RESTAURANT OUVERT\n\n");
        print("Le restaurant est maintenant ouvert,les employés peuvent commencer à travailler.");
        print("Lorsque vous voudrez mettre fin au service, revenez ici pour fermer le restaurant.\n\n");
        print("1 - Retour au menu principale\n");
        String choixEcran = menuScanner.next();
        if(choixEcran.equals("1")){
            App.showMainMenu();
        } else {
            showOpenedConfirmationScreen(menuScanner);
        }
    }

    public static void setSaved(boolean b){
        isSaved = b;
    }


    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        for(int i = 0; i < 50; ++i)
            System.out.println();
    }
}