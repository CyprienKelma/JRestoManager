package main.launcher;

import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        print("Quel écran souhaitez-vous afficher?");
        print("1- Ecran prise de commande");
        print("2- Ecran cuisine");
        print("3- Ecran bar");
        print("4- Ecran Monitoring");

        try (Scanner scanner = new Scanner(System.in)) {
            int choixEcran = scanner.nextInt();

            switch (choixEcran) {
                case 1:
                    // Appeler la fonction pour l'écran de prise de commande
                    break;
                case 2:
                    // Appeler la fonction pour l'écran de cuisine
                    break;
                case 3:
                    // Appeler la fonction pour l'écran de bar
                    break;
                case 4:
                    // Appeler la fonction pour l'écran de monitoring
                    // => Suivi des performances du restaurant
                    // => Impression de la liste de course
                    break;
                case 5:
                    // Appeler la fonction pour l'écran de gestion des employés
                    break;
                case 6:
                    // Appeler la fonction pour l'écran de gestion de la réserve d'aliments
                    break;
                default:
                    System.out.println("Choix invalide");
            }
        }
    }

    public static void print(String message) {
        System.out.println(message);
    }

}
