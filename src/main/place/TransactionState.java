package main.place;


/*
 * Enum qui définit les différents états d'une transaction
 * Elle est très importante car elle permet de savoir comment gérer les commandes
 * entre les différents écrans (OrderTakingScreen, KitchenScreen, BarScreen) en fonction
 * de leurs avancements. Cela permet au serveur d'être disponible sur des commande d'état
 * différente. Par exemple, encaisser une commande puis acceuillir de nouveaux clients, etc...
 */
public enum TransactionState {
    // Toute les commandes commence à l'état "NOT_STARTED"
    // une fois qu'on intervient sur elle, on la passe à l'état suivant
    // (voir les fonctions de changement d'état plus bas)


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

    public static void checkIfCommandReady(Transaction transaction){
        // Si la commande est vide, on la passe à l'état "READY"
        // => La commande ne contient plus de plats ET plus de boissons
        // => Comme ça toute la commande est servie en même temps
        // (comme demandé dans le sujet)
        if((transaction.getCommandeDemandé().getBoissons().size() == 0) && (transaction.getCommandeDemandé().getPlats().size() == 0)){
            transaction.setState(TransactionState.READY);
            // TODO : check debug transactionState
            print("debug : la transaction " + transaction + " est bien passée à l'état " + TransactionState.READY);
        }
    }

    public static void print(String string) {
        System.out.println(string);
    }
}
