package main.staff;

public class Manager extends Employé {

    public Manager(String nom, String prenom, double salaire) {
        super(nom, prenom, salaire);
    }

    public Manager(String nom, String prenom, double salaire, int nbrJoursConsecutifs) {
        super(nom, prenom, salaire);
    }

    @Override
    public String toString() {
        return "Manager{nom='" + getNom() + "', prenom='" + getPrenom() + "', salaire=" + getSalaire() + "}";
    }
}