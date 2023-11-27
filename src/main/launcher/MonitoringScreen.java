package main.launcher;

import java.util.Scanner;

public class MonitoringScreen {

    private MonitoringScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires qui ne contiennent 
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    public static void showMonitoringScreen(Scanner menuScanner) {

    }

    public static void print(String text) {
        System.out.println(text);
    }
}