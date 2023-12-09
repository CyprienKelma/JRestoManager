package main.carte;

public class Aliment {
    private String nom;
    private int quantite;

    //Getters et Setters

    public Aliment(String nom, int quantite) {
        this.nom = nom;
        this.quantite = quantite;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}