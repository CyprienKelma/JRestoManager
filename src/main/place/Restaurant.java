package main.place;

import java.util.*;
import main.staff.*;
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

    // Identifiant unique pour chaque transaction
    private static int transactionId = 1;

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

    // Initialisation des tables par défaut du restaurant
    // Dans l'écran monitoring, le manager peut (s'il le souhaite) créer sa composition
    // de tables personnalisée, mais par défaut, le restaurant est initialisé avec 10 tables
    static {
        tablesList.add(new Table(1, 2, true));
        tablesList.add(new Table(2, 2, true));
        tablesList.add(new Table(3, 2, true));
        tablesList.add(new Table(4, 2, true));
        tablesList.add(new Table(5, 4, true));
        tablesList.add(new Table(6, 4, true));
        tablesList.add(new Table(7, 4, true));
        tablesList.add(new Table(8, 4, true));
        tablesList.add(new Table(9, 8, true));
        tablesList.add(new Table(10, 8, true));
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

    public static boolean isEquipeActuelleCreated() {
        return equipeActuelle != null;
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

    public static int getTransactionId() {
        return transactionId;
    }

    public static void setTransactionId(int transactionId) {
        Restaurant.transactionId = transactionId;
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