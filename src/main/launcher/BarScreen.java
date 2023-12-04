package main.launcher;

import java.util.Scanner;

import main.place.Restaurant;

public class BarScreen {

    private BarScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires qui ne contiennent 
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }


    // Fonction qui tente d'afficher l'écran du barman
    // - Si l'équipe n'est pas encore créée, indique qu'il faut d'abord en créer une
    // - Si l'équipe est créée, affiche l'écran de selection du cuisinier
    // (showBarScreen)
    public static void tryShowingBarScreen(Scanner menuScanner) {
        if (!Restaurant.isOpen()) {
            App.clearConsole();
            print("==========================================================================\n");
            print("Accés refusé : Le restaurant n'est pas encore ouvert !\n");
            print("Pour préparer des boissons, votre manager doit d'abord créer une équipe, puis ouvrir le restaurant.\n");
            print("1 - Retour au menu principal\n");

            String choixEcran = menuScanner.next();

            if (choixEcran.equals("1")) {
                App.showMainMenu();
            } else {
                tryShowingBarScreen(menuScanner);
            }
        }
        showBarScreen(menuScanner);
    }

    public static void showBarScreen(Scanner menuScanner) {

    }

    public static void print(String text) {
        System.out.println(text);
    }
}