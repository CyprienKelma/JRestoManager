package main.carte;

public class Boisson {
    
    private String nom;
    private int quantite;

    public Boisson(String nom, int quantite) {
        this.nom = nom;
        this.quantite = quantite;
    }

    public String getNom() {
        return this.nom;
    }

    public int getQuantite() {
        return this.quantite;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

}