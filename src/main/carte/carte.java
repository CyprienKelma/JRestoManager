package main.carte;

import java.util.ArrayList;
import java.util.List;

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
            add("9€");
            add("9€");
            add("8€");
            add("8€");
            add("8€");
            add("15€");
            add("15€");
            add("15€");
            add("12€");
            add("12€");
            add("12€");
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
            add("Pain Tomate Fromage");
            add("Pain Tomate Fromage Champignon");
            add("Pain Tomate Fromage Chorizo");
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
            add("4");
            add("5");
            add("5");
            add("1");
            add("0");
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
        plat.addAll(liste4);
        plat.addAll(liste5);
    }


    public void verifieDispoPlat(List<String> plats, List<String> stock) {
        // Ajoutez ici la logique de vérification de la disponibilité des plats par rapport au stock.
    }

    public static void afficherMenu(List<String> plats, List<String> boissons) {
        System.out.println("La Liste des plats : \n");
        System.out.println(plats);
        System.out.println("\n\n");

        System.out.println("La Liste des Boissons : \n");
        System.out.println(boissons);
    }
}
