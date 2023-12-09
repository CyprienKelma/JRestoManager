package main.launcher.monitoring;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Cette classe est exclusivement dédié à la gestion de la liste de course
// qui s'affiche dans l'écran de gestion du manager (Ecran monitoring)
public class ShoppingList {
    

    // TODO :
    // - Enlever le doublons d'ingrédient
    // fix parce que la liste de course n'affiche pas la quantité qu'il manque

    public static void getLastServiceShoppingList(Scanner menuScanner) throws IOException {

        // Liste de quantité de base pour chaque ingrédient
        // Il s'agit de la quantité de chaque ingrédiennt pour considérer que le stock est "plein"
        Map<String, Integer> reserveDeBase = Map.ofEntries(
            Map.entry("Champignon", 80),
            Map.entry("Tomate", 80),
            Map.entry("Salade", 80),
            Map.entry("Oignon", 80),
            Map.entry("Pain", 80),
            Map.entry("Viande", 80),
            Map.entry("Fromage", 80),
            Map.entry("Pate", 80),
            Map.entry("Chorizo", 80),
            Map.entry("Limonade", 80),
            Map.entry("Cidre doux", 80),
            Map.entry("Biere sans alcool", 100),
            Map.entry("Jus de Fruits", 80),
            Map.entry("Verre d'eau", 3000)
        );

        // Pour chaque type d'élément, on calcule la quantité nécessaire
        Map<String, Integer> ingredientsNecessaires = calculateNeededQuantities(reserveDeBase, "src\\main\\data\\stock.txt");
        Map<String, Integer> boissonsNecessaires = calculateNeededQuantities(reserveDeBase, "src\\main\\data\\stock.txt");

        // Afficher la liste des courses pour les ingrédients
        print("Ingrédients nécessaires : ");
        showShoppingList(ingredientsNecessaires);

        // Afficher la liste des courses pour les boissons
        print("\nBoissons nécessaires : ");
        showShoppingList(boissonsNecessaires);
    }


    // Cette fonction permet de calculer les quantités nécessaires pour chaque ingrédient
    private static Map<String, Integer> calculateNeededQuantities(Map<String, Integer> reserveDeBase, String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        Map<String, Integer> stockActuel = convertStockLines(lines);

        // On créer une copie de la liste de base pour éviter une erreur du type
        // "java.lang.UnsupportedOperationException" lors de l'utilisation de la fonction "put"
        Map<String, Integer> quantitesNecessaires = new HashMap<>(reserveDeBase);

        // Calcul des quantités nécessaires pour chaque ingrédient
        for (Map.Entry<String, Integer> entry : reserveDeBase.entrySet()) {
            String ingredient = entry.getKey();
            int reserveBase = entry.getValue();

            if (stockActuel.containsKey(ingredient)) {
                int quantiteActuelle = stockActuel.get(ingredient);
                // Ici, on utilise la fonction Math.max pour ne pas avoir de quantité négative
                int quantiteNecessaire = Math.max(0, reserveBase - quantiteActuelle);
                quantitesNecessaires.put(ingredient, quantiteNecessaire);
            }
        }

        return reserveDeBase;
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
            print("- " + ingredient + ", quantité : " + quantiteNecessaire);
        }
    }



    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        for(int i = 0; i < 50; ++i)
            System.out.println();
    }
}