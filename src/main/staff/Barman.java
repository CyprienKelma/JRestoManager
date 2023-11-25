package main.staff;

public class Barman extends Employé {

    public Barman(String nom, String prenom, double salaire) {
        super(nom, prenom, salaire);
    }

    public void effectuerTache() {
        System.out.println("Le barman travaille");
    }
}