package main.carte;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class carte {

    List<String> liste1 = new ArrayList<>() {
        {
            add("Salade Tomates");
            add("Salade simple");
            add("Potage Oignon");
            add("Potage Tomates");
            add("Potage champignon");
            add("Burger");
            add("Burger Sans T");
            add("Burger Sans TS");
            add("Pizza fromage");
            add("Pizza champignon");
            add("Pizza Chorizo");
        }
    };

    List<String> liste2 = new ArrayList<>() {
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

    List<String> liste3 = new ArrayList<>() {
        {
            add("Salade Tomate");
            add("Salade");
            add("3*Oignon");
            add("3*Tomate");
            add("3*champignon");
            add("Pain Salade Tomate Viande");
            add("Pain Salade Viande");
            add("Pain Viande");
            add("pate Tomate Fromage");
            add("pate Tomate Fromage Champignon");
            add("pate Tomate Fromage Chorizo");
        }
    };

    List<String> liste4 = new ArrayList<>() {
        {
            add("Limonade");
            add("Cidre doux");
            add("Biere sans alcool");
            add("Jus de Fruits");
            add("Verre d'eau");
        }
    };

    List<String> liste5 = new ArrayList<>() {
        {
            add("4 euros");
            add("5 euros");
            add("5 euros");
            add("1 euros");
            add("Gratuit");
        }
    };

    List<String> plat = new ArrayList<>();

    public void initialiserPlat() {
        plat.addAll(liste1);
        plat.addAll(liste2);
        plat.addAll(liste3);
    }

    List<String> boisson = new ArrayList<>();

    public void initialiserBoisson() {
        boisson.addAll(liste4);
        boisson.addAll(liste5);
    }

    private void verifieDispoPlat(List<String> plats, List<Integer> quantites, List<aliment> stock) {
        if (plats.size() != quantites.size()) {
            System.out.println("Erreur : Les listes de plats et de quantités ne sont pas de la même taille.");
            return;
        }

        List<String> platsNonDisponibles = new ArrayList<>();

        for (int i = 0; i < plats.size(); i++) {
            String plat = plats.get(i);
            int quantiteDemandee = quantites.get(i);

            // Vérifier la disponibilité dans le stock mis à jour
            if (!verifierDisponibilite(plat, quantiteDemandee, stock)) {
                platsNonDisponibles.add(plat);
            }
        }

        // Afficher les plats non disponibles avec leurs détails
        if (!platsNonDisponibles.isEmpty()) {
            System.out.println("Les plats suivants ne sont pas disponibles en quantité suffisante :");
            for (String platNonDisponible : platsNonDisponibles) {
                System.out.println(platNonDisponible);
            }
        }
    }

    private boolean verifierDisponibilite(String plat, int quantiteDemandee, List<aliment> stock) {
        // Récupérer les ingrédients nécessaires pour le plat
        List<String> ingredients = getIngredients(plat);

        // Vérifier la disponibilité de chaque ingrédient dans le stock
        for (String ingredient : ingredients) {
            if (!verifierIngredientDisponible(ingredient, quantiteDemandee, stock)) {
                return false;
            }
        }

        return true;
    }

    private List<String> getIngredients(String plat) {
        List<String> ingredients = new ArrayList<>();

        // Ajouter chaque ingrédient à la liste
        for (int i = 0; i < liste3.size(); i++) {
            if (liste1.get(i).equalsIgnoreCase(plat)) {
                ingredients.add(liste3.get(i));
            }
        }

        return ingredients;
    }

    private boolean verifierIngredientDisponible(String ingredient, int quantiteDemandee, List<aliment> stock) {
        // Gérer le cas particulier pour "3*oignons"
        if (ingredient.startsWith("3*")) {
            String nomIngredient = ingredient.substring(2);
            int quantiteRequise = Integer.parseInt(ingredient.substring(2)) * quantiteDemandee;

            return verifierDisponibilite(nomIngredient, quantiteRequise, stock);
        }

        // Vérifier la disponibilité de l'ingrédient dans le stock
        for (aliment stockAliment : stock) {
            if (stockAliment.getNom().equalsIgnoreCase(ingredient)) {
                // Comparer la quantité demandée avec la quantité dans le stock
                return stockAliment.getQuantite() >= quantiteDemandee;
            }
        }

        // L'ingrédient n'est pas trouvé dans le stock
        return false;
    }

    private Commande commandeEnCours;
    private Scanner scanner;

    public carte() {
        this.commandeEnCours = new Commande();
        this.scanner = new Scanner(System.in);  // Initialize the Scanner
    }

    public void passerCommande() {
        int choix;

        do {
            System.out.println("1 - Commander un plat");
            System.out.println("2 - Fin de la commande");
            choix = scanner.nextInt();  // Use the class-level Scanner

            switch (choix) {
                case 1:
                    afficherPlatsNonDisponibles();
                    afficherPlatsDisponibles();
                    commanderPlat();
                    break;
                case 2:
                    System.out.println("Fin de la commande. Voici votre commande :");
                    afficherCommande();
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    break;
            }
        } while (choix != 2);
    }

    private void afficherPlatsNonDisponibles() {
        List<String> platsACommander = new ArrayList<>(commandeEnCours.getItems().keySet());
        List<Integer> quantitesACommander = new ArrayList<>(commandeEnCours.getItems().values());

        // Create a list of aliment objects from stock
        List<aliment> stockAliments = new ArrayList<>();
        for (int i = 0; i < liste3.size(); i++) {
            stockAliments.add(new aliment(liste3.get(i), 0)); // Assuming initial quantity is not needed
        }

        verifieDispoPlat(platsACommander, quantitesACommander, stockAliments);
    }

    private void afficherPlatsDisponibles() {
        System.out.println("Voici les plats disponibles :");
        for (int i = 0; i < liste1.size(); i++) {
            String plat = liste1.get(i);
            System.out.println((i + 1) + " - " + plat);
        }
    }

    private void commanderPlat() {
        System.out.println("Veuillez entrer le numéro du plat que vous souhaitez commander : ");
        int numeroPlat = scanner.nextInt(); // Utilisez le Scanner de la classe

        if (numeroPlat >= 1 && numeroPlat <= liste1.size()) {
            String platChoisi = liste1.get(numeroPlat - 1);
            // Retirer les ingrédients du plat choisi du stock
            retirerAliment("src\\main\\data\\stock.txt",platChoisi);
            // Ajouter le plat à la commande avec une quantité de 1 par défaut
            commandeEnCours.ajouterPlat(platChoisi, 1);

            System.out.println("Plat ajouté à votre commande.");
        } else {
            System.out.println("Numéro de plat invalide.");
        }
    }

    private void retirerAliment(String string, String platChoisi) {
    }

    private void afficherCommande() {
        Map<String, Integer> items = commandeEnCours.getItems();
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " portions");
        }
    }

    public static void afficherMenu(List<String> plats, List<String> prixPlats, List<String> descriptionsPlats, List<String> boissons, List<String> prixBoissons) {
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
    }

    public static void main(String[] args) {
        carte carteInstance = new carte();
        carteInstance.initialiserPlat();
        carteInstance.initialiserBoisson();

        carteInstance.passerCommande();
    }
}
