package main.launcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.carte.Aliment;
import main.carte.Boisson;
import main.carte.Stock;
import main.carte.StockDrink;
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
                showGestionBoissonScreen(menuScanner);
                break;
            default:
                tryShowingGestionScreen(menuScanner);
        }
    }
//----------------------------------------PLATS----------------------------------------------------------

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
                int quantiteAliment;
                do {
                    // Assurez-vous que la quantité est positive
                    quantiteAliment = menuScanner.nextInt();
                    if (quantiteAliment <= 0) {
                        print("La quantité doit être positive. Veuillez réessayer.\n");
                    }
                } while (quantiteAliment <= 0);

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
                    // Nettoyer le tampon avant de lire le nom de l'aliment
                    menuScanner.nextLine();
    
                    // Demander à l'utilisateur le nom de l'aliment à retirer
                    print("Entrez le nom de l'aliment à retirer : \n");
                    nomAliment = menuScanner.nextLine();
    
                    // Vérifier si l'aliment est dans la liste (en comparant le nom complet)
                    for (Aliment aliment : listeAliments) {
                        // Utilisez equalsIgnoreCase pour la comparaison insensible à la casse
                        if (aliment.getNom().equalsIgnoreCase(nomAliment)) {
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
                int quantiteAliment;
                do {
                    // Assurez-vous que la quantité est positive et ne dépasse pas la quantité en stock
                    quantiteAliment = menuScanner.nextInt();
                    int quantiteEnStock = getQuantiteEnStock(listeAliments, nomAliment);
                    if (quantiteAliment <= 0 || quantiteAliment > quantiteEnStock) {
                        print("La quantité doit être positive et ne pas dépasser la quantité en stock. Veuillez réessayer.\n");
                    }
                } while (quantiteAliment <= 0);
    
                // Créer une Map avec le nom de l'aliment et la quantité à retirer
                Map<String, Integer> ingredientsAretirer = Map.of(nomAliment, quantiteAliment);
    
                // Appel de la fonction pour retirer l'aliment du stock
                Stock.retirerAliment("src\\main\\data\\stock.txt", ingredientsAretirer);
    
                RetirerAlimentPlatScreen(menuScanner);
                break;
            default:
                showGestionPlatScreen(menuScanner);
        }
    }
//--------------------------------------------------------------------------------------------------------

//----------------------------------------Boissson--------------------------------------------------------

    public static void showGestionBoissonScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("Menu de Gestion du Stock des aliments pour les boisons : \n");
        print("--------------------------------------------------------------------------");
        print("                   Que souhaitez-vous faire ?");
        print("--------------------------------------------------------------------------\n");
        print("0 - Retour au menu gestion de stock\n");
        print("1 - Afficher le Stock des aliments pour les boissons\n");
        print("2 - Ajouter des aliments pour les boissons\n");
        print("3 - Retirer des aliments pour les boissonS\n");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "0":
                // Retour du screen
                showGestionScreen(menuScanner);
                break;
            case "1":
                // Appelle fontion pour afficher stock Aliment Boisson
                showAlimentBoissonScreen(menuScanner);
                break;
            case "2":
                // Appelle fontion pour ajouter Aliment Boisson
                AjouterAlimentBoissonScreen(menuScanner);
                break;
            case "3":
                // Appelle fontion pour retirer Aliment Boisson
                RetirerAlimentBoissonScreen(menuScanner);
                break;
            default:
                showGestionScreen(menuScanner);
        }
    }

    public static void showAlimentBoissonScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("Menu de Gestion du Stock des aliments pour les boissons : \n");
        print("--------------------------------------------------------------------------");
        print("                  Affichage des aliments des boissons");
        print("--------------------------------------------------------------------------\n");
 
        // Utilisation de la fonction lireFichier de la classe Stock pour récupérer la liste d'aliments
        List<Boisson> listeAliments = StockDrink.lireFichier("src\\main\\data\\stockDrink.txt");

        // Affichage des aliments
        for (Boisson aliment : listeAliments) {
            System.out.println(aliment.getNom() + ": " + aliment.getQuantite());
        }

        print("\n--------------------------------------------------------------------------\n");
        print("0 - Retour au menu gestion de stock des aliments des boissons\n");
        print("1 - Réafficher le stock des aliments des boissons\n");
        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "0":
                // Retour du screen
                showGestionBoissonScreen(menuScanner);
                break;
            case "1":
                // Rétour a la fonction
                showAlimentBoissonScreen(menuScanner);
                break;
            default:
                showGestionBoissonScreen(menuScanner);
        }
    }

    public static void AjouterAlimentBoissonScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("Menu de Gestion du Stock des aliments pour les boissons : \n");
        print("--------------------------------------------------------------------------");
        print("                  Ajouter des aliments des boissons");
        print("--------------------------------------------------------------------------\n");
        print("0 - Retour au menu gestion de stock des aliments des boissons\n");
        print("1 - Ajouter des aliments au stock des aliments des boissons\n");
    
        String choixEcran = menuScanner.next();
        switch (choixEcran) {
            case "0":
                // Retour du screen
                showGestionBoissonScreen(menuScanner);
                break;
            case "1":
    
                // Utilisation de la fonction lireFichier de la classe Stock pour récupérer la liste d'aliments
                List<Boisson> listeAliments = StockDrink.lireFichier("src\\main\\data\\stockDrink.txt");
                print("\n--------------------------------------------------------------------------\n");
                // Affichage des aliments
                for (Boisson aliment : listeAliments) {
                    System.out.println(aliment.getNom() + ": " + aliment.getQuantite());
                }
                print("\n--------------------------------------------------------------------------\n");
                boolean alimentExiste = false;
                String nomAliment;
                do {
                    // Nettoyer le tampon avant de lire le nom de l'aliment
                    menuScanner.nextLine();

                    // Demander à l'utilisateur le nom de l'aliment à ajouter
                    print("Entrez le nom de l'aliment à ajouter : \n");
                    nomAliment = menuScanner.nextLine();

                    // Vérifier si l'aliment est dans la liste (en comparant le nom complet)
                    for (Boisson aliment : listeAliments) {
                        // Utilisez equalsIgnoreCase pour la comparaison insensible à la casse
                        if (aliment.getNom().equalsIgnoreCase(nomAliment)) {
                            alimentExiste = true;
                            break;
                        }
                    }

                    if (!alimentExiste) {
                        print("Cet aliment n'est pas dans la liste. Veuillez réessayer.\n");
                    }

                } while (!alimentExiste);
    
                print("\nEntrez la quantité de l'aliment : \n");
                int quantiteAliment;
                do {
                    // Assurez-vous que la quantité est un entier positif
                    try {
                        quantiteAliment = menuScanner.nextInt();
                        if (quantiteAliment <= 0) {
                            print("La quantité doit être positive. Veuillez réessayer.\n");
                        }
                    } catch (InputMismatchException e) {
                        print("Veuillez entrer un nombre entier. Veuillez réessayer.\n");
                        quantiteAliment = -1; // Définir une valeur par défaut pour éviter une boucle infinie
                    }
                } while (quantiteAliment <= 0);
    
                // Créer une instance de Boisson avec les informations fournies
                Boisson nouvelAliment = new Boisson(nomAliment, quantiteAliment);
    
                // Appel de la fonction pour ajouter l'aliment au stock
                StockDrink.ajouterBoisson("src\\main\\data\\stockDrink.txt", nouvelAliment);
    
                AjouterAlimentBoissonScreen(menuScanner);
                break;
            default:
                showGestionBoissonScreen(menuScanner);
        }
    }
    

    public static void RetirerAlimentBoissonScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("Menu de Gestion du Stock des aliments pour les boissons : \n");
        print("--------------------------------------------------------------------------");
        print("                  Retirer des aliments des boissons");
        print("--------------------------------------------------------------------------\n");
        print("0 - Retour au menu gestion de stock des aliments des boissons\n");
        print("1 - Retirer des aliments au stock des aliments des boissons\n");

        String choixEcran = menuScanner.next();
        switch (choixEcran) {
            case "0":
                // Retour du screen
                showGestionBoissonScreen(menuScanner);
                break;
            case "1":
                // Utilisation de la fonction lireFichier de la classe Stock pour récupérer la liste d'aliments
                List<Boisson> listeAliments = StockDrink.lireFichier("src\\main\\data\\stockDrink.txt");
                print("\n--------------------------------------------------------------------------\n");
                // Affichage des aliments
                for (Boisson aliment : listeAliments) {
                    System.out.println(aliment.getNom() + ": " + aliment.getQuantite());
                }
                print("\n--------------------------------------------------------------------------\n");
                boolean alimentExiste = false;
                String nomAliment;
                do {
                    // Nettoyer le tampon avant de lire le nom de l'aliment
                    menuScanner.nextLine();

                    // Demander à l'utilisateur le nom de l'aliment à retirer
                    print("Entrez le nom de l'aliment à retirer : \n");
                    nomAliment = menuScanner.nextLine();

                    // Vérifier si l'aliment est dans la liste (en comparant le nom complet)
                    for (Boisson aliment : listeAliments) {
                        // Utilisez equalsIgnoreCase pour la comparaison insensible à la casse
                        if (aliment.getNom().equalsIgnoreCase(nomAliment)) {
                            alimentExiste = true;
                            break;
                        }
                    }

                    if (!alimentExiste) {
                        print("Cet aliment n'est pas dans la liste. Veuillez réessayer.\n");
                    }

                } while (!alimentExiste);
    
                print("\nEntrez la quantité de l'aliment à retirer : \n");
                int quantiteAliment;
                do {
                    // Assurez-vous que la quantité est un entier positif
                    try {
                        quantiteAliment = menuScanner.nextInt();

                        // Vérifier si la quantité à retirer est supérieure à la quantité en stock
                        int quantiteEnStock = getBoissonQuantiteEnStock(listeAliments, nomAliment);
                        if (quantiteAliment > quantiteEnStock) {
                            print("La quantité à retirer ne peut pas être supérieure à la quantité en stock. Veuillez réessayer.\n");
                            continue;
                        }

                        if (quantiteAliment <= 0) {
                            print("La quantité doit être positive. Veuillez réessayer.\n");
                        }
                    } catch (InputMismatchException e) {
                        print("Veuillez entrer un nombre entier. Veuillez réessayer.\n");
                        quantiteAliment = -1; // Définir une valeur par défaut pour éviter une boucle infinie
                    }
                } while (quantiteAliment <= 0);
    
                // Créer une instance de HashMap et retirer l'aliment à retirer
                Map<String, Integer> alimentARetirerMap = new HashMap<>();
                alimentARetirerMap.put(nomAliment, quantiteAliment);

    
                // Appel de la fonction pour retirer l'aliment du stock
                StockDrink.retirerBoisson("src\\main\\data\\stockDrink.txt", alimentARetirerMap);
    
                RetirerAlimentBoissonScreen(menuScanner);
                break;
            default:
                showGestionBoissonScreen(menuScanner);
        }
    }
//--------------------------------------------------------------------------------------------------------

    public static void print(String text) {
        System.out.println(text);
    }
    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }
    private static int getBoissonQuantiteEnStock(List<Boisson> listeAliments, String nomAliment) {
        for (Boisson aliment : listeAliments) {
            if (aliment.getNom().equalsIgnoreCase(nomAliment)) {
                return aliment.getQuantite();
            }
        }
        return 0; // Si l'aliment n'est pas trouvé, la quantité en stock est considérée comme 0
    }
    private static int getQuantiteEnStock(List<Aliment> listeAliments, String nomAliment) {
        for (Aliment aliment : listeAliments) {
            if (aliment.getNom().equalsIgnoreCase(nomAliment)) {
                return aliment.getQuantite();
            }
        }
        return 0; // Si l'aliment n'est pas trouvé, la quantité en stock est considérée comme 0
    }
}