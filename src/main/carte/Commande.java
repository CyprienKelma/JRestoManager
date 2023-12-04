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

    public void addPlats(String plat, int quantite) {
        plats.put(plat, plats.getOrDefault(plat, 0) + quantite);
    }

    public void addBoissons(String boisson, int quantite) {
        boissons.put(boisson, boissons.getOrDefault(boisson, 0) + quantite);
    }

    public void removePlats(String plat, int quantite) {
        plats.put(plat, plats.getOrDefault(plat, 0) - quantite);
    }

    public void removeBoissons(String boisson, int quantite) {
        boissons.put(boisson, boissons.getOrDefault(boisson, 0) - quantite);
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

    public void setPlatsSelectionnes(HashMap<String, Integer> platsSelectionnes) {
        this.plats.clear();  // Pour supprimer les plats déjà sélectionnés
        this.plats.putAll(platsSelectionnes);  // Ajoute tout les nouveaux plats passé en paramètre
    }

    public void setBoissonsSelectionnes(HashMap<String, Integer> boissonsSelectionnes) {
        this.boissons.clear();  // Pour supprimer les boissons déjà sélectionnés
        this.boissons.putAll(boissonsSelectionnes);  // Ajoute tout les nouvelles boissons passées en paramètre
    }

}