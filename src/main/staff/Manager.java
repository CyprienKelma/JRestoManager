package main.staff;

public class Manager extends Employé {

    Manager(String nom, String prenom, double salaire) {
        super(nom, prenom, salaire);
    }

    @Override
    public void effectuerTache() {
        // TODO définir les taches du manager
        System.out.println("Le manager travaille");
    }

}