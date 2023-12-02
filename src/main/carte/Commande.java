package main.carte;
import java.util.HashMap;
import java.util.Map;

public class Commande {
    private Map<String, Integer> plats;
    private Map<String, Integer> boissons;

    public Commande() {
        this.plats = new HashMap<>();
        this.boissons = new HashMap<>();
    }

    public void ajouterPlat(String plat, int quantite) {
        plats.put(plat, plats.getOrDefault(plat, 0) + quantite);
    }

    public void ajouterBoisson(String boisson, int quantite) {
        boissons.put(boisson, boissons.getOrDefault(boisson, 0) + quantite);
    }

    public Map<String, Integer> getPlats() {
        return plats;
    }

    public Map<String, Integer> getBoissons() {
        return boissons;
    }

    public void setPlats(Map<String, Integer> plats) {
        this.plats = plats;
    }

    public void setBoissons(Map<String, Integer> boissons) {
        this.boissons = boissons;
    }

    public void clearPlats() {
        plats.clear();
    }

    public void clearBoissons() {
        boissons.clear();
    }

    public void supprimerPlat(String plat) {
        plats.remove(plat);
    }

    public void supprimerBoisson(String boisson) {
        boissons.remove(boisson);
    }

    

}