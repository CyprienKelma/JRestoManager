package main.carte;

import java.io.IOException;
import java.util.*;

import main.place.Transaction;
import main.carte.stock;
import main.launcher.OrderTakingScreen;
import main.staff.Serveur;
import main.carte.*;

public class Carte {

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

    private static List<String> liste4 = new ArrayList<>() {
        {
            add("Limonade");
            add("Cidre doux");
            add("Biere sans alcool");
            add("Jus de Fruits");
            add("Verre d'eau");
        }
    };

    private static List<String> liste5 = new ArrayList<>() {
        {
            add("4 euros");
            add("5 euros");
            add("5 euros");
            add("1 euros");
            add("Gratuit");
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
                String nomIngredient = parts.length == 2 ? parts[1] : ingredientWithQuantity; // Utiliser le nom complet si pas de quantité spécifiée
                int quantite = parts.length == 2 ? Integer.parseInt(parts[0]) : 1;
                ingredients.put(nomIngredient, quantite);
            }
        }
    
        return ingredients;
    }

    private Scanner scanner;

    public static void passerCommandePlats(Scanner menuScanner, Transaction transaction) {
    
        int choix;
        do {
            clearConsole();
            print("==========================================================================");
            print("PRISE DE COMMANDE :\n");
            print("--------------------------------------------------------------------------");
            print("Table n°" + transaction.getTable().getNuméro() + " : "
                    + transaction.getState().getDescription());
            print("--------------------------------------------------------------------------\n");

            print("Plats commandés :");
            afficherCommande(transaction);
            System.out.println("\n1 - Ajouter un plat à la commande");
            System.out.println("2 - Réinitialiser la selection des plats");
            System.out.println("\n3 - Fin d'ajout des plats\n\n");
            choix = menuScanner.nextInt();
            String strChoice = Integer.toString(choix);
    
            try {
                switch (strChoice) {
                    case "1":
                        // Vérifie en temps réel si les plats sont disponibles
                        List<Aliment> stock1 = stock.lireFichier("src\\main\\data\\stock.txt");
                        afficherPlatsNonDisponibles(liste1, stock1);
                        // Affiche ceux qui le sont et ceux qui ne le sont pas
                        List<String> platsDisponibles = afficherPlatsDisponibles(liste1, stock1);
                        commanderPlat(platsDisponibles, transaction);
                        break;
                    case "2":

                        // TODO : Fix la fonction de réinitialisation de la selection des plats
                        // actuellement le stock n'est pas remis à jour

                        for (Map.Entry<String, Integer> entry : transaction.getCommandeDemandé().getPlats().entrySet()) {
                            String plat = entry.getKey();
                            int quantite = entry.getValue();
                            quantitesRetirées.put(plat, quantite);
                        }
                        
                        
                        try {
                            // Réinitialiser le stock
                            stock.retournerAliments("src\\main\\data\\stock.txt", quantitesRetirées);
                        } catch (IOException e) {
                            System.out.println("Erreur lors de la mise à jour du stock : " + e.getMessage());
                        }
                        
                        // Comma ça, en cas de faute de frappe, on peux réinitialiser la selection
                        // sans casser la transaction
                        transaction.getCommandeDemandé().clearPlats();

                        // Vide la liste temporaires des quantités retirées
                        
                        // TODO : Fix la fonction de réinitialisation de la selection des plats
                        //quantitesRetirées.clear();

                        passerCommandePlats(menuScanner, transaction);
                        break;
                    case "3":
                        OrderTakingScreen.takeCommand(menuScanner, transaction.getServeurAssociate(), transaction);
                        break;
                    default:
                        passerCommandePlats(menuScanner, transaction);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace(); // Gérer l'exception de manière appropriée en fonction de vos besoins
            }
        } while (choix != 2);
    }

    public static void afficherPlatsNonDisponibles(List<String> plats, List<Aliment> stock) {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\nPlats non disponibles :");
        System.out.println("--------------------------------------------------------------------------");
        
        for (String plat : plats) {
            Map<String, Integer> ingredients = getIngredients(plat);
            boolean platDisponible = true;

            for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
                String ingredientNom = entry.getKey();
                int quantiteRequise = entry.getValue();

                // Vérifier si l'ingrédient est présent dans le stock
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

    public static List<String> afficherPlatsDisponibles(List<String> plats, List<Aliment> stock) {
        List<String> platsDisponibles = new ArrayList<>();
        System.out.println("\n\tPlats disponibles :");
    
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


    private static void commanderPlat(List<String> platsDisponibles, Transaction selectedTransaction) {
        System.out.println("\nVeuillez entrer le numéro du plat à ajouter : ");
        Scanner scanner = new Scanner(System.in);
        int numeroPlat = scanner.nextInt(); // Utilisez le Scanner de la classe
    
        if (numeroPlat >= 1 && numeroPlat <= platsDisponibles.size()) {
            String platChoisi = platsDisponibles.get(numeroPlat - 1);
    
            Map<String, Integer> ingredients = getIngredients(platChoisi);
    
            try {
                // Retirer les ingrédients du plat choisi du stock
                stock.retirerAliment("src\\main\\data\\stock.txt", ingredients);
    
                // Ajouter le plat à la commande avec une quantité de 1 par défaut
                selectedTransaction.getCommandeDemandé().ajouterPlat(platChoisi, 1);
    
                System.out.println("Le plat à été ajouté à la commande.");
            } catch (IOException e) {
                System.out.println("Erreur lors de la mise à jour du stock : " + e.getMessage());
            }
        } else {
            System.out.println("Numéro de plat invalide.");
        }
    }
    

    // Fonction qui permet de selectionner les boissons
    public static void passerCommandeBoissons(Scanner menuScanner, Transaction transaction){

        int choix;

        do {
            clearConsole();
            print("==========================================================================");
            print("PRISE DE COMMANDE :\n");
            print("--------------------------------------------------------------------------");
            print("Table n°" + transaction.getTable().getNuméro() + " : "
                    + transaction.getState().getDescription());
            print("--------------------------------------------------------------------------\n");

            print("Boissons commandés :");
            afficherCommande(transaction);
            System.out.println("\n1 - Ajouter une boissons à la commande");
            System.out.println("2 - Réinitialiser la selection des boissons");
            System.out.println("\n3 - Fin d'ajout des plats\n\n");
            choix = menuScanner.nextInt();
            String strChoice = Integer.toString(choix);
    
            try {
                switch (strChoice) {
                    case "1":
                        // Vérifie en temps réel si les bdoissons sont disponibles
                        List<Aliment> stock1 = stock.lireFichier("src\\main\\data\\stock.txt");
                        afficherPlatsNonDisponibles(liste1, stock1);
                        // Affiche ceux qui le sont et ceux qui ne le sont pas
                        List<String> platsDisponibles = afficherPlatsDisponibles(liste1, stock1);
                        commanderPlat(platsDisponibles, transaction);
                        break;
                    case "2":

                        // TODO : Fix la fonction de réinitialisation de la selection des plats
                        // actuellement le stock n'est pas remis à jour

                        for (Map.Entry<String, Integer> entry : transaction.getCommandeDemandé().getPlats().entrySet()) {
                            String plat = entry.getKey();
                            int quantite = entry.getValue();
                            quantitesRetirées.put(plat, quantite);
                        }
                        
                        
                        try {
                            // Réinitialiser le stock
                            stock.retournerAliments("src\\main\\data\\stock.txt", quantitesRetirées);
                        } catch (IOException e) {
                            System.out.println("Erreur lors de la mise à jour du stock : " + e.getMessage());
                        }
                        
                        // Comma ça, en cas de faute de frappe, on peux réinitialiser la selection
                        // sans casser la transaction
                        transaction.getCommandeDemandé().clearPlats();

                        // Vide la liste temporaires des quantités retirées
                        
                        // TODO : Fix la fonction de réinitialisation de la selection des plats
                        //quantitesRetirées.clear();

                        passerCommandePlats(menuScanner, transaction);
                        break;
                    case "3":
                        OrderTakingScreen.takeCommand(menuScanner, transaction.getServeurAssociate(), transaction);
                        break;
                    default:
                        passerCommandePlats(menuScanner, transaction);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace(); // Gérer l'exception de manière appropriée en fonction de vos besoins
            }
        } while (choix != 2);
    }


    public static void afficherCommande(Transaction selectedTransaction) {
        Map<String, Integer> items = selectedTransaction.getCommandeDemandé().getPlats();

        double total = 0;
        System.out.println("\n");
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String plat = entry.getKey();
            int quantite = entry.getValue();
    
            // Trouver l'index du plat dans la liste1 pour obtenir le prix correspondant dans la liste2
            int indexPlat = liste1.indexOf(plat);
            if (indexPlat != -1 && indexPlat < liste2.size()) {
                double prixUnitaire = Double.parseDouble(liste2.get(indexPlat).replaceAll("[^\\d.]", ""));
                double prixTotal = prixUnitaire * quantite;
    
                System.out.println("\t"+plat + ": " + quantite + " portions | Coût : " + prixTotal + " euros");
    
                // Ajouter le coût total du plat à la somme totale
                total += prixTotal;
            } else {
                System.out.println("E\n\trreur : Prix non trouvé pour le plat " + plat);
            }
        }
    
        System.out.println("\nCoût total de la commande : " + total + " euros");
    }

    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }
    

   /*public static void afficherMenu(List<String> plats, List<String> prixPlats, List<String> descriptionsPlats, List<String> boissons, List<String> prixBoissons) {
        System.out.println("\t\t\t Menu du Restaurant \n");

        // Afficher la liste des plats
        System.out.println("\t Plats : \n");
        for (int i = 0; i < plats.size(); i++) {
            String plat = plats.get(i);
            System.out.println("\t\t " + plat);

            // Afficher le prix et la description associés
            if (i < prixPlats.size()) {
                System.out.println("\t\t Prix : " + prixPlats.get(i) + " | Description : " + descriptionsPlats.get(i) + "\n");
            }
        }

        // Afficher la liste des boissons
        System.out.println("\t Boissons : \n");
        for (int i = 0; i < boissons.size(); i++) {
            String boisson = boissons.get(i);
            System.out.println("\t\t " + boisson);

            // Afficher le prix associé
            if (i < prixBoissons.size()) {
                System.out.println("\t\t Prix : " + prixBoissons.get(i) + "\n");
            }
        }
    }*/

    // public static void main(String[] args) {
    //     Carte carteInstance = new Carte();
    //     carteInstance.initialiserPlat();
    //     carteInstance.initialiserBoisson();

    //     carteInstance.passerCommande();
    // }
}
