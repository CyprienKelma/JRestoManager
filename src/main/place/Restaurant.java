package main.place;

import java.util.*;
import main.staff.*;
import main.stock.*;
import main.carte.*;

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
    private static List<Transaction> transactionsList = new ArrayList<>();

    // La liste des tables du restaurant
    // Le nombre de tables est fixe et défini par le manager (voir écran monitoring)
    private static List<Table> tablesList = new ArrayList<>();

    // TODO : Ajouter gestion des tables dans l'écran monitoring
    // TEMPORAIRE : 5 tables de 2 et 4 personnes
    // C'est pour créer le système de commande sans créer de table
    static {
        tablesList.add(new Table(78, 2, true));
        tablesList.add(new Table(3, 2, true));
        tablesList.add(new Table(31, 2, true));
        tablesList.add(new Table(42, 4, true));
        tablesList.add(new Table(18, 4, true));
    }

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

    public static void setStockActuelle(Stock stockActuelle) {
        Restaurant.stockActuelle = stockActuelle;
    }

    public static List<Transaction> getTransactionsList() {
        return transactionsList;
    }

    public static int getTransactionsListSize() {
        if (transactionsList == null) {
            return 0;
        } else {
            return transactionsList.size();
        }
    }

    public static void setTransactionsList(List<Transaction> transactionsList) {
        Restaurant.transactionsList = transactionsList;
    }

    public static List<Table> getTablesList() {
        return tablesList;
    }

    public static void setTablesList(List<Table> tablesList) {
        Restaurant.tablesList = tablesList;
    }
}