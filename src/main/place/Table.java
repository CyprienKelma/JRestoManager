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

    public static int numeroToIndex(List<Table> list, int numero) {
        int ret = -1;
        for (Table table : list) {
            if (table.getNuméro() == numero) {
                ret = list.indexOf(table);
            }
        }
        return ret;
    }

}