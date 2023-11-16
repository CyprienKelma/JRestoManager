package main.carte;

import java.util.ArrayList;
import java.util.List;

public class carte {
    List <String> Liste1 = new ArrayList<>(){
            {
                add("Salade Tomates");
                add("Salade simple");
                add("Potage Oignon");
                add("Potage Tomates");
                add("Potage champignon");
                add("Burger");
                add("Burger Sans T");
                add("Burger Sans TS");
                add("Pizza formage");
                add("Pizza champignon");
                add("Pizza Chorizo");
            }

    };

    List <String> Liste2 = new ArrayList<>(){
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

    List <String> Liste3 = new ArrayList<>(){
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

    List <String> Liste4 = new ArrayList<>(){
        {
            add("Limonade");
            add("Cidre doux");
            add("Biere sans alcol");
            add("Jus de Fruis");
            add("Verre d'eau");
        }
    };

    List <String> Liste5 = new ArrayList<>(){
        {
            add("4");
            add("5");
            add("5");
            add("1");
            add("0");
        }
    };
    
    List <String> Plat = new ArrayList<>();
    Plat.addAll(liste1);
    Plat.addAll(liste2);
    Plat.addAll(liste3);


    public void VerifieDispoPlat(List Plats, List Stock){

    }

    public static String AfficherMenu(List Plats, List Boissons){
        systeme.out.println("La Liste des plats : \n");
        systeme.out.println(Plats);
        systeme.out.println("\n\n")

        systeme.out.println("La Liste des Boissons : \n");
        syteme.out.println(Boissons)
    }

}