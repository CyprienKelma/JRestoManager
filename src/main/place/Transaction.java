package main.place;
import main.staff.*;
import main.carte.*;

// Classe qui donne l'ensemble des informations de chaque transaction
// C'est à dire de chaque commande : 1 unique transaction <=> 1 unique table
public class Transaction {


    // Identifiant unique pour retrouver la transaction dans la liste des transactions
    //private int transactionId;

    // Le nombre de client pour cette transaction
    private int nbrClients;
    
    // Le serveur qui s'occupe de cette table
    private Serveur serveurAssociate;

    // L'état de la transaction (voir l'enum TransactionState)
    // Le but est de définir quand faire ou envoyer la commande entre les différent écrans
    // (OrderTakingScreen, KitchenScreen, BarScreen)
    private TransactionState state;

    // La table utilisée pour cette transaction
    private Table table;

    // La commande (Plat + Boisson) demandée par le client lors de la prise de commande
    Commande commandeDemandé = new Commande();

    // La commande reçu par les clients de la transaction une fois que les cuisiniers
    // et barman ont finis de préparer les plats et boissons
    Commande commandeReçu = new Commande();


    

    public Transaction(Serveur serveurAssociate, int nbrClients, Table table) {
        this.nbrClients = nbrClients;
        this.serveurAssociate = serveurAssociate;
        this.table = table;

        // Pas besoin de préciser l'état de la transaction, elle est toujours initialisée à NOT_STARTED
        // au moment ou la transaction est créée (voir enum TransactionState)
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

    /**
     * @return Commande return the commandeDemandé
     */
    public Commande getCommandeDemandé() {
        return commandeDemandé;
    }

    /**
     * @param commandeDemandé the commandeDemandé to set
     */
    public void setCommandeDemandé(Commande commandeDemandé) {
        this.commandeDemandé = commandeDemandé;
    }

    /**
     * @return Commande return the commandeReçu
     */
    public Commande getCommandeReçu() {
        return commandeReçu;
    }

    /**
     * @param commandeReçu the commandeReçu to set
     */
    public void setCommandeReçu(Commande commandeReçu) {
        this.commandeReçu = commandeReçu;
    }
    
}