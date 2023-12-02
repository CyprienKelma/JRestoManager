package main.place;

public enum TransactionState {
    // Quand les clients n'ont pas encore commandés
    NOT_STARTED("Commande à prendre"),

    // Quand les clients ont commandés. Leur commandes sont en cours de préparation
    PREPARING("Commande en cours de préparation"),

    // Quand les clients ont commandés. Leur commandes sont prêtes et à servir
    READY("Commande prête et à servir !"),

    // Quand les client ont reçu leur commandes. Ils sont en train de manger
    EATING("Commande servie et en train d'être mangée"),

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
