package main.staff;

public class Barman extends Employé {

    Barman(String nom, String prenom, double salaire) {
        super(nom, prenom, salaire);
    }

    @Override
    public void effectuerTache() {
        System.out.println("Le barman travaille");
    }
}