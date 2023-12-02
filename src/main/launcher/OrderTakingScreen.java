package main.launcher;

import java.util.*;

import main.place.*;
import main.staff.Employé;
import main.staff.Serveur;
import main.carte.*;

//Classe qui gère l'écran de prise de commande du restaurant (1er écran)
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
    public static void tryShowingOrderTakingScreen(Scanner menuScanner) {
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
    // et donc de toutes les transactions (voir "sendPreparingCommand()")
    public static void showOrderTakingScreen(Scanner menuScanner) {
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
     * de rediriger vers la fonction appropriée. EX : Si dans la transaction les clients n'ont pas commandé, 
     * on redirige vers la fonction de prise de commande
     */
    public static void showOrderSelectionScreen(Scanner menuScanner, Serveur whichWaiter) {

        clearConsole();
        print("==========================================================================");
        print("SELECTIONNER LA TRANSACTION :");
        print("--------------------------------------------------------------------------");
        print("0 - Ajouter une nouvelle transaction");
        print("--------------------------------------------------------------------------\n");

        // Affiche la liste des transactions en cours sous le format :
        // [Numéro de la table] : [Etat de la transaction]
        for (int i = 0; i < Restaurant.getTransactionsListSize(); i++) {
            if(Restaurant.getTransactionsList().get(i).getServeurAssociate().equals(whichWaiter)) {
                print((i + 1) + " - Table n°" + Restaurant.getTransactionsList().get(i).getTable().getNuméro() + " : "
                + Restaurant.getTransactionsList().get(i).getState().getDescription());
            }
        }

        print("\n\n");
        String choixEcran = menuScanner.next();
        int choixEcranInt = Integer.parseInt(choixEcran);


        // Quand on ajoute une nouvelle transaction, on redirige vers la fonction de création de transaction
        if (choixEcran.equals("0")) {
            // On ajoute une nouvelle transaction
            addNewTransactionScreen(menuScanner, whichWaiter);

            // Si par contre i >= 1, alors c'est que l'on souhaite sélectionner une dès transaction déjà existante
            // pour intéragir avec elle. 
            // On redirige donc vers la fonction "redirectToAppropriateAction" qui permet, selon l'état de la transaction,
            // de rediriger vers la fonction appropriée 
            // (ex : Si dans la transaction les clients n'ont pas commandé, on redirige vers la fonction de prise de commande)
        } else if (choixEcranInt >= 1 && choixEcranInt <= Restaurant.getTransactionsListSize()) {
            
             // Désigne la transaction d'indice i (avec i = choixEcran - 1) :
            Transaction selectedTransaction = Restaurant.getTransactionsList().get(Integer.parseInt(choixEcran) - 1);

            // Pour les transactions existantes, on redirige vers la fonction appropriée (selon l'état de celle-ci)
            redirectToAppropriateAction(menuScanner, whichWaiter, selectedTransaction);
        } else {
            // Si le choix n'est pas valide, on réaffiche l'écran de sélection des
            // transactions
            showOrderSelectionScreen(menuScanner, whichWaiter);
        }
    }


    // Fonction de "transfert" d'une transaction (passé en paramètre) vers la fonction appropriée.
    // Et ceux selon son état (voir TransactionState.java pour plus d'infos sur les états)
    public static void redirectToAppropriateAction(Scanner menuScanner, Serveur whichWaiter, Transaction selectedTransaction) {

        // On récupère l'état de la transaction :
        TransactionState actualTableState = selectedTransaction.getState();

        switch (actualTableState) {
            case NOT_STARTED:
                // Redirige de l'état de transaction créé vers la fonction de prise de commande :
                takeCommand(menuScanner, whichWaiter, selectedTransaction);
                // A récupérer auprès de Rémi :
                // fonction takeCommand(menuScanner, whichWaiter, selectedTransaction);
                break;
            case PREPARING:
                // Une fois la commande prise, redirige vers la fonction d'envoie en cuisine
                sendCommandToPrepare(menuScanner, whichWaiter, selectedTransaction);
                break;
            case READY:
                // Quand les plats et les boissons sont prêts, redirige vers la fonction
                // d'apport des plats et boissons aux clients
                break;
            case EATING:
                // Une fois qu'ils ont fini de manger, redirige vers la fonction d'encaissement
                break;
            case CASHED:
                // Dès lors qu'ils ont payés, redirige vers la fonction de libération de la table
                // de la transaction, et d'impression du ticket de caisse
                break;
            default:
                break;
        }
    }


    // Fonction qui permet de prendre la commande des clients
    // Celle-ci est appelée quand l'état de la transaction est "NOT_STARTED"
    public static void takeCommand(Scanner menuScanner, Serveur whichWaiter, Transaction selectedTransaction) {

        clearConsole();
        print("==========================================================================");
        print("PRISE DE COMMANDE :\n");
        print("--------------------------------------------------------------------------");
        print("Table n°" + selectedTransaction.getTable().getNuméro() + " : "
                + selectedTransaction.getState().getDescription());
        print("--------------------------------------------------------------------------\n");

        print("1 - Ajouter les plats");
        print("2 - Ajouter les boissons\n");
        print("3 - Confirmer la commande et l'envoyer en cuisine\n");
        print("4 - Retour à la sélection des transactions\n\n\n");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                // Ajouter un plat
                Carte.passerCommandePlats(menuScanner, selectedTransaction);
                break;
            case "2":
                // Ajouter une boisson

                // ! TODO : URGENT - Passer les fonctions du package carte en static
                // Carte.passerCommandeBoissons(menuScanner, selectedTransaction);
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
                sendCommandToPrepare(menuScanner, whichWaiter, selectedTransaction);
                break;
        }
    }


    // Fonction qui permet d'envoyer la commande en cuisine
    // Celle-ci est appelée quand l'état de la transaction est "PREPARING"
    public static void sendCommandToPrepare(Scanner menuScanner, Serveur whichWaiter, Transaction selectedTransaction) {

        clearConsole();
        print("==========================================================================");
        print("ENVOYER LA COMMANDE EN CUISINE :");
        print("--------------------------------------------------------------------------");
        print("Table n°" + selectedTransaction.getTable().getNuméro() + " : "
                + selectedTransaction.getState().getDescription());
        print("--------------------------------------------------------------------------\n");

        print(("La commande a bien été envoyée en cuisine !\n"));
        print("Lorsque les plats et les boissons seront prêts, vous pourrez les apporter aux clients.\n");
        print("\n1 - Retour à la sélection des transactions\n\n");

        String choixEcran = menuScanner.next();

        if(choixEcran.equals("1")) {
            // Retour à la sélection des transactions
                showOrderSelectionScreen(menuScanner, whichWaiter);
                selectedTransaction.setState(TransactionState.PREPARING);
        } else {
            sendCommandToPrepare(menuScanner, whichWaiter, selectedTransaction);
        }
    }

    public static void addNewTransactionScreen(Scanner menuScanner, Serveur whichWaiter) {

        clearConsole();
        print("==========================================================================");
        print("AJOUTER UNE NOUVELLE TRANSACTION :");

        print("Combien de clients sont présents à la table (entre 1 et 8) ?");
        String nbrClientsStr = menuScanner.next();

        // La valeur convertie en int ci-dessous est utilisée pour l'affichage de la
        // fonction showTableConfirmationScreen (qui prend un int en paramètre)
        int nbrClientInt = Integer.parseInt(nbrClientsStr);

        // On vérifie que le nombre de client est bien compris entre 1 et 8
        // Si ce n'est pas le cas on propose de réessayer ou de retourner au menu
        if (Integer.parseInt(nbrClientsStr) < 1 || Integer.parseInt(nbrClientsStr) > 8) {
            clearConsole();
            print("==========================================================================");
            print("ERREUR : Le nombre de clients doit être compris entre 1 et 8 !\n");
            print("1 - Réessayer");
            print("2 - Retour au menu principal");

            String choixEcran = menuScanner.next();

            switch (choixEcran) {
                case "1":
                    addNewTransactionScreen(menuScanner, whichWaiter);
                    break;
                case "2":
                    App.showMainMenu();
                    break;
                default:
                    addNewTransactionScreen(menuScanner, whichWaiter);
                    break;
            }
        }

        clearConsole();
        print("==========================================================================");
        print("AJOUTER UNE NOUVELLE TRANSACTION :");

        print("Voici les tables disponibles pour " + nbrClientsStr + " client(s) :");
        print("Rappel : Pensez à ne pas proposer une table de 10 pour 3 clients !");
        print("--------------------------------------------------------------------------\n");

        // ! TODO : Il faudra changer la liste des tables disponibles en fonction du
        // ! choix du manager

        // On répertorie les tables disponibles :
        List<Table> availableTablesTmp = new ArrayList<>();

        // On vérifie pour toute les tables du restaurant...
        for (int i = 0; i < Restaurant.getTablesList().size(); i++) {

            // ... si la table est disponible (autrement dit non occupée) ...
            if (Restaurant.getTablesList().get(i).isDisponible()) {

                // ... et aussi s'il y a assez de couverts ou non :
                if (Restaurant.getTablesList().get(i).getNbrCouvert() >= Integer.parseInt(nbrClientsStr)) {

                    // Si oui, on l'affiche :
                    print((i + 1) + ") Table n°" + Restaurant.getTablesList().get(i).getNuméro() + " :"
                            + Restaurant.getTablesList().get(i).getNbrCouvert() + " couverts");

                    // Et (si oui) on l'ajoute à la liste temporaire des tables disponibles :
                    availableTablesTmp.add(Restaurant.getTablesList().get(i));
                }
            }
        }

        // Si aucune table n'est disponible, on affiche un message d'erreur :
        if (availableTablesTmp.size() == 0) {
            clearConsole();
            print("==========================================================================");
            print("ERREUR : Aucune table n'est disponible pour " + nbrClientsStr + " client(s) !\n");
            print("Avant qu'il partent, n'oubliez pas de proposer au client :");
            print("     a) D'attendre que d'autres tables se libèrent.");
            print("     b) De proposer qu'ils mangent sur des tables séparés ...\n\n");

            print("1 - Réessayer");
            print("2 - Retour au menu principal");

            String choixEcran = menuScanner.next();

            switch (choixEcran) {
                case "1":
                    // Réaffiche l'écran d'ajout de nouvelle transaction
                    addNewTransactionScreen(menuScanner, whichWaiter);
                    break;
                case "2":
                    // Retourne au menu principal
                    App.showMainMenu();
                    break;
                default:
                    addNewTransactionScreen(menuScanner, whichWaiter);
                    break;
            }
        }

        // Si au moins une table est disponible, on demande au serveur de choisir la table que les clients veulent
        print("--------------------------------------------------------------------------");
        print("Quelle table les clients ont choisis ?");
        print("Entrez le numéro de la table :\n");

        String choixEcran = menuScanner.next();
        int indexTable = Integer.parseInt(choixEcran);


        // Permet de récupérer le numéro de la table choisie par les clients
        // (qui est différent de l'index de la table dans la liste des tables disponibles)
        int newIndexTable = Table.numeroToIndex(availableTablesTmp, indexTable);

        // selectedOne désigne la table choisie par les clients
        Table selectedOne = availableTablesTmp.get(newIndexTable);

        // Redirige vers la confirmation de table :
        showTableConfirmationScreen(menuScanner, whichWaiter, selectedOne, nbrClientInt);
    }

    public static void showTableConfirmationScreen(Scanner menuScanner, Serveur whichWaiter, Table selectedOne,
            int nbrClients) {

        // On instancie alors la nouvelle transaction :
        instanciateTransaction(menuScanner, whichWaiter, selectedOne, nbrClients);

        clearConsole();
        print("==========================================================================");
        print("CONFIRMATION DE LA TRANSACTION :");

        print("La table " + selectedOne.getNuméro() + " est maintenant occupée par "
                + nbrClients + " client(s).");

        print("--------------------------------------------------------------------------");

        print("1 - Retour à la selection des transactions");
        print("2 - Retour au menu principal");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                showOrderSelectionScreen(menuScanner, whichWaiter);
                break;
            case "2":
                App.showMainMenu();
                break;
            default:
                showTableConfirmationScreen(menuScanner, whichWaiter, selectedOne, nbrClients);
                break;
        }

    }

    // Fonction qui instancie une transaction (avec le nombre exact de client
    // 'nbrClients')
    public static void instanciateTransaction(Scanner menuScanner, Serveur whichWaiter, Table selectedOne,
            int nbrClients) {

        // On instancie la transaction :
        // Serveur, nbrClients, table
        Transaction newTransaction = new Transaction(whichWaiter, nbrClients, selectedOne);

        // On ajoute la transaction à la liste des transactions du restaurant :
        Restaurant.getTransactionsList().add(newTransaction);

        // On change l'état de la table :
        selectedOne.setDisponible(false);

        // On change l'état de la transaction :
        newTransaction.setState(TransactionState.NOT_STARTED);
    }

    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }
}