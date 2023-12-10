package main.staff;

public class Barman extends Employé {

    // Mesure pour les statistiques
    private int nbrBoissonsServies;

    // Sert à indiquer si l'on doit l'afficher ou non dans la liste des serveurs

    public Barman(String nom, String prenom, double salaire) {
        super(nom, prenom, salaire);
        this.nbrBoissonsServies = 0;
        this.nbrJoursConsecutifs = 0;
    }

    public Barman(String nom, String prenom, double salaire, int nbrJoursConsecutifs) {
        super(nom, prenom, salaire);
        this.nbrBoissonsServies = 0;
        this.nbrJoursConsecutifs = nbrJoursConsecutifs;
    }

    public void effectuerTache() {
        System.out.println("Le barman travaille");
    }
}