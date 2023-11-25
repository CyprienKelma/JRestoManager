package main.staff;

import java.util.List;

import main.launcher.App;

public class Cuisinier extends Employé {

    Cuisinier(String nom, String prenom, double salaire) {
        super(nom, prenom, salaire);
    }

    // TODO : Create the Plat class
    @Override
    public List<Plat> effectuerTache(List<Plat> platsAFaire) {
        System.out.println("Le cuisinier commence à préparer les plats");

        return List.of(/* A faire */);
    }
}