package main.staff;

import java.io.Serializable;

public class Serveur extends Employé implements Serializable {

    private static final long serialVersionUID = 123456789L;

    // Sert à mesurer le nombre total de table que le serveur a servi sur la journée
    protected int nombreDeTablesServies;

    // Sert à indiquer si l'on doit l'afficher ou non dans la liste des serveurs

    public Serveur(String nom, String prenom, double salaire) {
        super(nom, prenom, salaire);
        this.nombreDeTablesServies = 0;
        this.nbrJoursConsecutifs = 0;
    }

    public Serveur(String nom, String prenom, double salaire, int nbrJoursConsecutifs) {
        super(nom, prenom, salaire);
        this.nombreDeTablesServies = 0;
        this.nbrJoursConsecutifs = nbrJoursConsecutifs;
    }

    public void incNbrTableServies() {
        this.nombreDeTablesServies++;
    }

    public void servirTable() {
        System.out.println("Le serveur associé à cette table apporte les boissons et plats des clients");
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