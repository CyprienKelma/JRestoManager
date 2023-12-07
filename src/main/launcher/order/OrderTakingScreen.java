package main.launcher.order;

import java.io.IOException;
import java.util.*;

import main.place.*;
import main.staff.Serveur;
import main.carte.*;
import main.launcher.App;

//Classe qui gère l'écran de prise de commande du restaurant (1)
public class OrderTakingScreen {

    OrderTakingScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires qui n'ont
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    // Fonction qui tente d'afficher l'écran de prise de commande
    // - Si l'équipe n'est pas encore créée, indique qu'il faut d'abord en créer une
    // - Si l'équipe est créée, affiche l'écran de selection du serveur
    // (showOrderTakingScreen)
    public static void tryShowingOrderTakingScreen(Scanner menuScanner) throws IOException {
        if (!Restaurant.isOpen()) {
            clearConsole();
            print("==========================================================================\n");
            print("Accés refusé : Le restaurant n'est pas encore ouvert !\n");
            print("Pour prendre des commandes, votre manager doit d'abord créer une équipe, puis ouvrir le restaurant.\n");
            print("1 - Retour au menu principal\n");

            String choixEcran = menuScanner.next();

            if (choixEcran.equals("1")) {
                App.showMainMenu();
            } else {
                tryShowingOrderTakingScreen(menuScanner);
            }
        } else {
            // On accède à l'écran de sélection du serveur dans le cas ou le restaurant
            // est ouvert et et que l'équiope est formée
            showOrderTakingScreen(menuScanner);
        }
    }

    // Fonction qui affiche l'écran de sélection du serveur
    // Chaque serveur aura ses propres transactions, donc on doit choisir le serveur
    // Les cuisiniers et le barman, eux, recevront les commandes des 2 serveurs
    // et donc de toutes les transactions, indépendament du serveur (voir "sendPreparingCommand()")
    public static void showOrderTakingScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("Quel serveur êtes-vous ?\n");
        print("1 - " + Restaurant.getEquipeActuelle().getServeur1().getNom() + " "
                + Restaurant.getEquipeActuelle().getServeur1().getPrenom());
        print("2 - " + Restaurant.getEquipeActuelle().getServeur2().getNom() + " "
                + Restaurant.getEquipeActuelle().getServeur2().getPrenom());
        print("\n3- Retour au menu principal\n");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                // Appelle la fonction de l'écran de prise de commande
                showOrderSelectionScreen(menuScanner, Restaurant.getEquipeActuelle().getServeur1());
                break;
            case "2":
                // Appelle la fonction de l'écran de prise de commande
                showOrderSelectionScreen(menuScanner, Restaurant.getEquipeActuelle().getServeur2());
                break;
            case "3":
                // Appelle la fonction de l'écran de prise de commande
                App.showMainMenu();
                break;
            default:
                showOrderTakingScreen(menuScanner);
                break;
        }
    }

    /*
     * Fonction qui affiche l'écran de sélection des transactions
     * Une fois que l'on a choisi le serveur, on affiche la liste de SES transactions
     * Si l'on appuis sur 0, on créer une nouvelle transaction
     * Si l'on appuis sur une transaction existante, on redirige vers la fonction
     * "redirectToAppropriateAction" qui permet, selon l'état de la transaction,
     * de rediriger vers la fonction appropriée. EX : Si dans la transaction les
     * clients n'ont pas commandé, on redirige vers la fonction de prise de commande
     */
    public static void showOrderSelectionScreen(Scanner menuScanner, Serveur whichWaiter) throws IOException {

        clearConsole();
        int idtable = Restaurant.getTransactionsListSize() +1;
        print("==========================================================================");
        print("SELECTIONNER LA TRANSACTION :");
        print("--------------------------------------------------------------------------");
        print("0 - Ajouter une nouvelle transaction");
        print(idtable+" - Retour au choix des serveurs");
        print("--------------------------------------------------------------------------\n");

        // Affiche la liste des transactions en cours sous le format :
        // [Numéro de la table] : [Etat de la transaction]
        for (int i = 0; i < Restaurant.getTransactionsListSize(); i++) {

            // On affiche uniquement les transactions du serveur qui a été choisi
            // Par ailleurs, on n'affiche pas les transactions qui sont à l'état "CASHED", car elles
            // ne sont plus à traiter, les clients étant partis et la table étant de nouveau disponible
            if (Restaurant.getTransactionsList().get(i).getServeurAssociate().equals(whichWaiter) && !Restaurant.getTransactionsList().get(i).getState().equals(TransactionState.CASHED)) {
                print((i + 1) + " - Table n°" + Restaurant.getTransactionsList().get(i).getTable().getNumero() + " : "
                        + Restaurant.getTransactionsList().get(i).getState().getDescription());
            }
        }

        print("\n\n");
        String choixEcran = menuScanner.next();
        int choixEcranInt = Integer.parseInt(choixEcran);

        // Quand on ajoute une nouvelle transaction, on redirige vers la fonction de création de transaction
        if (choixEcran.equals("0")) {

            
            /*
             * On ajoute une nouvelle transaction. Le processus étant assez long, 
             * il se déroule au sein de la classe OrderCreationScreen
             */
            OrderCreationScreen.addNewTransactionScreen(menuScanner, whichWaiter);

            /*
             * Si par contre i > 1, alors c'est que l'on souhaite sélectionner une des
             * transactions déjà existante pour intéragir avec elle.
             * On redirige donc vers la fonction "redirectToAppropriateAction" qui permet,
             * selon l'état de la transaction, de rediriger vers la fonction appropriée
             * (ex : Si dans la transaction les clients n'ont pas commandé, on redirige vers
             * la fonction de prise de commande)
             */
        }else if (choixEcranInt == idtable){showOrderTakingScreen(menuScanner);

        } else if (choixEcranInt >= 1 && choixEcranInt <= Restaurant.getTransactionsListSize()) {

            // Désigne la transaction d'indice i (avec i = choixEcran - 1) :
            Transaction selectedTransaction = Restaurant.getTransactionsList().get(Integer.parseInt(choixEcran) - 1);

            // Pour les transactions existantes, on redirige vers la fonction appropriée
            // (selon l'état de celle-ci)
            redirectToAppropriateAction(menuScanner, whichWaiter, selectedTransaction);

        } else {
            // Si le choix n'est pas valide, on réaffiche l'écran de sélection des transactions
            showOrderSelectionScreen(menuScanner, whichWaiter);
        }
    }

    // Fonction de "transfert" d'une transaction (passé en paramètre) vers la bonne fonction.
    // Et ceux selon son état (voir TransactionState.java pour plus d'infos sur les états)
    // Joue un rôle essentielle puisque c'est elle qui permet de rediriger vers la fonction appropriée
    public static void redirectToAppropriateAction(Scanner menuScanner, Serveur whichWaiter,
            Transaction selectedTransaction) throws IOException {

        // On récupère l'état de la transaction :
        TransactionState actualTableState = selectedTransaction.getState();

        switch (actualTableState) {
            case NOT_STARTED:

                // Redirige de l'état de transaction créé vers la fonction de prise de commande :
                takeCommand(menuScanner, whichWaiter, selectedTransaction);
                break;
            case PREPARING:

                // Une fois la commande prise, redirige vers la fonction d'envoie en cuisine :
                sendCommandToPrepare(menuScanner, whichWaiter, selectedTransaction);
                break;
            case READY:

                // Quand les plats et les boissons sont prêts, redirige vers la fonction
                // d'apport des plats et boissons aux clients :
                bringPreparedCommand(menuScanner, selectedTransaction);
                break;
            case EATING:

                // Une fois qu'ils ont fini de manger, redirige vers la fonction d'encaissement :
                askForTheBill(menuScanner, selectedTransaction);
                break;
            case CASHED:

                // Dès lors qu'ils ont payés, redirige vers la fonction de libération de la
                // table de la transaction, et d'impression du ticket de caisse :

                break;
            default:
                break;
        }
    }

    // Fonction qui permet de prendre la commande des clients
    // Celle-ci est appelée quand l'état de la transaction est "NOT_STARTED"
    public static void takeCommand(Scanner menuScanner, Serveur whichWaiter, Transaction selectedTransaction)
            throws IOException {

        clearConsole();
        print("==========================================================================");
        print("PRISE DE COMMANDE :\n");
        print("--------------------------------------------------------------------------");
        print("Table n°" + selectedTransaction.getTable().getNumero() + " : "
                + selectedTransaction.getState().getDescription());
        print("--------------------------------------------------------------------------\n");

        print("1 - Ajouter les plats");
        print("2 - Ajouter les boissons\n");
        print("3 - Confirmer la commande et l'envoyer en cuisine\n");
        print("4 - Retour à la sélection des transactions\n");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                // Ajouter un plat
                Carte.passerCommandePlats(menuScanner, selectedTransaction);
                break;
            case "2":
                // Ajouter une boisson
                Carte.passerCommandeBoissons(menuScanner, selectedTransaction);
                break;
            case "3":
                // Confirmer la commande et l'envoyer en cuisine
                sendCommandToPrepare(menuScanner, whichWaiter, selectedTransaction);
                break;
            case "4":
                // Retour à la sélection des transactions
                showOrderSelectionScreen(menuScanner, whichWaiter);
                break;
            default:
                takeCommand(menuScanner, whichWaiter, selectedTransaction);
                break;
        }
    }

    // Fonction qui permet d'envoyer la commande en cuisine
    // Celle-ci est appelée quand l'état de la transaction est "PREPARING"
    public static void sendCommandToPrepare(Scanner menuScanner, Serveur whichWaiter, Transaction selectedTransaction) throws IOException {

        // On change l'état de la transaction sur PREPARING :
        // Ce qui veut dire que les plats qui la composent seront traités par les
        // cuisiniers
        // Pareil pour le barman avec les boissons
        selectedTransaction.setState(TransactionState.PREPARING);

        clearConsole();
        print("==========================================================================");
        print("ENVOYER LA COMMANDE EN CUISINE :");
        print("--------------------------------------------------------------------------");
        print("Table n°" + selectedTransaction.getTable().getNumero() + " : "
                + selectedTransaction.getState().getDescription());
        print("--------------------------------------------------------------------------\n");

        print(("La commande a bien été envoyée en cuisine et au barman !\n"));
        print("Lorsque les plats et les boissons seront prêts, vous pourrez les apporter aux clients.\n");
        print("1 - Retour à la sélection des transactions\n");
        print("2 - Retour au menu principal\n");

        String choixEcran = menuScanner.next();

        if (choixEcran.equals("1")) {

            // Retour à la sélection des transactions
            showOrderSelectionScreen(menuScanner, whichWaiter);

        } else if (choixEcran.equals("2")) {
            App.showMainMenu();
        } else {
            sendCommandToPrepare(menuScanner, whichWaiter, selectedTransaction);
        }
    }

    // Fonction qui permet d'apporter les plats et les boissons aux clients
    // Celle-ci est appelée quand l'état de la transaction est "READY"
    public static void bringPreparedCommand(Scanner menuScanner, Transaction transaction) throws IOException{
        
        clearConsole();
        print("==========================================================================");
        print("APPORTER LES PLATS ET BOISSONS AUX CLIENTS :");
        print("--------------------------------------------------------------------------");
        print("Table n°" + transaction.getTable().getNumero() + " : "
                + transaction.getState().getDescription());
        print("--------------------------------------------------------------------------\n");

        print(("Les plats et les boissons pour cette table sont prêts !\n"));
        print(" - Aller les chercher au bar et en cuisine.");
        print(" - Vous pouvez ensuite les apporter aux clients.\n");
        print("--------------------------------------------------------------------------\n");
        print("1 - Retour à la selection des transactions\n");
        print("2 - Retour au menu principal\n\n");

        // A ce stade on considère que les clients ont reçu leur commande
        // la transaction peut donc passer à l'état "EATING"
        transaction.setState(TransactionState.EATING);

        String choixEcran = menuScanner.next();

        Serveur serveur = transaction.getServeurAssociate();

        if (choixEcran.equals("1")) {

            // Retour à la sélection des transactions
            showOrderSelectionScreen(menuScanner, serveur);

        } else if (choixEcran.equals("2")) {
            App.showMainMenu();
        } else {
            bringPreparedCommand(menuScanner, transaction);
        }
    }

    public static void askForTheBill(Scanner menuScanner, Transaction transaction) throws IOException{
        
        clearConsole();
        print("============================================================================");
        print("DEMANDER L'ADDITION :");
        print("----------------------------------------------------------------------------");
        print("Table n°" + transaction.getTable().getNumero() + " : "
                + transaction.getState().getDescription());
        print("----------------------------------------------------------------------------\n\n");

        print("Selectionner l'action à effectuer :\n");
        print("1 - Procéder à l'addition (les clients ont fini de manger et souhaitent payer)\n ");
        print("2 - Attendre encore (retour à la sélection des transactions)");
        print("----------------------------------------------------------------------------\n\n");
        
        String choixEcran = menuScanner.next();
        

        if(choixEcran.equals("1")){
            // On change l'état de la transaction sur CASHED :
            // Ce qui veut dire que les clients ont payés et que la table est libérée
            transaction.setState(TransactionState.CASHED);
            // On libère la table :
            transaction.getTable().setDisponible(true);
            
            payment(menuScanner, transaction);
            // TODO : imprimer le ticket de caisse
            // Voir fontion de monitoring

            // On retourne à la sélection des transactions :
            showOrderSelectionScreen(menuScanner, transaction.getServeurAssociate());
        } else if(choixEcran.equals("2")){
            // On retourne à la sélection des transactions :
            showOrderSelectionScreen(menuScanner, transaction.getServeurAssociate());
        } else {
            askForTheBill(menuScanner, transaction);
        }
    }

    public static void confirmTheBill(Scanner menuScanner, Transaction transaction, int nbrfacture) throws IOException {
        clearConsole();
        print("============================================================================");
        print("CONFIRMATION D'ADDITION :");
        print("----------------------------------------------------------------------------");
        print("Table n°" + transaction.getTable().getNumero() + " : "
                + transaction.getState().getDescription());
        print("----------------------------------------------------------------------------\n");
    
        print("La transaction " + transaction.getTransactionId() + " est bien confirmée.");
        print("Les clients ont payé en " + nbrfacture + " fois et la table " + transaction.getTable().getNumero() + " est libérée.\n\n");
    
        print("Ticket de caisse :");
        print("----------------------------------------------------------------------------");
        print("\t\t\tCommande N°" + transaction.getTransactionId() + " - Table n°" + transaction.getTable().getNumero());
        print("\t\t\tServeur : " + transaction.getServeurAssociate().getNom() + " " + transaction.getServeurAssociate().getPrenom() + "\n");
    
        print("----------------------------------------------------------------------------");
        print("\t\t\tArticles : \n");
    
        // Extraire les entrées de plat et de boisson avant la boucle
        Map.Entry<String, Integer> platEntry = null;
        Map.Entry<String, Integer> boissonEntry = null;
    
        // Afficher la liste des plats avec quantité et prix
        for (Map.Entry<String, Integer> entry : transaction.getCommandeReçu().getPlats().entrySet()) {
            print(entry.getValue() + "x " + entry.getKey() + " - " + Carte.getPrixPlat(entry.getKey()) * entry.getValue() + " euros");
            platEntry = entry; // Mettre à jour platEntry pour le dernier plat
        }
    
        print("\n");
    
        // Afficher la liste des boissons avec quantité et prix
        for (Map.Entry<String, Integer> entry : transaction.getCommandeReçu().getBoissons().entrySet()) {
            print(entry.getValue() + "x " + entry.getKey() + " - " + Carte.getPrixBoisson(entry.getKey()) * entry.getValue() + " euros");
            boissonEntry = entry; // Mettre à jour boissonEntry pour la dernière boisson
        }
    
        print("\n----------------------------------------------------------------------------\n");
        double total = Carte.affichertotalPlatCommande(transaction);
        total = total + Carte.affichertotalBoissonCommande(transaction);
        print("TOTAL : " + total + " euros");
        print("Par CB : " + total + " euros\n");
        print("\t\t Nous vous remercions de votre visite");
        print("\t\t\t A Bientôt\n");

        print("============================================================================");
        print("1 - Continuer et retourner Ecran d'accueil\n");
    
    
        String choixEcran = menuScanner.next();
        if (choixEcran.equals("1")) {
            // On change l'état de la transaction sur CASHED :
            // Ce qui veut dire que les clients ont payé et que la table est libérée
            transaction.setState(TransactionState.CASHED);
    
            // On libère la table :
            transaction.getTable().setDisponible(true);
    
            // TODO : imprimer le ticket de caisse
            // Voir fonction de monitoring
            String nameserv = transaction.getServeurAssociate().getNom()+" "+transaction.getServeurAssociate().getPrenom();
    
            // Appel de la fonction pour sauvegarder le ticket de caisse dans le fichier "facture.txt"
            BillsManagement.sauvegardeFacture(
                nameserv,
                transaction.getTable().getNumero(),
                platEntry,
                boissonEntry,
                total,
                nbrfacture,
                transaction
            );
    
            // On retourne à la sélection des transactions :
            showOrderSelectionScreen(menuScanner, transaction.getServeurAssociate());
        } else if (choixEcran.equals("2")) {
            // On retourne à la sélection des transactions :
            showOrderSelectionScreen(menuScanner, transaction.getServeurAssociate());
        } else {
            confirmTheBill(menuScanner, transaction, nbrfacture);
        }
    }
    

    public static void payment(Scanner menuScanner, Transaction transaction) throws IOException{
        
        clearConsole();
        print("============================================================================");
        print("CONFIRMATION D'ADDITION :");
        print("----------------------------------------------------------------------------");
        print("Table n°" + transaction.getTable().getNumero() + " : "
                + transaction.getState().getDescription());
        print("----------------------------------------------------------------------------\n\n");
        print("0 - Retour confirmation d'addition\n");
        print("1 - Paiement en 1 fois\n");
        print("2 - Paiement en plusieurs fois\n");    

        String choixEcran = menuScanner.next();

        if(choixEcran.equals("0")){
            // Retour confirmation facture :
            askForTheBill(menuScanner, transaction);
        } else if(choixEcran.equals("1")){
            // On retourne à la sélection des transactions :
            confirmTheBill(menuScanner, transaction,1);
        } else if(choixEcran.equals("2")){

            double total = Carte.affichertotalPlatCommande(transaction);
            total += Carte.affichertotalBoissonCommande(transaction);
            int nbrmax = (int) total/9;
            int nombre = 0;
            boolean isValidInput = false;
            String choixEcran1;

            print("----------------------------------------------------------------------------\n");
            print("Paiement en plusieurs fois, jusqu'à un maximum de " + nbrmax + " factures\n");

            while (!isValidInput) {
                print("Donnez le nombre de factures souhaitées (inférieur ou égale à " + nbrmax + ") : ");
                choixEcran1 = menuScanner.next();

                try {
                    nombre = Integer.parseInt(choixEcran1);

                    if (nombre <= nbrmax) {
                        isValidInput = true;
                    } else {
                        print("Le nombre doit être inférieur ou égale à " + nbrmax + ". Veuillez réessayer.\n");
                    }
                

                } catch (NumberFormatException e) {
                    print("Erreur de saisie. Veuillez entrer un nombre valide.\n");
                }
            }
            int nbr = nombre;
            confirmTheBill(menuScanner, transaction,nbr);
        }else {
            payment(menuScanner, transaction);
        }
    }
    

    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }
}