package main.launcher;

import java.util.*;

import main.launcher.employee.EmployeeScreen;
import main.launcher.employee.SaveEmployee;
import main.place.*;

// Classe principale qui gère le lancement de l'application
public class App {
    public static void main(String[] args) {

        // Charge tout les fichiers de sauvegarde du projet
        loadAllFile();
        try {
            showMainMenu(); // Affiche le menu principal
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadAllFile() {
        SaveEmployee.loadEmployeeListFromTheFile();
    }

    public static void showMainMenu() {
        clearConsole();

        print("==========================================================================");
        print("************************** JRESTO MANAGER PRO® ***************************");
        print("**************************** Portail Employé *****************************");
        print("==========================================================================\n\n");

        print("           Bienvenue dans l'application JRESTO MANAGER PRO® !\n");
        print("                      Etat actuel du restaurant :");
        print("                                 " + (Restaurant.isOpen() ? "OUVERT" : "FERMÉ\n\n"));

        print("--------------------------------------------------------------------------");
        print("                    Quel écran souhaitez-vous afficher?");
        print("--------------------------------------------------------------------------\n");
        print("1 - Ecran prise de commande\n");
        print("2 - Ecran cuisine\n");
        print("3 - Ecran bar\n");
        print("4 - Ecran Monitoring\n");
        print("5 - Ecran gestion des employés\n");
        print("6 - Ecran gestion de la réserve d'aliments\n\n\n");

        Scanner menuScanner = new Scanner(System.in);
        int choixEcran = menuScanner.nextInt();

        switch (choixEcran) {
            case 1:
                // Appelle la fonction de l'écran de prise de commande
                OrderTakingScreen.tryShowingScreen(menuScanner);
                break;
            case 2:
                // Appelle la fonction de l'écran de cuisine
                KitchenScreen.tryShowingScreen(menuScanner);
                break;
            case 3:
                // Appelle la fonction de l'écran de bar
                BarScreen.tryShowingScreen(menuScanner);
                break;
            case 4:
                // Appelle la fonction de l'écran de monitoring
                // => Suivi des performances du restaurant
                // => Impression de la liste de course
                MonitoringScreen.showMonitoringScreen(menuScanner);
                break;
            case 5:
                // Appelle la fonction de l'écran de gestion des employés
                EmployeeScreen.showTeamScreen(menuScanner);
                break;
            case 6:
                // Appelle la fonction de l'écran de gestion de la réserve d'aliments
                break;
            default:
                showMainMenu();
        }
    }

    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }

}
