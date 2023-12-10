package main.staff;

public class Cuisinier extends Employé {

    // Mesure pour les statistiques
    private int nbrPlatsCuisines;

    // Sert à indiquer si l'on doit l'afficher ou non dans la liste des serveurs

    public Cuisinier(String nom, String prenom, double salaire) {
        super(nom, prenom, salaire);
        this.nbrPlatsCuisines = 0;
        this.nbrJoursConsecutifs = 0;
    }

    public Cuisinier(String nom, String prenom, double salaire, int nbrJoursConsecutifs) {
        super(nom, prenom, salaire);
        this.nbrPlatsCuisines = 0;
        this.nbrJoursConsecutifs = nbrJoursConsecutifs;
    }


    /**
     * @return int return the nbrPlatsCuisines
     */
    public int getNbrPlatsCuisines() {
        return nbrPlatsCuisines;
    }

    /**
     * @param nbrPlatsCuisines the nbrPlatsCuisines to set
     */
    public void setNbrPlatsCuisines(int nbrPlatsCuisines) {
        this.nbrPlatsCuisines = nbrPlatsCuisines;
    }
}