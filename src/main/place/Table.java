package main.place;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

    private int numero;
    private int nbrCouvert;
    private boolean disponible;

    public Table(int numero, int nbrCouvert, boolean disponible) {
        this.numero = numero;
        this.nbrCouvert = nbrCouvert;
        this.disponible = disponible;
    }

    // Fonction qui permet d'ajouter des tables au restaurant
    public static void addSomeTable(int nbrTable, int nbrCouvert){
        // On récupère l'index de la dernière table de la liste
        int lastTableIndex = Restaurant.getTablesList().size() - 1;

        // Pour récupérer le numéro de la dernière table de la liste
        int lastTableNumber = Restaurant.getTablesList().get(lastTableIndex).getNumero();
        for (int i = 0; i < nbrTable; i++) {
            Table table = new Table(lastTableNumber + i + 1, nbrCouvert, true);
            Restaurant.getTablesList().add(table);
        }
    }

    // Fonction qui permet d'afficher les tables du restaurants sous le format :
    // Table de n personnes : x disponibles
    // Avec n le nbr de couvert et x le nombre de table de ce type
    public static void showTables(List<Table> listTable){
        
        // On crée une map qui contient le nombre de couvert et le nombre de table
        Map<Integer, Integer> tableCompte = new HashMap<>();
        
        // On parcours la liste des tables pour compter le nombre de tables de chaque type
        for(Table table : listTable){
            // On récupère le nombre de couvert de la table
            int nbrCouvert = table.getNbrCouvert();
            // On ajoute le nombre de couvert de la table à la map
            tableCompte.put(nbrCouvert, tableCompte.getOrDefault(nbrCouvert, 0) + 1);
        }

        // On affiche le nombre de table de chaque type
        for (Map.Entry<Integer, Integer> entry : tableCompte.entrySet()) {
            int nbrCouvert = entry.getKey();
            int count = entry.getValue();
            print("- " + count + " table(s) de " + nbrCouvert + " personnes");
        }
    }

    /**
     * @return int return the numéro
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numéro the numéro to set
     */
    public void setNumero(int numéro) {
        this.numero = numéro;
    }

    /**
     * @return boolean return the disponible
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * @param disponible the disponible to set
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getNbrCouvert() {
        return nbrCouvert;
    }

    public void setNbrCouvert(int nbrCouvert) {
        this.nbrCouvert = nbrCouvert;
    }

    // Méthode qui permet de trouver l'index d'une table dans une liste de table
    public static int numeroToIndex(List<Table> list, int numeroVoulu) {
        for (Table table : list) {
            if (table.getNumero() == numeroVoulu) {
                return list.indexOf(table);
            }
        }

        // Dans le cas ou le numéro de table n'est pas trouvé dans la liste
        throw new IllegalArgumentException("Numéro de table non trouvé : " + numeroVoulu);
    }

    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        print("\n");
    }

}