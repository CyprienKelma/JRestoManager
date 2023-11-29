package main.place;

import java.util.*;
import main.staff.*;
import main.stock.*;

/*
 * Classe pricipale qui représente le coeur du restaurant
 * Elle contient toutes les informations relatives à l'état du restaurant
 * (employés, stock, équipe, transactions) dans le service actuellement en cours
 */
public class Restaurant {

    // Définie si le restaurant est ouvert ou fermé
    // Pour passer en ouvert, il faut que le manager ait consitué une
    // équipe valide (=> bon nombre d'employé de chaque type)
    private static boolean isOpen;

    // La liste de tous les employés du restaurant et
    // de tout type (Manager, Serveur, Barman, Cuisinier)
    private static List<Employé> employésList = new ArrayList<>();

    // L'équipe actuellement en service
    private static Equipe equipeActuelle;

    // Le stock actuel du restaurant
    private static Stock stockActuelle;

    // La liste des transactions effectuées par le restaurant
    private static List<Transaction> transactionsList;

    // Carte indiquant en tant réel les plats disponibles ou non (en fonction du
    // stock)
    // private static Carte carte; // TODO : Netoyer le package stock avant
    // d'importer la classe Carte

    Restaurant() {
        // Constructeur privé pour empêcher l'instanciation des classes "Restaurant"
        // C'est une classe utilitaire puisqu'on travaille avec un seul restaurant
        throw new IllegalStateException("Classe Restaurant : pas d'instances requises");
    }

    public static boolean isOpen() {
        return isOpen;
    }

    public static void setIsOpen(boolean isOpen) {
        Restaurant.isOpen = isOpen;
    }

    public static List<Employé> getEmployésList() {
        return employésList;
    }

    public static void setEmployésList(List<Employé> employésList) {
        Restaurant.employésList = employésList;
    }

    public static Equipe getEquipeActuelle() {
        return equipeActuelle;
    }

    public static void setEquipeActuelle(Equipe equipeActuelle) {
        Restaurant.equipeActuelle = equipeActuelle;
    }

    public static Stock getStockActuelle() {
        return stockActuelle;
    }

}