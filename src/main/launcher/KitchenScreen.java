package main.launcher;

import java.util.Scanner;

import main.place.Restaurant;

// Classe qui gère l'écran des cuisiniers (2)
public class KitchenScreen {

    KitchenScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires qui ne
        // contiennent
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    // Fonction qui tente d'afficher l'écran des cuisiniers
    // - Si l'équipe n'est pas encore créée, indique qu'il faut d'abord en créer une
    // - Si l'équipe est créée, affiche l'écran de selection du cuisinier
    // (showKitchenScreen)
    public static void tryShowingOrderTakingScreen(Scanner menuScanner) {
        if (!Restaurant.isOpen()) {
            App.clearConsole();
            print("==========================================================================\n");
            print("Accés refusé : Le restaurant n'est pas encore ouvert !\n");
            print("Pour préparer des plats, votre manager doit d'abord créer une équipe, puis ouvrir le restaurant.\n");
            print("1 - Retour au menu principal\n");

            String choixEcran = menuScanner.next();

            if (choixEcran.equals("1")) {
                App.showMainMenu();
            } else {
                tryShowingOrderTakingScreen(menuScanner);
            }
        }
    }

    public static void showKitchenScreen(Scanner menuScanner) {
        print("==========================================================================\n");
        print("Quel cuisinier êtes-vous ?\n");

        print("1 - " + Restaurant.getEquipeActuelle().getCuisinier1().getNom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier1().getPrenom());

        print("2 - " + Restaurant.getEquipeActuelle().getCuisinier2().getNom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier2().getPrenom());

        print("3 - " + Restaurant.getEquipeActuelle().getCuisinier3().getNom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier3().getPrenom());

        print("4 - " + Restaurant.getEquipeActuelle().getCuisinier4().getNom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier4().getPrenom());

        print("\n5- Retour au menu principal\n");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                // Appelle la fonction de l'écran de prise de commande
                // TODO : Appeler la fonction de l'écran de prise de commande
                break;
            case "2":
                // Appelle la fonction de l'écran de prise de commande
                // TODO : Appeler la fonction de l'écran de prise de commande
                break;
            case "3":
                // Appelle la fonction de l'écran de prise de commande
                // TODO : Appeler la fonction de l'écran de prise de commande
                break;
            case "4":
                // Appelle la fonction de l'écran de prise de commande
                // TODO : Appeler la fonction de l'écran de prise de commande
                break;
            case "5":
                App.showMainMenu();
                break;
            default:
                showKitchenScreen(menuScanner);
                break;
        }
    }

    public static void print(String text) {
        System.out.println(text);
    }
}