package main.carte;
import java.util.HashMap;
import java.util.Map;

public class Commande {
    private Map<String, Integer> items;

    public Commande() {
        this.items = new HashMap<>();
    }

    public void ajouterPlat(String plat, int quantite) {
        items.put(plat, items.getOrDefault(plat, 0) + quantite);
    }

    public Map<String, Integer> getItems() {
        return items;
    }
}