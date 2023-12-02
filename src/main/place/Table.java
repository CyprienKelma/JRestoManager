package main.place;

import java.util.List;

public class Table {

    private int numéro;
    private int nbrCouvert;
    private boolean disponible;

    public Table(int numéro, int nbrCouvert, boolean disponible) {
        this.numéro = numéro;
        this.nbrCouvert = nbrCouvert;
        this.disponible = disponible;
    }

    /**
     * @return int return the numéro
     */
    public int getNuméro() {
        return numéro;
    }

    /**
     * @param numéro the numéro to set
     */
    public void setNuméro(int numéro) {
        this.numéro = numéro;
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
    public static int numeroToIndex(List<Table> list, int numero) {
        for (Table table : list) {
            if (table.getNuméro() == numero) {
                return list.indexOf(table);
            }
        }

        // Dans le cas ou le numéro de table n'est pas trouvé dans la liste
        
        throw new IllegalArgumentException("Numéro de table non trouvé : " + numero);
    }

}