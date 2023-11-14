package main.staff;

import java.util.*;

public class Equipe {

    // TODO Vérifier le nombre [min, max] d'employés de chaque type dans une unique
    // équipe

    private Manager manager; // Un unique manager par équipe ?
    private List<Serveur> serveursList; // 2 serveur minimum
    private List<Barman> barmansList; // 1 barman minimum
    private List<Cuisinier> cuisiniersList; // 4 cuisiniers minimum

    public Equipe(Manager manager, List<Serveur> serveurs, List<Barman> barmans, List<Cuisinier> cuisiniers) {
        this.manager = manager;
        this.serveursList = serveurs;
        this.barmansList = barmans;
        this.cuisiniersList = cuisiniers;
    }

    /*
     * "T" employé : l'employé que l'on veut ajouter
     * employésList : la liste dans laquelle on veut l'ajouter (de type T)
     */
    public <T extends Employé> void ajouterEmployé(T employé, List<T> employésList) {
        employésList.add(employé);
    }

    /*
     * "T" employé : l'employé que l'on veut suppprimer
     * employésList : la liste (de type T) où l'on veut supprimer l'employé
     */
    public <T extends Employé> void supprimerEmployé(T employé, List<T> employésList) {
        employésList.remove(employé);
    }

    /**
     * @return Manager return the manager
     */
    public Manager getManager() {
        return manager;
    }

    /**
     * @param manager the manager to set
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    /**
     * @return List<Serveur> return the serveurs
     */
    public List<Serveur> getServeurs() {
        return serveursList;
    }

    /**
     * @param serveurs the serveurs to set
     */
    public void setServeurs(List<Serveur> serveurs) {
        this.serveursList = serveurs;
    }

    /**
     * @return List<Barman> return the barmans
     */
    public List<Barman> getbBarmans() {
        return barmansList;
    }

    /**
     * @param barmans the barmans to set
     */
    public void setBarmans(List<Barman> barmans) {
        this.barmansList = barmans;
    }
}