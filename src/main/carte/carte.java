package main.carte;

import java.io.IOException;
import java.util.*;

public class carte {

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

    public void initialiserPlat() {
        plat.addAll(liste1);
        plat.addAll(liste2);
        plat.addAll(liste3);
    }

    private static List<String> boisson = new ArrayList<>();

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

    private Commande commandeEnCours;
    private Scanner scanner;

    public carte() {
        this.commandeEnCours = new Commande();
        this.scanner = new Scanner(System.in);  // Initialize the Scanner
    }

    public void passerCommande() {
        int choix;
    
        do {
            System.out.println("\n\t1 - Commander un plat");
            System.out.println("\n\t2 - Fin de la commande");
            choix = scanner.nextInt();  // Utilisez le Scanner de la classe
    
            try {
                switch (choix) {
                    case 1:
                        List<aliment> stock1 = stock.lireFichier("src\\main\\data\\stock.txt");
                        afficherPlatsNonDisponibles(liste1, stock1);
                        List<String> platsDisponibles = afficherPlatsDisponibles(liste1, stock1);
                        commanderPlat(platsDisponibles);
                        break;
                    case 2:
                        System.out.println("\nFin de la commande. Voici votre commande :");
                        afficherCommande();
                        break;
                    default:
                        System.out.println("Choix invalide. Veuillez réessayer.");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace(); // Gérer l'exception de manière appropriée en fonction de vos besoins
            }
        } while (choix != 2);
    }

    public void afficherPlatsNonDisponibles(List<String> plats, List<aliment> stock) {
        System.out.println("\n\tPlats non disponibles :");
        
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
                    break;  // Sortir de la boucle dès qu'un ingrédient manque
                }
            }

            if (!platDisponible) {
                System.out.println(plat);
            }
        }
    }

    public List<String> afficherPlatsDisponibles(List<String> plats, List<aliment> stock) {
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
    
    private void commanderPlat(List<String> platsDisponibles) {
        System.out.println("\nVeuillez entrer le numéro du plat que vous souhaitez commander : ");
        int numeroPlat = scanner.nextInt(); // Utilisez le Scanner de la classe
    
        if (numeroPlat >= 1 && numeroPlat <= platsDisponibles.size()) {
            String platChoisi = platsDisponibles.get(numeroPlat - 1);
    
            Map<String, Integer> ingredients = getIngredients(platChoisi);
    
            try {
                // Retirer les ingrédients du plat choisi du stock
                stock.retirerAliment("src\\main\\data\\stock.txt", ingredients);
    
                // Ajouter le plat à la commande avec une quantité de 1 par défaut
                commandeEnCours.ajouterPlat(platChoisi, 1);
    
                System.out.println("Plat ajouté à votre commande.");
            } catch (IOException e) {
                System.out.println("Erreur lors de la mise à jour du stock : " + e.getMessage());
            }
        } else {
            System.out.println("Numéro de plat invalide.");
        }
    }
    


    private void afficherCommande() {
        Map<String, Integer> items = commandeEnCours.getItems();
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

    public static void main(String[] args) {
        carte carteInstance = new carte();
        carteInstance.initialiserPlat();
        carteInstance.initialiserBoisson();

        carteInstance.passerCommande();
    }
}
