package main.place;

import java.util.List;
import main.staff.*;

public class Transaction {

    private int nbrClients;
    private Serveur serveurAssociate;
    private TransactionState state;
    private Table table;

    // TODO : les ajouter quand les stocks fonctionneront
    // private List<Plat> platList;
    // private List<Boisson> boissonList;

    public Transaction(Serveur serveurAssociate, int nbrClients, Table table) {
        this.nbrClients = nbrClients;
        this.serveurAssociate = serveurAssociate;
        this.table = table;
        this.state = TransactionState.NOT_STARTED;
    }

    public void setState(TransactionState newState) {
        this.state = newState;
    }

    /**
     * @return int return the nbrClients
     */
    public int getNbrClients() {
        return nbrClients;
    }

    /**
     * @param nbrClients the nbrClients to set
     */
    public void setNbrClients(int nbrClients) {
        this.nbrClients = nbrClients;
    }

    /**
     * @return Serveur return the serveurAssociate
     */
    public Serveur getServeurAssociate() {
        return serveurAssociate;
    }

    /**
     * @param serveurAssociate the serveurAssociate to set
     */
    public void setServeurAssociate(Serveur serveurAssociate) {
        this.serveurAssociate = serveurAssociate;
    }

    /**
     * @return TransactionState return the state
     */
    public TransactionState getState() {
        return state;
    }

    /**
     * @return Table return the table
     */
    public Table getTable() {
        return table;
    }

    /**
     * @param table the table to set
     */
    public void setTable(Table table) {
        this.table = table;
    }

}