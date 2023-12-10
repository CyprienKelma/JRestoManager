package main.staff;

public class Barman extends Employé {

    // Mesure pour les statistiques
    private int nbrBoissonsServies;

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

    /**
     * @return int return the nbrBoissonsServies
     */
    public int getNbrBoissonsServies() {
        return nbrBoissonsServies;
    }

    /**
     * @param nbrBoissonsServies the nbrBoissonsServies to set
     */
    public void setNbrBoissonsServies(int nbrBoissonsServies) {
        this.nbrBoissonsServies = nbrBoissonsServies;
    }
}