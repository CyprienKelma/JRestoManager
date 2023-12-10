package main.launcher;

import java.io.IOException;
import java.util.*;

import main.launcher.employee.*;
import main.launcher.monitoring.MonitoringScreen;
import main.launcher.order.OrderTakingScreen;

//import main.launcher.employee.SaveTmpTeam;
import main.place.*;

// Classe principale qui gère le lancement de l'application
public class App {
    public static void main(String[] args) throws Exception {

        // Charge tout les fichiers de sauvegarde du projet 
        // dès le lancement de l'application
        loadAllFile();

        // Affiche le menu principal de l'application
        showMainMenu();
    }

    // Permet de charger les fichiers de sauvegarde du projet
    public static void loadAllFile() throws Exception {
        SaveEmployee.loadEmployeeListFromTheFile();
        //SaveTmpTeam.loadTemporaryVariablesFromFile();
    }

    // Fonction qui affiche le menu principal
    public static void showMainMenu() throws IOException {
        clearConsole();

        print("==========================================================================");
        print("************************** JRESTO MANAGER PRO® ***************************");
        print("**************************** Portail Employé *****************************");
        print("==========================================================================\n\n");

        print("           Bienvenue dans l'application JRESTO MANAGER PRO® !\n");
        print("                      Etat actuel du restaurant :");
        print("                                 " + (Restaurant.isOpen() ? "OUVERT" : "FERMÉ\n\n"));

        print("--------------------------------------------------------------------------");
        print("                   Quel écran souhaitez-vous afficher ?");
        print("--------------------------------------------------------------------------\n");
        print("1 - Ecran prise de commande\n");
        print("2 - Ecran cuisine\n");
        print("3 - Ecran bar\n");
        print("4 - Ecran Monitoring\n");
        print("5 - Ecran gestion des employés\n");
        print("6 - Ecran gestion de la réserve d'aliments\n\n");

        Scanner menuScanner = new Scanner(System.in);
        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                // Appelle la fonction de l'écran de prise de commande
                OrderTakingScreen.tryShowingOrderTakingScreen(menuScanner);
                break;
            case "2":
                // Appelle la fonction de l'écran de cuisine
                KitchenScreen.tryShowingKitchenScreen(menuScanner);
                break;
            case "3":
                // Appelle la fonction de l'écran de bar
                BarScreen.tryShowingBarScreen(menuScanner);
                break;
            case "4":
                // Appelle la fonction de l'écran de monitoring
                // => Suivi des performances du restaurant
                // => Impression de la liste de course
                MonitoringScreen.tryshowingMonitoringScreen(menuScanner);
                break;
            case "5":
                // Appelle la fonction de l'écran de gestion des employés
                EmployeeScreen.showTeamScreen(menuScanner);
                break;
            case "6":
                GestionStockScreen.showGestionScreen(menuScanner);
                // Appelle la fonction de l'écran de gestion de la réserve d'aliments
                break;
            default:
                showMainMenu();
        }
    }

    // pour écrire plus rapidement "System.out.println"
    public static void print(String text) {
        System.out.println(text);
    }

    // Permet de simuler un clear console
    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }

}
