package main.launcher;

import java.util.Scanner;

// Classe qui gère l'écran de prise de commande (1)
public class OrderTakingScreen {

    private OrderTakingScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Ce sont des classes utilitaires qui contiennent que des méthodes statiques
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    public static void tryShowingScreen(Scanner menuScanner) {
        // TODO: Implementer cette fonction dès que la classe "Equipe" sera implémentée

        // Pour l'instant rediriger vers l'écran de prise de commande
        showOrderTakingScreen(menuScanner);
    }

    public static void showOrderTakingScreen(Scanner menuScanner) {
        print("==========================================================================\n");
        print("Quel serveur êtes-vous ?\n");
        print("1- Serveur 1"); // TODO: Importer les serveurs de l'Equipe actuelle
        print("2- Serveur 2");
        print("\n3- Retour au menu principal\n");
        int choixEcran = menuScanner.nextInt();

        // TODO : Implémenter la suite de la fonction dès que la classe "Equipe" sera
        // implémentée
    }

    public static void print(String text) {
        System.out.println(text);
    }
}