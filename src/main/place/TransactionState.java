package main.place;

public enum TransactionState {
    // Quand les clients n'ont pas encore commandés
    NOT_STARTED("Commande à prendre"),

    // Quand les clients ont commandés. Leur commandes sont en cours de préparation
    PREPARING("Commande en cours de préparation"),

    // Quand les clients ont commandés. Leur commandes sont prêtes et servies
    READY("Commande prête et servie"),

    // Quand les clients ont encaissés et sont partis. La table est alors libéré
    CASHED("Commande encaissée et table libérée");

    private String description;

    TransactionState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
