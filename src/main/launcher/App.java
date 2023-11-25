package main.launcher;

import java.util.*;

// Classe principale qui gère le lancement de l'application
public class App {
    public static void main(String[] args) {
        try {
            showMainMenu(); // Affiche le menu principal
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showMainMenu() {
        clearConsole();

        print("==========================================================================");
        print("************************** JRESTO MANAGER PRO® ***************************");
        print("**************************** Portail Employé *****************************");
        print("==========================================================================\n\n");

        print("Quel écran souhaitez-vous afficher?\n\n");
        print("1- Ecran prise de commande\n");
        print("2- Ecran cuisine\n");
        print("3- Ecran bar\n");
        print("4- Ecran Monitoring\n");
        print("5- Ecran gestion des employés\n");
        print("6- Ecran gestion de la réserve d'aliments\n\n\n");

        Scanner menuScanner = new Scanner(System.in);
        int choixEcran = menuScanner.nextInt();

        switch (choixEcran) {
            case 1:
                // Appele la fonction pour l'écran de prise de commande
                OrderTakingScreen.tryShowingScreen(menuScanner);
                break;
            case 2:
                // Appeler la fonction pour l'écran de cuisine
                KitchenScreen.tryShowingScreen(menuScanner);
                break;
            case 3:
                // Appeler la fonction pour l'écran de bar
                BarScreen.tryShowingScreen(menuScanner);
                break;
            case 4:
                // Appeler la fonction pour l'écran de monitoring
                // => Suivi des performances du restaurant
                // => Impression de la liste de course
                MonitoringScreen.showMonitoringScreen(menuScanner);
                break;
            case 5:
                // Appeler la fonction pour l'écran de gestion des employés
                TeamScreen.showTeamScreen(menuScanner);
                break;
            case 6:
                // Appeler la fonction pour l'écran de gestion de la réserve d'aliments
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
