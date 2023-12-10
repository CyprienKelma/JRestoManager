package main.place;

import java.util.*;
import main.staff.*;
import main.carte.*;
import main.launcher.employee.SaveEmployee;

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
    // Dans l'écran monitoring, le manager peut (s'il le souhaite) créer sa
    // composition
    // de tables personnalisée, mais par défaut, le restaurant est initialisé avec
    // 10 tables
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

    // Nombre de connexion au fonctionnalité de monitoring
    // Cette variable est incrémentée à chaque fois que le manager
    // se connecte à l'écran monitoring, afin qu'il n'ai pas à se réauthentifier
    // à chaque fois qu'il souhaite accéder à l'écran monitoring
    // Au bout de 3 fois, il doit se réauthentifier (voir MonityoringScreen.java)
    private static int nbrConnexion = 0;

    private static Map<String, Integer> shoppingList = new HashMap<>();

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

    public static void incrementeNbJourConsecutifs(Equipe team) {
        List<Employé> fullTeam = getEmployésList();
        // List<Employé> newfullTeam = new ArrayList<>();
        List<Employé> tmpEquipe = new ArrayList<Employé>();
        tmpEquipe.add(team.getServeur1());
        tmpEquipe.add(team.getServeur2());
        tmpEquipe.add(team.getCuisinier1());
        tmpEquipe.add(team.getCuisinier2());
        tmpEquipe.add(team.getCuisinier3());
        tmpEquipe.add(team.getCuisinier4());
        tmpEquipe.add(team.getBarman());
        tmpEquipe.add(team.getManager());

        for (Employé element : fullTeam) {
            boolean isFind = false;
            for (Employé element2 : tmpEquipe) {
                if (element.getNom().equals(element2.getNom()) && element.getPrenom().equals(element2.getPrenom())) {
                    element.setNbJoursConsecutifs(element.getNbJoursConsecutifs() + 1);
                    isFind = true;
                    break;
                }
            }
            if (isFind == false) {
                element.setNbJoursConsecutifs(0);
            }
            // newfullTeam.add(element);

        }

        // setEmployésList(newfullTeam);
        SaveEmployee.saveEmployeeListToFile();

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

    public static int getNbrConnexion() {
        return nbrConnexion;
    }

    public static void setNbrConnexion(int nbrConnexion) {
        Restaurant.nbrConnexion = nbrConnexion;
    }

    public static Map<String, Integer> getShoppingList() {
        return shoppingList;
    }

    public static void setShoppingList(Map<String, Integer> newShoppingList) {
        shoppingList = newShoppingList;
    }
}