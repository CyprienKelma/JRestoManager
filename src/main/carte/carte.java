package main.carte;

import java.io.IOException;
import java.util.*;
import main.place.Transaction;
import main.launcher.order.OrderTakingScreen;

public class Carte {

    // Liste des plats
    private static List<String> liste1 = new ArrayList<>() {
        {
            add("Salade tomate");
            add("Salade simple");
            add("Potage oignon");
            add("Potage tomate");
            add("Potage champignon");
            add("Burger");
            add("Burger sans T");
            add("Burger sans TS");
            add("Pizza fromage");
            add("Pizza champignon");
            add("Pizza Chorizo");
        }
    };

    // Liste des prix de plats
    private static List<String> liste2 = new ArrayList<>() {
        {
            add("9 euros");
            add("9 euros");
            add("8 euros");
            add("8 euros");
            add("8 euros");
            add("15 euros");
            add("15 euros");
            add("15 euros");
            add("12 euros");
            add("12 euros");
            add("12 euros");
        }
    };

    // Liste des ingrédents des plats
    private static List<String> liste3 = new ArrayList<>() {
        {
            add("Salade Tomate");
            add("Salade");
            add("3*Oignon");
            add("3*Tomate");
            add("3*Champignon");
            add("Pain Salade Tomate Viande");
            add("Pain Salade Viande");
            add("Pain Viande");
            add("Pate Tomate Fromage");
            add("Pate Tomate Fromage Champignon");
            add("Pate Tomate Fromage Chorizo");
        }
    };

    // Liste des boissons
    private static List<String> liste4 = new ArrayList<>() {
        {
            add("Limonade");
            add("Cidre doux");
            add("Biere sans alcool");
            add("Jus de Fruits");
            add("Verre d'eau");
        }
    };

    // Liste des prix de boissons
    private static List<String> liste5 = new ArrayList<>() {
        {
            add("4 euros");
            add("5 euros");
            add("5 euros");
            add("1 euros");
            add("0 euros");
        }
    };

    private static List<String> plat = new ArrayList<>();
    private static List<String> boisson = new ArrayList<>();

    // HashMap temporaire qui à pour but de stocker les quantités retirées lors
    // d'une commande à l'aide de l'option "Reinitialiser la selection des plats"
    // Le but est de pouvoir réintégrer les quantités retirées dans le stock
    private static Map<String, Integer> quantitesRetirées = new HashMap<>();

    public void initialiserPlat() {
        plat.addAll(liste1);
        plat.addAll(liste2);
        plat.addAll(liste3);
    }

    public void initialiserBoisson() {
        boisson.addAll(liste4);
        boisson.addAll(liste5);
    }

    // Obtient les ingrédients des plats avec leur quantité dans une Map
    public static Map<String, Integer> getIngredients(String plat) {
        Map<String, Integer> ingredients = new HashMap<>();

        int indexPlat = -1;
        for (int i = 0; i < liste1.size(); i++) {
            if (liste1.get(i).equals(plat)) {
                indexPlat = i;
                break;
            }
        }

        if (indexPlat != -1) {
            String[] ingredientList = liste3.get(indexPlat).split("\\s+");
            for (String ingredientWithQuantity : ingredientList) {
                String[] parts = ingredientWithQuantity.split("\\*");
                String nomIngredient = parts.length == 2 ? parts[1] : ingredientWithQuantity; // Utiliser le nom complet
                                                                                              // si pas de quantité
                                                                                              // spécifiée
                int quantite = parts.length == 2 ? Integer.parseInt(parts[0]) : 1;
                ingredients.put(nomIngredient, quantite);
            }
        }

        return ingredients;
    }

    // Obtient le prix des plats
    public static double getPrixPlat(String plat) {
        int indexPlat = liste1.indexOf(plat);
        if (indexPlat != -1 && indexPlat < liste2.size()) {
            return Double.parseDouble(liste2.get(indexPlat).replaceAll("[^\\d.]", ""));
        }
        return 0;
    }

    // obtient le prix des boissons
    public static double getPrixBoisson(String boisson) {
        int indexBoisson = liste4.indexOf(boisson);
        if (indexBoisson != -1 && indexBoisson < liste5.size()) {
            return Double.parseDouble(liste5.get(indexBoisson).replaceAll("[^\\d.]", ""));
        }
        return 0; // or throw an exception indicating that the price is not found
    }

    private Scanner scanner;

    public static void passerCommandePlats(Scanner menuScanner, Transaction transaction) throws IOException {
        int choix;
        do {
            clearConsole();
            print("==========================================================================");
            print("PRISE DE COMMANDE :\n");
            print("--------------------------------------------------------------------------");
            print("Table n°" + transaction.getTable().getNumero() + " : "
                    + transaction.getState().getDescription());
            print("--------------------------------------------------------------------------\n");

            print("Plats commandés :");
            afficherCommande(transaction);
            System.out.println("\n1 - Ajouter un plat à la commande");
            System.out.println("2 - Fin d'ajout des plats\n");
            choix = menuScanner.nextInt();
            String strChoice = Integer.toString(choix);

            try {
                switch (strChoice) {
                    case "1":
                        // Vérifie en temps réel si les plats sont disponibles
                        List<Aliment> stock1 = Stock.lireFichier("src\\main\\data\\stock.txt");
                        afficherPlatsNonDisponibles(liste1, stock1);
                        // Affiche ceux qui le sont et ceux qui ne le sont pas
                        List<String> platsDisponibles = afficherPlatsDisponibles(liste1, stock1);
                        commanderPlat(platsDisponibles, transaction);
                        break;
                    case "2":
                        // Sauvegarde les plats de la commande dans la transaction dès qu'on reveint
                        // sur l'écran de commande générale (OrderTakingScreen.takeCommand)
                        transaction.getCommandeDemandé()
                                .setPlatsSelectionnes(new HashMap<>(transaction.getCommandeDemandé().getPlats()));

                        OrderTakingScreen.takeCommand(menuScanner, transaction.getServeurAssociate(), transaction);
                        break;
                    default:
                        passerCommandePlats(menuScanner, transaction);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (choix != 2);
    }

    // Affiche les plats non disponibles,
    // compare les ingrédients nécessaires avec les ingrédients disponibles dans le
    // stock
    public static void afficherPlatsNonDisponibles(List<String> plats, List<Aliment> stock) {
        clearConsole();
        print("==========================================================================");
        print("PRISE DE COMMANDE :\n");
        print("--------------------------------------------------------------------------");
        print("Plats non disponibles :");
        print("--------------------------------------------------------------------------");

        for (String plat : plats) {
            // On récupère les ingrédients du plat
            Map<String, Integer> ingredients = getIngredients(plat);
            boolean platDisponible = true;

            // On vérifie pour chaque ingrédients s'ils sont présents dans le stock
            for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
                String ingredientNom = entry.getKey();
                int quantiteRequise = entry.getValue();

                // On vérifie si l'ingrédient est présent dans le stock
                boolean ingredientPresent = stock.stream()
                        .anyMatch(a -> a.getNom().equals(ingredientNom) && a.getQuantite() >= quantiteRequise);

                if (!ingredientPresent) {
                    platDisponible = false;
                    break; // Ici pour arrêter la boucle dès qu'un ingrédient n'est pas disponible
                }
            }

            if (!platDisponible) {
                System.out.println(plat);
            }
        }
    }

    // Affiche les plats disponibles,
    // compare les ingrédients nécessaires avec les ingrédients disponibles dans le
    // stock
    public static List<String> afficherPlatsDisponibles(List<String> plats, List<Aliment> stock) {
        List<String> platsDisponibles = new ArrayList<>();
        print("--------------------------------------------------------------------------");
        print("Plats disponibles :");
        print("--------------------------------------------------------------------------");

        int numeroPlatDisponible = 1;

        for (int i = 0; i < plats.size(); i++) {
            String plat = plats.get(i);
            Map<String, Integer> ingredients = getIngredients(plat);
            boolean platDisponible = true;

            for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
                String ingredientNom = entry.getKey();
                int quantiteRequise = entry.getValue();

                boolean ingredientPresent = stock.stream()
                        .anyMatch(a -> a.getNom().equals(ingredientNom) && a.getQuantite() >= quantiteRequise);

                if (!ingredientPresent) {
                    platDisponible = false;
                    break;
                }
            }

            if (platDisponible) {
                System.out.println(numeroPlatDisponible + ". " + plat);
                platsDisponibles.add(plat); // Ajouter le plat à la liste des plats disponibles
                numeroPlatDisponible++;
            }
        }

        return platsDisponibles;
    }

    // Commander les plats et vérifie si possible
    private static void commanderPlat(List<String> platsDisponibles, Transaction selectedTransaction) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nVeuillez entrer le numéro du plat à ajouter : \n\n");

            // Vérifier si l'entrée est un entier
            if (scanner.hasNextInt()) {
                int numeroPlat = scanner.nextInt();

                if (numeroPlat >= 1 && numeroPlat <= platsDisponibles.size()) {
                    String platChoisi = platsDisponibles.get(numeroPlat - 1);

                    Map<String, Integer> ingredients = getIngredients(platChoisi);

                    try {
                        // Retirer les ingrédients du plat choisi du stock
                        Stock.retirerAliment("src\\main\\data\\stock.txt", ingredients);

                        // Ajouter le plat à la commande avec une quantité de 1 par défaut
                        selectedTransaction.getCommandeDemandé().addPlats(platChoisi, 1);

                        System.out.println("Le plat a été ajouté à la commande.");
                        break; // Sortir de la boucle une fois que le plat a été ajouté avec succès
                    } catch (IOException e) {
                        System.out.println("Erreur lors de la mise à jour du stock : " + e.getMessage());
                    }
                } else {
                    System.out.println("Numéro de plat invalide. Veuillez réessayer.");
                }
            } else {
                System.out.println("Veuillez entrer un numéro de plat valide. Veuillez réessayer.");
                scanner.next(); // Effacer l'entrée incorrecte du scanner
            }
        }
    }

    // Fonction qui permet de selectionner les boissons
    public static void passerCommandeBoissons(Scanner menuScanner, Transaction transaction) {

        int choix;

        do {
            clearConsole();
            print("==========================================================================");
            print("PRISE DE COMMANDE :\n");
            print("--------------------------------------------------------------------------");
            print("Table n°" + transaction.getTable().getNumero() + " : "
                    + transaction.getState().getDescription());
            print("--------------------------------------------------------------------------\n");

            print("Boissons commandés :");
            afficherCommandeBoisson(transaction);
            System.out.println("\n1 - Ajouter une boissons à la commande");
            System.out.println("2 - Réinitialiser la selection des boissons");
            System.out.println("\n3 - Fin d'ajout des boissons\n\n");
            choix = menuScanner.nextInt();
            String strChoice = Integer.toString(choix);

            try {
                switch (strChoice) {
                    case "1":
                        // Vérifie en temps réel si les bdoissons sont disponibles
                        List<Boisson> stock = StockDrink.lireFichier("src\\main\\data\\stockDrink.txt");
                        afficherBoissonsNonDisponibles(liste4, stock);
                        // Affiche ceux qui le sont et ceux qui ne le sont pas
                        List<String> boissonsDisponibles = afficherBoissonsDisponibles(liste4, stock);
                        commanderBoisson(boissonsDisponibles, transaction);
                        break;
                    case "2":

                        // réinitialisation de la selection des plats
                        for (Map.Entry<String, Integer> entry : transaction.getCommandeDemandé().getBoissons()
                                .entrySet()) {
                            String boissons = entry.getKey();
                            int quantite = entry.getValue();
                            quantitesRetirées.put(boissons, quantite);
                        }

                        try {
                            // Pour réinitialiser le stock
                            StockDrink.retournerBoissons("src\\main\\data\\stockDrink.txt", quantitesRetirées);
                        } catch (IOException e) {
                            System.out.println("Erreur lors de la mise à jour du stock : " + e.getMessage());
                        }

                        // Comma ça, en cas de faute de frappe, on peux réinitialiser la selection
                        // sans casser la transaction
                        transaction.getCommandeDemandé().clearBoissons();

                        passerCommandeBoissons(menuScanner, transaction);
                        break;
                    case "3":
                        // Sauvegarde les boissons de la commande dans la transaction dès qu'on reveint
                        // sur l'écran de commande générale (OrderTakingScreen.takeCommand)
                        transaction.getCommandeDemandé()
                                .setBoissonsSelectionnes(new HashMap<>(transaction.getCommandeDemandé().getBoissons()));

                        OrderTakingScreen.takeCommand(menuScanner, transaction.getServeurAssociate(), transaction);
                        break;
                    default:
                        passerCommandeBoissons(menuScanner, transaction);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace(); // Gérer l'exception de manière appropriée en fonction de vos besoins
            }
        } while (choix != 2);
    }

    // Affiche les boisons non disponibles,
    // compare les ingrédients nécessaires avec les ingrédients disponibles dans le
    // stock
    public static void afficherBoissonsNonDisponibles(List<String> boissons, List<Boisson> stock) {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\nBoissons non disponibles :");
        System.out.println("--------------------------------------------------------------------------");

        for (String boisson : boissons) {
            // On vérifie si la boisson est présente dans le stock à l'aide d'un stream
            boolean boissonPresente = stock.stream()
                    .anyMatch(b -> b.getNom().equals(boisson) && b.getQuantite() > 0);

            if (!boissonPresente) {
                System.out.println(boisson);
            }
        }
    }

    // Affiche les boissons disponibles,
    // compare les ingrédients nécessaires avec les ingrédients disponibles dans le
    // stock
    public static List<String> afficherBoissonsDisponibles(List<String> boissons, List<Boisson> stock) {
        List<String> boissonsDisponibles = new ArrayList<>();
        print("--------------------------------------------------------------------------");
        print("Boissons disponibles :");
        print("--------------------------------------------------------------------------");

        int index = 1;

        for (Boisson boisson : stock) {
            if (boisson.getQuantite() > 0) {
                System.out.println(index + ". " + boisson.getNom());
                index += 1;
                boissonsDisponibles.add(boisson.getNom());
            }
        }
        return boissonsDisponibles;
    }

    // Commander les boissons et vérifie si possible
    public static void commanderBoisson(List<String> boissonsDisponibles, Transaction selectedTransaction) {
        System.out.println("\nVeuillez entrer le numéro de la boisson à ajouter : ");
        Scanner scanner = new Scanner(System.in);
        int numeroBoisson = scanner.nextInt();

        if (numeroBoisson >= 1 && numeroBoisson <= boissonsDisponibles.size()) {
            String boissonChoisie = boissonsDisponibles.get(numeroBoisson - 1);

            try {
                // On appelle la fonction qui permet de retirer les boissons du stock
                StockDrink.retirerBoisson("src\\main\\data\\stockDrink.txt",
                        Collections.singletonMap(boissonChoisie, 1));

                // Ajoute la boisson à la commande avec une quantité de 1 par défaut
                selectedTransaction.getCommandeDemandé().addBoissons(boissonChoisie, 1);

                System.out.println("La boisson a été ajoutée à la commande.");
            } catch (IOException e) {
                System.out.println("Erreur lors de la mise à jour du stock : " + e.getMessage());
            }
        } else {
            System.out.println("Numéro de boisson invalide.");
        }
    }

    // affiche les commandes des plats
    public static void afficherCommande(Transaction selectedTransaction) {
        Map<String, Integer> items = selectedTransaction.getCommandeDemandé().getPlats();

        double total = 0;
        System.out.println("\n");
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String plat = entry.getKey();
            int quantite = entry.getValue();

            // Trouver l'index du plat dans la liste1 pour obtenir le prix correspondant
            // dans la liste2
            int indexPlat = liste1.indexOf(plat);
            if (indexPlat != -1 && indexPlat < liste2.size()) {
                double prixUnitaire = Double.parseDouble(liste2.get(indexPlat).replaceAll("[^\\d.]", ""));
                double prixTotal = prixUnitaire * quantite;

                System.out.println("\t" + plat + ": " + quantite + " portions | Coût : " + prixTotal + " euros");

                // Ajouter le coût total du plat à la somme totale
                total += prixTotal;
            } else {
                System.out.println("E\n\trreur : Prix non trouvé pour le plat " + plat);
            }
        }

        System.out.println("\nCoût total de la commande : " + total + " euros");
    }

    // Retourne le prix total des plats commandés
    public static double affichertotalPlatCommande(Transaction selectedTransaction) {
        Map<String, Integer> items = selectedTransaction.getCommandeReçu().getPlats();

        double total = 0;

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String plat = entry.getKey();
            int quantite = entry.getValue();

            // Trouver l'index du plat dans la liste1 pour obtenir le prix correspondant
            // dans la liste2
            int indexPlat = liste1.indexOf(plat);
            if (indexPlat != -1 && indexPlat < liste2.size()) {
                double prixUnitaire = Double.parseDouble(liste2.get(indexPlat).replaceAll("[^\\d.]", ""));
                double prixTotal = prixUnitaire * quantite;
                // Ajouter le coût total du plat à la somme totale
                total += prixTotal;
            } else {
                System.out.println("\nErreur : Prix non trouvé pour le plat suivant : " + plat);
            }
        }

        return total;
    }

    // Affiche la commande des boissons commandés
    public static void afficherCommandeBoisson(Transaction selectedTransaction) {

        Map<String, Integer> items = selectedTransaction.getCommandeDemandé().getBoissons();

        double total = 0;
        System.out.println("\n");
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String boisson = entry.getKey();
            int quantite = entry.getValue();

            // Trouver l'index du plat dans la liste1 pour obtenir le prix correspondant
            // dans la liste2
            int indexBoisson = liste4.indexOf(boisson);
            if (indexBoisson != -1 && indexBoisson < liste5.size()) {
                double prixUnitaire = Double.parseDouble(liste5.get(indexBoisson).replaceAll("[^\\d.]", ""));
                double prixTotal = prixUnitaire * quantite;

                System.out.println("\t" + boisson + ": " + quantite + " | Coût : " + prixTotal + " euros");

                // Ajouter le coût total du plat à la somme totale
                total += prixTotal;
            } else {
                System.out.println("\nErreur : Prix non trouvé pour la boisson suivante : " + boisson);
            }
        }

        System.out.println("\nCoût total des boisssons : " + total + " euros");
    }

    // Retourne le prix total des plats commandés
    public static double affichertotalBoissonCommande(Transaction selectedTransaction) {
        Map<String, Integer> items = selectedTransaction.getCommandeReçu().getBoissons();

        double total = 0;
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String boisson = entry.getKey();
            int quantite = entry.getValue();

            // Trouver l'index de la boisson dans la liste4 pour obtenir le prix
            // correspondant dans la liste5
            int indexBoisson = liste4.indexOf(boisson);
            if (indexBoisson != -1 && indexBoisson < liste5.size()) {
                double prixUnitaire = Double.parseDouble(liste5.get(indexBoisson).replaceAll("[^\\d.]", ""));
                double prixTotal = prixUnitaire * quantite;

                // Ajouter le coût total de la boisson à la somme totale
                total += prixTotal;
            } else {
                System.out.println("\nErreur : Prix non trouvé pour la boisson suivante : " + boisson);
            }
        }

        return total;
    }

    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }

}
