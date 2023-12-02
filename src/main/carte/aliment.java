package main.carte;

public class Aliment {
    private String nom;
    private int quantite;

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

    public int setQuantite(int quantite) {
        return this.quantite = quantite;
    }
}