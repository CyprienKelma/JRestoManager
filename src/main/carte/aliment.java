package main.carte;

public class aliment {
    private String nom;
    private int quantite;

    public aliment(String nom, int quantite) {
        this.nom = nom;
        this.quantite = quantite;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantite() {
        return quantite;
    }
}