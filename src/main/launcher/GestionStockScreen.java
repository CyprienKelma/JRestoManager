package main.launcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.carte.Aliment;
import main.carte.Stock;
import main.place.Restaurant;

//Classe qui gère l'écran d
public class GestionStockScreen {

    private GestionStockScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires qui ne contiennent 
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }


    // Fonction qui tente d'afficher l'écran du barman
    // - Si l'équipe n'est pas encore créée, indique qu'il faut d'abord en créer une
    // - Si l'équipe est créée, affiche l'écran de selection du cuisinier
    // (showBarScreen)
    public static void tryShowingGestionScreen(Scanner menuScanner) throws IOException {
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
                tryShowingGestionScreen(menuScanner);
            }
        }
        showGestionScreen(menuScanner);
    }

    public static void showGestionScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("Menu de Gestion du Stock des aliments\n");
        print("--------------------------------------------------------------------------\n");
        print("                   Quel écran souhaitez-vous afficher ?\n");
        print("--------------------------------------------------------------------------\n");
        print("0 - Retour au menu principal\n");
        print("1 - Gestion le Stock des aliments pour les plats\n");
        print("2 - Gestion le Stock des aliments pour les boissons\n");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "0":
                // Retour du screen
                App.showMainMenu();
                break;
            case "1":
                // Appelle fontion pour gestion stock Aliment Plat
                showGestionPlatScreen(menuScanner);
                break;
            case "2":
                // Appelle fontion pour gestion stock Aliment Boissson
                //showGestionBoissonScreen(menuScanner);
                break;
            default:
                tryShowingGestionScreen(menuScanner);
        }
    }

    public static void showGestionPlatScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("Menu de Gestion du Stock des aliments pour les plats : \n");
        print("--------------------------------------------------------------------------");
        print("                   Que souhaitez-vous faire ?");
        print("--------------------------------------------------------------------------\n");
        print("0 - Retour au menu gestion de stock\n");
        print("1 - Afficher le Stock des aliments pour les plats\n");
        print("2 - Ajouter des aliments pour les plats\n");
        print("3 - Retirer des aliments pour les plats\n");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "0":
                // Retour du screen
                showGestionScreen(menuScanner);
                break;
            case "1":
                // Appelle fontion pour afficher stock Aliment Plat
                showAlimentPlatScreen(menuScanner);
                break;
            case "2":
                // Appelle fontion pour ajouter Aliment Plat
                AjouterAlimentPlatScreen(menuScanner);
                break;
            case "3":
                // Appelle fontion pour retirer Aliment Plat
                RetirerAlimentPlatScreen(menuScanner);
                break;
            default:
                showGestionScreen(menuScanner);
        }
    }

    public static void showAlimentPlatScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("Menu de Gestion du Stock des aliments pour les plats : \n");
        print("--------------------------------------------------------------------------");
        print("                  Affichage des aliments des plats");
        print("--------------------------------------------------------------------------\n");
 
        // Utilisation de la fonction lireFichier de la classe Stock pour récupérer la liste d'aliments
        List<Aliment> listeAliments = Stock.lireFichier("src\\main\\data\\stock.txt");

        // Affichage des aliments
        for (Aliment aliment : listeAliments) {
            System.out.println(aliment.getNom() + ": " + aliment.getQuantite());
        }

        print("\n--------------------------------------------------------------------------\n");
        print("0 - Retour au menu gestion de stock des aliments des plats\n");
        print("1 - Réafficher le stock des aliments des plats\n");
        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "0":
                // Retour du screen
                showGestionPlatScreen(menuScanner);
                break;
            case "1":
                // Rétour a la fonction
                showAlimentPlatScreen(menuScanner);
                break;
            default:
                showGestionPlatScreen(menuScanner);
        }
    }

    public static void AjouterAlimentPlatScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("Menu de Gestion du Stock des aliments pour les plats : \n");
        print("--------------------------------------------------------------------------");
        print("                  Ajouter des aliments des plats");
        print("--------------------------------------------------------------------------\n");
        print("0 - Retour au menu gestion de stock des aliments des plats\n");
        print("1 - Ajouter des aliments au stock des aliments des plats\n");

        String choixEcran = menuScanner.next();
        switch (choixEcran) {
            case "0":
                // Retour du screen
                showGestionPlatScreen(menuScanner);
                break;
            case "1":

                // Utilisation de la fonction lireFichier de la classe Stock pour récupérer la liste d'aliments
                List<Aliment> listeAliments = Stock.lireFichier("src\\main\\data\\stock.txt");
                print("\n--------------------------------------------------------------------------\n");
                // Affichage des aliments
                for (Aliment aliment : listeAliments) {
                    System.out.println(aliment.getNom() + ": " + aliment.getQuantite());
                } 
                print("\n--------------------------------------------------------------------------\n");
                boolean alimentExiste = false;
                String nomAliment;
                do {
                    // Demander à l'utilisateur le nom de l'aliment à retirer
                    print("Entrez le nom de l'aliment à retirer : \n");
                    nomAliment = menuScanner.next();

                    // Vérifier si l'aliment est dans la liste
                    for (Aliment aliment : listeAliments) {
                        if (aliment.getNom().equals(nomAliment)) {
                            alimentExiste = true;
                            break;
                        }
                    }

                    if (!alimentExiste) {
                        print("Cet aliment n'est pas dans la liste. Veuillez réessayer.\n");
                    }

                } while (!alimentExiste);

                print("\nEntrez la quantité de l'aliment : \n");
                int quantiteAliment = menuScanner.nextInt();

                // Créer une instance d'Aliment avec les informations fournies
                Aliment nouvelAliment = new Aliment(nomAliment, quantiteAliment);

                // Appel de la fonction pour ajouter l'aliment au stock
                Stock.ajouterAliment("src\\main\\data\\stock.txt", nouvelAliment);

                AjouterAlimentPlatScreen(menuScanner);
                break;
            default:
                showGestionPlatScreen(menuScanner);
        }
    }

    public static void RetirerAlimentPlatScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("Menu de Gestion du Stock des aliments pour les plats : \n");
        print("--------------------------------------------------------------------------");
        print("                  Retirer des aliments des plats");
        print("--------------------------------------------------------------------------\n");
        print("0 - Retour au menu gestion de stock des aliments des plats\n");
        print("1 - Retirer des aliments au stock des aliments des plats\n");

        String choixEcran = menuScanner.next();
        switch (choixEcran) {
            case "0":
                // Retour du screen
                showGestionPlatScreen(menuScanner);
                break;
            case "1":
                // Utilisation de la fonction lireFichier de la classe Stock pour récupérer la liste d'aliments
                List<Aliment> listeAliments = Stock.lireFichier("src\\main\\data\\stock.txt");
                print("\n--------------------------------------------------------------------------\n");
                // Affichage des aliments
                for (Aliment aliment : listeAliments) {
                    System.out.println(aliment.getNom() + ": " + aliment.getQuantite());
                }
                print("\n--------------------------------------------------------------------------\n");
                boolean alimentExiste = false;
                String nomAliment;
                do {
                    // Demander à l'utilisateur le nom de l'aliment à retirer
                    print("Entrez le nom de l'aliment à retirer : \n");
                    nomAliment = menuScanner.next();

                    // Vérifier si l'aliment est dans la liste
                    for (Aliment aliment : listeAliments) {
                        if (aliment.getNom().equals(nomAliment)) {
                            alimentExiste = true;
                            break;
                        }
                    }

                    if (!alimentExiste) {
                        print("Cet aliment n'est pas dans la liste. Veuillez réessayer.\n");
                    }

                } while (!alimentExiste);

                // Demander à l'utilisateur la quantité de l'aliment à retirer
                print("\nEntrez la quantité de l'aliment à retirer : \n");
                int quantiteARetirer = menuScanner.nextInt();

                // Créer une Map avec le nom de l'aliment et la quantité à retirer
                Map<String, Integer> ingredientsAretirer = Map.of(nomAliment, quantiteARetirer);

                // Appel de la fonction pour retirer l'aliment du stock
                Stock.retirerAliment("src\\main\\data\\stock.txt", ingredientsAretirer);

                RetirerAlimentPlatScreen(menuScanner);
                break;
            default:
                showGestionPlatScreen(menuScanner);
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