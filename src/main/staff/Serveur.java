package main.staff;

public class Serveur extends Employé {

    // Sert à équilibrer le nombre de table entre les serveurs :
    private int nombreDeTables;

    // Sert à mesurer le nombre total de table que le serveur a servi sur la journée
    // :
    private int nombreDeTablesServies;

    public Serveur(String nom, String prenom, double salaire) {
        super(nom, prenom, salaire);
        this.nombreDeTables = 0;
        this.nombreDeTablesServies = 0;
    }

    public void effectuerTache() {
        System.out.println("Le serveur prend les commandes des clients");
        nombreDeTablesServies++;
        nombreDeTables++;
        servirTable();
    }

    public void servirTable() {
        System.out.println("Le serveur associé à cette table apporte les boissons et plats des clients");
    }

    @Override
    public String toString() {
        return "Serveur{nom='" + getNom() + "', prenom='" + getPrenom() + "', salaire=" + getSalaire() +
                ", nombreDeTables=" + nombreDeTables + ", nombreDeTablesServies=" + nombreDeTablesServies + '}';
    }

    /**
     * @return int return the nombreDeTables
     */
    public int getNombreDeTables() {
        return nombreDeTables;
    }

    /**
     * @param nombreDeTables the nombreDeTables to set
     */
    public void setNombreDeTables(int nombreDeTables) {
        this.nombreDeTables = nombreDeTables;
    }

    /**
     * @return int return the nombreDeTablesServies
     */
    public int getNombreDeTablesServies() {
        return nombreDeTablesServies;
    }

    /**
     * @param nombreDeTablesServies the nombreDeTablesServies to set
     */
    public void setNombreDeTablesServies(int nombreDeTablesServies) {
        this.nombreDeTablesServies = nombreDeTablesServies;
    }

}