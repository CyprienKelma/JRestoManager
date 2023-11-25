package main.place;

import java.util.List;
import main.staff.*;
import main.stock.*;

public class Restaurant {

    // Définit si le restaurant est ouvert ou fermé
    // Pour passer en ouvert, il faut que le manager ait consitué une
    // équipe valide (=> bon nombre d'employé de chaque type)
    private static boolean isOpen;

    // La liste de tous les employés du restaurant et
    // de tout type (Manager, Serveur, Barman, Cuisinier)
    private static List<Employé> employésList;

    // L'équipe actuellement en service
    private Equipe equipeActuelle;

    // Le stock actuel du restaurant
    private Stock stockActuelle;

    // La liste des transactions effectuées par le restaurant
    private List<Transaction> transactionsList;

    private Carte carte;

    /**
     * @return boolean return the isOpen
     */
    public boolean isIsOpen() {
        return isOpen;
    }

    /**
     * @param isOpen the isOpen to set
     */
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * @return Equipe return the equipeActuelle
     */
    public Equipe getEquipeActuelle() {
        return equipeActuelle;
    }

    /**
     * @param equipeActuelle the equipeActuelle to set
     */
    public void setEquipeActuelle(Equipe equipeActuelle) {
        this.equipeActuelle = equipeActuelle;
    }

}