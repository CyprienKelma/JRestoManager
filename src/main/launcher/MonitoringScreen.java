package main.launcher;

import java.util.Scanner;

public class MonitoringScreen {

    private MonitoringScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Ce sont des classes utilitaires qui contiennent que des méthodes statiques
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    public static void showMonitoringScreen(Scanner menuScanner) {

    }

    public static void print(String text) {
        System.out.println(text);
    }
}