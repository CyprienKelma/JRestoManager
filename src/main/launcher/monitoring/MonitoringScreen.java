package main.launcher.monitoring;

import java.io.IOException;
import java.util.Scanner;

import main.place.Restaurant;
import main.launcher.App;

//Classe qui gère l'écran de prise de commande du restaurant (4)
public class MonitoringScreen {

    private MonitoringScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires qui ne contiennent 
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    public static void tryshowingMonitoringScreen(Scanner menuScanner) throws IOException {

        clearConsole();

        print("==========================================================================");
        print("                      AUTENTIFICATION REQUISE\n");
        print("Cette fonctionnalité est réservée aux manager du restaurant.");
        print("Veuillez entrez vos identifiants administrateur pour accéder au menu de monitoring\n\n");
        print("--------------------------------------------------------------------------");
        
        print("Veuillez entrer votre identifiant :\n");
        String userName = menuScanner.next();

        print("Entrez votre mot de passe :\n");
        String password = menuScanner.next();

        if (userName.equals("ADMIN") && password.equals("1234")) {
            showMonitoringScreen(menuScanner);
        } else {
            print("\nIdentifiants incorrects, veuillez réessayer\n");
            print("1 - Réessayer");
            print("2 - Retour au menu principal\n\n");
            String choixEcran = menuScanner.next();
            switch(choixEcran){
                case "1":
                    tryshowingMonitoringScreen(menuScanner);
                    break;
                case "2":
                    App.showMainMenu();
                    break;
                default:
                    tryshowingMonitoringScreen(menuScanner);
                    break;
            }
        }
    }

    public static void showMonitoringScreen(Scanner menuScanner) throws IOException{

        clearConsole();

        print("==========================================================================");
        print("                            ECRAN MONITORING\n");

        print("                        Authentification réussie !");
        print("--------------------------------------------------------------------------");
        print("                  Quel écran souhaitez-vous afficher ?");
        

        // Redirige vers le menu adaptée en fonction de si le restaurant est ouvert ou fermé
        // (divisé en 2 classes différentes pour plus de clarté et d'organisation)
        if(Restaurant.isOpen()){
            OpenedRestaurantMonitoring.showOpenedRestaurantMonitoringScreen(menuScanner);
        } else {
            ClosedRestaurantMonitoring.showClosedRestaurantMonitoringScreen(menuScanner);
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