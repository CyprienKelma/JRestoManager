package main.staff;

import java.io.Serializable;

public abstract class Employé implements Serializable {

    protected String nom;
    protected String prenom;
    protected double salaire;

    Employé(String nom, String prenom, double salaire) {
        this.nom = nom;
        this.prenom = prenom;
        this.salaire = salaire;
    }


    /**
     * @return String return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return String return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return double return the salaire
     */
    public double getSalaire() {
        return salaire;
    }

    /**
     * @param salaire the salaire to set
     */
    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

}