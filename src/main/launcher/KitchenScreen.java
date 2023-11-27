package main.launcher;

import java.util.Scanner;

// Classe qui gère l'écran des cuisiniers (2)
public class KitchenScreen {

    private KitchenScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires qui ne contiennent 
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    // Vérifie si les conditions sont réunies pour afficher l'écran des cuinisiers
    // (ex : avoir une équipe formé de 4 cuisiniers comme demandé dans le sujet)
    public static void tryShowingScreen(Scanner menuScanner) {
        // TODO: Implémenter cette fonction dès que la classe "Equipe" sera implémentée

        // Pour l'instant rediriger vers l'écran de cuisine
        showKitchenScreen(menuScanner);
    }

    public static void showKitchenScreen(Scanner menuScanner) {

    }

    public static void print(String text) {
        System.out.println(text);
    }
}