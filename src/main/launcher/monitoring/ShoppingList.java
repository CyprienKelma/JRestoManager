package main.launcher.monitoring;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.place.Restaurant;

// Cette classe est exclusivement dédié à la gestion de la liste de course
// qui s'affiche dans l'écran de gestion du manager (Ecran monitoring)
public class ShoppingList {
    

    // TODO :
    // Liste de quantité de base pour chaque ingrédient
    // Il s'agit de la quantité de chaque ingrédiennt pour considérer que le stock est "plein"


    static Map<String, Integer> reserveIngrédientDeBase = Map.ofEntries(
        Map.entry("Champignon", 65),
        Map.entry("Tomate", 80),
        Map.entry("Salade", 90),
        Map.entry("Oignon", 40),
        Map.entry("Pain", 80),
        Map.entry("Viande", 60),
        Map.entry("Fromage", 80),
        Map.entry("Pate", 80),
        Map.entry("Chorizo", 70)
    );

    static Map<String, Integer> reserveBoissonDeBase = Map.ofEntries(
        Map.entry("Limonade", 45),
        Map.entry("Cidre doux", 50),
        Map.entry("Biere sans alcool", 50),
        Map.entry("Jus de Fruits", 60),
        Map.entry("Verre d'eau", 3000)
    );

    // - Enlever le doublons d'ingrédient
    // fix parce que la liste de course n'affiche pas la quantité qu'il manque

    public static void getLastServiceShoppingList(Scanner menuScanner) throws IOException {


        // Pour chaque type d'élément, on calcule la quantité nécessaire
        Map<String, Integer> ingredientsNecessaires = calculateNeededQuantities(reserveIngrédientDeBase, "src\\main\\data\\stock.txt");
        Map<String, Integer> boissonsNecessaires = calculateNeededQuantities(reserveBoissonDeBase, "src\\main\\data\\stockDrink.txt");
        
        // Afficher la liste des courses pour les ingrédients et les boissons
        print("Ingrédients nécessaires : ");
        showShoppingList(ingredientsNecessaires);

        print("\nBoissons nécessaires : ");
        showShoppingList(boissonsNecessaires);

    }

    // Cette fonction permet de calculer les quantités nécessaires pour chaque ingrédient
    // en fonction de la quantité de base et de la quantité actuelle
    // Elle est générique : fonctionne aussi bien pour les ingrédients que pour les boissons
    private static Map<String, Integer> calculateNeededQuantities(Map<String, Integer> reserveDeBase, String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        Map<String, Integer> stockActuel = convertStockLines(lines);

        // On créer une copie de la liste de base pour éviter une erreur du type
        // "java.lang.UnsupportedOperationException" lors de l'utilisation de la fonction "put"
        Map<String, Integer> quantitesNecessaires = new HashMap<>(reserveDeBase);

        // Calcul des quantités nécessaires pour chaque ingrédient
        for (Map.Entry<String, Integer> entry : quantitesNecessaires.entrySet()) {
            String ingredient = entry.getKey();
            int reserveBase = entry.getValue();

            // Si l'ingrédient est déjà dans le stock, on calcule la quantité nécessaire
            // pour atteindre la quantité de base (qqt nécessaire = qqt de base - qqt actuelle)
            if (stockActuel.containsKey(ingredient)) {
                int quantiteActuelle = stockActuel.get(ingredient);
                // Ici, on utilise la fonction Math.max pour ne pas avoir de quantité négative
                int quantiteNecessaire = Math.max(0, reserveBase - quantiteActuelle);
                quantitesNecessaires.put(ingredient, quantiteNecessaire);
            } else {
                // Si l'ingrédient n'est pas dans le stock, on considère qu'il faut le commander
                quantitesNecessaires.put(ingredient, reserveBase);
            }
        }

        return quantitesNecessaires;
    }


    // Fonction qui permet de passer d'une ligne de texte à une map
    // Exemple : "Champignon, 80" => Map.entry("Champignon", 80)
    private static Map<String, Integer> convertStockLines(List<String> lines) {
        Map<String, Integer> stockMap = new HashMap<>();

        for (String line : lines) {
            String[] elements = line.split(",");
            if (elements.length == 2) {
                String nom = elements[0].trim();
                int quantite = Integer.parseInt(elements[1].trim());
                stockMap.put(nom, quantite);
            }
        }

        return stockMap;
    }

    private static void showShoppingList(Map<String, Integer> shoppingList) {
        for (Map.Entry<String, Integer> entry : shoppingList.entrySet()) {
            String ingredient = entry.getKey();
            int quantiteNecessaire = entry.getValue();
            print("- " + ingredient + ", quantité nécéssaire : " + quantiteNecessaire);
        }
    }

    /* ------------------------------------- SAUVEGARDE DE FICHIER  --------------------------------------- */

    // Sauvegarde la liste de course dans un fichier lorsque l'on clique sur "1 - Imprimer la liste de course"
    public static void saveShoppingListToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src\\main\\data\\shoppingList.txt"))) {
            
            // Pour sauvegarder les ingrédients
            Map<String, Integer> ingredientQuantities = calculateNeededQuantities(reserveIngrédientDeBase, "src\\main\\data\\stock.txt");
            for (Map.Entry<String, Integer> entry : ingredientQuantities.entrySet()) {
                String ingredient = entry.getKey();
                int quantity = entry.getValue();
                // Debuging
                System.out.println("Ingrédient : " + ingredient + ", Quantité : " + quantity);
                writer.println(ingredient + ", " + quantity);
            }

            // Pour sauvegarder les boissons
            Map<String, Integer> drinkQuantities = calculateNeededQuantities(reserveBoissonDeBase, "src\\main\\data\\stockDrink.txt");
            for (Map.Entry<String, Integer> entry : drinkQuantities.entrySet()) {
                String drink = entry.getKey();
                int quantity = entry.getValue();
                writer.println(drink + ", " + quantity);
            }
            System.out.println("Liste de course sauvegardée avec succès.");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde de la liste de course : " + e.getMessage());
        }
    }

    // Charge la liste de course depuis le fichier, lors du lancement de l'application
    public static void loadShoppingListFromTheFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\data\\shoppingList.txt"))) {
            // Chargement des ingrédients
            loadIngredients(reader);
            System.out.println("Liste de course chargée avec succès.");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la liste de course : " + e.getMessage());
        }
    }

    // Fonction utilitaire pour charger les ingrédients
    private static void loadIngredients(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            // On divise la ligne en 2 parties "nom, quantité"
            String[] parts = line.split(", ");
            String name = parts[0];
            int quantity = Integer.parseInt(parts[1]);

            // On ajoute ensuite l'ingrédient
            Restaurant.getShoppingList().put(name, quantity);
        }
    }




    // // Suvegarde la liste de course dans un fichier lorsque l'on clique sur "1 - Imprimer la liste de course"
    // public static void saveShoppingListToFile() {
    //     try (PrintWriter writer = new PrintWriter(new FileWriter("src\\main\\data\\shoppingList.txt"))) {
    //         for (Map.Entry<String, Integer> entry : calculateNeededQuantities(Map.of(), "src\\main\\data\\stock.txt").entrySet()) {
    //             String ingredient = entry.getKey();
    //             int quantiteNecessaire = entry.getValue();
    //             writer.println(ingredient + ", " + quantiteNecessaire);
    //         }
    //         for (Map.Entry<String, Integer> entry : calculateNeededQuantities(Map.of(), "src\\main\\data\\stockDrink.txt").entrySet()) {
    //             String ingredient = entry.getKey();
    //             int quantiteNecessaire = entry.getValue();
    //             writer.println(ingredient + ", " + quantiteNecessaire);
    //         }
    //         print("Liste de course sauvegardée avec succès.");
    //     } catch (IOException e) {
    //         System.err.println("Erreur lors de la sauvegarde de la liste de course : " + e.getMessage());
    //     }
    // }

    // // Charge la liste de course depuis le fichier, lors du lancement de l'application
    // public static void loadShoppingListFromTheFile() {
    //     try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\data\\shoppingList.txt"))) {
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             // On divise la ligne en 2 parties
    //             // "nom, quantité"
    //             String[] parts = line.split(", ");
    //             String nom = parts[0];
    //             int quantite = Integer.parseInt(parts[1]);

    //             // On ajoute ensuite l'ingrédient ou la boisson
    //             Restaurant.getShoppingList().put(nom, quantite);
    //         }
    //         print("Liste de course chargée avec succès.");
    //     } catch (IOException e) {
    //         System.err.println("Erreur lors du chargement de la liste de course : " + e.getMessage());
    //     }
    // }


    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        for(int i = 0; i < 50; ++i)
            System.out.println();
    }

    public static void setIsSaved(boolean b) {

    }
}