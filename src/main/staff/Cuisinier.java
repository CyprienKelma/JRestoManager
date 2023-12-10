package main.staff;

public class Cuisinier extends Employé {

    // Mesure pour les statistiques
    private int nbrPlatsCuisines;

    // Sert à indiquer si l'on doit l'afficher ou non dans la liste des serveurs
    private int nbrJoursConsecutifs;

    public Cuisinier(String nom, String prenom, double salaire) {
        super(nom, prenom, salaire);
        this.nbrPlatsCuisines = 0;
        this.nbrJoursConsecutifs = 0;
    }
}